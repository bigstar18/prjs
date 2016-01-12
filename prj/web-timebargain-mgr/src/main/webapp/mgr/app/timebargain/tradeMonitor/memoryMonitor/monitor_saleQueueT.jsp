<%@ page pageEncoding="GBK" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>


<html>
  <head>
	<title>default</title>
		<link href="${mgrPath}/skinstyle/default/css/app/monitorSet.css" rel="stylesheet" type="text/css">
    <script type="text/javascript">
		var commodityID2 = "";
		
		function query()
		{ 
			commodityID2=document.getElementById("commodityID2").innerHTML;
			if (commodityID2 != "null" && commodityID2 != "") { 
				frm.parameter.value = commodityID2;
			} 
			 //parent.leftFrame.location.reload();
			parent.leftFrame.query(frm.parameter.value); 
			parent.rightFrame.query(frm.parameter.value); 
		}
	
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
        		<label id="commodityID2" style="display:none">${sessionScope.commodityID2 }</label>
        		<span class="pagetype"><label id="queryTypeText">��Ʒ����</label>��</span>
        		<select id="parameter" style="width:111">
        			<option value="">��ѡ��</option>
        			<c:forEach items="${sessionScope.commoditySelect}" var="result">
						<option value="${result.COMMODITYID}">${result.COMMODITYID}</option>
					</c:forEach>
        		</select>
        	</div>
		</td>
		<td width="6%"><div align="RIGHT"><input type="button" value="��ѯ" onclick="query()"></div></td>
  	</tr>
	</table>
   	</form>
	</body>
</html>
