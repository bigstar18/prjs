<%@ page contentType="text/html;charset=GBK" %>

<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ include file="marketName.jsp" %>
<html>
	<%
	String title = request.getParameter("title");
	
	String reportName = null;
	String sign = request.getParameter("sign");
	if(sign != null && !"".equals(sign)){
		reportName = sign;
	}else{
	%>
	<script type="text/javascript">
		alert("û��ָ������Ĳ�ѯ���ݣ�");
	</script>
	<%
	reportName = "[***]";//��������str�����쳣
	}
	%>
<head>
<style media=print>
    .Noprint{display:none;}<!--�ñ���ʽ�ڴ�ӡʱ���طǴ�ӡ��Ŀ-->
</style>
<title><%=marketName%> - <%=title%></title>
</head>
<body>
	<table align="center" width="600px" border="0">
		<tr><td>&nbsp;</td></tr>
		<tr><td>&nbsp;</td></tr>
		<tr><td>&nbsp;</td></tr>
		<tr>
			<td>
				<table align="right" width="10%" border="0">
					<tr>
					<td align="right">
						<div align="right" id="butDivModUp" name="butDivModUp" class="Noprint">
		     		<input type="submit" onclick="javascript:window.print();" class="button" value="��ӡ">
		     		 
						</div>
					</td>
					</tr>
			</table>
			</td>
		</tr>
		<tr>
			<td>
				<table align="center" width="600px">
					<tr>
						<td>
					  </td>
					 </tr>
				</table>
			</td>
			</tr>
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
							<%//�ּ����̳ɽ�ͳ�Ʊ�
							}else if("tradeBybroker".equals(reportName.trim())){
							%>
							<jsp:include page="tradeBybroker.jsp" />
							<%//�ּ����̶���ͳ�Ʊ�
							}else if("holdBybroker".equals(reportName.trim())){ 
							%>
							<jsp:include page="holdBybroker.jsp" />
							<%//�ּ������ۺ�ͳ�Ʊ�
							}else if("brokerallReport".equals(reportName.trim())){ 
							%>
							<jsp:include page="brokerallReport.jsp" />
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
		<tr>
			<td>
				<table align="right" width="10%" border="0">
						<tr>
						<td align="right">
						<div align="right" id="butDivModDown" name="butDivModDown" class="Noprint">
						     <input type="submit" onclick="javascript:window.print();" class="button" value="��ӡ"> 
						</div>
						</td>
						</tr>
				</table>
			</td>	
		</tr>
	</table>
</body>
</html>
<SCRIPT LANGUAGE="JavaScript">
function replaceHtml(replacedStr,repStr,endStr){   
    var replacedStrF = "";   
    var replacedStrB = "";   
    var repStrIndex = replacedStr.indexOf(repStr);   
    while(repStrIndex != -1){   
        replacedStrF = replacedStr.substring(0,repStrIndex);   
        replacedStrB = replacedStr.substring(repStrIndex,replacedStr.length);   
        replacedStrB = replacedStrB.substring(replacedStrB.indexOf(endStr)+1,replacedStrB.length);   
        replacedStr = replacedStrF + replacedStrB;   
        repStrIndex = replacedStr.indexOf(repStr);   
    }   
    return replacedStr;   
}   
//elTalbeOut ���Ϊ�������ݵ��������Ҫ������border֮�����ʽ��elDiv��������������html����   
function htmlToExcel(elTableOut,elDiv){   
    try{   
        //���õ���ǰ�����ݣ�Ϊ�����󷵻ظ�ʽ������   
        var elDivStrBak = elDiv.innerHTML;   
        //����table��border=1��������excel�о��б���� ps:��л˫������   
        elTableOut.border=1;   
        //����elDiv����   
        var elDivStr = elDiv.innerHTML;   
        elDivStr = replaceHtml(elDivStr,"<A",">");   
        elDivStr = replaceHtml(elDivStr,"</A",">");   
        elDiv.innerHTML=elDivStr;      
           
        var oRangeRef = document.body.createTextRange();   
        oRangeRef.moveToElementText( elDiv );   
        oRangeRef.execCommand("Copy");   
           
        //���ظ�ʽ�任��ǰ������   
        elDiv.innerHTML = elDivStrBak;   
        //�������ݿ��ܴܺ����Ը���   
        elDivStrBak = "";   
        elDivStr = "";   
           
        var oXL = new ActiveXObject("Excel.Application")   
        var oWB = oXL.Workbooks.Add ;   
        var oSheet = oWB.ActiveSheet ;   
        oSheet.Paste();   
        oSheet.Cells.NumberFormatLocal = "@";   
        oSheet.Columns("D:D").Select   
        oXL.Selection.ColumnWidth = 20  
        oXL.Visible = true;        
        oSheet = null;   
        oWB = null;   
        appExcel = null;   
    }catch(e){   
        alert(e.description)   
    }   
}
function method1(tableid) {//������񿽱���EXCEL�� 
    var curTbl = document.getElementById(tableid); 
    var oXL = new ActiveXObject("Excel.Application"); 
    //����AX����excel 
    var oWB = oXL.Workbooks.Add(); 
    //��ȡworkbook���� 
        var oSheet = oWB.ActiveSheet; 
    //���ǰsheet 
    var sel = document.body.createTextRange(); 
    sel.moveToElementText(curTbl); 
    //�ѱ���е������Ƶ�TextRange�� 
    sel.select(); 
    //ȫѡTextRange������ 
    sel.execCommand("Copy"); 
    //����TextRange������  
    oSheet.Paste(); 
    //ճ�������EXCEL��       
    oXL.Visible = true; 
    //����excel�ɼ����� 
} 
</SCRIPT>