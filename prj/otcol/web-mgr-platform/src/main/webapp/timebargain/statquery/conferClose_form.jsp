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
		if (confirm("��ȷ��Ҫ�ύ��")) {
			//alert(document.forms(0).commodityID);
		if (document.forms(0).commodityID.value == "") {
		
			alert("��Ʒ���벻��Ϊ�գ�");
			return false;
		}
		if (document.forms(0).price.value == "") {
			alert("ת�ü۲���Ϊ�գ�");
			return false;
		}
		if (document.forms(0).bCustomerID.value == "") {
			alert("��������벻��Ϊ�գ�");
			return false;
		}
		
		
		if (document.forms(0).sCustomerID.value == "") {
			alert("���������벻��Ϊ�գ�");
			return false;
		}
		if (document.forms(0).sHoldQty.value == "") {
			alert("��ת����������Ϊ�գ�");
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
    document.forms(0).paraminfo_img3.src = "<c:url value="/timebargain/images/ctl_detail_Down.gif"/>";
    tmp_paraminfo3 = paraminfo3.innerHTML;
    paraminfo3.innerHTML = "";
  }
  else
  {
    tmp_paraminfo_up3 = true;
    document.forms(0).paraminfo_img3.src = "<c:url value="/timebargain/images/ctl_detail_Up.gif"/>";
    paraminfo3.innerHTML = tmp_paraminfo3;
  }
}
	
</SCRIPT>
    
  </head>
  
  <body leftmargin="0" topmargin="0" onLoad="return window_onload()"
		onkeypress="keyEnter(event.keyCode);">
		<table cellSpacing="0" cellPadding="0" width="500" border="0" align="center" class="common">
			<tr>
				<td>
					<html:form action="/timebargain/statquery/statQuery.do?funcflg=saveConferClose" method="POST" styleClass="form" target="HiddFrame">
    					<tr class="common">
          <td colspan="4">
            <fieldset>
              <legend>
                <b>Э�齻��</b>
              </legend>
<span id="paraminfo3">
    					
    					<table cellspacing="0" cellpadding="0" border="0" width="500" class="common" align="center">
    						<tr>
    							<td align="right" width="130">
    								��Ʒ���룺
    							</td>
    							<td height="5" width="130">
    								<html:text property="commodityID"  maxlength="16"  style="ime-mode:disabled" styleClass="text" size="10" />
    								<span class="req">*</span>
    							</td>
    							
    						</tr>
    			
    			<tr>
				<td align="right">
					��������룺
				</td>
				<td>
					<html:text property="bCustomerID"   style="ime-mode:disabled"  maxlength="16"  styleClass="text" size="10"/>
					<span class="req">*</span>
			</td>
			<td align="right">
        			���������룺
        		</td>
        		<td>
        			<html:text property="sCustomerID"   style="ime-mode:disabled" maxlength="16"  styleClass="text" size="10"></html:text>
        			<span class="req">*</span>
        		</td>
				
			</tr>
         	<tr>
         		<td align="right">
    								ת�üۣ�
    							</td>
    							<td  width="140">
    								<html:text property="price" maxlength="10" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" size="10"/>
    								<span class="req">*</span>
    							</td>
    			<td align="right" width="120">
        			ת��������
        		</td>
        		<td>
        			<html:text property="sHoldQty" maxlength="10" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" size="10"></html:text>
        			<span class="req">*</span>
        			
        		</td>
         	</tr>
			
     
    							<tr>
									<td colspan="4" height="3">	
								</td>
								</tr>
								                   
								<tr>
								<td>&nbsp;</td>
									<td colspan="4" align="center" width="240">
										<html:button property="save" styleClass="button"
											onclick="javascript:return save_onclick();">
											�ύ
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
			src="<html:rewrite page="/timebargain/common/validator.jsp"/>"></script>
		<%@ include file="/timebargain/common/messages.jsp"%>
  </body>
</html:html>
