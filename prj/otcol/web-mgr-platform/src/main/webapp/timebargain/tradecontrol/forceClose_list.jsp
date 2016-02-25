<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<html>
<head>
		<LINK REL="stylesheet" type="text/css"
			href="<c:url value="/timebargain/styles/common.css"/>" />
<%@ include file="/timebargain/common/ecside_head.jsp" %>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/open.js"/>"></script>
<title>
default
</title>
<script type="text/javascript">
<!--12
function row_onclick(firmID,commodityID,bs_Flag,holdQty,evenPrice)
{

    pTop("<c:url value="/timebargain/tradecontrol/forceClose_list_qp.jsp?CommodityID="/>" + commodityID + 
    "&BS_Flag=" + bs_Flag + "&HoldQty=" + holdQty + "&EvenPrice=" + evenPrice + "&FirmID=" + firmID,600,600);
}
//logoff
function logoff_onclick()
{
	parent.HiddFrame.location.href = "<c:url value="/timebargain/order/orders.do?funcflg=logoff&mkName=forceClose"/>";
}

function window_close(){
	window.close();
}
// -->
</script>
</head>
<body>
<fieldset class="pickList" >
<legend class="common"><b>查询条件</b></legend>
<form action="${pageContext.request.contextPath}/timebargain/tradecontrol/tradeCtl.do?funcflg=searchForceClose"
						method="POST" >
<table border="0" width="100%" cellpadding="0" cellspacing="0" class="common" align="center">
<tr>
	<td style="text-align:right;">
		交易节：
	</td>
	<td>
		<select name="sectionId">
			<option value="">全部</option>
			<c:forEach items="${tradeTimeList}" var="tradeTime">
			<c:if test="${sectionId !=null && sectionId == tradeTime.sectionId}">
				<option value="${tradeTime.sectionId}" selected="selected">${tradeTime.name}</option>
			</c:if>
			<c:if test="${sectionId ==null || sectionId != tradeTime.sectionId}">
				<option value="${tradeTime.sectionId}" >${tradeTime.name}</option>
			</c:if>
			</c:forEach>
		</select>
	</td>
	<td aling="left"><input type="submit" value="查询"/></td>
	</tr>
