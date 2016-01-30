<%@ page contentType="text/html;charset=GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/front/public/includefiles/allIncludeFiles.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>申报 资金 订货</title>
		
		<link href="${skinPath}/css/mgr/memberadmin/module.css"
			rel="stylesheet" type="text/css" />
			<style type="text/css">
				*{margin:0;padding:0;list-style-type:none;}
				a,img{border:0;}
				body{font:12px/180% Arial, Helvetica, sans-serif,"宋体";}
				a{color:#333;text-decoration:none;}
				a:hover{color:#3366cc;text-decoration:underline;}
				.demopage{width:760px;margin:0 auto;}
				.demopage h2{font-size:14px;text-align:center;}
				/* tabbox */
				.tabbox{width:100%;margin:20px auto;position:relative;height:100%;overflow:hidden; margin-left: 14px;}
				.tabbox .tabbtn{height:30px;background:url(../images/tabbg.gif) repeat-x;border-left:solid 1px #ddd;border-right:solid 1px #ddd;}
				.tabbox .tabbtn li{float:left;position:relative;margin:0 0 0 -1px;}
				.tabbox .tabbtn li a,.tabbox .tabbtn li span{display:block;float:left;height:30px;line-height:30px;overflow:hidden;width:108px;text-align:center;font-size:12px;cursor:pointer;}
				.tabbox .tabbtn li.current{border-left:solid 1px #d5d5d5;border-right:solid 1px #d5d5d5;border-top:solid 1px #c5c5c5;}
				.tabbox .tabbtn li.current a,.tabbox .tabbtn li.current span{border-top:solid 2px #ff6600;height:27px;line-height:27px;background:#fff;color:#3366cc;font-weight:800;}
				.tabbox .loading{height:40px;width:432px;text-align:center;position:absolute;left:0;top:120px;}
				.tabbox .tabcon{padding:10px;border-width:0 1px 1px 1px;border-color:#ddd;border-style:solid;}
				.tabbox .tabcon li{height:24px;line-height:24px;overflow:hidden;}
				.tabbox .tabcon li span{margin:0 10px 0 0;font-family:"宋体";font-size:12px;font-weight:400;color:#ddd;}
			</style>
	</head>
	<body>
		<!-------------------------Body Begin------------------------->
		
		<!---------------------  选项卡面板 div 开始   --------------------->
		<div class="tabbox" id="clicktab">
			<ul class="tabbtn">
				<li class="current" processMode = "declaration" action="${frontPath }/app/timebargain/delayAjax/delayOrderQuery.action"><span>申报查询</span></li>
				<li processMode = "capital" action = "${frontPath }/app/timebargain/delayAjax/capitalQuery.action"><span>资金查询</span></li>
				<li processMode = "order" action="${frontPath }/app/timebargain/delayAjax/delayCommodityHoldingQuery.action"><span>订货查询</span></li>
			</ul><!--tabbtn end-->
			
			<!-----------  申报查询div 开始 ----------->
			<div class="tabcon">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr style="height: 40px;">
						<td colspan="2">
							<div>
								<table width="100%" border="0" cellspacing="0" cellpadding="0"
									class="content">
									<tr class="font_b">
										<td width="50%">
											<div>
												商品代码：<input type = "text" id = "commodityId_declaration" value = ""/>
											</div>
										</td>
										<td align="right" width = "20%">
											<div>
												<input type="button" name = "delayOrderQuery" processMode = "declaration" action="${frontPath }/app/timebargain/delayAjax/delayOrderQuery.action" value = "查询" class = "btn"/>
											</div>
										</td>
										<td align="right" width="30%">
											<div>
												<input type="button" id = "cancel" value = "撤所选单" class = "btn" action = "${frontPath }/app/timebargain/delayAjax/cancelDelayOrder.action"/>
											</div>
										</td>
									</tr>
								</table>
							</div>
						</td>
					</tr>
					
					<tr class="font_b tr_bg" >
						<td colspan="2">
							<div class="tb_bg" >
								<table id="delayOrderInfo" width="100%" border="0" cellspacing="0" cellpadding="0"
									class="content">
									<tr class="font_b tr_bg" id = "declarationFirstTr">
										<td class="mainw01" width="4%">
											<div class="ordercol">
												<input type="checkbox" id = "sellectAll"/>
											</div>
										</td>
										<td width="12%">
											<div class="ordercol">
												申报号
											</div>
										</td>
										<td width="12%">
											<div class="ordercol">
												申报时间
											</div>
										</td>
										<td width="12%">
											<div class="ordercol">
												交易码
											</div>
										</td>
										<td width="10%">
											<div class="ordercol">
												商品代码
											</div>
										</td>
										<td width="10%">
											<div class="ordercol">
												买/卖
											</div>
										</td>
										<td width="10%">
											<div class="ordercol">
												数量
											</div>
										</td>
										<td width="10%">
											<div class="ordercol">
												未成交
											</div>
										</td>
										<td width="10%">
											<div class="ordercol">
												状态
											</div>
										</td>
										<td width="10%">
											<div class="ordercol">
												申报类型
											</div>
										</td>
									</tr>
								</table>
							</div>
						</td>
					</tr>
				</table>
			</div>
			
			<!-----------  申报查询div 结束   ----------->
			
			<!-----------  资金查询div 开始 ----------->
			<div class="tabcon">
				<div class = "column1">可用资金：<span id = "usableCapital" class = "ordercol_on"></span></div>
				<div class = "column1">冻结资金：<span id = "unusableCapital" class = "ordercol_on"></span></div>
			</div>
			<!-----------  资金查询div 结束   ----------->
			
			<!-----------  订货查询div 开始   ----------->
			<div class="tabcon">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr style="height: 40px;">
						<td colspan="2">
							<div>
									<table width="100%" border="0" cellspacing="0" cellpadding="0"
										class="content">
										<tr class="font_b">
											<td width="50%">
												<div>
													商品代码：<input type = "text" id = "commodityId_order" value = ""/>
												</div>
											</td>
											<td align="right" width="20%">
												<div>
													<input type="button" name = "delayOrderQuery" processMode = "order" action="${frontPath }/app/timebargain/delayAjax/delayCommodityHoldingQuery.action" value = "查询" class = "btn" />
												</div>
											</td>
											<td align="right" width="30%">
												<div>
												</div>
											</td>
										</tr>
									</table>
							</div>
						</td>
					</tr>
					
					<tr class="font_b tr_bg">
						<td colspan="2">
							<div class="tb_bg">
								<table id="orderInfo"  width="100%" border="0" cellspacing="0" cellpadding="0"
									class="content">
									<tr class="font_b tr_bg" id = "orderFirstTr">
										<td width="20%">
											<div class="ordercol">
												交易代码
											</div>
										</td>
										<td width="20%">
											<div class="ordercol">
												商品代码
											</div>
										</td>
										<td width="20%">
											<div class="ordercol">
												买/卖
											</div>
										</td>
										<td width="20%">
											<div class="ordercol">
												持仓数量
											</div>
										</td>
										<td width="20%">
											<div class="ordercol">
												冻结数量
											</div>
										</td>
									</tr>
								</table>
							</div>
						</td>
					</tr>
				</table>
			</div>
			<!-----------  订货查询div 结束  ----------->
			
		</div>
		<!---------------------  选项卡面板 div 结束  --------------------->
		
		<!-------------------------Body End------------------------->
	</body>
</html>