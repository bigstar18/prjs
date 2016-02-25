<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../public/session.jsp"%>
<html>
  <head>
    <title></title>
  </head>
  <body >
  <form id="frm" name="frm" action="" method="post">
  	<fieldset>
  		<legend>交易管理</legend>
  	 <table width=""  border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
        	<td>&nbsp;</td>
        </tr>
        <tr>
          <td height="24" class="bt"><div align="center">交易管理</div></td>
        </tr>
        <tr>
        	<td>&nbsp;</td>
        </tr>
      </table>
      <br>	  
      <table width="45%"  border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#4980DA">        
        <tr>
          <td bgcolor="#ACC5EE" height="25" colspan=2><div align="center"><span class="xbt">系统当前状态&nbsp;
          	  <c:if test="${marketStatus.currentState==1}">开市准备</c:if>
			  <c:if test="${marketStatus.currentState==2}">开市</c:if>
			  <c:if test="${marketStatus.currentState==3}">节间休息</c:if>
			  <c:if test="${marketStatus.currentState==4}">交易结束</c:if>
			  <c:if test="${marketStatus.currentState==5}">暂停</c:if>
			  <c:if test="${marketStatus.currentState==6}">恢复</c:if>
			  <c:if test="${marketStatus.currentState==7}">闭市处理</c:if>
			  </span></div>
		  </td>          
        </tr>
		<tr>          
          <td bgcolor="#FFFFFF" align=center height=20 colspan=2>&nbsp;
              <BR><BR><BR>
			  <c:if test="${marketStatus.currentState==1}">开市准备</c:if>
			  <c:if test="${marketStatus.currentState==2}">开市处理</c:if>
			  <c:if test="${marketStatus.currentState==3}">节间休息</c:if>
			  <c:if test="${marketStatus.currentState==4}">交易结束</c:if>
			  <c:if test="${marketStatus.currentState==5}">暂停交易</c:if>
			  <c:if test="${marketStatus.currentState==6}">恢复交易</c:if>
			  <c:if test="${marketStatus.currentState==7}">闭市处理</c:if>
			  <BR><BR><BR>
			  </td>
        </tr>						
      </table>
	  <BR><BR>
      <table width="60%" height="36"  border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
        <td height="36">
        <div align="center">
	        <input name="optFlag" type="hidden" value=""/>
	        <input name="start" type="button" value="开市处理" onclick="tradeOpt('start');" <c:if test="${marketStatus.currentState!=7}">disabled</c:if> <c:if test="${marketStatus.isAuto=='Y' }">disabled</c:if>>&nbsp;
			<input name="pause" type="button" value="暂停交易" onclick="tradeOpt('pause');" <c:if test="${marketStatus.currentState!=2 }">disabled</c:if>>&nbsp;
	
			<input name="resume" type="button" value="恢复交易" onclick="tradeOpt('resume')" <c:if test="${marketStatus.currentState!=5}">disabled</c:if> />&nbsp;	
				
			<input name="tradeover" type="button" value="交易结束" onclick="tradeOpt('tradeover')" <c:if test="${marketStatus.currentState!=1&&marketStatus.currentState!=2&&marketStatus.currentState!=5&&marketStatus.currentState!=3}">disabled</c:if> <c:if test="${marketStatus.isAuto=='Y' }">disabled</c:if>/>&nbsp;
			
			<input name="shut" type="button" value="闭市处理" onclick="tradeOpt('shut')" <c:if test="${marketStatus.currentState!=4 }">disabled</c:if> <c:if test="${marketStatus.isAuto=='Y' }">disabled</c:if>/>&nbsp;	
			
		</div>
		</td>
        </tr>
      </table>
	  <BR><BR>
</fieldset>
</form>
</body>
</html>
<script language="javascript">

function tradeOpt(optFlag){
	frm.optFlag.value = optFlag;	
	frm.action="<%=basePath%>tradeController.zcjs?funcflg=tradeManage&optFlag="+optFlag;	
	frm.submit();
}
</script>