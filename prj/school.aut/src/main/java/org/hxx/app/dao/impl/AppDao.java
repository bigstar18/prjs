package org.hxx.app.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hxx.app.dao.IAppDao;
import org.hxx.app.model.AppUser;
import org.hxx.app.model.UserPkPic;
import org.hxx.util.DBUtil;

public class AppDao implements IAppDao {

	private Connection connection = DBUtil.getConnection();

	// private IVoteDao voteDao = new VoteDao();

	@Override
	public boolean isLegal(String phone, String signId) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select * from TS_SYS_User where UserAccount=? and SignId=?";
		try {
			ps = connection.prepareStatement(sql);
			ps.setString(1, phone);
			ps.setString(2, signId);
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

	@Override
	public String getUIDByUserAccount2(String userAccount) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String UID = "";
		String sql = "select * from TS_SYS_User where UserAccount=?";
		try {
			ps = connection.prepareStatement(sql);
			ps.setString(1, userAccount);
			rs = ps.executeQuery();
			if (rs.next()) {
				UID = rs.getString("UID");
			}
		} finally {
			DBUtil.close(rs);
			DBUtil.close(ps);
		}
		return UID;
	}

	@Override
	public List<AppUser> getPic(String eventId) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<AppUser> users = new ArrayList<AppUser>();
		List<AppUser> result = new ArrayList<AppUser>();
		String sql1 = "select s3.UserUID,s3.NickName,s3.HeadPic,s3.SexType,s3.Pictures,s3.CreateDate ,count(d.picOwnerId) as voteNum from (select c.UserUID,c.NickName,c.HeadPic,c.SexType,s2.Pictures,s2.CreateDate from TS_APP_User c inner join (select b.AddUserUID,b.Pictures,b.CreateDate from TS_APP_FreshNews b inner join (select a.AddUserUID,MAX(CreateDate) as cdate from TS_APP_FreshNews a where a.Pictures is not null and a.NewsContent like ? group by a.AddUserUID) s1 on b.AddUserUID=s1.AddUserUID and b.CreateDate=s1.cdate) s2 on c.UserUID=s2.AddUserUID) s3 left join TS_EVENT_VOTE d on s3.UserUID=d.picOwnerId group by s3.UserUID,s3.NickName,s3.HeadPic,s3.SexType,s3.Pictures,s3.CreateDate order by s3.CreateDate desc";
		try {
			ps = connection.prepareStatement(sql1);
			ps.setString(1, "%#mjx#%");
			rs = ps.executeQuery();
			int i = 0;
			while (rs.next() && (i++) < 100) {// 取前100个
				AppUser user = new AppUser();
				user.setUid(rs.getString("UserUID"));
				user.setNick(rs.getString("NickName"));
				user.setHeadPic(rs.getString("HeadPic"));
				String pictures = rs.getString("Pictures");
				String[] pics = pictures.split(",");
				user.setPicurl(pics[0]);
				user.setPicdate(rs.getDate("CreateDate"));
				user.setVotes(rs.getInt("voteNum"));
				user.setSex(rs.getString("SexType"));
				users.add(user);
			}
			int total = users.size();
			if (total > 0) {
				Collections.shuffle(users);
				// result.addAll(users.subList(0, Math.min(10, total)));
				result.addAll(users);
			}
		} finally {
			DBUtil.close(rs);
			DBUtil.close(ps);
		}
		return result;
	}

	@Override
	public List<AppUser> getUser(int sexType, String eventId) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<AppUser> users = new ArrayList<AppUser>();
		List<AppUser> result = new ArrayList<AppUser>();
		String sql1 = "select s3.UserUID,s3.NickName,s3.HeadPic,s3.Pictures,s3.CreateDate ,count(d.picOwnerId) as voteNum from (select c.UserUID,c.NickName,c.HeadPic,s2.Pictures,s2.CreateDate from TS_APP_User c inner join (select b.AddUserUID,b.Pictures,b.CreateDate from TS_APP_FreshNews b inner join (select a.AddUserUID,MAX(CreateDate) as cdate from TS_APP_FreshNews a where a.Pictures is not null and a.NewsContent like ? group by a.AddUserUID) s1 on b.AddUserUID=s1.AddUserUID and b.CreateDate=s1.cdate) s2 on c.UserUID=s2.AddUserUID where c.SexType=? ) s3 left join TS_EVENT_VOTE d on s3.UserUID=d.picOwnerId group by s3.UserUID,s3.NickName,s3.HeadPic,s3.Pictures,s3.CreateDate order by s3.CreateDate desc";
		try {
			ps = connection.prepareStatement(sql1);
			ps.setString(1, "%#mjx#%");
			ps.setInt(2, sexType);
			rs = ps.executeQuery();
			int i = 0;
			while (rs.next() && (i++) < 100) {// 取前100个
				AppUser user = new AppUser();
				user.setUid(rs.getString("UserUID"));
				user.setNick(rs.getString("NickName"));
				user.setHeadPic(rs.getString("HeadPic"));
				String pictures = rs.getString("Pictures");
				String[] pics = pictures.split(",");
				user.setPicurl(pics[0]);
				user.setPicdate(rs.getDate("CreateDate"));
				user.setVotes(rs.getInt("voteNum"));
				users.add(user);
			}
			int total = users.size();
			if (total > 0) {
				Collections.shuffle(users);
				// result.addAll(users.subList(0, Math.min(10, total)));
				result.addAll(users);
			}
		} finally {
			DBUtil.close(rs);
			DBUtil.close(ps);
		}
		return result;
	}

	@Override
	public List<AppUser> search(String nickname, String eventId) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<AppUser> users = new ArrayList<AppUser>();
		List<AppUser> result = new ArrayList<AppUser>();
		String sql1 = "select s3.UserUID,s3.NickName,s3.HeadPic,s3.Pictures,s3.CreateDate ,count(d.picOwnerId) as voteNum from (select s1.UserUID,s1.NickName,s1.HeadPic,c.Pictures,c.CreateDate from (select b.UserUID,b.NickName,b.HeadPic from TS_APP_User b where b.NickName like ?) s1 inner join TS_APP_FreshNews c on s1.UserUID=c.AddUserUID where c.Pictures is not null and c.NewsContent like ?) s3 left join TS_EVENT_VOTE d on s3.UserUID=d.picOwnerId group by s3.UserUID,s3.NickName,s3.HeadPic,s3.Pictures,s3.CreateDate order by s3.CreateDate desc";
		try {
			ps = connection.prepareStatement(sql1);
			ps.setString(1, "%" + nickname + "%");
			ps.setString(2, "%#mjx#%");
			rs = ps.executeQuery();
			int i = 0;
			while (rs.next() && (i++) < 100) {// 取前100个
				AppUser user = new AppUser();
				user.setUid(rs.getString("UserUID"));
				user.setNick(rs.getString("NickName"));
				user.setHeadPic(rs.getString("HeadPic"));
				String pictures = rs.getString("Pictures");
				String[] pics = pictures.split(",");
				user.setPicurl(pics[0]);
				user.setPicdate(rs.getDate("CreateDate"));
				user.setVotes(rs.getInt("voteNum"));
				users.add(user);
			}
			int total = users.size();
			if (total > 0) {
				Collections.shuffle(users);
				// result.addAll(users.subList(0, Math.min(10, total)));
				result.addAll(users);
			}
		} finally {
			DBUtil.close(rs);
			DBUtil.close(ps);
		}
		return result;
	}

	@Override
	public UserPkPic getPicByUserId(String uid, String eventId) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		UserPkPic pics = new UserPkPic();
		List<String> picUrls = new ArrayList<String>();
		List<Date> createDate = new ArrayList<Date>();
		String sql1 = "select s1.UserUID,s1.NickName,s1.HeadPic,c.Pictures,c.CreateDate from (select b.UserUID,b.NickName,b.HeadPic from TS_APP_User b where b.UserUID = ?) s1 inner join TS_APP_FreshNews c on s1.UserUID=c.AddUserUID where c.Pictures is not null and c.NewsContent like ? order by c.CreateDate desc";
		try {
			ps = connection.prepareStatement(sql1);
			ps.setString(1, uid);
			ps.setString(2, "%#mjx#%");
			rs = ps.executeQuery();
			while (rs.next()) {
				pics.setUid(uid);
				pics.setNick(rs.getString("NickName"));
				pics.setHeadPic(rs.getString("HeadPic"));

				if (picUrls.size() > 5)
					break;

				String pictures = rs.getString("Pictures");
				String[] pic = pictures.split(",");
				for (int i = 0; i < pic.length; i++) {
					picUrls.add(pic[i]);
					createDate.add(rs.getDate("CreateDate"));
				}
			}
			pics.setPicurl(picUrls);
			pics.setPicdate(createDate);
		} finally {
			DBUtil.close(rs);
			DBUtil.close(ps);
		}
		return pics;
	}

	@Deprecated
	public String getUIDByUserAccount(String voterPhone) throws Exception {
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String UID = "";
		String sql = "select * from TS_EVENT_APPLY where phone=?";
		try {
			connection = DBUtil.getConnection();
			ps = connection.prepareStatement(sql);
			ps.setString(1, voterPhone);
			rs = ps.executeQuery();
			if (rs.next()) {
				UID = rs.getString("uid");
			}
		} finally {
			DBUtil.close(rs);
			DBUtil.close(ps);
			DBUtil.close(connection);
		}
		return UID;
	}

	@Deprecated
	private List<AppUser> getPic2(String eventId) throws Exception {
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<AppUser> users = new ArrayList<AppUser>();
		List<AppUser> result = new ArrayList<AppUser>();
		String sql1 = "select * from TS_EVENT_APPLY as a inner join TS_APP_User as b " + "on a.uid=b.UserUID where a.eventId=?";
		try {
			connection = DBUtil.getConnection();
			ps = connection.prepareStatement(sql1);
			ps.setString(1, eventId);
			rs = ps.executeQuery();
			while (rs.next()) {
				AppUser user = new AppUser();
				user.setUid(rs.getString("UserUID"));
				user.setNick(rs.getString("NickName"));
				user.setHeadPic(rs.getString("HeadPic"));
				users.add(user);
			}
			int total = users.size();
			if (total > 0) {//
				StringBuilder sb = new StringBuilder("select a.Pictures,a.AddUserUID,a.CreateDate,count(*) as voteNum from TS_APP_FreshNews a ,TS_EVENT_VOTE b where a.AddUserUID=b.picOwnerId and a.Pictures is not null and a.AddUserUID in ( ");
				Map<String, AppUser> map = new HashMap<String, AppUser>();
				for (int i = 0; i < total; i++) {
					AppUser user = users.get(i);
					sb.append("'").append(user.getUid()).append("',");
					map.put(user.getUid(), user);
				}
				sb.deleteCharAt(sb.length() - 1).append(" ) and a.NewsContent like ? group by a.Pictures,a.AddUserUID,a.CreateDate order by a.CreateDate desc");
				ps = connection.prepareStatement(sb.toString());
				ps.setString(1, "%#mjx#%");
				rs = ps.executeQuery();
				List<AppUser> tmp = new ArrayList<AppUser>();
				while (rs.next()) {
					AppUser user = (AppUser) map.get(rs.getString("AddUserUID"));
					String pictures = rs.getString("Pictures");
					String[] pics = pictures.split(",");
					user.setPicurl(pics[0]);
					user.setVotes(rs.getInt("voteNum"));
					user.setPicdate(rs.getDate("CreateDate"));
					tmp.add(user);
				}
				if (tmp.size() > 0) {
					Collections.shuffle(tmp);
					result.addAll(tmp.subList(0, Math.min(10, tmp.size())));
				}
			}
		} finally {
			DBUtil.close(rs);
			DBUtil.close(ps);
			DBUtil.close(connection);
		}
		return result;
	}

	@Deprecated
	private List<AppUser> getUser2(int sexType, String eventId) {
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<AppUser> result = new ArrayList<AppUser>();
		List<AppUser> users = new ArrayList<AppUser>();
		String sql1 = "select * from TS_EVENT_APPLY as a inner join TS_APP_User as b " + "on a.uid=b.UserUID where b.SexType=? and a.eventId=?";
		try {
			connection = DBUtil.getConnection();
			ps = connection.prepareStatement(sql1);
			ps.setInt(1, sexType);
			ps.setString(2, eventId);
			rs = ps.executeQuery();
			while (rs.next()) {
				AppUser user = new AppUser();
				user.setUid(rs.getString("UserUID"));
				user.setNick(rs.getString("NickName"));
				user.setHeadPic(rs.getString("HeadPic"));
				users.add(user);
			}
			int total = users.size();
			if (total > 0) {//
				StringBuilder sb = new StringBuilder("select a.Pictures,a.AddUserUID,a.CreateDate,count(*) as voteNum from TS_APP_FreshNews a ,TS_EVENT_VOTE b where a.AddUserUID=b.picOwnerId and a.Pictures is not null and a.AddUserUID in ( ");
				Map<String, AppUser> map = new HashMap<String, AppUser>();
				for (int i = 0; i < total; i++) {
					AppUser user = users.get(i);
					sb.append("'").append(user.getUid()).append("',");
					map.put(user.getUid(), user);
				}
				sb.deleteCharAt(sb.length() - 1).append(" ) and a.NewsContent like ? group by a.Pictures,a.AddUserUID,a.CreateDate order by a.CreateDate desc");
				ps = connection.prepareStatement(sb.toString());
				ps.setString(1, "%#mjx#%");
				rs = ps.executeQuery();
				List<AppUser> tmp = new ArrayList<AppUser>();
				while (rs.next()) {
					AppUser user = (AppUser) map.get(rs.getString("AddUserUID"));
					String pictures = rs.getString("Pictures");
					String[] pics = pictures.split(",");
					user.setPicurl(pics[0]);
					// user.setVotes(voteDao.getTickets(user.getUid()));
					user.setPicdate(rs.getDate("CreateDate"));
					tmp.add(user);
				}
				if (tmp.size() > 0) {
					Collections.shuffle(tmp);
					result.addAll(tmp.subList(0, Math.min(10, tmp.size())));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(ps);
			DBUtil.close(connection);
		}
		return result;
	}

	@Deprecated
	private List<AppUser> search2(String nickname, String eventId) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<AppUser> users = new ArrayList<AppUser>();
		List<AppUser> results = new ArrayList<AppUser>();
		String sql1 = "select * from TS_EVENT_APPLY as a inner join TS_APP_User as b " + "on a.uid=b.UserUID where b.NickName like ? and a.eventId=?";
		String sql2 = "select * from TS_APP_FreshNews where AddUserUID=? and NewsContent like ?";
		try {
			ps = connection.prepareStatement(sql1);
			ps.setString(1, "%" + nickname + "%");
			ps.setString(2, eventId);
			rs = ps.executeQuery();
			while (rs.next()) {
				AppUser user = new AppUser();
				user.setUid(rs.getString("UserUID"));
				user.setNick(rs.getString("NickName"));
				user.setHeadPic(rs.getString("HeadPic"));
				users.add(user);
			}
			for (AppUser user : users) {
				ps = connection.prepareStatement(sql2);
				ps.setString(1, user.getUid());
				ps.setString(2, "%#mjx#%");
				rs = ps.executeQuery();
				if (rs.next()) {
					String pictures = rs.getString("Pictures");
					String[] pics = pictures.split(",");
					user.setPicurl(pics[0]);
					// user.setVotes(voteDao.getTickets(user.getUid()));
					user.setPicdate(rs.getDate("CreateDate"));
					results.add(user);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(ps);
		}
		return results;
	}

	@Deprecated
	public UserPkPic getPicByUserId2(String uid, String eventId) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		UserPkPic pics = new UserPkPic();
		List<String> picUrls = new ArrayList<String>();
		List<Date> createDate = new ArrayList<Date>();
		String sql1 = "select * from TS_EVENT_APPLY as a inner join TS_APP_User as b " + "on a.uid=b.UserUID where b.UserUID=? and a.eventId=?";
		String sql2 = "select * from TS_APP_FreshNews where Pictures is not null and AddUserUID=? and NewsContent like ? order by CreateDate desc";
		try {
			ps = connection.prepareStatement(sql1);
			ps.setString(1, uid);
			ps.setString(2, eventId);
			rs = ps.executeQuery();
			while (rs.next()) {
				pics.setUid(uid);
				pics.setNick(rs.getString("NickName"));
				pics.setHeadPic(rs.getString("HeadPic"));
			}
			ps = connection.prepareStatement(sql2);
			ps.setString(1, uid);
			ps.setString(2, "%#mjx#%");
			rs = ps.executeQuery();
			while (rs.next()) {
				if (picUrls.size() <= 5) {
					String pictures = rs.getString("Pictures");
					String[] pic = pictures.split(",");
					picUrls.add(pic[0]);
					createDate.add(rs.getDate("CreateDate"));
				}
			}
			pics.setPicurl(picUrls);
			pics.setPicdate(createDate);
		} finally {
			DBUtil.close(rs);
			DBUtil.close(ps);
		}
		return pics;
	}

}
