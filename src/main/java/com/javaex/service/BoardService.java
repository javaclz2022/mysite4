package com.javaex.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaex.dao.BoardDao;
import com.javaex.vo.BoardVo;

@Service
public class BoardService {

	@Autowired
	private BoardDao boardDao;

	//리스트(일반)
	public List<BoardVo> getBoardList4(){
		System.out.println("BoardService>getBoardList4()");
		
		List<BoardVo> boardList = boardDao.selectList4();
		
		return boardList;
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
