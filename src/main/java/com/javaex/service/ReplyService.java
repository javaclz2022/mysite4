package com.javaex.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaex.dao.ReplyDao;
import com.javaex.vo.ReplyVo;

@Service
public class ReplyService {
	
	@Autowired
	private ReplyDao replyDao;
	
	
	// 리스트
	public List<ReplyVo> getReplyList(){
		System.out.println("replyService/getReplyList");
		
		return  replyDao.selectList();
	}
	
	
	// 글쓰기(루트글, 댓글)
	public int addReply(ReplyVo replyVo, String type) {
		System.out.println("replyService/addReply");
		
		
		if("rootWrite".equals(type)) { //최상위글(rootWrite)일때
			return replyDao.insert(replyVo);
			
		}else { //댓글(replyWrite)일때
			int groupNo = replyVo.getGroupNo();
			int orderNo = replyVo.getOrderNo();
			int depth = replyVo.getDepth();
			
			//자신보다 orderNo가 큰 글 +1시키기
			replyDao.increaseOrderNo(groupNo, orderNo);
			
			//orderNo, depth 부모값에서 증가시키기
			replyVo.setOrderNo(orderNo+1);
			replyVo.setDepth(depth+1);
			
			return replyDao.insert(replyVo);
		}
		
	}
	
	
	// 글 가져오기(글읽기, 수정폼, 댓글쓰기폼)
	public ReplyVo getReply(int no, String type ) {
		System.out.println("replyService/getReply");
		
		if("read".equals(type)) {//읽기 일때는 조회수 올림
			replyDao.updateHit(no);
			ReplyVo replyVo = replyDao.select(no);
			return replyVo;
		}else { //수정폼(modifyForm) 일때, 댓글쓰기폼(reWriteForm)는 조회수 올리지 않음
			ReplyVo replyVo = replyDao.select(no);
			return replyVo;
		}
	}
	
	
	//글 수정
	public int modifyReply(ReplyVo replyVo) {
		System.out.println("replyService/modifyReply");
		
		return replyDao.update(replyVo);
	}
	

	//글 삭제
	public int removeReply(ReplyVo replyVo) {
		System.out.println("replyService/removeReply");
		
		return replyDao.delete(replyVo);
	}
	
}
