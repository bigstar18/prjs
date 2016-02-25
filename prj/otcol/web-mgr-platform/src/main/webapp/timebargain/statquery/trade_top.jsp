<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>
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

  var day = new Date();
  var Year = 0;
  var Month = 0;
  var Day = 0;
  var CurrentDate = "";
  Year       = day.getFullYear();
  Month      = day.getMonth()+1;
  Day        = day.getDate();   
  CurrentDate += Year + "-";
  if (Month >= 10 )
  {
   CurrentDate += Month + "-";
  }
  else
  {
   CurrentDate += "0" + Month + "-";
  }
  if (Day >= 10 )
  {
   CurrentDate += Day ;
  }
  else
  {
   CurrentDate += "0" + Day ;
  }
  var begin = statQueryForm.beginDate.value;
  var end = statQueryForm.endDate.value;
  var b = begin.replace("","-");
  var e = end.replace("","-");
  var c = CurrentDate.replace("","-");
	if(c < b){
		alert("��ʼ���ڴ��ڵ�ǰ���ڣ�");
		return false;
	}
	if(c < e){
		alert("�������ڴ��ڵ�ǰ���ڣ�");
		return false;
	}
	if(e < b){
		alert("��������С�ڵ�ǰ���ڣ�");
		return false;
	}
}
if(statQueryForm.Price.value!=""){
	if(isNaN(statQueryForm.Price.value)){
		alert("�ɽ���ӦΪ���֣�");
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
					<html:form action="/timebargain/statquery/statQuery.do?funcflg=listTrade"
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
											styleClass="text"onkeypress="onlyNumberAndCharInput()" />
									</td>
									
			                        <td align="right">
											&nbsp;&nbsp;���������ƣ�
									</td>
									<td>
										<input type="text" id="firmName" name="_m.Name[like]" title="������ģʽƥ�����ѯ" style="width:111" maxlength="32" onkeypress="onlyNumberAndCharInput()"styleClass="text"/>
									</td>
									
									
			                        <td align="right"><label>&nbsp;&nbsp;������</label></td>
			                        <td>
				                        <select id="BS_Flag" name="_a.BS_Flag[=]" style="width:111">
				                          <option value="">ȫ��</option>
				                          <option value="1">��</option>
					                      <option value="2">��</option>
			                            </select>
			                        </td>
			                         <!-- �����̴��� -->
									<td align="right">
											&nbsp;&nbsp;�����̱�ţ�
									</td>
									<td>
										<input type="text" id="brokerID" name="_BrokerID[=]" style="width:111" maxlength="16" title=""
											styleClass="text" onkeypress="onlyNumberAndCharInput()" />
									</td>	
									<!-- �����̴������ -->																		
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
                                        <select id="tradeType" name="_a.TradeType[=]" style="width:111">
                                          <option value="">ȫ������</option>
											<option value="1">��������</option>
											<option value="3">ǿ��ת��</option>
											<option value="4">ί�н���</option>
											<option value="7">���ֵ�</option>
											<option value="8">�ֶ�ת��</option>
                                        </select>
                                    </td>						
									<td align="right">
											��Ʒ���룺
									</td>
									<td>
										<input type="text" id="uni_Cmdty_Code" name="_a.commodityid[like]" title="������ģʽƥ�����ѯ" style="width:111" maxlength="24"
											styleClass="text" onkeypress="onlyNumberAndCharInput()"/>
									</td>	
			                        <td align="right"><label>ί�����ͣ�</label></td>
			                        <td>
										<select id="orderType" name="_a.OrderType[=]" style="width:111">
											<option value="">ȫ������</option>
											<option value="1">����</option>
											<option value="2">ת��</option>
										<!--  	<option value="3"><fmt:message key="ordersForm.OrderType.option.qp" /></option>-->
										</select>
			                        </td> 
			                       	  <td align="right">
											&nbsp;&nbsp;���������
									</td>
									<td>
											<select id="firmCategoryId" style="width:111" name="_firmcategoryId[like]">
												<option  value="" ></option>
												<c:forEach items="${resultList}" var="result">
												   <option value="${result.id}" >${result.name}</option>
												</c:forEach>
											</select>
											</div> 
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
										<html:reset property="reset" style="width:60" styleClass="button">
											����
										</html:reset>
									</td>
									<td>
										
									</td>
									<td></td>
									<td></td>																		
								</tr>
								<tr>
									<td align="right">
											&nbsp;&nbsp;�ɽ��ۣ�
									</td>
									<td>
										<input type="text" id="Price" name="_price[=]" style="width:111" maxlength="16" title=""
											 />
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
