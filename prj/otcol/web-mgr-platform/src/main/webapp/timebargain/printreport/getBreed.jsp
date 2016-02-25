<%@ page language="java" pageEncoding="GBK"%>
<%@ page import="gnnt.MEBS.base.dao.DaoHelper"%>
<%@ page import="gnnt.MEBS.base.util.SysData"%>
<%@ page import="java.util.*" %>
<%@ page import="java.math.BigDecimal"%>
<%
	String breedId = request.getParameter("breedId");
	String sql="select t.commodityID from t_commodity t";
	if( breedId !=null){
 		sql+=" where t.breedid='"+breedId+"'";
	}
	sql+=" order by t.commodityID";
	DaoHelper dao = (DaoHelper)SysData.getBean("useBackDsDaoHelper");
	List firmIdList=dao.queryBySQL(sql);
	String data="";
	for(int i=0;i<firmIdList.size();i++){
		data+=firmIdList.get(i)+"-";
	}
	response.getWriter().print(data);

%>