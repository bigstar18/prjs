<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>
<html>
	<head>
		<LINK REL="stylesheet" type="text/css" href="<c:url value="/timebargain/styles/common.css"/>" />
		<c:import url="/timebargain/statquery/customerID_change.jsp"/>
		<c:import url="/timebargain/common/date.jsp"/>
		<c:import url="/common/getCommodityIdListForJs.jsp"/>
		<script language="VBScript" src="<c:url value="/timebargain/scripts/vbfunction.vbs"/>"></script>
		<title>
		</title>
		<script type="text/javascript"> 
function window_onload()
{
	//alert("${lastDay}");
    highlightFormElements();
    query_onclick();
    initPar('uni_Cmdty_Code');
}
//query_onclick
function query_onclick()
{
if(statQueryForm.isQryHis.checked)
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
}
  wait.style.visibility = "visible";
  statQueryForm.submit();
  statQueryForm.query.disabled = true;  
}
//requery_onclick
function requery_onclick()
{
  top.MainFrame.location.href = "<c:url value="/timebargain/statquery/trade.jsp"/>";
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

	<body leftmargin="6" topmargin="0" onLoad="return window_onload()"
		onkeypress="keyEnter(event.keyCode);">
		<c:import url="/timebargain/common/waitbar.jsp" charEncoding="GBK"/>
		<table border="0" height="100%" width="100%" cellpadding="0" cellspacing="0" class="common" align="center">
			<tr>
				<td>
					<html:form action="/timebargain/statquery/statQuery.do?funcflg=listBrokerTrade"
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
											&nbsp;&nbsp;�����̴��룺
									</td>
									<td>
										<input type="text" id="brokerid" name="_a.brokerid[=]" style="width:111" maxlength="16" title=""
											styleClass="text" />
									</td>
			                        <td align="right">
											&nbsp;&nbsp;���������ƣ�
									</td>
									<td>
										<input type="text" id="name" name="_a.Name[like]" title="������ģʽƥ�����ѯ" style="width:111" maxlength="32" styleClass="text"/>
									</td>
			                        <td align="right"><label>&nbsp;&nbsp;������</label></td>
			                        <td>
				                        <select id="BS_Flag" name="_t.BS_Flag[=]" style="width:111">
				                          <option value=""></option>
				                          <option value="1">��</option>
					                      <option value="2">��</option>
			                            </select>
			                        </td>	
			                        <td align="right"><label>&nbsp;&nbsp;</label></td>
			                        <td>
										&nbsp;
			                        </td> 																	
								</tr>
								<tr>
                              <!--       <td align="right"><fmt:message key="marketForm.marketName"/></td>
                                    <td>
                                        <select id="marketCode" name="_c.MarketCode[=]" style="width:111">
                                          <c:forEach items="${marketSelect}" var="market">
                                            <option value='<c:out value="${market.value}"/>'><c:out value="${market.label}"/></option>
                                          </c:forEach>
                                        </select>
                                    </td>	 -->	
                                    <td align="right">�ɽ����ͣ�</td>
                                    <td>
                                        <select id="tradeType" name="_t.TradeType[=]" style="width:111">
                                          <option value=""></option>
											<option value="1">��������</option>
											<option value="3">ǿ��ת��</option>
											<option value="4">ί�н���</option>
                                        </select>
                                    </td>						
									<td align="right">
											��Ʒ���룺
									</td>
									<td>
										<input type="text" id="uni_Cmdty_Code" name="_t.commodityid[like]" title="������ģʽƥ�����ѯ" style="width:111" maxlength="24"
											styleClass="text" onkeyup="autoComplete()" onblur="setDisplay();"/>
										<div id="divContent" style="display:none; position:absolute; background-color:#ffffff;border: solid 1px #9DCEE8 "></div>
									</td>	
			                        <td align="right"><label>ί�����ͣ�</label></td>
			                        <td>
										<select id="orderType" name="_t.OrderType[=]" style="width:111">
											<option value=""></option>
											<option value="1">����</option>
											<option value="2">ת��</option>
										<!--  	<option value="3"><fmt:message key="ordersForm.OrderType.option.qp" /></option>-->
										</select>
			                        </td> 
			                       <td align="right">
											&nbsp;&nbsp;
									</td>
									<td>
										&nbsp;
									</td>
			                        <td></td>		                                                           							
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
										<input type="text" id="beginDate" name="_t.ClearDate[>=][date]" ondblclick="if(!this.readOnly){setRq(this);}" value="<c:out value="${lastDay}"/>" title="˫��ѡ������" readonly="true" styleId="beginDate" maxlength="10" style="ime-mode:disabled;width:111" onkeypress="return numberPass()" class="ReadOnlyString"/>
									</td>
									<td align="right">
										�������ڣ�
									</td>
									<td>
										<input type="text" id="endDate" name="_t.ClearDate[<=][date]" ondblclick="if(!this.readOnly){setRq(this);}" value="<c:out value="${lastDay}"/>" title="˫��ѡ������" readonly="true" styleId="endDate" maxlength="10" style="ime-mode:disabled;width:111" onkeypress="return numberPass()" class="ReadOnlyString"/>
									</td>	
									<td></td>
									<td align="left">
										<html:button property="query" style="width:60" styleClass="button"
											onclick="javascript:return query_onclick();">
											ִ�в�ѯ
										</html:button>
										
									</td>
									<td>
										
									</td>
									<td></td>	
									<td></td>	
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
