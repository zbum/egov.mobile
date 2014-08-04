package egovframe.android.spring.entity;

import java.util.ArrayList;


/**
 * 전자정부 표준프레임워크 오픈커뮤니티 
 * 전자정부표준프레임워크 모바일 - 안드로이드
 * 
 * Rest Webservices를 제공하는 서버로부터 받은 User목록 정를 객체화 하기위한
 * entity 클래스 
 * 
 * @author zbum (jibum.jung@gmail.com)
 *
 */
public class SampleVOList {
	
	private ArrayList<SampleVO> sampleVOList;

	public ArrayList<SampleVO> getSampleVOList() {
		return sampleVOList;
	}

	public void setSampleVOList(ArrayList<SampleVO> sampleVOList) {
		this.sampleVOList = sampleVOList;
	}
	
	

}
