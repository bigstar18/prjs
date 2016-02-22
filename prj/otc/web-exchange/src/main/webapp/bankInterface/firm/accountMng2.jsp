<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.util.Date"/>
<%@ include file="../globalDef.jsp"%>
<%@ include file="../session.jsp"%>


<%
String bankID = request.getParameter("MbankID");
String firmID = request.getParameter("MfirmID");
String contact = request.getParameter("Mcontact");
String account = request.getParameter("Maccount");
String bankCardPassword = request.getParameter("MbankCardPassword");
int size = Tool.strToInt(request.getParameter("pageSize"));
int pageIndex= Tool.strToInt(request.getParameter("pageIndex"));
BankDAO dao = BankDAOFactory.getDAO();
CapitalProcessorRMI cp = null;
try{
	//�ദ����·��
	//cp = (CapitalProcessorRMI)Naming.lookup("//" + RmiIpAddress + ":" + RmiPortNumber + "/"+ RmiServiceName);
	cp = getBankUrl(bankID);
}catch(Exception e){
	e.printStackTrace();
}
LogValue lv = new LogValue();
lv.setLogopr(AclCtrl.getLogonID(request));
lv.setLogtime(new Date());
lv.setIp(computerIP);
lv.setLogtype("2110");
lv.setLogoprtype("E");
Vector<CorrespondValue> cps = dao.getCorrespondList(" and firmid='"+firmID+"' and bankid='"+bankID+"'");
CorrespondValue corr = null;
if(cps != null && cps.size()>0){
	corr = cps.get(0);
	ErrorCode errorcode = new ErrorCode();
	errorcode.load();
}
String headmsg = "�����ʽ��˺Ź���";
String strs = "";
long bak = -1;
if("disAccount".equalsIgnoreCase(request.getParameter("submitFlag"))){//����
	String str = headmsg+"-�����˺�";
	if(corr.isOpen != 1){
		strs = "��ǩԼ״̬�������";
	}else {
		try {
			corr.status = 1;
			ReturnValue rv = cp.modCorrespondStatus(corr);
			if(rv.result < 0){
				strs = rv.remark;
				if(strs == null || strs.trim().length()<=0){
					if(ErrorCode.error.get(rv.result) != null){
						strs = ErrorCode.error.get(rv.result);
					}else{
						strs = "���ش�����["+rv.result+"]";
					}
				}
			}else{
				bak = 0;
				strs = "�����˺�["+corr.firmID+"]�ɹ�";
			}
		} catch(Exception e) {
			strs = "�����˺�["+corr.firmID+"]ϵͳ�쳣";
			e.printStackTrace();
		}
		lv.setResult(bak==0 ? 0 : 1);
		lv.setLogcontent(str+"-"+strs);
		dao.log(lv);
	}
	%>
		<script>
			alert('<%=strs%>')
		</script>
	<%
}else if("recAccount".equalsIgnoreCase(request.getParameter("submitFlag"))){//����
	String str = headmsg+"-�����˺�";
	if(corr.isOpen != 1){
		strs = "��ǩԼ״̬��������";
	}else {
		try {
			corr.status = 0;
			ReturnValue rv = cp.modCorrespondStatus(corr);
			if(rv.result < 0){
				strs = rv.remark;
				if(strs == null || strs.trim().length()<=0){
					if(ErrorCode.error.get(rv.result) != null){
						strs = ErrorCode.error.get(rv.result);
					}else{
						strs = "���ش�����["+rv.result+"]";
					}
				}
			}else{
				bak = 0;
				strs = "�����˺�["+corr.firmID+"]�ɹ�";
			}
		} catch(Exception e) {
			strs = "�����˺�["+corr.firmID+"]ϵͳ�쳣";
			e.printStackTrace();
		}
		lv.setResult(bak==0 ? 0 : 1);
		lv.setLogcontent(str+"-"+strs);
		dao.log(lv);
	}
	%>
		<script>
			alert('<%=strs%>')
		</script>
	<%
}else if ("recPassword".equalsIgnoreCase(request.getParameter("submitFlag"))){//��ʼ������
	String password = "111111";
	String str = headmsg+"-��ʼ���ʽ�����";
	ReturnValue rv=cp.initializeFrimPwd(firmID,password);
	if(rv.result<0){
		strs = rv.remark;
		if(strs == null || strs.trim().length()<=0){
			if(ErrorCode.error.get(rv.result) != null){
				strs = ErrorCode.error.get(rv.result);
			}else{
				strs = "��ʼ���ʽ����룬������["+rv.result+"]";
			}
		}
	}else{
		bak = 0;
		strs = "��ʼ��["+firmID+"]�ʽ�����["+password+"]�ɹ�";
	}
	lv.setResult(bak==0 ? 0 : 1);
	lv.setLogcontent(str+"-"+strs);
	dao.log(lv);
	%>
		<script>
			alert('<%=strs%>')
		</script>
	<%
}
%>

<body>
<form name="frm" action="accountMng.jsp" method="post">
<input type="hidden" name="pageSize" value="<%=size%>">
<input type="hidden" name="pageIndex" value="<%=pageIndex%>">
</form>
</body>
<SCRIPT LANGUAGE="JavaScript">
	frm.submit();
</SCRIPT>