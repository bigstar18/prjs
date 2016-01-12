<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>

<html>
  <head>
  	<base target="_self"/>
    <title>修改开盘指导价</title>
    <link rel="stylesheet" href="${mgrPath}/skinstyle/default/css/app/common.css" type="text/css"/>
    <script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="${publicPath}/js/firmjson.js"></script>
	<script src="${publicPath }/js/validationengine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
	<script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>
	<script type="text/javascript" src="${publicPath}/js/autocomplete/jquery.autocomplete.js"></script>
    <script type="text/javascript"> 

//save
function save_onclick()
{
	if (confirm("您确定要提交吗？")) {
		if (document.forms[0].commodityID.value == "") {
			alert("商品代码不能为空！");
			return false;
		}
		if (document.forms(0).openingPrice.value == "") {
			alert("开盘价不能为空！");
			return false;
		}
		var updateDataUrl = $("#ok").attr("action");
		$("#frm").attr("action",updateDataUrl);
		frm.submit();
	}
  
}

function commodity_onchange(){
	var commodityId=document.forms(0).commodityID.value;
	if ( commodityId== "") {
		alert("商品代码不能为空！");
		document.getElementById("openingPrice").value="";
		return false;
	}
	isCommodity();
}
function isCommodity(){
	var commodityId=document.forms(0).commodityID.value;
	var oldAjaxAsync = $.ajaxSettings.async;
	var url = "${basePath}/timebargain/xtwh/ycclCommodity.action?id=" + commodityId;
	$.ajaxSettings.async = false;
	$.getJSON(url,null,function call(result){
		var value=obj2str(result);
    	var arrStr=value.split(",");
    	document.getElementById("openingPrice").value=arrStr[5];
	});
	$.ajaxSettings.async = oldAjaxAsync;
}
//Object转换成String
function obj2str(o){
   var r = [];
   if(typeof o == "string" || o == null) {
     return o;
   }
   if(typeof o == "object"){
     if(!o.sort){
       r[0]="{"
       for(var i in o){
         r[r.length]=i;
         r[r.length]=":";
         r[r.length]=obj2str(o[i]);
         r[r.length]=",";
       }
       r[r.length-1]="}"
     }else{
       r[0]=""
       for(var i =0;i<o.length;i++){
         r[r.length]=obj2str(o[i]);
         r[r.length]=",";
       }
       r[r.length-1]=""
     }
     return r.join("");
   }
   return o.toString();
}
function dealcontent1(sel)
{
    document.forms[0].commodityID.value=sel.options[sel.selectedIndex].value;
}
function cancle_onclick(){
	window.close();
}
function onlyNumberInput()
{
  if (event.keyCode<45 || event.keyCode>57 || event.keyCode == 47)
  {
    event.returnValue=false;
  }
}
</script>
  </head>
  
<body leftmargin="0" topmargin="0" >
		<table border="0" height="80%" width="55%" align="center">
			<tr>
				<td>
					<form id="frm" action="" method="POST" >
						<fieldset class="pickList" >
							<legend class="common">
								<b>
								  修改开盘指导价
								</b>
							</legend>
							<table border="0" align="center" cellpadding="5" cellspacing="5"
								class="common">
								<tr>
									<td align="right">
											商品代码：
									</td>
									<td>
										<input type="hidden" id="commodityID"  name="commodityID" value=""/>
                                        <SELECT  name="select6" style="width:100px"  onchange="dealcontent1(this);commodity_onchange()">
								            <OPTION value="">请选择</OPTION>
								            <c:forEach items="${commodityList}" var="result">
									        	<option value="${result.commodityId}">${result.commodityId}</option>
										    </c:forEach>
								        </SELECT>   
										<span class="req">*</span>
									</td>
									<td></td>
								</tr>
								
								<tr>
									<td align="right">
										开盘指导价：
									</td>
									<td>
										<input type="text" name="openingPrice" maxlength="10" onkeypress="return onlyNumberInput()" style="ime-mode:disabled" styleClass="Number"/>
										<span class="req">*</span>
									</td>
									<td></td>
								</tr>																																							
								<tr>
									<td colspan="3" align="center">
										<rightButton:rightButton name="提交"   id="ok" onclick="save_onclick();" className="button2" action="${basePath}/timebargain/xtwh/ycclEditLastPrice.action" ></rightButton:rightButton>
										
										<rightButton:rightButton name="关闭"   id="cancel" onclick="cancle_onclick();" className="button2" action="${basePath}/timebargain/xtwh/yccl.action" ></rightButton:rightButton>
									</td>
								</tr>
							</table>
						</fieldset>
					</form>
				</td>
			</tr>
		</table>
	</body>
</html>