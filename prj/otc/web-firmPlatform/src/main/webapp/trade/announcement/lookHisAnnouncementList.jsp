<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<%@page import="java.util.*"%>

<html>
	<head>
		<title>ϵͳ�û����</title>
		<%@ include file="/public/ecsideLoad.jsp"%>
	</head>
	<body style="background-color: #f5f5f5;">
		<form name="frm"
			action="${basePath}/tradeManage/lookHisAnnouncement/list.action?LOGINID=${LOGINID}&username=${username}"
			method="post">

			<table border="0" cellspacing="0" cellpadding="0" width="98%"
				align="center" height="30" style="border: 1px solid #bfbfbf;">
				<tr>
					<td>
						<table border="0" cellspacing="0" cellpadding="0" width="1000"
							height="30" style="padding-left: 10px;">
							<tr>
								<td height="40" width="1000">
									<div class="div2_top">
										<table class="table3_style" border="0" cellspacing="0"
											cellpadding="0">
											<tr>
												<td width="190" align="left">
													����:&nbsp;
													<label>
														<input type="text" class="input_text"
															name="${GNNT_}notice.title[like][String]" id="messageId"
															value="${oldParams['notice.title[like][String]'] }" />
													</label>
												</td>
												<td width="650" align="left">
													����ʱ��:&nbsp;��
													<input type="text" style="width: 100px" id="beginDate_send"
														class="wdate" maxlength="10"
														name="${GNNT_}notice.sendTime[>=][date]"
														value='${oldParams["notice.sendTime[>=][date]"]}'
														onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
													��
													<input type="text" style="width: 100px" id="endDate_send"
														class="wdate" maxlength="10"
														name="${GNNT_}notice.sendTime[<=][date]"
														value='${oldParams["notice.sendTime[<=][date]"]}'
														onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
												</td>
												<td width="650" align="left">
													ʧЧʱ��:&nbsp;��
													<input type="text" style="width: 100px" id="beginDate"
														class="wdate" maxlength="10"
														name="${GNNT_}notice.expiryTime[>=][date]"
														value='${oldParams["notice.expiryTime[>=][date]"]}'
														onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
													��
													<input type="text" style="width: 100px" id="endDate"
														class="wdate" maxlength="10"
														name="${GNNT_}notice.expiryTime[<=][date]"
														value='${oldParams["notice.expiryTime[<=][date]"]}'
														onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
												</td>
												<td width="260" align="left">
													<button class="btn_anniu" onclick="select1()">
														��ѯ
													</button>
													&nbsp;&nbsp;
													<button class="btn_anniu" onclick="myReset()">
														����
													</button>
												</td>
											</tr>
										</table>
									</div>
								</td>
							</tr>
						</table>
				</tr>
			</table>
		</form>
		<table border="0" cellspacing="0" cellpadding="0" width="90%"
			align="center">
			<tr>
				<td height="5">
					&nbsp;
				</td>
			</tr>
		</table>
		<table id="tb" border="0" cellspacing="0" cellpadding="0" width="98%"
			align="center">
			<tr>
				<td>

					<ec:table items="resultList"
						autoIncludeParameters="${empty param.autoInc}" var="notice"
						action="${basePath}/tradeManage/lookHisAnnouncement/list.action?LOGINID=${LOGINID}&username=${username}"
						minHeight="345" height="345" listWidth="100%"
						retrieveRowsCallback="limit" sortRowsCallback="limit"
						resizeColWidth="false" style="table-layout:fixed">
						<ec:row recordKey="${notice.id}">
							<ec:column width="3%" property="_0" title="���"
								value="${GLOBALROWCOUNT}" sortable="false" filterable="false" />
							<ec:column property="title[like]" title="����" width="20%"
								style="text-align:center;" tipTitle='${notice.titleHtml}'><a href="#" class="blank_a"
									onclick="showAnnouncement(${notice.id});"><font
									style="color: #880000">${notice.titleHtml}</font></a></ec:column>
							<ec:column property="publishOrganization[like]" title="��������"
								width="10%" style="text-align:left;"
								value="${notice.publishOrganization}" />
							<ec:column property="sendTime[=][date]" title="����ʱ��" width="12%"
								style="text-align:center;" filterable="false">
								${datefn:formatdate(notice.sendTime)}
							</ec:column>
							<ec:column property="expiryTime[=][Date]" title="ʧЧʱ��"
								width="12%" style="text-align:center;" filterable="false">
								${datefn:formatdate(notice.expiryTime)}
							</ec:column>
						</ec:row>
					</ec:table>
				</td>
			</tr>
		</table>
		<!-- �༭�͹�����ʹ�õ� ͨ�õ��ı���ģ�� -->
		<textarea id="ecs_t_input" rows="" cols="" style="display: none">
			<input type="text" class="inputtext" value=""
				onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="" />
		</textarea>
	</body>
	<script type="text/javascript">
function select1() {
	var now = new Date();
	var s = new Date(Date.parse(frm.beginDate.value.replace(/-/g, "/")));
	var e = new Date(Date.parse(frm.endDate.value.replace(/-/g, "/")));
	var s_send = new Date(Date.parse(frm.beginDate_send.value
			.replace(/-/g, "/")));
	var e_send = new Date(Date.parse(frm.endDate_send.value.replace(/-/g, "/")));
	if (s_send > e_send) {
		alert("��ѯ��ʼ���ڲ��ܴ��ڽ�������");
		return false;
	} else if (s_send != "" && s_send > now || e_send != "" && e_send > now) {
		alert("����ʱ�䲻�ܴ��ڵ�ǰʱ��");
		return false;
	} else if (s > e) {
		alert("��ѯ��ʼ���ڲ��ܴ��ڽ�������");
		return false;
	} else {
		frm.submit();
	}
}
function resetNun() {
	frm.messageId.value = "";
	frm.submit();
}
function showAnnouncement(id) {
	var url = "${basePath}/tradeManage/lookHisAnnouncement/forwardUpdate.action?obj.id="
			+ id + "&LOGINID=${LOGINID}&username=${username}";
	ecsideDialog(url, null, 610, 450);
}
</script>
</html>

<%@ include file="/public/footInc.jsp"%>