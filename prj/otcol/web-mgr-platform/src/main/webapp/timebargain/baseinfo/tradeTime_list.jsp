<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<html:html>
<head>


<%@ include file="/timebargain/common/ecside_head.jsp" %>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/open.js"/>"></script>

<title>
default
</title>
<script type="text/javascript">
<!--
//add
function add_onclick()
{
    document.location.href = "<c:url value="/timebargain/baseinfo/tradeTime.do?crud=create&funcflg=edit"/>";
}
//修改状态正常
function correct_onclick(){
	document.location.href = "<c:url value="/timebargain/baseinfo/tradeTime.do?crud=correct&funcflg=updateStatus"/>";
}

//修改状态禁用
function incorrect_onclick(){
	document.location.href = "<c:url value="/timebargain/baseinfo/tradeTime.do?crud=incorrect&funcflg=updateStatus"/>";
}

function online(){
	document.location.href = "<c:url value="/timebargain/baseinfo/tradeTime.do?funcflg=onLine"/>";
}
// -->
</script>
</head>
<body>
<div id="content">
    <table width="100%">
  <tr><td>
	<ec:table items="tradeTimeList" var="tradeTime" 
			action="${pageContext.request.contextPath}/timebargain/baseinfo/tradeTime.do?funcflg=search"	
			autoIncludeParameters="${empty param.autoInc}"
			toolbarContent="save add del|extend"
			pageSizeList="all"
			minHeight="300"
			listWidth="100%"
			title=""
	>
    <ec:row>
			<ec:column cell="checkbox" headerCell="checkbox" alias="itemlist" columnId="itemlist" value="${tradeTime.sectionID}" viewsAllowed="html" style="text-align:center;padding-left:7px;width:30;"/>						
            <ec:column property="sectionID" title="编号" width="10%" style="text-align:center;">
              <a href="<c:out value="${ctx}"/>/timebargain/baseinfo/tradeTime.do?crud=update&funcflg=edit&sectionID=<c:out value="${tradeTime.sectionID}"/>" title="修改"><c:out value="${tradeTime.sectionID}"/></a> 
            </ec:column>
            <ec:column property="name" title="交易节名称" width="20%" style="text-align:center;">
              <a href="<c:out value="${ctx}"/>/timebargain/baseinfo/tradeTime.do?crud=update&funcflg=edit&sectionID=<c:out value="${tradeTime.sectionID}"/>" title="修改"><c:out value="${tradeTime.name}"/></a> 
            </ec:column>
            <ec:column property="startTime" title="交易开始时间" width="20%" style="text-align:center;"/>
			<ec:column property="endTime" title="交易结束时间" width="20%" style="text-align:center;"/>
			<ec:column property="status" title="状态" width="10%" style="text-align:center;"/>
			<ec:column property="gatherBid" title="集合竞价" width="20%" editTemplate="ecs_t_gatherBid"  mappingItem="GATHERBID_STATUS"  style="text-align:center;" />
			
					
		</ec:row>
   
	  <ec:extend>
			
			<img src="<c:url value="/timebargain/images/girdadd.gif"/>" usemap="#Map1">
			<img src="<c:url value="/timebargain/images/girddel.gif"/>" usemap="#Map2">
			
			
		</ec:extend>	
	</ec:table>
	
		<map name="Map1">
			<area shape="rect" coords="1,1,51,19" href="javascript:add_onclick()">
		</map>
		<map name="Map2">
			<area shape="rect" coords="1,1,51,19" href="javascript:batch_do('交易节记录','<c:url value="/timebargain/baseinfo/tradeTime.do?funcflg=delete"/>');">
		</map>
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
			<ec:options items="CUSTOMER_STATUS" />
		</select>
	</textarea>	
	<textarea id="ecs_t_gatherBid" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="gatherBid" >
			<ec:options items="GATHERBID_STATUS" />
		</select>
	</textarea>
</div>
	
<%@ include file="/timebargain/common/messages.jsp" %>
</body>
</html:html>
