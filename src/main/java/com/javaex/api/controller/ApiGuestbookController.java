package com.javaex.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.javaex.service.GuestBookService;


@Controller
public class ApiGuestbookController {

	@Autowired
	private GuestBookService guestbookService;
	
	
	@RequestMapping(value="/api/guestbook/addList", method = {RequestMethod.GET, RequestMethod.POST})
	public String addList() {
		System.out.println("ApiGuestbookController>addList()");
		
		return "apiGuestbook/addList";
		
	}
	
	
	//방명록 리스트 데이타만 보내줘
	@RequestMapping(value="/api/guestbook/list", method = {RequestMethod.GET, RequestMethod.POST})
	public String list() {
		System.out.println("ApiGuestbookController>List()");
		guestbookService.getGuestList();
		
		
		return "";
	}
	
	
	
}
