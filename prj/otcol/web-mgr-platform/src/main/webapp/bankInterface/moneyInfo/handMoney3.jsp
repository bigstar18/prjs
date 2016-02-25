<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ include file="../globalDef.jsp"%>
<%@ include file="../session.jsp"%>
<%


//提交 到inOutMoneyByHand
String firmId =Tool.delNull(request.getParameter("firmID").trim());
String bankId =Tool.delNull(request.getParameter("bank").trim());
String inOutMoneyFlag =Tool.delNull(request.getParameter("type").trim());
double money  = Double.parseDouble(request.getParameter("money").trim());
String funID =Tool.delNull(request.getParameter("funID").trim());
System.out.println("firmId==>"+firmId+ "bankId==>"+bankId+ "inOutMoneyFlag==>"+inOutMoneyFlag+ "money==>"+money+ "funID==>"+funID );

InOutMoney inOutMoney =new InOutMoney();
inOutMoney.setFirmID(firmId);
inOutMoney.setBankID(bankId);
inOutMoney.setInOutMoneyFlag(inOutMoneyFlag);
inOutMoney.setMoney(money);
inOutMoney.setFunID(funID);
ReturnValue result = new ReturnValue();
PlatformProcessorRMI cp = null;
	try{
		cp = (PlatformProcessorRMI)Naming.lookup("//" + RmiIpAddress + ":" + RmiPortNumber + "/"+ PTRmiServiceName);
	}catch(Exception e){
		e.printStackTrace();
	}
RequestMsg req = new  RequestMsg();
req.setBankID("");
req.setMethodName("inOutMoneyByHand");
req.setParams(new Object[]{inOutMoney});

try{
	result = cp.doWork(req);//执行手工出入金
}catch(Exception e){
	e.printStackTrace();
	result.result = -1;
}
if(result.result ==0){
	System.out.println("手工出入金成功！！");
	%>
	 <script>
	       alert("操作成功");
	 
	 </script>
	<% 
}
else {
	System.out.println(result.remark);
	System.out.println("手工出入金失败！！");	
	%>
	 <script>
	        alert('<%=result.remark%>');
	 
	 </script>
	
	<% 
}


%>

<body>
    <form name ="frm" action="handMoney.jsp"  method ="post">
    
    
    </form>


</body>
<script>
     frm.submit();

</script>