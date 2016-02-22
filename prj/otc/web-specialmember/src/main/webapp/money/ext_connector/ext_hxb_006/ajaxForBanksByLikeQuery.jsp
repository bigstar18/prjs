<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.util.Date"/>
<%@ include file="../../globalDef.jsp"%>
<%@ include file="../../session.jsp"%>
<%
    request.setCharacterEncoding("GBK");

	HXBBankDAO hxDao = BankDAOFactory.getHXBDAO();
	//String bank = new String(Tool.delNull(request.getParameter("bank")).getBytes("ISO-8859-1"),"GBK");
	//String filter = new String(Tool.delNull(request.getParameter("filter")).getBytes("ISO-8859-1"),"GBK");
	
	String bank = request.getParameter("bank");
	String filter = request.getParameter("filter");
	
	String filterSQL = "where nbkname like '%" + bank + "%' ";
	for (int i=0;i<filter.length();i++) {
		String subfilter = filter.substring(i, i + 1);
		if (!"省".equals(subfilter) && !"市".equals(subfilter) && !"县".equals(subfilter) && !"区".equals(subfilter)) {
			filterSQL = filterSQL + "and nbkname like '%" + subfilter + "%' ";
		}
	}
	System.out.println(filterSQL);
	
		
	
	if("".equals(bank)){
		response.getWriter().print("<select style='width: 300px'><option value='' selected>--请选择--</option></select><span>*</span>");
	}else{
		Vector<BanksInfoValue> list = hxDao.getBanksInfo(filterSQL);
		BanksInfoValue banksValue = new BanksInfoValue();
		if(list.size() == 0){
			response.getWriter().print("<select name='acctBankName' id='acctBankName' style='width: 300px' onChange='bankNameChange()'><option value='' selected>没有符合条件的数据</option></select><span>*</span>");
		}else{
			response.getWriter().print("<select name='acctBankName' id='acctBankName' style='width: 300px' onChange='bankNameChange()'><option value='' selected>--请选择--</option>");
			for(int i=0;i<list.size();i++){
				banksValue = list.get(i);
				response.getWriter().print("<option value='" + banksValue.nbkname + "'>" + banksValue.nbkname + "</option>");
			}
			response.getWriter().print("</select><span>*</span>");
		}
	}
	
%>
