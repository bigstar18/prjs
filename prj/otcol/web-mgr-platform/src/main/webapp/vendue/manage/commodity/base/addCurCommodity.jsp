<%@ page contentType="text/html;charset=GBK" %>
<html xmlns:MEBS>
<%@ include file="../../globalDef.jsp"%>
<base target="_self">
<head>
<title>添加到当前交易商品</title>
</head>
<body>
	<form name=frm id=frm action="addCurCommodityImp.jsp" method="post">
    <table border="0" cellspacing="0" cellpadding="0" width="100%">
       <tr height="35">
          <td width="40%">
          <div align="center">
            <input type="hidden" name="add">
            <input type="hidden" name="ids" value="${param.ids}">
			<input type="hidden" onclick="return frmChk();" class="btn" value="保存">&nbsp;&nbsp;
			</div>
            </td>
          </tr>
    </table>
</form>
<script language="javascript">
	//------------------------xieying 
	frm.add.value="确定";
	frm.submit();
	//window.close();
	//------------------------
  function frmChk(){
    if(userConfirm()){
  	    frm.add.value="确定";
  	    return true;
  	}else{
  	    return false;
  	}
  }
</script>
</body>
</html>