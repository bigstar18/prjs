<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/broker/public/includefiles/allincludefiles.jsp"%>
<script type="text/javascript">
<c:if test='${not empty prompt}'>
	alert('<c:out value="${prompt}"/>');
</c:if>
</script>
<html>
	<head>
		<title>委托列表</title>
		<link rel="stylesheet" href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationengine/template.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath}/css/autocomplete/jquery.autocomplete.css" type="text/css" />
		<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
		<script src="${publicPath }/js/validationengine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
		<script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>
		<script type="text/javascript" src="${publicPath}/js/autocomplete/jquery.autocomplete.js"></script>
		<script type="text/javascript" src="${publicPath}/js/firmjson.js"></script>
		<SCRIPT type="text/javascript">
		jQuery(document).ready(function() {
			$("#logoff").click(function(){
				var vaild = affirm("您确定要注销吗？");
				if(vaild){
					//添加信息URL
					var logoffUrl = $("#logoff").attr("action");
					//全 URL 路径
					var url = "${basePath}"+logoffUrl;
					$("#frm").attr("action",url);
					frm.submit();
			}})	
		});
		//批量删除信息
		function deleteOrderList(){
			//获取配置权限的 URL
			var deleteUrl = document.getElementById('withdraw').action;
			//获取完整跳转URL
			var url = "${basePath}"+deleteUrl+"?autoInc=false";
			//执行删除操作
			updateRMIEcside(ec.itemlist,url);
		}
		</SCRIPT>
	</head>
	<body>
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<div class="warning">
							<div class="content">
									温馨提示 ：代为撤单
							</div>
						</div>
					</td>
				</tr>
				<tr>
					<td>
						<form id="frm" method="post" enctype="multipart/form-data" action="" target="ListFrame">
						<div class="div_gn">
							<rightButton:rightButton name="撤单" onclick="deleteOrderList();" className="anniu_btn" action="/timebargain/authorize/withdrawOrder.action" id="withdraw"></rightButton:rightButton>
							&nbsp;&nbsp;
							<rightButton:rightButton name="注销登出" onclick="" className="anniu_btn" action="/timebargain/authorize/logoffConsigner.action?mkName=noTrade" id="logoff"></rightButton:rightButton>
						</div>
						</form>
						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
								<tr>
									<td>
									<ec:table items="pageInfo.result" var="noTrade"
											action="${basePath}/timebargain/authorize/noTradeList.action"											
											autoIncludeParameters="${empty param.autoInc}"
											xlsFileName="noTradeList.xls" csvFileName="noTradeList.csv"
											showPrint="true" listWidth="100%"
											minHeight="345"  style="table-layout:fixed;">
										<ec:row>
											<ec:column cell="checkbox" headerCell="checkbox" alias="itemlist" style="text-align:center; " value="${noTrade.A_ORDERNO};${noTrade.COMMODITYID};${noTrade.CUSTOMERID }" width="30" viewsAllowed="html" />		
								            <ec:column property="CUSTOMERID" title="客户代码" width="80" style="text-align:center;" ellipsis="true"/>
								            <ec:column property="A_ORDERNO" title="委托单号" width="110" style="text-align:center;"/>
								            <ec:column property="COMMODITYID" title="商品代码" width="100" style="text-align:center;"/>
											<ec:column property="BS_FLAG" title="买卖" width="55" style="text-align:center;">
											<c:forEach items="${authorize_BS_FlagMap}" var="result">
											<c:if test="${noTrade.BS_FLAG==result.key }">${result.value }</c:if>
											</c:forEach>
											</ec:column>
											<ec:column property="PRICE" title="委托价格" width="90" format="#,##0.00"  cell="number" style="text-align:center;" />
											<ec:column property="QUANTITY" title="委托数量" width="80" style="text-align:center;"/>
											<ec:column property="TRADEQTY" title="已成交数量" width="80" style="text-align:center;"/>			
											<ec:column property="STATUS" title="状态" width="80" style="text-align:center;">
											<c:forEach items="${authorize_Order_StatusMap}" var="result">
											<c:if test="${noTrade.STATUS==result.key }">${result.value }</c:if>
											</c:forEach>
											</ec:column>
											<ec:column property="ORDERTIME" title="委托时间"  width="150" cell="date" style="text-align:center;"/>
											<ec:column property="CLOSEFLAG" title="转让标志" width="80" style="text-align:center;">
											<c:forEach items="${authorize_CloseFlagMap}" var="result">
											<c:if test="${noTrade.CLOSEFLAG==result.key }">${result.value }</c:if>
											</c:forEach>
											</ec:column>
											<ec:column property="TRADERID" title="交易员代码" width="80" style="text-align:center;" ellipsis="true"/>
											<ec:column property="CONSIGNERID" title="代为委托员" width="80" style="text-align:center;"/>
										</ec:row>	
									</ec:table>
										
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
			</table>
		<!-- 编辑和过滤所使用的 通用的文本框模板 -->
		<textarea id="ecs_t_input" rows="" cols="" style="display: none">
			<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;" name="" />
		</textarea>
		</div>
	</body>
</html>