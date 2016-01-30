<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<script type="text/javascript">
<c:if test='${not empty prompt}'>
	alert('<c:out value="${prompt}"/>');
</c:if>
</script>
<html>
	<head>
		<title>强行转让过期持仓</title>
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


		  // 详细信息跳转
		  function viewById(firmID,customerID,commodityID,BSFlag){
				//获取配置权限的 URL
				var viewUrl = "/timebargain/authorize/viewDetailForceClose.action";
				//获取完整跳转URL
				var url = "${basePath}"+viewUrl;
				//给 URL 添加参数
				url += "?entity.firmID="+ firmID + "&entity.customerID=" + customerID + "&entity.commodityID=" + commodityID + "&entity.BSFlag=" + BSFlag;
				// 弹出修改页面
				if(showDialog(url, "", 800, 550)){
					// 如果修改成功，则刷新列表
					ECSideUtil.reload("ec");
				};
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
									温馨提示 ：强行转让过期持仓
							</div>
						</div>
					</td>
				</tr>
				<tr>
					<td>
						<form id="frm" method="post" enctype="multipart/form-data" action="" target="HiddFrame">
						<div class="div_gn">
							<rightButton:rightButton name="注销登出" onclick="" className="anniu_btn" action="/timebargain/authorize/logoffConsigner.action?mkName=detailForceClose" id="logoff"></rightButton:rightButton>
						</div>
						</form>
						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
								<tr>
									<td>
									<ec:table items="pageInfo.result" var="hold"
											action="${basePath}/timebargain/authorize/searchDetailForceClose.action"											
											autoIncludeParameters="${empty param.autoInc}"
											xlsFileName="强行转让过期持仓.xls" csvFileName="强行转让过期持仓.csv"
											showPrint="true" listWidth="100%"
											minHeight="345"  style="table-layout:fixed;">
										<ec:row>		
										    <ec:column width="5%" property="_0" title="序号" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" style="text-align:center;"/>
								            <ec:column property="FIRMID" title="交易商代码" width="10%" ellipsis="true" style="text-align:center;"/>
								            <ec:column property="COMMODITYID" title="商品代码" width="10%" style="text-align:center;"/> 
											<ec:column property="BS_FLAG" title="买卖" width="5%" style="text-align:center;">
											  <c:if test="${hold.BS_FLAG eq '1'}">买</c:if>
											  <c:if test="${hold.BS_FLAG eq '2'}">卖</c:if>
											</ec:column>
											<ec:column property="OPENQTY" title="订立数量" width="10%" style="text-align:right;"/>		
											<ec:column property="HOLDQTY" title="持仓数量" width="10%" style="text-align:right;"/>	
											<ec:column property="MAYCLOSEQTY" title="可转让数量" width="10%" style="text-align:right;" sortable="false" >
											  <c:choose>
					                            <c:when test="${hold.HOLDQTY <= hold.MAYCLOSEQTY}">${hold.HOLDQTY }</c:when>
					                            <c:otherwise>${hold.MAYCLOSEQTY }</c:otherwise> 
				                              </c:choose>  
											</ec:column>		
											<ec:column property="proc" title="操作" width="10%" style="text-align:center;" sortable="false" filterable="false" >
											  <rightHyperlink:rightHyperlink text="强平" href="#" onclick="viewById('${hold.FIRMID}','${hold.CUSTOMERID}','${hold.COMMODITYID}','${hold.BS_FLAG}')" action="/timebargain/authorize/viewDetailForceClose.action"/>
											</ec:column>
										</ec:row>	
									</ec:table>
										
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
				
			</table>
			
		</div>
	</body>
</html>