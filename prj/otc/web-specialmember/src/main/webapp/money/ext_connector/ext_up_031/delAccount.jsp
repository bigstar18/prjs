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
		String headmsg = "�����ʽ��˺Ź���";
		String str=headmsg+"-��Լũ���˺�["+firmID+"]";
		String strs = request.getParameter("sReturnMsg");
		
			
	try {
		//�ദ����·��
		
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
					strs = "������ ["+ result+"]";
				}
			}else{
			f=0;
				bak = 0;
				strs = "�ʽ��˺�["+corr.firmID+"]��Լ�ɹ�";
			}
		}
	} catch(Exception e) {
		strs = "�ʽ��˺�["+corr.firmID+"]��Լϵͳ�쳣";
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

