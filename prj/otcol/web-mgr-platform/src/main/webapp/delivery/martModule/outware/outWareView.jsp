<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../public/session.jsp"%>
<html xmlns:MEBS>
  <head>
	<IMPORT namespace="MEBS" implementation="<%=basePath%>public/jstools/calendar.htc">
	<title><%=TITLE%></title>
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
	-->
    </style>
</head>
<c:if test="${overruleMsg!=null }">
			<script type="text/javascript">
				alert("<c:out value='${overruleMsg }'/>");
			</script>
	</c:if>
<body>
        <form action="" name="frm" method="POST" targetType="hidden">
		<input type="hidden" name="id" value="${operateObj.id}"/>
		<input type="hidden" name="firmID" value="${operateObj.firmId}"/>
		<input type="hidden" name="warehouseID" value="${operateObj.warehouseId}"/>
		<input type="hidden" name="commodityID" value="${operateObj.commodityId}"/>
		<input type="hidden" name="ability" value="${operateObj.ability}"/>
		<fieldset width="100%">
		<legend>��Ʒ���ⵥ<font color=red>��<c:out value="${status.name}"/>��</font></legend>
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
						<td  width="11%" class="cd_bt">
							���ⵥ�ţ�<c:out value="${operateObj.id}"/>
						</td>
						
						<td colspan="3" width="60%">
							&nbsp;
						</td>
					</tr>
				</table>
				<table id="tableList" width="100%" border="1" align="center"
					cellpadding="0px" cellspacing="0" bordercolor="#333333"
					style="border-collapse:collapse;">

					<tr height="25px">
						<td width="25%" class="cd_bt">
							${FIRMID}��
						</td>
						<td width="25%" class="cd_list1">
								<c:out value="${operateObj.firmId}"/>
						</td>
						<td width="25%" class="cd_bt">
							${FIRMNAME}��
						</td>
						<td width="25%"  class="cd_list1">
							${dealer.name}
						</td>
						
					</tr>
					<tr height="25px">
						<td width="15%" class="cd_bt">
							�ֿ���룺
						</td>
						<td width="15%" class="cd_list1">
							${operateObj.warehouseId}
						</td>
						<td class="cd_bt">
							�ֿ����ƣ�
						</td>
						<td  class="cd_list1">
							${warehouse.name}
						</td>
						
					</tr>
					<tr height="25px">
						<td width="15%" class="cd_bt">
							Ʒ�����ƣ�
						</td>
						<td width="15%" class="cd_list1" >
							${commodity.name}
						</td>
						<!-- <td width="15%" class="cd_bt">
							��������
						</td>
						<td width="15%" class="cd_list1" >
							<fmt:formatDate value="${operateObj.createDate}" pattern="yyyy-MM-dd"/>
						</td> -->
						<td class="cd_bt">
							����������
						</td>
						<td class="cd_list1">
							${operateObj.weight}
						</td>
					</tr>
					<tr height="25px">
							<!-- <td class="cd_bt">��&nbsp;&nbsp;&nbsp;&nbsp;��	</td>
							<td class="cd_list1" >
								${operateObj.quantity}
							</td> -->
							<td class="cd_bt">�����ʱ�䣺</td>
							<td class="cd_list1" >
								
								<fmt:formatDate value="${operateObj.planOutDate}" pattern="yyyy-MM-dd"/>
							</td>
							<td class="cd_bt">��ϵ�绰��	</td>
							<td class="cd_list1" >
								${operateObj.tel}
							</td>
					</tr>
					
					</table>
			<br>
					<table width="100%" border="0" align="center" cellpadding="0px"
						cellspacing="0" bordercolor="#333333"
						style="border-collapse:collapse;" >
						<tr height="25px">
							<td width="15%" class="cd_bt">
								�ֿ⾭���ˣ�
							</td>
							<td width="15%" class="cd_list1">
								<input name="Agency" type="text" size="15"
									value="${operateObj.agency}" class="readonly"
									reqfv="required;�ֿ⾭����" readonly="true">
							</td>
							<td width="15%" class="cd_bt">
								�ֿ⸺���ˣ�
							</td>
							<td width="15%" class="cd_list1">
								<input name="Superinrtendent" type="text" size="15"
									value="${operateObj.responsibleman}" class="readonly"
									reqfv="required;�ֿ⸺����" readonly="true">
							</td>
							<td width="15%" class="cd_bt">
								�����̾����ˣ�
							</td>
							<td width="15%" class="cd_list1">
								<input name="Dealer_Agency" type="text" size="15"
									value="${operateObj.dealerAgency}" class="readonly"
									reqfv="required;�����̾�����" readonly="true">
							</td>
						</tr>
					</table>
					<%@ include file= "../../public/approveOrOverrule.jsp" %>
			<br>
		</fieldset>
        </form>
</body>
</html>
<script>

</script>
