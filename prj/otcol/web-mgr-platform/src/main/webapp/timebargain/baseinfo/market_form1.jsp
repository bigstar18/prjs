<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>
<html>
	<head>
		<LINK REL="stylesheet" type="text/css"
			href="<c:url value="/timebargain/styles/common.css"/>" />
		<script language="JavaScript"
			src="<c:url value="/timebargain/scripts/global.js"/>"></script>
		<script language="JavaScript" src="<c:url value="/timebargain/scripts/open.js"/>"></script>
		<title>
		</title>
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
		<script language="JavaScript"> 
function window_onload()
{
    highlightFormElements();

    
    if(marketForm.crud.value == "create")
    {
      marketForm.marginFBFlag.focus();
      marketForm.type[0].checked = true;
      setDisabled(marketForm.floatingLossComputeType2);
      
    }
    else if(marketForm.crud.value == "update")
    {	
    	changeManner('<%=(String)request.getAttribute("typeFloat")%>');
    }
    changevalue();
    var isCPriceCpFloat=document.getElementById("isCPriceCpFloatingLosss");
    var isCPriceCpFloatingLoss=document.getElementById("isCPriceCpFloatingLoss").value;
		
		for(var i=0;i<isCPriceCpFloat.length;i++){
			
			if(isCPriceCpFloat.options[i].value==isCPriceCpFloatingLoss){
   			
				isCPriceCpFloat.options[i].selected=true;
			}
			
		}
}



//save111111
function save_onclick()
{
	if (confirm("您确定要提交吗？")) {
		
    
   
    
    	
    	if (marketForm.marginFBFlag.value == "") {
    	alert("保证金调整时间不能为空！");
    	return false;
    	}
    	
    	if (marketForm.settleMode.value == "") {
    		alert("交收数据生成方式不能为空！");
    		return false;
    	}
    	if (marketForm.runMode.value == "") {
    		alert("交易运行模式不能为空！");
    		return false;
    	}
    	
    	if (marketForm.floatingProfitSubTax.value == "") {
    		alert("浮动盈亏是否扣税不能为空！");
    		return false;
    	}
    	
    	if (marketForm.gageMode.value == "") {
    		alert("抵顶模式不能为空！");
    		return false;
    	}
    	
    	if (marketForm.tradeTimeType.value == "") {
    		alert("交易时间类型不能为空！");
    		return false;
    	}
    	if (marketForm.asMarginType.value == ""){
    	    alert("提前交收是否收取保证金不能为空！");
    	    return false;
    	}
    	if (marketForm.delayQuoShowType.value == "") {
    		alert("延期交收行情显示类型不能为空！");
    		return false;
    	}
    	if (marketForm.neutralFeeWay.value == "") {
    		alert("中立仓交收手续费收取方式不能为空！");
    		return false;
    	}
    	if (marketForm.delayOrderIsPure.value == "") {
    		alert("交收申报是否按净订货量不能为空！");
    		return false;
    	}
    	if (marketForm.chargeDelayFeeType.value == "") {
    		alert("延期补偿金收取类型为空！");
    		return false;
    	}
    	
    	//alert(marketForm.type[0].checked == true);
    	if (marketForm.type[0].checked == true) {
    		if (marketForm.floatingLossComputeType1.value == "") {
    			alert("只记浮亏不能为空！");
    			marketForm.floatingLossComputeType1.focus();
    			return false;
    		}
    		marketForm.floatingLossComputeType2.value = "";
    		
    	}
    	if (marketForm.type[1].checked == true) {
    		if (marketForm.floatingLossComputeType2.value == "") {
    			alert("计盈亏不能为空！");
    			marketForm.floatingLossComputeType2.focus();
    			return false;
    		}
    		marketForm.floatingLossComputeType1.value = "";
    	}
	    marketForm.submit();
	    marketForm.save.disabled = true;
	}
    
    //}
}
//cancel
function cancel_onclick()
{
   document.location.href = "<c:url value="/timebargain/baseinfo/market.do"/>";
}

