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
		<script type="text/javascript"> 
function window_onload()
{
    highlightFormElements();
    
    if(document.forms(0).crud.value == "create")
    {  
      document.forms(0).consignerID.focus();
    }
    else if(document.forms(0).crud.value == "update")
    {
      setReadOnly(document.forms(0).consignerID);   
      document.forms(0).name.focus();
    }
	<c:if test="${param['crud'] == 'update'}">      
	    
    </c:if>
}

//save
function save_onclick()
{
	if (confirm("��ȷ��Ҫ�ύ��")) {
		if(!tmp_baseinfo_up)
    {
    //alert("1");
      baseinfo_onclick();
    }
    if(!tmp_paraminfo_up)
    {
    // alert("2");
      paraminfo_onclick();
    }
     //alert(document.forms(0).crud.value);
    if(document.forms(0).crud.value == "create")
    {
    // alert("3");
        if(trim(document.forms(0).password.value).length < 4)
        {
        	alert("���볤��Ӧ����6��");
	        document.forms(0).password.focus();
	        return false;
        }
        if(trim(document.forms(0).password.value) != trim(document.forms(0).confirmPassword.value))
        {
        	alert("������ȷ�����벻����");
	        document.forms(0).confirmPassword.focus();
	        return false;
        }
    }
    else if(document.forms(0).crud.value == "update")
    {
     //alert("4");
    }    
    if (document.forms(0).consignerID.value == "") {
    	alert("��Ϊί��Ա���Ʋ���Ϊ�գ�");
    	document.forms(0).consignerID.focus();
    	return false;
    }
    if (document.forms(0).status.value == "") {
    	alert("״̬����Ϊ�գ�");
    	document.forms(0).status.focus();
    	return false;
    }
    if (document.forms(0).type.value == "") {
    	alert("���Ͳ���Ϊ�գ�");
    	document.forms(0).status.focus();
    	return false;
    }
    document.forms(0).submit();
    document.forms(0).save.disabled = true;
	}
    
  
}
//cancel
function cancel_onclick()
{
   document.location.href = "<c:url value="/timebargain/baseinfo/consigner.do?funcflg=searchConsigner"/>";
  // parent.TopFrame.query_onclick();
}
function modifyPwd()
{
   pTop("<c:url value="/timebargain/common/consigner_modifyPwd1.jsp?type=consigner&prikey="/>" + trim(document.forms(0).consignerID.value),300,190);  
}
//---------------------------start baseinfo-------------------------------
var tmp_baseinfo;
var tmp_baseinfo_up = true;
function baseinfo_onclick()
{
  if (tmp_baseinfo_up)
  {
    tmp_baseinfo_up = false;
    document.forms(0).baseinfo_img.src = "<c:url value="/timebargain/images/ctl_detail_Down.gif"/>";
    if(document.forms(0).crud.value == "create")
    {
    	document.forms(0).tmp_password.value = document.forms(0).password.value;
    	document.forms(0).tmp_confirmPassword.value = document.forms(0).confirmPassword.value;
    }
    tmp_baseinfo = baseinfo.innerHTML;
    baseinfo.innerHTML = "";
  }
  else
  {
    tmp_baseinfo_up = true;
    document.forms(0).baseinfo_img.src = "<c:url value="/timebargain/images/ctl_detail_Up.gif"/>";
    baseinfo.innerHTML = tmp_baseinfo;
    if(document.forms(0).crud.value == "create")
    {
    	document.forms(0).password.value = document.forms(0).tmp_password.value;
    	document.forms(0).confirmPassword.value = document.forms(0).tmp_confirmPassword.value;
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
    document.forms(0).paraminfo_img.src = "<c:url value="/timebargain/images/ctl_detail_Down.gif"/>";
    tmp_paraminfo = paraminfo.innerHTML;
    paraminfo.innerHTML = "";
  }
  else
  {
    tmp_paraminfo_up = true;
    document.forms(0).paraminfo_img.src = "<c:url value="/timebargain/images/ctl_detail_Up.gif"/>";
    paraminfo.innerHTML = tmp_paraminfo;
  }
}
//-----------------------------end paraminfo-----------------------------
//---------------------------start mapinfo-------------------------------
var tmp_mapinfo;
var tmp_mapinfo_up = true;

//-----------------------------end mapinfo-----------------------------
</script>
	</head>

	<body leftmargin="0" topmargin="0" onLoad="return window_onload()"
		onkeypress="keyEnter(event.keyCode);">
		<table border="0" height="400" width="600" align="center">
			<tr>
				<td>
					<html:form action="/timebargain/baseinfo/consigner.do?funcflg=saveConsigner"
						method="POST" styleClass="form" target="ListFrame">
						<fieldset class="pickList" >
							<legend class="common">
								<b>���ô�Ϊί��Ա��Ϣ
								</b>
							</legend>
<table width="100%" border="0" align="center"  class="common" cellpadding="0" cellspacing="2">
<!-- ������Ϣ -->
        <tr class="common">
          <td colspan="4">
            
<span id="baseinfo">
<table cellSpacing="0" cellPadding="0" width="100%" border="0" align="center" class="common">
								<tr>																		
									<td align="right">
											��Ϊί��Ա���룺
									</td>
									<td>
										<html:text property="consignerID" maxlength="10"
											styleClass="text" onkeypress="onlyNumberAndCharInput()"/>
										<span class="req">*</span>
									</td>
									</tr>
									<tr>
									<td align="right">
											��Ϊί��Ա���ƣ�
									</td>
									<td>
										<html:text property="name" maxlength="16" styleClass="text" onkeypress="onlyNumberAndCharInput()"/>
										<span class="req">&nbsp;</span>
									</td>
								</tr>	
							<c:if test="${param['crud'] == 'create'}">      
								<tr>								
									<td align="right">
											���룺
									</td>
									<td>
										<html:password property="password" value="" maxlength="64" size="21" onkeypress="onlyNumberAndCharInput()"/>&nbsp;<span class="req">*</span>
									</td>
									</tr>
									<tr>
									<td align="right">
											ȷ�����룺
									</td>
									<td>
										<html:password property="confirmPassword" value="" maxlength="64" size="21" onkeypress="onlyNumberAndCharInput()"/>&nbsp;<span class="req">*</span>
										
									</td>
								</tr>	
							<input type="hidden" name="tmp_password" value=""><input type="hidden" name="tmp_confirmPassword" value="">
							</c:if>	
								<tr>									
									<td align="right">
											״̬��
									</td>
									<td>
										<html:select property="status" style="width:150">
										   <html:option value="">��ѡ��</html:option>
				                           <html:option value="0">����</html:option>
					                       <html:option value="1">��ֹ��¼</html:option>
			                            </html:select> <span class="req">*</span>
									</td>	
																	
								</tr>			
								
								<tr>									
									<td align="right">
											���ͣ�
									</td>
									<td>
										<html:select property="type" style="width:150">
										   <html:option value="">��ѡ��</html:option>
				                           <html:option value="0">��Ϊί��Ա</html:option>
					                       <html:option value="1">ǿƽԱ</html:option>
			                            </html:select> <span class="req">*</span>
									</td>	
																	
								</tr>																			
								
								
</table >
</span>
           
          </td>
        </tr>					
      				
								<tr>
									<td colspan="4" height="3">	
								</td>
								</tr>																																											
								<tr>
									<td colspan="4" align="center">
										<html:button property="save" styleClass="button"
											onclick="javascript:return save_onclick();">
											�ύ
										</html:button>
										<html:button property="cancel" styleClass="button"
											onclick="javascript:return cancel_onclick();">
											����
										</html:button>
										<c:if test="${param['crud'] == 'update'}"> 
											<html:button property="cancel" styleClass="button"
											onclick="javascript:modifyPwd();">
											�������
											</html:button>								   		
										</c:if>	
									</td>
								</tr>
							</table>
						</fieldset>
						<html:hidden property="crud" />
						<html:hidden property="oldPassword" />
					</html:form>
				</td>
			</tr>
		</table>

		
		<script type="text/javascript"
			src="<html:rewrite page="/timebargain/common/validator.jsp"/>"></script>
		<%@ include file="/timebargain/common/messages.jsp"%>
	</body>
</html>
