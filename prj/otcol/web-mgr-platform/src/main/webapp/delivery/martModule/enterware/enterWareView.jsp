<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../public/session.jsp"%>
<%@ page import="gnnt.MEBS.delivery.model.*"%>
<html xmlns:MEBS>
  <head>
	<IMPORT namespace="MEBS" implementation="<%=basePath%>public/jstools/calendar.htc">
	<title></title>
	<style type="text/css">
	<!--
	.cd_bt {
		font-size: 12px;
		line-height: 16px;
		font-weight: bold;
		text-decoration: none;
		text-align: right;
		padding-right: 5px;
	}
	.cd_hr {
		font-size: 14px;
		line-height: 16px;
		font-weight: bold;
		text-decoration: none;
		text-align: center;
	}
	-->
    </style>
</head>
<body>
        <form id="frm" name="frm" method="POST" targetType="hidden">
        <input type="hidden" name="enterWareId" value="${operateObj.id}"/>
		<fieldset width="100%">
		<legend>��Ʒ��ⵥ<font color=red>(<c:out value="${status.name}"/>)</font></legend>
					<table width="100%" border="0" align="center" cellpadding="0"
					cellspacing="0" bordercolor="#333333"
					style="border-collapse:collapse;">
					<tr height="40px">
						<td colspan="5" class="cd_hr">
							<br>
							&nbsp;
						</td>
					</tr>
				</table>
				<table width="100%" border="0" align="center" cellpadding="0px"
					cellspacing="0" bordercolor="#333333"
					style="border-collapse:collapse;">
										<tr height="30px">
						<td  width="15%"class="cd_bt">
							���֪ͨ���ţ�${operateObj.enterInformId}
						</td>
						<td  width="25%"class="cd_list1">
				
						</td>
						<td  width="15%" class="cd_bt">
							���ʱ�䣺
						</td>
						<td  width="25%" class="cd_list1">
						<fmt:formatDate value="${operateObj.enterDate}" pattern="yyyy-MM-dd"/>
							
							
						</td>
					</tr>
				</table>
				<table id="tableList" width="100%" border="1" align="center"
					cellpadding="0px" cellspacing="0" bordercolor="#333333"
					style="border-collapse:collapse;">

					<tr height="25px">
						<td width="15%" class="cd_bt">
							${FIRMID}��
						</td>
						<td width="25%" class="cd_list1">
							${operateObj.firmId}
						</td>
						<td class="cd_bt">
							${FIRMNAME}��
						</td>
						<td  class="cd_list1" colspan=3>
							${dealer.name}
						</td>
						
					</tr>
					<tr height="25px">
						<td width="20%" class="cd_bt">
							�ֿ����ƣ�
						</td>
						<td width="30%" class="cd_list1">
							${warehouse.name}
						</td>
					
						<td width="" class="cd_bt">
							Ʒ�����ƣ�
						</td>
					
						<td width="" class="cd_list1" colspan=3>
							${commodity.name}
						</td>
					</tr>
					<tr height="25px">
					
						
							<td width="20%" class="cd_bt">
							�ȼ���
						</td>
						<td width="30%"class="cd_list1">
							${operateObj.grade}
						</td>
						
						<td width="" class="cd_bt">
								�������һ���أ�
						</td>
						<td width="" colspan=3 class="cd_list1">
							${operateObj.origin}
						</td>
					</tr>
					<tr height="25px">
				<td width="" class="cd_bt">
								���ࣺ
						</td>
						<td width=""  class="cd_list1">
							${operateObj.sort}
						</td>
						<td width="" class="cd_bt">
							���ţ�
						</td>
						<td width=" class="cd_list1" >
							${operateObj.lot}
						</td>
						<td width="" class="cd_bt">
							�������£�
						</td>
						<td width="" class="cd_list1" >
							${operateObj.productionDate}
						</td>
					</tr>
					
				
						<tr height="25px">
							<td class="cd_bt">���������</td>
							<td class="cd_list1" >
								<fmt:formatNumber value="${operateObj.weight }" pattern="#,##0.0000"/>
								<!-- ��ӶԵ�λΪ�յ��жϣ������λΪ�ա�����ʾ  add by zhaodc -->
				                <c:if test="${commodity.countType !=null}">(${commodity.countType })</c:if>
							</td> 
							<td class="cd_bt">��&nbsp;&nbsp;&nbsp;&nbsp;����	</td>
							<td class="cd_list1" >
								${operateObj.quantity}
							</td>
							<td class="cd_bt" >�� λ �ţ�</td>
							<td class="cd_list1">
								${operateObj.cargoNo}
							</td>
						</tr>

					<tr height="25px">
							<td class="cd_bt">ÿ��������</td>
							<td class="cd_list1" >
								<fmt:formatNumber value="${operateObj.unitWeight }" pattern="#,##0.0000"/>
								<!-- ��ӶԵ�λΪ�յ��жϣ������λΪ�ա�����ʾ  add by zhaodc -->
				                <c:if test="${commodity.countType !=null}">(${commodity.countType })</c:if>
							</td>
							<td class="cd_bt">��װ��ʽ��	</td>
							<td class="cd_list1" colspan=3>
								${operateObj.packaging}							
							</td>
					 </tr>
			</table>
			<br>
			
				<table width="100%" border="0" align="center" cellpadding="0"
					cellspacing="0" bordercolor="#333333"
					style="border-collapse:collapse;">
					<table border="0" align="center" cellpadding="0"
					cellspacing="0" bordercolor="#333333" style="border-collapse:collapse;">
					<tr height="40px">
						<td colspan="8" class="cd_hr">
							<br>
							������׼��<font color=red></font>
						</td>
					</tr>
					</table>
					<table width="100%" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#333333"
						style="border-collapse:collapse;">
					<%
						
						List list = (List)request.getAttribute("qualityStandardList");
						int num = 0;
						int leftNum = 0;
						if (list != null && list.size() > 3) {
							num = ((Double)Math.floor(list.size()/4)).intValue();
						}
						leftNum = list.size()%4;
						
						if (list.size() > 3) {
							for (int i = 0; i < num; i++) {
								KeyValue keyValue = (KeyValue)list.get(i);
								String key = keyValue.getKey();
								if (key == null || key == "") {
									key = "";
								}
								String value = keyValue.getValue();
								if (value == null || value == "") {
									value = "";
								}
					  %>
								<tr height="25">
					  <%
									for (int j = 0; j < 4; j++) {
										KeyValue keyValueT = (KeyValue)list.get((i*4)+j);
					  %>
										<td class="cd_bt" align="right" width="100">
											<%=keyValueT.getKey()%>
										</td>
										<td width="100">
											<%=keyValueT.getValue()%>
										</td>
					  <%
									}
					  %>
								</tr>
					  <%
							}
						}
					  %>
					
					<%
						if (leftNum == 1) {
							KeyValue keyValue  = (KeyValue)list.get(list.size()-1);
							String key = keyValue.getKey();
							String value = keyValue.getValue();
							if (key == null || key == "") {
								key = "";
							}
							if (value == null || value == "") {
								value = "";
							}
					%>
								<tr height="25">
									<td class="cd_bt" width="100"><%=key%></td><td width="100"><%=value%></td>
									<td width="100">&nbsp;</td><td width="50">&nbsp;</td><td width="100">&nbsp;</td>
								</tr>
					<%
						}
						if (leftNum == 2) {
					%>
							<tr height="25">
					<%
							for (int i1 = list.size()-1; i1 >= list.size()-2; i1--) {
								KeyValue keyValue = (KeyValue)list.get(i1);
								String key = keyValue.getKey();
								String value = keyValue.getValue();
								if (key == null || key == "") {
									key = "";
								}
								if (value == null || value == "") {
									value = "";
								}
					%>
								<td class="cd_bt" width="100"><%=key%></td><td width="100"><%=value%></td>
					<%
							}
					%>
							<td align="right" width="100">&nbsp;</td><td width="100">&nbsp;</td>
							<td align="right" width="100">&nbsp;</td><td width="100">&nbsp;</td>
							</tr>
					<%
						}
						if (leftNum == 3) {
					%>
							<tr height="25">
					<%
							for (int i2 = list.size()-1; i2 >= list.size()-3; i2--) {
								KeyValue keyValue = (KeyValue)list.get(i2);
								String key = keyValue.getKey();
								String value = keyValue.getValue();
								if (key == null || key == "") {
									key = "";
								}
								if (value == null || value == "") {
									value = "";
								}
					%>
								<td class="cd_bt" width="100" ><%=key%></td><td width="100"><%=value%></td>
					<%
							}
					%>
							<td width="100">&nbsp;</td><td width="100">&nbsp;</td>
							</tr>
					<%
						}
					%>
					
					</table>
				</table>
				<BR>
					<table width="100%" id="quality" border="1" align="center"
					cellpadding="0px" cellspacing="0" bordercolor="#333333"
					style="border-collapse:collapse;">

					</table>
			<table width="100%" border="0" align="center" cellpadding="0px"
					cellspacing="0" bordercolor="#333333"
					style="border-collapse:collapse;">
					
					<tr height="25px">
						<td width="15%" class="cd_bt">
							�ֿ⾭���ˣ�
						</td>
						<td width="10%" class="cd_list1">
							<input name="Agency" type="text" size="15"
								value="<c:out value="${operateObj.agency}"/>" class="readonly"
								reqfv="required;�ֿ⾭����" readOnly="true">
						</td>
						<td width="15%" class="cd_bt">
							�ֿ⸺���ˣ�
						</td>
						<td width="10%" class="cd_list1">
							<input name="Superinrtendent" type="text" size="15"
								value="<c:out value="${operateObj.responsibleman}"/>" class="readonly"
								reqfv="required;�ֿ⸺����" readOnly="true">
						</td>
						<td width="17%" class="cd_bt">
							�����̾����ˣ�
						</td>
						<td width="10%" class="cd_list1">
							<input name="Dealer_Agency" type="text" size="15"
								value="<c:out value="${operateObj.dealerAgency}"/>" class="readonly"
								reqfv="required;�����̾�����" readOnly="true">
						</td>
					</tr>
				</table>
				
				<BR>
					<table width="100%" id="quality" height="50" border="0" align="center"
					cellpadding="0px" cellspacing="0" bordercolor="#333333"
					style="border-collapse:collapse;">
						<tr>
							<td align="center" class="cd_hr">
								����ϸ��
							</td>
						</tr>
					</table>
			<table width="100%" border="0" align="center" cellpadding="0px"
					cellspacing="0" bordercolor="#333333"
					style="border-collapse:collapse;">
					
					<tr height="25px">
						<td width="15%" class="cd_bt">
							�ִ�������
						</td>
						<td width="10%" class="cd_list1">
							<input name="Agency" type="text" size="15"
								value="<c:out value="${operateObj.existAmount}"/>" class="readonly"
								reqfv="required;�ֿ⾭����" readOnly="true">
						</td>
						<td width="15%" class="cd_bt">
							����������
						</td>
						<td width="10%" class="cd_list1">
							<input name="Superinrtendent" type="text" size="15"
								value="<c:out value="${operateObj.frozenAmount}"/>" class="readonly"
								reqfv="required;�ֿ⸺����" readOnly="true">
						</td>
						<td width="17%" class="cd_bt">
							����������
						</td>
						<td width="10%" class="cd_list1">
							<input name="Dealer_Agency" type="text" size="15"
								value="<c:out value="${operateObj.existAmount - operateObj.frozenAmount}"/>" class="readonly"
								reqfv="required;�����̾�����" readOnly="true">
						</td>
					</tr>
					<tr>
					<td>
					&nbsp;
					</td>
					</tr>
					<!-- <tr height="25px">
						<td width="17%" class="cd_bt">
							�ִ������
						</td>
						<td width="10%" class="cd_list1">
							<input id="nowQuantity" name="nowQuantity" type="text" size="15"
								value="" class="readonly"
								reqfv="required;�����̾�����" readOnly="true">
						</td>
					</tr> -->
				</table>
				<%@ include file= "../../public/approveOrOverrule.jsp" %>
		</fieldset>
        </form>
</body>
</html>