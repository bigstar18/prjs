<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page  pageEncoding="GBK"%>



<html:html >
  <head>
    <LINK REL="stylesheet" type="text/css"
			href="<c:url value="/timebargain/styles/common.css"/>" />
		<script language="JavaScript"
			src="<c:url value="/timebargain/scripts/global.js"/>"></script>
    
    <title>conferClose_form.jsp</title>
    
<SCRIPT language="javascript">
	function window_onload(){
		highlightFormElements();
		document.forms(0).commodityID.focus();
	}
	
	function save_onclick(){
		if (confirm("您确定要提交吗？")) {
			//alert(document.forms(0).commodityID);
		if (document.forms(0).commodityID.value == "") {
		
			alert("商品代码不能为空！");
			return false;
		}
		if (document.forms(0).price.value == "") {
			alert("交收价不能为空！");
			return false;
		}
		if (document.forms(0).bCustomerID.value == "") {
			alert("买客户ID不能为空！");
			return false;
		}
		
		
		if (document.forms(0).sCustomerID.value == "") {
			alert("卖客户ID不能为空！");
			return false;
		}
		if (document.forms(0).sHoldQty.value == "") {
			alert("卖交收数量不能为空！");
			return false;
		}
		if (document.forms(0).sGageQty.value == "") {
			alert("卖抵顶数量不能为空！");
			return false;
		}
		
		
		if (document.forms(0).sGageQty.value > document.forms(0).sHoldQty.value) {
			alert("其中卖抵顶数量应小于交收数量！");
			return false;
		}
		document.forms(0).submit();
		document.forms(0).save.disabled = true;
		}
		
		
	}
	


var tmp_paraminfo3;
var tmp_paraminfo_up3 = true;
function paraminfo3_onclick()
{
  if (tmp_paraminfo_up3)
  {
    tmp_paraminfo_up3 = false;
    document.forms(0).paraminfo_img3.src = "<c:url value="/images/ctl_detail_Down.gif"/>";
    tmp_paraminfo3 = paraminfo3.innerHTML;
    paraminfo3.innerHTML = "";
  }
  else
  {
    tmp_paraminfo_up3 = true;
    document.forms(0).paraminfo_img3.src = "<c:url value="/images/ctl_detail_Up.gif"/>";
    paraminfo3.innerHTML = tmp_paraminfo3;
  }
}
	
</SCRIPT>
    
  </head>
  
  <body leftmargin="0" topmargin="0" onLoad="return window_onload()"
		onkeypress="keyEnter(event.keyCode);">
		<table cellSpacing="0" cellPadding="0" width="600" border="0" align="center" class="common">
			<tr>
				<td>
					<html:form action="/statquery/statQuery.do?method=saveAheadSettle" method="POST" styleClass="form" target="HiddFrame">
    					
    					<tr class="common">
          <td colspan="4">
            <fieldset>
              <legend>
                <b>提前交收</b>
              </legend>
<span id="paraminfo3">
    					
    					<table cellspacing="0" cellpadding="0" border="0" width="600" class="common" align="center">
    						<tr>
    							<td align="right" width="180">
    								商品代码：
    							</td>
    							<td height="5" width="130">
    								<html:text property="commodityID" maxlength="10" style="ime-mode:disabled" styleClass="text" size="10"/>
    								<span>*</span>
    							</td>
    						
    							<td align="right" width="160">
    								交收价：
    							</td>
    							<td align="left" width="140">
    								<html:text property="price" maxlength="10" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" size="10"/>
    								<span>*</span>
    							</td>
    							<td align="right" width="200">
        			交收数量：
        		</td>
        		<td>
        			<html:text property="sHoldQty" maxlength="10" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" size="10"></html:text><span>*</span>
        			
        		</td>
    						
    						</tr>
    						
    			<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
    						
    						 <tr class="common">
          
			<tr>
				<td align="right">
					买交易客户ID：
				</td>
				<td>
					<html:text property="bCustomerID" maxlength="10" style="ime-mode:disabled"  styleClass="text" size="10"/>
					<span>*</span>
			</td>
				<td align="right">
        			卖交易客户ID：
        		</td>
        		<td>
        			<html:text property="sCustomerID" maxlength="10" style="ime-mode:disabled"  styleClass="text" size="10"></html:text>
        			<span>*</span>
        		</td>
        		<td align="right">
        			卖抵顶数量：
        		</td>
        		<td>
        			<html:text property="sGageQty" maxlength="10" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" size="10"></html:text><span>*</span>
        		</td>
			</tr>



        
        
        
        
        	<tr>
        		
        		
        	</tr>
        
        
     
    							<tr>
									<td colspan="4" height="3">	
								</td>
								</tr>
								                   
								<tr>
									<td colspan="4" align="right" width="320">
										<html:button property="save" styleClass="button"
											onclick="javascript:return save_onclick();">
											<fmt:message key="button.save" />
										</html:button>
										
									</td>
								</tr>
    					</table>
    						</span>
            </fieldset>
          </td>
        </tr> 
      				<html:hidden property="marketCode"/>
    				</html:form>
				</td>
			</tr>
		</table>
    
    
    
		<script type="text/javascript"
			src="<html:rewrite page="/common/validator.jsp"/>"></script>
		<%@ include file="/common/messages.jsp"%>
  </body>
</html:html>
