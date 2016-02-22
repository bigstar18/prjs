<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<%@ page contentType="text/html;charset=GBK"%>
<%@page import="gnnt.MEBS.commodity.model.Commodity"%>
<%@ include file="/public/session.jsp"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
	<head>
		<title>性能监控</title>
		<script language="javascript" type="text/javascript"
			src="${basePath}/js/jquery-1.5.2.min.js"></script>
		<script src="${basePath}/js/heighCharts/highcharts.js"  ></script>
		<script src="${basePath}/js/heighCharts/dark-blue.js"></script>
		<script type="text/javascript" src="${basePath}/js/heighCharts/online.js"></script>
		<script type="text/javascript" src="${basePath}/js/heighCharts/ordernum.js"></script>
		<script type="text/javascript" src="${basePath}/js/heighCharts/holdnum.js"></script>
		<script type="text/javascript" src="${basePath}/js/heighCharts/tradenum.js"></script>

			
		<style type="text/css">
			body {background:#ffffff; margin:0px; font-size:14px;text-align: center; color:#36332D; font-weight:bold;}
			.bor_980{width:1200px; height:auto; margin:10px auto;}
			.bor_450{width:548px; height:auto; float:left; margin:0px 20px 20px 20px; border:1px solid #36332D;}
			.line{ border-bottom:1px solid #36332D; border-right:1px solid #36332D;}
			.line1{ border-bottom:1px solid #36332D;}
			.clear{clear:both;}
			.font_14B_G{}
		</style>
		<script type="text/javascript">
		var onlineRes;
		var orderRes;
		var tradeRes;
		var holdRes;
		//ajax 获取在线监控数据
		function getOnlineInfo(type){
			if(type!="1"){
				return;
			}
			
			var url = "${basePath}/monitor/performanceForAjax/queryPerformanceForOnline.action?type="+type+"&t="+Math.random();
			$.ajaxSetup({
                error: function (x, e) {
                    if(x.responseText!=''){
						document.write(x.responseText);
                    };
                    return false;
                }
            });
			$.getJSON(url,function(data){
					online.series[0].setData(data.jsonMap['online']); 
			});
			
		}
		//获取委托监控数据
		function getOrdersInfo(type){
			if(type!="2"){
				return;
			}
			var url = "${basePath}/monitor/performanceForAjax/queryPerformanceForOrders.action?type="+type+"&t="+Math.random();
			$.ajaxSetup({
                error: function (x, e) {
                    if(x.responseText!=''){
						document.write(x.responseText);
                    };
                    return false;
                }
            });
			$.getJSON(url,function(data){
				if(order.series!=null&&data.commodityList.length==order.series.length){
					for(var i=0;i<order.series.length;i++){
						order.series[i].setData(data.jsonMap[data.commodityList[i].id]); 
					}
				}

				if(order.series!=null&&data.commodityList.length!=order.series.length&&order.series.length!=0){
					var holdSeries=order.series.length;
					for(var i=0;i<holdSeries;i++){
						order.series[0].remove();
					}
				}

				if(order.series==null||(order.series!=null&&order.series.length==0)){
					for(var i=0;i<data.commodityList.length;i++){
						order.addSeries({
							type: 'line',
							name: data.commodityList[i].id,
							data: data.jsonMap[data.commodityList[i].id]
						});
					}
				}
					
			});
		}
		//获取成交监控数据
		function getTradesInfo(type){
			if(type!="3"){
				return;
			}
			var url = "${basePath}/monitor/performanceForAjax/queryPerformanceForTrades.action?type="+type+"&t="+Math.random();
			$.ajaxSetup({
                error: function (x, e) {
                    if(x.responseText!=''){
						document.write(x.responseText);
                    };
                    return false;
                }
            });
			$.getJSON(url,function(data){
				if(trade.series!=null&&data.commodityList.length==trade.series.length){
					for(var i=0;i<trade.series.length;i++){
						trade.series[i].setData(data.jsonMap[data.commodityList[i].id]); 
					}
				}

				if(trade.series!=null&&data.commodityList.length!=trade.series.length&&trade.series.length!=0){
					var holdSeries=trade.series.length;
					for(var i=0;i<holdSeries;i++){
						trade.series[0].remove();
					}
				}

				if(trade.series==null||(trade.series!=null&&trade.series.length==0)){
					for(var i=0;i<data.commodityList.length;i++){
						trade.addSeries({
							type: 'line',
							name: data.commodityList[i].id,
							data: data.jsonMap[data.commodityList[i].id]
						});
					}
				}
					
			});
			
		}
	
		
		//获取持仓监控数据
		function getHoldInfo(type){
			if(type!="4"){
				return;
			}
			var url = "${basePath}/monitor/performanceForAjax/queryPerformanceForHold.action?type="+type+"&t="+Math.random();
			$.ajaxSetup({
                error: function (x, e) {
                    if(x.responseText!=''){
						document.write(x.responseText);
                    };
                    return false;
                }
            });
			$.getJSON(url,function(data){
				if(hold.series!=null&&data.commodityList.length==hold.series.length){
					for(var i=0;i<hold.series.length;i++){
						hold.series[i].setData(data.jsonMap[data.commodityList[i].id]); 
					}
				}

				if(hold.series!=null&&data.commodityList.length!=hold.series.length&&hold.series.length!=0){
					var holdSeries=hold.series.length;
					for(var i=0;i<holdSeries;i++){
						hold.series[0].remove();
					}
				}

				if(hold.series==null||(hold.series!=null&&hold.series.length==0)){
					for(var i=0;i<data.commodityList.length;i++){
						hold.addSeries({
							type: 'line',
							name: data.commodityList[i].id,
							data: data.jsonMap[data.commodityList[i].id]
						});
					}
				}
					
			});
			
		}

		function start(){
			onlineRes=setInterval("getOnlineInfo(1);",2000);
			orderRes=setInterval("getOrdersInfo(2);",2000);
			tradeRes=setInterval("getTradesInfo(3);",2000);
			holdRes=setInterval("getHoldInfo(4);",2000);
		}
		start();
		</script>
	</head>

	<body>
	<div class="bor_980">

	  <div class="bor_450">
	    <table id="tab1" width="548" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
	      <tr>
	        <td colspan="2" align="center" bordercolor="#36332D" class="line">
				<div id="container_online" style="height: 300px; margin: 0 auto" ></div>
			</td>
	      </tr>
	      
	      
	    </table>
	  </div>
  
  <div class="bor_450">
	    <table id="tab2" width="548" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
	      <tr>
	        <td colspan="2" align="center" bordercolor="#36332D" class="line">
				<div id="container_order" style="height: 300px; margin: 0 auto" ></div>
			</td>
	      </tr>
	      
	     
	    </table>
	  </div>
	  
	  <div class="bor_450">
	    <table id="tab3" width="548" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
	      <tr>
	        <td colspan="2" align="center" bordercolor="#36332D" class="line">
				<div id="container_trade" style="height: 300px; margin: 0 auto" ></div>
			</td>
	      </tr>
	   
	    </table>
	  </div>
	  
	  <div class="bor_450">
	    <table id="tab4" width="548" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
	      <tr>
	        <td colspan="2" align="center" bordercolor="#36332D" class="line">
				<div id="container_hold" style="height: 300px; margin: 0 auto" ></div>
			</td>
	      </tr>
	      
	     
	    </table>
	  </div>



  </div>
	</body>
</html>
