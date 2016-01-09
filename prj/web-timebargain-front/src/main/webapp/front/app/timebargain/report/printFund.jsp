<%@ page contentType="text/html;charset=GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/front/public/includefiles/allIncludeFiles.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>�ʽ�����ҳ��</title>
		<link href="${skinPath}/css/mgr/memberadmin/module.css"
			rel="stylesheet" type="text/css" />
		<link rel="stylesheet"
			href="${skinPath }/css/validationEngine.jquery.css" type="text/css" />
		<script type="text/javascript"
			src="${publicPath}/js/jquery-1.6.2.min.js"></script>
		<script type="text/javascript"
			src="${frontPath }/app/timebargain/js/jquery.validationEngine-zh_CN.js"></script>
		<script type="text/javascript"
			src="${publicPath }/js/jquery.validationEngine.js"></script>
		<script type="text/javascript"
			src="${publicPath}/js/calendar/WdatePicker.js"></script>
		<script type="text/javascript" src="${publicPath}/js/tool.js"></script>
		<script>
	jQuery(document).ready(function() {
		//���ñ���֤
			$("#frm").validationEngine("attach");
			$("#view").click(function() {
				var tradeDate = jQuery('#tradeDate').val();
				if (tradeDate == '') {
					alert("��ѯ���ڲ���Ϊ��");
					return;
				}
				if ($("#frm").validationEngine('validate')) {
					$("#frm").submit();
				}
			});
		});
