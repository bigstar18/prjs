<%@ include file="/timebargain/printreport/util.jsp"%>
<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>
<%
Date sysdate = new Date();
SimpleDateFormat df =new SimpleDateFormat("yyyy-MM-dd");
String nowDate = df.format(sysdate);
%>
<html>
	<head>
		<LINK REL="stylesheet" type="text/css" href="<c:url value="/timebargain/styles/common.css"/>" />
		<c:import url="/timebargain/statquery/customerID_change.jsp"/>
		<c:import url="/timebargain/common/date.jsp"/>
		<script language="VBScript" src="<c:url value="/timebargain/scripts/vbfunction.vbs"/>"></script>
		<title>
		</title>
		<script type="text/javascript"> 
function window_onload()
{
    //highlightFormElements();
 //   statQueryForm.marketCode.focus();
    query_onclick();
}
//query_onclick
function query_onclick()
{
	var startDate = statQueryForm.beginDate.value;
	var endDate = statQueryForm.endDate.value;

 if(startDate != "" && endDate != "" || startDate == "" && endDate != "" || startDate != "" && endDate == "")
 {
  if(statQueryForm.beginDate.value=="")
  {
    alert("��ʼ���ڲ���Ϊ�գ�");
    statQueryForm.beginDate.focus();
    return false;
  }
  if(statQueryForm.endDate.value=="")
  {
    alert("�������ڲ���Ϊ�գ�");
    statQueryForm.endDate.focus();
    return false;
  }
  if(!isDateFormat(statQueryForm.beginDate.value))
  {
    alert("��ʼ���ڸ�ʽ����ȷ��");
    statQueryForm.beginDate.focus();
    return false;
  }
  if(!isDateFormat(statQueryForm.endDate.value))
  {
    alert("�������ڸ�ʽ����ȷ��");
    statQueryForm.endDate.focus();
    return false;
  }
  if ( startDate > '<%=nowDate%>' ) { 
      alert("��ʼ���ڲ��ܴ��ڵ�������!"); 
      statQueryForm.beginDate.focus();
      return false; 
  } 
  if ( startDate > endDate ) { 
      alert("��ʼ���ڲ��ܴ��ڽ�������!"); 
      return false; 
  } 
 }
  statQueryForm.submit();
}
//requery_onclick
function requery_onclick()
{
  top.MainFrame.location.href = "<c:url value="/timebargain/statquery/quotation.jsp"/>";
}

function isQryHis_onclick()
{
  if(statQueryForm.isQryHis.checked)
  {
    setReadWrite(statQueryForm.beginDate);
    setReadWrite(statQueryForm.endDate);
    statQueryForm.isQryHis.value = true;
  }
  else
  {
    setReadOnly(statQueryForm.beginDate);
    setReadOnly(statQueryForm.endDate);
    statQueryForm.isQryHis.value = false;
  }
  statQueryForm.isQryHisHidd.value = statQueryForm.isQryHis.value;
}
</script>
	</head>
<%
   List brokerAscList=getList("select brokerId from m_b_broker order by brokerId");
   List brokerDescList=getList("select brokerId from m_b_broker order by brokerId desc");
   pageContext.setAttribute("brokerAscList",brokerAscList);
   pageContext.setAttribute("brokerDescList",brokerDescList);
 %>
	<body leftmargin="6" topmargin="0" onLoad="return window_onload()"
		onkeypress="keyEnter(event.keyCode);">
		<table border="0" height="100%" width="100%" cellpadding="0" cellspacing="0" class="common" align="center">
			<tr>
				<td>
					<form action="${pageContext.request.contextPath}/member/brokerReportController.mem?funcflg=tradeFeeBrokerList"
						method="POST" styleClass="form" target="ListFrame" name="statQueryForm">
						<fieldset class="pickList" >
							<legend class="common">
								<b>��ѯ����
								</b>
							</legend>
							<table border="0" align="center" cellpadding="0" cellspacing="0"
								class="common">

								<tr>
									<td align="right">
									ģ�飺
									</td>
									<td>
									<select name="moduleId" style="width:111" maxlength="16" id="moduleId">
										<option selected="true" value="">ȫ��</option>
										<option value="2">����</option>
										<option value="3">����</option>
										<option value="4">����</option>
									</select>
									</td>
									<td align="left">
						            </td> 
						            <td>&nbsp;</td>
									<td align="right">
										��ʼ�����̣�
									</td>
									<td>
									<SELECT  name="_a.brokerid[>=]" >
	            						<OPTION value="">ȫ��</OPTION>
							            <c:forEach items="${brokerAscList}" var="result">
								        <option value="${result.brokerId}">${result.brokerId}</option>
									    </c:forEach>
          							</SELECT>
									</td>
									<td align="right">
										���������̣�
									</td>
									<td>
									<SELECT  name="_a.brokerid[<=]" >
	            						<OPTION value="">ȫ��</OPTION>
							            <c:forEach items="${brokerDescList}" var="result">
								        <option value="${result.brokerId}">${result.brokerId}</option>
									    </c:forEach>
          							</SELECT>
									</td>	
									<td align="right">
										&nbsp;&nbsp;
									</td>
								</tr>
								<tr>
									<td align="right">
									</td>
									<td>
									</td>
									<td align="left">
						            </td> 
						            <td>&nbsp;</td>
									<td align="right">
										��ʼ���ڣ�
									</td>
									<td>
										<input type="text" id="beginDate" name="_to_char(trunc(a.occurdate),'yyyy-MM-dd')[>=]" ondblclick="if(!this.readOnly){setRq(this);}" value="<c:out value='${lastDay}'/>" title="˫��ѡ������"  styleId="beginDate" maxlength="10" style="ime-mode:disabled;width:111" onkeypress="return numberPass()" />
									</td>
									<td align="right">
										�������ڣ�
									</td>
									<td>
										<input type="text" id="endDate" name="_to_char(trunc(a.occurdate),'yyyy-MM-dd')[<=]" ondblclick="if(!this.readOnly){setRq(this);}" value="<c:out value='${lastDay}'/>" title="˫��ѡ������" styleId="endDate" maxlength="10" style="ime-mode:disabled;width:111" onkeypress="return numberPass()" />
									</td>	
									<td align="right">
										&nbsp;&nbsp;<input type="button"
											onclick="javascript:return query_onclick();" class="button" value="ִ�в�ѯ"/>
									</td>
																										
								</tr>
							</table>
						</fieldset>
					</form>
				</td>
			</tr>
		</table>

		<%@ include file="/timebargain/common/messages.jsp"%>
	</body>
</html>
