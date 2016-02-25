<meta http-equiv="content-type" content="text/html;charset=gbk">
<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="util.jsp" %>
<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ include file="marketName.jsp" %>

<%
	String title = request.getParameter("title");
	String reportName = null;
	String sign = request.getParameter("sign");

 SimpleDateFormat tempDate = new SimpleDateFormat("yyyyMMdd HHmm");
	        String datetime = tempDate.format(new java.util.Date());	 
	if(sign != null && !"".equals(sign)){
		reportName = sign;
	}else{
	reportName = "[***]";//��������str�����쳣
	}
	StringBuffer sb=new StringBuffer();
	for(int i=0;i<title.length();i++){
		char c=title.charAt(i);
		if(c>=0 && c<=255){
			sb.append(c);
		}
		byte []b;
		try{
			b=Character.toString(c).getBytes("utf-8");
		}catch(Exception e){
			b=new byte[0];
		}
		for(int j=0;j<b.length;j++){
			int k=b[j];
			if(k<0){
				k+=256;
			}
			sb.append("%"+Integer.toHexString(k).toUpperCase());
		}
	}
	title=sb.toString();
	response.reset();
	response.setContentType("application/vnd.ms-excel;charset=GBK");
	response.setHeader("Content-disposition","attachment; filename="+title+datetime +".xls");
%>

