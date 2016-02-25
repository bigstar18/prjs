<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>
<%@ include file="/timebargain/widgets/calendar/calendar.jsp"%>	

<%@ page import="gnnt.MEBS.timebargain.manage.service.*"%>
<%@ page import="gnnt.MEBS.timebargain.manage.util.*"%>
<%@ page import="gnnt.MEBS.timebargain.manage.webapp.form.*"%>
	<%
		LookupManager lookupMgr = (LookupManager)SysData.getBean("lookupManager");
		request.setAttribute("commoditySelect", lookupMgr
		.getSelectLabelValueByTable("T_COMMODITY", "commodityID",
				"commodityID"," order by commodityID "));
				
		String type3 = (String)request.getAttribute("type3");
		String typeMode = (String)request.getAttribute("typeMode");
		DeductForm deductForm=new DeductForm();
		try {
			deductForm = (DeductForm)request.getAttribute("deductForm");
			//out.print("deductForm.getDeductDate(): "+deductForm.getDeductDate());
		} catch (Exception e) {
			e.printStackTrace();
			out.print("cuo ---------------------");
		}
		
	%>
<html xmlns:MEBS>
	<head>
		<LINK REL="stylesheet" type="text/css"
			href="<c:url value="/timebargain/styles/common.css"/>" />
		<script language="JavaScript"
			src="<c:url value="/timebargain/scripts/global.js"/>"></script>
			<c:import url="/timebargain/common/date.jsp"/>
		<title>
		</title>
		
		<IMPORT namespace="MEBS" implementation="<c:url value="/timebargain/scripts/calendar2.htc"/>">
		
<style type="text/css">
<!--
.yin {
	visibility:hidden;
	position:absolute;
	
}
.xian{
	visibility:visible;
}
-->
</style>
		<script type="text/javascript"> 
function window_onload()
{
    highlightFormElements();
    if (deductForm.crud.value == "create") {
    	deductForm.selfCounteract[0].checked = true;
    }
   	
}
//save
function save_onclick()
{
   if (confirm("您确定要提交吗？")) {
   	if(trim(deductForm.deductDate.value) == "")
    {
       alert("强减日期不能为空！");
       deductForm.deductDate.focus();
 	   return false;
    }
    if(!isDateFomat(deductForm.deductDate.value))
    {
        alert("强减日期格式不正确！\n如：2012-02-08");
        deductForm.deductDate.value = "";
        return false;
    }
   if(trim(deductForm.commodityID.value) == "")
   {
       alert("商品代码不能为空！");
       deductForm.commodityID.focus();
 	   return false;
   }
   if(trim(deductForm.deductPrice.value) == "")
   {
       alert("强减价格不能为空！");
       deductForm.deductPrice.focus();
 	   return false;
   }
   if(trim(deductForm.loserBSFlag.value) == "")
   {
       alert("亏损方买卖标志不能为空！");
       deductForm.loserBSFlag.focus();
 	   return false;
   }
   if(trim(deductForm.loserMode.value) == "")
   {
       alert("亏损方强减模式不能为空！");
       deductForm.loserMode.focus();
 	   return false;
   }
   
   if(trim(deductForm.loserMode.value) == "2")
   {
   	   if (!deductForm.selfCounteract[0].checked && !deductForm.selfCounteract[1].checked && !deductForm.selfCounteract[2].checked) {
   	   		alert("自我对冲不能为空！");
 	   		return false;	
   	   }
   }else {
   		if (!deductForm.selfCounteract[0].checked && !deductForm.selfCounteract[1].checked) {
   			alert("是否对冲不能为空！");
 	   		return false;	
   		}
   }
   
   if(trim(deductForm.lossRate.value) == "")
   {
       alert("亏损比例不能为空！");
       deductForm.lossRate.focus();
 	   return false;
   }
   
   if (trim(deductForm.profitLvl1.value) == "") {
   	   alert("盈利分级比例1不能为空！");
       deductForm.profitLvl1.focus();
 	   return false;
   }
   if (trim(deductForm.profitLvl2.value) == "") {
   	   alert("盈利分级比例2不能为空！");
       deductForm.profitLvl2.focus();
 	   return false;
   }
   if (trim(deductForm.profitLvl3.value) == "") {
   	   alert("盈利分级比例3不能为空！");
       deductForm.profitLvl3.focus();
 	   return false;
   }
   if (trim(deductForm.profitLvl4.value) == "") {
   	   alert("盈利分级比例4不能为空！");
       deductForm.profitLvl4.focus();
 	   return false;
   }
   if (trim(deductForm.profitLvl5.value) == "") {
   	   alert("盈利分级比例5不能为空！");
       deductForm.profitLvl5.focus();
 	   return false;
   }
   var v1 = parseFloat(deductForm.profitLvl1.value);
   var v2 = parseFloat(deductForm.profitLvl2.value);
   var v3 = parseFloat(deductForm.profitLvl3.value);
   var v4 = parseFloat(deductForm.profitLvl4.value);
   var v5 = parseFloat(deductForm.profitLvl5.value);
   if (!(((v1 >= v2) && (v2 >= v3) && (v3 >= v4) && (v4 >= v5)) || (v1 == v2 == v3 == v4 == v5))) {
   	   alert("请按规则填写！");
   	   return false;
   }
   
   deductForm.submit();	
   deductForm.save.disabled = true;
   }
   
}
//cancel

//控制输入字符
 function suffixNamePress()
{
	
  if (event.keyCode>=46 && event.keyCode<=57)  
  {
    event.returnValue=true;
  }
  else
  {
    event.returnValue=false;
  }
}

