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
			alert("���ռ۲���Ϊ�գ�");
			return false;
		}
		if (document.forms(0).bCustomerID.value == "") {
			alert("��ͻ�ID����Ϊ�գ�");
			return false;
		}
		
		
		if (document.forms(0).sCustomerID.value == "") {
			alert("���ͻ�ID����Ϊ�գ�");
			return false;
		}
		if (document.forms(0).sHoldQty.value == "") {
			alert("��������������Ϊ�գ�");
			return false;
		}
		if (document.forms(0).sGageQty.value == "") {
			alert("���ֶ���������Ϊ�գ�");
			return false;
		}
		
		
		if (document.forms(0).sGageQty.value > document.forms(0).sHoldQty.value) {
			alert("�������ֶ�����ӦС�ڽ���������");
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
                <b>��ǰ����</b>
              </legend>
<span id="paraminfo3">
    					
    					<table cellspacing="0" cellpadding="0" border="0" width="600" class="common" align="center">
    						<tr>
    							<td align="right" width="180">
    								��Ʒ���룺
    							</td>
    							<td height="5" width="130">
    								<html:text property="commodityID" maxlength="10" style="ime-mode:disabled" styleClass="text" size="10"/>
    								<span>*</span>
    							</td>
    						
    							<td align="right" width="160">
    								���ռۣ�
    							</td>
    							<td align="left" width="140">
    								<html:text property="price" maxlength="10" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" size="10"/>
    								<span>*</span>
    							</td>
    							<td align="right" width="200">
        			����������
        		</td>
        		<td>
        			<html:text property="sHoldQty" maxlength="10" style="ime-mode:disabled" onkeypress="return onlyNumberInput()" styleClass="text" size="10"></html:text><span>*</span>
        			
        		</td>
    						
    						</tr>
    						
    			<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
    						
    						 <tr class="common">
          
			<tr>
				<td align="right">
					���׿ͻ�ID��
				</td>
				<td>
					<html:text property="bCustomerID" maxlength="10" style="ime-mode:disabled"  styleClass="text" size="10"/>
					<span>*</span>
			</td>
				<td align="right">
        			�����׿ͻ�ID��
        		</td>
        		<td>
        			<html:text property="sCustomerID" maxlength="10" style="ime-mode:disabled"  styleClass="text" size="10"></html:text>
        			<span>*</span>
        		</td>
        		<td align="right">
        			���ֶ�������
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
