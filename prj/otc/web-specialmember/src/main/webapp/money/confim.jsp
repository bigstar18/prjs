<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="./globalDef.jsp"%>
<%@ include file="./session.jsp"%>

<%

    YjfCapitalProcessorRMI cp = null;
	String actionID = request.getParameter("actionID");
	String bankID = request.getParameter("bankID");
    int type = Integer.parseInt(request.getParameter("type"));

	try {
		cp = getYjfBankUrl(bankID);
	} catch(Exception e) {
		e.printStackTrace();
	}
	
	ReturnValue returnValue=new ReturnValue();
	//�����г���ˮ�Ų�ѯ��ˮ״̬
	Vector<CapitalValue> capitalValues = cp.getCapitalList(" and ACTIONID="+actionID);
	CapitalValue capitalValue=capitalValues.get(0);
	System.out.println(capitalValue.status);
	if(capitalValue.status!=0){
		 if(type==ProcConstants.inMoneyType){
		  returnValue=cp.inMoneyResultQuery(actionID,bankID);
	     }else if(type==ProcConstants.outMoneyType){
		  returnValue=cp.outMoneyResultQuery(actionID,bankID);
	     }
		  if(returnValue.result==0){
		 returnValue.remark="�ɹ�";
		 }
	}else{
	     returnValue.remark="�ñ���ˮ�Ѿ�Ϊ�ɹ�״̬";
	}
  
	
	System.out.println(returnValue.result);

%>


<html>
<head></head>
<body>
<form name="frm" action="list.jsp" method="post">

</form>
</body>
    <script>
	    alert("�г�������ѯ���:<%=returnValue.remark%>");
		frm.submit();
		
	</script>
</html>