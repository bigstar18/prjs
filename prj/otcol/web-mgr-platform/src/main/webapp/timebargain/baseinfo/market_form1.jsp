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
	if (confirm("��ȷ��Ҫ�ύ��")) {
		
    
   
    
    	
    	if (marketForm.marginFBFlag.value == "") {
    	alert("��֤�����ʱ�䲻��Ϊ�գ�");
    	return false;
    	}
    	
    	if (marketForm.settleMode.value == "") {
    		alert("�����������ɷ�ʽ����Ϊ�գ�");
    		return false;
    	}
    	if (marketForm.runMode.value == "") {
    		alert("��������ģʽ����Ϊ�գ�");
    		return false;
    	}
    	
    	if (marketForm.floatingProfitSubTax.value == "") {
    		alert("����ӯ���Ƿ��˰����Ϊ�գ�");
    		return false;
    	}
    	
    	if (marketForm.gageMode.value == "") {
    		alert("�ֶ�ģʽ����Ϊ�գ�");
    		return false;
    	}
    	
    	if (marketForm.tradeTimeType.value == "") {
    		alert("����ʱ�����Ͳ���Ϊ�գ�");
    		return false;
    	}
    	if (marketForm.asMarginType.value == ""){
    	    alert("��ǰ�����Ƿ���ȡ��֤����Ϊ�գ�");
    	    return false;
    	}
    	if (marketForm.delayQuoShowType.value == "") {
    		alert("���ڽ���������ʾ���Ͳ���Ϊ�գ�");
    		return false;
    	}
    	if (marketForm.neutralFeeWay.value == "") {
    		alert("�����ֽ�����������ȡ��ʽ����Ϊ�գ�");
    		return false;
    	}
    	if (marketForm.delayOrderIsPure.value == "") {
    		alert("�����걨�Ƿ񰴾�����������Ϊ�գ�");
    		return false;
    	}
    	if (marketForm.chargeDelayFeeType.value == "") {
    		alert("���ڲ�������ȡ����Ϊ�գ�");
    		return false;
    	}
    	
    	//alert(marketForm.type[0].checked == true);
    	if (marketForm.type[0].checked == true) {
    		if (marketForm.floatingLossComputeType1.value == "") {
    			alert("ֻ�Ǹ�������Ϊ�գ�");
    			marketForm.floatingLossComputeType1.focus();
    			return false;
    		}
    		marketForm.floatingLossComputeType2.value = "";
    		
    	}
    	if (marketForm.type[1].checked == true) {
    		if (marketForm.floatingLossComputeType2.value == "") {
    			alert("��ӯ������Ϊ�գ�");
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
		var option =new Option('�ý���ۼ��㸡��','0');
		isCPriceCpFloat.options.add(option);
		var option2 =new Option('�����¼ۼ��㸡��','1');
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
		var option =new Option('���а�����㣬�̺󰴽���','0');
		isCPriceCpFloat.options.add(option);
      //alert(isCPriceCpFloat.text);
	}else{
		isCPriceCpFloat.length=0;
		var option =new Option('�ý���ۼ��㸡��','0');
		isCPriceCpFloat.options.add(option);
		var option2 =new Option('�����¼ۼ��㸡��','1');
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
								<b>�г����ײ���
								</b>
							</legend>

<table width="100%" border="0" align="center"  class="common" cellpadding="0" cellspacing="2">
<!-- ������Ϣ -->
      					
        	
<!-- ������Ϣ -->
        <tr class="common">
          <td colspan="4">
            
<span id="paraminfo">
<table cellSpacing="0" cellPadding="0" width="700" border="0" align="center" class="common">    
						<%
							String typeFloat = (String)request.getAttribute("typeFloat");
						%>    
								<tr>
									
										
									
									<td align="right">
										��������ģʽ��
									</td>
									<td >
										<html:select property="runMode" style="width:120">
											<html:option value=""></html:option>
				                           <html:option value="0">�ֶ�</html:option>
					                       <html:option value="1">�Զ�</html:option>
			                            </html:select> <span class="req">*</span>
									</td>	
									<td align="right" >
											�ֶ�ģʽ��
									</td>
									<td>
										<html:select property="gageMode" style="width:120">
											<html:option value=""></html:option>
				                           <html:option value="0">ȫ�ֶ�</html:option>
					                       <html:option value="1">��ֶ�</html:option>
			                            </html:select> <span class="req">*</span>
									</td>						
								</tr>
								<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
								<tr>									
									<td align="right">
											��֤�����ʱ�䣺
									</td>
									<td>
										<html:select property="marginFBFlag" style="width:120">
											<html:option value=""></html:option>
				                           <html:option value="0">����ʱ����</html:option>
					                       <html:option value="1">����ʱ����</html:option>
			                            </html:select> <span class="req">*</span>
									</td>
									 	
										
									<td align="right" >
											����ʱ�����ͣ�
									</td>
									<td>
										<html:select property="tradeTimeType" style="width:120">
											<html:option value=""></html:option>
				                           <html:option value="0">ͬһ�콻��</html:option>
					                       <html:option value="1">���콻��</html:option>
			                            </html:select> <span class="req">*</span>
									</td>
								</tr>
								
								<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
								<tr>									
									 
									<td align="right" >
											�����������ɷ�ʽ��
									</td>
									<td>
										<html:select property="settleMode" style="width:120">
											<html:option value=""></html:option>
				                           <html:option value="0">�Զ�</html:option>
					                       <html:option value="1">�ֶ�</html:option>
			                            </html:select> <span class="req">*</span>
									</td>
										
									<td align="right">
											����ӯ���Ƿ��˰��
									</td>
									<td>
										<html:select property="floatingProfitSubTax" style="width:120">
											<html:option value=""></html:option>
				                           <html:option value="0">����˰</html:option>
					                       <html:option value="1">��˰</html:option>
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
											���ڽ���������ʾ���ͣ�
									</td>
									<td>
										<html:select property="delayQuoShowType" style="width:120">
											<html:option value=""></html:option>
				                           <html:option value="0">���պ���������ʾ</html:option>
					                       <html:option value="1">ʵʱ��ʾ</html:option>
			                            </html:select> <span class="req">*</span>
									</td>	
										
									<td align="right" width="160">
											�����ֽ�����������ȡ��ʽ��
									</td>
									<td>
										<html:select property="neutralFeeWay" style="width:120">
											<html:option value=""></html:option>
				                           <html:option value="0">����</html:option>
					                       <html:option value="1">��ȡ</html:option>
			                            </html:select> <span class="req">*</span>
									</td>
								</tr>
								<tr>
								<td align="right" width="160">
											���ڲ�������ȡ���ͣ�
									</td>
									<td>
										<html:select property="chargeDelayFeeType" style="width:120">
											<html:option value=""></html:option>
				                           <html:option value="0">������������ȡ</html:option>
					                       <html:option value="1">�����߶���������ȡ</html:option>
			                            </html:select> <span class="req">*</span>
									</td>	
									<td align="right" width="160">
											�����걨�Ƿ񰴾���������
									</td>
									<td>
										<html:select property="delayOrderIsPure" style="width:120">
											<html:option value=""></html:option>
				                           <html:option value="0">��</html:option>
					                       <html:option value="1">��</html:option>
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
											��ǰ�����Ƿ���ȡ��֤��
									</td>
									<td>                        
										<html:select property="asMarginType" style="width:120">
											<html:option value=""></html:option>
				                           <html:option value="0">����ȡ</html:option>
					                       <html:option value="1">��ȡ</html:option>
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
								<b>�������㷽ʽ
								</b>
							</legend>

<table width="700" border="0" align="center"  class="common" cellpadding="0" cellspacing="0">
					
				<tr>
				
									<td>
										<input type="radio" name="type" onclick="changeManner(1);" <%if("1".equals(typeFloat)){out.println("checked");} %> style="border:0px;">						
									</td>
					<td align="right" width="140">
											ӯ���Գ�ƿ�����ӯ��
									</td>
									<td width="140">
									
										<html:select property="floatingLossComputeType1" style="width:180">
											 <html:option value=""></html:option> 
				                           <html:option value="0">ͬ��Ʒͬ����ӯ���Գ�</html:option>
					                       <html:option value="1">ͬ��Ʒӯ���Գ�</html:option>
					                       <html:option value="2">������Ʒӯ���Գ�</html:option>
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
											�޸�ծģʽ��
									</td>
									<td width="190">
										<html:select property="floatingLossComputeType2" style="width:120" onchange="changevalue()">
											 <html:option value=""></html:option> 
				                           <html:option value="3">ʵʱ�޸�ծ</html:option>
					                       <html:option value="4">ÿ���޸�ծ</html:option>
			                            </html:select> 
									</td>	
						
									
				</tr>	
				<tr>
				                  <td></td>
				                   <td align="right" width="140">
											����ӯ����ʽ��
									</td>
									<td>
									<input type="hidden" value="${isCPriceCpFloatingLoss }" id="isCPriceCpFloatingLoss"/>
										<select id="isCPriceCpFloatingLosss" name="isCPriceCpFloatingLosss" style="width:180" >
										<% if("1".equals(typeFloat)){ %>
				                          
					                       <script type="text/javascript">
				                       		var isCPriceCpFloat=document.getElementById("isCPriceCpFloatingLosss");
					                       isCPriceCpFloat.length=0;
			                       			var option =new Option('�ý���ۼ��㸡��','0');
			                       			isCPriceCpFloat.options.add(option);
			                       			var option2 =new Option('�����¼ۼ��㸡��','1');
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
					                       			var option =new Option('���а�����㣬�̺󰴽���','0');
					                       			isCPriceCpFloat.options.add(option);
					                       	      //alert(isCPriceCpFloat.text);
					                       		}else{
					                       			isCPriceCpFloat.length=0;
					                       			var option =new Option('�ý���ۼ��㸡��','0');
					                       			isCPriceCpFloat.options.add(option);
					                       			var option2 =new Option('�����¼ۼ��㸡��','1');
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
											�ύ
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
