<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ include file="../globalDef.jsp"%>
<%@ include file="../session.jsp"%>
<%


//�ύ ��inOutMoneyByHand


/**

String firmId =request.getParameter("firmID");
String bankId =request.getParameter("bank");
String inOutMoneyFlag =request.getParameter("type");
double money  = Double.parseDouble(request.getParameter("money"));
String funID =request.getParameter("funID");
InOutMoney inOutMoney =new InOutMoney();
inOutMoney.setFirmID(firmId);
inOutMoney.setBankID(bankId);
inOutMoney.setInOutMoneyFlag(inOutMoneyFlag);
inOutMoney.setMoney(money);
inOutMoney.setFunID(funID);
ReturnValue result = new ReturnValue();
CapitalProcessorRMI cp = null;
try
{
	cp = (CapitalProcessorRMI)Naming.lookup("//" + RmiIpAddress + ":" + RmiPortNumber + "/"+ RmiServiceName);
}
catch(Exception e)
{
	e.printStackTrace();
}
RequestMsg req = new  RequestMsg();
req.setBankID("");
req.setMethodName("inOutMoneyByHand");
req.setParams(new Object[]{inOutMoney});

try{
	result = cp.doWork(req);//ִ���ֹ������
}catch(Exception e){
	e.printStackTrace();
	result.result = -1;
}
if(result.result ==0){
	System.out.println("�ֹ������ɹ�����");
}
else {
	System.out.println("�ֹ������ʧ�ܣ���");		
}
*/

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
   
    
    <title>My JSP 'HandMoney.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

  </head>
  
  <body>
  <div id ="test"></div>
   <form id="frm" action="handMoney3.jsp" method="post">
		<fieldset width="95%">
			<legend>�ֹ������</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
				<tr height="35" >
					<td align="right">ƽ̨�˻���&nbsp;</td>
					<td align="left">
						<input name="firmID"  type=text  class="text"  style="width: 100px"  onblur="bankFlag()">
					</td>
				</tr>
				<tr  height="35" >
					<td align="right">���У�&nbsp;</td>
					<td align="left" id="bankTD">		
						<select name="bank" id="bank" class="normal" style="width: 100px" >
							
						</select>
					</td>
				</tr>
				<tr  height="35" >
					<td align="right">�������ͣ�&nbsp;</td>
					<td align="left">
						<select name="type" class="normal" style="width: 100px">
							<OPTION value="-1">--��ѡ��--</OPTION>
							<option value="0" >���</option>
              				<option value="1" >����</option>		
						</select>
					</td>
				</tr>
				<tr  height="35" >
					<td align="right">��&nbsp;</td>
					<td align="left">
						<input name="money"  type=text  class="text"  style="width: 100px">
					</td>
				</tr>
				<tr  height="35" >
					<td align="right">������ˮ�ţ�&nbsp;</td>
					<td align="left">
						<input name="funID"   type=text  class="text"  style="width: 100px">
					</td>
					
				</tr>
				<tr  height="35" >
					<td colspan="2" align="center">
						<button type="button" class="smlbtn" onclick="return doQuery();">�ύ</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<button type="button" class="smlbtn" onclick="doReset();">����</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	
				    </td>
				</tr>
			</table>
		</fieldset>
  </body>
  
   <script type="text/javascript">
        var xmlHttp;
        // ����XMLHttpRequest����
        function createXMLHttpRequest() {
            if (window.ActiveXObject) {
                xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
            }
            else if (window.XMLHttpRequest) {
                xmlHttp = new XMLHttpRequest();
            }
        }
        // ����url����
        function createQueryString() {
           
            var firmID = document.getElementById("firmID").value;
           
            var queryString = "firmID=" + encodeURIComponent(firmID);
            return queryString;
        }
        // ����Get��ʽ���ݲ���
        function doRequestUsingGET() {
            createXMLHttpRequest();
            var queryString = "handMoney2.jsp?";
            queryString = queryString + createQueryString();
            xmlHttp.onreadystatechange = handleStateChange;
            xmlHttp.open("GET", queryString, true);
            xmlHttp.send(null);
        }
        // ��POST��ʽ���ݲ���
        function doRequestUsingPOST() {
            createXMLHttpRequest();
            var url = "handMoney2.jsp?firmID=" + frm.firmID.value;
            var queryString = createQueryString();
            xmlHttp.open("POST", url, true);
            xmlHttp.onreadystatechange = handleStateChange;
            xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;");
			xmlHttp.send(queryString);
        }
        // �ص�����
        function handleStateChange() {
            if (xmlHttp.readyState == 4) {
                if (xmlHttp.status == 200) {
                    parseResults();
                }
            }
        }
        // �����������Ӧ����
        function parseResults() {
        	var res = xmlHttp.responseText;
			if(!(res.indexOf('select')==-1)){//ȡ������ʱ
				document.getElementById("bankTD").innerHTML=res;
			}
        }
        
       //���������ƽ̨�ŵ���ajax 
        function  bankFlag(){
            if(frm.firmID.value==""){
				alert("û������ƽ̨�˺�");
				return;
            }else{
              doRequestUsingPOST();
     	   }
        }
        
        //�ύ��
       function doQuery()
       { 
		   
           if(frm.firmID.value==""){
        	  alert("û������ƽ̨�˺�");  
      	    return false;
             } 
		      /*
           if(frm.bankID.value==-1||frm.bankID.value==""){
      	      alert("����Ϊ��");  
       	    return false  ;
               } 
			    */
           if(frm.type.value==-1){
        	  alert("�������Ͳ���Ϊ��"); 
        	  return false; 
              } 
			  
           if(frm.money.value==""){
        	  alert("����Ϊ��");
        	  return false;  
              } 
          if(frm.funID.value==""){
        	  alert("������ˮ�Ų���Ϊ�� ");
        	  return false;  
              } else{
           	
       if(confirm("��ȷ��Ҫ�ύ�� "))
    	{
       		frm.submit();
    		return true;
    	}
    	else
    	{
    		return false;
    	}
       }
	  }
       //����
       function  doReset(){
            frm.firmID.value="";
     	    frm.bank.value="";
            frm.type.value="-1";
            frm.money.value="";
            frm.funID.value="";
     	   
     }
    </script>
  

</html>