function changeManner(id){
	if (id == "1") {
		setEnabled(marketForm.floatingLossComputeType1);
		setDisabled(marketForm.floatingLossComputeType2);
		var isCPriceCpFloat=document.getElementById("isCPriceCpFloatingLosss");
		
		isCPriceCpFloat.length=0;
		var option =new Option('用结算价计算浮亏','0');
		isCPriceCpFloat.options.add(option);
		var option2 =new Option('用最新价计算浮亏','1');
		isCPriceCpFloat.options.add(option2);
	}
	if (id == "2") {
		setEnabled(marketForm.floatingLossComputeType2);
		setDisabled(marketForm.floatingLossComputeType1);
		changevalue();
	}
}
function changevalue(){
	var floatingLossComputeType2=document.getElementById("floatingLossComputeType2").value;
	var isCPriceCpFloat=document.getElementById("isCPriceCpFloatingLosss");
	if(floatingLossComputeType2==4){
		isCPriceCpFloat.length=0;
		var option =new Option('盘中按昨结算，盘后按结算','0');
		isCPriceCpFloat.options.add(option);
      //alert(isCPriceCpFloat.text);
	}else{
		isCPriceCpFloat.length=0;
		var option =new Option('用结算价计算浮亏','0');
		isCPriceCpFloat.options.add(option);
		var option2 =new Option('用最新价计算浮亏','1');
		isCPriceCpFloat.options.add(option2);
	}

	marketForm.isCPriceCpFloatingLoss.value = document.getElementById("isCPriceCpFloatingLosss").value;
}


//---------------------------start baseinfo-------------------------------
var tmp_baseinfo;
var tmp_baseinfo_up = true;
function baseinfo_onclick()
{
  if (tmp_baseinfo_up)
  {
    tmp_baseinfo_up = false;
    marketForm.baseinfo_img.src = "<c:url value="/timebargain/images/ctl_detail_Down.gif"/>";
    if(marketForm.crud.value == "create")
    {
    	marketForm.tmp_tradePassword.value = marketForm.tradePassword.value;
    	marketForm.tmp_confirmPassword.value = marketForm.confirmPassword.value;
    }
    tmp_baseinfo = baseinfo.innerHTML;
    baseinfo.innerHTML = "";
  }
  else
  {
    tmp_baseinfo_up = true;
    marketForm.baseinfo_img.src = "<c:url value="/timebargain/images/ctl_detail_Up.gif"/>";
    baseinfo.innerHTML = tmp_baseinfo;
    if(marketForm.crud.value == "create")
    {
    	marketForm.tradePassword.value = marketForm.tmp_tradePassword.value;
    	marketForm.confirmPassword.value = marketForm.tmp_confirmPassword.value;
    }
  }
}
//-----------------------------end baseinfo-----------------------------
//---------------------------start paraminfo-------------------------------
var tmp_paraminfo;
var tmp_paraminfo_up = true;
function paraminfo_onclick()
{
  if (tmp_paraminfo_up)
  {
    tmp_paraminfo_up = false;
    marketForm.paraminfo_img.src = "<c:url value="/timebargain/images/ctl_detail_Down.gif"/>";
    tmp_paraminfo = paraminfo.innerHTML;
    paraminfo.innerHTML = "";
  }
  else
  {
    tmp_paraminfo_up = true;
    marketForm.paraminfo_img.src = "<c:url value="/timebargain/images/ctl_detail_Up.gif"/>";
    paraminfo.innerHTML = tmp_paraminfo;
  }
}
//-----------------------------end paraminfo-----------------------------
</script>
	</head>

	<body onLoad="return window_onload()"
		onkeypress="keyEnter(event.keyCode);">
		<table border="0" height="400" width="700" align="center">
			<tr>
				<td>
					<html:form action="/timebargain/baseinfo/market.do?funcflg=save"
						method="POST" styleClass="form">
						<fieldset class="pickList">
							<legend class="common">
								<b>市场交易参数
								</b>
							</legend>

<table width="100%" border="0" align="center"  class="common" cellpadding="0" cellspacing="2">
<!-- 基本信息 -->
      					
        	
<!-- 参数信息 -->
        <tr class="common">
          <td colspan="4">
            
