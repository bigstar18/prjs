<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/broker/public/includefiles/allincludefiles.jsp"%>
<script type="text/javascript">
<c:if test='${not empty prompt}'>
	alert('<c:out value="${prompt}"/>');
</c:if>
</script>
<html>
	<head>
		<title>ǿ��ת��</title>
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
									��ܰ��ʾ ��ǿ��ת��
							</div>
						</div>
					</td>
				</tr>
				<tr>
					<td>
						<form id="frm" method="post" enctype="multipart/form-data" action="" target="HiddFrame">
						<div class="div_gn">
							<rightButton:rightButton name="ע���ǳ�" onclick="" className="anniu_btn" action="/timebargain/authorize/logoffConsigner.action?mkName=forceClose" id="logoff"></rightButton:rightButton>
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
								            <ec:column property="FIRMID" title="�����̴���" width="10%" style="text-align:center;"/>
								            <ec:column property="USEFULFUNDS" title="�����ʽ�" format="#,##0.00"  cell="number" width="13%" style="text-align:center;"/>
								            <ec:column property="LEFTOVERPRICE" title="�������" format="#,##0.00"  cell="number" width="13%" style="text-align:center;"/>
											<ec:column property="MINCLEARDEPOSIT" title="��ͽ���׼����" format="#,##0.00"  cell="number" width="20%" style="text-align:center;"/>
											<ec:column property="COMMODITYID" title="��Ʒ����" width="10%" style="text-align:center;"/>
											<ec:column property="BS_FLAG" title="��������" width="10%" style="text-align:center;">
											<c:forEach items="${authorize_BS_FlagMap}" var="result">
											<c:if test="${forceClose.BS_FLAG==result.key }">${result.value }</c:if>
											</c:forEach>
											</ec:column>
											<ec:column property="HOLDQTY" title="��������" width="10%" style="text-align:center;"/>			
											<ec:column property="EVENPRICE" title="��������" format="#,##0.00"  cell="number" width="14%" style="text-align:center;"/>
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
				˵����˫��Ҫƽ�ֵ��н���ǿƽҳ��
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