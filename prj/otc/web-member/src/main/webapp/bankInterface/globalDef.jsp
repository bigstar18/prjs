<%@ page contentType="text/html;charset=GBK" %>
<%@ page import="java.util.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.math.BigDecimal" %>

<%@ page import="gnnt.trade.bank.dao.*"%>
<%@ page import="gnnt.trade.bank.vo.*"%>
<%@ page import="gnnt.trade.bank.util.*"%>

<%@ page import="java.rmi.*"%>
<%@ page import="gnnt.trade.bank.processorrmi.CapitalProcessorRMI"%>
<%@ page import="gnnt.trade.bank.processorrmi.CCBCapitalProcessorRMI"%>

<%@ page import="gnnt.MEBS.common.security.util.*"%>
<%@ page import="gnnt.MEBS.common.security.*"%>
<%
response.setHeader("Cache-Control","no-cache");
response.setHeader("Cache-Control","no-store");
response.setHeader("Pragma","no-cache");
%>
<%!
// ��ҳĬ��ÿҳ��С
public final int BANKPAGESIZE = 15;
//��Ա��ͻ����
public final String FIRMTITLE = "�����˺�";
//�����˺�
public final String CONTACTTITLE = "�����˺�";
//�����˺�
public final String ACCOUNTTITLE = "�����˺�";
//�����˺�����
public final String ACCOUNTNAMETITLE = "�˺�����";
//��������
public final String BANKTITLE = "����";
//�������
public final String SEQUNCE = "���";
// ����Ϊ�յ��ַ���
public String replaceNull(String s){
  if(s==null||"".equals(s.trim())||"null".equals(s.trim())) return "-";
  else return s;
}
//֧���г���ǩԼ
public boolean openAccountBank(String bankID){
	if(bankID.startsWith("1") || "005".equals(bankID)){
		return true;
	}
	return false;
}
//֧���г��˽�Լ
public boolean delAccountBank(String bankID){
	if(bankID.startsWith("1") || "005".equals(bankID) || "006".equals(bankID)){
		return true;
	}
	return false;
}
//�жϷ������������
public int sendQSBank(String bankID){
	if("01".equals(bankID)){
		return 1;
	}
	return 0;
}
//����Ҫ���������˺ŵ�����
public boolean checkAccount(String bankID){
	if("005".equals(bankID) || "006".equals(bankID)){//ũҵ����
		return true;
	}
	return false;
}
%>
<%
	/*String RmiIpAddress = Tool.getConfig("RmiIpAddress");
	String RmiPortNumber = Tool.getConfig("RmiPortNumber");
	String RmiServiceName = Tool.getConfig("RmiServiceName");*/
	String computerIP = request.getRemoteAddr();
%>
<%!
StaticMsg staticMsg = new  StaticMsg();
	public String getProcUrl(String bankID){
		String procUrl = "";
	if(bankID != null && bankID.trim().length()>0&&!bankID.toLowerCase().equals("null")){
		procUrl = staticMsg.getBank(bankID).beleiveProcessor;
	}
	if(procUrl == null || procUrl.trim().length()<=0){
		procUrl = staticMsg.getBankMap().values().iterator().next().beleiveProcessor;
	}
	return procUrl;
	}
public CapitalProcessorRMI getBankUrl(String bankID){
	CapitalProcessorRMI result = null;
	String procUrl=getProcUrl(bankID);
	try{
		result = (CapitalProcessorRMI)Naming.lookup(procUrl);
	}catch(Exception e){
		e.printStackTrace();
	}
	return result;
}
public CCBCapitalProcessorRMI getCCBBankUrl(String bankID){
	CCBCapitalProcessorRMI result = null;
	String procUrl =getProcUrl(bankID);
	try{
		result = (CCBCapitalProcessorRMI)Naming.lookup(procUrl);
	}catch(Exception e){
		e.printStackTrace();
	}
	return result;
}
%>

<script type="text/javascript">
//��ǩԼ����
function openAccountBank(bankID){
	if(bankID.substr(0,1)==1 || "005"==bankID){
		return true;
	}
	return false;
}
//�ɽ�Լ����
function delAccountBank(bankID){
	if(bankID.substr(0,1)==1 || "005"==bankID || "006"==bankID){
		return true;
	}
	return false;
}
//�������ʱ����
function gotoBankQS(){
	var bankID = frm.bankID.value;
	if(bankID == ""){
		alert("��ѡ����������");
	}else{
		frm.action="<%=request.getContextPath()%>/bankInterface/compareInfo/"+bankQsPage(bankID);
		frm.submit();
	}
}
//ȷ��ÿ������ҳ���λ��
function bankQsPage(bankID){
	if(bankID == "-1"){//���س�ʼ��ҳ��
		return "qs.jsp";
	}if(bankID == "01"){//��������
		return "dz/demo/DEMO.jsp";
	}
}
</script>
