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
	<title>创建交易商</title>
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
		<legend>交易商基本信息</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
			  <tr height="35">
                <td align="right"> <%=FIRMID%> ：</td>
                <td align="left">
                	<input class="normal" name="firmId" id="firmId" type="text" style="width: 200px;" reqfv="required;用户ID" onkeyup="value=value.replace(/[\W]/g,'') "onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))" >&nbsp;<font color="#ff0000">*</font>
                </td>
            	<td align="right"> <%=FIRMNAME%> ：</td>
                <td align="left">
                	<input name="name" type="text" class="text" style="width: 150px;" reqfv="required;用户名称">&nbsp;<font color="#ff0000">*</font>
                </td>
                </tr>
              <tr height="35">
            	<td align="right"> <%=FULLNAME%> ：</td>
                <td align="left">
                	<input name="fullname" type="text" class="text" style="width: 200px;" >
                </td>
              </tr>
              <tr height="35">
            	<td align="right"> 开户银行 ：</td>
                <td align="left">
                	<input name="bank" type="text" class="text" style="width: 200px;" >
                </td>
            	<td align="right"> 银行帐号 ：</td>
                <td align="left">
                	<input name="bankAccount" type="text" class="text" style="width: 150px;" >
                </td>
              </tr>
              <tr height="35">
                <td align="right"> 类型 ：</td>
                <td align="left">
               	<select id="type" name="type" class="normal" style="width: 150px" reqfv="required;类别">
				<OPTION value=""></OPTION>
				<option value="1">法人</option>
        		<option value="2">代理</option>
           		<option value="3">个人</option>
	    		</select>
	    		&nbsp;<font color="#ff0000">*</font>
                </td>
            	<td align="right"> 联系人 ：</td>
                <td align="left">
                	<input name="contactMan" type="text" class="text" style="width: 150px;">
                </td>
              </tr>
              <tr height="35">
            	<td align="right"> 联系人电话 ：</td>
                <td align="left">
                	<input name="phone" type="text" class="text" style="width: 200px;">
                </td>
            	<td align="right"> 联系人传真 ：</td>
                <td align="left">
                	<input name="fax" type="text" class="text" style="width: 150px;">
                </td>
              </tr>
              <tr height="35">
            	<td align="right"> 邮编 ：</td>
                <td align="left">
                	<input name="postCode" type="text" class="text" style="width: 200px;">
                </td>
            	<td align="right"> EMail ：</td>
                <td align="left">
                	<input name="email" type="text" class="text" style="width: 150px;">
                </td>
                <!-- <tr height="35">
            	<td align="right"> 法人 ：</td>
                <td align="left">
                	<input name="corporate" type="text" class="text" style="width: 200px;">
                </td>
            	<td align="right"> 企业编号 ：</td>
                <td align="left">
                	<input name="code" type="text" class="text" style="width: 150px;">
                </td>
              </tr> -->
                <input type="hidden" name="zoneCode">
                <input type="hidden" name="industryCode">
              <tr height="35">
              <td align="right"> 备注 ：</td>
              <td align="left">
                <textarea name="note" cols="25" rows="6" ></textarea>
              </td>
              <td align="right"> 地址 ：</td>
                <td align="left">
                <textarea name="address" cols="18" rows="6" ></textarea>
                </td>
              </tr>
              <tr height="35">
                <td align="right"> 权限 ：</td>
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
                  <button class="smlbtn" type="button" onclick="doSubmit();">提交</button>&nbsp;
      			  <button class="smlbtn" type="button" onclick="window.close()">关闭窗口</button>
                </div></td>
              </tr>
          </table>
		</fieldset>
        </form>
</body>
</html>
<%@ include file="../public/footInc.jsp" %>