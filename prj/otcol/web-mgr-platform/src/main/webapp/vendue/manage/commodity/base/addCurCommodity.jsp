<%@ page contentType="text/html;charset=GBK" %>
<html xmlns:MEBS>
<%@ include file="../../globalDef.jsp"%>
<base target="_self">
<head>
<title>��ӵ���ǰ������Ʒ</title>
</head>
<body>
	<form name=frm id=frm action="addCurCommodityImp.jsp" method="post">
    <table border="0" cellspacing="0" cellpadding="0" width="100%">
       <tr height="35">
          <td width="40%">
          <div align="center">
            <input type="hidden" name="add">
            <input type="hidden" name="ids" value="${param.ids}">
			<input type="hidden" onclick="return frmChk();" class="btn" value="����">&nbsp;&nbsp;
			</div>
            </td>
          </tr>
    </table>
</form>
<script language="javascript">
	//------------------------xieying 
	frm.add.value="ȷ��";
	frm.submit();
	//window.close();
	//------------------------
  function frmChk(){
    if(userConfirm()){
  	    frm.add.value="ȷ��";
  	    return true;
  	}else{
  	    return false;
  	}
  }
</script>
</body>
</html>