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

import beans.Register;
import dao.ToDoDAO;
import dao.ToDoDAOImpl;


@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		HttpSession session=request.getSession();
		ServletContext context=getServletContext();
		
		String fname=request.getParameter("fname").trim();
		String lname=request.getParameter("lname").trim();
		String email=request.getParameter("email").trim();
		String pass=request.getParameter("pass").trim();
		long mobile=Long.parseLong(request.getParameter("mobile").trim());
		String address=request.getParameter("address").trim();
		
		
		Register register=new Register(0,fname,lname,email,pass,mobile,address);
		
		ToDoDAO dao=ToDoDAOImpl.getInstance();
		int regId=dao.register(register);
		
		if(regId>0) {
			context.getRequestDispatcher("/Login.jsp").forward(request, response);
		} else {
			out.println("Registration Failed");
		}
	}
}