<span id="paraminfo">
<table cellSpacing="0" cellPadding="0" width="700" border="0" align="center" class="common">    
						<%
							String typeFloat = (String)request.getAttribute("typeFloat");
						%>    
								<tr>
									
										
									
									<td align="right">
										交易运行模式：
									</td>
									<td >
										<html:select property="runMode" style="width:120">
											<html:option value=""></html:option>
				                           <html:option value="0">手动</html:option>
					                       <html:option value="1">自动</html:option>
			                            </html:select> <span class="req">*</span>
									</td>	
									<td align="right" >
											抵顶模式：
									</td>
									<td>
										<html:select property="gageMode" style="width:120">
											<html:option value=""></html:option>
				                           <html:option value="0">全抵顶</html:option>
					                       <html:option value="1">半抵顶</html:option>
			                            </html:select> <span class="req">*</span>
									</td>						
								</tr>
								<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
								<tr>									
									<td align="right">
											保证金调整时间：
									</td>
									<td>
										<html:select property="marginFBFlag" style="width:120">
											<html:option value=""></html:option>
				                           <html:option value="0">结算时调整</html:option>
					                       <html:option value="1">开市时调整</html:option>
			                            </html:select> <span class="req">*</span>
									</td>
									 	
										
									<td align="right" >
											交易时间类型：
									</td>
									<td>
										<html:select property="tradeTimeType" style="width:120">
											<html:option value=""></html:option>
				                           <html:option value="0">同一天交易</html:option>
					                       <html:option value="1">跨天交易</html:option>
			                            </html:select> <span class="req">*</span>
									</td>
								</tr>
								
								<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
								<tr>									
									 
									<td align="right" >
											交收数据生成方式：
									</td>
									<td>
										<html:select property="settleMode" style="width:120">
											<html:option value=""></html:option>
				                           <html:option value="0">自动</html:option>
					                       <html:option value="1">手动</html:option>
			                            </html:select> <span class="req">*</span>
									</td>
										
									<td align="right">
											浮动盈亏是否扣税：
									</td>
									<td>
										<html:select property="floatingProfitSubTax" style="width:120">
											<html:option value=""></html:option>
				                           <html:option value="0">不扣税</html:option>
					                       <html:option value="1">扣税</html:option>
			                            </html:select> <span class="req">*</span>
									</td>
								</tr>
								
								<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
								<%
									String useDelay = (String) request.getAttribute("useDelay");
									if (useDelay.equals("Y")) {
								%>
								<tr>									
									<td align="right" width="160">
											延期交收行情显示类型：
									</td>
									<td>
										<html:select property="delayQuoShowType" style="width:120">
											<html:option value=""></html:option>
				                           <html:option value="0">交收和中立仓显示</html:option>
					                       <html:option value="1">实时显示</html:option>
			                            </html:select> <span class="req">*</span>
									</td>	
										
									<td align="right" width="160">
											中立仓交收手续费收取方式：
									</td>
									<td>
										<html:select property="neutralFeeWay" style="width:120">
											<html:option value=""></html:option>
				                           <html:option value="0">不收</html:option>
					                       <html:option value="1">收取</html:option>
			                            </html:select> <span class="req">*</span>
									</td>
								</tr>
								<tr>
								<td align="right" width="160">
											延期补偿金收取类型：
									</td>
									<td>
										<html:select property="chargeDelayFeeType" style="width:120">
											<html:option value=""></html:option>
				                           <html:option value="0">按净订货量收取</html:option>
					                       <html:option value="1">按单边订货总量收取</html:option>
			                            </html:select> <span class="req">*</span>
									</td>	
									<td align="right" width="160">
											交收申报是否按净订货量：
									</td>
									<td>
										<html:select property="delayOrderIsPure" style="width:120">
											<html:option value=""></html:option>
				                           <html:option value="0">否</html:option>
					                       <html:option value="1">是</html:option>
			                            </html:select> <span class="req">*</span>
									</td>	
								
								</tr>
								<%
									} else if (useDelay.equals("N")) {
								%>
									<html:hidden property="delayQuoShowType" />
									<html:hidden property="neutralFeeWay" />
									<html:hidden property="chargeDelayFeeType" />
									<html:hidden property="delayOrderIsPure" />
								<%
									}
								%>
								<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
								<tr> 
								    <td align="right" width="160">
											提前交收是否收取保证金：
									</td>
									<td>                        
										<html:select property="asMarginType" style="width:120">
											<html:option value=""></html:option>
				                           <html:option value="0">不收取</html:option>
					                       <html:option value="1">收取</html:option>
			                            </html:select> <span class="req">*</span>
									</td>
																	
									</tr>
								<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
								
							
								
																	
</table >
</span>
            
          </td>
        </tr>        			
        
        						<tr>
        							<td>
        				<fieldset class="pickList">
							<legend class="common">
								<b>浮亏计算方式
								</b>
							</legend>

