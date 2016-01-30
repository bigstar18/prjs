package org.hxx.event.web;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import org.hxx.app.dao.IAppDao;
import org.hxx.app.dao.impl.AppDao;
import org.hxx.app.model.AppUser;
import org.hxx.event.dao.IUserEventDao;
import org.hxx.event.dao.impl.UserEventDaoImpl;
import org.hxx.event.model.UserEvent;

public class EnrollServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public static Logger LOG = LoggerFactory.getLogger(EnrollServlet.class);

	private IUserEventDao dao = new UserEventDaoImpl();
	private IAppDao app = new AppDao();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = (String) req.getParameter("act");
		String signId = (String) req.getParameter("signId");
		String userName = (String) req.getParameter("phone");
		String eventId = (String) req.getParameter("eventId");
		LOG.info(action + "," + eventId + "," + signId + "," + userName);
		// resp.setContentType("text/html;charset=UTF-8");
		if (StringUtils.isEmpty(action) || StringUtils.isEmpty(signId) || StringUtils.isEmpty(userName) || StringUtils.isEmpty(eventId)) {
			resp.getWriter().write("{msg:'系统错误'}");
			return;
		}
		// req.getSession().setAttribute("signId", signId);
		// req.getSession().setAttribute("phone", userName);
		// req.getSession().setAttribute("eventId", eventId);

		try {
			if (!app.isLegal(userName, signId)) {// filter?
				resp.getWriter().write("{msg:'非法用户，请登录'}");
				return;
			}

			if ("checkapply".equals(action)) {
				String uid = app.getUIDByUserAccount2(userName);
				req.getSession(true).setAttribute("uid", uid);
				// search
				UserEvent userEvent = dao.queryByUserAndEvent(userName, eventId);
				if (userEvent == null) {
					resp.getWriter().write("notApplied");
					return;
				}
			} else {
				// String uid = app.getUIDByUserAccount2(userName);
				String uid = (String) req.getSession().getAttribute("uid");
				UserEvent userEvent = new UserEvent();
				userEvent.setUid(uid);
				userEvent.setPhone(userName);
				userEvent.setEventId(eventId);
				userEvent.setApplyStatus(0);
				userEvent.setCheckStatus(0);
				userEvent.setId(UUID.randomUUID().toString());
				dao.addUserEvent(userEvent);
			}

			// JSON.toString(array);
			List<AppUser> users = app.getPic(eventId);
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
		} catch (Exception e) {
			LOG.error("error", e);
			resp.getWriter().write("{msg:'系统错误'}");
		}
	}

}
