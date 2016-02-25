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
function add_onclick(breedID)
{
	var oper = document.forms(0).oper.value;
    document.location.href = "<c:url value='/timebargain/baseinfo/commodity.do?crud=create&funcflg=edit'/>&breedID="+breedID + "&oper=" + oper;
}
//breed
function breed_onclick()
{
    //top.MainFrame.location.href = "<c:url value="/baseinfo/breed.do"/>";
    parent.location.href ="breed.jsp";
}
// -->
function batch_do(entityName, action)
{	
    if (confirm("您确定要" + entityName + "吗？"))
    {
        if (!atleaseOneCheck())
        {
            alert('请至少选择一' + entityName + '！');
            return;
        }
       // parent.location.replace( 'breed.jsp');
        var form = document.forms.ec;
        form.action = action + '&autoInc=false';  
        form.submit();
    }
}
function status2(){
	//alert(document.getElementById("status").value);
	var status = document.getElementById("status").value;
	//alert(status);
	if (status == "1") {
		batch_do('修改商品状态','<c:url value="/timebargain/baseinfo/commodity.do?crud=correct&funcflg=updateStatus"/>');
	}
	if (status == "2") {
		batch_do('修改商品状态','<c:url value="/timebargain/baseinfo/commodity.do?crud=incorrect&funcflg=updateStatus"/>');
	}
	if (status == "3") {
		batch_do('修改商品状态','<c:url value="/timebargain/baseinfo/commodity.do?crud=pause&funcflg=updateStatus"/>');
	}
}
</script>
</head>
<body>
		<%
			String oper = (String)request.getAttribute("oper");
			String breedName = (String)request.getAttribute("breedName");
			if (breedName == null) {
				breedName = "";
			}
		%>
	<input type="hidden" name="oper" value="<%=oper%>"/>
	<input type="hidden" name="breedId" value="<%= (String)request.getAttribute("breedID") %>>"/>	
<table width="100%">
  <tr><td>
	<ec:table items="commodityList" var="commodity" 
			action="${pageContext.request.contextPath}/timebargain/baseinfo/commodity.do?funcflg=search"	
			autoIncludeParameters="${empty param.autoInc}"
			xlsFileName="commodity.xls" 
			csvFileName="commodity.csv"
			showPrint="true" 
			listWidth="100%"
			title=""
			rowsDisplayed="20"
			minHeight="300"
	>
		<ec:row>
			<ec:column cell="checkbox" headerCell="checkbox" alias="itemlist" columnId="itemlist" value="${commodity.CommodityID}" viewsAllowed="html" style="text-align:center;padding-left:6px;width:30;"/>		
			<ec:column property="name" title="商品名称" width="10%" style="text-align:center;">	
			  <a href="<c:out value="${ctx}"/>/timebargain/baseinfo/commodity.do?crud=update&oper=${oper}&funcflg=edit&commodityID=<c:out value="${commodity.CommodityID}"/>&breedID=<c:out value="${commodity.breedID}"/>" title="修改"><c:out value="${commodity.name}"/></a> 
            </ec:column>	
            <ec:column property="commodityID" title="商品代码" width="10%" style="text-align:center;"/>              								
			<%
				if ("cur".equals(oper)) {
			%>
			
			<ec:column property="status"  title="状态" editTemplate="ecs_t_status" mappingItem="COMMODITY_STATUS" width="15%" style="text-align:center;"/>			
			<%
				}
			%>
			<ec:column property="marginRate_B" title="买保证金系数" width="15%" style="text-align:center;"/> 
			<ec:column property="marginRate_S" title="卖保证金系数" width="15%" style="text-align:center;"/> 
			<ec:column property="marketDate" title="上市日期" cell="date" format="date" width="20%" style="text-align:center;"/>
			<ec:column property="settleDate" title="交收日期" cell="date" format="date" width="20%" style="text-align:center;"/>				
		</ec:row>
		<ec:extend>
		<%
			if ("cur".equals(oper)) {
		%>
						
			<a href="#" onclick="add_onclick('${param.breedID}')"><img src="<c:url value="/timebargain/images/girdadd.gif"/>"></a>
			<a href="#" onclick="javascript:batch_do('删除商品','<c:url value="/timebargain/baseinfo/commodity.do?funcflg=delete"/>');"><img src="<c:url value="/timebargain/images/girddel.gif"/>"></a>
			
		<%
			}
		%>	

			<a href="#" onclick="breed_onclick()"><img src="<c:url value="/timebargain/images/returnBreed.gif"/>"></a>
		</ec:extend>		
	</ec:table>
</td></tr>

		<%
			if ("cur".equals(oper)) {
		%>
<tr>
	<td>
		<select id="status">
			<option value="1">有效</option>
			<option value="3">暂停交易</option>
		</select>
		<input type="button" class="button" value="修改状态"  onclick="status2()">
	</td>
</tr>

		<%
			}
		%>	
</table>	
	<!-- 编辑和过滤所使用的 通用的文本框模板 -->
	<textarea id="ecs_t_input" rows="" cols="" style="display:none">
		<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" 
		 style="width:100%;" name="" />
	</textarea>
	<!-- 编辑状态所用模板 -->
	<textarea id="ecs_t_status" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="status" >
			<ec:options items="COMMODITY_STATUS" />
		</select>
	</textarea>		
<%@ include file="/timebargain/common/messages.jsp" %>
</body>

</html>
