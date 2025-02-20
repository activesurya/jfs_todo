package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import beans.Register;
import beans.Task;
import factory.DBConn;

public class ToDoDAOImpl implements ToDoDAO {

	Connection con;
	Statement stmt;
	ResultSet rs;
	PreparedStatement pstmt1,pstmt2,pstmt3,pstmt4,pstmt5;
	
	// make this class Singleton
	static ToDoDAO dao;
	
	private ToDoDAOImpl() {
		try {
			con=DBConn.getConn();
			System.out.println(con);
			stmt=con.createStatement();
			pstmt1=con.prepareStatement("insert into register values (?,?,?,?,?,?,?)");
			pstmt2=con.prepareStatement("insert into tasks values (?,?,?,?,?)");
			pstmt3=con.prepareStatement("insert into taskid_pks values (?,?)");
			pstmt4=con.prepareStatement("update taskid_pks set taskid=? where regid=?");
			pstmt5=con.prepareStatement("update tasks set taskstatus=3 where regid=? and taskid=?");
			System.out.println("Statements are created");
		} catch(Exception e) {
			e.printStackTrace();
		}		
	}
	
	// factory method that is creating and returning Singleton object
	public static ToDoDAO getInstance() {
		if(dao==null)
			dao=new ToDoDAOImpl();
		System.out.println(dao);
		return dao;
	}
	
	@Override
	public int register(Register reg) {
		int regId=0;
		try {
			// pk generation
			rs=stmt.executeQuery("select max(regid) from register");
			if(rs.next()) {
				regId=rs.getInt(1);
			}
			regId++;
			
			// insert into register table
			pstmt1.setInt(1, regId);;
			pstmt1.setString(2, reg.getFname());
			pstmt1.setString(3, reg.getLname());
			pstmt1.setString(4, reg.getEmail());
			pstmt1.setString(5, reg.getPass());
			pstmt1.setLong(6, reg.getMobile());
			pstmt1.setString(7, reg.getAddress());
			int i=pstmt1.executeUpdate();
			if(i==1)
				System.out.println("TX Success, New Registration added");
		} catch(Exception e) {
			e.printStackTrace();
		}
		return regId;
	}

	@Override
	public int login(String email, String pass) {
		int regId=0;
		try {
			rs=stmt.executeQuery("select regId from register where email='"+email+"' and pass='"+pass+"'");
			if(rs.next()) {
				regId=rs.getInt(1);
				System.out.println("Login Success");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return regId;
	}

	@Override
	public int addTask(int regId, Task task) {
		int taskId=0;
		boolean isNew=true;
		int i,j=0;
		try {
			// retrieve taskId from taskid_pks table using regId
			// if taskId comes increment the value
			// otherwise taskId is 1
			// insert record into tasks table
			// if task for the regId is new, insert record into taskId_pks table with regId and taskId as 1
			// if task of the user is not a new, update taskid with regId in taskid_pks table
			rs=stmt.executeQuery("select taskid from taskid_pks where regid="+regId);
			if(rs.next()) {
				taskId=rs.getInt(1);
				isNew=false;
			}
			taskId++;
			
			con.setAutoCommit(false);
			pstmt2.setInt(1,taskId);
			pstmt2.setString(2, task.getTaskName());
			pstmt2.setString(3, task.getTaskDate());
			pstmt2.setInt(4, task.getTaskStatus());
			pstmt2.setInt(5, regId);
			i=pstmt2.executeUpdate();
			
			if(isNew) {
				pstmt3.setInt(1, regId);
				pstmt3.setInt(2, taskId);
				j=pstmt3.executeUpdate();
			} else {
				pstmt4.setInt(1, taskId);
				pstmt4.setInt(2, regId);
				j=pstmt4.executeUpdate();
			}
			
			if(i==1 && j==1) {
				con.commit();
				System.out.println("TX Success, Task Added");
			} else {
				con.commit();
				System.out.println("TX Failed");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return taskId;
	}

	@Override
	public List<Task> findAllTasksByRegId(int regId) {
		List<Task> tasks=new ArrayList<Task>();
		try {
			rs=stmt.executeQuery("select * from tasks where regid="+regId);
			while(rs.next()) {
				Task task=new Task(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4),rs.getInt(5));
				tasks.add(task);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return tasks;
	}

	@Override
	public boolean markTaskCompleted(int regId, int taskId) {
		boolean flag=false;
		try {
			pstmt5.setInt(1, regId);
			pstmt5.setInt(2, taskId);
			int i=pstmt5.executeUpdate();
			if(i==1)
				flag=true;
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	public String getFnameLnameByRegId(int regId) {
		String flname="";
		try {
			rs=stmt.executeQuery("select fname,lname from register where regid="+regId);
			if(rs.next()) {
				flname=rs.getString(1)+" "+rs.getString(2);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return flname;
	}
}
