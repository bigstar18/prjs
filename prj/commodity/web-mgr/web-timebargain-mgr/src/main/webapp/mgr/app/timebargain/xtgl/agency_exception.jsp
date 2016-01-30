<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>

<html>
<head>
<link rel="stylesheet" href="${mgrPath}/skinstyle/default/css/app/common.css" type="text/css"/>
<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
<title></title>	
<script type="text/javascript">
 

var status;
window_onload = function() {
 	status=$("#status").attr("value");
}
function balanceChk_onclick(id)
{
  var name;
 
  if(id=='04')
  {
  name="在线更新交易数据";
  }
  if (id == '13') {
  	name="重算保证金";
  }
  if (id == 'online') {
  	name = "在线更新交易节设置";
  }
  if (id == 'recoverDelayTrade') {
  	name = "交易结束转恢复延期交易";
  }
  if (id == 'onlineDelay') {
  	name = "在线更新延期交易节设置";
  }
  if (confirm("您确定要" + name + "吗？"))
  {
	  if(id=='04'){
	  	//在线更新交易数据
		var updateDataUrl = $("#ok4").attr("action");
		$("#frm").attr("action",updateDataUrl);
		frm.submit();
	  }
	 if (id == '13') {
	 	//重算保证金
	  	var updateDataUrl = $("#ok13").attr("action");
		$("#frm").attr("action",updateDataUrl);
		frm.submit();
	 }
	 if (id == 'online') {
	  	//在线更新交易节设置
	  	var updateDataUrl = $("#ok16").attr("action");
		$("#frm").attr("action",updateDataUrl);
		frm.submit();
	 }
	 if (id == 'recoverDelayTrade') {
	  	//交易结束转恢复延期交易
	  	var updateDataUrl = $("#ok17").attr("action");
		$("#frm").attr("action",updateDataUrl);
		frm.submit();
	 }
	 if (id == 'onlineDelay') {
	  	//在线更新延期交易节设置
	  	var updateDataUrl = $("#ok18").attr("action");
		$("#frm").attr("action",updateDataUrl);
		frm.submit();
	 }
  }
}

function balance(){
	if (confirm("您确定要重做结算吗？")) {
		if (('3' == status) || '10' == status) {
			var updateDataUrl = $("#ok14").attr("action");
			$("#frm").attr("action",updateDataUrl);
			frm.submit();
		}else {
			alert("系统不是结算完成状态，不能重做！");
			return false;
		}
		
	}
	
}
//在线设置保证金
function operateMargin(){
	var url = $("#ok15").attr("action");
	if(showDialog(url, "", 600, 600)){
	
	}
}
//在线设置特殊保证金
function operateSpacMargin(){
	var url = $("#ok11").attr("action");
	showDialog(url, "", 600, 600);
}

function openingPrice(){//开盘价
	var url = $("#ok20").attr("action");
	showDialog(url, "", 600, 600);
}

</script>
</head>
<body  leftmargin="0" topmargin="0" onLoad="window_onload()">

<table border="0" height="300" align="center" >
<tr><td>
<form id="frm" method="post" action="">
	<fieldset  class="pickList">
	<legend class="common">普通操作</legend>
      <table border="1" align="center" cellpadding="5" cellspacing="5" class="commonTest" height="220" width="500">
      
    	<tr>
    		<td>
    			操作
    			<input type="hidden" id="status" value="${sessionScope.status }">
    		</td>
    		<td>
    			说明
    		</td>
    	</tr>
        <tr height="20">
        <td>
        <rightButton:rightButton name="在线更新交易数据"  id="ok4" onclick="balanceChk_onclick('04');" className="button1" action="${basePath}/timebargain/xtwh/yccl4.action" ></rightButton:rightButton>
