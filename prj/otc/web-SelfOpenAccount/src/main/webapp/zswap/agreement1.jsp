<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
    <script type="text/javascript">
    function validates() {
		if (!validateChecked("ckb_Agree", "需要同意《投资者确认函》方可开户！")) {
	        return false;
	    } 
		$.removeCookie("COOKIE_CARDTYPE");
     	$.removeCookie("COOKIE_BANKNAME");
     	$("#frm_openAccount").submit();
    }
    //验证控件是否被选中
    function validateChecked(formId,tips) {
        if ($("#" + formId).is(":checked")) {
            return true;
        }
        EV_modeAlert(formId, 'errorMSG', tips);
        return false;
    }
    </script> 
  </head>
  <body  >
    <form name="frm_openAccount" id="frm_openAccount" method="post" action="<%=basePath%>/weixinAddZs.fir?funcflg=hrefagreement2">
        <div class="frame">
            <div>
                <!--<input name="" type="button" class="back_button " />-->
            </div>
            <div class="title">风险揭示书</div>
            <div class="con">
                <div class="back_frame">
                
                	<table width="100%">
                		<tr>
                			<td>甲方：长三角商品交易所有限公司   </td>
                			<td>乙方：${auditInfo.name }</td>
                		</tr>
                	</table>
        	                <textarea rows="30%" id="agreement" cols="95%" style="overflow: auto;width: 100%;" readonly="readonly" disabled="disabled">
	       	                	${fn:replace(agreementMap.AGREEMENT1, "${memberInfo.Name}", memberInfo.Name)}   
							</textarea>
                 </div>
                <div class="system">
                    <input name="" type="checkbox" id="ckb_Agree">我已认真阅读以上内容并完全理解，同意遵守交易所各项制度。
                </div>
            </div>
            <div class="line">&nbsp;</div>
            <div class="con">
                <div class="button_l"></div>
                <div class="button_r"></div>
                <div class="button_m"  onclick="javascript:history.go(-1);">上一步</div>
                <div class="button_l"></div>
                <div class="button_r"></div>
                <div class="button_m"  onclick="validates();">同意并继续</div>
            </div>
            <div class="name">
                <p><span id="market_name">长三角商品交易所</span></p>
                <p><span id="market_phone"></span></p>
            </div>
        </div>

        <div id="errorMSG" class="wrong_frame" style="display: none;">
            <div class="wrong_title "><div class="fl pl5">信息</div></div>
            <p id="errMsg"></p>
            <div class="tc"><input type="button" name="" value="确定" class=" blue_button"></div>
        </div>
    </form>
	</body>
</html>