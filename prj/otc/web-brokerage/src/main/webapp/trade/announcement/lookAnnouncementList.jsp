<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<%@page import="java.util.*"%>

<html>
	<head>
		<title>ϵͳ�û����</title>
		<%@ include file="/public/ecsideLoad.jsp"%>
	</head>
	<body>
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0"
				cellpadding="0">
				<tr>
					<td>
						<div class="div_tj">
							<form name="frm"
								action="${basePath}/tradeManage/lookAnnouncement/list.action"
								method="post">
								<table border="0" cellpadding="0" cellspacing="0"
									class="table2_style">
									<tr>
										<td height="40" width="800">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td width="190" align="left">
															����:&nbsp;
															<label>
																<input type="text" class="input_text"
																	name="gnnt_notice.title[like][String]" id="messageId"
																	value="${oldParams['notice.title[like][String]'] }" />
															</label>
														</td>
														<td width="400" align="left">
															����ʱ��:&nbsp;��
															<input type="text" style="width: 100px" id="beginDate"
																class="wdate" maxlength="10"
																name="gnnt_notice.sendTime[>=][date]"
																value='${oldParams["notice.sendTime[>=][date]"]}'
																onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
															��
															<input type="text" style="width: 100px" id="endDate"
																class="wdate" maxlength="10"
																name="gnnt_notice.sendTime[<=][date]"
																value='${oldParams["notice.sendTime[<=][date]"]}'
																onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
														</td>
														<td class="table3_td_anniu" align="left">
															<button class="btn_sec" onclick="select1()">
																��ѯ
															</button>&nbsp;&nbsp;
															<button class="btn_cz" onclick="myReset()">
																����
															</button>
														</td>
													</tr>
												</table>
											</div>
										</td>
									</tr>
								</table>
							</form>
						</div>

						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0"
								width="100%">
								<tr>
									<td>
										<ec:table items="noticeList"
											autoIncludeParameters="${empty param.autoInc}" var="notice"
											action="${basePath}/tradeManage/lookAnnouncement/list.action"
											minHeight="345" listWidth="100%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" 
											csvFileName="�����б�.csv"   style="table-layout:fixed">
											<ec:row recordKey="${notice.id}">
											    <ec:column width="3%" property="_0" title="���" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" />
												<ec:column property="title[like]" title="����" width="20%"
													style="text-align:center;" tipTitle='${notice.titleHtml}'><a href="#" class="blank_a"
														onclick="showAnnouncement(${notice.id});"><font style="color: #880000">${notice.titleHtml}</font></a></ec:column>
												<ec:column property="author[like]" title="����Ա" width="10%"
													style="text-align:center; ">
													${notice.author}
												</ec:column>
												<ec:column property="publishOrganization[like]" title="��������" width="10%"
													style="text-align:left;" value="${notice.authorOrganization}" filterable="false" />
												<ec:column property="sendTime[=][date]" title="����ʱ��"
													width="12%" style="text-align:center;" filterable="false">
													${datefn:formatdate(notice.sendTime)}
												</ec:column>
												<ec:column property="expiryTime[=][Date]" title="ʧЧʱ��" width="12%" 
													style="text-align:left;" filterable="false">
													${datefn:formatdate(notice.expiryTime)}
												</ec:column>
												</ec:row>
										</ec:table>
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
			</table>
		</div>
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
	var s = new Date(Date.parse(frm.beginDate.value.replace(/-/g,   "/")));
	var e = new Date(Date.parse(frm.endDate.value.replace(/-/g,   "/")));
	if (s!="" && s > now || e!="" && e > now) {
		alert("����ʱ�䲻�ܴ��ڵ�ǰʱ��");
		return false;
	} else if(s > e) {
		alert("��ѯ��ʼ���ڲ��ܴ��ڽ�������");
		return false;
	} else {
		frm.submit();
	}
}
function showAnnouncement(id) {
	var url = "${basePath}/tradeManage/lookAnnouncement/forwardUpdate.action?obj.id="+ id;
	ecsideDialog(url, null, 610, 560);
}

											
</script>

</html>