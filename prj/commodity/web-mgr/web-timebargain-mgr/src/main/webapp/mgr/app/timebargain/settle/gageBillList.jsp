<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<link rel="stylesheet" href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationengine/template.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath}/css/autocomplete/jquery.autocomplete.css" type="text/css" />
		<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
		<script type="text/javascript" src="${publicPath}/js/autocomplete/jquery.autocomplete.js"></script>
		<script type="text/javascript" src="${publicPath}/js/firmjson.js"></script>
		<script src="${basePath}/mgr/app/timebargain/js/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
		<script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>
	
		<style type="text/css">
		a:link {text-decoration:none;} 
		a:active:{text-decoration:none;} 
		a:visited {text-decoration:none;}
		a:hover { text-decoration:none;}
		
		</style>
		<script type="text/javascript">
			$(function(){
				$("#frm").validationEngine({
					promptPosition:'bottomRight'
				}); 
				//�����Ϣ��ת
				$("#add").click(function(){
					var addUrl = $("#add").attr('action');
					var url = "${basePath}"+addUrl;
					if(showDialog(url, "", 800, 550)){
						//�����ӳɹ�����ˢ���б�
						ECSideUtil.reload("ec");
					}
				});

				//$("a").click(function(){
					//viewById('<c:out value="${bill['ID']}"/>', '<c:out value="${bill['COMMODITYID']}"/>')
					//var gageBillID = $(this).attr("billId");
					//var commodityID = $(this).attr("commodityId");
					//var viewUrl = "/timebargain/bill/gageBillDetail.action";
					//var url = "${basePath}"+viewUrl + "?gageBillID="+gageBillID + "&commodityID="+commodityID;
					// �����޸�ҳ��
					//showDialog(url, "", 800, 550);
				//});
			});
			
			// ��ϸ��Ϣ��ת
			  function viewById(gageBillID, commodityID){
					//��ȡ����Ȩ�޵� URL
					var viewUrl = "/timebargain/bill/gageBillDetail.action";
					//��ȡ������תURL
					var url = "${basePath}"+viewUrl;
					//�� URL ��Ӳ���
					url += "?gageBillID="+gageBillID + "&commodityID=" + commodityID;
					// �����޸�ҳ��
					// �����޸�ҳ��
					showDialog(url, "", 800, 550);
			  }
		</script>
		<script type="text/javascript">
		</script>
		</head>
		<body>
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
					    <div class="div_cx">
							<form id = "frm" name="frm" action="${basePath}/timebargain/bill/gageBillList.action" method="post">
								<table border="0" cellpadding="0" cellspacing="0" class="table2_style">
									<tr>
										<td class="table5_td_width">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0" cellpadding="0">
													<tr>
														<td class="table3_td_1" align="right">
															�����̴���:&nbsp;
															<input id="firmID" name="${GNNT_}gageBill.firmId" type="text" value="${oldParams['gageBill.firmId'] }" class="validate[custom[noSpecialChar]] input_text"/>
														</td>
														<td align="center">��Ʒ���룺&nbsp;
															<input id="commodityID" name="${GNNT_}gageBill.commodityID" type="text" value="${oldParams['gageBill.commodityID'] }" class="validate[custom[noSpecialChar]] input_text"/>
														</td>
														
														<td class="table3_td_1" align="left">
															<input id="view" class="btn_sec" type="submit" value = "��ѯ"/>
															&nbsp;&nbsp;
															<button class="btn_cz" onclick="myReset();">����</button>
														</td>    
												    </tr>
												</table>
											</div>
										</td>
									</tr>
								</table>
							</form>
						</div>
						<br />
					    <div class="div_gn">
							<rightButton:rightButton name="���" onclick="" className="anniu_btn" action="/timebargain/bill/getCommodity.action" id="add"></rightButton:rightButton>
						</div>
						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
								<tr>
									<td>
										<ec:table items="pageInfo.result" var="bill"
											action="${basePath}/timebargain/bill/gageBillList.action"											
											autoIncludeParameters="${empty param.autoInc}"
											xlsFileName="�����б�.xls" csvFileName="�����б�.csv"
											showPrint="true" listWidth="100%"
											minHeight="345"  style="table-layout:fixed;">
											<ec:row>
									            <ec:column property="FIRMID" title="�����̴���" width="10%" ellipsis="true" style="text-align:center;"/>
									            
									            <ec:column property="COMMODITYID" title="��Ʒ����" width="10%" style="text-align:center;"/>
									            <ec:column property="QUANTITY" title="�ֵ�����" width="10%" style="text-align:center;"/>
									            <ec:column property="CREATETIME" title="����ʱ��" width="15%" style="text-align:center;"> 
									            	<fmt:formatDate pattern="yyyy-MM-dd HH:mm:SS" value="${bill['CREATETIME']}"/> 		
									            </ec:column>
									            <ec:column property="MODIFYTIME" title="����޸�ʱ��" width="15%" style="text-align:center;">  		
									            	<fmt:formatDate pattern="yyyy-MM-dd HH:mm:SS" value="${bill['MODIFYTIME']}"/> 		
									            </ec:column>
									            <ec:column property="REMARK" title="��ע" width="20%" style="text-align:center;"/>  
									            <ec:column property="_1" title="��ϸ��Ϣ" width="10%" style="text-align:center;">
									            	<%-- <a name = "detail" href="#" billId = "${bill['ID']}" commodityId = "${bill['COMMODITYID']}">
									            		<img title="����鿴��ϸ��Ϣ" src="<c:url value="${skinPath}/image/app/timebargain/commodity.gif"/>"/>
									            	</a>--%>
									            	<rightHyperlink:rightHyperlink text="<img title='����鿴��ϸ��Ϣ' src='${skinPath }/image/app/timebargain/commodity.gif'/>" onclick="viewById('${bill['ID']}','${bill['COMMODITYID']}')" action="/timebargain/bill/gageBillDetail.action"/>
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
				
			<!-- �༭�͹�����ʹ�õ� ͨ�õ��ı���ģ�� -->
			<textarea id="ecs_t_input" rows="" cols="" style="display:none">
				<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" 
				 style="width:100%;" name="" />
			</textarea>	
		</body>
</html>
