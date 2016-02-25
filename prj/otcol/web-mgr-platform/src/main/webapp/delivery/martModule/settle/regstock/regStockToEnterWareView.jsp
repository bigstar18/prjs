<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../../public/session.jsp"%>
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
	-->
    </style>
</head>
<body>
        <form id="frm" name="frm" method="POST" targetType="hidden">
        <input type="hidden" name="id" value="${operateObj.id}"/>
        <input type="hidden" name="regStockId" value="${operateObj.regStockId}"/>
		<input type="hidden" name="firmId" value="${operateObj.firmId}"/>
		<input type="hidden" name="warehouseId" value="${operateObj.warehouseId}"/>
		<input type="hidden" name="breedId" value="${operateObj.breedId}"/>
		
		<fieldset width="100%">
		<legend>
			注册仓单转入库单详情
			<font color="red">
				<c:choose>
					<c:when test="${operateObj.type==0 }">(标准仓单)</c:when>
					<c:when test="${operateObj.type==1 }">(非标准仓单)</c:when>
					<c:when test="${operateObj.type==2 }">(临时仓单)</c:when>
				</c:choose>
			</font>
		</legend>
					<table width="100%" border="0" align="center" cellpadding="0"
					cellspacing="0" bordercolor="#333333"
					style="border-collapse:collapse;">
					<tr height="40px">
						<td colspan="5" class="cd_hr">
							<br>
							&nbsp;<font color=red></font>
						</td>
					</tr>
				</table>
				<table width="100%" border="0" align="center" cellpadding="0px"
					cellspacing="0" bordercolor="#333333"
					style="border-collapse:collapse;">
										<tr height="30px">
						<td  width="15%"class="cd_bt">
							注册单号：${operateObj.regStockId}
						</td>
						<td  width="25%"class="cd_list1">
				
						</td>
						<td  width="15%" class="cd_bt">
							注册日期：
						</td>
						<td  width="25%" class="cd_list1">
							<fmt:formatDate value="${operateObj.createDate}" pattern="yyyy-MM-dd"/>
						</td>
					</tr>
				</table>
				<table id="tableList" width="100%" border="1" align="center"
					cellpadding="0px" cellspacing="0" bordercolor="#333333"
					style="border-collapse:collapse;">

					<tr height="25px">
						
						<td width="15%" class="cd_bt">
							${FIRMID}
						</td>
						<td width="25%" class="cd_list1">
							${operateObj.firmId}
						</td>
						<td class="cd_bt">
							${FIRMNAME}
						</td>
						<td  class="cd_list1" >
							${dealer.name}
						</td>
						
					</tr>
					<c:choose>
						<c:when test="${operateObj.type==0 }">
							<tr height="25px">
								<td width="20%" class="cd_bt">仓库名称</td>
								<td width="30%" class="cd_list1">${warehouse.name}</td>
								<td width="" class="cd_bt">品种名称</td>
								<td width="" class="cd_list1" >${commodity.name}</td>
							</tr>
							<tr height="25px">
								<td width="" class="cd_bt">转入数量</td>
						        <td width="" class="cd_list1">${operateObj.relTurnToWeight}</td>
						        <td width="" class="cd_bt">修改时间</td>
				       			 <td width=""  class="cd_list1">
									<fmt:formatDate value="${operateObj.modifyTime}" pattern="yyyy-MM-dd"/>
								</td>
							</tr>
						</c:when>
						<c:otherwise>
							<tr height="25px">
								<td width="" class="cd_bt">品种名称</td>
								<td width="" class="cd_list1" >${commodity.name}</td>
								<td width="" class="cd_bt">&nbsp;</td>
						        <td width="" class="cd_bt">&nbsp;</td>
							</tr>
						</c:otherwise>
					</c:choose>
					
					
			</table>
			<%@ include file= "../../../public/approveOrOverrule.jsp" %>
		</fieldset>
        </form>
</body>
</html>

<SCRIPT LANGUAGE="JavaScript">

	
</SCRIPT>