<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>
<html>
	<head>
		<LINK REL="stylesheet" type="text/css"
			href="<c:url value="/timebargain/styles/common.css"/>" />
		<script language="JavaScript"
			src="<c:url value="/timebargain/scripts/global.js"/>"></script>
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
<script type="text/javascript">
//页面装载时调用 
function window_onload()
{
    highlightFormElements();
    init();
    ordersForm.customerID.focus();
    //ordersForm.marketCode.value='01';
 //   market_onchange();
    //setDisabled(ordersForm.closeFlag); 

    
}
//公共的方法
function init()
{
   //setDisabled(ordersForm.closeFlag);
    setDisabled(ordersForm.closeMode);
    setDisabled(ordersForm.timeFlag);
    setReadOnly(ordersForm.specPrice);
    spanTime.style.visibility = "hidden";
    spanPrice.style.visibility = "hidden";
    inCloseMode.style.visibility = "hidden";
}
//下单提交
function order_onclick()
{
  	
    if (confirm("您确定要提交吗？")) {
    	if (ordersForm.customerID.value == "") {
  		alert("二级代码不能为空！");
  		ordersForm.customerID.focus();
  		return false;
  	}
  	if (ordersForm.commodityID.value == "") {
  		alert("商品代码不能为空！");
  		ordersForm.commodityID.focus();
  		return false;
  	}
  	if (ordersForm.BS_Flag.value == "") {
  		alert("买卖标志不能为空！");
  		ordersForm.BS_Flag.focus();
  		return false;
  	}
  	if (ordersForm.orderType.value == "") {
  		alert("委托类型不能为空！");
  		ordersForm.orderType.focus();
  		return false;
  	}
  	if (ordersForm.price.value == "") {
  		alert("委托价格不能为空！");
  		ordersForm.price.focus();
  		return false;
  	}
  	if (ordersForm.quantity.value == "") {
  		alert("委托数量不能为空！");
  		ordersForm.quantity.focus();
  		return false;
  	}
  	if (ordersForm.quantity.value == 0) {
  		alert("委托数量不能为零！");
  		ordersForm.quantity.focus();
  		return false;
  	}
  	if (ordersForm.orderType.value == "2") {
  		if (ordersForm.closeMode.value == "") {
  			alert("转让方式不能为空！");
  			ordersForm.closeMode.focus();
  			return false;
  		}
  	}
  	
    if (ordersForm.orderType.value == "2") {
		if (ordersForm.closeFlag.checked) {
			ordersForm.closeFlag.value = "2";
		}else {
			ordersForm.closeFlag.value = "1";
		}
	}else if (ordersForm.orderType.value == "1") {
	
		if (ordersForm.closeFlag.checked == false) {
		//alert("这");
			ordersForm.closeFlag.value = "";
		//	alert(ordersForm.closeFlag.value);
		}
		
	}
   
    if(ordersForm.orderType.value == "2")  //pc 
    {
      
      if(ordersForm.closeMode.value == "2")  //timeFlag
      {
        if(ordersForm.timeFlag.value == "")
        {
          alert("指定时间标志不能为空！");
          ordersForm.timeFlag.focus();
		  return false;
        }
      }
      else if(ordersForm.closeMode.value == "3")  //specPrice
      {
        if(trim(ordersForm.specPrice.value) == "")
        {
          alert("指定价格不能为空！");
          ordersForm.specPrice.focus();
		  return false;
        }
      }
    }
   
    //先检查交易员是否登录
    ordersForm.submit();   
    }
 
}
//交易员是否登录后提交委托(统一回调函数)
function order()
{	
	
	ordersForm.action = "<c:url value="/timebargain/order/orders.do?funcflg=order"/>";
	ordersForm.submit();
	ordersForm.order.disabled = true;
	ordersForm.action = "<c:url value="/timebargain/order/orders.do?funcflg=chkLogin"/>";
}

