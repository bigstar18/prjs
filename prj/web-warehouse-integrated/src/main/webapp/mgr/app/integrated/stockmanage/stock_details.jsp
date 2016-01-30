<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<link rel="stylesheet"
		href="${skinPath }/css/validationengine/validationEngine.jquery.css"
		type="text/css" />
	<link rel="stylesheet"
		href="${skinPath }/css/validationengine/template.css" type="text/css" />
	<script src="${publicPath }/js/jquery-1.6.min.js"
		type="text/javascript"></script>
	<script
		src="${basePath }/mgr/app/bill/js/jquery.validationEngine-zh_CN.js"
		type="text/javascript" charset="GBK">
		</script>
	<script type="text/javascript">
	jQuery(document).ready(function(){
		//  չʾ�ֵ���Ӧ��Ϣ 
	//	var value = ${outStock.deliveryStatus};
		
		var value ;
			if( $("#ValueId").val()!="" || $("#ValueId").val()!=null ){
				value=$("#ValueId").val();
			}
		
		
		
		
		 /*  if(value==0){
		 		document.getElementById('idnumbertr').style.display='block';
		 		document.getElementById('addressphone').style.display='none';		 		
		 		
				document.getElementById("span").innerText = "�û�����";
			 		 
			  }
		  if(value==1){
			  	document.getElementById('idnumbertr').style.display='none';
			  	document.getElementById('addressphone').style.display='';
			  	
			    document.getElementById("span").innerText = "����";
			  		
			  }		 */
				if(value==0){
				 		$("#addressphone").attr("style","display:none");
				 		 $("#deliveryStatus").find("option[value='0']").attr("selected",true);
				 		
						 		$("#span").text("�û�����");
					 		
					  }
				  if(value==1){
				 		$("#idnumbertr").attr("style","display:none");
					  	$("#deliveryStatus").find("option[value='1']").attr("selected",true);
					
						 		$("#span").text("����");
					  		
					  }		
});
	
	</script>
	<head>
		<title>�ֵ�����</title>
		<meta http-equiv="Pragma" content="no-cache">
	</head>
	<body style="overflow-y: hidden">
		<form id="frm" method="post">
		<input type="hidden" id="ValueId" value="${outStock.deliveryStatus }" />
			<div
				style="overflow-x: hidden; position: relative;  overflow: auto; height: 400px; width: 100%;">
				<div class="div_cx">
					<table border="0" width="95%" align="center">
						<tr>
							<td>
								<div class="warning">
									<div class="content">
										��ܰ��ʾ :�ֵ�����
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td>
								<table border="0" width="100%" align="center">
									<tr>
										<td>
											<div class="div_cxtj">
												<div class="div_cxtjL"></div>
												<div class="div_cxtjC">
													�ֵ���Ϣ
												</div>
												<div class="div_cxtjR"></div>
											</div>
											<div style="clear: both;"></div>
											<div>
												<table border="0" cellspacing="0" cellpadding="4"
													width="100%" align="center" class="table2_style">
													<tr>
														<td align="right" width="20%">
															�ֵ��ţ�
														</td>
														<td width="20%">
															${entity.stockId}
														</td>
														<!-- 
														<td align="right" width="20%">
															�ֿ��ţ�
														</td>
														<td width="30%">
															${entity.warehouseId}
														</td>
														 -->
														<td align="right" width="20%">
															&nbsp;
														</td>
														<td width="30%">
															&nbsp;
														</td>
													</tr>
													<tr>
														<td align="right" width="20%">
															�ֿ�ԭʼƾ֤�ţ�
														</td>
														<td width="30%">
															<div style="width:185px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;wzy:expression(void(this.title=this.innerText))">${entity.realStockCode}</div>
														</td>
														<td align="right" width="20%">
															���������̣�
														</td>
														<td width="20%">
															${entity.ownerFirm}
														</td>
													</tr>
													<tr>
														<td align="right" width="20%">
															Ʒ����
														</td>
														<td width="20%">
															${entity.breed.breedName}
														</td>
														<td align="right" width="20%">
															��Ʒ������
														</td>
														<td width="30%"><div style="width:185px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;wzy:expression(void(this.title=this.innerText))">
															<fmt:formatNumber value="${entity.quantity}"
																pattern="#,##0.00" />��${entity.unit }��</div>
														</td>
													</tr>
													<tr>
														<td align="right" width="20%">
															����ʱ�䣺
														</td>
														<td width="20%">
															<fmt:formatDate value="${entity.createTime}"
																pattern="yyyy-MM-dd HH:mm:ss" />
														</td>
													<c:if test="${entity.lastTime !=null}">
														<td align="right" width="20%">
															�����ʱ�䣺
														</td>
														<td width="30%">
															<fmt:formatDate value="${entity.lastTime}"
																pattern="yyyy-MM-dd HH:mm:ss" />
														</td>
													</c:if>
													</tr>
													<c:if test="${entity.stockStatus==2 || entity.stockStatus==5}">
													<tr>
														<td align="right">
															�����ʽ��
														</td>
														<td>
															<span id="span"></span>
														</td>
														<%-- <td align="right">
															�����Կ��
														</td>
														<td>
															${outStock.key}
														</td> --%>
														<td align="right">
															����ˣ�
														</td>
														<td>
															${outStock.deliveryPerson}
														</td>
													</tr>
													
													<tr id="idnumbertr" >
														<td align="right" width="20%">
															��������֤��
														</td>
														<td width="20%">
															${outStock.idnumber}
														</td>
													</tr>
													<tr id="addressphone" >
														<td align="right" width="20%">
															�绰��
														</td>
														<td width="20%">
															${outStock.address}
														</td>
														<td align="right" width="20%">
															��ַ��
														</td>
														<td width="20%">
															${outStock.phone}
														</td>
													</tr>
													</c:if>
												</table>
											</div>
										</td>
									</tr>
								</table>

							</td>
						</tr>
						<tr>
							<td>
								<table border="0" width="100%" align="center">
									<c:forEach var="map" items="${tpmap}">
									<tr>
										<td>
											<div class="div_cxtj">
												<div class="div_cxtjL"></div>
												<div class="div_cxtjC">
													${map.key.name}
												</div>
												<div class="div_cxtjR"></div>
											</div>
											<div style="clear: both;"></div>
											<div>
												<table border="0" cellspacing="0" cellpadding="4"
													width="100%" align="center" class="table2_style">
													<c:if test="${not empty map.value }">
														<c:set var="propertysize"
															value="${fn:length(map.value)}"></c:set>
														<tr>
															<c:forEach var="property"
																items="${map.value }" varStatus="status">
																<c:if
																	test="${(status.count-1)%2==0 and status.count!=1}">
														</tr>
														<tr>
													</c:if>
													<td align="right" width="20%" scope="row">
														${property.propertyName}��
													</td>
													<td width="30%">
														${property.propertyValue}
													</td>
													</c:forEach>
													<c:if test="${propertysize%2!=0}">
														<c:forEach begin="1" end="${2-(propertysize%2)}">
															<th align="center" valign="middle" scope="row">
																&nbsp;
															</th>
															<td align="center" valign="middle">
																&nbsp;
															</td>
														</c:forEach>
													</c:if>
													</tr>
													</c:if>
												</table>
											</div>
										</td>
									</tr>
									</c:forEach>
								</table>
							</td>
						</tr>

						<tr>
							<td align="right">
								<button class="btn_sec" onClick=window.close();>
									�ر�
								</button>
								&nbsp;&nbsp;&nbsp;&nbsp;
							</td>
						</tr>
					</table>
				</div>
			</div>
		</form>

	</body>
</html>
<%@ include file="/mgr/public/jsp/footinc.jsp"%>