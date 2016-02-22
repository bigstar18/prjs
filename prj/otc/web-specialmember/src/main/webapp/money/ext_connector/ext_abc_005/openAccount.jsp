<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.util.Date"/>
<%@ include file="../../globalDef.jsp"%>
<%@ include file="../../session.jsp"%>
<base target="_self"> 
<%
	new ErrorCode().load();
	BankDAO dao = BankDAOFactory.getDAO();
	String firmID = request.getParameter("firmID");
	String contact = request.getParameter("contact");
	String bankID = request.getParameter("bankID");
	
	
	Vector<CorrespondValue> cps = dao.getCorrespondList(" and firmid='"+firmID+"' and bankID='"+bankID+"'");
	CorrespondValue corr = null;		   
	FirmValue firm = dao.getFirm(firmID);		
	corr = new CorrespondValue();
	corr.firmID = firm.firmID;
	corr.contact = firm.contact;
	corr.card = firm.card;
	corr.cardType = firm.cardType;
    corr.bankID = request.getParameter("bankID");
	//corr.bankID = "050";
	corr.account = request.getParameter("account").trim();
	
	ABCCapitalProcessorRMI cp = null;
	int f =1;
		
		long bak = -1;		
		String strs =request.getParameter("sReturnMsg");
		
		try {
			//多处理器路由
			
			try{
				cp = getABCBankUrl(corr.bankID);
			}catch(Exception e){
				e.printStackTrace();
			}
			if("0000".equals(request.getParameter("sReturnCode"))){
				String sCustomer = request.getParameter("sCustomer");
				String[] aa = sCustomer.split("\\|");
				corr.account1= aa[0];
				corr.accountName=aa[1];
				ReturnValue result=cp.openAccountAbc(corr);
				if(result.result<0){
				
					strs = result.remark;
					if(strs == null || strs.trim().length()<=0){
						if(ErrorCode.error.get(result.result) != null){
							strs = ErrorCode.error.get(result.result);
						}else{
							strs = "返回码 "+ result.result;
						}
					}
				}else{
				
					f =0;
					strs= "交易账号["+corr.contact+"]在银行["+corr.bankID+"]成功";
					bak = 0;
				}
			}
		} catch(Exception e) {
		    
			strs = "交易账号["+corr.contact+"]在银行["+corr.bankID+"]签约时返回系统异常";
			e.printStackTrace();
		}
		InterfaceLog log = new InterfaceLog();
		LogEndmsg endmsg = new LogEndmsg();
		log.account = corr.account;
		log.bankID = corr.bankID;
		log.contact = corr.contact;
		//endmsg.code = String.valueOf(f);						
		log.type = 3;									
		endmsg.note = strs;		
		log.result = f;			
		log.endMsg = endmsg.toString();
		cp.interfaceLog(log);
				
			
		
	%>
		<script>
			alert('<%=strs%>');
			<%
				if(bak == 0){
			%>
				window.returnValue="1";
				window.close();
			<%
				}
			%>
			window.close();
		</script>

