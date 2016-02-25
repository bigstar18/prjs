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
		<legend>商品入库单<font color=red>(<c:out value="${status.name}"/>)</font></legend>
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
							入库通知单号：${operateObj.enterInformId}
						</td>
						<td  width="25%"class="cd_list1">
				
						</td>
						<td  width="15%" class="cd_bt">
							入库时间：
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
							${FIRMID}：
						</td>
						<td width="25%" class="cd_list1">
							${operateObj.firmId}
						</td>
						<td class="cd_bt">
							${FIRMNAME}：
						</td>
						<td  class="cd_list1" colspan=3>
							${dealer.name}
						</td>
						
					</tr>
					<tr height="25px">
						<td width="20%" class="cd_bt">
							仓库名称：
						</td>
						<td width="30%" class="cd_list1">
							${warehouse.name}
						</td>
					
						<td width="" class="cd_bt">
							品种名称：
						</td>
					
						<td width="" class="cd_list1" colspan=3>
							${commodity.name}
						</td>
					</tr>
					<tr height="25px">
					
						
							<td width="20%" class="cd_bt">
							等级：
						</td>
						<td width="30%"class="cd_list1">
							${operateObj.grade}
						</td>
						
						<td width="" class="cd_bt">
								生产厂家或产地：
						</td>
						<td width="" colspan=3 class="cd_list1">
							${operateObj.origin}
						</td>
					</tr>
					<tr height="25px">
				<td width="" class="cd_bt">
								种类：
						</td>
						<td width=""  class="cd_list1">
							${operateObj.sort}
						</td>
						<td width="" class="cd_bt">
							批号：
						</td>
						<td width=" class="cd_list1" >
							${operateObj.lot}
						</td>
						<td width="" class="cd_bt">
							生产年月：
						</td>
						<td width="" class="cd_list1" >
							${operateObj.productionDate}
						</td>
					</tr>
					
				
						<tr height="25px">
							<td class="cd_bt">入库重量：</td>
							<td class="cd_list1" >
								<fmt:formatNumber value="${operateObj.weight }" pattern="#,##0.0000"/>
								<!-- 添加对单位为空的判断，如果单位为空。则不显示  add by zhaodc -->
				                <c:if test="${commodity.countType !=null}">(${commodity.countType })</c:if>
							</td> 
							<td class="cd_bt">件&nbsp;&nbsp;&nbsp;&nbsp;数：	</td>
							<td class="cd_list1" >
								${operateObj.quantity}
							</td>
							<td class="cd_bt" >货 位 号：</td>
							<td class="cd_list1">
								${operateObj.cargoNo}
							</td>
						</tr>

					<tr height="25px">
							<td class="cd_bt">每件重量：</td>
							<td class="cd_list1" >
								<fmt:formatNumber value="${operateObj.unitWeight }" pattern="#,##0.0000"/>
								<!-- 添加对单位为空的判断，如果单位为空。则不显示  add by zhaodc -->
				                <c:if test="${commodity.countType !=null}">(${commodity.countType })</c:if>
							</td>
							<td class="cd_bt">包装方式：	</td>
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
							质量标准：<font color=red></font>
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
							仓库经办人：
						</td>
						<td width="10%" class="cd_list1">
							<input name="Agency" type="text" size="15"
								value="<c:out value="${operateObj.agency}"/>" class="readonly"
								reqfv="required;仓库经办人" readOnly="true">
						</td>
						<td width="15%" class="cd_bt">
							仓库负责人：
						</td>
						<td width="10%" class="cd_list1">
							<input name="Superinrtendent" type="text" size="15"
								value="<c:out value="${operateObj.responsibleman}"/>" class="readonly"
								reqfv="required;仓库负责人" readOnly="true">
						</td>
						<td width="17%" class="cd_bt">
							交易商经办人：
						</td>
						<td width="10%" class="cd_list1">
							<input name="Dealer_Agency" type="text" size="15"
								value="<c:out value="${operateObj.dealerAgency}"/>" class="readonly"
								reqfv="required;交易商经办人" readOnly="true">
						</td>
					</tr>
				</table>
				
				<BR>
					<table width="100%" id="quality" height="50" border="0" align="center"
					cellpadding="0px" cellspacing="0" bordercolor="#333333"
					style="border-collapse:collapse;">
						<tr>
							<td align="center" class="cd_hr">
								重量细则：
							</td>
						</tr>
					</table>
			<table width="100%" border="0" align="center" cellpadding="0px"
					cellspacing="0" bordercolor="#333333"
					style="border-collapse:collapse;">
					
					<tr height="25px">
						<td width="15%" class="cd_bt">
							现存重量：
						</td>
						<td width="10%" class="cd_list1">
							<input name="Agency" type="text" size="15"
								value="<c:out value="${operateObj.existAmount}"/>" class="readonly"
								reqfv="required;仓库经办人" readOnly="true">
						</td>
						<td width="15%" class="cd_bt">
							冻结重量：
						</td>
						<td width="10%" class="cd_list1">
							<input name="Superinrtendent" type="text" size="15"
								value="<c:out value="${operateObj.frozenAmount}"/>" class="readonly"
								reqfv="required;仓库负责人" readOnly="true">
						</td>
						<td width="17%" class="cd_bt">
							可用重量：
						</td>
						<td width="10%" class="cd_list1">
							<input name="Dealer_Agency" type="text" size="15"
								value="<c:out value="${operateObj.existAmount - operateObj.frozenAmount}"/>" class="readonly"
								reqfv="required;交易商经办人" readOnly="true">
						</td>
					</tr>
					<tr>
					<td>
					&nbsp;
					</td>
					</tr>
					<!-- <tr height="25px">
						<td width="17%" class="cd_bt">
							现存件数：
						</td>
						<td width="10%" class="cd_list1">
							<input id="nowQuantity" name="nowQuantity" type="text" size="15"
								value="" class="readonly"
								reqfv="required;交易商经办人" readOnly="true">
						</td>
					</tr> -->
				</table>
				<%@ include file= "../../public/approveOrOverrule.jsp" %>
		</fieldset>
        </form>
</body>
</html>