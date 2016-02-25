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
    //conditionOrderForm.isOne.value='true';
    
    query_onclick();
    //conditionOrderForm.isOne.value='false';
}
//query_onclick
function query_onclick()
{
if(conditionOrderForm.isQryHis.checked)
{
  if(conditionOrderForm.beginDate.value=="")
  {
    alert("开始日期不能为空！");
    conditionOrderForm.beginDate.focus();
    return false;
  }
  if(conditionOrderForm.endDate.value=="")
  {
    alert("结束日期不能为空！");
    conditionOrderForm.endDate.focus();
    return false;
  }
  if(!isDateFormat(conditionOrderForm.beginDate.value))
  {
    alert("开始日期格式不正确！");
    conditionOrderForm.beginDate.focus();
    return false;
  }
  if(!isDateFormat(conditionOrderForm.endDate.value))
  {
    alert("结束日期格式不正确！");
    conditionOrderForm.endDate.focus();
    return false;
  }
	 
	   var begin = conditionOrderForm.beginDate.value;
	   var end = conditionOrderForm.endDate.value;
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
			alert("结束日期小于开始日期！");
			return false;
		}
}
  wait.style.visibility = "visible";
  conditionOrderForm.submit();
  conditionOrderForm.query.disabled = true;  
}
//requery_onclick
function requery_onclick()
{
  top.mainFrame.location.href = "<c:url value="/timebargain/statquery/conditionOrder.jsp"/>";
}

function isQryHis_onclick()
{
  if(conditionOrderForm.isQryHis.checked)
  {
	  conditionOrderForm.beginDate.value = CurrentDate;
	  conditionOrderForm.endDate.value = CurrentDate;
		
    setReadWrite(conditionOrderForm.beginDate);
    setReadWrite(conditionOrderForm.endDate);
    conditionOrderForm.isQryHis.value = true;
  }
  else
  {
	// 清空日期内容
	conditionOrderForm.beginDate.value = "";
	conditionOrderForm.endDate.value = "";
	
    setReadOnly(conditionOrderForm.beginDate);
    setReadOnly(conditionOrderForm.endDate);
    conditionOrderForm.isQryHis.value = false;
  }
  conditionOrderForm.isQryHisHidd.value = conditionOrderForm.isQryHis.value;
}

</script>
  </head>
  <body leftmargin="6" topmargin="0" onLoad="return window_onload()" onkeypress="keyEnter(event.keyCode);">
    <c:import url="/timebargain/common/waitbar.jsp" charEncoding="GBK"/>
	<table border="0" height="100%" width="100%" cellpadding="0" cellspacing="0" class="common" align="center">
	  <tr>
	    <td>
		  <html:form action="/timebargain/statquery/conditionOrder.do?funcflg=listConditionOrder" method="POST" styleClass="form" target="ListFrame">
			<fieldset class="pickList" >
			  <legend class="common">
			    <b>查询条件</b>
			  </legend>
			  <table border="0" align="center" cellpadding="0" cellspacing="0" class="common" width="100%">
			    <tr>
			      <td align="right">&nbsp;&nbsp;条件单号：</td>
			      <td>
			        <input type="text" id="orderNo" name="_A_OrderNo[=]" style="width:111" maxlength="16" title="" styleClass="text" onkeypress="onlyNumberAndCharInput()" />
			      </td>
			      <td align="right">&nbsp;&nbsp;交易商代码：</td>
				  <td>
					<input type="text" id="firmID" name="_FirmID[=]" style="width:111" maxlength="16" title="" styleClass="text" onkeypress="onlyNumberAndCharInput()" />
			      </td>      
			      <td align="right">&nbsp;&nbsp;委托商品代码：</td>
				  <td>
					<input type="text" id="commodityID" name="_CommodityID[=]" style="width:111" maxlength="16" title="" styleClass="text" onkeypress="onlyNumberAndCharInput()" />
			      </td>
			      <td align="right">&nbsp;&nbsp;条件商品代码：</td>
			      <td>
			        <input type="text" id="conditionCommodityID" name="_ConditionCommodityID[=]" style="width:111" maxlength="16" title="" styleClass="text" onkeypress="onlyNumberAndCharInput()" />
			      </td>
			    </tr>
				<tr>
				  <td align="right">
			        <label>买卖：</label>
			      </td>
			      <td>
				    <select id="BS_Flag" name="_BS_Flag[=]" style="width:111">
				      <option value="">全部</option>
				      <option value="1">买</option>
					  <option value="2">卖</option>
			        </select>
			      </td>		
			     
				  <td align="right">
			        <label>&nbsp;&nbsp;订立/转让：</label>
			      </td>
			      <td>
					<select id="orderType" name="_OrderType[=]" style="width:111">
				      <option value="">全部</option>
					  <option value="1">订立</option>
					  <option value="2">转让</option>
					</select>	
			      </td> 			
                  <td align="right">条件类型：</td>
                  <td>
                    <select id="conditionType" name="_ConditionType[=]" style="width:111">
                      <option value="">全部</option>
                      <option value="1">现价</option>
					  <option value="2">买1</option>										
					  <option value="3">卖1</option>	
                    </select>
                  </td>							
									
                  <td align="right">委托状态：</td>
                  <td>
                    <select id="sendStatus" name="_SendStatus[=]" style="width:111">
				      <option value="">全部</option>
					  <option value="01">未委托</option>
					  <option value="02">已过期</option>
					  <option value="11">委托成功</option>
					  <option value="12">委托失败</option>											
					  <option value="2">已撤单</option>
			        </select>			                            
                  </td>	                                            							
				</tr>
				<tr>
				  <td align="right"></td>
				  <td align="right"></td>
				  <td align="right"></td>
				  <td align="right">
					<input type="checkbox" name="isQryHis" id="isQryHis" onclick="isQryHis_onclick()" value="false" class="NormalInput"/><label for="isQryHis" class="hand">查历史</label>
					<input type="hidden" id="isQryHisHidd" name="_isQryHis[=]" value="false">
				  </td>		
				  <td align="right">开始日期：</td>
				  <td>
					<input type="text" id="beginDate" name="_validDate[>=][date]" ondblclick="if(!this.readOnly){setRq(this);}"  title="双击选择日期" readonly="true" styleId="beginDate" maxlength="10" style="ime-mode:disabled;width:111" onkeypress="return numberPass()" class="ReadOnlyString"/>
				  </td>
				  <td align="right">结束日期：</td>
				  <td>
					<input type="text" id="endDate" name="_validDate[<=][date]" ondblclick="if(!this.readOnly){setRq(this);}"  title="双击选择日期" readonly="true" styleId="endDate" maxlength="10" style="ime-mode:disabled;width:111" onkeypress="return numberPass()" class="ReadOnlyString"/>
			      </td>	
				  <td align="left">
					<html:button property="query" style="width:60" styleClass="button" onclick="javascript:return query_onclick();">
					       查询
					</html:button>
					<html:reset property="reset" style="width:60" styleClass="button">
					       重置
				    </html:reset>
				  </td>				
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
