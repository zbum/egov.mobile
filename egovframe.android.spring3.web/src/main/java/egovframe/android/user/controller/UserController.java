package egovframe.android.user.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import egovframe.android.user.service.User;

/**
 * 전자정부 표준프레임워크 오픈커뮤니티 
 * 전자정부표준프레임워크 모바일 - 안드로이드
 * 
 * 사용자관련 Rest Webservices를 제공하는 Controller 클래
 * 
 * @author zbum (jibum.jung@gmail.com)
 *
 */
@Controller
public class UserController {
	
	@RequestMapping(value="/ws/getUserList/{empName}", method=RequestMethod.GET )
	public ArrayList<User> getUserList(@PathVariable("empName") String empName ) {
		ArrayList<User> userList = new ArrayList<User>();

		for ( int inx = 0 ; inx < 20 ; inx++ ){
			userList.add(new User(empName+inx , 10+inx, "male"));
		}

		return userList;
	}
	
	@RequestMapping(value="/ws/getUserArray/{empName}", method=RequestMethod.GET )
	public User[] getUserArray(@PathVariable("empName") String empName ) {
		User[] users = new User[20];

		for ( int inx = 0 ; inx < 20 ; inx++ ){
			users[inx] = new User("user"+inx , 10+inx, "male");
		}

		return users;
	}
	

}
