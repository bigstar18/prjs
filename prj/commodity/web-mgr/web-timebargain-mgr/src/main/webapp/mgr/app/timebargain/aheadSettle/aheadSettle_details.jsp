<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<base target="_self" />
		<title>��ǰ������������</title>
		<link rel="stylesheet" href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationengine/template.css" type="text/css" />
		<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
		<script src="${publicPath }/js/validationengine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
		<script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>
		<script type="text/javascript">
			if(${result!=''} && ${result!=null}){
				alert('${result }');
			}
			jQuery(document).ready(function() {
				jQuery("#frm").validationEngine('attach');
				//�����ͨ��������¼�
				$("#auditPass").click(function(){
					//��֤��Ϣ
					if(jQuery("#frm").validationEngine('validate')){
						var vaild = affirm("��ȷ��Ҫ������");
						if(vaild){
							var statusValue = -1;
							var status = document.getElementsByName("status");
							for(var i=0;i<=status.length;i++){
								if(status[i]!=null){
									if(status[i].checked){
										statusValue = status[i].value;
									}
								}
							}
							//�����ϢURL
							var auditUrl = $(this).attr("action");
							//ȫ URL ·��
							var url = "${basePath}"+auditUrl;
							$("#frm").attr("action",url);
							$("#frm").submit();
						}
					}
				});

				//����˲�ͨ��������¼�
				$("#auditFail").click(function(){
					//��֤��Ϣ
					if(jQuery("#frm").validationEngine('validate')){
						var vaild = affirm("��ȷ��Ҫ������");
						if(vaild){
							var statusValue = -1;
							var status = document.getElementsByName("status");
							for(var i=0;i<=status.length;i++){
								if(status[i]!=null){
									if(status[i].checked){
										statusValue = status[i].value;
									}
								}
							}
							//�����ϢURL
							var auditUrl = $(this).attr("action");
							//ȫ URL ·��
							var url = "${basePath}"+auditUrl;
							$("#frm").attr("action",url);
							$("#frm").submit();
						}
					}
				});
			});
		</script>
	</head>
	<body>
		<div class="div_cx">
		<form id="frm" method="post" enctype="multipart/form-data" action="">
			<input type="hidden" name="entity.applyId" value="${entity.applyId}"/>
			<table border="0" width="100%" align="center">
				<tr>
					<td>
						<div class="warning">
							<div class="content">
								��ܰ��ʾ :��ǰ����${entity.applyId}��Ϣ����
							</div>
						</div>
					</td>
				</tr>
				<tr>
					<td>
						<table border="0" width="100%" align="center">
							<tr>
								<td>
									<div class="div_cxtj">
										<div class="div_cxtjL"></div>
										<div class="div_cxtjC">
											������Ϣ
										</div>
										<div class="div_cxtjR"></div>
									</div>
									<div style="clear: both;"></div>
									<div>
										<table border="1" cellspacing="0" cellpadding="4" width="100%" align="center" class="table2_style">
												<tr>
													<td align="right" width="25%">
														���뵥�ţ�
													</td>
													<td width="25%">
														${entity.applyId}&nbsp;
													</td>
													<td align="right" width="25%">
														��Ʒ���룺
													</td>
													<td width="25%">
														${entity.commodityId}&nbsp;
													</td>
												</tr>
												<tr>
													<td align="right">
														�����������룺
													</td>
													<td>
														${entity.customerId_S}&nbsp;
													</td>
													<td align="right">
														�򷽶������룺
													</td>
													<td>
														${entity.customerId_B}&nbsp;
													</td>
												</tr>
												<tr>
													<td align="right">
														���ռ۸�
													</td>
													<td>
														<c:if test="${entity.price!=0}">${entity.price}</c:if>
														<c:if test="${entity.price==0}">�����۽���</c:if>&nbsp;
													</td>
													<td align="right">
														����������
													</td>
													<td colspan="2">
														${entity.quantity}&nbsp;
													</td>
												</tr>
												<tr>
													<td align="right">
														״̬��
													</td>
													<td>
														<c:forEach items="${audit_statusMap}" var="result">
															<c:if test="${entity.status==result.key }">${result.value }</c:if>
														</c:forEach>&nbsp;
													</td>
													<td align="right">
														����ʱ�䣺
													</td>
													<td>
														<fmt:formatDate value="${entity.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>&nbsp;
													</td>
												</tr>
												<tr>
													<td align="right">
														�����ˣ�
													</td>
													<td>
														${entity.creator}&nbsp;
													</td>
													<td align="right">
														�����˱�ע��
													</td>
													<td colspan="2">
														${entity.remark1}&nbsp;
													</td>
												</tr>
												<tr>
													<td align="right">
														�ֵ��ţ�
													</td>
													<td colspan="3">
														<c:forEach items="${list}" var="map">
															${map['STOCKID'] }&nbsp;
														</c:forEach>
													</td>
												</tr>
												<%-- 
												<tr>
													<td align="right">
														������
													</td>
													<td colspan="3">
														<input type="radio" id="status1" name="status" checked="checked" value="2">ͨ��
														<input type="radio" id="status2" name="status" value="3">��ͨ��
													</td>
												</tr>
												--%>
												<tr>
													<td align="right">
														�޸��˱�ע��
													</td>
													<td colspan="3">
														<input type="text" id="remark2" name="entity.remark2" size="60"
															class="validate[maxSize[126]] "/>
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
		<div class="tab_pad">
			<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center">
				<tr>
					<td align="right">
						<rightButton:rightButton name="���ͨ��" onclick="" className="btn_sec" action="/timebargain/aheadSettle/aheadSettleAuditPass.action" id="auditPass"></rightButton:rightButton>
						&nbsp;&nbsp;
						<rightButton:rightButton name="��˲�ͨ��" onclick="" className="btn_sec1" action="/timebargain/aheadSettle/aheadSettleAuditFail.action" id="auditFail"></rightButton:rightButton>
						&nbsp;&nbsp;
						<button class="btn_sec" onClick="window.close();">�ر�</button>
					</td>
				</tr>
			</table>
		</div>
	</body>
</html>