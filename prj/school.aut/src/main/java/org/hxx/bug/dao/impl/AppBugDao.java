package org.hxx.bug.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.hxx.bug.dao.IAppBugDao;
import org.hxx.bug.model.AppBug;
import org.hxx.util.DBUtil;

public class AppBugDao implements IAppBugDao {

	@Override
	public void add(AppBug appBug) throws Exception {
		Connection connection = null;
		PreparedStatement ps = null;
		String sql = "insert into TS_EVENT_BUG(Id,Username,Phone,BugTitle,BugInfo,Time) values (?,?,?,?,?,?)";
		try {
			connection = DBUtil.getConnection();
			ps = connection.prepareStatement(sql);
			ps.setString(1, appBug.getId());
			ps.setString(2, appBug.getUsername());
			ps.setString(3, appBug.getPhone());
			ps.setString(4, appBug.getBugTitle());
			ps.setString(5, appBug.getBugInfo());
			ps.setTimestamp(6,
					new java.sql.Timestamp(System.currentTimeMillis()));
			ps.executeUpdate();
		} finally {
			DBUtil.close(ps);
			DBUtil.close(connection);
		}
	}

	@Override
	public List<AppBug> load(String phone) {
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<AppBug> bugs = new ArrayList<AppBug>();
		String sql = "select * from TS_EVENT_BUG where Phone=?";
		try {
			connection = DBUtil.getConnection();
			ps = connection.prepareStatement(sql);
			ps.setString(1, phone);
			rs = ps.executeQuery();
			while (rs.next()) {
				AppBug bug = new AppBug();
				bug.setId(rs.getString("Id"));
				bug.setUsername(rs.getString("Username"));
				bug.setPhone(rs.getString("Phone"));
				bug.setBugTitle(rs.getString("BugTitle"));
				bug.setBugInfo(rs.getString("BugInfo"));
				bug.setTime(rs.getTimestamp("Time"));
				bugs.add(bug);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(ps);
			DBUtil.close(connection);
		}
		return bugs;
	}

}
