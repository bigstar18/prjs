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
//ҳ��װ��ʱ���� 
function window_onload()
{
    highlightFormElements();
    init();
    ordersForm.customerID.focus();
    //ordersForm.marketCode.value='01';
 //   market_onchange();
    //setDisabled(ordersForm.closeFlag); 

    
}
//�����ķ���
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
//�µ��ύ
function order_onclick()
{
  	
    if (confirm("��ȷ��Ҫ�ύ��")) {
    	if (ordersForm.customerID.value == "") {
  		alert("�������벻��Ϊ�գ�");
  		ordersForm.customerID.focus();
  		return false;
  	}
  	if (ordersForm.commodityID.value == "") {
  		alert("��Ʒ���벻��Ϊ�գ�");
  		ordersForm.commodityID.focus();
  		return false;
  	}
  	if (ordersForm.BS_Flag.value == "") {
  		alert("������־����Ϊ�գ�");
  		ordersForm.BS_Flag.focus();
  		return false;
  	}
  	if (ordersForm.orderType.value == "") {
  		alert("ί�����Ͳ���Ϊ�գ�");
  		ordersForm.orderType.focus();
  		return false;
  	}
  	if (ordersForm.price.value == "") {
  		alert("ί�м۸���Ϊ�գ�");
  		ordersForm.price.focus();
  		return false;
  	}
  	if (ordersForm.quantity.value == "") {
  		alert("ί����������Ϊ�գ�");
  		ordersForm.quantity.focus();
  		return false;
  	}
  	if (ordersForm.quantity.value == 0) {
  		alert("ί����������Ϊ�㣡");
  		ordersForm.quantity.focus();
  		return false;
  	}
  	if (ordersForm.orderType.value == "2") {
  		if (ordersForm.closeMode.value == "") {
  			alert("ת�÷�ʽ����Ϊ�գ�");
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
		//alert("��");
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
          alert("ָ��ʱ���־����Ϊ�գ�");
          ordersForm.timeFlag.focus();
		  return false;
        }
      }
      else if(ordersForm.closeMode.value == "3")  //specPrice
      {
        if(trim(ordersForm.specPrice.value) == "")
        {
          alert("ָ���۸���Ϊ�գ�");
          ordersForm.specPrice.focus();
		  return false;
        }
      }
    }
   
    //�ȼ�齻��Ա�Ƿ��¼
    ordersForm.submit();   
    }
 
}
//����Ա�Ƿ��¼���ύί��(ͳһ�ص�����)
function order()
{	
	
	ordersForm.action = "<c:url value="/timebargain/order/orders.do?funcflg=order"/>";
	ordersForm.submit();
	ordersForm.order.disabled = true;
	ordersForm.action = "<c:url value="/timebargain/order/orders.do?funcflg=chkLogin"/>";
}

//ί�����ͱ仯�¼�
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
//ת��ģʽ�仯�¼�
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
								<b>��Ϊί��
								</b>
							</legend>
							<table border="0" align="center" cellpadding="2" cellspacing="0"
								class="common">
	    <tr>	     
			<td align="right"><label>�������룺</label></td>
			<td>
				<html:text property="customerID" maxlength="16" styleClass="text" style="width:105;ime-mode:disabled"  />
				<span class="req">*</span>			
			</td> 
		
            <td align="right"><label>��Ʒ���룺</label></td>
			<td>
			    <html:text property="commodityID"  maxlength="16"  style="width:105;ime-mode:disabled"/>                            
				<span class="req">*</span>
			</td>
            <td align="right"><label>������</label></td>
			<td>
				<html:select property="BS_Flag" style="width:105">
				    <html:option value=""></html:option>
					<html:option value="1">���</html:option>
					<html:option value="2">����</html:option>
			    </html:select>
				<span class="req">*</span>
			</td>
            	
            <html:hidden property="marketCode"/>				
		</tr>																
		<tr>
			<td align="right"><label>ί�����ͣ�</label></td>
			<td>
				<html:select property="orderType" onchange="orderType_onchange()" style="width:105">
				    <html:option value=""></html:option>
					<html:option value="1">����</html:option>
					<html:option value="2">ת��</html:option>
			    </html:select>
				<span class="req">*</span>
			</td>
			<td align="right"><label>ί�м۸�</label></td>
			<td>
				<html:text property="price" maxlength="16"  style="ime-mode:disabled;width:105"  onkeypress="return numberPass()" styleClass="Number"/>
				<span class="req">*</span>
			</td>	
			<td align="right"><label>ί��������</label></td>
			<td>
				<html:text property="quantity" maxlength="16" onkeypress="return numberPass()" style="ime-mode:disabled;width:105" styleClass="Number"/>
				<span class="req">*</span>
			</td>					
	     </tr>
	     <tr>
			<td align="right"><label>ת�÷�ʽ��</label></td>
			<td>
				<html:select property="closeMode" onchange="closeMode_onchange()" style="width:105">
				    <html:option value=""></html:option>
				    <html:option value="1">��ָ��ת��</html:option>
					<html:option value="2">ָ��ʱ��ת��</html:option>
					<html:option value="3">ָ���۸�ת��</html:option>
			    </html:select>
				<span id="inCloseMode" class="req" style="visibility:hidden">*</span>			
			</td>	
			<td align="right"><label>ָ��ʱ���־��</label></td>
			<td>
				<html:select property="timeFlag" style="width:105">
				    <html:option value=""></html:option>
					<html:option value="1">ת�ý񶩻�</html:option>
					<html:option value="2">ת����ʷ����</html:option>
			    </html:select>
				<span id="spanTime" class="req" style="visibility:hidden">*</span>
			</td>		
			<td align="right"><label>ָ���۸�</label></td>
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
	      <td>ǿ��ת�ã�<input type="checkbox" name="closeFlag" value="2"  class="NormalInput" onclick="isCheck()"/></td> 
	      
	      <td id="td1" class="yin" >ǿ��ת�ü۸�ʽ��</td>
            <td id="td2" class="yin">
            	<html:select property="forceCloseType" style="width:105" onchange="forceCloseType_onchange()">
					<html:option value=""></html:option>
				    <html:option value="1">ָ���۸�</html:option>
					<html:option value="2">���м�</html:option>
			   </html:select>
				<span class="req">*</span>
            </td>
	    </tr>
	    
	     <tr>
			<td colspan="2" height="10"></td>
		 </tr>
		 
	     <tr>	       							
			<td colspan="4" align="center">
			  <html:button  property="order" styleClass="button" onclick="javascript:return order_onclick();">ȷ��</html:button>
			  <html:reset  property="reset" styleClass="button">����</html:reset>	
			  <c:if test="${not empty sessionID}">
			  	<html:button  property="logoff" styleClass="button" onclick="logoff_onclick()">ע����¼</html:button>
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