<table align="center">
		<tr>
			<td>
				<div id = ediv>
				<table align="center" height="400px" width="800px" border="0" id ="tableList">
					<tr>
						<td valign="top">
							<%//�ɽ���¼��
							if("bargainResult".equals(reportName.trim())){
							%>
							<jsp:include page="bargainResult.jsp" />
							<%//ת��ӯ����ϸ��
							}else if("transferProfitAndLoss".equals(reportName.trim())){
							%>
							<jsp:include page="transferProfitAndLoss.jsp" />
							<%//�������ܱ�
							}else if("indentCollect".equals(reportName.trim())){
							%>
							<jsp:include page="indentCollect.jsp" />
							<%//�ɽ���ͳ�Ʊ�
							}else if("tradeMonth".equals(reportName.trim())){
							%>
							<jsp:include page="tradeMonth.jsp" />
							<%//�������������¼��
							}else if("cushInAndOut".equals(reportName.trim())){
							%>
							<jsp:include page="cushInAndOut.jsp" />
							<%//���ж������ܱ�
							}else if("ownGoodsCollect".equals(reportName.trim())){
							%>
							<jsp:include page="ownGoodsCollect.jsp" />
							<%//������ϸ��
							}else if("indentDetail".equals(reportName.trim())){
							%>
							<jsp:include page="indentDetail.jsp" />
							<%//ת�úͽ���ӯ��ͳ�Ʊ�
							}else if("tAPLStatistic".equals(reportName.trim())){
							%>
							<jsp:include page="tAPLStatistic.jsp" />
							<%//������ͳ�Ʊ�
							}else if("serviceFee".equals(reportName.trim())){
							%>
							<jsp:include page="serviceFee.jsp" />
							<%//����ͳ�Ʊ�
							}else if("indentStatistic".equals(reportName.trim())){
							%>
							<jsp:include page="indentStatistic.jsp" />
							<%//����Ʒ�ɽ���ͳ�Ʊ�
							}else if("comTrade".equals(reportName.trim())){
							%>
							<jsp:include page="comTrade.jsp" />
							<%//���շ���Ʒ�ɽ���ͳ�Ʊ�
							}else if("comTradeday".equals(reportName.trim())){
							%>
							<jsp:include page="comTradeday.jsp" />
							<%//�ֽ����̳ɽ���ͳ�Ʊ�
							}else if("comTradeDayRecord".equals(reportName.trim())){
							%>
							<jsp:include page="comTradeDayRecord.jsp" />
							<%//ÿ�����������
							}else if("dayHQ".equals(reportName.trim())){
							%>
							<jsp:include page="dayHQ.jsp" />
							<%//���շ���Ʒ�ɽ���¼��
							}else if("commodityTradeDayRecord".equals(reportName.trim())){
							%>
							<jsp:include page="commodityTradeDayRecord.jsp" />
							<%//���ճɽ���¼��
							}else if("tradeDayResult".equals(reportName.trim())){
							%>
							<jsp:include page="tradeDayResult.jsp" />
							<%//���ռ�¼��
							}else if("tradeResult".equals(reportName.trim())){
							%>
							<jsp:include page="tradeResult.jsp" />
							<%//�ֶ���¼��
							}else if("diDingResult".equals(reportName.trim())){
							%>
							<jsp:include page="diDingResult.jsp" />
							<%//����ͳ�Ʊ�
							}else if("HQStatistic".equals(reportName.trim())){
							%>
							<jsp:include page="HQStatistic.jsp" />
							<%//Э��ת�ü�¼��
							}else if("agreePCRecord".equals(reportName.trim())){
							%>
							<jsp:include page="agreePCRecord.jsp" />
							<%//��Ϊת�ü�¼��
							}else if("repTransRecord".equals(reportName.trim())){
							%>
							<jsp:include page="repTransRecord.jsp" />
							<%//�ʽ��㽻���������
							}else if("tradeUserNotEnoughMoney".equals(reportName.trim())){
							%>
							<jsp:include page="tradeUserNotEnoughMoney.jsp" />
							<%//�ʽ�����
							}else if("zijinjiesuan".equals(reportName.trim())){
							%>
							<jsp:include page="zijinjiesuan.jsp" />
							<%//�ʽ��ձ���
							}else if("delayFinance".equals(reportName.trim())){
							%>
							<jsp:include page="delayFinance.jsp" />
							<%//���ջ��ܱ�
							}else if("bargainOnCollect".equals(reportName.trim())){
							%>
							<jsp:include page="bargainOnCollect.jsp" />
							<%//��������ͳ�Ʊ�
							}else if("superAddBail".equals(reportName.trim())){
							%>
							<jsp:include page="superAddBail.jsp" />
							<%//�ʽ��±���
							}else if("firmSettle".equals(reportName.trim())){
							%>
							<jsp:include page="firmSettle.jsp" />
							<%//ί�������
							}else if("orders".equals(reportName.trim())){
							%>
							<jsp:include page="orders.jsp" />
							<%//�ۺ��ձ���
							}else if("todayTogether".equals(reportName.trim())){
							%>
							<jsp:include page="todayTogether.jsp" />
							<%//�ʽ��±���
							}else if("monthFinance".equals(reportName.trim())){
							%>
							<jsp:include page="monthFinance.jsp" />
							<%// �������ʽ𱨱�
							}else if("brokerFundflow".equals(reportName.trim())){
							%>
							<jsp:include page="brokerFundflow.jsp" />
							<%// �����̷���Ʒӯ����
							}else if("firmCommodityTradePL".equals(reportName.trim())){
							%>
							<jsp:include page="firmCommodityTradePL.jsp" />
							<%// �����̷���Ʒ�ɽ���
							}else if("firmCommodityTrade".equals(reportName.trim())){
							%>
							<jsp:include page="firmCommodityTrade.jsp" />
							<%// ������Լ���ܱ�
							}else if("contractCollect".equals(reportName.trim())){
							%>
							<jsp:include page="contractCollect.jsp" />
							<%//�ּ������ۺ�ͳ�Ʊ�
							}else if("brokerallReport".equals(reportName.trim())){
							%>
							<jsp:include page="brokerallReport.jsp" />
							<%//�ּ����̶���ͳ�Ʊ�
							}else if("holdBybroker".equals(reportName.trim())){ 
							%>
							<jsp:include page="holdBybroker.jsp" />
							<%//�ּ����̳ɽ�ͳ�Ʊ�
							}else if("tradeBybroker".equals(reportName.trim())){ 
							%>
							<jsp:include page="tradeBybroker.jsp" />
							<%
							}// �����̳ɽ������
						    else if("brokerFirmTrade".equals(reportName.trim())){
							%>
							<jsp:include page="brokerFirmTrade.jsp" />
							<%
						    }else{
							%>
							<center>û�пɲ�ѯ����...</center>
							<%
							}
							%>
					  </td>
					 </tr>
					 <tr><td></td></tr>
				</table>
				</div>
			</td>	
		</tr>
	</table>