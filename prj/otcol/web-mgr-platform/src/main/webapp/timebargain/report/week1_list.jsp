<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<html>
<head>
<%@ include file="/timebargain/common/ecside_head.jsp" %>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/open.js"/>"></script>
<title>
default
</title>
		<script type="text/javascript"> 
//dy_onclick
function dy_onclick()
{
  if (!atleaseOneCheck())
        {
            alert('请至少选择一条记录！');
            return;
        }
  ec.action = "<c:url value="/timebargain/report/customerFund.jsp"/>";
  ec.target = "_blank";
  ec.exportTo.value = "pdf";
  ec.submit();   
  ec.action = "<c:url value="${pageContext.request.contextPath}/timebargain/report/report.do?funcflg=listWeek1Detail"/>";
}
function dy_onclick1(id)
{
  //alert(id);
  ec.action = "<c:url value="/timebargain/report/customerFund.jsp"/>?id="+id;
  ec.target = "_blank";
  ec.exportTo.value = "pdf";
  ec.submit();   
  ec.action = "<c:url value="${pageContext.request.contextPath}/timebargain/report/report.do?funcflg=listWeek1Detail"/>";
}

function doExport()
{
   if (!atleaseOneCheck())
        {
            alert('请至少选择一个客户！');
            return;
        }
  /*reportForm.exportTo.value = "excel";
  reportForm.submit();   */
  ec.action = "../commodityImp";
  ec.target = "_blank";
  ec.submit();   
  ec.action = "<c:url value="${pageContext.request.contextPath}/timebargain/report/report.do?funcflg=listWeek1Detail"/>";
}
</script>
</head>
<body leftmargin="2" topmargin="0">

<div id="content">
	<table width="900">
    <tr><td>
	<ec:table items="week1DetailList" var="week1Detail" 
			action="${pageContext.request.contextPath}/timebargain/report/report.do?funcflg=listWeek1Detail"	
			autoIncludeParameters="${empty param.autoInc}"
			xlsFileName="week1DetailList.xls" 
			csvFileName="week1etailList.csv"
			showPrint="true"
			rowsDisplayed="20"
			listWidth="100%"
	>
		<ec:row>	
		    <ec:column cell="checkbox" headerCell="checkbox" alias="itemlist" columnId="itemlist" value="${week1Detail.CustomerID}" viewsAllowed="html" style="text-align:center;padding-left:7px;width:30;"/>
            <ec:column property="FirmID" title="交易商ID" width="65" style="text-align:center;">
            <a href="javascript:dy_onclick1('<c:out value="${week1Detail.FirmID}"/>')"><c:out value="${week1Detail.FirmID}"/></a> 
            </ec:column>
            <ec:column property="FirmName" title="交易商名称" width="85" style="text-align:center;"/>
            <ec:column property="InitFunds" title="report.week1Detail.InitFunds" width="80" style="text-align:right;"/>
            <ec:column property="InFunds" title="report.week1Detail.InFund"  width="80" style="text-align:right;"/>
            <ec:column property="OutFunds" title="report.week1Detail.OutFund" width="80" style="text-align:right;"/>
            <ec:column property="ClearFee" title="report.week1Detail.ClearFee" width="80" style="text-align:right;"/>
			<ec:column property="RuntimeFee" title="report.week1Detail.RuntimeFee" width="80" style="text-align:right;"/>
			<ec:column property="Close_PL" title="report.week1Detail.Close_PL" width="80" style="text-align:right;"/>
			<ec:column property="TradeFee" title="report.week1Detail.TradeFee" width="90" style="text-align:right;"/>
			<ec:column property="LastFund" title="report.week1Detail.LastFund" width="80" style="text-align:right;"/>
			
		</ec:row>
		<ec:extend>
			<a href="#" onclick="javascript:return dy_onclick();"><img src="<c:url value="/timebargain/images/03.gif"/>"></a>
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
			<ec:options items="BS_FLAG1" />
		</select>
	</textarea>	
</div>
<%@ include file="/common/messages.jsp" %>
<script type="text/javascript">
<!--
<%
  if(request.getAttribute("week1DetailList") != null)
  {
%>
    parent.TopFrame.reportForm.query.disabled = false;
    parent.TopFrame.reportForm.dy.disabled = false;
    parent.TopFrame.wait.style.visibility = "hidden";
<%
  }
%>
// -->
</script>
</body>

</html>
