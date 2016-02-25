<%@ page contentType="text/html;charset=GBK" %>
<%@ page import="gnnt.MEBS.finance.base.dao.DaoHelper"%>
<%@ page import="gnnt.MEBS.finance.base.util.SysData"%>
<%@ page import="java.math.BigDecimal"%>
<%@ page import="java.util.*" %>
<%
	//get query condition and handle 
	String outCleardate = request.getParameter("clearDate"); 
	String startFirmID = request.getParameter("startFirmID");
	String endFirmID = request.getParameter("endFirmID");
	
	String filter = " 1=1 ";
	if(outCleardate != null){
		filter += " and t.cleardate=to_date('"+outCleardate +"','yyyy-MM-dd')";
	}
	if(startFirmID != null){
		filter += " and t.firmid>='"+startFirmID +"'";
	}
	if(endFirmID != null){
		filter += " and t.firmid<='"+endFirmID +"'";
	}
	//query data
	String sql = " select firmid from T_H_firm t where "+filter;	
		DaoHelper dao = (DaoHelper)SysData.getBean("daoHelper");
	    List outSidelist=dao.queryBySQL(sql);    
	    
    	%>    		   
	<br><br>
		<center colspan="4" class="reportHead">综合日报表</center>
	<%
		String signmark = "hasValue";
	for(int outSide = 0 ; outSide < outSidelist.size() ; outSide ++){
		Map outSidemap = (Map)outSidelist.get(outSide);
		String outFirmID = (String)outSidemap.get("firmid");
		%>
		<jsp:include page="zijinjiesuan.jsp">
			<jsp:param value="<%=signmark %>" name="outSign"/>
			<jsp:param value="<%=outFirmID %>" name="firmID"/>
			<jsp:param value="<%=outCleardate %>" name="cleardate"/>
		</jsp:include>
		<jsp:include page="bargainResult.jsp">
			<jsp:param value="<%=signmark %>" name="outSign"/>
			<jsp:param value="<%=outFirmID %>" name="firmID"/>
			<jsp:param value="<%=outCleardate %>" name="cleardate"/>
		</jsp:include>
		<jsp:include page="transferProfitAndLoss.jsp">
			<jsp:param value="<%=signmark %>" name="outSign"/>
			<jsp:param value="<%=outFirmID %>" name="firmID"/>
			<jsp:param value="<%=outCleardate %>" name="cleardate"/>
		</jsp:include>
		<jsp:include page="indentCollect.jsp">
			<jsp:param value="<%=signmark %>" name="outSign"/>
			<jsp:param value="<%=outFirmID %>" name="firmID"/>
			<jsp:param value="<%=outCleardate %>" name="cleardate"/>
		</jsp:include>
		
		<br><br>
		<br><br>
		<br><br>
	<%
	}
	%>