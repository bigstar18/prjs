<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.util.Date"/>
<%@ include file="../../globalDef.jsp"%>
<%@ include file="../../session.jsp"%>
<%@ include file="marketInfo.jsp"%>
<%@ page import="java.rmi.*"%>
<%@ page import="gnnt.trade.bank.util.*"%>
<%@ page import="gnnt.trade.bank.processorrmi.GFBCapitalProcessorRMI"%>
<base target="_blank">
<html>
<%
	String GFBBankID = "66";
	GFBBankDAO dao = BankDAOFactory.getGFBDAO();
	String firmID = request.getParameter("firmID");
	String pwd = request.getParameter("pwd");
	String signValue="";
	String bankID="66";
	int type=1;
	String datetime="";
	String computerIP = "127.0.0.1";
	ReturnValue rv = new ReturnValue();
	System.out.println("==============================>pwd=[" + pwd + "]");
	CorrespondValue corr = new CorrespondValue();
	Vector<CorrespondValue> vc = dao.getCorrespondList(" and firmID = '" + firmID + "' and bankid='66' ");
	String mgs = "";
	if(vc == null || vc.size() == 0){
		mgs = "�͹������İ󶨹�ϵΪ�գ�ǩԼʧ��";
	}else{
		corr = vc.get(0);
	}
	if(corr.isOpen == 1){
		mgs = "��ǩԼ�Ľ����̲�����ǩԼ";
	}else{
		
		rv.result = -999999;//��ֵ��ʼֵ����֤��ʼֵ�ķ��ش���ʧ��
		corr.bankCardPassword = pwd;
		GFBCapitalProcessorRMI cp = getGFBBankUrl(GFBBankID);
		rv = cp.openAccountMarket(corr);
		if(rv.result == 0){
			mgs = "ǩԼ��ϢУ��ɹ�";
			datetime=Tool.fmtDateTime(new java.util.Date());
			computerIP = request.getRemoteAddr();
			if(type==1){
				System.out.println("firmID:"+firmID+"���ͣ�"+type+"   ��ˮ�ţ�"+rv.actionId);
				signValue=MD5orSHA.rgstEncryption(MarkCode+"-"+rv.actionId,datetime,MarkCode,backgroundMerUrl,VerficationCode);
				System.out.println("firmID:"+firmID+"ǩ����"+signValue);
			}else if(type==2){System.out.println("firmID:"+firmID+"���ͣ�"+type+"   ��ˮ�ţ�"+rv.actionId+"  ʱ�䣺"+datetime);
				signValue=MD5orSHA.delEncryption(corr.account,MarkCode+"-"+rv.actionId,datetime,MarkCode,backgroundMerUrl,VerficationCode);
				System.out.println("firmID:"+firmID+"ǩ����"+signValue);
			}
		}else{
			mgs = rv.remark;
			if(mgs == null || "".equals(mgs)){
				mgs = "ǩԼʧ��";
			}
		}
	}
	
	
%>

<body>
<form name="frm" id="frm" method="post" >
<input type="hidden" id="tranCode"name="tranCode" value="<%=(type==1)?"10000":"10001"%>"/> 
<%if(type==2){System.out.println("�����˺ţ�"+corr.account);%>
<input type="hidden" id="contractNo" name="contractNo" value="<%=corr.account%>" size="50"/>
<%}%>         
<input type="hidden" id="merOrderNum"name="merOrderNum" value="<%=MarkCode+"-"+rv.actionId%>"/>       
<input type="hidden" id="merchantID"name="merchantID"value="<%=MarkCode%>"/>        
<input type="hidden" id="tranDateTime"name="tranDateTime"value="<%=datetime%>"/>      
<input type="hidden" id="merURL"name="merURL"value="<%=backgroundMerUrl%>"/>               
<input type="hidden" id="tranIP"name="tranIP"value="<%=computerIP%>"/>               
<input type="hidden" id="version"name="version"value="2.0"/>              
<input type="hidden" id="signType"name="signType"value="1"/>             
<input type="hidden" id="msgExt"name="msgExt"value="rgst"/>               
<input type="hidden" id="remark"name="remark"value="<%=frontMerUrl%> "/>               
<input type="hidden" id="remark1"name="remark1"value="<%=firmID%>"/>              
<input type="hidden" id="remark2"name="remark2"value="<%=corr.contact%>"/>
<input type="hidden" id="signValue"name="signValue"value="<%=signValue%>"/>
<input type="hidden" id="gopayServerTime"name="gopayServerTime"value=""/>
</form>
</body>
</html>
<SCRIPT LANGUAGE="JavaScript">

<%if(rv.result>=0&&"66".equals(bankID)){%>

	frm.action = '<%=netUrl%>';
	frm.submit();
<%}else{%>
alert('<%=rv.remark%>');
<%}%>
window.close();
</script>
