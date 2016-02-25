<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<%@ page import="java.util.*" %>
<%@ page import="gnnt.MEBS.timebargain.manage.service.*"%>
<%@ page import="gnnt.MEBS.timebargain.manage.util.*"%>
<%@ page import="gnnt.MEBS.timebargain.manage.model.*" %> 
<%
		LookupManager lookupMgr = (LookupManager)SysData.getBean("lookupManager");
		//lookupMgr.getSelectLabelValueByTableDistinctCommodityID：通过条件查询出商品Id
         request.setAttribute("commoditySelect", lookupMgr.getSelectLabelValueByTableDistinctCommodityID("t_orders", "commodityID",
				"commodityID"," order by commodityID ")); 
          String commodityID2 = request.getParameter("commodityID2");

         
		
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<title>default</title>
	<link href="<c:url value="/timebargain/styles/maintest.css"/>" rel="stylesheet" type="text/css">
    <script type="text/javascript">
	<!--
		var commodityID2 = '<%=commodityID2%>';
		
		function query()
		{ 
			if (commodityID2 != "null" && commodityID2 != "") { 
				frm.parameter.value = commodityID2;
			} 
			 //parent.leftFrame.location.reload();
			parent.leftFrame.query(frm.parameter.value); 
			parent.rightFrame.query(frm.parameter.value); 
			}
	// -->
	
	function windowload(){
	//alert(1);
	//	setTimeout("query()",2000);
	}
	</script>
  </head>
  <body bgcolor="#000000" onload="windowload()">

  <form name="frm" METHOD=POST ACTION="">
  <table width="100%" height="5%"  border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="94%">
        	<div align="center"></div>
        	<div align="right">
        		<span class="pagetype"><label id="queryTypeText">商品代码</label>：</span>
        		<select id="parameter" style="width:111">
        			<%
        				String commodityID = "";
        				//list中存放的是所有商品的Id 
						List list = (List)request.getAttribute("commoditySelect");
						if (list != null) {
							for (int i = 0; i < list.size(); i++) {
								LabelValue lv = (LabelValue)list.get(i);
								if (lv != null) {
									commodityID = lv.getValue();
					%>
					<option value="<%=commodityID%>"><%=commodityID%></option>
					<%
								}
							}
							
						}
        			%>
        		</select>
        	</div>
		</td>
		<td width="6%"><div align="RIGHT"><input type="button" value="查询" onclick="query()"></div></td>
  	</tr>
	</table>
   	</form>
	</body>
</html>
