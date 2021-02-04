package com.wwe.member.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.wwe.member.mail.MailSender;
import com.wwe.member.model.service.MemberService;
import com.wwe.member.model.vo.Member;

/**
 * Servlet implementation class MemberController
 */
@WebServlet("/member/*")
public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MemberService memberService = new MemberService();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String uri = request.getRequestURI();
		String[] uriArr = uri.split("/");
		
		switch(uriArr[uriArr.length-1]) {
		
			case "signin" : signIn(request, response);
				break;
			case "signup" : signUp(request, response);
				break;
			case "find" : find(request, response);
				break;
			case "findresult" : findResult(request, response);
				break;
				
			case "signinimpl" : signInImpl(request, response);
				break;
			case "signupimpl" : signUpImpl(request, response);
				break;
			case "findidimpl" : findIDImpl(request, response);
				break;
			case "findpwimpl" : findPWImpl(request, response);
				break;
				
			case "mailsender" : sendMail(request, response);
				break;
				
			case "mypage" : myPage(request, response);
				break;
			case "modifyimpl" : modifyImpl(request, response);
				break;
				
			case "kakao" : kakao(request, response);
				break;
				
			case "logout" : logout(request, response);
				break;
			default : ;	
			
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private void signIn(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/view/member/SignIn.jsp")
		.forward(request, response);
	}
	
	private void signUp(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/view/member/SignUp.jsp")
		.forward(request, response);
	}
	
	private void signInImpl(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String data = request.getParameter("data");
		Gson gson = new Gson();
		Map parsedData = gson.fromJson(data, Map.class);

		String userID = (String) parsedData.get("userID");
		String userPW = (String) parsedData.get("userPW");
		
		Member user = memberService.memberAuthenticate(userID, userPW);
		Member userProject = memberService.getMemberProject(userID);
		
		if(user != null) {
			//session scope로 user 전달
			request.getSession().setAttribute("user", user);
			request.getSession().setAttribute("project", userProject);
			response.getWriter().print("success");
		}else {
			response.getWriter().print("fail");
			
		}
	}
	
	private void signUpImpl(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String data = request.getParameter("data");
		System.out.println(data);
		
		Gson gson = new Gson();
		Map parsedData = gson.fromJson(data, Map.class);
		
		String userID = (String) parsedData.get("userID");
		String userPW = (String) parsedData.get("userPW");
		String userEmail = (String) parsedData.get("userEmail");
		String userName = (String) parsedData.get("userName");
		String userAdd = (String) parsedData.get("userAdd");
		String userTell = (String) parsedData.get("userTell");
		String userBirth = (String) parsedData.get("userBirth");
	
		
		
		Member member = new Member();
		member.setUserID(userID);
		member.setUserPW(userPW);
		member.setUserEmail(userEmail);
		member.setUserName(userName);
		member.setUserAdd(userAdd);
		member.setUserTell(userTell);
		member.setUserBirth(userBirth);
		
		int res = memberService.insertMember(member);
		
		
		
		if(res == 1) {	
			System.out.println("회원가입 성공");
			request
			.getRequestDispatcher("/WEB-INF/view/member/MyPage.jsp")
			.forward(request, response);
		}else {
			System.out.println("회원가입 실패");
			request
			.getRequestDispatcher("/WEB-INF/view/member/Logout.jsp")
			.forward(request, response);
		}
	}
	
	private void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		memberService.kakaoLogout((String)request.getAttribute("access_Token"));
		request.getSession().removeAttribute("project");
		request.getSession().removeAttribute("user");
		request.getSession().removeAttribute("access_Token");
		request.getRequestDispatcher("/WEB-INF/view/index/index.jsp")
		.forward(request, response);
	}
	
	private void find(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.getRequestDispatcher("/WEB-INF/view/member/FindSign.jsp")
		.forward(request, response);
	}
	
	private void findResult(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/view/member/FindResult.jsp")
		.forward(request, response);
	}
	
	private void myPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		
		request.getRequestDispatcher("/WEB-INF/view/member/MyPage.jsp")
		.forward(request, response);
	}
	
	
	
	private void kakao(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		
		String code = request.getParameter("code");
		System.out.println(code);
		String access_Token = memberService.getAccessToken(code);
		Member userInfo = memberService.kakaoUserInfo(access_Token);
		
		
		if(userInfo.getUserEmail() != null) {
			
			userInfo = memberService.memberAuthenticateWithEmail(userInfo.getUserEmail());
			request.getSession().setAttribute("user", userInfo);
			request.getSession().setAttribute("access_Token", access_Token);
			request.getRequestDispatcher("/WEB-INF/view/member/MyPage.jsp")
			.forward(request, response);
			
		}
		
	}
	

	
	private void findIDImpl(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String data = request.getParameter("data");
		Gson gson = new Gson();
		Map parsedData = gson.fromJson(data, Map.class);

		String userEmail = (String) parsedData.get("userEmail");
		
		System.out.println(userEmail);
		
		Member user = memberService.findMemberID(userEmail);
		
		if(user != null) {
			//session scope로 user 전달
			request.getSession().setAttribute("user", user);
			response.getWriter().print("find id success");
		}else {
			response.getWriter().print("fail");
		}
	}
	
	private void findPWImpl(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String data = request.getParameter("data");
		Gson gson = new Gson();
		Map parsedData = gson.fromJson(data, Map.class);

		String userID = (String) parsedData.get("userID");
		String userEmail = (String) parsedData.get("userEmail");
		
		Member user = memberService.findMemberPW(userID, userEmail);
		
		if(user != null) {
			//session scope로 user 전달
			request.getSession().setAttribute("user", user);
			response.getWriter().print("find PW success");
			System.out.println("cont / find PW success");
		}else {
			response.getWriter().print("find PW fail");
			System.out.println("cont / find PW fail");
		}
	}
	
	private void sendMail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		MailSender ms = new MailSender();
		
		String data = request.getParameter("data");
		Gson gson = new Gson();
		Map parsedData = gson.fromJson(data, Map.class);
		
		String userEmail = (String) parsedData.get("userEmail");
		String userAuthCode = (String) parsedData.get("userAuthCode");
		System.out.println(userAuthCode);
		
		ms.GmailSet(userEmail, "wwe 인증메일", "인증번호는?? 뚜둔 \n" + userAuthCode);
		System.out.println("메일 전송송");
	}
	
	private void modifyImpl(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String data = request.getParameter("data");
		System.out.println(data);
		
		Gson gson = new Gson();
		Map parsedData = gson.fromJson(data, Map.class);
		
		String userID = (String) parsedData.get("userID");
		String userPW = (String) parsedData.get("userPW");
		String userEmail = (String) parsedData.get("userEmail");
		String userName = (String) parsedData.get("userName");
		String userAdd = (String) parsedData.get("userAdd");
		String userTell = (String) parsedData.get("userTell");
		String userBirth = (String) parsedData.get("userBirth");
	
		
		
		Member member = new Member();
		member.setUserID(userID);
		member.setUserPW(userPW);
		member.setUserEmail(userEmail);
		member.setUserName(userName);
		member.setUserAdd(userAdd);
		member.setUserTell(userTell);
		member.setUserBirth(userBirth);
		
		int res = memberService.modifyMember(member);
		
		
		
		if(res == 1) {	
			System.out.println("회원가입 성공");
			request
			.getRequestDispatcher("/WEB-INF/view/member/MyPage.jsp")
			.forward(request, response);
		}else {
			System.out.println("회원가입 실패");
			request
			.getRequestDispatcher("/WEB-INF/view/member/Logout.jsp")
			.forward(request, response);
		}
	}
	

	
}
