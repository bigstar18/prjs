<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../../public/session.jsp"%>
<%@ page import="gnnt.MEBS.delivery.model.workflow.*"%>
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
	-->
    </style>
</head>
<body>
        <form id="formNew" name="frm" method="POST" targetType="hidden">
        <input type="hidden" name="regStockId" value="${regStock.regStockId}"/>
		<input type="hidden" name="firmId" value="${regStock.firmId}"/>
		<input type="hidden" name="warehouseId" value="${regStock.warehouseId}"/>
		<input type="hidden" name="breedId" value="${regStock.breedId}"/>
		<fieldset width="100%">
		<legend>
			注册仓单
			<font color="red">
				<c:choose>
					<c:when test="${regStock.type==0 }">(标准仓单)</c:when>
					<c:when test="${regStock.type==1 }">(非标准仓单)</c:when>
					<c:when test="${regStock.type==2 }">(临时仓单)</c:when>
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
							注册单号：${regStock.regStockId}
						</td>
						<td  width="25%"class="cd_list1">
						</td>
						<td  width="15%" class="cd_bt">
							注册时间：
						</td>
						<td  width="25%" class="cd_list1">
							<fmt:formatDate value="${regStock.createTime}" pattern="yyyy-MM-dd"/>
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
							${regStock.firmId}
						</td>
						<td class="cd_bt">
							${FIRMNAME}：
						</td>
						<td  class="cd_list1" >
							${dealer.name}
						</td>
					</tr>
					<c:choose>
						<c:when test="${regStock.type==0 }">
							<tr height="25px">
								<td width="20%" class="cd_bt">仓库名称：</td>
								<td width="30%" class="cd_list1">${house.name}</td>
								<td width="" class="cd_bt">品种名称：</td>
								<td width="" class="cd_list1" >${commodity.name}</td>
							</tr>
							<tr height="25px">
								<td width="20%" class="cd_bt">入库单号：</td>
								<td width="30%"class="cd_list1">${regStock.stockId}</td>
								<td width="" class="cd_bt">单件数量：</td>
								<td width=""  class="cd_list1">${regStock.unitWeight}</td>
							</tr>
						</c:when>
						<c:otherwise>
							<tr height="25px">
								<td width="" class="cd_bt">品种名称：</td>
								<td width="" class="cd_list1" >${commodity.name}</td>
								<td width="" class="cd_bt">单件数量：</td>
								<td width=""  class="cd_list1">${regStock.unitWeight}</td>
							</tr>
						</c:otherwise>
					</c:choose>
					<tr height="25px">
						<td width="" class="cd_bt">
								修改时间：
						</td>
						<td width=""  class="cd_list1">
							<fmt:formatDate value="${regStock.modifyTime}" pattern="yyyy-MM-dd"/>
						</td>
					</tr>
			</table>
			<br>
			<c:if test="${regStock.type==0 }">
			<table width="100%" id="quality" height="50" border="0" align="center" cellpadding="0px" cellspacing="0" bordercolor="#333333" style="border-collapse:collapse;">
				<tr><td align="center" class="cd_hr">质量标准：</td></tr>
			</table>
			<table width="100%" border="1" align="center" cellpadding="0px" cellspacing="0" bordercolor="#333333" style="border-collapse:collapse;">
			<%  EnterWare enterware =  (EnterWare)request.getAttribute("enterware");
				java.util.List<KeyValue> list = enterware.getQualityStandardList();
				double num = Math.floor(list.size()/4);
				int count = list.size()%4;
				KeyValue ky = null;
				int i=0,j=0;
				for(i=0;i<num;i++){
			 %>
				<tr height="25px">
				<%  for(j=0;j<4;j++){
					ky=list.get(i*4+j);
				%>	
					<td class="cd_bt"><%=ky.getKey()%></td>
					<td class="cd_list1"><%=ky.getValue()%></td>
				<%  } %>	
				</tr>
			<%}%> 
			<%  int c = i*4-1;
				if(i==0)
					c=-1;
				if(count==1){
					ky=list.get(c+1);
			%>
				<tr height="25px">
					<td class="cd_bt"><%=ky.getKey()%></td>
					<td class="cd_list1"><%=ky.getValue()%></td>
				<% 	for(int m=0;m<3;m++){%>
					<td class="cd_bt">&nbsp;</td>
					<td class="cd_list1">&nbsp;</td>
			<%      }
				} else if(count==2){ 
					ky=list.get(c+1);
			%>
				</tr>
				<tr height="25px">
					<td class="cd_bt"><%=ky.getKey()%></td>
					<td class="cd_list1"><%=ky.getValue()%></td>
			        <%ky=list.get(c+2); %>
					<td class="cd_bt"><%=ky.getKey()%></td>
					<td class="cd_list1"><%=ky.getValue()%></td>
				<% 	for(int m=0;m<2;m++){%>
					<td class="cd_bt">&nbsp;</td>
					<td class="cd_list1">&nbsp;</td>
			<%      }%>
			<%  } else if(count==3){  
					ky=list.get(c+1);
			%>
				</tr>
				<tr height="25px">
					<td class="cd_bt"><%=ky.getKey()%></td>
					<td class="cd_list1"><%=ky.getValue()%></td>
					<%ky=list.get(c+2); %>
					<td class="cd_bt"><%=ky.getKey()%></td>
					<td class="cd_list1"><%=ky.getValue()%></td>
					<%ky=list.get(c+3); %>
					<td class="cd_bt"><%=ky.getKey()%></td>
					<td class="cd_list1"><%=ky.getValue()%></td>
					<td class="cd_bt">&nbsp;</td>
					<td class="cd_list1">&nbsp;</td>
			<%} %>
				</tr>
			</table>
				</c:if>
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
								value="<c:out value="${regStock.weight}"/>" class="readonly"
								reqfv="required;仓库经办人" readOnly="true">
						</td>
						<td width="15%" class="cd_bt">
							冻结重量：
						</td>
						<td width="10%" class="cd_list1">
							<input name="Superinrtendent" type="text" size="15"
								value="<c:out value="${regStock.frozenWeight}"/>" class="readonly"
								reqfv="required;仓库负责人" readOnly="true">
						</td>
						<td width="17%" class="cd_bt">
							可用重量：
						</td>
						<td width="10%" class="cd_list1">
							<input name="Dealer_Agency" type="text" size="15"
								value="<c:out value="${availablenum }"/>" class="readonly"
								reqfv="required;交易商经办人" readOnly="true">
						</td>
					</tr>
				</table>
			<br>
			<div align="center">
			<%String flag=request.getParameter("flag");
			if((flag==null || flag.equals(""))) {%>
			  <button class="smlbtn" type="button" onClick="cancle()">返回</button>
			<%}else{
			%>
			<button class="smlbtn" type="button" onClick="javascript:window.close()">关闭</button>
			<%
			} %>
            </div>
		</fieldset>
        </form>
</body>
</html>

<SCRIPT LANGUAGE="JavaScript">
	function cancle(){
		frm.action = "<%=basePath%>servlet/regStockController.${POSTFIX}?funcflg=regStockReturn";
		frm.submit();
	}
	
</SCRIPT>