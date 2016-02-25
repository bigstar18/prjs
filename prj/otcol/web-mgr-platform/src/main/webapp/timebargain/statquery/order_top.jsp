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
		
function window_onload()
{
    highlightFormElements();
    statQueryForm.isOne.value='true';
    
    query_onclick();
    statQueryForm.isOne.value='false';
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
if(statQueryForm.DPrice.value !="" ){
	if(isNaN(statQueryForm.DPrice.value)){
		alert("ί�м۸�ӦΪ���֣�");
		return false;
	}
}
if(statQueryForm.LPrice.value !="" ){
	if(isNaN(statQueryForm.LPrice.value)){
		alert("ί�м۸�ӦΪ���֣�");
		return false;
	}
}
if(statQueryForm.DPrice.value !="" && statQueryForm.LPrice.value !=""){
	if(statQueryForm.DPrice.value>statQueryForm.LPrice.value){
		alert("��һ��ί�м۸��ܴ��ڵڶ���ί�м۸�");
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

 	top.mainFrame.location.href = "<c:url value="/timebargain/statquery/order.jsp"/>";
}

function isQryHis_onclick()
{
  if(statQueryForm.isQryHis.checked)
  {
	statQueryForm.beginDate.value = CurrentDate;
	statQueryForm.endDate.value = CurrentDate;
		
    setReadWrite(statQueryForm.beginDate);
    setReadWrite(statQueryForm.endDate);
    statQueryForm.isQryHis.value = true;
  }
  else
  {
	// �����������
	statQueryForm.beginDate.value = "";
	statQueryForm.endDate.value = "";
	
    setReadOnly(statQueryForm.beginDate);
    setReadOnly(statQueryForm.endDate);
    statQueryForm.isQryHis.value = false;
  }
  statQueryForm.isQryHisHidd.value = statQueryForm.isQryHis.value;
}

function isConsigner_onclick(){

	if (statQueryForm.isConsigner.checked) {
		statQueryForm.isConsigner.value = true;
		
	}else {
		statQueryForm.isConsigner.value = false;
	}
	statQueryForm.isConsignerHidd.value = statQueryForm.isConsigner.value;
}
</script>
	</head>

	<body leftmargin="6" topmargin="0" onLoad="return window_onload()"
		onkeypress="keyEnter(event.keyCode);">
		<c:import url="/timebargain/common/waitbar.jsp" charEncoding="GBK"/>
		<table border="0" height="100%" width="100%" cellpadding="0" cellspacing="0" class="common" align="center">
			<tr>
				<td>
					<html:form action="/timebargain/statquery/statQuery.do?funcflg=listOrder"
						method="POST" styleClass="form" target="ListFrame">
						<fieldset class="pickList">
							<legend class="common">
								<b>��ѯ����
								</b>
							</legend>
							<table border="0" align="center" cellpadding="0" cellspacing="0"
								class="common" width="100%" >

								<tr>
									
									<td align="right">
											&nbsp;&nbsp;�����̴��룺
									</td>
									<td>
										<input type="text" id="firmID" name="_FirmID[=]" style="width:111" maxlength="16" title=""
											styleClass="text" onkeypress="onlyNumberAndCharInput()" />
									</td>
			                        <td align="right">
											&nbsp;&nbsp;���������ƣ�
									</td>
									<td>
										<input type="text" id="firmName" name="_FIRMNAME[like]" title="������ģʽƥ�����ѯ" style="width:111" maxlength="32" styleClass="text" 
onkeypress="onlyNumberAndCharInput()" />
									</td>
			                        <td align="right"><label>������</label></td>
			                        <td>
				                        <select id="BS_Flag" name="_BS_Flag[=]" style="width:111">
				                          <option value="">ȫ��</option>
				                          <option value="1">��</option>
					                      <option value="2">��</option>
			                            </select>
			                        </td>		
			                        <td align="right"><label>&nbsp;&nbsp;ί�����ͣ�</label></td>
			                        <td>
										<select id="orderType" name="_OrderType[=]" style="width:111">
											<option value="">ȫ������</option>
											<option value="1">����</option>
											<option value="2">ת��</option>
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
			                        <tr>
																								
								</tr>
								<tr>
                                
                                    <td align="right">ת�����ͣ�</td>
                                    <td>
                                        <select id="CloseFlag" name="_CloseFlag[=]" style="width:111">
                                          <option value="">ȫ������</option>
                                          	<option value="0">��ͨת��</option>
					                        <option value="1">ί��ת��</option>
					                        <option value="2">ǿ��ת��</option>
                                        </select>
                                    </td>							
									<td align="right">
											��Ʒ���룺
									</td>
									<td>
										<input type="text" id="uni_Cmdty_Code" name="_commodityid[like]" title="������ģʽƥ�����ѯ" style="width:111" maxlength="24"
											styleClass="text" onkeypress="onlyNumberAndCharInput()" />
									</td>
                                    <td align="right">״̬��</td>
                                    <td>
                                        <select id="status" name="_Status[=]" style="width:111">
				                            <option value="">ȫ��״̬</option>
					                        <option value="1">��ί��</option>
					                        <option value="2">���ֳɽ�</option>
					                        <option value="3">ȫ���ɽ�</option>
					                        <option value="5">ȫ������</option>
					                        <option value="6">���ֳɽ��󳷵�</option>
			                            </select>			                            
                                    </td>	
			                        <td align="right"><label>&nbsp;&nbsp;�������ͣ�</label></td>
			                        <td>
										<select id="withdrawType" name="_WithdrawType[=]" style="width:111">
											<option value="">ȫ������</option>		
											<option value="1">ί�г���</option>
											<option value="2">��Ϊ����</option>
											<option value="4">����ʱ�Զ�����</option>
										</select>
										
			                        </td>   
			                         <td align="right">
											&nbsp;&nbsp;���������
									</td>
									<td>
											<select name = "_firmcategoryId[like]" style="width:111">
												<option  value="" ></option>
												<c:forEach items="${resultList}" var="result">
												   <option value="${result.id}" >${result.name}</option>
												</c:forEach>
											</select>
									</td>                                                          							
								</tr>
								<tr>
								  <td align="right" >�ֵ��������ͣ�</td>
                                    <td>
                                        <select id="BillTradeType" name="_BillTradeType[=]" style="width:111">
                                          <option value="">ȫ������</option>
                                          	<option value="0">����</option>
					                        <option value="1">���ֵ�</option>
					                        <option value="2">�ֶ�ת��</option>
                                        </select>
                                    </td>		
								<td align="right">
									<input type="checkbox" name="isConsigner" id="isConsigner" onclick="isConsigner_onclick()" value="false" class="NormalInput"/><label for="isConsigner" class="hand">��Ϊί��</label>
										<input type="hidden" id="isConsignerHidd" name="_isConsigner[=]" value="false">
								</td>
								<td align="center">
									<input type="checkbox" name="isQryHis" id="isQryHis" onclick="isQryHis_onclick()" value="false" class="NormalInput"/><label for="isQryHis" class="hand">����ʷ</label>
										<input type="hidden" id="isQryHisHidd" name="_isQryHis[=]" value="false">
										<input type="hidden" id="isOne" name="_isOne[=]" value="false">
								</td>
									
									<td align="right">
										��ʼ���ڣ�
									</td>
									<td>
										<input type="text" id="beginDate" name="_orderTime[>=][date]" ondblclick="if(!this.readOnly){setRq(this);}"  title="˫��ѡ������" readonly="true" styleId="beginDate" maxlength="10" style="ime-mode:disabled;width:111" onkeypress="return numberPass()" class="ReadOnlyString"/>
									</td>
									<td align="right">
										�������ڣ�
									</td>
									<td>
										<input type="text" id="endDate" name="_orderTime[<=][datetime]" ondblclick="if(!this.readOnly){setRq(this);}"  title="˫��ѡ������" readonly="true" styleId="endDate" maxlength="10" style="ime-mode:disabled;width:111" onkeypress="return numberPass()" class="ReadOnlyString"/>
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
								</tr>
								<tr>
									<td align="right">
										ί�м۸�(>=)��
									</td>
									<td>
										<input type="text" id="DPrice" name="_Price[>=]" title="������ģʽƥ�����ѯ" style="width:111" maxlength="24"
											styleClass="text" onkeypress="onlyNumberAndCharInput()" />
									</td>
									<td align="right">
										ί�м۸�(<=)��
									</td>
									<td>
										<input type="text" id="LPrice" name="_Price[<=]" title="������ģʽƥ�����ѯ" style="width:111" maxlength="24"
											styleClass="text" onkeypress="onlyNumberAndCharInput()" />
									</td>
									<td colspan="5">&nbsp;</td>
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
