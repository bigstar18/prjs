<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="GBK"%>
<%@ page import="java.util.*" %>
<%@ page import="org.ecside.table.limit.*" %>
<%@ page import="org.ecside.util.RequestUtils" %>
<%@ page import="gnnt.MEBS.timebargain.manage.service.SettleManage" %>
<html>
<%@ include file="/timebargain/common/ecside_head.jsp" %>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
<LINK REL="stylesheet" type="text/css"
			href="<c:url value="/timebargain/styles/common.css"/>" />
<script type="text/javascript">
	function doQuery()
	{
		var CommodityID = document.getElementById("commodityID").value;
		var firmID = document.getElementById("firmID").value;
		//var filter = " and 1=1 ";
		//if(CommodityID!=""){
		//	filter = filter + " and CommodityID='"+CommodityID+"'";
		//}
		//if(firmID!=""){
		//	filter = filter + " and (FirmID_B='"+firmID+"' or FirmID_S='"+firmID+"')";
		//}
		//document.getElementById("condition").value = filter;
		//window.location.href="settle.jsp?condition="+filter;
		window.location.href="settle.jsp?CommodityID="+CommodityID+"&firmID="+firmID;
	}
	
	function add_onclick()
	{
		var result = window.showModalDialog("createSettleRecord.jsp?d="+Date(),"", "dialogWidth=500px; dialogHeight=330px; status=no;scroll=yes;help=no;"); 
		if(result)
		{
			//window.location.href="settle.jsp";		
			window.location.reload();	
		}
		
	}
	function edit_onclick(id)
	{
			var settlePageInfo=ec.ec_pg.value;//第几页
			var settlePageSize=ec.ec_rd.value;//每页条数
			var settleTotalPage=ec.ec_totalpages.value;//总页数
			var CommodityID = document.getElementById("commodityID").value;
		  var firmID = document.getElementById("firmID").value;
			//alert(t_p);
			window.location.href='settleMsg.jsp?MatchID='+id+'&&settlePageInfo='+settlePageInfo+'&settlePageSize='+settlePageSize+'&settleTotalPage='+settleTotalPage+'&CommodityID='+CommodityID+'&firmID='+firmID;
		
	}
	function trunTo	(v)
	{
		window.location.href="settle.jsp";
	}

	//重置数据
	function resetData()
	{
		document.getElementsByName("firmID")[0].value="";
		document.getElementsByName("commodityID")[0].value="";
		
	}
</script>
<%
	//首次访问或者按条件查询
	String settlePageInfo = request.getParameter("settlePageInfo");
	String settlePageSize = request.getParameter("settlePageSize");
	String settleTotalPage = request.getParameter("settleTotalPage");	
	String CommodityID = request.getParameter("CommodityID");
	String firmID = request.getParameter("firmID");

	String condition = "";
	if(CommodityID != null && !"".equals(CommodityID.trim())){
		condition = condition + " and s.CommodityID='"+CommodityID+"'";
	}else{
		CommodityID="";
	}
	if(firmID != null && !"".equals(firmID.trim())){
		condition = condition + " and (FirmID_B='"+firmID+"' or FirmID_S='"+firmID+"')";
	}else{
		firmID="";
	}
	List settleList = SettleManage.getSettlesNew(condition);
	request.setAttribute("settleList",settleList);
	Map statusMap=new HashMap();
	statusMap.put("0","未处理");
	statusMap.put("1","处理中");
	statusMap.put("2","处理完成");
	statusMap.put("3","作废");
	statusMap.put("-2","作废");
	request.setAttribute("statusMap",statusMap);
	Map resultMap=new HashMap();
	resultMap.put("1","履约");
	resultMap.put("2","买方违约");
	resultMap.put("3","卖方违约");
	resultMap.put("4","双方违约");
	request.setAttribute("resultMap",resultMap);
