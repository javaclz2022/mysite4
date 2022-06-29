package com.javaex.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaex.dao.GuestbookDao;
import com.javaex.vo.GuestbookVo;

@Service
public class GuestBookService {

	@Autowired
	private GuestbookDao guestbookDao;
	
	//전체리스트 가져오기
	public List<GuestbookVo> getGuestList(){
		System.out.println("GuestBookService>getGuestList()");
		
		List<GuestbookVo> guestbookList = guestbookDao.selectList();
		
		return guestbookList;
	}
	
	
	//방명록 저장(ajax)
	public GuestbookVo addGuest(GuestbookVo guestbookVo) {
		System.out.println("GuestBookService>addGuest()");
		
		
		//저장
		System.out.println("전-->" + guestbookVo);
		int count = guestbookDao.insertGuest(guestbookVo);
		System.out.println("후-->" + guestbookVo);
		
		int no =guestbookVo.getNo();
		
		//방금저장한 1개의 데이터를 가져온다
		GuestbookVo gVo = guestbookDao.getGuest(no);
		
		return gVo;
	}
	
	//방명록 삭제
	public String removeGuest(GuestbookVo guestbookVo) {
		System.out.println("GuestBookService>removeGuest()");
		
		String state;
		
		int count = guestbookDao.guestDelete(guestbookVo);
		
		if(count>0) {
			state = "succeess";
		}else {
			state = "fail";
		}
		
		return state;
	}
	
	
}
