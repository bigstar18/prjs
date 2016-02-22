<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.util.Date"/>
<%@ include file="../globalDef.jsp"%>
<%@ include file="../session.jsp"%>
<%@ page import = "com.hitrust.trustpay.client.market.IMarketTagName" %>
<%@ page import = "com.hitrust.trustpay.client.IFunctionID" %>
<base target="_self">
		
<%
	new ErrorCode().load();
	BankDAO dao = BankDAOFactory.getDAO();
	String firmID = request.getParameter("firmID");
	String bankID = request.getParameter("bankID");
	String RequestID =request.getParameter("RequestID");
	String CustSignInfo =request.getParameter("CustSignInfo");
	String falg = request.getParameter("falg");
	String account = request.getParameter("account").trim();
	System.out.println(firmID);
	Vector<CorrespondValue> cps = dao.getCorrespondList(" and firmID='"+firmID+"' and bankID='"+bankID+"'");
	CorrespondValue corr = null;
	if(cps != null && cps.size()>0){
		corr = cps.get(0);
	}else{
	   
		FirmValue firm = dao.getFirm(firmID);
		if(firm == null){
			out.print("δ��Ѱ���ͻ���Ϣ");
			return;
		}else{
			corr = new CorrespondValue();
			corr.firmID = firm.firmID;
			corr.contact = firm.contact;
			corr.card = firm.card;
			corr.cardType = firm.cardType;
			corr.accountName = firm.firmName;
			corr.account=account;
		}
	}
	
	ABCCapitalProcessorRMI cp = null;
	ReturnValue result=new ReturnValue();
		
		
		String str="�����ʽ��˺Ź���-ǩԼũ���˺�["+firmID+"]";
		String strs = "";
		if(corr == null){
			strs = "-δ��Ѱ���˺���Ϣ";
		}else{
			corr.bankID = request.getParameter("bankID");
			
			if(corr.bankID == null || corr.bankID.trim().length()<=0){
				strs += "���в���Ϊ��";
			}else if(!isRegstatus(corr.bankID)){
				strs += corr.bankID+"�����в����г������ǩԼ";
			}else if(corr.isOpen==1){
				strs += "���˻���ũ���Ѿ���ǩԼ״̬�������ظ�ǩԼ";
			}else{
				try {
					//�ദ����·��
					
					try{
						cp = getABCBankUrl(corr.bankID);
						if(falg.equals("rgst")){
						RequestID=String.valueOf(cp.getMktActionID());
						}
						if(falg.equals("rgstsign")){
							corr.signInfo=CustSignInfo;
							corr.actionID=RequestID;
							corr.account=account;
							if(CustSignInfo==null){
							result.result=-1;
							}
							result=cp.openAccountMarket(corr);
							strs=result.remark;
						}
					}catch(Exception e){
						e.printStackTrace();
					}
					
					
				} catch(Exception e) {
				    
					strs = "�����˺�["+corr.contact+"]������["+corr.bankID+"]ǩԼʱ����ϵͳ�쳣";
					e.printStackTrace();
				}
				
				}
				
		}
		
%>
	
	

<html xmlns:MEBS>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=GBK">
	<script language="javascript" src="../tools.js"></script>
    
  </head>
  
  <body style="overflow-y: hidden" >
  <object classid=clsid:62B938C4-4190-4F37-8CF0-A92B0A91CC77 
		codebase="NetSign.cab" data=data:application/x-oleobject;base64,xDi5YpBBN0+M8KkrCpHMdwADAACJEwAAPAcAAA== 
		id=InfoSecNetSign1 style="HEIGHT: 0px; WIDTH: 0px" VIEWASTEXT width="0" height="0">
    <embed width="0" height="0" src="data:application/x-oleobject;base64,xDi5YpBBN0+M8KkrCpHMdwADAACJEwAAPAcAAA==">
    </embed> 
</object>
<form name="frm" action="" method="post">
<input type="hidden" name="RequestID" value="<%=RequestID%>">
<input type="hidden" value="" name="CustSignInfo">
<input type="hidden" name="MerchantID" value="<%=Tool.getConfig("MarkCode")%>">
<input type="hidden" name="MerchantName" value="<%=Tool.getConfig("MerchantName")%>">
<input type="hidden" value="<%=corr.accountName%>" name="CustName">
<input type="hidden" value="<%=falg%>" name="falg">
<input type="hidden" value="<%=firmID%>" name="firmID">
<input type="hidden" value="<%=bankID%>" name="bankID">
<input type="hidden" value="<%=account%>" name="account">
  </body>
