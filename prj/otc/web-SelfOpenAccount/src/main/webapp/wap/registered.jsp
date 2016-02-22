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
 <script type="text/javascript">
    
        function validates() {
        	
            if (validateNull("input_name", "请输入姓名！")) {
                return false;
            }
            if (!validateName("input_name", "姓名必须为2-6个汉字！")) {
                return false;
            }
            if (!checkMobilePhoneForm("input_tel")) {
                return false;
            }
            if (validateNull("select_cardType", "选择证件类型！")) {
                return false;
            }
            if (validateNull("input_cardNum", "请输入证件号码！")) {
                return false;
            }

            if (validateNull("input_members", "选择会员单位！")) {
                return false;
            }
        	if (!validateChecked("ckb_Agree", "需要同意交易所制度方可开户！")) {
                return false;
            }  
        	
            if (!validateCardNum("input_cardNum", "select_cardType")) {
                return false;
            } 
            return true;
        }
         
	              
          // 验证证件号码是否重复
          function vadateCard(){
			var cardNum = $("#input_cardNum").val();
	 		var bReturn=true;
			jQuery.ajax({
				url:"validateAction.action",
				async:false,
				type:"post",
				data:{content:cardNum,type:'A'},
				cache:false,
				datatype:"html",
				success:function(data){
					if(data/1>0)
					{
						EV_modeAlert("input_cardNum",'errorMSG', "该证件号码已被注册！");
						bReturn= false;
					}
				},
				error:function(XMLHttpResuest,textStatus,errorThrown){}
			});
			return bReturn;
		}
		 
		
        //验证姓名是否符合2-6个汉字
        function validateName(formId,tips) {
            var formValue = $("#" + formId).val();
            if (formValue.length < 2 || formValue.length > 6) {
                EV_modeAlert(formId, 'errorMSG', tips);
                return false;
            }
            var re = /[^\u4e00-\u9fa5]/;
            if (re.test(formValue)) {
                EV_modeAlert(formId, 'errorMSG', tips);
           	    return false;
        	}
        return true;
        } 
        //验证证件号码是否符合规则
        function validateCardNum(formId,selectId) {
            var formValue = $("#" + formId).val();
            var selectValue = $("#" + selectId).find("option:selected").val();
            if (selectValue == "2") {
                var checkFlag = new clsIDCard(formValue);
                if (!checkFlag.IsValid()) {
                    EV_modeAlert(formId, 'errorMSG', "身份证号输入有误，请检查！");
                    return false;
                }
                return true;
            } else {
                var reg = /^[0-9a-zA-Z]+$/;
                if (!reg.test(formValue)) {
                    EV_modeAlert(formId, 'errorMSG', "证件号输入有误，请检查！");
                    return false;
                }
                return true;
            }
        }
        //验证控件是否被选中
        function validateChecked(formId,tips) {
            if ($("#" + formId).is(":checked")) {
                return true;
            }
            EV_modeAlert(formId, 'errorMSG', tips);
            return false;
        }
        $(document).ready(function () {
           <%--
            //去除微信功能按钮
            document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
                WeixinJSBridge.call('hideToolbar');
            });
            document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
                WeixinJSBridge.call('hideOptionMenu');
            });
            var openId = $.getUrlParam('openId');
            
            $("#td_cards").load("dictAction.action?type=cards");
            $("#td_zjbank").load("dictAction.action?type=bank");
            
            $("#open_id").val(openId);
            $("#to_user_name").val($.getUrlParam('toUserName'));
            $("#void_firm_id").val($.getUrlParam('voidFirmId'));
           
            --%>
            
            
            $("#select_cardType").live("change",function(){
            	$.cookie("COOKIE_CARDTYPE",$("#select_cardType").find("option:selected").val());
            });
            $("#select_bankName").live("change",function(){
            	$.cookie("COOKIE_BANKNAME",$("#select_bankName").find("option:selected").val());
            });
            
            $("#div_sumbit").bind("click", function () {
            	if(!validates()){
            		return false;
    			}
        		var openDiv = "<div id='openDiv' style='position:absolute;z-index:200;margin:50px 0px 0px 50px;background-color:#FFFFFF; border:1px solid #000;'>"+
        		"<div id='openImg' class='list_tab'>姓名："+$("#input_name").val()+"</br>电话："+$("#input_tel").val()+"</br>证件类型："+$("#select_cardType").find("option:selected").text()+"</br>证件号码："+$("#input_cardNum").val()+
        		"</br>会员单位:"+$("#select_members").find("option:selected").text()+"</br>会员编号："+$("#input_members").val()+"</br><div class='button_m' style='width:30%;float:left;' id='div_div_sumbit'>确认</div> <div class='button_m'  style='width:30%;float:left;margin-left:10px;' id='div_div_close'>取消</div></div></div>"
        		$("body").append(openDiv);
        		$("body").attr("style","overflow-x:hidden;overflow-y:hidden");
        		$("#openDiv").css(
        			{
        				"top":document.body.scrollTop+"px",
        				"left":10+"px"
        			}
        		).show("fast");
        		$("#openDiv").click(function(){
        			return false;
        		});
        		$("#div_div_close").click(function(event){
        			$("#openDiv").remove();
        			$("body").attr("style","");
        			event.stopPropagation();

        		});
        		$("#div_div_sumbit").click(function(event){
     	            	$.removeCookie("COOKIE_CARDTYPE");
     	            	$.removeCookie("COOKIE_BANKNAME");
     	            	$("#frm_openAccount").submit();
        		});
        		return false;
            });
        });
        function setmembersvalue(memberValue){
           if(memberValue==-1){
               return;
           }
			$("#input_members").val(memberValue);
        }
    </script>
  </head>
  <body onload="marketInfo()">
    <form name="frm_openAccount" id="frm_openAccount" method="post" action="<%=basePath%>/weixinAdd.fir?funcflg=eidtFirm">
        <div class="frame">
            <div>
                <!--<input name="" type="button" class="back_button " />-->
            </div>
            <div class="title">模拟注册-填写信息</div>
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
                            <td width="34%" class="td_1"><span class="span_1">*</span>微信昵称：</td>
                            <td width="65%" class="td_2" style="text-align: left;">${nikeName }</td>
                        </tr>
                        
                        <tr>
                            <td width="34%" class="td_1"><span class="span_1">*</span>姓名：</td>
                            <td width="65%" class="td_2">
                                <input name="fullName" id="input_name" type="text" maxlength="6"   required=""></td>
                        </tr>
                        <tr>
                            <td class="td_1"><span class="span_1">*</span>移动电话：</td>
                            <td class="td_2">
                                <input name="phone" id="input_tel" type="tel" maxlength="11"  pattern="[0-9]*"></td>
                        </tr>
                        <tr>
                            <td class="td_1"><span class="span_1">*</span>证件类型：</td>
                            <td class="td_2" id="td_cards">
								<select name="papersType" id="select_cardType" type="select" class="down_ico">
								    <option value="-1">请选择证件类型</option>
								    
								    	<option value="1">
								    		机构代码
								    	</option>
								    
								    	<option value="2">
								    		身份证
								    	</option>
								    
								    	<option value="3">
								    		护照
								    	</option>
								    
								    	<option value="4">
								    		营业执照
								    	</option>
								     
								    
								</select>
							</td>
                        </tr>
                        <tr>
                            <td class="td_1"><span class="span_1">*</span>证件号码：</td>
                            <td class="td_2">
                                <input name="papersName" id="input_cardNum"  type="text" maxlength="18" required=""></td>
                        </tr>
							
                        <tr>
                            <td class="td_1"><span class="span_1">*</span>会员单位：</td>
                            <td class="td_2">
                                <select  name="memberNo"  id="select_members"  onchange="setmembersvalue(this.value)"  class="down_ico">
								    <option value="-1">请选择会员单位</option>
								    ${members }
								</select>
                            </td>
                        </tr>
                        <tr>
                            <td class=" tl pl5"><span class="span_1">*</span>会员编号：</td>
                            <td class="tr pr5">
                                <input type="text"  id="input_members"  disabled="disabled"></td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <div class="bottom_left"></div>
                                <div class="bottom_right"></div>
                            </td>
                        </tr>
                    </tbody></table>
                </div>
                <div class="system">
                    <input name="" type="checkbox" id="ckb_Agree">我已认真阅读<a href="<%=basePath%>/wap/agreement4.jsp">《开户约定书》</a>、<a href="<%=basePath%>/wap/agreement3.jsp">《关于禁止代客交易的说明书》</a>完全理解，同意遵守交易所各项制度
                </div>
            </div>
            <div class="line">&nbsp;</div>
            <div class="con">
                <div class="button_l"></div>
                <div class="button_r"></div>
                <div class="button_m" id="div_sumbit">立即提交</div>
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