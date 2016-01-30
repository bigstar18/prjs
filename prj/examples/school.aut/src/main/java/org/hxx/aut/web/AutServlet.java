package org.hxx.aut.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import org.hxx.aut.service.AutService;
import org.hxx.util.OutputGenUtil;

public class AutServlet extends HttpServlet {

	private static final long serialVersionUID = 1427714759153163292L;
	public static Logger LOG = LoggerFactory.getLogger(AutServlet.class);

	private AutService service = new AutService();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/html; charset=utf-8");
		resp.setCharacterEncoding("utf-8");
		String schoolCode = (String) req.getParameter("schoolCode");
		String userName = (String) req.getParameter("userName");
		String userCode = (String) req.getParameter("userCode");
		String password = (String) req.getParameter("password");
		LOG.info(schoolCode + "," + userName + "," + userCode + "," + password);

		if (StringUtils.isEmpty(schoolCode) || StringUtils.isEmpty(userName)
				|| StringUtils.isEmpty(userCode)
				|| StringUtils.isEmpty(password)) {
			resp.getWriter().print(OutputGenUtil.getOutput("405"));
			return;
		}
		// 405 非法
		// 406 未通过
		// 407 通过
		// 408 需要人工验证
		// 409 排队人数多，再尝试几次
		// 410 超时提示稍后验证
		String output = OutputGenUtil.getOutput(service.authentication(
				schoolCode, userName, userCode, password));
		resp.getWriter().print(output);
	}
}
