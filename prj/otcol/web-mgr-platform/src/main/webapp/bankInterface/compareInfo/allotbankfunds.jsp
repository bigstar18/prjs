<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../globalDef.jsp"%>
<%@ include file="../session.jsp"%>

<%
BankDAO dao = BankDAOFactory.getDAO();
java.util.Date allotDate = dao.getMaxDate();//��ȡ�����������
PlatformProcessorRMI cp = null;
	try{
		cp = (PlatformProcessorRMI)Naming.lookup("//" + RmiIpAddress + ":" + RmiPortNumber + "/"+ PTRmiServiceName);
	}catch(Exception e){
		e.printStackTrace();
	}
if( "do".equals(request.getParameter("submitFlag")))
{
	//boolean flag = false;
	//flag = dao.getSysStatus(new java.util.Date());//��ȡ����ϵͳ��ǰ״̬
	//if(flag){
		RequestMsg req = new RequestMsg();
		req.setBankID("");
		req.setMethodName("allotbankfunds");
		req.setParams(new Object[]{new java.util.Date()});
		ReturnValue rv = cp.doWork(req);//ִ���ʽ����
		if(rv.result==1){
			%><script>alert('�ʽ����ɹ���');</script><%
		}else{
			%><script>alert('�ʽ����ʧ�ܣ�');</script><%
		}
	//}else{
	//	%><!--script>alert('����ϵͳδ������ɣ�����ִ�в�����㣡');</script--><%
	//}
	%><script>window.location="allotbankfunds.jsp";</script><%
}
%>

<html xmlns:MEBS>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=GBK">
    <IMPORT namespace="MEBS" implementation="<%=request.getContextPath()%>/bankInterface/lib/calendar.htc">
	<script language="javascript" src="../lib/tools.js"></script>
    <title>�ʽ����</title>
  </head>
  
  <body>
  	<form id="frm" action="" method="POST">
		<fieldset width="100%">
		<legend>�ʽ����</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="1600px" height="350">
			  <tr height="35">
			  	<td width="30%"></td>
                <td align="left"><br><br>
                	<b><font size="3" color="red">ע���˲���Ӧ�ڽ���ϵͳ��������</font></b><br><br>
                </td>
              </tr>
              <tr height="40">
                <td colspan="4"></td>
              </tr>
              <tr height="40">
                <td colspan="4"><div align="center">
                  <b>����������ڣ�<%=Tool.fmtDate(allotDate)%></b>
                </div></td>
              </tr>
              <tr height="30">
                <td colspan="4"><div align="center">
                  <!-- <button id="submitBtn" class="lgrbtn" type="button" onclick="accountVoucher();">ƾ֤���˽���</button>&nbsp; -->
                  <button id="submitBtn" class="lgrbtn" type="button" onclick="doCompare();">ִ�н���</button>&nbsp;
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