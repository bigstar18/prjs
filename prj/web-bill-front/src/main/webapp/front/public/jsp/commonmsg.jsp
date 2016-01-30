<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<script type="text/javascript" src="${frontPath }/public/js/AsyncBox.v1.4.5.js"></script>
<link href="${skinPath }/css/asyncbox/asyncbox.css" type="text/css" rel="stylesheet" />
<style>
<!--

/*ie6 filter*/
.b_t_l{_filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='${skinPath}/image/asyncbox/ie6/dialog_lt.png',sizingMethod='crop');}
.b_t_r{_filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='${skinPath}/image/asyncbox/ie6/dialog_rt.png',sizingMethod='crop');}

.b_tipsbar_l,.b_m_l,.b_btnsbar_l{_filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='${skinPath}/image/asyncbox/ie6/dialog_mlm.png',sizingMethod='scale');}
.b_tipsbar_r,.b_m_r,.b_btnsbar_r{_filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='${skinPath}/image/asyncbox/ie6/dialog_mrm.png',sizingMethod='scale');}

.b_b_l{_filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='${skinPath}/image/asyncbox/ie6/dialog_lb.png',sizingMethod='crop');}
.b_b_r{_filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='${skinPath}/image/asyncbox/ie6/dialog_rb.png',sizingMethod='crop');}

.b_t_m{_filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='${skinPath}/image/asyncbox/ie6/dialog_ct.png',sizingMethod='scale');}
.b_b_m{_filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='${skinPath}/image/asyncbox/ie6/dialog_cb.png',sizingMethod='scale');}
-->
</style>
<script type="text/javascript">
$(function(){
	if(${ReturnValue!=null}&&'${not empty ReturnValue.info}'=='true'){
		//默认从最外层框架弹出信息
		var msgpage = window;
		//如果父页面中有 showMsgBox 方法，则从父页面中弹出信息
		if(typeof window.parent.showMsgBox == 'function'){
			msgpage = window.parent;
		}else if(typeof window.top.showMsgBox == 'function'){
			msgpage = window.top;
		}
		if(${ReturnValue.result==1}){
			msgpage.showMsgBox(1,'操作信息','${ReturnValue.info}','${ReturnValue.resultType}');
		}else if(${ReturnValue.result==0}){
			msgpage.showMsgBox(0,'警告信息','${ReturnValue.info}','${ReturnValue.resultType}');
		}else if(${ReturnValue.result==-1}){
			msgpage.showMsgBox(-1,'错误信息','${ReturnValue.info}','${ReturnValue.resultType}');
		}else{
			msgpage.showMsgBox('${ReturnValue.result}','警告信息','${ReturnValue.info}','${ReturnValue.resultType}');
		}
	}
});

function showMsgBox(result,title,msg,resultType){
	var callbakfun;
	if(typeof showMsgBoxCallbak == 'undefined' || !(showMsgBoxCallbak instanceof Function)){
		callbakfun=function (){}
	}else{
	    callbakfun=	function (){
			showMsgBoxCallbak(result,msg,resultType);
		};
	}
	if(result==1){//如果返回结果为 1 或者结果值大于 10 则认为返回成功
		asyncbox.success(msg,title,callbakfun);
	}else if(result==0){//如果返回结果为 0 则认为返回警告信息
		asyncbox.alert(msg,title,callbakfun);
	}else if(result==-1){//如果返回结果小于 0 则认为返回失败信息
		asyncbox.error(msg,title,callbakfun);
	}else{//如果返回结果大于 1 并且小于 10 则不弹出信息，直接调用页面回调函数。如返回待客户判断信息等
		callbakfun();
	}
}
</script>
<%//弹出返回信息页面 %>