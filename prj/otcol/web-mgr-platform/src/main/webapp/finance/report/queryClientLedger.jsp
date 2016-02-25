<%@ page contentType="text/html;charset=GBK" %>
<%@ page import='java.util.Map' %>
<%@ page import='java.math.BigDecimal' %>

<html xmlns:MEBS>
  <head>
    <%@ include file="../public/headInc.jsp" %>
    <IMPORT namespace="MEBS" implementation="<%=basePath%>/public/jstools/calendar.htc">
    <title>交易商总帐表</title>
    <script language="javascript" src="<%=basePath%>/public/jstools/tools.js"></script>
	<script language="javascript" src="<%=basePath%>/public/jstools/common.js"></script>
	<script language="JavaScript"> 
		function init(){
			  changeOrder(sign);
		}
		function doQuery(){
			if (frm_query.beginDate.value>frm_query.endDate.value) {
				alert("开始日期不能大于结束日期！");
				return;
			}
			frm_query.type.value="";
			frm_query.submit();
		}
		function resetForm(){
			frm_query.beginDate.value = "";
			frm_query.endDate.value = "";
			frm_query.firmId.value = "";
		}
		
		//点击菜单中的板块栏做查询时
		function clickMenu(v){
			frm_query.partitionID.value=v;
			frm_query.idx.value=v;
			frm_query.action="<%=basePath%>/reportController.spr?funcflg=queryClientLedger&type="+v;
		    frm_query.submit();	
		}
		
	</script>
  </head> 
  
  <body onload="init();">
   <form id="frm_query" action="<%=basePath%>/reportController.spr?funcflg=queryClientLedger" method="post">
    <c:set var="defaultSec" value="${param.partitionID}"/>
	  <c:if test="${empty param.partitionID}">
	  <c:set var="defaultSec" value=""/>
      </c:if>
     <jsp:include page="../public/menu.jsp">
	  	<jsp:param name="partitionID" value="${defaultSec}"/>
	  	<jsp:param name="idx" value="${defaultSec}"/>
	  </jsp:include>
	  <c:if test="${not empty param.partitionID}">
	  <%
    List list=(List)request.getAttribute("list");
    pageContext.setAttribute("filed", list);
    int size=list.size();
    List resultList=(List)request.getAttribute("resultList");
    List fileds=(List)request.getAttribute("fileds");
    int size1=(size*80);
    String width=size1+"";
 %>
  	   
  		<input type="hidden" name="orderField" value="${orderFiled}">
		<input type="hidden" name="orderDesc" value="${orderType}">
		<input type="hidden" name="pageSize" value="<c:out value="${pageInfo.pageSize}"/>">
		<input type="hidden" id="pageNo" name="pageNo">
		<input type="hidden" id="type" name="type">
		<fieldset width="95%">
			<legend>客户总帐表查询</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="80%" height="35">
				<tr height="35">
					<td align="right">开始日期：&nbsp;</td>
					<td align="left">
						<MEBS:calendar eltID="beginDate" eltName="_b_date[>=][date]" eltCSS="date" eltStyle="width:80px" eltImgPath="<%=skinPath%>/images/" eltValue="<c:out value='${oldParams["b_date[>=][date]"]}'/>"/>
					</td>
					<td align="right">结束日期：&nbsp;</td>
					<td align="left">
						<MEBS:calendar eltID="endDate" eltName="_b_date[<=][date]" eltCSS="date" eltStyle="width:80px" eltImgPath="<%=skinPath%>/images/" eltValue="<c:out value='${oldParams["b_date[<=][date]"]}'/>"/>
					</td>
					<td align="right">交易商代码：&nbsp;</td>
					<td align="left">
						<input id="firmId" name="_firmId[like]" value="<c:out value='${oldParams["firmId[like]"]}'/>" type=text class="text" style="width: 60px" onkeypress="onlyNumberAndCharInput()" maxlength="16">
					</td>
					<td align="left">
						<button type="button" class="smlbtn" onclick="doQuery();">查询</button>&nbsp;
						<!-- add by yangpei 2011-11-22 增加重置功能 -->
		            	<button type="button" class="smlbtn" onclick="resetForm()">重置</button>&nbsp;
		            	<script>
		            		function resetForm(){
		            			frm_query.beginDate.value="";
		            			frm_query.endDate.value="";
		            			frm_query.firmId.value="";
		            		}
		            	</script>
					</td>
					
					<td align="left">
						<!-- <button type="button" class="smlbtn" onclick="doQuery(1);">查询合计</button>&nbsp; -->
					</td>
				</tr>
			</table>
		</fieldset>
	
	  <table id="tableList" border="0" cellspacing="0" cellpadding="0" width="100%" height="400">
  		<tHead>
			<tr height="25">
  			<td class="panel_tHead_LB">&nbsp;</td>
			<c:forEach items="${filed}" var="result">
			<td class="panel_tHead_MB_Curr2" nowrap abbr="${result.code}">${result.name}</td>
			</c:forEach>
			<td class="panel_tHead_RB">&nbsp;</td>
			</tr>
		</tHead>
		<tBody>
	  		<%
	  		  if(resultList!=null&&resultList.size()>0&&fileds!=null&&fileds.size()>0)
	  		  {
		  		    for(int i=0;i<resultList.size();i++)
		  		    {
		  		       Map map=(Map)resultList.get(i);
		  		       if(map!=null)
		  		       {
		  		       %>
		  		       <tr height="22" onclick="selectTr();">
	  			       <td class="panel_tBody_LB">&nbsp;</td>
		  		       <%
		  		         for(int ii=0;ii<fileds.size();ii++)
		  		         {
		  		           String field=(String)fileds.get(ii);
		  		           if(ii<2)
		  		           {
		  		             Object value=map.get(field);
		  		             %>
		  		             <td class="underLine" nowrap><%=value.toString()%></td>
		  		             <% 
		  		           }
		  		           else
		  		           {
		  		             Object value=map.get(field);
		  		             %>
		  <td class="underLineCurr2" nowrap><fmt:formatNumber value="<%=value.toString()%>" pattern="#,##0.00"/></td>
		  		             <%
		  		           }
		  		         }
		  		         %>
		  		         <td class="panel_tBody_RB">&nbsp;</td>
	  		             </tr>
		  		         <%
		  		       }
		  		    }
	  		  }
	  		 %>
	  	</tBody>
	  	<tFoot>
			<tr height="100%">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td colspan="<%=size%>">&nbsp;</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td class="pager" colspan="<%=size%>">
				  <%@ include file="../public/pagerInc.jsp" %>
				</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tFoot_LB">&nbsp;</td>
				<td class="panel_tFoot_MB" colspan="<%=size%>"></td>
				<td class="panel_tFoot_RB">&nbsp;</td>
			</tr>
		</tFoot>
	</table>
	</c:if>
	</form>
  </body>
</html>
<%@ include file="../public/footInc.jsp" %>