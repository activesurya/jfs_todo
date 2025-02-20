package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.ToDoDAO;
import dao.ToDoDAOImpl;


@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		HttpSession session=request.getSession();
		ServletContext context=getServletContext();
		
		String email=request.getParameter("email").trim();
		String pass=request.getParameter("pass").trim();
		
		boolean isValid=true;
		
		if(email.length()==0 || pass.isEmpty()) {
			request.setAttribute("loginError", "Email/Pass is empty");
			isValid=false;
		} else {
			ToDoDAO dao=ToDoDAOImpl.getInstance();
			int regId=dao.login(email,pass);
			
			if(regId>0) {
				session.setAttribute("regId", regId);
				context.getRequestDispatcher("/ViewTasks.jsp").forward(request, response);
				isValid=true; // no need
			} else {
				request.setAttribute("loginError", "Email/Pass is wrong");
				isValid=false;
			}
		}
		
		if(isValid==false) {
			context.getRequestDispatcher("/Login.jsp").forward(request, response);
		}
	}
}
