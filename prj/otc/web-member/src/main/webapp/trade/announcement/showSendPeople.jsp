<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>

<html>
	<head>
		<title>���Ͷ���</title>
	</head>
	<body style="overflow-y: hidden">
		<form name="frm" action="" method="post" targetType="hidden">
			<div style="overflow:auto;height:500px;">
				<table border="0" width="500" align="center">
					<tr height="30"></tr>
					<tr>
						<td>
							<div class="st_title">
								<img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />
								&nbsp;&nbsp;���Ͷ���
							</div>
							<table border="0" cellspacing="0" cellpadding="10" width="100%"
								align="center" class="st_bor" style="word-break:break-all;table-layout:fixed;">
								<c:if test="${specialMemberNames != ''}">
									<tr>
										<td align="right" width="80">
											�ر��Ա:
										</td>
										<td>
											<input type="text" class="input_text" id="specialMemberNames" name="obj.specialMemberNames"
												value="${specialMemberNames}">
										</td>
									</tr>
								</c:if>
								<c:if test="${memberNames != ''}">
									<tr>
										<td align="right" width="80">
											��Ա:
										</td>
										<td>
											<input type="text" class="input_text" id="memberNames" name="obj.memberNames" 
												value="${memberNames}">
										</td>
									</tr>
								</c:if>
								<c:if test="${organizationNames != ''}">
									<tr>
										<td align="right" width="80">
											����:
										</td>
										<td>
											<input type="text" class="input_text" id="organizationNames" name="obj.organizationNames" 
												value="${organizationNames}">
										</td>
									</tr>
								</c:if>
								<c:if test="${brokerageNames != ''}">
									<tr>
										<td align="right" width="80">
											�Ӽ�:
										</td>
										<td>
											<input type="text" class="input_text" id="brokerageNames" 
												name="obj.brokerageNames" value="${brokerageNames}" >
										</td>
									</tr>
								</c:if>
								<c:if test="${memberOrganizationNames != ''}">
									<tr>
										<td align="right" width="80">
											����:
										</td>
										<td>
											<input type="text" class="input_text" id="organizationNames" name="obj.organizationNames" 
												value="${memberOrganizationNames}">
										</td>
									</tr>
								</c:if>
								<c:if test="${memberBrokerageNames != ''}">
									<tr>
										<td align="right" width="80">
											�Ӽ�:
										</td>
										<td>
											<input type="text" class="input_text" id="brokerageNames" 
												name="obj.brokerageNames" value="${memberBrokerageNames}" >
										</td>
									</tr>
								</c:if>
								<c:if test="${customerNames != ''}">
									<tr>
										<td align="right" width="80">
											�ͻ�:
										</td>
										<td>
											<input type="text" class="input_text" id="customerNames" 
												name="obj.customerNames" value="${customerNames}" >
										</td>
									</tr>
								</c:if>
								<c:if test="${memberForCustomerNames != ''}">
									<tr>
										<td align="right" width="80">
											�ͻ�:
										</td>
										<td>
											<input type="text" class="input_text" id="memberForCustomerNames" 
												name="obj.memberForCustomerNames" value="${memberForCustomerNames}�ͻ�" >
										</td>
									</tr>
								</c:if>
								<c:if test="${organizationCustomerNames != ''}">
									<tr>
										<td align="right" width="80">
											�����ͻ�:
										</td>
										<td>
											<input type="text" class="input_text" id="organizationCustomerNames" 
												name="obj.organizationCustomerNames" value="${organizationCustomerNames}�µĿͻ�" >
										</td>
									</tr>
								</c:if>
								<%--<c:if test="${brokerageCustomerNames != ''}">
									<tr>
										<td align="right" width="80">
											�Ӽ�ͻ�:
										</td>
										<td>
											<input type="text" class="input_text" id="brokerageCustomerNames" 
												name="obj.brokerageCustomerNames" value="${brokerageCustomerNames}�µĿͻ�" >
										</td>
									</tr>
								</c:if>
								--%>
								<c:if test="${tradermembernoticeNames != '' && tradermembernoticeNames != null}">
									<tr>
										<td align="right" width="80">
											�ͻ�:
										</td>
										<td>
											<input type="text" class="input_text" id="tradermembernoticeNames" 
												name="obj.tradermembernoticeNames" value="${tradermembernoticeNames}" >
										</td>
									</tr>
								</c:if>
							</table>
						</td>
					</tr>
				</table>
			</div>
			<div class="tab_pad">
			<table border="0" cellspacing="0" cellpadding="0" width="100%"
				align="center" >
				<tr>
					<td align="center">
						<button class="btn_sec" onclick="window.close()">
							�ر�
						</button>
					</td>
				</tr>
			</table>
			</div>
		</form>
	</body>

</html>
<script type="text/javascript">

</script>

<%@ include file="/public/footInc.jsp"%>

