<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../public/session.jsp"%>

<html>
  <head>
	<title><%=TITLE%></title>
</head>
<body>
        <form id="frm" method="POST" targetType="hidden" action="<%=basePath%>servlet/warehouseController.${POSTFIX}?funcflg=warehouseMod">
		<fieldset width="100%">
		<legend>仓库信息</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
			  <tr height="35">
                <td align="right" width="40%">编号：</td>
                <td align="left">
                	<input class="readonly" id="id" name="id" value="${warehouse.id}" style="width: 150px;" reqfv="required;用户ID" readonly>
                </td>
              </tr>
              <tr height="35" width="60%">
            	<td align="right">简称：</td>
                <td align="left">
                	<input name="name" type="text" value="${warehouse.name}" class="text" style="width: 150px;" reqfv="required;名称" maxlength="10">&nbsp;<font color="red">*</font>
                </td>
              </tr>
			  <tr height="35">
            	<td align="right">全称：</td>
                <td align="left">
                	<input name="fullName" type="text" value="${warehouse.fullName}" class="text" style="width: 150px;" reqfv="required;名称" maxlength="20">&nbsp;<font color="red">*</font>
                </td>
              </tr>
			  <tr height="35">
            	<td align="right">地址：</td>
                <td align="left">
                	<input name="address" type="text" value="${warehouse.address}" class="text" style="width: 150px;">
                </td>
              </tr>
			  <tr height="35">
            	<td align="right">负责人：</td>
                <td align="left">
                	<input name="linkman" type="text" value="${warehouse.linkman}" class="text" style="width: 150px;" maxlength="20" reqfv="required;负责人" >&nbsp;<font color="red">*</font>
                </td>
              </tr>
			  <tr height="35">
            	<td align="right">电话：</td>
                <td align="left">
                	<input name="tel" type="text" value="${warehouse.tel}" class="text" style="width: 150px;" maxlength="32">
                </td>
              </tr>
			  <tr height="35">
            	<td align="right">传真：</td> 
                <td align="left">
                	<input name="fax" type="text" value="${warehouse.fax}" class="text" style="width: 150px;" maxlength="32">
                </td>
              </tr>
			  <tr height="35">
            	<td align="right">电子邮件：</td>
                <td align="left">
                	<input name="email" type="text" value="${warehouse.email}" class="text" style="width: 150px;" maxlength="32">
                </td>
              </tr>
			  <tr height="35">
            	<td align="right">邮编：</td>
                <td align="left">
                	<input name="postcode" type="text" value="${warehouse.postcode}" class="text" style="width: 150px;" maxlength="6" onkeyup="this.value=this.value.replace(/\D/g,'')" >
                </td>
                <!-- 
			    <tr height="35">
            	<td align="right">总库容：</td>
                <td align="left">
                	<input name="max_Capacity" type="text" value="<fmt:formatNumber value="${warehouse.max_Capacity}" pattern="##0"/>" class="text" style="width: 150px;" reqfv="REQ_RANGE;1;0;0;;最大库容">&nbsp;<font color="red">*</font>
                </td>
              </tr>
			    <tr height="35">
            	<td align="right">已用库容</td>
                <td align="left">
                	<input name="used_Capacity" type="text" value="<fmt:formatNumber value="${warehouse.used_Capacity}" pattern="##0"/>" class="text" style="width: 150px;" reqfv="REQ_RANGE;1;0;0;;已用库容">&nbsp;<font color="red">*</font>
                </td>
              </tr>
			  <tr height="35">
            	<td align="right">入库保证金：</td>
                <td align="left">
                	<input name="bail" type="text" value="<fmt:formatNumber value="${warehouse.bail}" pattern="##0.00"/>" class="text" style="width: 150px;" reqfv="req_num;1;0;入库保证金">&nbsp;<font color="red">*</font>
                </td>
              </tr>
               -->
              <tr height="35">
                <td colspan="2"><div align="center">
                  <button class="smlbtn" type="button" onClick="doSubmit();">确定</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      			  <button class="smlbtn" type="button" onClick="freturn()">返回</button>
                </div></td>
              </tr>
          </table>
		</fieldset>
        </form>
</body>
</html>
<script>
	function doSubmit()
	{
		if(!checkValue("frm"))
			return;
		//var maxCap = parseFloat(frm.max_Capacity.value);
		//var useCap = parseFloat(frm.used_Capacity.value);
		//if(maxCap<useCap){
		//	alert("已用库容不能大于总库容！");
			//frm.used_Capacity.select();
			//frm.used_Capacity.focus();
			//return;
		//}
		if(confirm("确定执行此操作？")){
			frm.submit();
		}
	}
	function freturn(){
		frm.action = "<%=basePath%>servlet/warehouseController.${POSTFIX}?funcflg=warehouseReturn";
		frm.submit();
	}
</script> 