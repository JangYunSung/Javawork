package com.lec.sts11_param;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	// parameter 추출
	// handler 메소드에서도 서블릿에서 보앗던 HttpServletRequest, HttpServletResponse 매개변수 가능.!!!
	
	@RequestMapping(value = "/member/delete", method = RequestMethod.GET)   // -> /member/delete?id=34
	//public String delMember(Model model, HttpServletRequest request) {
	public String delMember(HttpServletRequest request, Model model) {  // 순서 바꾸어도 동작!
		String id = request.getParameter("id");
		model.addAttribute("mbId", id);
		return "member/delete";
	}
	
	@RequestMapping(value = "/member/regOk", method = RequestMethod.POST)
	public String regOkMember() {
		System.out.println("/member/regOk : POST");
		return "member/regOk";
	}

	@RequestMapping(value = "/member/regOk", method = RequestMethod.GET)
	public String regOkMember(Model model) {
		System.out.println("/member/regOk : GET");
		return "member/regOk";
	}
	
	@RequestMapping(value = "/member/regist")
	public String registMember() {
		return "member/regist";
	}
	
	// GET/POST 둘다 받는 handler
	@RequestMapping(value = "/member/regOk2", 
			method = {RequestMethod.GET, RequestMethod.POST})
	public String regOkMember2() {
		return "member/regOk";
	}
	
	// handler 에
	// request parameter 의 name 값과 '같은 이름의 매개변수' 가 있으면
	// 바로 그 매개변수가 request parameter 값을 받아온다.
	
	@RequestMapping("/member/find")
	public String findMember(String id, String name, Model model) {
		model.addAttribute("id", id);
		model.addAttribute("name", name);
		
		return "member/find";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
