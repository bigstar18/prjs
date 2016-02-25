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
 //   statQueryForm.marketCode.focus();
    query_onclick();
}
//query_onclick
function query_onclick()
{
	//add by yangpei 2011-10-18 增加输入日期的日期格式验证,如果日期格式不正确，则不提交
	//mod by zhangjian  2011-11-16 只有当手动输入数据的时候才会进行格式判断
  var settleDate=statQueryForm.settleProcessDate.value; 
  if(settleDate!=null&&settleDate!=""&&!isDateFormat(settleDate)){
  	alert("请输入正确的日期格式");
  }else{
	  wait.style.visibility = "visible";
	  statQueryForm.submit();
	  statQueryForm.query.disabled = true;  
  }
}
//requery_onclick
function requery_onclick()
{
  top.mainFrame.location.href = "<c:url value="/timebargain/statquery/quotation.jsp"/>";
}


</script>
	</head>

	<body leftmargin="13" topmargin="0" onLoad="return window_onload()"
		onkeypress="keyEnter(event.keyCode);">
		<c:import url="/timebargain/common/waitbar.jsp" charEncoding="GBK"/>
		<table border="0" height="100%" width="100%" cellpadding="0" cellspacing="0" class="common" align="center">
			<tr>
				<td>
					<html:form action="/timebargain/statquery/statQuery.do?funcflg=listSettle"
						method="POST" styleClass="form" target="List2Frame">
						<fieldset class="pickList" >
							<legend class="common">
								<b>查询条件
								</b>
							</legend>
							<table border="0" align="center" cellpadding="0" cellspacing="0"
								class="common">
								
								<tr>
									<td align="left">
											交收日期：
									</td>
									<td>
										<html:text property="settleProcessDate" ondblclick="if(!this.readOnly){setRq(this);}" title="双击选择日期" styleClass="text" maxlength="16" size="12" 
onkeypress="onlyDate()" ></html:text>
										
									</td>
									<td>&nbsp;</td>		
									<td align="left">
											交易商代码：
									</td>
									<td>
										<html:text property="firmID" styleClass="text" maxlength="16" size="12"onkeypress="onlyNumberAndCharInput()"></html:text>
										
									</td>
									<td>&nbsp;</td>
						            <td align="left">商品代码：</td>
						            <td >
						            	<html:text  property="commodityID" styleClass="text" maxlength="16" size="12"onkeypress="onlyNumberAndCharInput()"></html:text>
						            </td>  
						            <td>&nbsp;</td> 
								</tr>
								<tr>
                           			
						            <td>
						            	买卖标志：
						            </td>		
						            <td>
						            	<html:select property="BS_Flag" style="width:95">
											<html:option value="">全部</html:option>
				    						<html:option value="1">买</html:option>
											<html:option value="2">卖</html:option>
			   							</html:select> 
						            </td>	
						            <td>&nbsp;</td> 
						            <td>
						            	交收类型：
						            </td>		
						            <td>
						            	<html:select property="settleType" style="width:95">
											<html:option value="">全部类型</html:option>
				    						<html:option value="0">自动交收</html:option>
				    						<html:option value="1">手动交收</html:option>
											<html:option value="2">提前交收</html:option>
											<html:option value="3">延期交收</html:option>
			   							</html:select> 
						            </td>									
						              	
						              	<td>&nbsp;</td>
						              	<td>&nbsp;</td>
						            <td align="left">
										<html:button property="query" style="width:60" styleClass="button"
											onclick="javascript:return query_onclick();">
											查询
										</html:button>
									<input type="reset"  style="width:60" class="button" value="重置" />
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
