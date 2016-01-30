<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<script type="text/javascript">
<c:if test='${not empty prompt}'>
	alert('<c:out value="${prompt}"/>');
</c:if>
</script>
<html>
	<head>
		<title>ǿ��ת�ù��ڳֲ�</title>
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


		  // ��ϸ��Ϣ��ת
		  function viewById(firmID,customerID,commodityID,BSFlag){
				//��ȡ����Ȩ�޵� URL
				var viewUrl = "/timebargain/authorize/viewDetailForceClose.action";
				//��ȡ������תURL
				var url = "${basePath}"+viewUrl;
				//�� URL ��Ӳ���
				url += "?entity.firmID="+ firmID + "&entity.customerID=" + customerID + "&entity.commodityID=" + commodityID + "&entity.BSFlag=" + BSFlag;
				// �����޸�ҳ��
				if(showDialog(url, "", 800, 550)){
					// ����޸ĳɹ�����ˢ���б�
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
									��ܰ��ʾ ��ǿ��ת�ù��ڳֲ�
							</div>
						</div>
					</td>
				</tr>
				<tr>
					<td>
						<form id="frm" method="post" enctype="multipart/form-data" action="" target="HiddFrame">
						<div class="div_gn">
							<rightButton:rightButton name="ע���ǳ�" onclick="" className="anniu_btn" action="/timebargain/authorize/logoffConsigner.action?mkName=detailForceClose" id="logoff"></rightButton:rightButton>
						</div>
						</form>
						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
								<tr>
									<td>
									<ec:table items="pageInfo.result" var="hold"
											action="${basePath}/timebargain/authorize/searchDetailForceClose.action"											
											autoIncludeParameters="${empty param.autoInc}"
											xlsFileName="ǿ��ת�ù��ڳֲ�.xls" csvFileName="ǿ��ת�ù��ڳֲ�.csv"
											showPrint="true" listWidth="100%"
											minHeight="345"  style="table-layout:fixed;">
										<ec:row>		
										    <ec:column width="5%" property="_0" title="���" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" style="text-align:center;"/>
								            <ec:column property="FIRMID" title="�����̴���" width="10%" ellipsis="true" style="text-align:center;"/>
								            <ec:column property="COMMODITYID" title="��Ʒ����" width="10%" style="text-align:center;"/> 
											<ec:column property="BS_FLAG" title="����" width="5%" style="text-align:center;">
											  <c:if test="${hold.BS_FLAG eq '1'}">��</c:if>
											  <c:if test="${hold.BS_FLAG eq '2'}">��</c:if>
											</ec:column>
											<ec:column property="OPENQTY" title="��������" width="10%" style="text-align:right;"/>		
											<ec:column property="HOLDQTY" title="�ֲ�����" width="10%" style="text-align:right;"/>	
											<ec:column property="MAYCLOSEQTY" title="��ת������" width="10%" style="text-align:right;" sortable="false" >
											  <c:choose>
					                            <c:when test="${hold.HOLDQTY <= hold.MAYCLOSEQTY}">${hold.HOLDQTY }</c:when>
					                            <c:otherwise>${hold.MAYCLOSEQTY }</c:otherwise> 
				                              </c:choose>  
											</ec:column>		
											<ec:column property="proc" title="����" width="10%" style="text-align:center;" sortable="false" filterable="false" >
											  <rightHyperlink:rightHyperlink text="ǿƽ" href="#" onclick="viewById('${hold.FIRMID}','${hold.CUSTOMERID}','${hold.COMMODITYID}','${hold.BS_FLAG}')" action="/timebargain/authorize/viewDetailForceClose.action"/>
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