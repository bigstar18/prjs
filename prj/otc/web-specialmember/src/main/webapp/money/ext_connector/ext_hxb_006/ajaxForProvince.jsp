<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.util.Date"/>
<%@ include file="../../globalDef.jsp"%>
<%@ include file="../../session.jsp"%>
<%
	HXBBankDAO hxDao = BankDAOFactory.getHXBDAO();
	request.setCharacterEncoding("GBK");
	String cityID = request.getParameter("cityID");
	if("0".equals(cityID)){
		response.getWriter().print("<select style='width: 150px'><option value='' selected>--«Î—°‘Ò--</option></select>");
	}else{
		String filterForCity = "where parentID='" + cityID + "'";
		Vector<CitysValue> cityValues = hxDao.getCityOfBank(filterForCity);
		CitysValue cityValue = new CitysValue();
		
		response.getWriter().print("<select name='bankCityName' id='bankCityName' style='width: 150px' onChange='cityChange()'><option value='' selected>--«Î—°‘Ò--</option>");
		for(int i=0;i<cityValues.size();i++){
			cityValue = cityValues.get(i);
			response.getWriter().print("<option value='" + cityValue.ID + "'>" + cityValue.cityName + "</option>");
		}
		response.getWriter().print("</select>");
	}
%>
