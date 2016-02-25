<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>
<html>
	<head>
		<LINK REL="stylesheet" type="text/css"
			href="<c:url value="/timebargain/styles/common.css"/>" />
		<script language="JavaScript"
			src="<c:url value="/timebargain/scripts/global.js"/>"></script>
		<script type="text/javascript" src="../../dwr/interface/commodityManager.js"></script>
		<script type="text/javascript" src="../../dwr/engine.js"></script>
		<script type="text/javascript" src="../../dwr/util.js"></script>
		<script language="javascript" src="<%=request.getContextPath()%>/delivery/public/jstools/jquery.js"></script>
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
		
		<title>
		</title>
		<script type="text/javascript"> 
function window_onload()
{
    highlightFormElements();
    if (tradeRuleForm.crud.value == "create") {
    	tradeRuleForm.firmID.focus();
    	tradeRuleForm.type101[0].checked = true;
      	tradeRuleForm.type102[0].checked = true;
      	tradeRuleForm.maxHoldQty.value = "-1";
      	tradeRuleForm.cleanMaxHoldQty.value = "-1";
      	setReadOnly(tradeRuleForm.maxHoldQty);
      	setReadOnly(tradeRuleForm.cleanMaxHoldQty);
    }
    if (tradeRuleForm.crud.value == "update") {
    	setReadOnly(tradeRuleForm.firmID);
      	setReadOnly(tradeRuleForm.commodityID);
      	if (tradeRuleForm.maxHoldQty.value == "-1") {
      		changeManner101(2);
      	}else {
      		changeManner101(1);
      	}
      	if (tradeRuleForm.cleanMaxHoldQty.value == "-1") {
      		changeManner102(2);
      	}else {
      		changeManner102(1);
     	}
    }
}
//save
function save_onclick()
{
	if (confirm("您确定要提交吗？")) {
		if (tradeRuleForm.firmID.value == "") {
			alert("交易商代码不能为空！");
			tradeRuleForm.firmID.focus();
			return false;
		}
		if (tradeRuleForm.commodityID.value == "") {
			alert("商品代码不能为空！");
			tradeRuleForm.commodityID.focus();
			return false;
		}
		if (tradeRuleForm.maxHoldQty.value == "") {
			alert("最大订货量不能为空！");
			tradeRuleForm.maxHoldQty.focus();
			return false;
		}
		if (tradeRuleForm.cleanMaxHoldQty.value == "") {
			alert("最大净订货量不能为空！");
			tradeRuleForm.cleanMaxHoldQty.focus();
			return false;
		}
		if (tradeRuleForm.type101[1].checked == true) {
			if (tradeRuleForm.maxHoldQty.value < 0) {
				alert("最大订货量不能为负！");
				tradeRuleForm.maxHoldQty.focus();
				return false;
			}
		}
		
		if (tradeRuleForm.type102[1].checked == true) {
			if (tradeRuleForm.cleanMaxHoldQty.value < 0) {
				alert("最大净订货量不能为负！");
				tradeRuleForm.cleanMaxHoldQty.focus();
				return false;
			}
		}
    	tradeRuleForm.submit();
    	tradeRuleForm.save.disabled = true;
  		
	}
  
}
function cancel_onclick()
{
   document.location.href = "<c:url value="/timebargain/baseinfo/tradeRule.do?funcflg=searchFirmMaxHoldQty"/>";
}
//commodity_onchange
function commodity_onchange()
{
  var commodity = tradeRuleForm.commodityID.value;
  if(commodity == "")
  {
    return false;
  }
  commodityManager.getCommodityById(commodity,function(commodity)
  {
    if(commodity != null)
    {
      
      tradeRuleForm.maxHoldQty.value = commodity.firmMaxHoldQty;
      tradeRuleForm.cleanMaxHoldQty.value = commodity.firmCleanQty;
      if (commodity.firmMaxHoldQty != "-1") {
      	changeManner101(1);
      	tradeRuleForm.type101[1].checked = true;
      }else {
      	changeManner101(2);
      	tradeRuleForm.type101[0].checked = true;
      }
      if (commodity.firmCleanQty != "-1") {
      	changeManner102(1);
      	tradeRuleForm.type102[1].checked = true;
      }else {
      	changeManner102(2);
      	tradeRuleForm.type102[0].checked = true;
      }
    }
  });   
}


 function changeManner101(id){
 	if (id == "1") {
 		setReadWrite(tradeRuleForm.maxHoldQty);
 	}
 	if (id == "2") {
 		tradeRuleForm.maxHoldQty.value = "-1";
 		setReadOnly(tradeRuleForm.maxHoldQty);
 	}
 }
 
  function changeManner102(id){
 	if (id == "1") {
 		setReadWrite(tradeRuleForm.cleanMaxHoldQty);
 	}
 	if (id == "2") {
 		tradeRuleForm.cleanMaxHoldQty.value = "-1";
 		setReadOnly(tradeRuleForm.cleanMaxHoldQty);
 	}
 }
  function dealTest(){
		var dealerId = tradeRuleForm.firmID.value;
		$.ajax({
			type:"post",
			url:"<c:url value="/timebargain/baseinfo/tradeRule.do?funcflg=getFirmInfo"/>",
			data:"firmId=" + dealerId,
			success : function(data){
						if(data==1){
						  tradeRuleForm.firmID.value = "";
						  $("#hint").html("没有该交易商");
						}else{
						  $("#hint").html("");
						}
			}
		});
	}
