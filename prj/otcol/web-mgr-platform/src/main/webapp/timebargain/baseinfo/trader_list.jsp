<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<%@ page import="java.util.*" %>
<html>
<head>
<%@ include file="/timebargain/common/ecside_head.jsp" %>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
		<LINK REL="stylesheet" type="text/css"
			href="<c:url value="/timebargain/styles/common.css"/>" />
<title>
default
</title>
<script type="text/javascript">
<!--
//add
function add_onclick()
{
    document.location.href = "<c:url value="/timebargain/baseinfo/trader.do?crud=create&funcflg=edit&firmID="/>" + document.forms(0).firmID.value;
}

function batch_do1(entityName, action)
{
	var firmID = document.forms(0).firmID.value;
	//document.location.href = ;
    if (confirm("您确定要" + entityName + "吗？"))
    {
        if (!atleaseOneCheck())
        {
            alert('请至少选择一' + entityName + '！');
            return;
        }
        var form = document.forms.ec;
        form.action = "<c:url value="/timebargain/baseinfo/trader.do?crud=correct&funcflg=updateStatusY&firmID"/>" + firmID + '&autoInc=false';
        form.submit();
    }
}
function batch_do2(entityName, action)
{
	var firmID = document.forms(0).firmID.value;
	//document.location.href = 
    if (confirm("您确定要" + entityName + "吗？"))
    {
        if (!atleaseOneCheck())
        {
            alert('请至少选择一' + entityName + '！');
            return;
        }
        var form = document.forms.ec;
        form.action = "<c:url value="/timebargain/baseinfo/trader.do?crud=incorrect&funcflg=updateStatusY&firmID"/>" + firmID + '&autoInc=false';
        form.submit();
    }
}


function status2(){
	var status = document.getElementById("status").value;
	if (status == "1") {
		batch_do1('修改交易员状态','');
	}
	if (status == "2") {
		batch_do2('修改交易员状态','');
	}
	
}

function gobackToFirm(){
	document.location.href = "<c:url value="/timebargain/menu/Firm.do"/>";
}
// -->
</script>
</head>
<body>
	<%
		List list = (List) request.getAttribute("traderList");
		Map map = (Map)list.get(0);
		String firmID = map.get("firmID").toString();
	%>
	<input type="hidden" name="firmID" value="firmID"/>
	<div align="center"><B>交易商代码: <%=firmID%></B></div>
	
<table width="100%">
  <tr>
  		
  <td>
	<ec:table items="traderList" var="trader" 
			  action="${pageContext.request.contextPath}/timebargain/baseinfo/trader.do?funcflg=search"	
			height="390px" 
			autoIncludeParameters="${empty param.autoInc}"
			xlsFileName="trader.xls" 
			csvFileName="trader.csv"
			showPrint="true" 
			filterable="true" 
			listWidth="100%"
			minHeight="300"
	>		
		<ec:row>
<%--		    <ec:column cell="checkbox" headerCell="checkbox" alias="itemlist" columnId="itemlist" value="${trader.traderID}" style="text-align:center;padding-left:6px;width:30;" viewsAllowed="html" />				--%>
            <ec:column property="traderID" title="交易员代码" style="text-align:center;" width="25%">
            </ec:column>
			<ec:column property="name" title="交易员名称" width="25%" style="text-align:center;"/>
			<ec:column property="_2" title="所属二级客户" width="25%" tipTitle="进入二级客户信息" style="text-align:center;">
              <a href="<c:out value="${ctx}"/>/timebargain/baseinfo/trader.do?funcflg=editCode&traderID=<c:out value="${trader.traderID}"/>&firmID=<c:out value="${trader.firmID}"/>"/><img src="<c:url value="/timebargain/images/man.gif"/>"></a> 
            </ec:column>
            <ec:column property="_1" title="交易员权限" width="25%" tipTitle="查看权限" style="text-align:center;">
              <a href="<c:out value="${ctx}"/>/timebargain/baseinfo/trader.do?funcflg=tradePrivilege&traderID=<c:out value="${trader.traderID}"/>&firmID=<c:out value="${trader.firmID}"/>"/><img src="<c:url value="/timebargain/images/wheel.gif"/>"></a> 
            </ec:column>
		</ec:row>
		<ec:extend>
			<a href="#" onclick="gobackToFirm()">返回交易商列表</a>
		</ec:extend>		
	</ec:table>
</td></tr>
<tr></tr><tr></tr>

</table>
	<!-- 编辑和过滤所使用的 通用的文本框模板 -->
	<textarea id="ecs_t_input" rows="" cols="" style="display:none">
		<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" 
		 style="width:100%;" name="" />
	</textarea>
	<!-- 编辑状态所用模板 -->
	<textarea id="ecs_t_status" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="status" >
			<ec:options items="TRADER_STATUS" />
		</select>
	</textarea>	
<%@ include file="/timebargain/common/messages.jsp" %>
</body>

</html>
