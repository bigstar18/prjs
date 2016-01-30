<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>已撤销申请详情</title>
	</head>
	<body style="overflow-y: hidden">
		<form name="myForm" action="" method="post" targetType="hidden">
			<div class="div_scrospmid">
				<table border="0" width="90%" align="center">
					<tr>
						<td>
							<div class="st_title">
								<img src="<%=skinPath%>/image/detail_title.gif" align="absmiddle" />
								&nbsp;详细信息
							</div>
							<input type="hidden" name="applyId" value="${apply.id}">
							<c:if
								test="${apply.operateType=='add'||apply.operateType=='update'}">
								<jsp:include
									page="/mgr/app/${auditOperateMap[apply.applyType].detailTablePath}"></jsp:include>
							</c:if>
							<table border="0" width="100%" align="center">
								<tr height="50"></tr>
								<tr>
									<td>


										<div class="div_cxtj">
											<div class="div_cxtjL"></div>
											<div class="div_cxtjC">
												<c:if test="${apply.operateType=='add'}">
									添加记录
								</c:if>
												<c:if test="${apply.operateType=='update'}">
									修改记录
								</c:if>
												<c:if
													test="${apply.operateType=='delete'||apply.operateType=='deleteCollection'}">
									删除记录
								</c:if>
											</div>
											<div class="div_cxtjR"></div>
										</div>
										<div style="clear: both;"></div>
										<div class="div_cx">
											<table border="0" cellspacing="0" cellpadding="0" width="80%"
												height="50" class="table2_style">
												<tr>
													<td>
														${apply.discribe }
													</td>
												</tr>
											</table>
										</div>
									</td>
								</tr>
							</table>
							<table border="0" width="100%" align="center">
								<tr height="50"></tr>
								<tr>
									<td>
										<div class="div_cxtj">
											<div class="div_cxtjL"></div>
											<div class="div_cxtjC">
												已撤销历史记录
											</div>
											<div class="div_cxtjR"></div>
										</div>
										<div style="clear: both;"></div>

										<div class="div_cx">
											<table border="0" cellspacing="0" cellpadding="0" width="80%"
												height="70" class="table2_style">
												<tr>
													<td width="20%"></td>
													<td align="left" width="40%">
														申请时间
													</td>
													<td align="left" width="40%">
														撤销时间
													</td>
													<tr>
														<tr>
															<td></td>
															<td align="left">
																<fmt:formatDate value="${apply.createTime}"
																	pattern="yyyy-MM-dd HH:mm:ss" />
															</td>
															<td align="left">
																<fmt:formatDate value="${apply.modTime}"
																	pattern="yyyy-MM-dd HH:mm:ss" />
															</td>
														</tr>
											</table>
									</td>
								</tr>
							</table>
							</div>
							<div class="tab_pad">
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
							</form>
	</body>
</html>

<script language="javascript">
	setReadOnly();
</script>
<%@include file="/mgr/public/jsp/footinc.jsp"%>