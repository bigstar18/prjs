package org.hxx.event.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import org.hxx.event.dao.IUserEventDao;
import org.hxx.event.model.UserEvent;
import org.hxx.util.DBUtil;

public class UserEventDaoImpl implements IUserEventDao {
	Connection connection = DBUtil.getConnection();

	@Override
	public UserEvent queryByUserAndEvent(String phone, String eventId) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		UserEvent userEvent = null;
		String sql = "select * from TS_EVENT_APPLY where phone=? and eventId=?";
		try {
			ps = connection.prepareStatement(sql);
			ps.setString(1, phone);
			ps.setString(2, eventId);
			rs = ps.executeQuery();
			while (rs.next()) {// last?
				userEvent = new UserEvent();
				userEvent.setPhone(phone);
				userEvent.setEventId(eventId);
				userEvent.setId(rs.getString("id"));
				userEvent.setUid(rs.getString("uid"));
				userEvent.setApplyStatus(rs.getInt("applyStatus"));
				userEvent.setCheckStatus(rs.getInt("checkStatus"));
			}
		} finally {
			DBUtil.close(rs);
			DBUtil.close(ps);
		}
		return userEvent;
	}

	@Override
	public void addUserEvent(UserEvent userEvent) throws Exception {
		PreparedStatement ps = null;
		String sql = "insert into TS_EVENT_APPLY(id,uid,phone,eventId,applyStatus,checkStatus,insertTime) values (?,?,?,?,?,?,?)";
		try {
			ps = connection.prepareStatement(sql);
			ps.setString(1, userEvent.getId());
			ps.setString(2, userEvent.getUid());
			ps.setString(3, userEvent.getPhone());
			ps.setString(4, userEvent.getEventId());
			ps.setInt(5, userEvent.getApplyStatus());
			ps.setInt(6, userEvent.getCheckStatus());
			ps.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
			ps.executeUpdate();
		} finally {
			DBUtil.close(ps);
			// DBUtil.close(connection);
		}
	}

}