</script>
	</head>
	<body>
		<!-------------------------Body Begin------------------------->
		<div class="main">
			<jsp:include page="/front/frame/current.jsp"></jsp:include>
			<div class="warning">
				<div class="title font_orange_14b">
					��ܰ��ʾ :
				</div>
				<div class="content">
					�ڴ�չʾ�����ʽ������Ϣ��
				</div>
			</div>
			<div class="main_copy">
				<form id="frm"
					action="${frontPath}/app/timebargain/report/firmFundsQuery.action"
					method="post">
					<div class="sort">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td height="30" width="250">
									<label>
										��ѯ���ڣ�
										<input id="tradeDate"
											class="Wdate validate[required,custom[date],past[${today}]] datepicker"
											name="${GNNT_}b_date" value="${oldParams['b_date']}"
											onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})" />
									</label>
								</td>
								<td width="110" height="30" align="center" valign="middle">
									<a href="#" id="view"><img
											src="${skinPath }/image/memberadmin/searchbt1.gif" width="93"
											height="23" border="0" /> </a>
								</td>
							</tr>
						</table>
					</div>
				</form>
				<div class="form margin_10b">
					<div class="column1">
						�ʽ�����
					</div>
					<table border="0" cellspacing="0" cellpadding="0" class="content">
						<c:if test="${fn:length(fundList)>0}">
							<c:forEach items="${fundList}" var="fund">
								<tr>
									<th width="17%" scope="row">
										�������ڣ�
									</th>
									<td width="83%">
										<fmt:formatDate value="${fund.B_DATE}" pattern="yyyy-MM-dd" />
										&nbsp;
									</td>
								</tr>
								<tr>
									<th scope="row">
										�����̴��룺
									</th>
									<td>
										<c:out value="${fund.FIRMID}" />
										&nbsp;
									</td>
								</tr>
								<tr>
									<th scope="row" class="font_orange_12">
										�ڳ��ʽ���
										<span class="font_orange_12">*</span>
									</th>
									<td>
										<fmt:formatNumber value="${fund.LASTBALANCE}"
											pattern="#,##0.00#" />
										&nbsp;
									</td>
								</tr>
								<c:if test="${fn:length(fundFieldList)>0}">
									<c:forEach items="${fundFieldList}" var="funFilds">
										<tr>
											<th scope="row">
												<c:choose>
													<c:when test="${funFilds.FIELDSIGN==1}">
														<c:out value="(+)${funFilds.NAME}��" />&nbsp;
												</c:when>
													<c:otherwise>
														<c:out value="(-)${funFilds.NAME}��" />&nbsp;
												</c:otherwise>
												</c:choose>
											</th>
											<td>
												<fmt:formatNumber value="${funFilds.VALUE}"
													pattern="#,##0.00#" />
												&nbsp;
											</td>
										</tr>
									</c:forEach>
								</c:if>
								<tr>
									<th scope="row">
										(+)����ϵͳ�����
									</th>
									<td>
										<fmt:formatNumber value="${fund.VALUE}" pattern="#,##0.00#" />
										&nbsp;
									</td>
								</tr>
								<tr>
									<th scope="row" class="font_orange_12">
										��ĩ�ʽ���
										<span class="font_orange_12">*</span>
									</th>
									<td>
										<fmt:formatNumber value="${fund.TODAYBALANCE}"
											pattern="#,##0.00#" />
										&nbsp;
									</td>
								</tr>
								<tr>
									<th scope="row">
										(+)���ձ�֤��
									</th>
									<td>
										<fmt:formatNumber value="${fund.RUNTIMEMARGIN}"
											pattern="#,##0.00#" />
										&nbsp;
									</td>
								</tr>
								<tr>
									<th scope="row">
										(+)���ո�����
									</th>
									<td>
										<fmt:formatNumber value="${fund.RUNTIMEFL}"
											pattern="#,##0.00#" />
										&nbsp;
									</td>
								</tr>
								<tr>
									<th scope="row">
										(+)���ս��ձ�֤��
									</th>
									<td>
										<fmt:formatNumber value="${fund.RUNTIMESETTLEMARGIN}"
											pattern="#,##0.00#" />
										&nbsp;
									</td>
								</tr>
								<tr>
									<th scope="row">
										(+)����ӯ����
									</th>
									<td>
										<fmt:formatNumber value="${fund.PL}" pattern="#,##0.00#" />
										&nbsp;
									</td>
								</tr>
								<!-- ��ʼ������Ȩ�� -->
								<c:set var="benefit" value="0.00" />
								<c:set var="benefit"
									value="${benefit+fund.TODAYBALANCE+fund.RUNTIMEMARGIN+fund.RUNTIMESETTLEMARGIN+fund.RUNTIMEFL+fund.PL+fund.CLOSE_PL-fund.TRADEFEE}" />
								<tr>
									<th scope="row" class="font_orange_12">
										����Ȩ�棺&nbsp;
									</th>
									<td>
										<fmt:formatNumber value="${ benefit }" pattern="#,##0.00#" />
										&nbsp;
									</td>
								</tr>
								<tr>
									<th scope="row">
										���ձ�֤��&nbsp;
									</th>
									<td>
										<fmt:formatNumber value="${fund.CLEARMARGIN}"
											pattern="#,##0.00#" />
										&nbsp;
									</td>
								</tr>
								<tr>
									<th scope="row">
										���ո�����&nbsp;
									</th>
									<td>
										<fmt:formatNumber value="${fund.CLEARFL}" pattern="#,##0.00#" />
										&nbsp;
									</td>
								</tr>
								<tr>
									<th scope="row">
										���ս��ձ�֤��&nbsp;
									</th>
									<td>
										<fmt:formatNumber value="${fund.CLEARSETTLEMARGIN}"
											pattern="#,##0.00#" />
										&nbsp;
									</td>
								</tr>
								<tr>
									<th scope="row">
										����׼��������޶&nbsp;
									</th>
									<td>
										<fmt:formatNumber value="${fund.MINCLEARDEPOSIT}"
											pattern="#,##0.00#" />
										&nbsp;
									</td>
								</tr>
								<tr>
									<th scope="row">
										��Ѻ�ʽ�&nbsp;
									</th>
									<td>
										<fmt:formatNumber value="${fund.MAXOVERDRAFT}"
											pattern="#,##0.00#" />
										&nbsp;
									</td>
								</tr>
								<c:if test="${fund.TODAYBALANCE<0}">
									<font color="red">���ѣ��������ʽ���,�뼰ʱ׷��</font>
								</c:if>
							</c:forEach>
						</c:if>

					<c:if test="${fn:length(fundList)<=0}">
							<c:if test="${fn:length(query)>0}">
								<div class="column1">
									<c:out value="����ѯ���������ʽ��¼��û�н����ʽ��������Ƿǽ�����" />&nbsp;
								</div>
								
							</c:if>	
					</c:if>
					</table>
					
				</div>
			</div>
		</div>
		<!-------------------------Body End------------------------->
	</body>
</html>