<table width="700" border="0" align="center"  class="common" cellpadding="0" cellspacing="0">
					
				<tr>
				
									<td>
										<input type="radio" name="type" onclick="changeManner(1);" <%if("1".equals(typeFloat)){out.println("checked");} %> style="border:0px;">						
									</td>
					<td align="right" width="140">
											盈亏对冲计亏不计盈：
									</td>
									<td width="140">
									
										<html:select property="floatingLossComputeType1" style="width:180">
											 <html:option value=""></html:option> 
				                           <html:option value="0">同商品同方向盈亏对冲</html:option>
					                       <html:option value="1">同商品盈亏对冲</html:option>
					                       <html:option value="2">所有商品盈亏对冲</html:option>
			                            </html:select>
									</td>	
									
									<td>&nbsp;</td><td>&nbsp;</td>
									<td>&nbsp;</td><td>&nbsp;</td>
									<td>&nbsp;</td><td>&nbsp;</td>
									<td>&nbsp;</td><td>&nbsp;</td>
									<td>
										<input type="radio" name="type"  onclick="changeManner(2);" <%if("2".equals(typeFloat)){out.println("checked");} %> style="border:0px;">
									</td>
					<td align="right" width="100">
											无负债模式：
									</td>
									<td width="190">
										<html:select property="floatingLossComputeType2" style="width:120" onchange="changevalue()">
											 <html:option value=""></html:option> 
				                           <html:option value="3">实时无负债</html:option>
					                       <html:option value="4">每日无负债</html:option>
			                            </html:select> 
									</td>	
						
									
				</tr>	
				<tr>
				                  <td></td>
				                   <td align="right" width="140">
											计算盈亏方式：
									</td>
									<td>
									<input type="hidden" value="${isCPriceCpFloatingLoss }" id="isCPriceCpFloatingLoss"/>
										<select id="isCPriceCpFloatingLosss" name="isCPriceCpFloatingLosss" style="width:180" >
										<% if("1".equals(typeFloat)){ %>
				                          
					                       <script type="text/javascript">
				                       		var isCPriceCpFloat=document.getElementById("isCPriceCpFloatingLosss");
					                       isCPriceCpFloat.length=0;
			                       			var option =new Option('用结算价计算浮亏','0');
			                       			isCPriceCpFloat.options.add(option);
			                       			var option2 =new Option('用最新价计算浮亏','1');
			                       			isCPriceCpFloat.options.add(option2);
			                       			var isCPriceCpFloatingLoss=document.getElementById("isCPriceCpFloatingLoss").value;
			                       			
			                       			for(var i=0;i<isCPriceCpFloat.length;i++){
				                       			
				                       			if(isCPriceCpFloat.options[i].value==isCPriceCpFloatingLoss){
					                       			
				                       				isCPriceCpFloat.options[i].selected=true;
				                       			}
				                       			
			                       			}
					                      		
					                      		 
					                     </script>			
					                       
					                       <%}else{ %>
					                       <script type="text/javascript">
					                       		var  floatingLossComputeType2=document.getElementById("floatingLossComputeType2").value;
					                       		var isCPriceCpFloat=document.getElementById("isCPriceCpFloatingLosss");
					                       	
					                       		if(floatingLossComputeType2==4){
					                       			isCPriceCpFloat.length=0;
					                       			var option =new Option('盘中按昨结算，盘后按结算','0');
					                       			isCPriceCpFloat.options.add(option);
					                       	      //alert(isCPriceCpFloat.text);
					                       		}else{
					                       			isCPriceCpFloat.length=0;
					                       			var option =new Option('用结算价计算浮亏','0');
					                       			isCPriceCpFloat.options.add(option);
					                       			var option2 =new Option('用最新价计算浮亏','1');
					                       			isCPriceCpFloat.options.add(option2);
					                       			var isCPriceCpFloatingLoss=document.getElementById("isCPriceCpFloatingLoss").value;
					                       			for(var i=0;i<isCPriceCpFloat.length;i++){
						                       			
						                       			if(isCPriceCpFloat.options[i].value==isCPriceCpFloatingLoss){
						                       				isCPriceCpFloat.options[i].selected=true;
						                       			}
						                       			
					                       			}
					                       							                       			
					                       		}
					                       </script>
					                       <% }%>
					                      
					                       
			                            </select> 
			                            
			                           <html:hidden property="isCPriceCpFloatingLoss" />
									</td>	
				</tr>
</table>
        							</td>
        						</tr>				
																																																	
								<tr>
									<td colspan="4" align="center">
										<html:button property="save" styleClass="button"
											onclick="javascript:return save_onclick();">
											提交
										</html:button>
									<!--	<html:button property="cancel" styleClass="button"
											onclick="javascript:return cancel_onclick();">
											<fmt:message key="button.return" />
										</html:button> -->
									</td>
								</tr>
							</table>
						</fieldset>
						<html:hidden property="crud" />
						<html:hidden property="marketCode" />
						<html:hidden property="shortName" />
						<html:hidden property="tradePriceAlgr" value="0"/>
					</html:form>
				</td>
			</tr>
		</table>

		<html:javascript formName="marketForm" cdata="false"
			dynamicJavascript="true" staticJavascript="false" />
		<script language="javascript"
			src="<html:rewrite page="/timebargain/common/validator.jsp"/>"></script>
		<%@ include file="/timebargain/common/messages.jsp"%>
	</body>
</html>
