<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>
<html>
	<head>
		<LINK REL="stylesheet" type="text/css"
			href="<c:url value="/timebargain/styles/common.css"/>" />
		<script language="JavaScript"
			src="<c:url value="/timebargain/scripts/global.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/timebargain/widgets/dwr/interface/commodityManager.js"/>"></script>
			
		    <script type="text/javascript" src="<c:url value="/timebargain/widgets/dwr/engine.js"/>"></script>
		    <script type="text/javascript" src="<c:url value="/timebargain/widgets/dwr/util.js"/>"></script>	
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
    if (tradeBreedRuleForm.crud.value == "create") {
    	tradeBreedRuleForm.firmID.focus();
    	tradeBreedRuleForm.type101[0].checked = true;
      	tradeBreedRuleForm.type102[0].checked = true;
      	tradeBreedRuleForm.maxHoldQty.value = "-1";
      	tradeBreedRuleForm.cleanMaxHoldQty.value = "-1";
      	setReadOnly(tradeBreedRuleForm.maxHoldQty);
      	setReadOnly(tradeBreedRuleForm.cleanMaxHoldQty);
    }
    if (tradeBreedRuleForm.crud.value == "update") {
    	setReadOnly(tradeBreedRuleForm.firmID);
      	setReadOnly(tradeBreedRuleForm.breedID);
      	if (tradeBreedRuleForm.maxHoldQty.value == "-1") {
      		changeManner101(2);
      	}else {
      		changeManner101(1);
      	}
      	if (tradeBreedRuleForm.cleanMaxHoldQty.value == "-1") {
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
		if (tradeBreedRuleForm.firmID.value == "") {
			alert("交易商代码不能为空！");
			tradeBreedRuleForm.firmID.focus();
			return false;
		}
		if (tradeBreedRuleForm.breedID.value == "") {
			alert("品种代码不能为空！");
			tradeBreedRuleForm.breedID.focus();
			return false;
		}
		if (tradeBreedRuleForm.maxHoldQty.value == "") {
			alert("最大订货量不能为空！");
			tradeBreedRuleForm.maxHoldQty.focus();
			return false;
		}
		if (tradeBreedRuleForm.cleanMaxHoldQty.value == "") {
			alert("最大净订货量不能为空！");
			tradeBreedRuleForm.cleanMaxHoldQty.focus();
			return false;
		}
		if (tradeBreedRuleForm.type101[1].checked == true) {
			if (tradeBreedRuleForm.maxHoldQty.value < 0) {
				alert("最大订货量不能为负！");
				tradeBreedRuleForm.maxHoldQty.focus();
				return false;
			}
		}
		
		if (tradeBreedRuleForm.type102[1].checked == true) {
			if (tradeBreedRuleForm.cleanMaxHoldQty.value < 0) {
				alert("最大净订货量不能为负！");
				tradeBreedRuleForm.cleanMaxHoldQty.focus();
				return false;
			}
		}
		if(confirm("是否级联变动该品种下商品的交易商特殊订货量？")){
    		tradeBreedRuleForm.action="<c:url value='/timebargain/baseinfo/tradeBreedRule.do?funcflg=saveFirmBreedMaxHoldQty&logos=true'/>";
			tradeBreedRuleForm.submit();
		}else{
			tradeBreedRuleForm.submit();
		}
    	tradeBreedRuleForm.save.disabled = true;
  		
	}
  
}

//commodity_onchange
function commodity_onchange()
{
  var commodity = tradeBreedRuleForm.breedID.value;
  if(commodity == "")
  {
    return false;
  }
  commodityManager.getCommodityById(commodity,function(commodity)
  {
    if(commodity != null)
    {
      
      tradeBreedRuleForm.maxHoldQty.value = commodity.firmMaxHoldQty;
      tradeBreedRuleForm.cleanMaxHoldQty.value = commodity.firmCleanQty;
      if (commodity.firmMaxHoldQty != "-1") {
      	changeManner101(1);
      	tradeBreedRuleForm.type101[1].checked = true;
      }else {
      	changeManner101(2);
      	tradeBreedRuleForm.type101[0].checked = true;
      }
      if (commodity.firmCleanQty != "-1") {
      	changeManner102(1);
      	tradeBreedRuleForm.type102[1].checked = true;
      }else {
      	changeManner102(2);
      	tradeBreedRuleForm.type102[0].checked = true;
      }
    }
  });   
}


 function changeManner101(id){
 	if (id == "1") {
 		setReadWrite(tradeBreedRuleForm.maxHoldQty);
 	}
 	if (id == "2") {
 		tradeBreedRuleForm.maxHoldQty.value = "-1";
 		setReadOnly(tradeBreedRuleForm.maxHoldQty);
 	}
 }
 
  function changeManner102(id){
 	if (id == "1") {
 		setReadWrite(tradeBreedRuleForm.cleanMaxHoldQty);
 	}
 	if (id == "2") {
 		tradeBreedRuleForm.cleanMaxHoldQty.value = "-1";
 		setReadOnly(tradeBreedRuleForm.cleanMaxHoldQty);
 	}
 }
  function dealFirm(){
		var dealerId = tradeBreedRuleForm.firmID.value;
		$.ajax({
			type:"post",
			url:"<c:url value="/timebargain/baseinfo/tradeRule.do?funcflg=getFirmInfo"/>",
			data:"firmId=" + dealerId,
			success : function(data){
						if(data==1){
						  tradeBreedRuleForm.firmID.value = "";
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
					<html:form action="/timebargain/baseinfo/tradeBreedRule.do?funcflg=saveFirmBreedMaxHoldQty"
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
										<html:text size="10" property="firmID" maxlength="16" style="ime-mode:disabled" styleClass="text" onchange="dealFirm()"/>         
										<span class="req">*</span><span id="hint" style="color: red"></span>
									</td>
								</tr>
								
								<tr>
									<td align="right">
											品种代码：
									</td>
									<c:if test="${param['crud'] == 'create'}">
										<td>
										<!-- onchange="javascript:commodity_onchange()" -->
											<html:select property="breedID" >
	                                          <html:options collection="breedSelect" property="value" labelProperty="label"/>
	                                        </html:select>
										<span class="req">*</span>
										</td>
									</c:if>
									<c:if test="${param['crud'] == 'update'}">
									<td>
									<%
										String breedID = (String)request.getAttribute("breedID");
										String name = (String)request.getAttribute("name");
										System.out.println("================================="+breedID);
									%>
										<%=breedID%>
										<html:hidden property="breedID" value="<%=breedID%>"/>
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
										
										<html:reset  property="reset" onclick="javascript:tradeBreedRuleForm.maxHoldQty.focus();" styleClass="button">重置</html:reset>
									</td>
								</tr>
							</table>
						</fieldset>
						<html:hidden property="crud"/>
					</html:form>
				</td>
			</tr> 
		</table>
		<%@ include file="/timebargain/common/messages.jsp"%>
	</body>
</html>