</html>
	<script>
		
			
			<%
				if("rgst".equals(falg)){
			%>
			
			    var now = new Date();
				var year = now.getYear(); 
				var month = eval(now.getMonth())+1 ;
				month = eval(month)<10?'0'+month:month;
				var day = now.getDate() ;
				day = eval(day)<10?'0'+day:day;
				var reqID = "RSU"+(year+'').substring(2,4)+month+day+"001";
				
				var sign_merchantid = '<%=IMarketTagName.MARKET_SIGN_MERCHANTID%>'
				var sign_merchantid_desc = '<%=IMarketTagName.MARKET_SIGN_MERCHANTID_DESC%>'
				var sign_merchanttrxno = '<%=IMarketTagName.MARKET_SIGN_TRXNO%>'
				var sign_merchanttrxno_desc = '<%=IMarketTagName.MARKET_SIGN_TRXNO_DESC%>'
				var sign_functionid = '<%=IMarketTagName.MARKET_SIGN_FUNCTIONID%>'
				var sign_functionid_desc = '<%=IMarketTagName.MARKET_SIGN_FUNCTIONID_DESC%>'
				var sign_MerchantName = '<%=IMarketTagName.MARKET_SIGN_MERCHANTNAME%>'
				var sign_MerchantName_desc = '<%=IMarketTagName.MARKET_SIGN_MERCHANTNAME_DESC%>'
				var sign_CustName = '<%=IMarketTagName.MARKET_SIGN_CUSTNAME%>'
				var sign_CustName_desc = '<%=IMarketTagName.MARKET_SIGN_CUSTNAME_DESC%>'
				var sign_time = '<%=IMarketTagName.MARKET_SIGN_SIGNTIME%>'
				var sign_time_desc = '<%=IMarketTagName.MARKET_SIGN_SIGNTIME_DESC%>'
				
				var signdate = new Date();
					//�ͻ���ǩ�������õ�javascript�������ɺ�����javascript�����ϲ�
				//��form.name�Ž�[[...]]�У���form.name.value����ַ���������ǩ���ֶκϳ�һ���ַ���
				var signstring =   sign_merchantid + frm.MerchantID.value
								 + sign_merchanttrxno + frm.RequestID.value
								 + sign_functionid + '<%=IFunctionID.MARKET_SIGNUP%>'
								 + sign_MerchantName + frm.MerchantName.value
								 + sign_CustName + frm.CustName.value
								 + sign_time + signdate;
								 
				var TempString;							
				TempString="<�ͻ�ǩԼȷ��>\n\n";
				TempString= TempString+sign_functionid_desc+"�����г�ǩԼ"+"\n";				
				TempString= TempString+sign_merchanttrxno_desc+frm.RequestID.value+"\n";
				TempString= TempString+sign_MerchantName_desc+frm.MerchantName.value+"\n";
				TempString= TempString+sign_merchantid_desc+frm.MerchantID.value+"\n";
				TempString= TempString+sign_CustName_desc+frm.CustName.value+"\n";
				TempString= TempString+sign_time_desc+now;

				var sure = confirm("��ȷ����Ҫ�ύ��ǩ����Ϣ:\n\n" + TempString);
		
				if(sure == false) {					
					window.close();
				}else{
				if(typeof(InfoSecNetSign1)  ==  "undefined"){  
					alert("�����K������װK������");  
				}else {
					try{ 					
						InfoSecNetSign1.addFormItem(signstring);
						InfoSecNetSign1.addFormItem(TempString);
						InfoSecNetSign1.makeAttachedSign();
						frm.CustSignInfo.value = InfoSecNetSign1.attachedSign;
					}catch(err){
						alert("�����K������װK������");  
						window.close();
					}
				}	    
				}
				if(frm.CustSignInfo.value.length == 0){
				     alert("ǩ������Ϊ��");
					 window.close();
				}
				
				frm.falg.value='rgstsign';	
				frm.submit();
					
			
	
			<%
			}else{
			%>
			alert('<%=strs%>');
			window.returnValue="1";
		    window.close();
			<%
			}
			%>
		</script>
