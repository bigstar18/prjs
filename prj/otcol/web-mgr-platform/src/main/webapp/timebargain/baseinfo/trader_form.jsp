<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>
<html>
	<head>
		<LINK REL="stylesheet" type="text/css"
			href="<c:url value="/timebargain/styles/common.css"/>" />
		<script language="JavaScript"
			src="<c:url value="/timebargain/scripts/global.js"/>"></script>
		<script language="JavaScript" src="<c:url value="/timebargain/scripts/open.js"/>"></script>
		<style type="text/css">

.yin {
	visibility:hidden;
	position:absolute;
	
}
.xian{
	visibility:visible;
}

</style>
		<title>
		</title>
		<script type="text/javascript"> 
function window_onload()
{
    highlightFormElements();
   // alert(traderForm.firmID.value);
   // alert(traderForm.traderID.value);
    if(traderForm.crud.value == "create")
    {
      //alert();
      traderForm.traderID.value = traderForm.firmID.value;
	  traderForm.traderID.focus();
      setReadOnly(traderForm.firmID);
      traderForm.traderID.focus();
      //alert();
    }
    else if(traderForm.crud.value == "update")
    {
    	
      setReadOnly(traderForm.traderID);
      setReadOnly(traderForm.firmID); 
      
    	
      traderForm.name.focus();
    }
   
}

//覆盖key
function cover_onclick(){
 if(confirm("您需要绑定key值吗？")){
   var str1 = "";
	var ifInstalled = true;
	try
	{
		str1 = ePass.VerifyUser(4,traderForm.traderID.value);	
		//alert(str1);
	}
	catch(err)
	{
	//alert("1");
		ifInstalled = false;	
														
	}	
	if(isNaN(str1))
	{
	}
	else
	{
	   if(parseInt(str1)<0)
	   {
	   	ifInstalled = false;	
	   }
	}
		if(!ifInstalled)
		{
		//alert("2");
			alert("请安装交易控件以正常绑定！");	
			return false;	
		}
		else
		{	if(str1!=-3){
				traderForm.keyCode.value = str1;	
			}else{
				alert("输入信息与key盘信息不符！")
				return false;	
			}			
		}
	}
}

//清空key
function empty_onclick(){
	traderForm.keyCode.value = "";
}

//隐藏控制
function changeManner(id){
	if (id == "0") {
		document.getElementById("id1").style.visibility = "hidden";
		document.getElementById("id2").style.visibility = "hidden";
		document.getElementById("id3").style.visibility = "hidden";
		document.getElementById("id4").style.visibility = "hidden";
		document.getElementById("aaa").className = "yin";
	}else if (id == "1") {
		document.getElementById("id1").style.visibility = "visible";
		document.getElementById("id2").style.visibility = "visible";
		document.getElementById("id3").style.visibility = "visible";
		document.getElementById("id4").style.visibility = "visible";
		document.getElementById("aaa").className = "xian";
	}
}

//联动
function firmToTrader(){
	//alert(traderForm.firmID.value);
	traderForm.traderID.value = traderForm.firmID.value;
	traderForm.traderID.focus();
}
//save
function save_onclick()
{
	if (confirm("您确定要提交吗？")) {
		if (traderForm.crud.value == "create") {
		
		if (traderForm.password.value == "") {
			alert("口令不能为空！");
			return false;
		}
		if (traderForm.confirmPassword.value == "") {
			alert("确认口令不能为空！");
			return false;
		}
	}
		if (traderForm.firmID.value == "") {
			alert("交易商ID不能为空！");
			return false;
		}
		if (traderForm.traderID.value == "") {
			alert("交易员ID不能为空！");
			return false;
		}
		
		if (traderForm.status.value == "") {
			alert("状态不能为空！");
			return false;
		}
		
		if (traderForm.keyStatus.value == "") {
			alert("key码状态不能为空！");
			return false;
		}
    traderForm.submit();
    traderForm.save.disabled = false;
	}
	
 // }
}
//cancel
function cancel_onclick()
{
   window.history.back(-1);
}
//修改密码
function modifyPwd()
{
   pTop("<c:url value="/timebargain/common/modifyPwd1.jsp?type=trader&prikey="/>" + trim(traderForm.traderID.value),300,190);  
}