//委托类型变化事件
function orderType_onchange()
{
  if(ordersForm.orderType.value == "1")
  {
    init();
    setDisabled(ordersForm.closeFlag);
  }
  else if(ordersForm.orderType.value == "2")
  {
  	inCloseMode.style.visibility = "visible";
  	
 	setEnabled(ordersForm.closeFlag);
    setEnabled(ordersForm.closeMode);
    ordersForm.closeMode.focus();
  }
}
//转让模式变化事件
function closeMode_onchange()
{
  if(ordersForm.closeMode.value == "1")
  {
    setDisabled(ordersForm.timeFlag);
    setReadOnly(ordersForm.specPrice);
    spanTime.style.visibility = "hidden";
    spanPrice.style.visibility = "hidden";
  }
  else if(ordersForm.closeMode.value == "2")
  {
    setEnabled(ordersForm.timeFlag);
    setReadOnly(ordersForm.specPrice);
    spanTime.style.visibility = "visible";
    spanPrice.style.visibility = "hidden";
    ordersForm.timeFlag.focus();
  }
  else if(ordersForm.closeMode.value == "3")
  {
    setDisabled(ordersForm.timeFlag);
    setReadWrite(ordersForm.specPrice);
    spanTime.style.visibility = "hidden";
    spanPrice.style.visibility = "visible";
    ordersForm.specPrice.focus();
  }
}

//market_onchange
function market_onchange()
{
  parent.HiddFrame.location.href = "<c:url value="/timebargain/order/orders.do?funcflg=marketChg&MarketCode="/>" + ordersForm.marketCode.value;
}

//logoff
function logoff_onclick()
{
	parent.HiddFrame.location.href = "<c:url value="/timebargain/order/orders.do?funcflg=logoff&mkName=order"/>";
}

function isCheck(){
	if (ordersForm.closeFlag.checked) {
		document.getElementById("td1").className = 'xian';
		document.getElementById("td2").className = 'xian';
		ordersForm.forceCloseType.value = "1";
	}else {
		document.getElementById("td1").className = 'yin';
		document.getElementById("td2").className = 'yin';
	}
}

