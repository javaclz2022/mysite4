package com.javaex.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.javaex.service.BoardService;
import com.javaex.vo.BoardVo;

@Controller
@RequestMapping("/board")
public class BoardContoller {

	@Autowired
	private BoardService boardService;

	//리스트(일반 + 검색)
	@RequestMapping(value = "/list3", method = { RequestMethod.GET, RequestMethod.POST })
	public String list3(Model model, 
						@RequestParam(value="keyword", required=false, defaultValue="") String keyword) {
		System.out.println("BoardContoller>list3()");

		List<BoardVo> boardList = boardService.getBoardList3(keyword);	
		model.addAttribute("boardList", boardList);
		
		return "board/list3";
	}
	
	
	// 리스트(리스트만 출력할때)
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public String list(Model model) {
		System.out.println("BoardContoller>list()");

		List<BoardVo> boardList = boardService.getBoardList();
		
		model.addAttribute("boardList", boardList);
		return "board/list";
	}

	
	
	@RequestMapping(value = "/search", method = { RequestMethod.GET, RequestMethod.POST })
	public String search(@RequestParam("keyword") String keyword, Model model  ) {
		System.out.println("BoardContoller>search()");
		
		List<BoardVo> boardList = boardService.getBoardList2(keyword);
		model.addAttribute("boardList", boardList);
		
		return "board/list2";
	}
	
	
	
	
}