</script>
	</head>

	<body leftmargin="0" topmargin="0" onLoad="return window_onload()"
		onkeypress="keyEnter(event.keyCode);">
		<table border="0" height="300" width="700" align="center">
			<tr>
				<td>
					<html:form action="/timebargain/baseinfo/trader.do?funcflg=save"
						method="POST" styleClass="form" target="mainFrame">
						<fieldset class="pickList" >
							<legend class="common">
								<b>设置交易员信息
								</b>
							</legend>
							<table border="0" align="center" cellpadding="5" cellspacing="5" width="650" 
								class="common">
									
									
								<tr>
									<td align="right" width="240">
											交易商ID：
									</td>
									<td width="170">
									
                                        <html:text property="firmID" maxlength="10"
											styleClass="text" />
										<span class="req">*</span>
									</td>
									<td align="right" width="100">
											交易员代码：
									</td>
									<td width="260">
										<html:text property="traderID" maxlength="10"
											styleClass="text" />
										<span class="req">*</span>
									</td>
								</tr>	
									
									
								
								<tr>
									<td align="right">
											交易员名称：
									</td>
									<td>
										<html:text property="name" maxlength="8"
											styleClass="text" />
										<span class="req">&nbsp;</span>
									</td>
									<td align="right" width="100">
											交易员状态：
									</td>
									<td>
										<html:select property="status" style="width:153">
											<html:option value=""></html:option>
				                           <html:option value="0"><fmt:message key="traderForm.Status.option.zc"/></html:option>
					                       <html:option value="1"><fmt:message key="traderForm.Status.option.jz"/></html:option>
			                            </html:select>
										<span class="req">*</span>
									</td>
								</tr>
								
								<c:if test="${param['crud'] == 'create'}">  
								<tr>
									<td align="right">
											密码：
									</td>
									<td>
										<html:password property="password" maxlength="64" styleClass="text"/>
										<span class="req">*</span>
									</td>
									
									<td align="right">
											确认密码：
									</td>
									<td>
										<html:password property="confirmPassword" maxlength="64" styleClass="text"/>
										<span class="req">*</span>
									</td>
								</tr>
									
								</c:if>  
								
								<%
									String keycode =(String)request.getAttribute("keycode"); 
									String keyStatus =(String)request.getAttribute("keyStatus"); 
									System.out.println("---=================keycode:"+keycode);
									System.out.println("---=================keyStatus:"+keyStatus);
								%>
								<tr>
									<td  align="right">
										是否使用身份验证key：
									</td>
									<td>
										<input type="radio" name="keyStatus" value="1" onclick="changeManner(1);" <%if("1".equals(keyStatus)){out.println("checked");} %> style="border:0px;">是
             							<input type="radio" name="keyStatus" value="0" onclick="changeManner(0);" <%if("0".equals(keyStatus)){out.println("checked");} %> style="border:0px;">否
										<span class="req">*</span>
									</td>
								</tr>
								<tr class="<%if("0".equals(keyStatus)){%>yin<%}else{%>xian<%}%>" id="aaa">
									<td align="right" id="id1" style="visibility:<%if("0".equals(keyStatus)){%>hidden<%}else{%>visible<%}%>;">
										身份验证key使用状态：
									</td>
									<td id="id2" style="visibility:<%if("0".equals(keyStatus)){%>hidden<%}else{%>visible<%}%>;">
										<%if("".equals(keycode) || keycode == null){%>未绑定<%}else {%>已绑定<%} %>
										
									</td>
									<td id="id3" style="visibility:<%if("0".equals(keyStatus)){%>hidden<%}else{%>visible<%}%>;">
										<html:button property="cover" styleClass="button"
											onclick="javascript:return cover_onclick();">
											绑定key
										</html:button>
									</td>
									<td id="id4" style="visibility:<%if("0".equals(keyStatus)){%>hidden<%}else{%>visible<%}%>;">
										<html:button property="empty" styleClass="button"
											onclick="javascript:return empty_onclick();">
											清空key
										</html:button>
									</td>
								</tr>
								
								
								<tr>
								<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
									<td colspan="2" align="center" width="700">
										<html:button property="save" styleClass="button"
											onclick="javascript:return save_onclick();">
											提交
										</html:button>
										<html:button property="cancel" styleClass="button"
											onclick="javascript:return cancel_onclick();">
											返回
										</html:button>
										<c:if test="${param['crud'] == 'update'}"> 
											<html:button property="cancel" styleClass="button"
											onclick="javascript:modifyPwd();">
											口令修改
											</html:button>								   		
										</c:if>	
									</td>
								</tr>
							</table>
						</fieldset>
						<html:hidden property="crud"/>
						<html:hidden property="keyCode"/>
					</html:form>
				</td>
			</tr>
		</table>

		<html:javascript formName="traderForm" cdata="false"
			dynamicJavascript="true" staticJavascript="false" />
		<script type="text/javascript"
			src="<html:rewrite page="/timebargain/common/validator.jsp"/>"></script>
		<%@ include file="/timebargain/common/messages.jsp"%>
	</body>
</html>
