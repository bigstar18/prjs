<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.util.Date"/>
<%@ include file="../../globalDef.jsp"%>
<%@ include file="../../session.jsp"%>
<base target="_self"> 
<%
	BankDAO dao = BankDAOFactory.getDAO();
	String firmID = request.getParameter("firmID");
	String contact = request.getParameter("contact");
	String bankID = request.getParameter("bankID");
	long result =0;
	Vector<CorrespondValue> cps = dao.getCorrespondList(" and bankID='"+bankID+"' and firmid='"+firmID+"'");
	CorrespondValue corr = null;
	
		corr = cps.get(0);
		corr.status = 0;
		
		new ErrorCode().load();
	
	
	ABCCapitalProcessorRMI cp = null;
		int f =1;
		long bak = -1;
		String headmsg = "交易资金账号管理";
		String str=headmsg+"-解约农行账号["+firmID+"]";
		String strs = request.getParameter("sReturnMsg");
		
			
	try {
		//多处理器路由
		
		try{
			cp = getABCBankUrl(corr.bankID);
		}catch(Exception e){
			e.printStackTrace();
		}
		if("0000".equals(request.getParameter("sReturnCode"))){
			result=cp.delAccountAbc(corr);
			System.out.println(result);
			if(result<0){
			
				if(ErrorCode.error.get(result) != null){
					strs = ErrorCode.error.get(result);
				}else{
					strs = "返回码 ["+ result+"]";
				}
			}else{
			f=0;
				bak = 0;
				strs = "资金账号["+corr.firmID+"]解约成功";
			}
		}
	} catch(Exception e) {
		strs = "资金账号["+corr.firmID+"]解约系统异常";
		e.printStackTrace();
	}
	System.out.println(strs);
	InterfaceLog log = new InterfaceLog();
	LogEndmsg endmsg = new LogEndmsg();
	log.account = corr.account;
	log.bankID = corr.bankID;
	log.contact = corr.firmID;
	//endmsg.code = result;						
	log.type = 4;	
             log.result = f;				
	endmsg.note = strs;
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

