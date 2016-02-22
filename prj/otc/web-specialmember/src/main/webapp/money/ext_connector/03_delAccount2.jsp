<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.util.Date"/>
<%@ include file="../globalDef.jsp"%>
<%@ include file="../session.jsp"%>
<%@ page import = "com.hitrust.trustpay.client.market.IMarketTagName" %>
<%@ page import = "com.hitrust.trustpay.client.IFunctionID" %>
<base target="_self"> 
<%
	BankDAO dao = BankDAOFactory.getDAO();
	String firmID = request.getParameter("firmID");
	String bankID = request.getParameter("bankID");
	String falg = request.getParameter("falg");
	String RequestID =request.getParameter("RequestID");
	String CustSignInfo =request.getParameter("CustSignInfo");
	Vector<CorrespondValue> cps = dao.getCorrespondList(" and bankID='"+bankID+"' and firmid='"+firmID+"'");
	CorrespondValue corr = null;
	if(cps != null && cps.size()>0){
		corr = cps.get(0);
		corr.status = 0;
		//ErrorCode errorcode = new ErrorCode();
		//errorcode.load();
		new ErrorCode().load();
	}
	if(corr == null){
		out.print("δ��Ѱ���ͻ���Ϣ");
		return ;
	}
	ReturnValue result=new ReturnValue();
	ABCCapitalProcessorRMI cp = null;
		
		String headmsg = "�����ʽ��˺Ź���";
		String str=headmsg+"-��Լũ���˺�["+firmID+"]";
		String strs = "";
		if(corr == null){
			strs = "δ��Ѱ���˺���Ϣ";
		}else{
			if(corr.bankID == null || corr.bankID.trim().length()<=0){
				strs = "���в���Ϊ��";
			}
			else if(!delAccountBank(corr.bankID)){
				strs = corr.bankID+"���в����г��˽�Լ";
			}else if(corr.isOpen==0||corr.isOpen==2){
				strs = "���˻�δǩԼ���ѽ�Լ�����ܽ�Լ";
			}
			else{
				try {
					//�ദ����·��
					
					try{
						cp = getABCBankUrl(corr.bankID);
						if(falg.equals("del")){//�ж�ǩԼ 
			              RequestID=String.valueOf(cp.getMktActionID());
						}
						if(falg.equals("delsign")){
							corr.signInfo=CustSignInfo;
							corr.actionID=RequestID;
							System.out.println("corr.signInfo:"+corr.signInfo);
							if(CustSignInfo==null){
							result.result=-1;
							}
							result=cp.delAccountMaket(corr);
							strs=result.remark;
						}
					}catch(Exception e){
						e.printStackTrace();
					}
					
				} catch(Exception e) {
					strs = "�ʽ��˺�["+corr.firmID+"]��Լϵͳ�쳣";
					e.printStackTrace();
				}
				
				}
			}
			    
		    
	%>


<html>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=GBK">
	<script language="javascript" src="../../lib/tools.js"></script>
	<link type="text/css" rel="stylesheet" href="../../lib/jquery/style/validator.css"></link>
	</head>
  
  <body style="overflow-y: hidden" action=''>
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
   
  </body>
</html>
		<script>
			
			<%
			if("del".equals(falg)){
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
				var sign_offtime = '<%=IMarketTagName.MARKET_SIGN_SIGNOFFTIME%>'
	            var sign_offtime_desc = '<%=IMarketTagName.MARKET_SIGN_SIGNOFFTIME_DESC%>'
				
				var signdate = new Date();
					//�ͻ���ǩ�������õ�javascript�������ɺ�����javascript�����ϲ�
				//��form.name�Ž�[[...]]�У���form.name.value����ַ���������ǩ���ֶκϳ�һ���ַ���
				var signstring =   sign_merchantid + frm.MerchantID.value
								 + sign_merchanttrxno + frm.RequestID.value
								 + sign_functionid + '<%=IFunctionID.MARKET_SIGNOFF%>'
								 + sign_MerchantName + frm.MerchantName.value
								 + sign_CustName + frm.CustName.value
								 + sign_offtime  + signdate;
								 
				var TempString;							
				TempString="<�ͻ���Լȷ��>\n\n";
				TempString= TempString+sign_functionid_desc+"�����г���Լ"+"\n";				
				TempString= TempString+sign_merchanttrxno_desc+frm.RequestID.value+"\n";
				TempString= TempString+sign_MerchantName_desc+frm.MerchantName.value+"\n";
				TempString= TempString+sign_merchantid_desc+frm.MerchantID.value+"\n";
				TempString= TempString+sign_CustName_desc+frm.CustName.value+"\n";
				TempString= TempString+sign_offtime_desc+now;

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
			<%
			}else{
			%>
			alert('<%=strs%>');
			 document.frm.action="../firmInfo.jsp";
			<%
			}
			%>
			frm.falg.value='delsign';	
			frm.submit();
		</script>
