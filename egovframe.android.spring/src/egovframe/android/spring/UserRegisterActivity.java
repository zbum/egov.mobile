/*
 * Copyright 2008-2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package egovframe.android.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import egovframe.android.spring.entity.SampleVO;

public class UserRegisterActivity extends Activity implements TextWatcher{
	
	private EditText name;
	
	private EditText desc;
	
	private RadioGroup useYN;
	
	private Button regButton;
	
	private ProgressDialog progressDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sample_register);
		
		name = (EditText)findViewById(R.id.name);
		name.addTextChangedListener(this);
		
		useYN = (RadioGroup)findViewById(R.id.useYN);
		
		desc = (EditText)findViewById(R.id.description);
		desc.addTextChangedListener(this);
		
		regButton = (Button)findViewById(R.id.regButton);
		
		regButton.setEnabled(false);
		
		regButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				new RegisterUserTask().execute();
			}
		});
	}
	
	
	public void showLoadingProgressDialog() {
		progressDialog = ProgressDialog.show(this, "", "Sending request. Please wait...", true);
	}

	public void dismissProgressDialog() {

		if (progressDialog != null) {
			progressDialog.dismiss();
		}
	}
	
	
	class RegisterUserTask extends AsyncTask<String, Void, Boolean> {

		@Override
		protected void onPreExecute() {
			showLoadingProgressDialog();
		}

		@Override
		protected Boolean doInBackground(String... args) {
			RestTemplate restTemplate = new RestTemplate();

			List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
			acceptableMediaTypes.add(new MediaType("application", "json"));

			HttpHeaders requestHeaders = new HttpHeaders();
			requestHeaders.setAccept(acceptableMediaTypes);
			
			RadioButton checkedButton = (RadioButton)findViewById(useYN.getCheckedRadioButtonId());
			
			SampleVO sampleVO = new SampleVO();
			sampleVO.setName(name.getText().toString());
			sampleVO.setUseYn(checkedButton.getText().toString());
			sampleVO.setDescription(desc.getText().toString());

			HttpEntity<?> requestEntity = new HttpEntity<Object>(sampleVO,requestHeaders);

			String url = "http://10.0.2.2:8080/ws/sample/samples";

			try {
				restTemplate.exchange(url, HttpMethod.POST, requestEntity,String.class);

			} catch (Exception e) {
				e.printStackTrace();
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(UserRegisterActivity.this, getResources().getString(R.string.message_error_network),
								Toast.LENGTH_LONG).show();
					}
				});
				
				return false;

			}

			return true;

		}

		@Override
		protected void onPostExecute(Boolean success) {
			dismissProgressDialog();
			if ( success ){
				AlertDialog.Builder ab=new AlertDialog.Builder(UserRegisterActivity.this);
				ab.setMessage("User information was registered successfully!!");
				ab.setPositiveButton("ok", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
							finish();	
					}
				});
				ab.show();
				
				
			}
		}

	}

	/*
	 * [START]TextWatcher Implementations ...
	 */

	@Override
	public void afterTextChanged(Editable arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		if ( !"".equals(name.getText().toString()) && !"".equals(desc.getText().toString()) ){
			regButton.setEnabled(true);
		}else{
			regButton.setEnabled(false);
		}
		
	}
	
	/*
	 * [END]TextWatcher Implementations ...
	 */

}