function forceCloseType_onchange(){
	if (ordersForm.forceCloseType.value == "1") {
		ordersForm.price.value = "";
		setReadWrite(ordersForm.price);
	}
	if (ordersForm.forceCloseType.value == "2") {
		ordersForm.price.value = "0";
		setReadOnly(ordersForm.price);
	}
}
</script>
	</head>

	<body leftmargin="0" topmargin="0" onLoad="return window_onload()"
		onkeypress="keyEnter(event.keyCode);">
		<table border="0" height="400" width="700" align="center">
			<tr>
				<td>
					<html:form action="/timebargain/order/orders.do?funcflg=chkLogin" method="POST" target="HiddFrame">
						<fieldset>
							<legend class="common">
								<b>代为委托
								</b>
							</legend>
							<table border="0" align="center" cellpadding="2" cellspacing="0"
								class="common">
	    <tr>	     
			<td align="right"><label>二级代码：</label></td>
			<td>
				<html:text property="customerID" maxlength="16" styleClass="text" style="width:105;ime-mode:disabled"  />
				<span class="req">*</span>			
			</td> 
		
            <td align="right"><label>商品代码：</label></td>
			<td>
			    <html:text property="commodityID"  maxlength="16"  style="width:105;ime-mode:disabled"/>                            
				<span class="req">*</span>
			</td>
            <td align="right"><label>买卖：</label></td>
			<td>
				<html:select property="BS_Flag" style="width:105">
				    <html:option value=""></html:option>
					<html:option value="1">买进</html:option>
					<html:option value="2">卖出</html:option>
			    </html:select>
				<span class="req">*</span>
			</td>
            	
            <html:hidden property="marketCode"/>				
		</tr>																
		<tr>
			<td align="right"><label>委托类型：</label></td>
			<td>
				<html:select property="orderType" onchange="orderType_onchange()" style="width:105">
				    <html:option value=""></html:option>
					<html:option value="1">订立</html:option>
					<html:option value="2">转让</html:option>
			    </html:select>
				<span class="req">*</span>
			</td>
			<td align="right"><label>委托价格：</label></td>
			<td>
				<html:text property="price" maxlength="16"  style="ime-mode:disabled;width:105"  onkeypress="return numberPass()" styleClass="Number"/>
				<span class="req">*</span>
			</td>	
			<td align="right"><label>委托数量：</label></td>
			<td>
				<html:text property="quantity" maxlength="16" onkeypress="return numberPass()" style="ime-mode:disabled;width:105" styleClass="Number"/>
				<span class="req">*</span>
			</td>					
	     </tr>
	     <tr>
			<td align="right"><label>转让方式：</label></td>
			<td>
				<html:select property="closeMode" onchange="closeMode_onchange()" style="width:105">
				    <html:option value=""></html:option>
				    <html:option value="1">不指定转让</html:option>
					<html:option value="2">指定时间转让</html:option>
					<html:option value="3">指定价格转让</html:option>
			    </html:select>
				<span id="inCloseMode" class="req" style="visibility:hidden">*</span>			
			</td>	
			<td align="right"><label>指定时间标志：</label></td>
			<td>
				<html:select property="timeFlag" style="width:105">
				    <html:option value=""></html:option>
					<html:option value="1">转让今订货</html:option>
					<html:option value="2">转让历史订货</html:option>
			    </html:select>
				<span id="spanTime" class="req" style="visibility:hidden">*</span>
			</td>		
			<td align="right"><label>指定价格：</label></td>
			<td>
				<html:text property="specPrice" maxlength="16" styleClass="ReadOnlyNumber" onkeypress="return numberPass()"  style=" ime-mode:disabled;width:105 " />
				<span id="spanPrice" class="req" style="visibility:hidden">*</span>
			</td>
		</tr>
		<%
			String type = (String)request.getAttribute("type");
		%>
	    <tr class="<%if("1".equals(type)){%>xian<%}else{%>yin<%}%>">
	      <td></td> 
	      <td>强行转让：<input type="checkbox" name="closeFlag" value="2"  class="NormalInput" onclick="isCheck()"/></td> 
	      
	      <td id="td1" class="yin" >强行转让价格方式：</td>
            <td id="td2" class="yin">
            	<html:select property="forceCloseType" style="width:105" onchange="forceCloseType_onchange()">
					<html:option value=""></html:option>
				    <html:option value="1">指定价格</html:option>
					<html:option value="2">按市价</html:option>
			   </html:select>
				<span class="req">*</span>
            </td>
	    </tr>
	    
	     <tr>
			<td colspan="2" height="10"></td>
		 </tr>
		 
	     <tr>	       							
			<td colspan="4" align="center">
			  <html:button  property="order" styleClass="button" onclick="javascript:return order_onclick();">确认</html:button>
			  <html:reset  property="reset" styleClass="button">重置</html:reset>	
			  <c:if test="${not empty sessionID}">
			  	<html:button  property="logoff" styleClass="button" onclick="logoff_onclick()">注销登录</html:button>
			  </c:if>		  
			</td>
		</tr>
		<tr>
			<td colspan="2" height="10"></td>
		 </tr>	
							</table>
						</fieldset>
					<!-- 	<html:hidden property="closeFlag" value="1"/> -->
					<html:hidden property="type"/>
					</html:form>
				</td>
			</tr>
		</table>
<html:javascript formName="ordersForm" cdata="false" dynamicJavascript="true" staticJavascript="false"/>
<script type="text/javascript" src="<html:rewrite page="/timebargain/common/validator.jsp"/>"></script>

<%@ include file="/timebargain/common/messages.jsp" %>
	</body>
</html>