//控制自我对冲隐现
function selfCounteract_onclick(){
	
	if (deductForm.loserMode.value == "2") {
		document.getElementById("selfId").className = "xian";
	}else {
		document.getElementById("selfId").className = "yin";
	}
}


</script>
	</head>

	<body leftmargin="0" topmargin="0" onLoad="return window_onload()"
		onkeypress="keyEnter(event.keyCode);">
		
		<table border="0" height="300" width="500" align="center">
			<tr>
				<td>
					<html:form action="/timebargain/deduct/deduct.do?funcflg=saveUpdate"
						method="POST" styleClass="form" >
						<fieldset class="pickList" >
							<legend class="common">
								<b>强制减仓配置
								</b>
							</legend>
							<table width="750" border="0" align="center" cellpadding="3" cellspacing="3"
								class="common">
								
								<tr>
									<td align="right">
											强减日期：
									</td>
									<td width="150">
									
									<MEBS:calendar eltID="deductDate" eltName="deductDate" eltCSS="date" eltStyle="width:115px" eltImgPath="<%=skinPath%>/images/" eltValue="<%=deductForm.getDeductDate() == null ? "" : deductForm.getDeductDate()%>" />
									<span class="req">*</span>
									</td>
									<td align="right" width="100">
											商品代码：
									</td>
									<td width="310">
										<html:select property="commodityID"  style="width:115" >
                                          <html:options collection="commoditySelect" property="value" labelProperty="label"/>
                                        </html:select>
									<span class="req">*</span>
									</td>
											                                                           							
								</tr>
								
								<tr>
									<td align="right">
											盈利分级比例1：
									</td>
									<td >
										<html:text property="profitLvl1" onkeyup="this.value=this.value.replace(/\D/g,'')" styleClass="text" maxlength="16" size="15"></html:text>
									<span >%</span><span class="req">*</span>
									</td>
									
									<td align="right">
											强减价格：
									</td>
									<td>
										<html:text property="deductPrice" styleClass="text" onkeypress="return onlyNumberInput()" maxlength="16" size="15"></html:text>
										<span class="req">*</span>
									</td>
								</tr>
								
								<tr>
									<td align="right">
											盈利分级比例2：
									</td>
									<td>
										<html:text property="profitLvl2" onkeyup="this.value=this.value.replace(/\D/g,'')" styleClass="text" maxlength="16" size="15"></html:text>
									<span >%</span><span class="req">*</span>
									</td>
									
									<td align="right">
											亏损方买卖标志：
									</td>
									<td>
										<html:select property="loserBSFlag" style="width:115" >
											<html:option value=""></html:option>
				                           <html:option value="1">买方</html:option>
					                       <html:option value="2">卖方</html:option>
			                  	 		</html:select>
										<span class="req">*</span>
									</td>
								</tr>
								<tr>
									<td align="right">
											盈利分级比例3：
									</td>
									<td>
										<html:text property="profitLvl3" onkeyup="this.value=this.value.replace(/\D/g,'')" styleClass="text" maxlength="16" size="15"></html:text>
									<span >%</span><span class="req">*</span>
									</td>
									
									<td align="right">
											亏损方强减模式：
									</td>
									<td>
										<html:select property="loserMode" style="width:115" onchange="selfCounteract_onclick()">
											<html:option value=""></html:option>
				                           <html:option value="1">所有亏损会员</html:option>
					                       <html:option value="2">亏损且报单会员</html:option>
			                  	 		</html:select>
										<span class="req">*</span>
									</td>
								</tr>
								<tr>
									<td align="right">
											盈利分级比例4：
									</td>
									<td>
										<html:text property="profitLvl4" onkeyup="this.value=this.value.replace(/\D/g,'')" styleClass="text" maxlength="16" size="15"></html:text>
									<span >%</span><span class="req">*</span>
									</td>
									
									<td align="right">
											是否对冲：
									</td>
									<td>
									
									<input type="radio" name="selfCounteract" value="0"  <%if("0".equals(type3)){out.println("checked");} %> style="border:0px;">否
									<input type="radio" name="selfCounteract" value="1"  <%if("1".equals(type3)){out.println("checked");} %> style="border:0px;">所有会员双向订货对冲
             						
             						
										<span id="selfId" class="<%if("2".equals(typeMode)){%>xian<%}else{%>yin<%}%>"><input type="radio" name="selfCounteract" value="2"  <%if("2".equals(type3)){out.println("checked");} %> style="border:0px;">超净订货申报对冲&nbsp;<span class="req">*</span></span>
										
									</td>
								</tr>
								<tr>
									<td align="right">
											盈利分级比例5：
									</td>
									<td>
										<html:text property="profitLvl5" onkeyup="this.value=this.value.replace(/\D/g,'')" styleClass="text" maxlength="16" size="15"></html:text>
										<span >%</span><span class="req">*</span>
									</td>
									<td align="right">
											亏损比例：
									</td>
									<td>
										<html:text property="lossRate" onkeyup="this.value=this.value.replace(/\D/g,'')" styleClass="text" maxlength="16" size="15"></html:text>
									<span >%</span><span class="req">*</span>
									</td>
								</tr>
								<tr>
									
									<td colspan="2">
										<font color="red">(注意：盈利分级比例 P1>=P2>=P3>=P4>=P5)</font>
									</td>
								</tr>	
								
								
								<tr>
								
									<td colspan="4" align="center">
										<html:button property="save" styleClass="button"
											onclick="javascript:return save_onclick();">
											保存并下一步
										</html:button>
										
									</td>
								</tr>
							</table>
						</fieldset>
						<html:hidden property="crud"/>
						<html:hidden property="deductID"/>
					</html:form>
				</td>
			</tr>
			
		</table>

		<%@ include file="/timebargain/common/messages.jsp"%>
	</body>
</html>
