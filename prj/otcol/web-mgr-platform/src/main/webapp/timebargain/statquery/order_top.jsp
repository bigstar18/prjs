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
	 
	   var begin = statQueryForm.beginDate.value;
	   var end = statQueryForm.endDate.value;
	   var b = begin.replace("","-");
	   var e = end.replace("","-");
	   var c = CurrentDate.replace("","-");
		if(c < b){
			alert("开始日期大于当前日期！");
			return false;
		}
		if(c < e){
			alert("结束日期大于当前日期！");
			return false;
		}
		if(e < b){
			alert("结束日期小于当前日期！");
			return false;
		}
}
if(statQueryForm.DPrice.value !="" ){
	if(isNaN(statQueryForm.DPrice.value)){
		alert("委托价格应为数字！");
		return false;
	}
}
if(statQueryForm.LPrice.value !="" ){
	if(isNaN(statQueryForm.LPrice.value)){
		alert("委托价格应为数字！");
		return false;
	}
}
if(statQueryForm.DPrice.value !="" && statQueryForm.LPrice.value !=""){
	if(statQueryForm.DPrice.value>statQueryForm.LPrice.value){
		alert("第一个委托价格不能大于第二个委托价格！");
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
	// 清空日期内容
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
								<b>查询条件
								</b>
							</legend>
							<table border="0" align="center" cellpadding="0" cellspacing="0"
								class="common" width="100%" >

								<tr>
									
									<td align="right">
											&nbsp;&nbsp;交易商代码：
									</td>
									<td>
										<input type="text" id="firmID" name="_FirmID[=]" style="width:111" maxlength="16" title=""
											styleClass="text" onkeypress="onlyNumberAndCharInput()" />
									</td>
			                        <td align="right">
											&nbsp;&nbsp;交易商名称：
									</td>
									<td>
										<input type="text" id="firmName" name="_FIRMNAME[like]" title="可输入模式匹配符查询" style="width:111" maxlength="32" styleClass="text" 
onkeypress="onlyNumberAndCharInput()" />
									</td>
			                        <td align="right"><label>买卖：</label></td>
			                        <td>
				                        <select id="BS_Flag" name="_BS_Flag[=]" style="width:111">
				                          <option value="">全部</option>
				                          <option value="1">买</option>
					                      <option value="2">卖</option>
			                            </select>
			                        </td>		
			                        <td align="right"><label>&nbsp;&nbsp;委托类型：</label></td>
			                        <td>
										<select id="orderType" name="_OrderType[=]" style="width:111">
											<option value="">全部类型</option>
											<option value="1">订立</option>
											<option value="2">转让</option>
										</select>
										
			                        </td> 	
			                        
			                        	 
			                        <!-- 加盟商代码 -->
									<td align="right">
											&nbsp;&nbsp;加盟商编号：
									</td>
									<td>
										<input type="text" id="brokerID" name="_BrokerID[=]" style="width:111" maxlength="16" title=""
											styleClass="text" onkeypress="onlyNumberAndCharInput()" />
									</td>	
									<!-- 加盟商代码结束 -->	
			                        <tr>
																								
								</tr>
								<tr>
                                
                                    <td align="right">转让类型：</td>
                                    <td>
                                        <select id="CloseFlag" name="_CloseFlag[=]" style="width:111">
                                          <option value="">全部类型</option>
                                          	<option value="0">普通转让</option>
					                        <option value="1">委托转让</option>
					                        <option value="2">强制转让</option>
                                        </select>
                                    </td>							
									<td align="right">
											商品代码：
									</td>
									<td>
										<input type="text" id="uni_Cmdty_Code" name="_commodityid[like]" title="可输入模式匹配符查询" style="width:111" maxlength="24"
											styleClass="text" onkeypress="onlyNumberAndCharInput()" />
									</td>
                                    <td align="right">状态：</td>
                                    <td>
                                        <select id="status" name="_Status[=]" style="width:111">
				                            <option value="">全部状态</option>
					                        <option value="1">已委托</option>
					                        <option value="2">部分成交</option>
					                        <option value="3">全部成交</option>
					                        <option value="5">全部撤单</option>
					                        <option value="6">部分成交后撤单</option>
			                            </select>			                            
                                    </td>	
			                        <td align="right"><label>&nbsp;&nbsp;撤单类型：</label></td>
			                        <td>
										<select id="withdrawType" name="_WithdrawType[=]" style="width:111">
											<option value="">全部类型</option>		
											<option value="1">委托撤单</option>
											<option value="2">代为撤单</option>
											<option value="4">闭市时自动撤单</option>
										</select>
										
			                        </td>   
			                         <td align="right">
											&nbsp;&nbsp;交易商类别：
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
								  <td align="right" >仓单交易类型：</td>
                                    <td>
                                        <select id="BillTradeType" name="_BillTradeType[=]" style="width:111">
                                          <option value="">全部类型</option>
                                          	<option value="0">正常</option>
					                        <option value="1">卖仓单</option>
					                        <option value="2">抵顶转让</option>
                                        </select>
                                    </td>		
								<td align="right">
									<input type="checkbox" name="isConsigner" id="isConsigner" onclick="isConsigner_onclick()" value="false" class="NormalInput"/><label for="isConsigner" class="hand">代为委托</label>
										<input type="hidden" id="isConsignerHidd" name="_isConsigner[=]" value="false">
								</td>
								<td align="center">
									<input type="checkbox" name="isQryHis" id="isQryHis" onclick="isQryHis_onclick()" value="false" class="NormalInput"/><label for="isQryHis" class="hand">查历史</label>
										<input type="hidden" id="isQryHisHidd" name="_isQryHis[=]" value="false">
										<input type="hidden" id="isOne" name="_isOne[=]" value="false">
								</td>
									
									<td align="right">
										开始日期：
									</td>
									<td>
										<input type="text" id="beginDate" name="_orderTime[>=][date]" ondblclick="if(!this.readOnly){setRq(this);}"  title="双击选择日期" readonly="true" styleId="beginDate" maxlength="10" style="ime-mode:disabled;width:111" onkeypress="return numberPass()" class="ReadOnlyString"/>
									</td>
									<td align="right">
										结束日期：
									</td>
									<td>
										<input type="text" id="endDate" name="_orderTime[<=][datetime]" ondblclick="if(!this.readOnly){setRq(this);}"  title="双击选择日期" readonly="true" styleId="endDate" maxlength="10" style="ime-mode:disabled;width:111" onkeypress="return numberPass()" class="ReadOnlyString"/>
									</td>	
									<td></td>	
									<td align="left">
										<html:button property="query" style="width:60" styleClass="button"
											onclick="javascript:return query_onclick();">
											查询
										</html:button>
										<html:reset property="reset" style="width:60" styleClass="button">
											重置
										</html:reset>
									</td>
								</tr>
								<tr>
									<td align="right">
										委托价格(>=)：
									</td>
									<td>
										<input type="text" id="DPrice" name="_Price[>=]" title="可输入模式匹配符查询" style="width:111" maxlength="24"
											styleClass="text" onkeypress="onlyNumberAndCharInput()" />
									</td>
									<td align="right">
										委托价格(<=)：
									</td>
									<td>
										<input type="text" id="LPrice" name="_Price[<=]" title="可输入模式匹配符查询" style="width:111" maxlength="24"
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
