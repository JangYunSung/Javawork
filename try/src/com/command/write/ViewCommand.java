package com.command.write;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lec.beans.WriteDAO;
import com.lec.beans.WriteDTO;

public class ViewCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		
		
		WriteDAO dao = new WriteDAO();  // DAO 객체 생성
		WriteDTO [] arr = null;  
		
		int uid = Integer.parseInt(request.getParameter("uid"));
		if(uid > 0 && uid !=0)  {
		try {
			// 트랜잭션 수행
			
			arr = dao.readByUid(uid);
			
			// "view" 란 name 으로 request 에 arr 값 전달
			// 즉 request 에 담아서 컨트롤러에 전달되는 셈.
			
		} catch(SQLException e) {
			// 만약 CP 사용한다면
			// NamingException 도 처리 해야 함.
			
			e.printStackTrace();
		}
		}
		request.setAttribute("view", arr);
	}
}
