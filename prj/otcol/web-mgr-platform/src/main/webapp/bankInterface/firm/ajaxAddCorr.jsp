<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.util.Date"/>
<%@ include file="../globalDef.jsp"%>
<%@ include file="../session.jsp"%>
<%
	BankDAO dao = BankDAOFactory.getDAO();
	String provinceCode = request.getParameter("provinceCode");
	if("0".equals(provinceCode)){
		response.getWriter().print("<select style='width: 140px'><option value='0' selected>--«Î—°‘Ò--</option></select>");
	}else{
		String filterForCity = "where cityCode='" + provinceCode + "'";
		CityValue city = new CityValue();
		city = dao.getCityNames(filterForCity).get(0);
		
		String filterForGetCitys = "where parentID='" + city.getCityID() + "'";
		Vector<CityValue> cityValues = dao.getCityNames(filterForGetCitys);
		CityValue cityValue = new CityValue();
		
		response.getWriter().print("<select name='bankCityName' id='bankCityName' style='width: 140px'>");
		for(int i=0;i<cityValues.size();i++){
			cityValue = cityValues.get(i);
			response.getWriter().print("<option value='" + cityValue.getCityCode() + "'>" + cityValue.getCityName() + "</option>");
		}
		response.getWriter().print("</select>");
	}
	
%>
