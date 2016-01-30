<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<html>
	<head>
		<title>仓单详情</title>
		<meta http-equiv="Pragma" content="no-cache">
	</head>
	<body style="overflow-y: hidden" oncopy="return false;" onpaste="return false;">
		<form id="frm" method="post">
			<div
				style="position: absolute; z-index: 1; overflow: auto; height: 450px; width: 100%;">
				<div class="div_cx">
					<table border="0" width="95%" align="center">
						<tr>
							<td>
								<div class="warning">
									<div class="content">
										温馨提示 :仓单详情
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td><%@ include file="stockdetails_common.jsp"%></td>
						</tr>
						<tr>
							<td><%@ include file="stockprops_common.jsp"%></td>
						</tr>	
						<tr>
							<td>
								<table border="0" width="100%" align="center">
									<tr>
										<td>
											<div class="div_cxtj">
												<div class="div_cxtjL"></div>
												<div class="div_cxtjC">
													关联业务信息
												</div>
												<div class="div_cxtjR"></div>
											</div>
											<div style="clear: both;"></div>
											<div>
												<table border="0" cellspacing="0" cellpadding="4"
													width="100%" align="center" class="table2_style">
													<tr align="left">
														<td align="right" width="20%">
															模块名：
														</td>
														<td width="30%" align="left">
															<c:forEach var="module" items="${moduleSysList}">
																<c:if test="${module.MODULEID==stockOperation.moduleId}">
																	${module.CNNAME }
																</c:if>
															</c:forEach>
														</td>
														<td align="right" width="20%">
															委托号：
														</td>
														<td width="30%" align="left">
															${stockOperation.orderId}
														</td>
													</tr>
												</table>
											</div>
										</td>
									</tr>
								</table>
							</td>
						</tr>						
						<tr>
							<td align="right" position: absolute; z-index: 1;margin-top: 470px;>
								<button class="btn_sec" onClick=window.close();>
									关闭
								</button>
								&nbsp;&nbsp;&nbsp;&nbsp;
							</td>
						</tr>
					</table>
				</div>
			</div>
		</form>

	</body>
</html>
<%@ include file="/mgr/public/jsp/footinc.jsp"%>