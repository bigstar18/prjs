<%@ page pageEncoding="GBK"%>
<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page import='java.util.Date'%>
<%@ page import='gnnt.MEBS.base.query.*'%>
<%
   String memberPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/member";
   
   String nowDate = Utils.formatDate("yyyy-MM-dd",new Date());
   String nowTime = Utils.formatDate("yyyyMMdd HHmm",new Date());
   pageContext.setAttribute("nowDate",nowDate);
   pageContext.setAttribute("nowTime",nowTime);
%>

<html xmlns:MEBS>
	<head>
	  <title>手续费明细(竞价)</title>
		<%@ include file="/timebargain/common/ecside_head.jsp"%>
		<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
		 
		<import namespace="MEBS" implementation="<%=memberPath%>/public/calendar.htc">
		
		<script type="text/javascript">
		//提交设置
		function submitForm(){
			var startDate = document.getElementById("scleardate").value;
			var endDate = document.getElementById("ecleardate").value;
			

		  if(startDate != "" && endDate != "" || startDate == "" && endDate != "" || startDate != "" && endDate == "")
		  {
			if(startDate == ""){
				alert("起始日期不能为空！");
				frm_query.scleardate.focus();
				return false;
				
			}
			if(endDate == ""){
				alert("结束日期不能为空！");
				frm_query.ecleardate.focus();
				return false;
				
			}
			if(!isDateFomat(startDate))
		    {
		        alert("起始日期格式不正确！\n如：" + '<%=nowDate%>');
		        frm_query.scleardate.value = "";
		        frm_query.scleardate.focus();
		        return false;
		    }
		    if(!isDateFomat(endDate))
		    {
		        alert("结束日期格式不正确！\n如：" + '<%=nowDate%>');
		        frm_query.ecleardate.value = "";
		        frm_query.ecleardate.focus();
		        return false;
		    }
		  
		    if ( startDate > '<%=nowDate%>' ) { 
		        alert("起始日期不能大于当天日期!"); 
		        frm_query.scleardate.focus();
		        return false; 
		    } 
		    if ( startDate > endDate ) { 
		        alert("起始日期不能大于结束日期!"); 
		        return false; 
		    } 
		  }  
			  frm_query.submit();
			
		}
		//重置设置
		function resetForm(){
			document.getElementById("firmId").value="";
			document.getElementById("brokerId").value="";
			frm_query.bsFlag.value="";
			document.getElementById("scleardate").value="";
			document.getElementById("ecleardate").value="";
			document.getElementById("bsFlag").value = "";
			document.getElementById("scleardate").value = "";
			document.getElementById("ecleardate").value = "";	
       }
		</script>
	</head>
	<body leftmargin="2" topmargin="0">
	
		<table width="100%">
		    <tr>
				<td>
					<form name="frm_query" action="${pageContext.request.contextPath}/member/feeDetailController.mem?funcflg=hisDealDetailVdList"
						method="POST" styleClass="form" >
						<fieldset class="pickList" >
							<legend class="common">
								<b>查询条件</b>
							</legend>
							<table border="0" cellspacing="0" cellpadding="0" width="100%">
						<tr height="35" width="100%">
							<td align="right">加盟商编号：</td>
							<td align="left">
								<input type="text" size="10" name="_brokerId[like]" id="brokerId" value="<c:out value='${oldParams["brokerId[like]"]}'/>" 
onkeypress="onlyNumberAndCharInput()" maxlength="16">
							</td>
						
							<td align="right">交易商代码：</td>
							<td align="left">
								<input type="text" size="10" name="_firmId[like]" id="firmId" value="<c:out value='${oldParams["firmId[like]"]}'/>" 
onkeypress="onlyNumberAndCharInput()" maxlength="16">
							</td>
							<td align="right">成交类型：</td>
							<td align="left">
							<select id="bsFlag" name="_bsFlag[=]">
								<option value="">全部</option>
								<option value="1">买成交</option>
								<option value="2">卖成交</option>
							</select>
							<script>
								frm_query.bsFlag.value = "<c:out value='${oldParams["bsFlag[=]"]}'/>";
							</script>
							</td>
							<td align="right">结算日期：</td>
							<td align="left" colspan="6">
								<MEBS:calendar eltName="_trunc(cleardate)[>=][date]" eltCSS="date" eltID="scleardate" eltImgPath="<%=skinPath%>/images/" eltStyle="width:80px" eltValue="<c:out value='${oldParams["trunc(cleardate)[>=][date]"]}'/>"/>
							&nbsp;至&nbsp;
							    <MEBS:calendar eltName="_trunc(cleardate)[<=][date]" eltCSS="date" eltID="ecleardate" eltImgPath="<%=skinPath%>/images/" eltStyle="width:80px" eltValue="<c:out value='${oldParams["trunc(cleardate)[<=][date]"]}'/>"/>
							</td>
							<td align="right">
								<input type="button" class="btn" value="查询" onclick="submitForm();">&nbsp;
								<input type="button" class="btn" value="重置" onclick="resetForm();">
							</td>
						</tr>
					</table>
						</fieldset>
					</form>
				</td>
			</tr>
			
			<tr>
				<td>
					<ec:table items="resultList" var="result"
						action="${pageContext.request.contextPath}/member/feeDetailController.mem?funcflg=hisDealDetailVdList"
						xlsFileName="手续费明细(竞价)${nowTime}.xls"
					    showPrint="false"
						rowsDisplayed="20" listWidth="100%" 
						minHeight="300"
						retrieveRowsCallback="limit"
						>
						<ec:row>
						  <ec:column property="brokerId" title="加盟商编号"  width="10%" style="text-align:center;" />
						  <ec:column property="firmId" title="交易商代码"  width="10%" style="text-align:center;" />
						  <ec:column property="clearDate" title="结算日期"  width="10%" style="text-align:center;" >
						     <fmt:formatDate value="${result.clearDate}" pattern="yyyy-MM-dd"/>
						  </ec:column>
						  <ec:column property="tradeNo" title="成交号"  width="10%" style="text-align:center;" />
						  <ec:column property="bsFlag" title="成交类型"  width="10%" style="text-align:right;" >						   
						    <c:if test="${result.bsFlag==1}">
								<c:out value="买成交"/>
							</c:if>
							<c:if test="${result.bsFlag==2}">
								<c:out value="卖成交"/>
							</c:if>
						  </ec:column>
						  <ec:column property="quantity" title="成交量"  width="10%" style="text-align:right;" calcTitle="合计" format="#,###" calc="total">
						    <fmt:formatNumber value="${result.quantity}" pattern="#,###"/>
						  </ec:column>
						  <ec:column property="tradeFee" title="交易手续费(元)"  width="10%" style="text-align:right;" format="#,##0.00" calc="total">
						    <fmt:formatNumber value="${result.tradeFee}" pattern="#,##0.00"/>
						  </ec:column>
						
						</ec:row>
					</ec:table>
				</td>
			</tr>
		</table>
		
<%@ include file="/timebargain/common/messages.jsp" %>
</body>
</html>
