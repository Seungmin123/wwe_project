package com.wwe.task.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import com.wwe.common.jdbc.JDBCTemplate;
import com.wwe.task.feedback.Feedback;
import com.wwe.task.model.dao.TaskDao;
import com.wwe.task.model.vo.Task;

public class TaskService {
	
	TaskDao taskDao = new TaskDao();
	JDBCTemplate jdt = JDBCTemplate.getInstance();
	
	//서비스단의 업무추가
	public int insertTask(Task task) {
			
			Connection conn = jdt.getConnection();
			int res = 0;
			
			try {
				
				res = taskDao.insertTask(conn, task);
				jdt.commit(conn);
				
			} finally {
				jdt.close(conn);
			}
			
			return res;
	}
	
	//서비스단의 프로젝트별 업무리스트조회
	public ArrayList<Task> selectAllTaskList(String projectId){
		
		Connection conn = jdt.getConnection();
		ArrayList<Task> taskList = null;
		
		try {
			
			taskList = taskDao.selectAllTaskList(conn, projectId);
			
		} finally {
			jdt.close(conn);
		}
		
		return taskList;
		
	}
	
	//업무 별 상세내용 조회
	public ArrayList<Task> detailTask(String taskId){
		

		Connection conn = jdt.getConnection();
		ArrayList<Task> detailList = null;
		
		try {
			
			detailList = taskDao.detailTask(conn, taskId);
			
		} finally {
			jdt.close(conn);
		}
		
		return detailList;
	}

	//피드백 추가
	public int insertFeedback(Feedback feedback) {
		
		Connection conn = jdt.getConnection();
		int res = 0;
		
		try {
			
			res = taskDao.insertFeedback(conn, feedback);
			jdt.commit(conn);
			
		} finally {
			jdt.close(conn);
		}
		
		return res;
	}
	
	//내 업무리스트 불러오기
	public ArrayList<Task> selectMyList(String userId){
		
		Connection conn = jdt.getConnection();
		ArrayList<Task> myList = null;
		
		try {
			
			myList = taskDao.selectMyList(conn, userId);
			
		} finally {
			jdt.close(conn);
		}
		
		return myList;
	}

	

}
