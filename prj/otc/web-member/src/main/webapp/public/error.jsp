<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ include file="/common/public/choSkin.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:if test="${not empty resultMsg }">
	<script>
        alert('${resultMsg}');
    </script>
</c:if>
<style>
.error_bor01{ 
	width:570px; 
	border:1px solid #c5c0c0; 
	height:230px; 
	margin:0px auto 0px auto;
	}
.error_bor02{ 
	width:568px; 
	border:1px solid #ffffff; 
	height:228px; 
	background-color:#e4e2e2;
	}
</style>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>error</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

  </head>
  <body class="login_body" style="background-color: #fff6f4;">
    <table width="772" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="160"></td>
  </tr>
  <tr>
    <td><div class="error_bor01">
    	<div class="error_bor02">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="40">&nbsp;</td>
  </tr>
  <tr>
    <td align="center"><img src="<%=skinPath%>/cssimg/error.jpg" width="219" height="74" /></td>
  </tr>
  <tr>
    <td height="40">&nbsp;</td>
  </tr>
  <tr>
    <td align="center"><span class="right_01zi">系统错误，请重新操作或联系管理员！</span></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
</table>

        </div>
    </div></td>
  </tr>
  <tr>
    <td align="center" class="login_w14">&nbsp;</td>
  </tr>
</table>
  </body>
</html>
