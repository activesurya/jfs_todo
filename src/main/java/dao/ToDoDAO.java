package dao;

import java.util.List;

import beans.Register;
import beans.Task;

public interface ToDoDAO {
	public int register(Register reg);
	public int login(String email, String pass);
	public int addTask(int regId, Task task);
	public List<Task> findAllTasksByRegId(int regId);
	public boolean markTaskCompleted(int regId, int taskId);
	public String getFnameLnameByRegId(int regId);
}
