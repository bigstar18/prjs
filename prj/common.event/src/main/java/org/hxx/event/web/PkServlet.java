package org.hxx.event.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import org.hxx.app.dao.IAppDao;
import org.hxx.app.dao.impl.AppDao;
import org.hxx.app.model.UserPkPic;
import org.hxx.util.JsonUtil;

public class PkServlet extends HttpServlet {

	private static final long serialVersionUID = -8349153111177685631L;
	public static Logger LOG = LoggerFactory.getLogger(PkServlet.class);

	private IAppDao app = new AppDao();

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// String voterPhone = request.getParameter("myPhone");
		String eventId = request.getParameter("eventId");
		String picOwnerId = request.getParameter("otherUid");

		// String voterId = "";
		// try {// legal check
		// voterId = app.getUIDByUserAccount2(voterPhone);
		// } catch (Exception e) {
		// response.getWriter().write("{msg:'错误啦!'}");
		// return;
		// }
		String voterId = (String) request.getSession().getAttribute("uid");
		if (StringUtils.isEmpty(voterId)) {
			response.getWriter().write("{msg:'非法用户，请登录'}");
			return;
		}
		if (voterId.equals(picOwnerId)) {
			response.getWriter().write("{msg:'无法与自己PK'}");
			return;
		}

		try {
			UserPkPic pk1 = app.getPicByUserId(voterId, eventId);
			UserPkPic pk2 = app.getPicByUserId(picOwnerId, eventId);
			if (pk1.getPicurl().size() != 0 && pk2.getPicurl().size() != 0) {
				String strPk1 = JsonUtil.UserPkPicToJson(pk1);
				String strPk2 = JsonUtil.UserPkPicToJson(pk2);
				String strReturn = "[" + strPk1 + "," + strPk2 + "]";
				response.getWriter().write(strReturn);
			} else {
				response.getWriter().write("{msg:'你还没有马甲线的照片'}");
			}
		} catch (Exception e) {
			LOG.error("error", e);
			response.getWriter().write("{msg:'系统错误'}");
		}

	}

}
