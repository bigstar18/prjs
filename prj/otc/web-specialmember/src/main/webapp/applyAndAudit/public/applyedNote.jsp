<%@ page pageEncoding="GBK"%>
<%@page import="gnnt.MEBS.audit.service.ParmaLogServiceJDBC"%><%@page import="gnnt.MEBS.base.query.jdbc.QueryConditions"%>
<%@page import="java.util.List"%><%@page import="gnnt.MEBS.audit.model.ParmaLog"%>
<%@page import="java.util.Map"%>
<%@page import="gnnt.MEBS.base.copy.XmlToMap"%><%@page import="gnnt.MEBS.base.util.SpringContextHelper"%>
<%@page import="gnnt.MEBS.base.query.hibernate.QueryHelper"%><%@page import="java.util.Date"%>




<table border="0" width="100%" align="center"  class="st_bor">
	<tr>
	<%
	ParmaLogServiceJDBC parmaLogService = (ParmaLogServiceJDBC)SpringContextHelper.getBean("parmaLogServiceJDBC");
	QueryConditions qc = new QueryConditions();
	qc.addCondition("operateGflag","=",(String)request.getAttribute("applyType"));
	
	Map<String, String> map = QueryHelper.getMapFromRequest(request, "obj.");
	for(String key :map.keySet() ){
		qc.addCondition("extractValue(xmlType(primary.currentvalue), '/root/"+key+"')","=",map.get(key));
	}
	List<Map<String,Object>> resultList =  parmaLogService.getApplyList(qc,null);
	List<Map<String,Object>> DateList =  parmaLogService.getSystemStatusDate(null,null);
		if(resultList.size()>0){
			%>
			<div class="div_cxtj"><img src="<%=skinPath%>/cssimg/13.gif" />&nbsp;&nbsp;记&nbsp;&nbsp;录</div>
			<td>
		<%
			
			for(int i = 0; i< resultList.size();i++){
				Date  nextTradeDate =(Date)(DateList.get(0).get("nexttradedate"));
				String operateContent = resultList.get(i).get("operateContent").toString();
				Date operateTime = (Date)(resultList.get(i).get("operatetime"));
				String operator = resultList.get(i).get("operator").toString();
				request.setAttribute("nextTradeDate",nextTradeDate);
				%>
				
					<tr>
						<td><% if(i!=0){ %><hr/><%} %>记录<%=i+1 %>：</td>
					</tr>
					<tr>
						<td>详细信息：此申请由<%=operator %>提交,生效时间;<fmt:formatDate pattern="yyyy-MM-dd" value="${nextTradeDate}"/></td>
					</tr>
					<tr>
						<td>申请修改内容：<%=operateContent %></td>
					</tr>
			<%	
			}
		}
	%>
	</td>
</tr>
</table>