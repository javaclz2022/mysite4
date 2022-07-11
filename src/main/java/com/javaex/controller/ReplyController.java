package com.javaex.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.javaex.service.ReplyService;
import com.javaex.vo.ReplyVo;
import com.javaex.vo.UserVo;

@Controller
@RequestMapping("/reply")
public class ReplyController {

	@Autowired
	private ReplyService replyService;
	
	// 리스트(리스트만 출력할때)
	@RequestMapping(value="/list", method={RequestMethod.GET, RequestMethod.POST})
	public String list(Model model) {
		System.out.println("replyContoller/list");
		
		List<ReplyVo> replyList = replyService.getReplyList();
		model.addAttribute("replyList", replyList);
		return "reply/list";
		
	}
	
	
	// 게시판 root 글쓰기 폼
	@RequestMapping(value="/writeForm", method={RequestMethod.GET, RequestMethod.POST})
	public String writeForm() {
		System.out.println("replyContoller/writeForm");
		
		return "reply/writeForm";
		
	}
	
	
	// 게시판 root 글쓰기
	@RequestMapping(value="/write", method={RequestMethod.GET, RequestMethod.POST })
	public String write(@ModelAttribute ReplyVo replyVo, HttpSession session) {
		System.out.println("replyContoller/write");

		UserVo authUser = (UserVo) session.getAttribute("authUser");
		replyVo.setUserNo(authUser.getNo());
		replyService.addReply(replyVo, "rootWrite");

		return "redirect:/reply/list";
	}

	
	// 게시판 댓글 글쓰기 폼
	@RequestMapping(value="/reWriteForm", method={RequestMethod.GET, RequestMethod.POST})
	public String reWriteForm(@RequestParam("no") int no, Model model) {
		System.out.println("replyContoller/reWriteForm");
		
		ReplyVo replyVo = replyService.getReply(no, "reWriteForm");
		model.addAttribute("replyVo", replyVo);
		
		return "reply/reWriteForm";
	}
	
	
	// 게시판 댓글쓰기
	@RequestMapping(value="/reWrite", method={RequestMethod.GET, RequestMethod.POST })
	public String reWrite(@ModelAttribute ReplyVo replyVo, HttpSession session) {
		System.out.println("replyContoller/write");

		UserVo authUser = (UserVo) session.getAttribute("authUser");
		replyVo.setUserNo(authUser.getNo());
		replyService.addReply(replyVo, "replyWrite");

		return "redirect:/reply/list";
	}
	
	
	// 게시판 글 읽기
	@RequestMapping(value="/read", method={RequestMethod.GET, RequestMethod.POST})
	public String read(@RequestParam("no") int no, Model model) {
		System.out.println("replyContoller/read");
		
		ReplyVo replyVo = replyService.getReply(no, "read");
		model.addAttribute("replyVo", replyVo);
		return "reply/read";
	}
	
	
	// 글수정 폼
	@RequestMapping(value="/modifyForm", method={RequestMethod.GET, RequestMethod.POST})
	public String modifyForm(@RequestParam("no") int no, Model model) {
		
		ReplyVo replyVo = replyService.getReply(no, "modify");
		model.addAttribute("replyVo", replyVo);
		return "reply/modifyForm";
	}
	
	
	//글수정
	@RequestMapping(value="/modify", method={RequestMethod.GET, RequestMethod.POST})
	public String modify(@ModelAttribute ReplyVo replyVo,  HttpSession session) {
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		int userNo = authUser.getNo();
		replyVo.setUserNo(userNo);
		
		replyService.modifyReply(replyVo);
		return "redirect:/reply/list";
	}
	
	
	//글삭제
	@RequestMapping(value="/delete", method={RequestMethod.GET, RequestMethod.POST})
	public String delete(@ModelAttribute ReplyVo replyVo,  HttpSession session) {
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		int userNo = authUser.getNo();
		replyVo.setUserNo(userNo);
		
		replyService.removeReply(replyVo);
		return "redirect:/reply/list";
	}
	
	
}
