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
import org.hxx.vote.dao.IVoteDao;
import org.hxx.vote.dao.impl.VoteDao;
import org.hxx.vote.model.TicketInfo;

public class VoteServlet extends HttpServlet {

	private IVoteDao dao = new VoteDao();
	private IAppDao app = new AppDao();
	private static final long serialVersionUID = -9038442527560937240L;
	public static Logger LOG = LoggerFactory.getLogger(VoteServlet.class);

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String voterPhone = request.getParameter("voterPhone");
		String picOwnerId = request.getParameter("picOwnerId");
		String picOwnerPhone = "";
		String eventId = request.getParameter("eventId");

		// String voterId = "";
		// try {// check legal
		// voterId = app.getUIDByUserAccount2(voterPhone);
		// } catch (Exception e) {
		// response.getWriter().write("{msg:'投票失败'}");
		// return;
		// }
		String uid = (String) request.getSession().getAttribute("uid");
		if (StringUtils.isEmpty(uid)) {
			response.getWriter().write("{msg:'非法用户，请登录'}");
			return;
		}
		TicketInfo ticket = new TicketInfo(uid, voterPhone, picOwnerId, picOwnerPhone, eventId);
		try {
			if (dao.check(ticket)) {
				response.getWriter().write("{msg:'您已经给她/他投过票啦!'}");
				return;
			}

			dao.vote(ticket);
			int tickets = dao.getTickets(picOwnerId);
			response.getWriter().write("{tickets:'" + tickets + "票',msg:'投票成功'}");
		} catch (Exception e) {
			LOG.error("error", e);
			response.getWriter().write("{msg:'系统错误'}");
		}
	}

}
