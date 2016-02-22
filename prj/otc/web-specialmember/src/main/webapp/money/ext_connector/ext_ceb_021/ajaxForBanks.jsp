<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.util.Date"/>
<%@ include file="../../globalDef.jsp"%>
<%@ include file="../../session.jsp"%>
<%
	CEBBankDao dao = BankDAOFactory.getCEBDAO();
	//String bank = new String(Tool.delNull(request.getParameter("bank")).getBytes("ISO-8859-1"),"GBK");
	//String city = new String(Tool.delNull(request.getParameter("city")).getBytes("ISO-8859-1"),"GBK");
	//String county = new String(Tool.delNull(request.getParameter("county")).getBytes("ISO-8859-1"),"GBK");
	String bank = Tool.delNull(request.getParameter("bank"));
	String city = Tool.delNull(request.getParameter("city"));
	String county = Tool.delNull(request.getParameter("county"));
	String cityName = dao.getCityOfBank("where ID='" + city + "'").get(0).cityName;
	String countyName = dao.getCityOfBank("where ID='" + county + "'").get(0).cityName;
	
	if("".equals(county)){
		response.getWriter().print("<select style='width: 300px'><option value='' selected>--«Î—°‘Ò--</option></select><span>*</span>");
	}else{
		String filterForBanks = "where nbkname like '%" + bank + "%' and (nbkname like '%" + cityName + "%' or nbkname like '%" + countyName + "%')";
		Vector<BanksInfoValue> list = dao.getBanksInfo(filterForBanks);
		BanksInfoValue banksValue = new BanksInfoValue();
		
		response.getWriter().print("<select name='acctBankName' id='acctBankName' style='width: 300px' onChange='bankNameChange()'><option value='' selected>--«Î—°‘Ò--</option>");
		for(int i=0;i<list.size();i++){
			banksValue = list.get(i);
			response.getWriter().print("<option value='" + banksValue.nbkname + "'>" + banksValue.nbkname + "</option>");
		}
		response.getWriter().print("</select><span>*</span>");
	}
	
%>
