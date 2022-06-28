package com.javaex.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.GuestbookVo;

@Repository
public class GuestbookDao {

	@Autowired
	private SqlSession sqlSession;
	
	
	//전체리스트 가져오기
	public List<GuestbookVo> selectList(){
		System.out.println("GuestbookDao>selectList()");
		
		List<GuestbookVo> guestbookList = sqlSession.selectList("guestbook.selectList");
		
		return guestbookList;
		
	}
	
	
	//방명록 저장(ajax)
	public int insertGuest(GuestbookVo guestbookVo) {
		System.out.println("GuestbookDao>insertGuest()");
		
		int count = sqlSession.insert("guestbook.insertSelectKey", guestbookVo);
		
		return count;
	}
	
	//방명록 저장후 등록한 데이타 가져오기(ajax)
	public GuestbookVo getGuest(int no) {
		System.out.println("GuestbookDao>getGuest()");
		
		GuestbookVo guestbookVo = sqlSession.selectOne("guestbook.getGuest", no);
		
		return guestbookVo;
	}
	
	
	
	
}
