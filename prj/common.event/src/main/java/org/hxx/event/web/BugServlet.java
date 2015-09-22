package org.hxx.event.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.hxx.bug.dao.impl.AppBugDao;
import org.hxx.bug.model.AppBug;
import org.hxx.event.util.CheckTheSame;
import org.hxx.event.util.IKAnalyzerUtil;
import org.hxx.util.UUIDGenerator;

/**
 * Servlet implementation class BugServlet
 */
public class BugServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static Logger LOG = LoggerFactory.getLogger(BugServlet.class);
	private AppBugDao dao = new AppBugDao();

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");

		String id = UUIDGenerator.getUUID();
		String username = request.getParameter("contacts");
		String phone = request.getParameter("phone");
		String bugTitle = request.getParameter("title");
		String bugInfo = request.getParameter("message");
		LOG.info(username + "," + phone + "," + bugTitle);
		// 用户名，标题，内容不能为空
		if (username == null || "".equals(username.trim()) || bugTitle == null
				|| "".equals(bugTitle.trim()) || bugInfo == null
				|| "".equals(bugInfo.trim())) {
			response.getWriter().write("用户名，标题，内容不能为空");
			return;
		}
		// 文本相似度结果
		double result = 0;
		try {
			List<AppBug> bugs = dao.load(phone);
			if (bugs.size() != 0) {
				for (AppBug bug : bugs) {
					// 判断标题是否相同
					if (bugTitle.equals(bug.getBugTitle())) {
						response.getWriter().write("相似报告已存在");
						return;
					}
				}
				for (AppBug bug : bugs) {
					result = CheckTheSame.getSimilarity(
							IKAnalyzerUtil.participle(bugInfo),
							IKAnalyzerUtil.participle(bug.getBugInfo()));
					// 判断内容是否相似
					if (result > 0.9) {
						response.getWriter().write("相似报告已存在");
						return;
					}
				}
			}
			
			AppBug bug = new AppBug();
			bug.setId(id);
			bug.setUsername(username);
			bug.setPhone(phone);
			bug.setBugTitle(bugTitle);
			bug.setBugInfo(bugInfo);
			dao.add(bug);

			response.getWriter().write("你的建议意见被收录，等待惊喜吧！");
			return;
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().write("发生错误啦！等待程序猿来解决！");
		}
	}

}
