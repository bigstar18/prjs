<%@ page contentType="text/html;charset=GBK" %>
<%@ page import='gnnt.MEBS.finance.manager.VoucherManager' %>
<%@ page import='gnnt.MEBS.finance.unit.Summary' %>
<%
	String summaryNo = request.getParameter("summaryNo");
	String summaryC = null;
	String voucherT = null;
	String voucherType = null;
	if(summaryNo != null){
		Summary summary = VoucherManager.getSummaryByNo(summaryNo);
		//out.println(summary==null);
		summaryC = summary.getSummary();
	}
%>
<%=summaryC%>|||