<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../globalDef.jsp"%>
<SCRIPT LANGUAGE="JavaScript">
<!--
<%
	ManageDAO manage6 = ManageDAOFactory.getDAO();
	QueryValue qv6 = new QueryValue();
	qv6.pageSize = 10000;
	qv6.filter = " order by id ";
	CommodityPropertiesValue[] cpvs8 = manage6.getCommodityPropertiesList(qv6);
%>


function fun3(v1,v2,v3,m1,m2,m3)
{
   for(var i=0;i<<%=cpvs8.length%>;i++)
   {
	   if(v2[i] == m1)
	   {
		 if(v1[i] == m2)
		 {
			 v3[i] = m3;
			 break;
		 }
	   }
   }
}

function disCommodityName(code)
{
	var commodityName = "";	
	if(code == null || code.length != <%=cpvs8.length*ATTRIBUTELENTH%>) return commodityName;
	else
   {
	var vals = new Array(<%=cpvs8.length%>);
	var val1s = new Array(<%=cpvs8.length%>);
	var val2s = new Array(<%=cpvs8.length%>);
	<%
	for(int i=0;i<cpvs8.length;i++)
	{	
		%>
		vals[<%=i%>] = code.substring(<%=i*ATTRIBUTELENTH%>,<%=(i+1)*ATTRIBUTELENTH%>);
		val1s[<%=i%>] = '<%=cpvs8[i].getName()%>';
		val2s[<%=i%>] = "";
		<%
	}
	for(int i=0;i<cpvs8.length;i++)
	{
		 CommodityPropertiesValue cpv = cpvs8[i];
		 QueryValue qv7 = new QueryValue();
		 qv7.pageSize = 10000;
		 qv7.filter = " and status=0 and classid="+ cpv.getID() +" ";
		 CommodityParameterValue[] cpavs = manage6.getCommodityParameterList(qv7);
		 for(int k=0;k<cpavs.length;k++)
		 {
			 CommodityParameterValue cpav = cpavs[k];
          %> 
			 fun3(vals,val1s,val2s,'<%=cpv.getName()%>','<%=cpav.getID()%>','<%=cpav.getDescription()%>');<%
		 }
	}	
	%>

	for(var i=0;i<<%=cpvs8.length%>;i++)   
	{
		commodityName += val2s[i] + ' ';
	}
	//document.write(commodityName);
	return commodityName;
	}
	
}
//-->
</SCRIPT>