</script>
	</head>

	<body leftmargin="0" topmargin="0" onLoad="return window_onload()"
		onkeypress="keyEnter(event.keyCode);">
		<table border="0" height="80%" width="70%" align="center">
			<tr>
				<td>
					<html:form action="/timebargain/baseinfo/tradeRule.do?funcflg=saveFirmMaxHoldQty"
						method="POST" styleClass="form" target="ListFrame1">
						<fieldset class="pickList" >
							<legend class="common">
								<b>
								  设置特殊订货量
								</b>
							</legend>
							<table width="80%" border="0" align="center" cellpadding="5" cellspacing="5"
								class="common">
								<tr>
									<td align="right">
											交易商代码：
									</td>
									<td>
										<html:text size="10" property="firmID" maxlength="16" style="ime-mode:disabled" styleClass="text"  onchange="dealTest();"/>         
										<span class="req">*</span><span id="hint" style="color: red"></span>
									</td>
								</tr>
								
								<tr>
									<td align="right">
											商品代码：
									</td>
									<c:if test="${param['crud'] == 'create'}">
										<td>
										<html:select property="commodityID" onchange = "commodity_onchange();">
                                          <html:options collection="commoditySelect" property="value" labelProperty="label"/>
                                        </html:select>
										<span class="req">*</span>
									</td>
									</c:if>
									<c:if test="${param['crud'] == 'update'}">
									<td>
									<%
										String commodityID = (String)request.getAttribute("commodityID");
										String name = (String)request.getAttribute("name");
										System.out.println("================================="+commodityID);
									%>
										<%=commodityID%>
										<html:hidden property="commodityID" value="<%=commodityID%>"/>
										</td>
									</c:if>
								</tr>
								<tr>
									<%
										String type101 = (String)request.getAttribute("type101");
										String type102 = (String)request.getAttribute("type102");
									%>
									<td align="right">
											最大双边订货量：<input type="radio" name="type101" value="2" onclick="changeManner101(2);" <%if("2".equals(type101)){out.println("checked");} %> style="border:0px;">不限制
													 <input type="radio" name="type101" value="1" onclick="changeManner101(1);" <%if("1".equals(type101)){out.println("checked");} %> style="border:0px;">限制
									</td>
									<td>
										<html:text property="maxHoldQty" maxlength="10" onkeypress="return onlyNumberInput()" style="ime-mode:disabled" styleClass="Number"/>
										<span class="req">*</span>
									</td>
									<td></td>
								</tr>
								<tr>
									<td align="right">
											最大净订货量：<input type="radio" name="type102" value="2" onclick="changeManner102(2);" <%if("2".equals(type102)){out.println("checked");} %> style="border:0px;">不限制
													   <input type="radio" name="type102" value="1" onclick="changeManner102(1);" <%if("1".equals(type102)){out.println("checked");} %> style="border:0px;">限制
									</td>
									<td>
									
										<html:text property="cleanMaxHoldQty" maxlength="15" onkeypress="return onlyNumberInput()" style="ime-mode:disabled" styleClass="Number"/>
										<span class="req">*</span>
									</td>
									<td></td>
								</tr>								
																																											
								<tr>
									<td colspan="3" align="center">
										<html:button property="save" styleClass="button"
											onclick="javascript:return save_onclick();">
											提交
										</html:button>
										
										<html:button property="cancel" styleClass="button"
											onclick="javascript:return cancel_onclick();">
											返回
										</html:button>
									</td>
								</tr>
							</table>
						</fieldset>
						<html:hidden property="crud"/>
					</html:form>
				</td>
			</tr>
		</table>
		<html:javascript formName="tradeRuleForm" cdata="false"
			dynamicJavascript="true" staticJavascript="false" />
		
		<%@ include file="/timebargain/common/messages.jsp"%>
	</body>
</html>
