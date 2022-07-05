package com.javaex.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.BoardVo;

@Repository
public class BoardDao {

	@Autowired
	private SqlSession sqlSession;

	//리스트(일반)
	public List<BoardVo> selectList4(){
		System.out.println("BoardDao>selectList4()");
		
		List<BoardVo> boardList = sqlSession.selectList("board.selectList4");
		
		return boardList;
	}
	
	
	
	public List<BoardVo> selectList3(String keyword) {
		System.out.println("BoardDao>selectList3()");

		List<BoardVo> boardList = sqlSession.selectList("board.selectList3", keyword);

		return boardList;
	}

	// 글전체 가져오기(리스트만 출력할때)
	public List<BoardVo> selectList() {
		System.out.println("BoardDao>selectList()");

		List<BoardVo> boardList = sqlSession.selectList("board.selectList");

		return boardList;
	}

	public List<BoardVo> selectList2(String keyword) {
		System.out.println("BoardDao>selectList2()");
		System.out.println(keyword);

		List<BoardVo> boardList = sqlSession.selectList("board.selectList2", keyword);
		return boardList;
	}

	// 글저장
	public int insert(BoardVo boardVo) {
		System.out.println("boardDao>insert");

		return sqlSession.insert("board.insert", boardVo);
	}

	// 글 1개 가져오기
	public BoardVo select(int no) {
		System.out.println("boardDao>select");

		return sqlSession.selectOne("board.selcet", no);
	}

	// 조회수 업데이트
	public int updateHit(int no) {
		System.out.println("boardDao>updateHit");

		return sqlSession.update("board.updateHit", no);
	}

	// 글수정
	public int update(BoardVo boardVo) {
		System.out.println("boardDao>update");

		return sqlSession.update("board.update", boardVo);
	}

	// 글삭제
	public int delete(BoardVo boardVo) {
		System.out.println("boardDao/delete");

		return sqlSession.delete("board.delete", boardVo);
	}
	
	

}
