/*
 * 通过参数查询交易商代码列表，并封装成json串返回
 * 
 * 使用方式：
 * <link rel="stylesheet" href="${skinPath}/css/autocomplete/jquery.autocomplete.css" type="text/css" />
 * <script type="text/javascript" src="${publicPath}/js/jquery-1.6.min.js"></script>
 * <script type="text/javascript" src="${publicPath}/js/autocomplete/jquery.autocomplete.js"></script>
 * <script type="text/javascript" src="${basePath}/mgr/app/integrated/js/firmjson.js"></script>
 * 
 * jQuery(document).ready(function() {
 * 		$("#firmId").focus().autocomplete(getfirmList());
 * 	});
 * firmId 为输入框的 Id 号
 * 
 * 
 * @param firmId 部分交易商代码，查询形式 like
 * @param type 交易商类型，查询形式 =
 * @param status 交易商状态，查询形式 =
 * @return json 串
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