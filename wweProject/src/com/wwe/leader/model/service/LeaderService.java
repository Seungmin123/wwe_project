package com.wwe.leader.model.service;

import java.sql.Connection;
import java.sql.SQLException;

import com.wwe.common.exception.DataAccessException;
import com.wwe.common.exception.ToAlertException;
import com.wwe.common.jdbc.JDBCTemplate;
import com.wwe.leader.model.dao.LeaderDao;
import com.wwe.leader.model.vo.Leader;

public class LeaderService {

	JDBCTemplate jdt = JDBCTemplate.getInstance();
	LeaderDao leaderDao = new LeaderDao();
	
	// 유저에게 권한을 추가하는 메소드
	public int inviteUser(String userId, String authorty) {
		
		int res = 0;
		Connection conn  = jdt.getConnection();
		
		try {
			res = leaderDao.inviteUser(conn, userId,authorty);
			jdt.commit(conn);
		}catch (DataAccessException  e) {
			//커스텀 예외처리를  해주어야 할 부분
			jdt.rollback(conn);
			throw new ToAlertException(e.error);
		}finally {
			jdt.close(conn);
		}
		
		return res;
	}
	
	//팀원 초대 시 입력한 아이디가 유효한 아이디인지 체크하는 메소드
	public String chkInvalidUser(String userId) {
		Connection conn = jdt.getConnection();
		String mUserId= " ";
		mUserId = leaderDao.chkInvalidUser(conn, userId);
		jdt.close(conn);
		return mUserId;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
