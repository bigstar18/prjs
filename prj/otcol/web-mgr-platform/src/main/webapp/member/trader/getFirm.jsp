<%@ page contentType="text/html;charset=GBK" %>
<%@ page import='gnnt.MEBS.member.firm.unit.Firm' %>
<%
	String firmId = request.getParameter("firmId");
	String sign = null;
	String permissions="";
	if(firmId != null){
		Firm firm = FirmManager.getFirmById(firmId);
		if(firm!=null)
		{
		  sign="true";
		}
		else
		{
		  sign="false";
		}
		String[] p=firm.getPermissions();
		if(p!=null&&p.length>0)
		{
			for(int i=0;i<p.length;i++)
			{  
			  permissions+=","+p[i]+",";
			}
		}
		
	}
%>
<%=sign%>|||<%=permissions%> 