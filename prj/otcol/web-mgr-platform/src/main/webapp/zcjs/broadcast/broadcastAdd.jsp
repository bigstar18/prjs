<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../public/session.jsp"%>

<html xmlns:MEBS>
  <head>
  <IMPORT namespace="MEBS" implementation="<%=publicPath%>jstools/calendar.htc">
	<title></title>
</head>

<body>
        <form id="formNew" name="frm" method="POST" action="<%=basePath%>/broadcastController.zcjs?funcflg=add" >
		<fieldset width="100%">
		<legend>�㲥��Ϣ��Ϣ</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
			<tr height="30">
                <td align="right" width="40%">��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��:</td>
                <td align="left" width="60%">
                	<input class="text" name="broadcastTitle" value="" style="width: 150px;" reqfv="required;����">&nbsp;
                	<font color="red">*</font>&nbsp;
                </td>
			  </tr>
			  <tr height="30">
                <td align="right" width="40%">��&nbsp;&nbsp;&nbsp;��&nbsp;&nbsp;&nbsp;��:</td>
                <td align="left" width="60%">
                	<input class="text" name="broadcastSender" value="${operator }" style="width: 150px;" readonly="readonly">&nbsp;
                </td>
			  </tr>
			   <tr height="30">
                <td align="right" width="40%">����ʱ��:</td>
                <td align="left" width="60%">
                	<input type="text" id="broadcastSendTime" name="broadcastSendTime" >&nbsp;<font color="red">* &nbsp;&nbsp;(yyyy-mm-dd hh:mm)</font></td>
                </td>
			  </tr>
			   <tr height="60">
                <td align="right" width="40%">����:</td>
                <td align="left" width="60%"><textarea name="broadcastContent" rows=8 cols=30></textarea>&nbsp;
                </td>
			  </tr>
			
              <tr height="30">
            
                <td colspan="2"><div align="center">
				  <button class="smlbtn" type="button" onclick="doSubmit();">ȷ��</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      			  <button class="smlbtn" type="button" onclick="freturn()">����</button>
                </div></td>
              </tr>
          </table>
		</fieldset>
        </form>
</body>
</html>
<script>

	function doSubmit()
	{
		var	dateStr =  frm.broadcastSendTime.value;
        var matchArray = dateStr.match(/^[0-9]+-[0-1][0-9]-[0-3][0-9](\s)((0[0-9])|(1[0-9])|(2[0-3])):([0-5][0-9])$/);
		if(frm.broadcastTitle.value == "")
		{
		alert("���ⲻ��Ϊ�գ�");
		frm.broadcastTitle.focus();
		return false;
		}
		else if(frm.broadcastSender.value == "")
		{
		alert("���߲���Ϊ�գ�");
		frm.broadcastSender.focus();
		return false;
		}
		else if(frm.broadcastSendTime.value == "")
		{
		alert("����ʱ�䲻��Ϊ�գ�");
		frm.broadcastSendTime.focus();
		return false;
		}else if(matchArray == null)
		{
			alert("����ʱ���ʽ����");
			frm.broadcastSendTime.focus();
			return false;
		}
		if(confirm("ȷ����Ӵ����㲥��Ϣ?")){
			frm.submit();
		}
	}
	function freturn(){
		frm.action = "<%=basePath%>broadcastController.zcjs?funcflg=listReturn";
		frm.submit();
	}
 

</script> 
