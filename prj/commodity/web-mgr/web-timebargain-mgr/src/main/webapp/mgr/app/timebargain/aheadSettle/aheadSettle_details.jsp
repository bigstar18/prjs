<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<base target="_self" />
		<title>提前交收申请详情</title>
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
				//“审核通过”点击事件
				$("#auditPass").click(function(){
					//验证信息
					if(jQuery("#frm").validationEngine('validate')){
						var vaild = affirm("您确定要操作吗？");
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
							//添加信息URL
							var auditUrl = $(this).attr("action");
							//全 URL 路径
							var url = "${basePath}"+auditUrl;
							$("#frm").attr("action",url);
							$("#frm").submit();
						}
					}
				});

				//“审核不通过”点击事件
				$("#auditFail").click(function(){
					//验证信息
					if(jQuery("#frm").validationEngine('validate')){
						var vaild = affirm("您确定要操作吗？");
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
							//添加信息URL
							var auditUrl = $(this).attr("action");
							//全 URL 路径
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
								温馨提示 :提前交收${entity.applyId}信息详情
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
											详情信息
										</div>
										<div class="div_cxtjR"></div>
									</div>
									<div style="clear: both;"></div>
									<div>
										<table border="1" cellspacing="0" cellpadding="4" width="100%" align="center" class="table2_style">
												<tr>
													<td align="right" width="25%">
														申请单号：
													</td>
													<td width="25%">
														${entity.applyId}&nbsp;
													</td>
													<td align="right" width="25%">
														商品代码：
													</td>
													<td width="25%">
														${entity.commodityId}&nbsp;
													</td>
												</tr>
												<tr>
													<td align="right">
														卖方二级代码：
													</td>
													<td>
														${entity.customerId_S}&nbsp;
													</td>
													<td align="right">
														买方二级代码：
													</td>
													<td>
														${entity.customerId_B}&nbsp;
													</td>
												</tr>
												<tr>
													<td align="right">
														交收价格：
													</td>
													<td>
														<c:if test="${entity.price!=0}">${entity.price}</c:if>
														<c:if test="${entity.price==0}">订立价交收</c:if>&nbsp;
													</td>
													<td align="right">
														交收数量：
													</td>
													<td colspan="2">
														${entity.quantity}&nbsp;
													</td>
												</tr>
												<tr>
													<td align="right">
														状态：
													</td>
													<td>
														<c:forEach items="${audit_statusMap}" var="result">
															<c:if test="${entity.status==result.key }">${result.value }</c:if>
														</c:forEach>&nbsp;
													</td>
													<td align="right">
														创建时间：
													</td>
													<td>
														<fmt:formatDate value="${entity.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>&nbsp;
													</td>
												</tr>
												<tr>
													<td align="right">
														创建人：
													</td>
													<td>
														${entity.creator}&nbsp;
													</td>
													<td align="right">
														创建人备注：
													</td>
													<td colspan="2">
														${entity.remark1}&nbsp;
													</td>
												</tr>
												<tr>
													<td align="right">
														仓单号：
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
														操作：
													</td>
													<td colspan="3">
														<input type="radio" id="status1" name="status" checked="checked" value="2">通过
														<input type="radio" id="status2" name="status" value="3">不通过
													</td>
												</tr>
												--%>
												<tr>
													<td align="right">
														修改人备注：
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
						<rightButton:rightButton name="审核通过" onclick="" className="btn_sec" action="/timebargain/aheadSettle/aheadSettleAuditPass.action" id="auditPass"></rightButton:rightButton>
						&nbsp;&nbsp;
						<rightButton:rightButton name="审核不通过" onclick="" className="btn_sec1" action="/timebargain/aheadSettle/aheadSettleAuditFail.action" id="auditFail"></rightButton:rightButton>
						&nbsp;&nbsp;
						<button class="btn_sec" onClick="window.close();">关闭</button>
					</td>
				</tr>
			</table>
		</div>
	</body>
</html>