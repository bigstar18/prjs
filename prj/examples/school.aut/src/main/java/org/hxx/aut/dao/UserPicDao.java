package org.hxx.aut.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.hxx.aut.model.UserPic;
import org.hxx.util.DBUtil;

public class UserPicDao implements IUserPicDao {

	@Override
	public void add(UserPic userPic) {
		Connection connection = null;
		PreparedStatement ps = null;
		String sql = "insert into TS_EVENT_UPLOAD(PhoneNum,OriginPic,ZipPic) values (?,?,?)";
		try {
			connection = DBUtil.getConnection();
			ps = connection.prepareStatement(sql);
			ps.setString(1, userPic.getPhoneNum());
			ps.setString(2, userPic.getOriginPicPath());
			ps.setString(3, userPic.getCompressPicPath());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(ps);
			DBUtil.close(connection);
		}
	}

	@Override
	public int check(String phoneNum) {
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int count = 0;
		String sql = "select count(*) from TS_EVENT_Upload where PhoneNum=?";
		try {
			connection = DBUtil.getConnection();
			ps = connection.prepareStatement(sql);
			ps.setString(1, phoneNum);
			rs = ps.executeQuery();
			while(rs.next()){
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(ps);
			DBUtil.close(connection);
		}
		return count;
	}

	@Override
	public void setEventStatus(String phoneNum,String eventId) {
		Connection connection = null;
		PreparedStatement ps = null;
		String sql = "update TS_EVENT_APPLY set applyStatus='1' where phone=?" ;
		try {
			connection = DBUtil.getConnection();
			ps = connection.prepareStatement(sql);
			ps.setString(1, phoneNum);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(ps);
			DBUtil.close(connection);
		}
	}
}
