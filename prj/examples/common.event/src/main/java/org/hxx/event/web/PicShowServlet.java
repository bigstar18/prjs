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

public class PicShowServlet extends HttpServlet {

	private static final long serialVersionUID = 4616475080322247282L;
	public static Logger LOG = LoggerFactory.getLogger(PicShowServlet.class);

	private IAppDao dao = new AppDao();

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int sexType = Integer.parseInt(request.getParameter("sexType"));
		String eventId = request.getParameter("eventId");

		try {
			List<AppUser> users = dao.getUser(sexType, eventId);
			if (users.size() == 0) {
				response.getWriter().write("{msg:'还没有人参加'}");
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
				response.getWriter().write(json.toString());
			}
		} catch (Exception e) {
			LOG.error("error", e);
			response.getWriter().write("{msg:'系统错误'}");
		}
	}
}
