package egovframe.android.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import egovframe.android.spring.entity.SampleVOList;
import egovframe.android.spring.entity.SampleVO;

/**
 * 전자정부 표준프레임워크 오픈커뮤니티 전자정부표준프레임워크 모바일 - 안드로이드
 * 
 * RestTemplate을 활용하여 사용자 목록으로 조회하고 ListView에 표시하는 화면구현을 위한 ListActivity
 * 
 * @author zbum (jibum.jung@gmail.com)
 * 
 */
public class UserListActivity extends ListActivity {

	private Button searchButton;

	private EditText searchCondition;

	private ProgressDialog progressDialog;

	private ArrayAdapter<String> mAdapter;

	private ArrayList<String> mStrings = new ArrayList<String>();

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sample_list);

		searchButton = (Button) findViewById(R.id.searchButton);
		searchCondition = (EditText) findViewById(R.id.searchCondition);

		searchButton.setEnabled(false);
		searchCondition.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable arg0) {
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
			}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				if (searchCondition.getText() == null || "".equals(searchCondition.getText().toString())) {
					searchButton.setEnabled(false);
				} else {
					searchButton.setEnabled(true);
				}

			}

		});

		searchButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				new RetrieveUserListTask().execute(searchCondition.getText().toString());
				searchCondition.setText("");
				searchButton.setEnabled(false);
			}
		});

		mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mStrings);

		setListAdapter(mAdapter);
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		new RetrieveUserListTask().execute(searchCondition.getText().toString());
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		String name = mAdapter.getItem(position);
		
		AlertDialog.Builder ab=new AlertDialog.Builder(this);
		ab.setMessage("Selected User Name is ["+name+"]");
		ab.setPositiveButton("ok", null);
		ab.show();
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.addSubMenu(1, 1, 1, "Register User");

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();

		switch (itemId) {
		case 1:
			Intent intent = new Intent(this, UserRegisterActivity.class);
			startActivity(intent);
			break;
			
		default:

		}
		return super.onOptionsItemSelected(item);
	}

	public void showLoadingProgressDialog() {
		progressDialog = ProgressDialog.show(this, "", "Loading. Please wait...", true);
	}

	public void dismissProgressDialog() {

		if (progressDialog != null) {
			progressDialog.dismiss();
		}
	}

	class RetrieveUserListTask extends AsyncTask<String, Void, SampleVO[]> {

		@Override
		protected void onPreExecute() {
			showLoadingProgressDialog();
		}

		@Override
		protected SampleVO[] doInBackground(String... args) {
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.getMessageConverters();

			List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
			acceptableMediaTypes.add(new MediaType("application", "json"));

			HttpHeaders requestHeaders = new HttpHeaders();
			requestHeaders.setAccept(acceptableMediaTypes);

			HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);

			String url = "http://10.0.2.2:8080/ws/sample/samples/{empName}";

			String empName = args[0];
			if ( args[0] == null || "".equals(args[0])) {
				empName = "%";
			}
			
			try {
				ResponseEntity<SampleVO[]> responseEntity = null;

				responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, SampleVO[].class, empName);
				SampleVO[] vos = responseEntity.getBody();
				return vos;
			} catch (Exception e) {
				try {
					ResponseEntity<SampleVOList> responseEntity = null;
					responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, SampleVOList.class, empName);
					SampleVOList vos = responseEntity.getBody();
					return vos.getSampleVOList().toArray(new SampleVO[]{}); 
				}catch(Exception ee) {
					ee.printStackTrace();
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							Toast.makeText(UserListActivity.this, getResources().getString(R.string.message_error_network),
									Toast.LENGTH_LONG).show();
						}
					});
				}

			}

			return new SampleVO[]{};

		}

		@Override
		protected void onPostExecute(SampleVO[] vos) {
			mAdapter.clear();
			for (SampleVO vo : vos) {
				mAdapter.add(vo.getName());
			}
			dismissProgressDialog();
		}

	}

}