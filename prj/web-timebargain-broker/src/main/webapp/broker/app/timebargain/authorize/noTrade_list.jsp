<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/broker/public/includefiles/allincludefiles.jsp"%>
<script type="text/javascript">
<c:if test='${not empty prompt}'>
	alert('<c:out value="${prompt}"/>');
</c:if>
</script>
<html>
	<head>
		<title>ί���б�</title>
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
				var vaild = affirm("��ȷ��Ҫע����");
				if(vaild){
					//�����ϢURL
					var logoffUrl = $("#logoff").attr("action");
					//ȫ URL ·��
					var url = "${basePath}"+logoffUrl;
					$("#frm").attr("action",url);
					frm.submit();
			}})	
		});
		//����ɾ����Ϣ
		function deleteOrderList(){
			//��ȡ����Ȩ�޵� URL
			var deleteUrl = document.getElementById('withdraw').action;
			//��ȡ������תURL
			var url = "${basePath}"+deleteUrl+"?autoInc=false";
			//ִ��ɾ������
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
									��ܰ��ʾ ����Ϊ����
							</div>
						</div>
					</td>
				</tr>
				<tr>
					<td>
						<form id="frm" method="post" enctype="multipart/form-data" action="" target="ListFrame">
						<div class="div_gn">
							<rightButton:rightButton name="����" onclick="deleteOrderList();" className="anniu_btn" action="/timebargain/authorize/withdrawOrder.action" id="withdraw"></rightButton:rightButton>
							&nbsp;&nbsp;
							<rightButton:rightButton name="ע���ǳ�" onclick="" className="anniu_btn" action="/timebargain/authorize/logoffConsigner.action?mkName=noTrade" id="logoff"></rightButton:rightButton>
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
								            <ec:column property="CUSTOMERID" title="�ͻ�����" width="80" style="text-align:center;" ellipsis="true"/>
								            <ec:column property="A_ORDERNO" title="ί�е���" width="110" style="text-align:center;"/>
								            <ec:column property="COMMODITYID" title="��Ʒ����" width="100" style="text-align:center;"/>
											<ec:column property="BS_FLAG" title="����" width="55" style="text-align:center;">
											<c:forEach items="${authorize_BS_FlagMap}" var="result">
											<c:if test="${noTrade.BS_FLAG==result.key }">${result.value }</c:if>
											</c:forEach>
											</ec:column>
											<ec:column property="PRICE" title="ί�м۸�" width="90" format="#,##0.00"  cell="number" style="text-align:center;" />
											<ec:column property="QUANTITY" title="ί������" width="80" style="text-align:center;"/>
											<ec:column property="TRADEQTY" title="�ѳɽ�����" width="80" style="text-align:center;"/>			
											<ec:column property="STATUS" title="״̬" width="80" style="text-align:center;">
											<c:forEach items="${authorize_Order_StatusMap}" var="result">
											<c:if test="${noTrade.STATUS==result.key }">${result.value }</c:if>
											</c:forEach>
											</ec:column>
											<ec:column property="ORDERTIME" title="ί��ʱ��"  width="150" cell="date" style="text-align:center;"/>
											<ec:column property="CLOSEFLAG" title="ת�ñ�־" width="80" style="text-align:center;">
											<c:forEach items="${authorize_CloseFlagMap}" var="result">
											<c:if test="${noTrade.CLOSEFLAG==result.key }">${result.value }</c:if>
											</c:forEach>
											</ec:column>
											<ec:column property="TRADERID" title="����Ա����" width="80" style="text-align:center;" ellipsis="true"/>
											<ec:column property="CONSIGNERID" title="��Ϊί��Ա" width="80" style="text-align:center;"/>
										</ec:row>	
									</ec:table>
										
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
			</table>
		<!-- �༭�͹�����ʹ�õ� ͨ�õ��ı���ģ�� -->
		<textarea id="ecs_t_input" rows="" cols="" style="display: none">
			<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;" name="" />
		</textarea>
		</div>
	</body>
</html>