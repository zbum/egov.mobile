package egovframework.android.user.webservices.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.stereotype.Component;

import egovframework.android.user.webservices.UserInfo;
import egovframework.android.user.webservices.UserWebService;

@Component("userWebService")
public class UserWebServiceImpl implements UserWebService {

	public Response connect(String username, String password) {
		if (username == null || "".equals(username) || password == null
				|| "".equals(password)) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		List<String> response = new ArrayList<String>();
		response.add(username);
		response.add(password);
		return Response.ok(response).build();
	}

	public UserInfo getUserInfo(String username) {
		UserInfo userInfo = new UserInfo();
		userInfo.setAge(100);
		userInfo.setName("zbum");
		userInfo.setGender("male");
		
		return userInfo;
		//return Response.ok(userInfo).build();
	}
}
