<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ include file="../globalDef.jsp"%>
<%@ include file="../session.jsp"%>
<%


//提交 到inOutMoneyByHand


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
	result = cp.doWork(req);//执行手工出入金
}catch(Exception e){
	e.printStackTrace();
	result.result = -1;
}
if(result.result ==0){
	System.out.println("手工出入金成功！！");
}
else {
	System.out.println("手工出入金失败！！");		
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
			<legend>手工出入金</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
				<tr height="35" >
					<td align="right">平台账户：&nbsp;</td>
					<td align="left">
						<input name="firmID"  type=text  class="text"  style="width: 100px"  onblur="bankFlag()">
					</td>
				</tr>
				<tr  height="35" >
					<td align="right">银行：&nbsp;</td>
					<td align="left" id="bankTD">		
						<select name="bank" id="bank" class="normal" style="width: 100px" >
							
						</select>
					</td>
				</tr>
				<tr  height="35" >
					<td align="right">操作类型：&nbsp;</td>
					<td align="left">
						<select name="type" class="normal" style="width: 100px">
							<OPTION value="-1">--请选择--</OPTION>
							<option value="0" >入金</option>
              				<option value="1" >出金</option>		
						</select>
					</td>
				</tr>
				<tr  height="35" >
					<td align="right">金额：&nbsp;</td>
					<td align="left">
						<input name="money"  type=text  class="text"  style="width: 100px">
					</td>
				</tr>
				<tr  height="35" >
					<td align="right">银行流水号：&nbsp;</td>
					<td align="left">
						<input name="funID"   type=text  class="text"  style="width: 100px">
					</td>
					
				</tr>
				<tr  height="35" >
					<td colspan="2" align="center">
						<button type="button" class="smlbtn" onclick="return doQuery();">提交</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<button type="button" class="smlbtn" onclick="doReset();">重置</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	
				    </td>
				</tr>
			</table>
		</fieldset>
  </body>
  
   <script type="text/javascript">
        var xmlHttp;
        // 创建XMLHttpRequest对象
        function createXMLHttpRequest() {
            if (window.ActiveXObject) {
                xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
            }
            else if (window.XMLHttpRequest) {
                xmlHttp = new XMLHttpRequest();
            }
        }
        // 整合url参数
        function createQueryString() {
           
            var firmID = document.getElementById("firmID").value;
           
            var queryString = "firmID=" + encodeURIComponent(firmID);
            return queryString;
        }
        // 按照Get方式传递参数
        function doRequestUsingGET() {
            createXMLHttpRequest();
            var queryString = "handMoney2.jsp?";
            queryString = queryString + createQueryString();
            xmlHttp.onreadystatechange = handleStateChange;
            xmlHttp.open("GET", queryString, true);
            xmlHttp.send(null);
        }
        // 按POST方式传递参数
        function doRequestUsingPOST() {
            createXMLHttpRequest();
            var url = "handMoney2.jsp?firmID=" + frm.firmID.value;
            var queryString = createQueryString();
            xmlHttp.open("POST", url, true);
            xmlHttp.onreadystatechange = handleStateChange;
            xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;");
			xmlHttp.send(queryString);
        }
        // 回调函数
        function handleStateChange() {
            if (xmlHttp.readyState == 4) {
                if (xmlHttp.status == 200) {
                    parseResults();
                }
            }
        }
        // 处理服务器响应内容
        function parseResults() {
        	var res = xmlHttp.responseText;
			if(!(res.indexOf('select')==-1)){//取得数据时
				document.getElementById("bankTD").innerHTML=res;
			}
        }
        
       //根据输入的平台号调用ajax 
        function  bankFlag(){
            if(frm.firmID.value==""){
				alert("没有输入平台账号");
				return;
            }else{
              doRequestUsingPOST();
     	   }
        }
        
        //提交表单
       function doQuery()
       { 
		   
           if(frm.firmID.value==""){
        	  alert("没有输入平台账号");  
      	    return false;
             } 
		      /*
           if(frm.bankID.value==-1||frm.bankID.value==""){
      	      alert("银行为空");  
       	    return false  ;
               } 
			    */
           if(frm.type.value==-1){
        	  alert("操作类型不能为空"); 
        	  return false; 
              } 
			  
           if(frm.money.value==""){
        	  alert("金额不能为空");
        	  return false;  
              } 
          if(frm.funID.value==""){
        	  alert("银行流水号不能为空 ");
        	  return false;  
              } else{
           	
       if(confirm("您确定要提交吗？ "))
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
       //重置
       function  doReset(){
            frm.firmID.value="";
     	    frm.bank.value="";
            frm.type.value="-1";
            frm.money.value="";
            frm.funID.value="";
     	   
     }
    </script>
  

</html>
