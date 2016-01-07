<%@ page language="java" pageEncoding="GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<script src="${publicPath}/js/jquery-1.6.2.min.js" type="text/javascript"></script>
<script src="${publicPath}/js/submitcount.js" type="text/javascript"></script>
<jsp:include page="/front/public/jsp/commonmsg.jsp" flush="false"></jsp:include>
<%//当页面提交到隐藏域返回时返回本页面，调用返回结果后执行父页面的 showMsgBoxCallbak 方法%>