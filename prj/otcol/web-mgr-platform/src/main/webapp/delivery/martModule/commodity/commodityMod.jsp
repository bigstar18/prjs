<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../public/session.jsp"%>

<html>
  <head>
	<title><%=TITLE%></title>
</head>
<body>
        <form id="formNew" name="frm" method="POST" action="<%=basePath%>servlet/commodityController.${POSTFIX}?funcflg=commodityMod" >
		<fieldset width="100%">
		<legend>品种信息</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
			
			  <tr height="30">
                <td align="right" width="40%">品种名称：</td>
                <td align="left" width="60%">
                	<select id="id" name="id">
                			<option value="${result.id }">
                				${result.name}
							</option>
                	</select>
                </td>
			  </tr>
			  <!-- 
			   <tr height="30">
                <td align="right" width="40%">最小交收量:</td>
                <td align="left" width="60%">
                	<input class="text" name="minJS" value="" style="width: 150px;" reqfv="required;最小交收量">&nbsp;<font color="red">*</font>
                </td>
			  </tr>
			   <tr height="30">
                <td align="right" width="40%">溢短:</td>
                <td align="left" width="60%">
                	<input class="text" name="yshort" value="" style="width: 150px;" reqfv="required;溢短">&nbsp;<font color="red">*</font>
                </td>
			  </tr>
			   <tr height="30">
                <td align="right" width="40%">溢长:</td>
                <td align="left" width="60%">
                	<input class="text" name="ylong" value="" style="width: 150px;" reqfv="required;溢长">&nbsp;<font color="red">*</font>
                </td>
			  </tr> 
			   -->
			  <tr height="30">
				<td align="right">计量单位：</td>
                <td align="left">
                	<input name="countType" type="text" value="${result.countType }" class="text" style="width: 100px;" reqfv="required;计量单位" maxlength="8">&nbsp;<font color="red">*</font>&nbsp;（eg.吨）
                </td>
			  </tr>
			  </table>
			 <table border="0" cellspacing="0" cellpadding="0" width="100%">
              <tr height="30">
                <td colspan="2"><div align="center">
				  <button class="smlbtn" type="button" onclick="doSubmit();">确定</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      			  <button class="smlbtn" type="button" onclick="freturn()">返回</button>
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
		var str = /^[1-9]*[1-9][0-9]*$/;
		var id = frm.id.value;
		if(frm.id.value==""){
			alert('请选择品种');
			return false；
		}
		if(frm.countType.value==""){
			alert('请填写计量单位');
			frm.countType.focus;
			return false;
		} 
		frm.submit();
	}
	function freturn(){
		frm.action = "<%=basePath%>servlet/commodityController.${POSTFIX}?funcflg=commodityReturn";
		frm.submit();
	}
</script> 
