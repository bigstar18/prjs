<%@ page contentType="text/html;charset=GBK" %>
<%@page import="gnnt.trade.bank.vo.BankValue"%>
<%@page import="gnnt.trade.bank.CapitalProcessor"%>

<%@ page import="java.util.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.math.BigDecimal" %>

<%@ page import="gnnt.trade.bank.dao.*"%>
<%@ page import="gnnt.trade.bank.vo.*"%>
<%@ page import="gnnt.trade.bank.util.*"%>
<%@ page import = "com.hitrust.trustpay.client.market.IMarketTagName" %>
<%@ page import = "com.hitrust.trustpay.client.IFunctionID" %>
<%@ page import="java.rmi.*"%>
<%@ page import="gnnt.trade.bank.processorrmi.CapitalProcessorRMI"%>
<jsp:directive.page import="java.text.DecimalFormat"/>
<%@ include file="globalDef.jsp"%>
<%@include file="session.jsp"%>
<%@include file="ajax.jsp"%>

<%
  String bankID = request.getParameter("bankID");
  System.out.println("bankID:"+bankID);

	ABCCapitalProcessorRMI cp = null;
	try {
		cp = getABCBankUrl(bankID);
		 System.out.println("cp:"+cp);
	} catch(Exception e) {
		e.printStackTrace();
	}
	//�����̵����а��б�
	Vector<CorrespondValue> cvs = cp.getCorrespondValue("and  firmId='"+((String)session.getAttribute("REGISTERID"))+"'");

String firmID = (String)session.getAttribute("REGISTERID");
if(firmID == null)
{
	out.println("��Ч�Ľ����̴��룡");
	return;
}

String datetime=Tool.fmtDateTime(new java.util.Date());
String code = request.getParameter("code");
long actionid=0;
String falg = request.getParameter("falg");
String tOldNo = request.getParameter("tOldNo");
String tNewNo = request.getParameter("tNewNo");
String str="";
System.out.println("falg:"+falg);
ReturnValue result=new ReturnValue();
CorrespondValue corr=new CorrespondValue();
CorrespondValue corr1=new CorrespondValue();
System.out.println(falg);
if(cvs.size()>0){
	corr = cvs.get(0);	
	bankID=corr.bankID;
	
         try
        {
           if(isRegstatus(corr.bankID)){
           	if (corr.bankID.equals("050")){
           		if(falg.equals("mod0000")){//������ǰ�����ˮ          		          			
           			   corr.account=tOldNo;
                       corr1.account=tNewNo.trim();          			   
           			   result=cp.modAccount(corr,corr1);//����
           		
           			str=str+"����ɹ�";
           		}else{//���д���ʧ��
           			str=request.getParameter("msg");
           		}	
           	}else{
           		if(falg.equals("rgst")){//ǩԼ
           			str=str+"openAccountMarket";
                     			result=cp.openAccountMarket(corr);
                     		}else if(falg.equals("del")){//��Լ
                     			str=str+"delAccountMaket";
                     			result=cp.delAccountMaket(corr);
                     		}
                     	}
          }else{
                     	str = str+corr.bankID+"���в���ǩ��Լ";
                    }
        }catch(Exception e){
             e.printStackTrace();
        }
               
		 InterfaceLog log = new InterfaceLog();
		LogEndmsg endmsg = new LogEndmsg();
		log.account = tOldNo;
		log.bankID = bankID;
		log.contact = corr.contact;
		endmsg.code = code;				
		if(falg.contains("mod")){	
			
			log.type = 9;
							
		endmsg.note = request.getParameter("msg");

		if(!falg.contains("0000")){
			log.result = 1;
		}
		log.endMsg = endmsg.toString();
		cp.interfaceLog(log);
		}
			
			
}

%>

<body>
<form name="frm" action="" method="post">

</form>
</body>
<SCRIPT LANGUAGE="JavaScript">
    
<%if(bankID.equals("050")&&falg.equals("update")){%>
alert("����");
document.frm.action="demo/ModifCustAccNo.jsp";
<%}else{
  if(!falg.contains("mod")){
%>
alert("<%=str%>"+"<%=result.remark%>");
<%}%>
document.frm.action="firmInfo.jsp";
<%}%>
frm.submit();
</SCRIPT>
