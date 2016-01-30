<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/broker/public/includefiles/allincludefiles.jsp"%>
<script type="text/javascript">
<c:if test='${not empty prompt}'>
	alert('<c:out value="${prompt}"/>');
</c:if>
</script>
<html>
	<head>
		<title>强行转让</title>
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
		
		function row_onclick(firmID,commodityID,bs_Flag,holdQty,evenPrice)
		{
			//window.showModalDialog("${basePath}/timebargain/authorize/forceCloseInfo.action?CommodityID=" + commodityID + "&BS_Flag=" + bs_Flag + "&HoldQty=" + holdQty + "&EvenPrice=" + evenPrice + "&FirmID=" + firmID, window, "dialogWidth=" + 600 + "px; dialogHeight=" + 600 + "px; status=yes;scroll=no;help=no;");
			showDialogRes("${basePath}/broker/app/timebargain/authorize/forceClose_list_qp.jsp?CommodityID=" + commodityID + "&BS_Flag=" + bs_Flag + "&HoldQty=" + holdQty + "&EvenPrice=" + evenPrice + "&FirmID=" + firmID,window,600,600);
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
									温馨提示 ：强行转让
							</div>
						</div>
					</td>
				</tr>
				<tr>
					<td>
						<form id="frm" method="post" enctype="multipart/form-data" action="" target="HiddFrame">
						<div class="div_gn">
							<rightButton:rightButton name="注销登出" onclick="" className="anniu_btn" action="/timebargain/authorize/logoffConsigner.action?mkName=forceClose" id="logoff"></rightButton:rightButton>
						</div>
						</form>
						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
								<tr>
									<td>
									<ec:table items="pageInfo.result" var="forceClose"
											action="${basePath}/timebargain/authorize/searchForceClose.action"											
											autoIncludeParameters="${empty param.autoInc}"
											xlsFileName="forceClose.xls" csvFileName="forceClose.csv"
											showPrint="true" listWidth="100%"
											minHeight="345"  style="table-layout:fixed;">
										<ec:row ondblclick="javascript:row_onclick('${forceClose.FIRMID}','${forceClose.COMMODITYID}','${forceClose.BS_FLAG}','${forceClose.HOLDQTY}','${forceClose.EVENPRICE}');" style="cursor:hand">		
								            <ec:column property="FIRMID" title="交易商代码" width="10%" style="text-align:center;"/>
								            <ec:column property="USEFULFUNDS" title="可用资金" format="#,##0.00"  cell="number" width="13%" style="text-align:center;"/>
								            <ec:column property="LEFTOVERPRICE" title="上日余额" format="#,##0.00"  cell="number" width="13%" style="text-align:center;"/>
											<ec:column property="MINCLEARDEPOSIT" title="最低结算准备金" format="#,##0.00"  cell="number" width="20%" style="text-align:center;"/>
											<ec:column property="COMMODITYID" title="商品代码" width="10%" style="text-align:center;"/>
											<ec:column property="BS_FLAG" title="订货类型" width="10%" style="text-align:center;">
											<c:forEach items="${authorize_BS_FlagMap}" var="result">
											<c:if test="${forceClose.BS_FLAG==result.key }">${result.value }</c:if>
											</c:forEach>
											</ec:column>
											<ec:column property="HOLDQTY" title="订货数量" width="10%" style="text-align:center;"/>			
											<ec:column property="EVENPRICE" title="订货均价" format="#,##0.00"  cell="number" width="14%" style="text-align:center;"/>
										</ec:row>	
									</ec:table>
										
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
				<tr>
				<td>
				<div style="color: red" id="memo">
				说明：双击要平仓的行进入强平页面
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