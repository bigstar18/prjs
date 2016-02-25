<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../globalDef.jsp"%>
<%@ include file="../session.jsp"%>

<%
BankDAO dao = BankDAOFactory.getDAO();
java.util.Date allotDate = dao.getMaxDate();//获取最近结算日期
PlatformProcessorRMI cp = null;
	try{
		cp = (PlatformProcessorRMI)Naming.lookup("//" + RmiIpAddress + ":" + RmiPortNumber + "/"+ PTRmiServiceName);
	}catch(Exception e){
		e.printStackTrace();
	}
if( "do".equals(request.getParameter("submitFlag")))
{
	//boolean flag = false;
	//flag = dao.getSysStatus(new java.util.Date());//获取财务系统当前状态
	//if(flag){
		RequestMsg req = new RequestMsg();
		req.setBankID("");
		req.setMethodName("allotbankfunds");
		req.setParams(new Object[]{new java.util.Date()});
		ReturnValue rv = cp.doWork(req);//执行资金结算
		if(rv.result==1){
			%><script>alert('资金结算成功！');</script><%
		}else{
			%><script>alert('资金结算失败！');</script><%
		}
	//}else{
	//	%><!--script>alert('财务系统未结算完成，请先执行财务结算！');</script--><%
	//}
	%><script>window.location="allotbankfunds.jsp";</script><%
}
%>

<html xmlns:MEBS>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=GBK">
    <IMPORT namespace="MEBS" implementation="<%=request.getContextPath()%>/bankInterface/lib/calendar.htc">
	<script language="javascript" src="../lib/tools.js"></script>
    <title>资金结算</title>
  </head>
  
  <body>
  	<form id="frm" action="" method="POST">
		<fieldset width="100%">
		<legend>资金结算</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="1600px" height="350">
			  <tr height="35">
			  	<td width="30%"></td>
                <td align="left"><br><br>
                	<b><font size="3" color="red">注：此操作应在交易系统结算后进行</font></b><br><br>
                </td>
              </tr>
              <tr height="40">
                <td colspan="4"></td>
              </tr>
              <tr height="40">
                <td colspan="4"><div align="center">
                  <b>最近结算日期：<%=Tool.fmtDate(allotDate)%></b>
                </div></td>
              </tr>
              <tr height="30">
                <td colspan="4"><div align="center">
                  <!-- <button id="submitBtn" class="lgrbtn" type="button" onclick="accountVoucher();">凭证入账结算</button>&nbsp; -->
                  <button id="submitBtn" class="lgrbtn" type="button" onclick="doCompare();">执行结算</button>&nbsp;
				  <input type=hidden name=submitFlag>
                </div></td>
              </tr>
              <tr height="100%">
              	<td></td>
              </tr>
          </table>
		</fieldset>
    </form>
  </body>
</html>

<script type="text/javascript"> 
		
		
	function doCompare()
	{
		frm.submitFlag.value = "do";
		frm.submit();
			
	}
</script>