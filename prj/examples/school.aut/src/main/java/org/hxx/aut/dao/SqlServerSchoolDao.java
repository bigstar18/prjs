package org.hxx.aut.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.hxx.aut.model.SchoolHandler;
import org.hxx.util.DBUtil;

public class SqlServerSchoolDao implements ISchoolDao {

	@Override
	public SchoolHandler load(String school) {
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		SchoolHandler sh = null;
		String sql = "select * from TS_AUT_School where School=?";
		try {
			connection = DBUtil.getConnection();
			ps = connection.prepareStatement(sql);
			ps.setString(1, school);
			rs = ps.executeQuery();
			while (rs.next()) {
				sh = new SchoolHandler();
				sh.setSchool(school);
				sh.setHandler(rs.getString("Handler"));
				sh.setLoginUrl(rs.getString("LoginUrl"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(ps);
			DBUtil.close(connection);
		}
		return sh;
	}

}
