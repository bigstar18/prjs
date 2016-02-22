<%@ page pageEncoding="GBK"%>
<%@page import="gnnt.MEBS.audit.service.ParmaLogServiceJDBC"%>
<%@page import="gnnt.MEBS.base.query.jdbc.QueryConditions"%>
<%@page import="java.util.List"%><%@page import="gnnt.MEBS.audit.model.ParmaLog"%>
<%@page import="java.util.Map"%><%@page import="gnnt.MEBS.base.util.SpringContextHelper"%>
<%@page import="gnnt.MEBS.base.query.hibernate.QueryHelper"%><%@page import="java.util.Date"%>
<%@page import="java.lang.Integer"%>
	<%
	ParmaLogServiceJDBC parmaLogService2 = (ParmaLogServiceJDBC)SpringContextHelper.getBean("parmaLogServiceJDBC");
	QueryConditions qc2 = new QueryConditions();
	Map<String, String> map2 = QueryHelper.getMapFromRequest(request, "obj.");
	String commodityId =map2.get("commodityId");
	//String id =map.get("id");
	if(commodityId!=null&&commodityId!=""){
		qc2.addCondition("primary.commodityId","=",commodityId);
		List<Map<String,Object>> resultList2 =  parmaLogService2.getComodityStatus(qc2,null);
		if(resultList2.size()>0){
			for(int i = 0; i< resultList2.size();i++){
				String status = resultList2.get(i).get("status").toString();
				if(status.endsWith("2")){
					request.setAttribute("isOpenOperationButton","N");
				}else{
					request.setAttribute("isOpenOperationButton","");
				}
			}
		}
	}
	//else if(id!=null&&id!=""){
	//	qc2.addCondition("primary.commodityId","=",id);
	//}
	
%>
