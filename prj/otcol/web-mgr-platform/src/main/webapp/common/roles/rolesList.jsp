<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../public/common.jsp"%>
<script language="javascript" src="<%=serverPath %>/public/jslib/pageAndOrder.js"></script>
<style>
	checkbox_1
	{
		border-width: 0px;	
		border-left-style: none;
		border-right-style: none;
		border-top-style: none;
		border-bottom-style: none;
		font-family: SimSun;
		font-size: 9pt;
		color: #333333;
		background-color: transparent;
	}
</style>
<html>
<body>
<form name="frm" method="post" action="<%=commonRoleControllerPath %>commonRoleList" callback="closeDialog(1);" targetType="hidden">
    	<input type="hidden" name="orderField" value="${orderFiled}">
		<input type="hidden" name="orderDesc" value="${orderType}">
		<input type="hidden" name="pageSize" value="<c:out value="${pageInfo.pageSize}"/>">
		<input type="hidden" id="pageNo" name="pageNo">
		<fieldset width="100%">
		<legend>ϵͳ��ɫ��Ϣ��ѯ</legend>
		<span>
			<table border="0" cellspacing="0" cellpadding="0" width="40%">
			  <tr height="35">
            	<td align="right"> ��ɫ���� ��</td>
                <td align="left">
                	<input name="_id[like]" id="roleId" type="text" class="text" style="width: 100px;" value="${oldParams['id[like]'] }" 
onkeypress="return onlyNumberAndCharInput()" maxlength="16">
                </td>
				<td align="left">
                	<input type="button" onclick="queryInfo();" class="btn" value="��ѯ">&nbsp;&nbsp;
                	<!-- add by yangpei 2011-11-22 �������ù��� -->
                	<input type="button" onclick="resetForm();" class="btn" value="����">
                	<script type="text/javascript">
                		function resetForm(){
                			frm.roleId.value="";
                		}
                	</script>
                </td>
              </tr>
        	</table>
        </span>  
		</fieldset>
		<br>
		<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%" height="300">
	  		<tHead>
  			<tr height="25"  align=center>
  				<td class="panel_tHead_LB">&nbsp;</td>
				<td class="panel_tHead_MB" align=left><input class=checkbox_1 type="checkbox" id="checkAll" onclick="selectAll(tb,'ck')"></td>
			    <td class="panel_tHead_MB" align=left>��ɫ���� </td>
				<td class="panel_tHead_MB" align=left>��ɫ���� </td>
			    <td class="panel_tHead_MB" align=left>��ɫ����</td>
			    <td class="panel_tHead_MB" align=left>Ȩ������</td>
			    <td class="panel_tHead_MB" align=left>�û�����</td>
				<td class="panel_tHead_RB">&nbsp;</td>
			</tr>
			</tHead>
			<tBody>
			<c:forEach items="${rolesList }" var="role">
				<tr onclick="selectTr();" align=center height="25">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td class="underLine" align=left>
					<input name="ck" type="checkbox" value="${role.id }">
				</td>
				<td class="underLine" align=left>
				<a href="javascript:editRoleInfo('${role.id }');" class="normal">${role.id }</a>
				</td>
				<td class="underLine" align=left>&nbsp;${role.name }</td>
				<td class="underLine" align=left>&nbsp;${role.description }</td>
				<td class="underLine" align=left>
				<a href="javascript:addRight('${role.id }');" class="nomal">����</a>  
				</td>
				<td class="underLine" align=left>
				<a href="javascript:mngUser('${role.id }');" class="nomal">����</a>
				</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			</c:forEach>
		  	</tBody>
			<tFoot>
				<tr height="100%">
					<td class="panel_tBody_LB">&nbsp;</td>
					<td colspan="6">&nbsp;</td>
					<td class="panel_tBody_RB">&nbsp;</td>
				</tr>
				<tr height="22">
					<td class="panel_tBody_LB">&nbsp;</td>
					<td colspan="6" align="right" class="pagerext">
						<%@ include file="../public/pagerInc.jsp" %>
					</td>
					<td class="panel_tBody_RB">&nbsp;</td>
				</tr>
				<tr height="22">
					<td class="panel_tFoot_LB">&nbsp;</td>
					<td class="panel_tFoot_MB" colspan="6"></td>
					<td class="panel_tFoot_RB">&nbsp;</td>
				</tr>
			</tFoot>			
		</table>
     <table border="0" cellspacing="0" cellpadding="0" width="100%">
          <tr height="35">
          	<td width="40%"><div align="right">
			    <input type="button" name="btn" onclick="javascript:addRole();" class="btn" value="���">&nbsp;
  			    <input type="button" onclick="deleteRole();" class="btn" value="ɾ��">&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          </div>
          <input type="hidden" name="opt">
          </td>
          </tr>
     </table>	 
</form>
</body>
</html>
<SCRIPT LANGUAGE="JavaScript">

	//����½�ɫ
	function addRole()
	{
		var result=window.showModalDialog("<%=commonRoleControllerPath %>commonRoleAdd&d="+Date(),"", "dialogWidth=600px; dialogHeight=350px; status=no;scroll=yes;help=no;"); 
		if(result)
		{
			queryInfo();
	  	}
	}
	
	//�޸Ľ�ɫ��Ϣ
	function editRoleInfo(roleId)
	{
	  	var result=window.showModalDialog("<%=commonRoleControllerPath %>commonRoleView&roleId="+roleId+"&d="+Date(),"", "dialogWidth=400px; dialogHeight=350px; status=no;scroll=yes;help=no;"); 
		if(result)
		{
			queryInfo();
	  	}
	}
	
	function deleteRole()
	{
	  frm.action="<%=commonRoleControllerPath %>commonRoleDelete";
	  deleteRec(frm,tb,'ck');
	}
	
	function queryInfo()
	{
	  frm.action="<%=commonRoleControllerPath %>commonRoleList";
	  frm.submit();	
	}
	
	//����û�Ȩ��
	function addRight(roleId)
	{
		var result = window.showModalDialog("<%=commonRightControllerPath %>commonRightRoleList&roleId="+roleId+"&d="+Date(),"", "dialogWidth=400px; dialogHeight=600px; status=yes;scroll=yes;help=no;"); 
	  	if(result)
		{		
			queryInfo();
		}
		//frm.action="<%//=serverPath %>/commonRightUserList.cmn?userId="+userId;
	  //frm.submit();
	}
	
	function mngUser(roleId)
	{
		window.location="<%=commonRoleControllerPath %>commonRoleUserAssociate&roleId="+roleId;
	}
	//���������ֺ���ĸ
function onlyNumberAndCharInput()
{
  if ((event.keyCode>=48 && event.keyCode<=57) || (event.keyCode>=65 && event.keyCode<=90) || (event.keyCode>=97 && event.keyCode<=122))
  {
    event.returnValue=true;
  }
  else
  {
    event.returnValue=false;
  }
}
</SCRIPT>