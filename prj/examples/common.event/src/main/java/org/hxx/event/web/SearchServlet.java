package org.hxx.event.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.hxx.app.dao.IAppDao;
import org.hxx.app.dao.impl.AppDao;
import org.hxx.app.model.AppUser;

public class SearchServlet extends HttpServlet {
	public static Logger LOG = LoggerFactory.getLogger(SearchServlet.class);

	private static final long serialVersionUID = 7481860686043384387L;

	private IAppDao dao = new AppDao();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String nickname = req.getParameter("nickname");
		String eventId = req.getParameter("eventId");

		try {
			List<AppUser> users = dao.search(nickname, eventId);
			if (users.size() == 0) {
				resp.getWriter().write("{msg:'未上传照片'}");
				return;
			} else {
				JSONArray json = new JSONArray();
				for (AppUser user : users) {
					JSONObject jo = new JSONObject();
					jo.put("uid", user.getUid());
					jo.put("votes", user.getVotes());
					jo.put("picurl", user.getPicurl());
					jo.put("picdate", user.getPicdate());
					jo.put("nick", user.getNick());
					jo.put("headPic", user.getHeadPic());
					json.put(jo);
				}
				resp.getWriter().write(json.toString());
			}
		} catch (Exception e) {
			LOG.error("error", e);
			resp.getWriter().write("{msg:'系统错误'}");
		}
	}
}
