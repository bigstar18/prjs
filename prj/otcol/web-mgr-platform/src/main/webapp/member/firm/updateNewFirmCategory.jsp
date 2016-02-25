<%@ page contentType="text/html;charset=GBK" %>
<%@page import="gnnt.MEBS.timebargain.manage.service.TariffManager"%>
<%
  pageContext.setAttribute("date", "2019-01-01");
  List list= (List)request.getAttribute("moduleList");
  pageContext.setAttribute("moduleList", list);
  TariffManager mgr=(TariffManager)gnnt.MEBS.timebargain.manage.util.SysData.getBean("tariffManager");
  pageContext.setAttribute("tariffList",mgr.getTariffPage());
%>
<html  xmlns:MEBS>
  <head>
    <%@ include file="../public/headInc.jsp" %>
    <IMPORT namespace="MEBS" implementation="<%=basePathF%>/public/jstools/calendar.htc">
	<title>�޸Ľ��������</title>
	<script language="javascript" src="<%=basePathF%>/public/jstools/common.js"></script>
	<script language="javascript" src="<%=basePathF%>/public/jstools/tools.js"></script>
	<script>
		
		function doSubmit() 
		{ 
			if(!checkValue("formNew"))
				return;
			if(!chkNum(formNew.firmId.value)){
				alert("���������������������");
				return;
			}
			if(formNew.name.value.length >64){
				alert("��������𲻵ö���64λ");
				return;
			}
			var reg =/(^\s+)|(\s+$)/g ;
			if(reg.test(formNew.name.value)){
				alert("������������Ʋ����пո�");
				return;
			}

			
			formNew.submit(); 
		}
		
		
		
	</script> 
	<script language="javascript">
		function textdown(e){
			textevent = e ; 
			if(textevent.keyCode == 8){ 
				return; 
			} 
			if(document.getElementById('note').value.length >= 64){ 
				alert("�����������64����")  
				if(!document.all){ 
					textevent.preventDefault(); 
				}else{ 
					textevent.returnValue = false; 
				} 
			}
		} 
		function textup(){ 
			var s = document.getElementById('note').value; 
			if(s.length > 64){ 
				document.getElementById('note').value=s.substring(0,64); 
			} 
		}
		</script>
</head>
<body>
        <form id="formNew" action="<%=basePath%>/firmController.mem?funcflg=firmCategoryUpdate" method="POST" targetType="hidden" callback="closeDialog(1);">
		<fieldset width="100%">
		<legend>����������޸���Ϣ</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
			<tr height="35">
                <td align="right"> ������������ ��</td>
                <td align="left">
                	<input name="oldId" id="oldId" value="${firmCategory['id']}" type="hidden" />
                	<input class="normal" name="firmId" id="firmId" value="${firmCategory['id']}" reqfv="required;������������"  type="text" style="width: 150px;"  maxlength="9"  "/> &nbsp;<font color="#ff0000">*</font>
                </td>
                </tr>
			  <tr height="35">
                <td align="right"> ������������� ��</td>
                <td align="left">
                	<input class="normal" name="name" id="name" value="${firmCategory['name']}" reqfv="required;�������������"  type="text" style="width: 150px;"  maxlength="9"  "/> &nbsp;<font color="#ff0000">*</font>
                </td>
                </tr>
            	
              <tr height="35">
              	<td align="right"> ������������� ��</td>
                <td align="left">
                	<textarea rows="4" cols="16" style="width: 150px;" onKeyDown="textdown(event)" onKeyUp="textup()" id = "note" name = "note">${firmCategory['note']}</textarea>
                </td>
              </tr>
              <tr height="35">
                <td colspan="4"><div align="center">
                  <button class="smlbtn" type="button" onclick="doSubmit();" readonly>�ύ</button>&nbsp;
      			  <button class="smlbtn" type="button" onclick="window.close()">�رմ���</button>
                </div></td>
              </tr>
          </table>
		</fieldset>
        </form>
</body>
</html>
<%@ include file="../public/footInc.jsp" %>