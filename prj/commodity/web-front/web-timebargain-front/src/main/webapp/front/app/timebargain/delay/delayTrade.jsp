<%@ page contentType="text/html;charset=GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/front/public/includefiles/allIncludeFiles.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>资金结算表页面</title>
		<link href="${skinPath}/css/mgr/memberadmin/module.css"
			rel="stylesheet" type="text/css" />
		<link href="${skinPath}/css/app/timebargain/delay/delayTrade.css"
			rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${publicPath}/js/jquery-1.6.2.min.js"></script>
			
		<script type="text/javascript">
			$(function() {
	
				/**************************** 以下为printDelayQuotaion.jsp中用到的脚本 ****************************/
				
				//刷新延期交收行情
				function refreshData(){
					$.ajax( {
						url : "${frontPath }/app/timebargain/delayAjax/delayRealTimeInfo.action",
						type : 'post',
						cache : false,
						success : function(msg) {
							//中立仓启用标志  启用:true 未启用:false   
							//var neutralFlag = $("#neutral").html() == null || $("#neutral").html() == "" ? false : true;
							$.each(msg, function(i, n) {
								var _commodityId = n['COMMODITYID'];
								var _buysettleqty = $("#" + _commodityId + " div[name='buysettleqty']");
								var _sellsettleqty = $("#" + _commodityId + " div[name='sellsettleqty']");
								var _totalamount = $("#" + _commodityId + " div[name='totalamount']");
								var _reservecount = $("#" + _commodityId + " div[name='reservecount']");
								var _price = $("#" + _commodityId + " div[name='price']");
								var _neutralQty = $("#" + _commodityId + " div[name='neutralQty']");
	
								_buysettleqty.html(n['BUYSETTLEQTY']);
								_sellsettleqty.html(n['SELLSETTLEQTY']);
								_totalamount.html(n['TOTALAMOUNT']);
								_reservecount.html(n['RESERVECOUNT']);
								_price.html(n['PRICE']);
								
								//如果启用中立仓
								if(_neutralQty.html() != null){
									var _neutralside = n['NEUTRALSIDE'];
									var qty = '-';
									if(_neutralside == '买'){
										qty = n['BUYNEUTRALQTY'];
									}else if(_neutralside == '卖'){
										qty = n['SELLNEUTRALQTY'];
									}
									_neutralQty.html(qty);
								}
									
							});
							
						}
					});
				}
	
				window.setInterval(function(){
					refreshData();
				},1000);
				
				
				/**************************** 以下为printDelayOrder.jsp中用到的脚本 ****************************/
				
				
				//默认显示的选项卡 即：申报查询选项卡 JQuery对象
				var defaultShow = $('#clicktab .tabbtn li').eq(0);
				//当前显示的选项卡, 初始值为默认选项卡JQuery对象, 
				//作为全局变量记录当前选项卡在交收申请成功后出发当前选项卡点击事件
				var currentShow = defaultShow;
				//选项卡鼠标滑点击
				$('#clicktab .tabbtn li').click(function(){
					//获取处理方式   ---  declaration 申报查询 、  capital 资金查询 、   order 订货查询
					var processMode = $(this).attr('processMode');
					//alert(processMode);
					//取得对应请求地址和请求参数
					var currAction = $(this).attr('action');
					var commodityId = $("#commodityId_" + processMode).val();
					//发送请求
					sendRequest(currAction, commodityId, processMode);
					currentShow = $(this);
					//数据展示
					TabSelect("#clicktab .tabbtn li", "#clicktab .tabcon", "current", $(this))
				});
				
				//触发点击申报查询事件
				defaultShow.trigger("click");
	
				//切换选项卡
				function TabSelect(tab,con,addClass,obj){
					var $_self = obj;
					var $_nav = $(tab);
					$_nav.removeClass(addClass),
					$_self.addClass(addClass);
					var $_index = $_nav.index($_self);
					var $_con = $(con);
					$_con.hide(),
					$_con.eq($_index).show();
				}
	
				//为两个查询按钮注册点击事件
				$("input[type='button'][name='delayOrderQuery']").click(function(){
					//取得对应请求地址、处理方式和请求参数
					var currAction = $(this).attr('action');
					var processMode = $(this).attr("processMode");
					var commodityId = $("#commodityId_" + processMode).val();
	
					sendRequest(currAction,commodityId, processMode);
				});
				
				//发送请求方法
				function sendRequest(currAction,commodityId, processMode){
				
					$.ajax({
						url : currAction,
						type : 'post',
						data:{"commodityId" : commodityId},
						cache : false,
						dataType : "json",
						success : function(msg) {
							processData(msg,processMode);
						},
						error : function(errormsg){
							alert("由于过长时间未操作，请重新登录。");
						}
					});
				}
				//处理数据方法
				function processData(msg,processMode){
					switch(processMode) {
						case "declaration":
							processDeclaration(msg);
						break;
						case "capital" :
							processCapital(msg);
						break; 
						case "order" :
							processOrder(msg);
						break; 
					}
				}
	
				var infoTagStart = "<td><div class='ordercol'>";
				var infoTagEnd = "</div></td>";
				
				//处理申报查询
				function processDeclaration(msg){
	
					$("#sellectAll").removeAttr("checked");
					
					var infoTag = [];
					
					$.each(msg, function(i, n) {
						infoTag.push("<tr class='font_b tr_bg'>");
						infoTag.push(infoTagStart);
						infoTag.push("<input type='checkbox' name='orderNo' value = '" + n['A_ORDERNO'] + "'/>");
						infoTag.push(infoTagEnd);
						//申报号
						infoTag = dataWrap(infoTag, n['A_ORDERNO']);
						//申报时间
						infoTag = dataWrap(infoTag, n['TIME']);
						//交易码
						infoTag = dataWrap(infoTag, n['CUSTOMERID']);
						//商品代码
						infoTag = dataWrap(infoTag, n['COMMODITYID']);
						//买/卖 
						infoTag = dataWrap(infoTag, n['FLAG']);
						//数量
						infoTag = dataWrap(infoTag, n['QUANTITY']);
						//未成交
						infoTag = dataWrap(infoTag, n['NOTRADEQTY']);
						//状态
						var status = n['STATUS'];
						switch (status) {
						case 1:
							status = "已申报";
							break;
						case 2:
							status = "部分成交";
							break;
						case 3:
							status = "全部成交";
							break;
						case 5:
							status = "已撤单";
							break;
						case 6:
							status = "部分成交后撤单";
							break;
	
						default:
							status = "未知";
							break;
						}
						infoTag = dataWrap(infoTag, status);
						//申报类型
						infoTag = dataWrap(infoTag, n['TYPE']);
	
						infoTag.push("</tr>");
					});
					
					var infoStr = infoTag.join('')
					$('#delayOrderInfo tr:not(#declarationFirstTr)').remove();
					$('#delayOrderInfo tr:last-child').after(infoStr);
				}
				
				//处理资金查询
				function processCapital(msg){
					var usableCapital = $("span[id='usableCapital']");
					var unusableCapital = $("span[id='unusableCapital']");
					if(msg.length != 0){
						usableCapital.html("￥" + msg[0]['USEFULFUND']);
						unusableCapital.html("￥" + msg[0]['FROZENFUNDS']);
					}else{
						usableCapital.html("抱歉！您的网络可能出现问题，请重新登录查询。");
					}
				}
				
				//处理订货查询
				function processOrder(msg){
					var infoTag = [];
	
					$.each(msg, function(i, n) {
						infoTag.push("<tr class='font_b tr_bg'>");
						
						//交易商代码
						infoTag = dataWrap(infoTag, n['CUSTOMERID']);
						//商品代码
						infoTag = dataWrap(infoTag, n['COMMODITYID']);
						//买/卖 
						infoTag = dataWrap(infoTag, n['FLAG']);
						//持仓数量
						infoTag = dataWrap(infoTag, n['HOLDQTY']);
						//冻结数量
						infoTag = dataWrap(infoTag, n['FROZENQTY']);
	
						infoTag.push("</tr>");
					});
					var infoStr = infoTag.join('')
					$('#orderInfo tr:not(#orderFirstTr)').remove();
					$('#orderInfo tr:last-child').after(infoStr);
				}
	
				//拼装html标签方法
				function dataWrap(infoTag, nodeData){
					infoTag.push(infoTagStart);
					infoTag.push(nodeData);
					infoTag.push(infoTagEnd);
					return infoTag;
				}
	
				//全选切换
				$("#sellectAll").change(function(){
					var isChecked = !!($(this).attr('checked'));
					var allCheckBox = $("input[type='checkbox'][name='orderNo']");
					if(isChecked){
						allCheckBox.attr("checked","checked");
					}else{
						allCheckBox.removeAttr("checked");
					}
				});
	
				//撤所选单
				$("#cancel").click(function(){
					var allChecked = $("input[type='checkbox'][name='orderNo']:checked");
					if(allChecked.length == 0){
						alert("请选择要操作的数据！");
						return;
					}
					if(!confirm("确认要撤单么？")){
						return;
					}
					var currAction = $(this).attr("action");
					var orderNos = [];
					allChecked.each(function(){
						orderNos.push($(this).val());
					});
					$.ajax({
						url : currAction,
						type : 'post',
						data:{"orderNos" : orderNos},
						cache : false,
						dataType : "json",
						success : function(msg) {
							alert(msg);
							$("#sellectAll").removeAttr("checked");
							$('#clicktab .tabbtn li').eq(0).trigger("click");
							
						},
						error : function(errormsg){
							alert("由于过长时间未操作，请重新登录。");
						}
					});
				});
	
	
				/**************************** 以下为delayOrder.jsp中用到的脚本 ****************************/
	
				$("#applayBtn").click(function(){
					if(!$(":radio[name='bs_flag']").attr("disabled") && !($(":radio:checked[name='bs_flag']").val())){
						alert("请选择买卖方向");
						return;
					}
					var quantity = $.trim($("#settleDeclare_quantity").val());
					if(!isNumber(quantity)){
						alert("申请数量必须为不以0开头的整数");
						$("#settleDeclare_quantity").val("");
						return;
					}
					if(quantity.length > 10){
						alert("申请数量不能超过10位数");
						$("#settleDeclare_quantity").val("");
						return;
					}
					if(!confirm("确认下单吗?")){
						return;
					}
					var currAction = $(this).attr('action');
					var commodityId = $("#settleDeclare_commodityId").val();
					var delayOrderType = $(":radio[name='delayOrderType']:checked").val();
					var bsFlag = $(":radio[name='bs_flag']:checked").val();
					
					$.ajax( {
						url : currAction,
						type : 'post',
						data:{"commodityId" : commodityId, "delayOrderType" : delayOrderType, "bsFlag" : bsFlag, "quantity" : quantity},
						cache : false,
						dataType : "json",
						success : function(msg) {
							alert(msg);
							currentShow.trigger("click");
						},
						error : function(errormsg){
							alert("由于过长时间未操作，请重新登录。");
						}
					});
				});
				//判断是否都为数字组成
				function isNumber(str){
					if(""==str){
						return false;
					}
					var reg = /^[1-9]\d*$/;
					return str.match(reg)!=null;
				}        
				$(":radio[name='delayOrderType']").change(function(){
					setBsFlagVisibility($(this).val());
				});
				//设置买入卖出的可用性 ----  1 : 可用    2 : 不可用
				function setBsFlagVisibility(s){
					var _bsFlag = $(":radio[name='bs_flag']");
					if(s == 1){
						_bsFlag.removeAttr("disabled");
					}else if(s == 2){
						_bsFlag.attr("disabled","disabled");
					}
				}
			});
				
		</script>
		<style type="text/css">
		.btn{cursor:pointer;}
		</style>
	</head>
	<body>
		<!-------------------------Body Begin------------------------->
		<div style="margin-left: 14px;" >
			<jsp:include page="/front/frame/current.jsp"></jsp:include>
		</div>
		<!-------------------------Body End------------------------->
		<div>
			<jsp:include page="/front/app/timebargain/delay/printDelayQuotation.jsp"></jsp:include>
		</div>
		<div>
			<!-- 查询DIV -->
			<div  style="width: 70%; float: left;">
				<jsp:include page="/front/app/timebargain/delay/printDelayOrder.jsp"></jsp:include>
			</div>
			<!-- 下单DIV -->
			<div style="width : 30%; float: left;">
				<jsp:include page="/front/app/timebargain/delay/delayOrder.jsp"></jsp:include>
			</div>
		</div>
	</body>
</html>