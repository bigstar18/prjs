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
    highlightFormElements();
    //setReadOnly(statQueryForm.FirmName);
    query_onclick();
}
//query_onclick
function query_onclick()
{
if(statQueryForm.isQryHis.checked)
{
  if(statQueryForm.beginDate.value=="")
  {
    alert("开始日期不能为空！");
    statQueryForm.beginDate.focus();
    return false;
  }
  if(statQueryForm.endDate.value=="")
  {
    alert("结束日期不能为空！");
    statQueryForm.endDate.focus();
    return false;
  }
  if(!isDateFormat(statQueryForm.beginDate.value))
  {
    alert("开始日期格式不正确！");
    statQueryForm.beginDate.focus();
    return false;
  }
  if(!isDateFormat(statQueryForm.endDate.value))
  {
    alert("结束日期格式不正确！");
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
  top.MainFrame.location.href = "<c:url value="/timebargain/statquery/dailyMoney.jsp"/>";
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
					<html:form action="/timebargain/statquery/statQuery.do?funcflg=listDailyMoney"
						method="POST" styleClass="form" target="ListFrame">
						<fieldset class="pickList" >
							<legend class="common">
								<b>查询条件
								</b>
							</legend>
							<table border="0" align="center" cellpadding="0" cellspacing="0"
								class="common">

								<tr>
									
									<td align="right">
											&nbsp;&nbsp;交易商ID：
									</td>
									<td>
										<input type="text" id="FirmID" name="_a.FirmID[=]" style="width:111" maxlength="16" 
											styleClass="text" />
									</td>
			                       
			                        <td align="right"><label>&nbsp;&nbsp;业务代码：</label></td>
			                        <td>
										<select id="operation" name="_a.OprCode[=]" style="width:111">
											<option value=""></option>
											<option value="101">入金</option>
											<option value="102">出金</option>
											<option value="103">手续费</option>
											<option value="104">收保证金</option>
											<option value="105">退保证金</option>
											<option value="106">平仓盈亏</option>
											<option value="107">收浮亏</option>
											<option value="108">退浮亏</option>
											<option value="112">收交收货款(买方)</option>
											<option value="113">交收手续费</option>
											<option value="114">交收盈亏</option>
											<option value="116">收交收保证金(卖方)</option>
											<option value="117">退交收保证金(卖方</option>
											<option value="118">付卖方货款</option>
										</select>
			                        </td> 																									
								</tr>
								<tr>
								<td></td>
									<td align="left">
										<input type="checkbox" name="isQryHis" id="isQryHis" onclick="isQryHis_onclick()" value="false" class="NormalInput"/><label for="isQryHis" class="hand">查历史</label>
										<input type="hidden" id="isQryHisHidd" name="_isQryHis[=]" value="false">									
										
									</td>
									<td align="right">
										开始日期：
									</td>
									<td>
										<input type="text" id="beginDate" name="_a.CreateTime[>=][date]" ondblclick="if(!this.readOnly){setRq(this);}" value="<c:out value='${lastDay}'/>" title="双击选择日期" styleId="beginDate" maxlength="10" style="ime-mode:disabled;width:111" onkeypress="return numberPass()" readonly="true" class="ReadOnlyString"/>
									</td>
									<td align="right">
										结束日期：
									</td>
									<td>
										<input type="text" id="endDate" name="_a.CreateTime[<=][datetime]" ondblclick="if(!this.readOnly){setRq(this);}" value="<c:out value='${lastDay}'/>" title="双击选择日期" styleId="endDate" maxlength="10" style="ime-mode:disabled;width:111" onkeypress="return numberPass()" readonly="true" class="ReadOnlyString"/>
									</td>	
									<td align="right">
										&nbsp;<html:button property="query" style="width:60" styleClass="button"
											onclick="javascript:return query_onclick();">
											查询
										</html:button>
										
									</td>
									<td>
										
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
