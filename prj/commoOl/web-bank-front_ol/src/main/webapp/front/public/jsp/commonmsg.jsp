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
		//Ĭ�ϴ�������ܵ�����Ϣ
		var msgpage = window;
		//�����ҳ������ showMsgBox ��������Ӹ�ҳ���е�����Ϣ
		if(typeof window.parent.showMsgBox == 'function'){
			msgpage = window.parent;
		}else if(typeof window.top.showMsgBox == 'function'){
			msgpage = window.top;
		}
		if(${ReturnValue.result==1}){
			msgpage.showMsgBox(1,'������Ϣ','${ReturnValue.info}','${ReturnValue.resultType}');
		}else if(${ReturnValue.result==0}){
			msgpage.showMsgBox(0,'������Ϣ','${ReturnValue.info}','${ReturnValue.resultType}');
		}else if(${ReturnValue.result==-1}){
			msgpage.showMsgBox(-1,'������Ϣ','${ReturnValue.info}','${ReturnValue.resultType}');
		}else{
			msgpage.showMsgBox('${ReturnValue.result}','������Ϣ','${ReturnValue.info}','${ReturnValue.resultType}');
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
	if(result==1){//������ؽ��Ϊ 1 ���߽��ֵ���� 10 ����Ϊ���سɹ�
		asyncbox.success(msg,title,callbakfun);
	}else if(result==0){//������ؽ��Ϊ 0 ����Ϊ���ؾ�����Ϣ
		asyncbox.alert(msg,title,callbakfun);
	}else if(result==-1){//������ؽ��С�� 0 ����Ϊ����ʧ����Ϣ
		asyncbox.error(msg,title,callbakfun);
	}else{//������ؽ������ 1 ����С�� 10 �򲻵�����Ϣ��ֱ�ӵ���ҳ��ص��������緵�ش��ͻ��ж���Ϣ��
		callbakfun();
	}
}
</script>
<%//����������Ϣҳ�� %>