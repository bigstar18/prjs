<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.util.Date"/>
<%@ include file="../../globalDef.jsp"%>
<%@ include file="../../session.jsp"%>
<%
	HXBBankDAO hxDao = BankDAOFactory.getHXBDAO();
	request.setCharacterEncoding("GBK");
	String bank = request.getParameter("bank");
	String city = request.getParameter("city");
	String county = Tool.delNull(request.getParameter("county"));
	
	String cityName = hxDao.getCityOfBank("where ID='" + city + "'").get(0).cityName;
	String countyName = hxDao.getCityOfBank("where ID='" + county + "'").get(0).cityName;
	
	if("".equals(county)){
		response.getWriter().print("<select style='width: 300px'><option value='' selected>--«Î—°‘Ò--</option></select><span>*</span>");
	}else{
		String filterForBanks = "where nbkname like '%" + bank + "%' and (nbkname like '%" + cityName + "%' or nbkname like '%" + countyName + "%')";
		Vector<BanksInfoValue> list = hxDao.getBanksInfo(filterForBanks);
		BanksInfoValue banksValue = new BanksInfoValue();
		
		response.getWriter().print("<select name='acctBankName' id='acctBankName' style='width: 300px' onChange='bankNameChange()'><option value='' selected>--«Î—°‘Ò--</option>");
		for(int i=0;i<list.size();i++){
			banksValue = list.get(i);
			response.getWriter().print("<option value='" + banksValue.nbkname + "'>" + banksValue.nbkname + "</option>");
		}
		response.getWriter().print("</select><span>*</span>");
	}
	
%>
