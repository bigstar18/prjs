<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>��˲�������</title>
	</head>
	<body style="overflow-y: hidden">
		<form name="myForm" action="" method="post" targetType="hidden">
			<div class="div_scrospmid">
				<table border="0" width="90%" align="center">
					<tr>
						<td>
							<div class="st_title">
								<img src="<%=skinPath%>/image/detail_title.gif" align="absmiddle" />
								&nbsp;��ϸ��Ϣ
							</div>
							<input type="hidden" name="auditId" value="${audit.id}">
							<c:if
								test="${audit.apply.operateType=='add'||audit.apply.operateType=='update'}">
								<jsp:include
									page="/mgr/app/${auditOperateMap[audit.apply.applyType].detailTablePath}"></jsp:include>
							</c:if>
							<table border="0" width="100%" align="center">
								<tr height="50"></tr>
								<tr>
									<td>
										<div class="div_cxtj">
											<div class="div_cxtjL"></div>
											<div class="div_cxtjC">
												<c:if test="${audit.apply.operateType=='add'}">
									��Ӽ�¼
								</c:if>
												<c:if test="${audit.apply.operateType=='update'}">
									�޸ļ�¼
								</c:if>
												<c:if
													test="${audit.apply.operateType=='delete'||audit.apply.operateType=='deleteCollection'}">
									ɾ����¼
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
														${audit.apply.discribe }
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
												���벵����ʷ��¼
											</div>
											<div class="div_cxtjR"></div>
										</div>
										<div style="clear: both;"></div>
										<div class="div_cx">
											<table border="0" cellspacing="0" cellpadding="0" width="80%"
												height="70" class="table2_style">
												<tr>
													<td width="5%"></td>
													<td align="left" width="15%">
														������
													</td>
													<td align="left" width="30%">
														����ʱ��
													</td>
													<td align="left" width="15%">
														������
													</td>
													<td align="left" width="30%">
														����ʱ��
													</td>
													<tr>
														<tr>
															<td></td>
															<td align="left">
																${audit.apply.applyUser}
															</td>
															<td align="left">
																<fmt:formatDate value="${audit.apply.createTime}"
																	pattern="yyyy-MM-dd HH:mm:ss" />
															</td>
															<td align="left">
																${audit.auditUser}
															</td>
															<td align="left">
																<fmt:formatDate value="${audit.modTime}"
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
												�ر�
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