<!--        <button id="ok1" value="在线更新交易数据" ></button>-->
        </td>
        
        <td>
        	"交易参数设置"菜单下的"交易市场参数"和"商品管理"模块改动;"交易商设置"菜单下的"特殊设置"改动,即时生效.
    	</td>
        </tr>
        <tr height="20">
        <td>
        <rightButton:rightButton name="在线设置保证金"  id="ok15" onclick="operateMargin();" className="button1" action="${basePath}/timebargain/xtwh/ycclEditMargin.action" ></rightButton:rightButton>
        </td>
        
        <td>
        	本日交易将使用新的保证金标准.操作顺序"暂停交易"-->"在线设置保证金"-->"在线更新交易数据"-->"恢复交易"
    	</td>
        </tr>
        
        <tr height="20">
        <td>
        <rightButton:rightButton name="在线设置特殊保证金"   id="ok11" onclick="operateSpacMargin();" className="button1" action="${basePath}/timebargain/xtwh/ycclSpacMargin.action" ></rightButton:rightButton>
        </td>
        
        <td>
        	本日交易将使用新的特殊保证金标准.操作顺序"暂停交易"-->"在线设置特殊保证金"-->"在线更新交易数据"-->"恢复交易"
    	</td>
        </tr>
        
        <tr height="20">
        <td>
        <rightButton:rightButton name="重算保证金"   id="ok13" onclick="balanceChk_onclick('13');" className="button1" action="${basePath}/timebargain/xtwh/yccl13.action" ></rightButton:rightButton>
        </td>
        
        <td>
        	按当前保证金标准,重新计算已成交合同保证金.
    	</td>
        </tr>
        
        <tr height="20">
        <td>
        <rightButton:rightButton name="重做交易系统结算"   id="ok14" onclick="balance();" className="button1" action="${basePath}/timebargain/xtwh/ycclbalanceChkFroenFundEXC.action" ></rightButton:rightButton>
        </td>
        
        <td>
        	再次对交易系统进行结算处理.
    	</td>
        </tr>
        
        <tr>
		<td >
			<rightButton:rightButton name="在线更新交易节设置"   id="ok16" onclick="balanceChk_onclick('online');" className="button1" action="${basePath}/timebargain/xtwh/ycclOnline.action" ></rightButton:rightButton>
		</td>
		
		<td>
			"交易节管理"中的交易节设置,在此触发即时生效.
    	</td>
		</tr>

           <tr>
		<td >
			<rightButton:rightButton name="修改开盘指导价"   id="ok20" onclick="openingPrice();" className="button1" action="${basePath}/timebargain/xtwh/yccl20.action" ></rightButton:rightButton>
		</td>
		
		<td>
			如果是在初始化完成状态下修改的话，修改成功需要触发在线更新交易数据按钮刷新内存
    	</td>
		</tr>
      </table>
  	</fieldset>

<c:choose>
	<c:when test="${sessionScope.useDelay=='Y'}">
  	<fieldset  class="pickList">
	<legend class="common">延期操作</legend>
      <table border="1" align="center" cellpadding="5" cellspacing="5" class="commonTest"  width="500">
      
        <tr height="20">
        <td>
        <rightButton:rightButton name="交易结束转恢复延期交易"   id="ok17" onclick="balanceChk_onclick('recoverDelayTrade');" className="button1" action="${basePath}/timebargain/xtwh/ycclRecoverDelayTrade.action" ></rightButton:rightButton>
        </td>
        
        <td>
        	延期交易的时间内,恢复已结束的延期交易.
    	</td>
        </tr>
        
        <tr>
		<td >
			<rightButton:rightButton name="在线更新延期交易节设置"   id="ok18" onclick="balanceChk_onclick('onlineDelay');" className="button1" action="${basePath}/timebargain/xtwh/ycclOnlineDelay.action" ></rightButton:rightButton>
		</td>
		
		<td>
			"延期交易节管理"中的延期交易节设置,在此触发即时生效.
    	</td>
		</tr>

      </table>
  	</fieldset>
  	</c:when>
</c:choose>

<form>
</td></tr>
</table>


</body>
</html>