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
        <div class="frame">
            <div>
                <!--<input name="" type="button" class="back_button " />-->
            </div>
            <div class="title">填写信息</div>
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
                            <td width="65%" class="td_2">${customer.customerNo}</td>
                        </tr>
                        <tr>
                            <td width="34%" class="td_1"><span class="span_1">*</span>密码：</td>
                            <td width="65%" class="td_2">${customer.password}</td>
                        </tr>
                        <tr>
                            <td width="34%" class="td_1"><span class="span_1">*</span>姓名：</td>
                            <td width="65%" class="td_2">${customer.name}</td>
                        </tr>
                        <tr>
                            <td class="td_1"><span class="span_1">*</span>移动电话：</td>
                            <td class="td_2">${customer.phone }</td>
                        </tr>
                        <tr>
                            <td class="td_1"><span class="span_1">*</span>证件类型：</td>
                            <td class="td_2" id="td_cards">
	                            <c:if test="${customer.papersType=='1' }">机构代码</c:if>
	                            <c:if test="${customer.papersType=='2' }">身份证</c:if>
	                            <c:if test="${customer.papersType=='3' }">护照</c:if>
	                            <c:if test="${customer.papersType=='4' }">营业执照</c:if>
							</td>
                        </tr>
                        <tr>
                            <td class="td_1"><span class="span_1">*</span>证件号码：</td>
                            <td class="td_2">${customer.papersName }</td>
                        </tr> 
                         <tr>
                            <td colspan="2">
                                <span class="span_1">温馨提示：</br>
							             &nbsp;&nbsp;&nbsp;请记住您的交易账号和初始密码。并且立即登陆交易端软件进行修改。交易端软件请向综合会员索取。
							             </br>
							             &nbsp;&nbsp;&nbsp;开立完实盘账号后请联系您的开户专员，在开户专员的帮助下进行银行资金三方托管的签约，并且提供身份证正反面照片用于激活您的实盘账户！
								</span>
                            </td>
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
                <div class="button_l"></div>
                <div class="button_r"></div>
                <div class="button_m" id="div_sumbit" onclick="WeixinJSBridge.call('closeWindow');">点击此处返回微信</div>
            </div>
            <div class="name">
                <p><span id="market_name">长三角商品交易所</span></p>
                <p><span id="market_phone"></span></p>
            </div>
        </div> 
	</body>
</html>