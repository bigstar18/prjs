<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>微信开户</title>
	<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, user-scalable=yes">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
 
    <link rel="stylesheet" type="text/css" href="<%=basePath%>weixincss/css.css">
    <script src="<%=basePath%>weixinjs/jquery-1.7.2.min.js"></script><style type="text/css"></style>
    <script src="<%=basePath%>weixinjs/errmsg.js"></script>
    <script src="<%=basePath%>weixinjs/jsTools.js"></script>
    <script src="<%=basePath%>weixinjs/jquery.cookie.js"></script>
    </head>
  <body onload="marketInfo()">
    <form name="frm_openAccount" id="frm_openAccount" method="post" action="<%=basePath%>/weixinAddZs.fir?funcflg=eidtFirm">
        <div class="frame">
            <div>
                <!--<input name="" type="button" class="back_button " />-->
            </div>
            <div class="title">开户成功</div>
            <div class="con">
                <div class="back_frame">
                    <table cellpadding="0" cellspacing="0" border="0" class="list_tab">
                        <tbody><tr>
                            <td colspan="2">
                                <div class="top_left"></div>
                                <div class="top_right"></div>
                            </td>
                        </tr>
                        <tr>
                            <td width="34%" class="td_1"><span class="span_1">*</span>账户：</td>
                            <td width="65%" class="td_2">${customer.customerNo }</td>
                        </tr>
                        <tr>
                            <td width="34%" class="td_1"><span class="span_1">*</span>姓名：</td>
                            <td width="65%" class="td_2">${customer.name }</td>
                        </tr>
                        <tr>
                            <td class="td_1"><span class="span_1">*</span>移动电话：</td>
                            <td class="td_2">${customer.phone }</td>
                        </tr>
                        <tr>
                            <td class="td_1"><span class="span_1">*</span>证件类型：</td>
                            <td class="td_2" id="td_cards">
                            <c:if test="${customer.papersType==2 }">身份证</c:if>
                            
                            </td>
                        </tr>
                        <tr>
                            <td class="td_1"><span class="span_1">*</span>证件号码：</td>
                            <td class="td_2">${customer.papersName }</td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <div class="bottom_left"></div>
                                <div class="bottom_right"></div>
                            </td>
                        </tr>
                    </tbody></table>
                </div>
            </div>
            <div class="line">&nbsp;</div>
            <div class="con">
                <span class="span_1">
					账户已开通，需要交易你还需完成以下步骤：<br/>
					1.请登录交易软件绑定银行（<a href="#">查看说明</a>）<br/>
					2.通过微信发送如下信息<br/>
					&nbsp;（1）身份原件证正、反面（<a href="#">查看</a>）；<br/>
					&nbsp;（2）客户手拿身份证拍照（<a href="#">查看</a>）；<br/>
					&nbsp;（3）会员单位激活申请表（<a href="#">查看</a>）；<br/>
					详细咨询客户电话：400-1188-860
				</span>
            </div>
            <div class="name">
                <p><span id="market_name">长三角商品交易所</span></p>
                <p><span id="market_phone"></span></p>
            </div>
        </div>
        </form>
	</body>
</html>