<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>消息详情</title>
		<meta http-equiv="Pragma" content="no-cache">
	</head>

	<body style="overflow-y: hidden">
		<div class="div_cx">
			<table border="0" width="80%" align="center">
				<tr height="25">
					<td>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td>
						<table border="0" width="100%" align="center">
							<tr height="30"></tr>
							<tr>
								<td>
									<div class="div_cxtj">
										<div class="div_cxtjL"></div>
										<div class="div_cxtjC">
											基本信息
										</div>
										<div class="div_cxtjR"></div>
									</div>
									<div style="clear: both;"></div>
									<div class="div_tj">
										<table border="0" cellspacing="0" cellpadding="4" width="100%"
											align="center" class="table2_style">
											<tr height="20">
												<td align="right" width="25%">
													管理员：
												</td>
												<td width="*">
													${entity.userId }
												</td>
											</tr>
												<tr height="20">
													<td align="right" width="25%">
														创建时间：
													</td>
													<td width="*">
														<fmt:formatDate value="${entity.createTime}"
															pattern="yyyy-MM-dd HH:mm:ss" />
													</td>
												</tr>
											</tr>
											<tr height="20">
													<td align="right" width="25%">
														接收人：
													</td>
													<td width="*">
														<c:if test="${entity.recieverType!=4&&entity.recieverType!=5}">
															<c:set var="key">
																<c:out value="${entity.recieverType}"></c:out>
															</c:set>
															${recieverMap[key]}
														</c:if>
														<c:if test="${entity.recieverType==4||entity.recieverType==5}">
															${entity.traderId}
														</c:if>
													</td>
												</tr>
												<tr height="20">
													<td align="right" width="25%">
														接收人类型：
													</td>
													<td width="*">
														<c:set var="key">
															<c:out value="${entity.recieverType}"></c:out>
														</c:set>
														${recieverMap[key]}
													</td>
												</tr>
												<tr>
													<td align="right">
														消息内容：
													</td>
													<td>
														<textarea rows="5" cols="30" readonly="readonly" style="text-align: left;overflow: auto">${entity.message }</textarea>
													</td>
												</tr>
										</table>
									</div>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
		<div>
			<table border="0" cellspacing="0" cellpadding="4" width="100%"
				align="center">
				<tr>
					<td align="center">
						<button class="btn_sec" onClick=
	window.close();
>
							关闭
						</button>
					</td>
				</tr>
			</table>
		</div>
	</body>
</html>
<%@ include file="/mgr/public/jsp/footinc.jsp"%>