<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>
<%@ page import="java.util.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	Date date = new Date();
	String lastDay = df.format(date);
	pageContext.setAttribute("lastDay",lastDay);
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
	//alert("${lastDay}");
    highlightFormElements();
    query_onclick();
}
//query_onclick
function query_onclick()
{
if(delayForm.isQryHis.checked)
{
  if(delayForm.beginDate.value=="")
  {
    alert("��ʼ���ڲ���Ϊ�գ�");
    delayForm.beginDate.focus();
    return false;
  }
  if(delayForm.endDate.value=="")
  {
    alert("�������ڲ���Ϊ�գ�");
    delayForm.endDate.focus();
    return false;
  }
  if(!isDateFormat(delayForm.beginDate.value))
  {
    alert("��ʼ���ڸ�ʽ����ȷ��");
    delayForm.beginDate.focus();
    return false;
  }
  if(!isDateFormat(delayForm.endDate.value))
  {
    alert("�������ڸ�ʽ����ȷ��");
    delayForm.endDate.focus();
    return false;
  }
}
  wait.style.visibility = "visible";
  delayForm.submit();
  delayForm.query.disabled = true;  
}
//requery_onclick


function isQryHis_onclick()
{
  if(delayForm.isQryHis.checked)
  {
    setReadWrite(delayForm.beginDate);
    setReadWrite(delayForm.endDate);
    delayForm.isQryHis.value = true;
  }
  else
  {
    setReadOnly(delayForm.beginDate);
    setReadOnly(delayForm.endDate);
    delayForm.isQryHis.value = false;
  }
  delayForm.isQryHisHidd.value = delayForm.isQryHis.value;
}
</script>
	</head>

	<body leftmargin="6" topmargin="0" onLoad="return window_onload()"
		onkeypress="keyEnter(event.keyCode);">
		<c:import url="/timebargain/common/waitbar.jsp" charEncoding="GBK"/>
		<table border="0" height="100%" width="100%" cellpadding="0" cellspacing="0" class="common" align="center">
			<tr>
				<td>
					<html:form action="/timebargain/delay/delay.do?funcflg=listDelayTrade"
						method="POST" styleClass="form" target="ListFrame">
						<fieldset class="pickList" >
							<legend class="common">
								<b>��ѯ����
								</b>
							</legend>
							<table border="0" align="center" cellpadding="0" cellspacing="0"
								class="common" width="100%">

								<tr>
									
									<td align="right">
											&nbsp;&nbsp;�����̴��룺
									</td>
									<td>
										<input type="text" id="firmID" name="_a.FirmID[=]" style="width:111" maxlength="16" title=""
											styleClass="text" />
									</td>
			                        <td align="right">
											��Ʒ���룺
									</td>
									<td>
										<input type="text" id="commodityID" name="_a.commodityid[like]" title="������ģʽƥ�����ѯ" style="width:111" maxlength="24"
											styleClass="text" />
									</td>	
			                        <td align="right"><label>&nbsp;&nbsp;������</label></td>
			                        <td>
				                        <select id="BS_Flag" name="_a.BS_Flag[=]" style="width:111">
				                          <option value=""></option>
				                          <option value="1">��</option>
					                      <option value="2">��</option>
			                            </select>
			                        </td>																	
								</tr>
								<tr>
									 <td></td>
			                        <td>
			                            <input type="checkbox" name="isQryHis" id="isQryHis" onclick="isQryHis_onclick()" value="false" class="NormalInput"/><label for="isQryHis" class="hand">����ʷ</label>
										<input type="hidden" id="isQryHisHidd" name="_isQryHis[=]" value="false">
			                        </td>	
									<td align="right">
										��ʼ���ڣ�
									</td>
									<td>
										<input type="text" id="beginDate" name="_a.ClearDate[>=][date]" ondblclick="if(!this.readOnly){setRq(this);}" value="<c:out value="${lastDay}"/>" title="˫��ѡ������" readonly="true" styleId="beginDate" maxlength="10" style="ime-mode:disabled;width:111" onkeypress="return numberPass()" class="ReadOnlyString"/>
									</td>
									<td align="right">
										�������ڣ�
									</td>
									<td>
										<input type="text" id="endDate" name="_a.ClearDate[<=][datetime]" ondblclick="if(!this.readOnly){setRq(this);}" value="<c:out value="${lastDay}"/>" title="˫��ѡ������" readonly="true" styleId="endDate" maxlength="10" style="ime-mode:disabled;width:111" onkeypress="return numberPass()" class="ReadOnlyString"/>
									</td>	
									<td></td>
									<td align="left">
										<html:button property="query" style="width:60" styleClass="button"
											onclick="javascript:return query_onclick();">
											��ѯ
										</html:button>
										
									</td>
									<td>
										
									</td>
									<td></td>
									<td></td>																		
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
