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
function dy_onclick(rowid)
{
  if (!atleaseOneCheck()){
     alert('请至少选择！');
     return;
  }
  //ec.action = "<c:url value="/timebargain/report/report.do?funcflg=printHoldDetailOther"/>";
  //ec.target = "_blank";
  //ec.exportTo.value = "pdf";
  //ec.submit();
  //alert(ec_table.rows(rowid).cells(8).innerHTML);
  var clearDate = ec_table.rows(rowid).cells(8).innerHTML;
  var items = document.getElementsByName('itemlist');
  var ids = "";
    if (items.length > 0) {
        for (var i = 0; i < items.length; i++)
        {
        	//alert(i);
            if (items[i].checked == true)
            {
            	//alert("in: "+i);
            	//alert(ec_table.rows(i).cells(9).innerHTML);
                ids = ids + "'" + ec_table.rows(i).cells(9).innerHTML + "'" + ",";
            }
        }
    }else {
        if (items.checked == true) {
            ids = ids + "'" + ec_table.rows(i).cells(9).innerHTML + "'" + ",";
        }
    }
  //alert(ids);
  openDialogLess("<c:url value="/timebargain/report/report.do?funcflg=printHoldDetailOther&ids="/>" + ids + "&clearDate=" + clearDate,"_blank");
  ec.action = "<c:url value="${pageContext.request.contextPath}/timebargain/report/report.do?funcflg=listHoldDetail"/>";
}
function dy_onclick1(id,rowid)
{
  //alert(id);
  //ec.action = "<c:url value="/timebargain/report/report.do?funcflg=printHoldDetailOther"/>&id="+id;
  //ec.target = "_blank";
  //ec.exportTo.value = "pdf";
  //ec.submit();
  alert(rowid);
  var clearDate = ec_table.rows(rowid).cells(8).innerHTML;
  openDialogLess("<c:url value="/timebargain/report/report.do?funcflg=printHoldDetailOther"/>&id="+id + "&clearDate=" + clearDate,"_blank",800,700);
  ec.action = "<c:url value="${pageContext.request.contextPath}/timebargain/report/report.do?funcflg=listHoldDetail"/>";
}


function openDialogLess(url, args, width, height) {
	if (!width) width = 600;
	if (!height) height = 400;
    window.showModelessDialog(url, args, "dialogWidth=" + width + "px; dialogHeight=" + height + "px; dialogTop:150px;center:yes;help:yes;resizable:yes;status:yes");
}
</script>
</head>
<body leftmargin="2" topmargin="0"  >

<table width="100%"><tr><td>
	<ec:table items="holdDetailList" var="holdDetail" 
			action="${pageContext.request.contextPath}/timebargain/report/report.do?funcflg=listHoldDetail"	
			autoIncludeParameters="${empty param.autoInc}"
			xlsFileName="holdDetailList.xls" 
			csvFileName="holdDetailList.csv"
			showPrint="true"
			rowsDisplayed="20"
			listWidth="100%"
			minHeight="300"
	>
		<ec:row>	
		    <ec:column cell="checkbox" headerCell="checkbox" alias="itemlist"  value="${holdDetail.FirmID}" viewsAllowed="html" style="text-align:center;padding-left:7px;width:30;"/>
		    <ec:column property="FirmID" title="交易商ID" style="text-align:center;" width="80">
            	<a href="javascript:dy_onclick1('<c:out value="${holdDetail.FirmID}"/>',${GLOBALROWCOUNT}-1)"><c:out value="${holdDetail.FirmID}"/></a> 
            </ec:column>
            <ec:column property="CommodityID" title="交货时间" style="text-align:center;"/>
            
            <ec:column property="BS_Flag" title="买/卖" editTemplate="ecs_t_status" mappingItem="BS_FLAG1" style="text-align:center;"/>
            <ec:column property="price" title="价格" style="text-align:right;"/>
			<ec:column property="holdQty" title="持仓数量" style="text-align:right;"/>
			<ec:column property="GageQty" title="抵顶数量" style="text-align:right;"/>
			<ec:column property="FloatingLoss" title="浮动盈亏" style="text-align:right;"/>
			<ec:column property="ClearDate" title="结算日期" cell="date" style="text-align:right;"/>
			<ec:column property="FirmID" title="交易商ID" style="text-align:center;" width="80"/>
		</ec:row>
		<ec:extend>
			<a href="#" onclick="javascript:return dy_onclick(${GLOBALROWCOUNT}-1);"><img src="<c:url value="/timebargain/images/03.gif"/>"></a>
		</ec:extend>
	</ec:table>
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
</td></tr></table>
<%@ include file="/timebargain/common/messages.jsp" %>
<script type="text/javascript">
<!--
<%
  if(request.getAttribute("holdDetailList") != null)
  {
%>
    parent.TopFrame.reportForm.query.disabled = false;
    parent.TopFrame.reportForm.dy.disabled = false;
    parent.TopFrame.wait.style.visibility = "hidden";
<%
  }
%>

	for (var i=0;i<ec_table.rows.length;i++){
		ec_table.rows(i).cells(8).style.display = "none";
		ec_table.rows(i).cells(9).style.display = "none";
	}
// -->
</script>
</body>

</html>
