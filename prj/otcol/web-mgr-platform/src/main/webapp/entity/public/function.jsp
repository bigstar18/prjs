<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../globalDef.jsp"%>

<%
ManageDAO manage = ManageDAOFactory.getDAO();
%>

<SCRIPT LANGUAGE="JavaScript">
<!--
function w(selObj)
{
   while(selObj.length > 1)
   {
	   selObj.removeChild(selObj.children(1));
   }
}

function z(v1,v2,selObj)
{
   var oNewItem = document.createElement("option");	
   selObj.options.add(oNewItem);
   oNewItem.innerText = v1;
   oNewItem.value = v2;
}



window.parent.frames("main").location = "../firmList.jsp";

//-->
</SCRIPT>