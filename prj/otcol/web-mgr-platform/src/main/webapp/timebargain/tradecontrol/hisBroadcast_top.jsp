<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>
<%@ page import="gnnt.MEBS.timebargain.manage.util.DateUtil" %>
<%@ page import="java.util.Date" %>
<%
	Date date = DateUtil.GoDate(new Date(), 0);
    String relDate = DateUtil.formatDate(date, "yyyy-MM-dd");
%>
<html>
	<head>
		<LINK REL="stylesheet" type="text/css" href="<c:url value="/timebargain/styles/common.css"/>" />
		<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
		<script language="JavaScript" src="<c:url value="/timebargain/scripts/open.js"/>"></script>
		<c:import url="/timebargain/common/date.jsp"/>
		<script language="VBScript" src="<c:url value="/timebargain/scripts/vbfunction.vbs"/>"></script>
		<title>
		</title>
		<script type="text/javascript"> 
function window_onload()
{
    highlightFormElements();
    query_onclick();
}
//query_onclick
function query_onclick()
{
  if(broadcastForm.beginDate.value=="")
  {
    alert("��ʼ���ڲ���Ϊ�գ�");
    broadcastForm.beginDate.focus();
    return false;
  }
  if(broadcastForm.endDate.value=="")
  {
    alert("�������ڲ���Ϊ�գ�");
    broadcastForm.endDate.focus();
    return false;
  }
  if(broadcastForm.endDate.value<broadcastForm.beginDate.value)
  {
    alert("�������ڲ���С�ڿ�ʼ���ڣ�");
    broadcastForm.endDate.focus();
    return false;
  }
  if(!isDateFormat(broadcastForm.beginDate.value))
  {
    alert("��ʼ���ڸ�ʽ����ȷ��");
    broadcastForm.beginDate.focus();
    return false;
  }
  if(!isDateFormat(broadcastForm.endDate.value))
  {
    alert("�������ڸ�ʽ����ȷ��");
    broadcastForm.endDate.focus();
    return false;
  }

  wait.style.visibility = "visible";
  broadcastForm.submit();
  broadcastForm.query.disabled = true;  
}

</script>
	</head>

	<body leftmargin="6" topmargin="0" onLoad="return window_onload()"
		onkeypress="keyEnter(event.keyCode);">
		<c:import url="/timebargain/common/waitbar.jsp" charEncoding="GBK"/>
		<table border="0" height="100%" width="100%" cellpadding="0" cellspacing="0" class="common" align="center">
			<tr>
				<td>
					<html:form action="/timebargain/tradecontrol/broadcast.do?funcflg=searchHis"
						method="POST" styleClass="form" target="ListFrame">
						<fieldset class="pickList" >
							<legend class="common">
								<b>��ѯ����
								</b>
							</legend>
							<table border="0" align="center" cellpadding="0" cellspacing="0"
								class="common">
								<tr>
									<td align="right">
										�ؼ��ֲ�ѯ��
									</td>
									<td>
										<input type="text" id="content" name="content"  size="16" onkeypress="onlyNumberAndCharInput()" maxlength="32"/>
									</td>								
								    <td align="right">
										&nbsp;&nbsp;��ʼ���ڣ�
									</td>
									<td>
										<input type="text" id="beginDate" name="createTime" ondblclick="if(!this.readOnly){setRq(this);}" value="<%=relDate%>" title="˫��ѡ������" styleId="beginDate" maxlength="10" style="ime-mode:disabled;width:111" onkeypress="return numberPass()" />
									</td>
									<td align="right">
										&nbsp;&nbsp;�������ڣ�
									</td>
									<td>
										<input type="text" id="endDate" name="sendTime" ondblclick="if(!this.readOnly){setRq(this);}" value="<%=relDate%>" title="˫��ѡ������" styleId="endDate" maxlength="10" style="ime-mode:disabled;width:111" onkeypress="return numberPass()"/>
									</td>	
									<td align="right">
										&nbsp;&nbsp;<html:button property="query" style="width:60" styleClass="button"
											onclick="javascript:return query_onclick();">
											��ѯ
										</html:button>
										
									</td>	
																																		
								</tr>
							</table>
						</fieldset>
					</html:form>
				</td>
			</tr>
		</table>

		<%@ include file="/timebargain/common/messages.jsp"%>
	</body>
</html>
