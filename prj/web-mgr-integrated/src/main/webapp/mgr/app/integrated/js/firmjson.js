/*
 * ͨ��������ѯ�����̴����б�����װ��json������
 * 
 * ʹ�÷�ʽ��
 * <link rel="stylesheet" href="${skinPath}/css/autocomplete/jquery.autocomplete.css" type="text/css" />
 * <script type="text/javascript" src="${publicPath}/js/jquery-1.6.min.js"></script>
 * <script type="text/javascript" src="${publicPath}/js/autocomplete/jquery.autocomplete.js"></script>
 * <script type="text/javascript" src="${basePath}/mgr/app/integrated/js/firmjson.js"></script>
 * 
 * jQuery(document).ready(function() {
 * 		$("#firmId").focus().autocomplete(getfirmList());
 * 	});
 * firmId Ϊ������ Id ��
 * 
 * 
 * @param firmId ���ֽ����̴��룬��ѯ��ʽ like
 * @param type ���������ͣ���ѯ��ʽ =
 * @param status ������״̬����ѯ��ʽ =
 * @return json ��
 */
function getfirmList(firmId,type,status){
	var oldAjaxAsync = $.ajaxSettings.async;
	var json;
	var url = "../../ajaxcheck/mfirm/getfirmListJson.action?t="+Math.random();
	if(firmId){
		url += "&firmId="+firmId;
	}
	if(type){
		url += "&type="+type;
	}
	if(status){
		url += "&status="+status;
	}else{
		url += "&status=N";
	}
	$.ajaxSettings.async = false;
	$.getJSON(url,null,function call(result){
		json = result;
	});
	$.ajaxSettings.async = oldAjaxAsync;
	return json;
}