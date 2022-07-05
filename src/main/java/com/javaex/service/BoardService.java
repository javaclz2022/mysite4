package com.javaex.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaex.dao.BoardDao;
import com.javaex.vo.BoardVo;

@Service
public class BoardService {

	@Autowired
	private BoardDao boardDao;

	//리스트(일반)
	public Map<String, Object> getBoardList4(int crtPage){
		System.out.println("BoardService>getBoardList4()");
		
		/////////////////////////////////////////////
		// 리스트가져오기
		/////////////////////////////////////////////
		
		//페이지당 글갯수
		int listCnt = 10;
		
		//현재페이지
		crtPage = (crtPage>0) ? crtPage : (crtPage=1);
		
		//시작글번호
		int startRnum = (crtPage-1)*listCnt + 1 ;
		
		//끝글번호
		int endRnum = (startRnum + listCnt) - 1;
		
		List<BoardVo> boardList = boardDao.selectList4(startRnum, endRnum);
		
		//////////////////////////////////////////////
		// 페이징 계산
		//////////////////////////////////////////////
		
		//전체글갯수
		int totalCnt = boardDao.selectTotalCnt();
		
		//페이지당 버튼 갯수
		int pageBtnCount = 5;
		
		//마지막 버튼 번호
		int endPageBtnNo = (int)Math.ceil(crtPage/(double)pageBtnCount)*pageBtnCount  ;
		
		//마지막 버튼 번호
		int startPageBtnNo = (endPageBtnNo-pageBtnCount)+1;
		
        //다음 화살표 유무
        boolean next = false;
        if( (listCnt*endPageBtnNo) < totalCnt  ) {
        	 next=true;
        
        }else {
        	endPageBtnNo =(int)Math.ceil(totalCnt/(double)listCnt);       
        }
       
        //이전 화살표 유무
        boolean prev = false;
        if(startPageBtnNo != 1) {
        	prev=true;
        }
        
        //리스트 페이징 정보 묶기
        Map<String, Object> pMap = new HashMap<String, Object>();
        pMap.put("boardList", boardList);
        pMap.put("prev", prev);
        pMap.put("startPageBtnNo", startPageBtnNo);
        pMap.put("endPageBtnNo", endPageBtnNo);
        pMap.put("next", next);
        
		return pMap;
	}
	

	
	
	
	
	// 리스트(일반 + 검색)
	public List<BoardVo> getBoardList3(String keyword) {
		System.out.println("BoardService>getBoardList3()");

		List<BoardVo> boardList = boardDao.selectList3(keyword);

		return boardList;
	}

	// 리스트(리스트만 출력할때)
	public List<BoardVo> getBoardList() {
		System.out.println("BoardService>getBoardList()");

		List<BoardVo> boardList = boardDao.selectList();

		return boardList;
	}

	// 리스트(검색했을때)
	public List<BoardVo> getBoardList2(String keyword) {
		System.out.println("BoardService>getBoardList2()");

		List<BoardVo> boardList = boardDao.selectList2(keyword);

		return boardList;
	}

	// 글쓰기
	public int addBoard(BoardVo boardVo) {
		System.out.println("boardService>addBoard");

		return boardDao.insert(boardVo);
	}

	// 글가져오기
	public BoardVo getBoard(int no, String type) {
		System.out.println("boardService>getBoard");

		if ("read".equals(type)) {// 읽기 일때는 조회수 올림
			boardDao.updateHit(no);
			BoardVo boardVo = boardDao.select(no);
			return boardVo;

		} else { // 수정 일때는 조회수 올리지 않음
			BoardVo boardVo = boardDao.select(no);
			return boardVo;
		}

	}

	// 글수정
	public int modifyBoard(BoardVo boardVo) {
		System.out.println("boardService>modifyBoard");

		return boardDao.update(boardVo);
	}

	// 글삭제
	public int removeBoard(BoardVo boardVo) {
		System.out.println("boardService/removeBoard");

		return boardDao.delete(boardVo);

	}
}
