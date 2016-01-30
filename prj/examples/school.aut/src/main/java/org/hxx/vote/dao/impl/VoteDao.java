package org.hxx.vote.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import org.hxx.util.DBUtil;
import org.hxx.vote.dao.IVoteDao;
import org.hxx.vote.model.TicketInfo;

public class VoteDao implements IVoteDao {

	private Connection connection = DBUtil.getConnection();

	@Override
	public void vote(TicketInfo ticket) throws Exception {
		PreparedStatement ps = null;
		String sql = "insert into TS_EVENT_VOTE(voterId,voterPhone,picOwnerId,picOwnerPhone,eventId,insertTime) values(?,?,?,?,?,?)";// 设置投票数
		try {
			ps = connection.prepareStatement(sql);
			ps.setString(1, ticket.getVoterId());
			ps.setString(2, ticket.getVoterPhone());
			ps.setString(3, ticket.getPicOwnerId());
			ps.setString(4, ticket.getPicOwnerPhone());
			ps.setString(5, ticket.getEventId());
			ps.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
			ps.executeUpdate();
		} finally {
			DBUtil.close(ps);
		}
	}

	@Override
	public int getTickets(String picOwnerId) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int tickets = 0;
		String sql = "select count(*) from TS_EVENT_VOTE where picOwnerId=?";//
		try {
			ps = connection.prepareStatement(sql);
			ps.setString(1, picOwnerId);
			rs = ps.executeQuery();
			if (rs.next()) {
				tickets = rs.getInt(1);
			}
		} finally {
			DBUtil.close(rs);
			DBUtil.close(ps);
		}
		return tickets;
	}

	/*
	 * 判断数据库中是否已经存在记录 返回值为true,说明记录已经存在 返回值为false，说明返回值不存在
	 */
	@Override
	public boolean check(TicketInfo ticket) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		if (ticket.getVoterPhone().equals(ticket.getPicOwnerPhone())) {
			return true;
		}
		String sql = "select * from TS_EVENT_VOTE where voterPhone=? and picOwnerId=? and eventId=?";
		try {
			ps = connection.prepareStatement(sql);
			ps.setString(1, ticket.getVoterPhone());
			ps.setString(2, ticket.getPicOwnerId());
			ps.setString(3, ticket.getEventId());
			rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			}
		} finally {
			DBUtil.close(rs);
			DBUtil.close(ps);
		}
		return false;
	}
}
