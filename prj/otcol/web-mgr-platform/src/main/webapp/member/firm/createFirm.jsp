<%@ page contentType="text/html;charset=GBK" %>
<%@ page import='gnnt.MEBS.member.firm.manager.FirmManager' %>
<%@ page import='gnnt.MEBS.member.system.manager.SystemManager' %>
<%
  /*List listZone=FirmManager.getZones(null,null);
  List listIndustry=FirmManager.getIndustrys(null,null);
  pageContext.setAttribute("zone", listZone);
  pageContext.setAttribute("industry", listIndustry);*/
  pageContext.setAttribute("date", "2109-01-01");
  List list=SystemManager.getTradeModuleList(null,null);
  pageContext.setAttribute("moduleList", list);
 %>
<html  xmlns:MEBS>
  <head>
    <%@ include file="../public/headInc.jsp" %>
    <IMPORT namespace="MEBS" implementation="<%=basePathF%>/public/jstools/calendar.htc">
	<title>����������</title>
	<script language="javascript" src="<%=basePathF%>/public/jstools/common.js"></script>
	<script language="javascript" src="<%=basePathF%>/public/jstools/tools.js"></script>
	<script>
		function doSubmit() 
		{ 
			if(!checkValue("formNew"))
				return;
			
			if((formNew.email.value!="")&&!checkEmail(formNew.email))
			{
			  return;
			}
			formNew.submit(); 
		}
	</script> 
</head>
<body>
        <form id="formNew" action="<%=basePath%>/firmCreate.spr" method="POST" targetType="hidden" callback="closeDialog(1);">
		<fieldset width="100%">
		<legend>�����̻�����Ϣ</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
			  <tr height="35">
                <td align="right"> <%=FIRMID%> ��</td>
                <td align="left">
                	<input class="normal" name="firmId" id="firmId" type="text" style="width: 200px;" reqfv="required;�û�ID" onkeyup="value=value.replace(/[\W]/g,'') "onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))" >&nbsp;<font color="#ff0000">*</font>
                </td>
            	<td align="right"> <%=FIRMNAME%> ��</td>
                <td align="left">
                	<input name="name" type="text" class="text" style="width: 150px;" reqfv="required;�û�����">&nbsp;<font color="#ff0000">*</font>
                </td>
                </tr>
              <tr height="35">
            	<td align="right"> <%=FULLNAME%> ��</td>
                <td align="left">
                	<input name="fullname" type="text" class="text" style="width: 200px;" >
                </td>
              </tr>
              <tr height="35">
            	<td align="right"> �������� ��</td>
                <td align="left">
                	<input name="bank" type="text" class="text" style="width: 200px;" >
                </td>
            	<td align="right"> �����ʺ� ��</td>
                <td align="left">
                	<input name="bankAccount" type="text" class="text" style="width: 150px;" >
                </td>
              </tr>
              <tr height="35">
                <td align="right"> ���� ��</td>
                <td align="left">
               	<select id="type" name="type" class="normal" style="width: 150px" reqfv="required;���">
				<OPTION value=""></OPTION>
				<option value="1">����</option>
        		<option value="2">����</option>
           		<option value="3">����</option>
	    		</select>
	    		&nbsp;<font color="#ff0000">*</font>
                </td>
            	<td align="right"> ��ϵ�� ��</td>
                <td align="left">
                	<input name="contactMan" type="text" class="text" style="width: 150px;">
                </td>
              </tr>
              <tr height="35">
            	<td align="right"> ��ϵ�˵绰 ��</td>
                <td align="left">
                	<input name="phone" type="text" class="text" style="width: 200px;">
                </td>
            	<td align="right"> ��ϵ�˴��� ��</td>
                <td align="left">
                	<input name="fax" type="text" class="text" style="width: 150px;">
                </td>
              </tr>
              <tr height="35">
            	<td align="right"> �ʱ� ��</td>
                <td align="left">
                	<input name="postCode" type="text" class="text" style="width: 200px;">
                </td>
            	<td align="right"> EMail ��</td>
                <td align="left">
                	<input name="email" type="text" class="text" style="width: 150px;">
                </td>
                <!-- <tr height="35">
            	<td align="right"> ���� ��</td>
                <td align="left">
                	<input name="corporate" type="text" class="text" style="width: 200px;">
                </td>
            	<td align="right"> ��ҵ��� ��</td>
                <td align="left">
                	<input name="code" type="text" class="text" style="width: 150px;">
                </td>
              </tr> -->
                <input type="hidden" name="zoneCode">
                <input type="hidden" name="industryCode">
              <tr height="35">
              <td align="right"> ��ע ��</td>
              <td align="left">
                <textarea name="note" cols="25" rows="6" ></textarea>
              </td>
              <td align="right"> ��ַ ��</td>
                <td align="left">
                <textarea name="address" cols="18" rows="6" ></textarea>
                </td>
              </tr>
              <tr height="35">
                <td align="right"> Ȩ�� ��</td>
                <td align="left" colspan="3">
                    <c:set var="count" value="0"/>
                    <c:set var="add" value="1"/>
                	<c:forEach items="${moduleList}" var="result">
                	<c:if test="${count%5==0&&count>0}">
                	<br/>
                	</c:if>
        			<input type="checkbox" name="permissions" value="${result.moduleid}">${result.name}&nbsp;&nbsp;
        			<c:set var="count" value="${count+add}"/>
		        	</c:forEach>
		        	
                </td>
              </tr>
              <tr height="35">
                <td colspan="4"><div align="center">
                  <button class="smlbtn" type="button" onclick="doSubmit();">�ύ</button>&nbsp;
      			  <button class="smlbtn" type="button" onclick="window.close()">�رմ���</button>
                </div></td>
              </tr>
          </table>
		</fieldset>
        </form>
</body>
</html>
<%@ include file="../public/footInc.jsp" %>