%>
<body>
	<fieldset>
		<legend class="common"><b>查询条件</b></legend>
		<span>
		<table class="common" align="left" valign="center">
			<tr class="common">
			<td align="left">&nbsp;&nbsp;商品代码：<input type="text" name="commodityID" value="<%=CommodityID%>"onkeypress="onlyNumberAndCharInput()" maxlength="16"></td>
			<td align="left">&nbsp;&nbsp;交易商代码：<input type="text" name="firmID" value="<%=firmID%>"onkeypress="onlyNumberAndCharInput()" maxlength="16"></td>
			<td align="left">&nbsp;&nbsp;<input type="button" class="button" value="查询" onclick="return doQuery();">&nbsp;&nbsp;<input type="button" class="button" value="重置" onclick="resetData();"></td>
			</tr>
		</table>
		</span>
	</fieldset>
	
	<!-- ecside展示数据 -->
	<table class="common" align="center" width="100%">
	<tr class="common"><td>
		  <ec:table items="settleList" 
		  	         autoIncludeParameters="${empty param.autoInc}"
		  	         var="settle" 
		  	         action=""	
		  	         height="500px" 
		  	         title=""
		  	         rowsDisplayed="20"
			           minHeight="300"
			           listWidth="100%"
		  >
			
			<ec:row>
				<ec:column property="MatchID" title="配对编号" width="10%" style="text-align:center;">	
					<a href="javascript:edit_onclick('${settle.matchID}')"><c:out value="${settle.matchID}"/></a>
				</ec:column>
				<ec:column property="CommodityID" title="商品代码" width="15%" style="text-align:center;" />
				<ec:column property="Quantity" title="交易数量" width="15%" style="text-align:center;" />
				<ec:column property="FirmID_B" title="买方交易商代码" width="15%" style="text-align:center;" />
				<ec:column property="FirmID_S" title="卖方交易商代码" width="15%" style="text-align:center;" />
				<ec:column property="Status" title="状态" editTemplate="ecs_t_status" mappingItem="statusMap" width="15%" style="text-align:center;" />
				<ec:column property="Result" title="执行结果" editTemplate="ecs_t_status1" mappingItem="resultMap" width="15%" style="text-align:center;" />			
			</ec:row>
			<ec:extend>
				<a href="#" onclick="add_onclick()"><img src="<c:url value="/timebargain/images/girdadd.gif"/>">
			</ec:extend>		
		</ec:table>
	</td></tr>
	</table>
	<!-- 编辑和过滤所使用的 通用的文本框模板 -->
	<textarea id="ecs_t_input" rows="" cols="" style="display:none">
		<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" 
		 style="width:100%;" name="" />
	</textarea>
	<textarea id="ecs_t_status" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="status" >
			<ec:options items="statusMap" />
		</select>
	</textarea>
	<textarea id="ecs_t_status1" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="status" >
			<ec:options items="resultMap" />
		</select>
	</textarea>
</body>
</html>
<script type="text/javascript">
	var settlePageInfo=<%=settlePageInfo%>;
	var settlePageSize=<%=settlePageSize%>;
	var settleTotalPage=<%=settleTotalPage%>
	function turnPages()
	{
	
	if(settleTotalPage)
	{
		//ECSideUtil.gotoPageByInput(ec.ec_pg,'ec');
		try{
		ECSideUtil.gotoPage(settlePageInfo,'ec');
	  }
	  catch(err)
	  {
	  	  setTimeout("turnPages()",10);
	  }	  
	}
}

 function isCan()
 {
 	if(settleTotalPage)
 	{
 	  //alert(ec.waitingBar);
 	  if(ec.ec_waitingBar)
 	  {
 	  	turnPages();
 	  }
 	  else
 	  {
 	  	setTimeout("isCan()",10);
 	  }
 	}
 }

if(settleTotalPage)
{
	  ec.ec_pg.value=settlePageInfo;
	  ec.ec_rd.value=settlePageSize;
    ec.ec_totalpages.value=settleTotalPage;
    ec.ec_crd.value=settlePageSize;
	  for (var i=0;i<ec_table.rows.length;i++)                
  {
  	ec_table.rows(i).style.display = "none";		
  }
}


	setTimeout("turnPages()",10);
</script>