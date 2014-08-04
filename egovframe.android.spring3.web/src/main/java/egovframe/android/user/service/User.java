package egovframe.android.user.service;


/**
 * 전자정부 표준프레임워크 오픈커뮤니티 
 * 전자정부표준프레임워크 모바일 - 안드로이드
 * 
 * User목록 정보를 객체화 하기위한
 * entity 클래스 
 * 
 * @author zbum (jibum.jung@gmail.com)
 *
 */
public class User {
	
	private String name;
	
	private int age;
	
	private String sex;
	
	

	public User(String name, int age, String sex) {
		super();
		this.name = name;
		this.age = age;
		this.sex = sex;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	

}
