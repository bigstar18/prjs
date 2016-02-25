<%@ page language="java" pageEncoding="GBK"%>
<%@ page import="gnnt.MEBS.base.dao.DaoHelper"%>
<%@ page import="gnnt.MEBS.base.util.SysData"%>
<%@ page import="java.util.*" %>
<%@ page import="java.math.BigDecimal"%>
<%
	String brokerId = request.getParameter("brokerId");
	String firmId=request.getParameter("firmId");
	System.out.println(brokerId);
	String sql="select t.firmid from t_firm t where 1=1";
	if(brokerId.length()>0){
 		sql+=" and t.firmId in (select m.firmId from m_b_broker m where m.brokerId ='"+brokerId+"')";
	}
	if(firmId.length()>0){
		sql+=" and t.firmId like '%"+firmId+"%'";
	}
	sql+=" order by t.firmid asc";
	System.out.println(sql);
	DaoHelper dao = (DaoHelper)SysData.getBean("useBackDsDaoHelper");
	List firmIdList=dao.queryBySQL(sql);
	String data="";
	for(int i=0;i<firmIdList.size();i++){
		data+=firmIdList.get(i)+"-";
	}
	System.out.println(data);
	response.getWriter().print(data);
%>