</table>
</form>
</fieldset>
<table width="100%">
  <tr><td>
	<ec:table items="forceCloseList" var="forceClose" 
			action="${pageContext.request.contextPath}/timebargain/tradecontrol/tradeCtl.do?funcflg=searchForceClose"	
			xlsFileName="ForceCloseList.xls" 
			csvFileName="ForceCloseList.csv"
			showPrint="true" 
			listWidth="100%"		
			rowsDisplayed="50"	 
			minHeight="300" 	
			title=""
	>
		<ec:row ondblclick="javascript:row_onclick('${forceClose.firmID}','${forceClose.commodityID}','${forceClose.BS_Flag}','${forceClose.HoldQty}','${forceClose.EvenPrice}');" style="cursor:hand">
		    <ec:column property="FirmID" title="交易商代码" width="10%" style="text-align:center;">  
		    	<c:choose>
					<c:when test="${forceClose.leftoverPrice < 0 && forceClose.usefulFunds < 0}">
						<font color="red">
							<c:out value="${forceClose.FirmID}"/>
						</font>
					</c:when>
					<c:when test="${forceClose.leftoverPrice < 0 && forceClose.usefulFunds > 0}">
						<font color="blue">
							<c:out value="${forceClose.FirmID}"/>
						</font>
					</c:when>
					<c:otherwise>
						<c:out value="${forceClose.FirmID}"/>
					</c:otherwise>
				</c:choose>
		    </ec:column>
		    <ec:column property="name" title="交易商名称" width="10%" style="text-align:center;">  
		    	<c:choose>
					<c:when test="${forceClose.leftoverPrice < 0 && forceClose.usefulFunds < 0}">
						<font color="red">
							<c:out value="${forceClose.name}"/>
						</font>
					</c:when>
					<c:when test="${forceClose.leftoverPrice < 0 && forceClose.usefulFunds > 0}">
						<font color="blue">
							<c:out value="${forceClose.name}"/>
						</font>
					</c:when>
					<c:otherwise>
						<c:out value="${forceClose.name}"/>
					</c:otherwise>
				</c:choose>
		    </ec:column>
            <ec:column property="usefulFunds" title="可用资金"  width="10%" style="text-align:right;">
            	<c:choose>
					<c:when test="${forceClose.leftoverPrice < 0 && forceClose.usefulFunds < 0}">
						<font color="red">
							<c:out value="${forceClose.usefulFunds}"/>
						</font>
					</c:when>
					<c:when test="${forceClose.leftoverPrice < 0 && forceClose.usefulFunds > 0}">
						<font color="blue">
							<c:out value="${forceClose.usefulFunds}"/>
						</font>
					</c:when>
					<c:otherwise>
						<c:out value="${forceClose.usefulFunds}"/>
					</c:otherwise>
				</c:choose>
            </ec:column>
            <ec:column property="leftoverPrice" title="上日余额" width="15%" style="text-align:right;">
				<c:choose>
					<c:when test="${forceClose.leftoverPrice < 0 && forceClose.usefulFunds < 0}">
						<font color="red">
							<c:out value="${forceClose.leftoverPrice}"/>
						</font>
					</c:when>
					<c:when test="${forceClose.leftoverPrice < 0 && forceClose.usefulFunds > 0}">
						<font color="blue">
							<c:out value="${forceClose.leftoverPrice}"/>
						</font>
					</c:when>
					<c:otherwise>
						<c:out value="${forceClose.leftoverPrice}"/>
					</c:otherwise>
				</c:choose>
			</ec:column>
            <ec:column property="MinClearDeposit" title="最低结算准备金" width="10%" style="text-align:right;"> 
            	<c:choose>
					<c:when test="${forceClose.leftoverPrice < 0 && forceClose.usefulFunds < 0}">
						<font color="red">
							<c:out value="${forceClose.MinClearDeposit}"/>
						</font>
					</c:when>
					<c:when test="${forceClose.leftoverPrice < 0 && forceClose.usefulFunds > 0}">
						<font color="blue">
							<c:out value="${forceClose.MinClearDeposit}"/>
						</font>
					</c:when>
					<c:otherwise>
						<c:out value="${forceClose.MinClearDeposit}"/>
					</c:otherwise>
				</c:choose>
            </ec:column>
            <ec:column property="CommodityID" title="商品代码" width="10%" style="text-align:center;">
            	<c:choose>
					<c:when test="${forceClose.leftoverPrice < 0 && forceClose.usefulFunds < 0}">
						<font color="red">
							<c:out value="${forceClose.CommodityID}"/>
						</font>
					</c:when>
					<c:when test="${forceClose.leftoverPrice < 0 && forceClose.usefulFunds > 0}">
						<font color="blue">
							<c:out value="${forceClose.CommodityID}"/>
						</font>
					</c:when>
					<c:otherwise>
						<c:out value="${forceClose.CommodityID}"/>
					</c:otherwise>
				</c:choose>
            </ec:column>
			<ec:column property="BS_Flag" title="订货类型" editTemplate="ecs_t_status" mappingItem="BS_FLAG" width="10%" style="text-align:center;">
				<c:choose>
					<c:when test="${forceClose.leftoverPrice < 0 && forceClose.usefulFunds < 0}">
						<c:choose>
							<c:when test="${forceClose.BS_Flag == 1}">
								<font color="red"><c:out value="买"/></font>
							</c:when>
							<c:when test="${forceClose.BS_Flag == 2}">
								<font color="red"><c:out value="卖"/></font>
							</c:when>
						</c:choose>
						
					</c:when>
					<c:when test="${forceClose.leftoverPrice < 0 && forceClose.usefulFunds > 0}">
						<c:choose>
							<c:when test="${forceClose.BS_Flag == 1}">
								<font color="blue"><c:out value="买"/></font>
							</c:when>
							<c:when test="${forceClose.BS_Flag == 2}">
								<font color="blue"><c:out value="卖"/></font>
							</c:when>
						</c:choose>
						
					</c:when>
					<c:otherwise>
						<c:choose>
							<c:when test="${forceClose.BS_Flag == 1}">
								<c:out value="买"/>
							</c:when>
							<c:when test="${forceClose.BS_Flag == 2}">
								<c:out value="卖"/>
							</c:when>
						</c:choose>
					</c:otherwise>
				</c:choose>
			</ec:column>
			<ec:column property="HoldQty" title="订货数量"  format="quantity" width="5%" style="text-align:right;">	
				<c:choose>
					<c:when test="${forceClose.leftoverPrice < 0 && forceClose.usefulFunds < 0}">
						<font color="red">
							<c:out value="${forceClose.HoldQty}"/>
						</font>
					</c:when>
					<c:when test="${forceClose.leftoverPrice < 0 && forceClose.usefulFunds > 0}">
						<font color="blue">
							<c:out value="${forceClose.HoldQty}"/>
						</font>
					</c:when>
					<c:otherwise>
						<c:out value="${forceClose.HoldQty}"/>
					</c:otherwise>
				</c:choose>
			</ec:column>
			<ec:column property="EvenPrice" title="订货均价" cell="currency" width="10%" style="text-align:right;">
				<c:choose>
					<c:when test="${forceClose.leftoverPrice < 0 && forceClose.usefulFunds < 0}">
						<font color="red">
							<c:out value="${forceClose.EvenPrice}"/>
						</font>
					</c:when>
					<c:when test="${forceClose.leftoverPrice < 0 && forceClose.usefulFunds > 0}">
						<font color="blue">
							<c:out value="${forceClose.EvenPrice}"/>
						</font>
					</c:when>
					<c:otherwise>
						<c:out value="${forceClose.EvenPrice}"/>
					</c:otherwise>
				</c:choose>
			</ec:column>
			<ec:column property="floatingloss" title="订货盈亏" cell="currency" width="15%" style="text-align:right;">
				<c:choose>
					<c:when test="${forceClose.leftoverPrice < 0 && forceClose.usefulFunds < 0}">
						<font color="red">
							<c:out value="${forceClose.floatingloss}"/>
						</font>
					</c:when>
					<c:when test="${forceClose.leftoverPrice < 0 && forceClose.usefulFunds > 0}">
						<font color="blue">
							<c:out value="${forceClose.floatingloss}"/>
						</font>
					</c:when>
					<c:otherwise>
						<c:out value="${forceClose.floatingloss}"/>
					</c:otherwise>
				</c:choose>
			</ec:column>
			
		</ec:row>
		<ec:extend>
	       <c:if test="${not empty sessionID}">
			  <a href="#" onclick="logoff_onclick()">注销登录</a>
			  </c:if>	
	    </ec:extend>	
	</ec:table>
		
</td></tr>
</table>	
	<!-- 编辑和过滤所使用的 通用的文本框模板 -->
	<textarea id="ecs_t_input" rows="" cols="" style="display:none">
		<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" 
		 style="width:100%;" name="" />
	</textarea>
	<!-- 编辑状态所用模板 -->
	<textarea id="ecs_t_status" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="status" >
			<ec:options items="BS_FLAG" />
		</select>
	</textarea>	
	
<div class="req" id="memo">
说明：双击要平仓的行进入强平页面
</div>
<%@ include file="/timebargain/common/messages.jsp" %>
</body>

<script type="text/javascript">
<!--
  

  
// -->
</script>
</html>
