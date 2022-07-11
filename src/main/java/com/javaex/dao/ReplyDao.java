package com.javaex.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.ReplyVo;

@Repository
public class ReplyDao {

	@Autowired
	private SqlSession sqlSession;
	
	//글리스트 가져오기
	public List<ReplyVo> selectList(){
		System.out.println("replyDao/selectList");
		
		return  sqlSession.selectList("reply.selectList");
	}
	
	
	//글저장(루트글, 댓글)
	public int insert(ReplyVo replyVo) {
		System.out.println("replyDao/insert");
		
		return  sqlSession.insert("reply.insert", replyVo);
	}
	
	
	//글순서 증가시키기
	public int increaseOrderNo(int groupNo, int orderNo) {
		System.out.println("replyDao/increaseOrderNo");
		Map<String, Integer> imap = new HashMap<String, Integer>();
		imap.put("groupNo", groupNo);
		imap.put("orderNo", orderNo);
		
		return sqlSession.update("reply.increaseOrderNo", imap);
	}
	
	
	//글가져오기
	public ReplyVo select(int no) {
		System.out.println("replyDao/select");
		
		return sqlSession.selectOne("reply.select", no);
	}
	
	
	//조회수 증가
	public int updateHit(int no) {
		System.out.println("replyDao/updateHit");
		
		return sqlSession.update("reply.updateHit", no);
	}
	
	
	//글수정
	public int update(ReplyVo replyVo) {
		System.out.println("replyDao/update");
			
		return sqlSession.update("reply.update", replyVo);
	}
	
	
	//글삭제
	public int delete(ReplyVo replyVo) {
		System.out.println("replyDao/delete");
			
		return sqlSession.delete("reply.delete", replyVo);
	}
	
}
