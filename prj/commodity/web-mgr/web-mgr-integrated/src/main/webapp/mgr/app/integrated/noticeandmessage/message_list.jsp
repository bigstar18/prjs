<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
	<head>
		<title>��Ϣ�б�</title>
	</head>
	<body onload="getFocus('id');">
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0"
				cellpadding="0">
				<tr>
					<td>
						<div class="div_cx">
							<form name="frm"
								action="${basePath}/trade/message/messageList.action?sortColumns=order+by+createTime+desc"
								method="post">
								<table border="0" cellpadding="0" cellspacing="0"
									class="table2_style" width="80%">
									<tr>
										<td width="600" height="40">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td class="table3_td_1" align="left">
															��Ϣ���:&nbsp;
															<label>
																<input type="text" class="input_text" id="id"
																	name="${GNNT_}primary.messageId[=][int]"
																	value="${oldParams['primary.messageId[=][int]']}"    maxLength="<fmt:message key='messageId_q' bundle='${PropsFieldLength}'/>"/>
															</label>
														</td>
														<td class="table3_td_1" align="left">
															����Ա����:&nbsp;
															<label>
																<input type="text" class="input_text" id="id"
																	name="${GNNT_}primary.userId[allLike]"
																	value="${oldParams['primary.userId[allLike]']}"  maxLength="<fmt:message key='userID_q' bundle='${PropsFieldLength}'/>"/>
															</label>
														</td>
														<td class="table3_td_anniu" align="left">
															<button class="btn_sec" onclick=select1();>
																��ѯ
															</button>
															&nbsp;&nbsp;
															<button class="btn_cz" onclick=myReset();>
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
						<div class="div_gn">
							<rightButton:rightButton name="���" onclick="addMessage();"
								className="anniu_btn"
								action="/trade/message/addForwardMessage.action" id="add"></rightButton:rightButton>
							&nbsp;&nbsp;
						</div>
						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0"
								width="100%">
								<tr>
									<td>
										<ec:table items="pageInfo.result" var="message"
											action="${basePath}/trade/message/messageList.action?sortColumns=order+by+createTime+desc"
											autoIncludeParameters="${empty param.autoInc}"
											xlsFileName="arbitration.xls" csvFileName="arbitration.csv"
											showPrint="true" listWidth="100%"
											minHeight="345" style="table-layout:fixed;">
											<ec:row>
												
												<ec:column width="5%" property="_0" title="���"
													value="${GLOBALROWCOUNT}" sortable="false"
													style="text-align:center;" filterable="false" />
												<ec:column property="messageId" title="��Ϣ���" width="10%"
													style="text-align:left;" />
												<ec:column property="message" title="��Ϣ����" width="20%"
													style="text-align:center;" ellipsis="true" />
												<ec:column property="createTime" title="����ʱ��"
													width="20%" style="text-align:center;">
													<fmt:formatDate value="${message.createTime}"
														pattern="yyyy-MM-dd HH:mm:ss" />
												</ec:column>
												<ec:column property="userId" title="����Ա����" width="10%"
													style="text-align:center;" />
												<ec:column property="traderId" title="������" width="10%"
													style="text-align:center;"  sortable="false"
													filterable="false" ellipsis="true">
														<c:if test="${message.recieverType!=4&&message.recieverType!=5}">
															<c:set var="key">
																<c:out value="${message.recieverType}"></c:out>
															</c:set>
															${recieverMap[key]}
														</c:if>
														<c:if test="${message.recieverType==4||message.recieverType==5}">
															${message.traderId}
														</c:if>
												</ec:column>
												<ec:column property="recieverType" title="����������"
													width="10%" style="text-align:center;">
													<c:set var="key">
														<c:out value="${message.recieverType}"></c:out>
													</c:set>
													${recieverMap[key]}
												</ec:column>
												<ec:column property="0" title="�鿴��Ϣ" width="10%"
													style="text-align:center;" sortable="false"
													filterable="false">
													<rightHyperlink:rightHyperlink href="#" className="blank_a" action="/trade/message/forwardMessage.action" id="detail" text="<font color='#880000'>����</font>" onclick="forwardMessage('${message.messageId }');"/>
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


			<!-- �༭�͹�����ʹ�õ� ͨ�õ��ı���ģ�� -->
			<textarea id="ecs_t_input" rows="" cols="" style="display: none">
		<input type="text" class="inputtext" value=""
					onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
					name="" />
	</textarea>

			<SCRIPT type="text/javascript">
	function select1() {
		frm.submit();
	}
	function  forwardMessage(id){
		var a=document.getElementById("detail").action;
		var url="<%=basePath%>"+a+"?entity.messageId="
				+ id;
		ecsideDialog(url, "", 500, 400);

	}
	function  addMessage(id){
		var url="${basePath}"+document.getElementById("add").action;
		ecsideDialog(url, "", 500, 450);

	}
</SCRIPT>
	</body>
</html>