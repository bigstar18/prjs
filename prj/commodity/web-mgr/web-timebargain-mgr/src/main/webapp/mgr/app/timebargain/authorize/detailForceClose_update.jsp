<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>ǿ��ת����Ϣ</title>
		<link rel="stylesheet" href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationengine/template.css" type="text/css" />
		<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
		<script src="${basePath}/mgr/app/timebargain/js/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
		<script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>
		
		<script src="${basePath}/mgr/app/timebargain/js/tool.js" type="text/javascript" charset="GBK"></script>
		
		<script type="text/javascript">
		  jQuery(document).ready(function() {
			jQuery("#frm").validationEngine('attach');
			//��Ӱ�ťע�����¼�
			$("#add").click(function(){
				//��֤��Ϣ
				if(jQuery("#frm").validationEngine('validate')){

				  if(save_onclick()){
					  var vaild = affirm("��ȷ��Ҫ������");
						if(vaild){
							//�����ϢURL
							var addUrl = $(this).attr("action");
							//ȫ URL ·��
							var url = "${basePath}"+addUrl;
							$("#frm").attr("action",url);
							$("#frm").submit();
						}
				  }
	
				}
			});
		  });
      </script>
      
      <script type="text/javascript">

		    function priceTypeSelect(obj){
				if(!obj.checked) {
				
					frm.price.value = ${price };
					setReadWrite(frm.price);
				}else {
					
					frm.price.value = "0";
					setReadOnly(frm.price);
				}
			}

		    // �رմ���
		    function close_close(){
		    	window.returnValue = true;
				window.close();
			}

			function save_onclick(){
				var quantity = document.getElementById("quantity").value;
				if(parseInt(quantity) <=0 )
				  {
				    alert("ƽ�������������0");
				    document.getElementById("quantity").focus();
				    return false;
				}
				
				if(frm.type.value == "0"){
					alert("��¼��Ϊ��Ϊί��Ա�����ܽ���ǿ��ת�ò�����");
			    	return false;
				} 

				return true;
			}

			
		</script>
	</head>
	<body>
		<form id="frm" method="post" enctype="multipart/form-data" action="" targetType="hidden">
			
			<div class="div_cx">
				<table border="0" width="100%" align="center">
					<tr>
						<td>
							<div class="warning">
								<div class="content">
									��ܰ��ʾ :ǿ��ת����Ϣ
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
												��Ӧ������Ϣ
											</div>
											<div class="div_cxtjR"></div>
										</div>
										<div style="clear: both;"></div>
										<div>
											<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center" class="table2_style">
												<tr>
													<td align="right">
														�����̴��룺
													</td>
													<td>
														${entity.firmID}&nbsp;
														<input type="hidden" id="firmID" name="entity.firmId" value="${entity.firmID}"/>
														<input type="hidden" id="customerID" name="entity.customerId" value="${entity.customerID}"/>
													</td>
													<td align="right">
														��Ʒ���룺
													</td>
													<td>
														${entity.commodityID}&nbsp;
														<input type="hidden" id="commodityID" name="entity.commodityId" value="${entity.commodityID}"/>
													</td>
												</tr>
												<tr>
													<td align="right">
														����������
													</td>
													<td>
														${entity.openQty}&nbsp;
													</td>
													<td align="right">
														��ת��������
													</td>
													<td>
														${closeQty}&nbsp;
													</td>
												</tr>
												<tr>
													<td align="right" >
														������
													</td>
													<td>
														<c:choose>
	            		                                  <c:when test="${entity.BSFlag eq '1'}">
	            			                                                                                       ���
	            		                                  </c:when>
	            		                                  <c:when test="${entity.BSFlag eq '2'}">
	            			                                                                                       ����
	            		                                  </c:when>
	            	                                    </c:choose>
	            	                                    <input type="hidden" id="BS_Flag" name="entity.BS_Flag" value="${entity.BSFlag }"/>  
	            	                                      &nbsp;
													</td>
													<td align="right" >
														&nbsp;
													</td>
													<td>
														&nbsp;
													</td>
													
												</tr>
												
											</table>
										</div>
									</td>
								</tr>
								
								<tr>
									<td>
										<div class="div_cxtj">
											<div class="div_cxtjL"></div>
											<div class="div_cxtjC">
												ƽ����Ϣ
											</div>
											<div class="div_cxtjR"></div>
										</div>
										<div style="clear: both;"></div>
										<div>
											<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center" class="table2_style">
												<tr>
													<td align="right">
													   <span class="required">*</span>
														ǿ��ת�ü۸�ʽ��
													</td>
													<td>
														���м�:<input type="checkbox" id="priceType" name="priceType" value="1" onselect="" onclick="priceTypeSelect(this);">
														&nbsp;
													</td>
													
												</tr>
												<tr>
												  <td align="right">
												     <span class="required">*</span>
														ί�м۸�
												  </td>
												  <td>
														<input type="text" id="price" name="entity.price" onkeypress="return onlyDoubleInput()" value="${price }"
															class="validate[required,maxSize[10],custom[onlyDoubleSp]] input_text "/>
														&nbsp;
												  </td>
												  <td>
														<div class="onfocus">����Ϊ�գ�</div>
												  </td>
												</tr>
												<tr>
													<td align="right">
													   <span class="required">*</span>
														ƽ��������
													</td>
													<td>
														<input type="text" id="quantity" name="entity.quantity" onkeypress="onlyNumberInput()" value="${closeQty }"
								                               class="validate[required,maxSize[10],custom[onlyNumberSp]] input_text"/>
														&nbsp;
													</td>
													<td>
														<div class="onfocus">����Ϊ�գ�</div>
												   </td>
												</tr>
											
												<input type="hidden" id="rel" name="rel" />
											</table>
											<font color="red">ǿת�ο��۸�<span id="">��ǿתĬ�ϼ۸�Ϊ��ͣ��۸���ǿתĬ�ϼ۸�Ϊ��ͣ��۸�</span></font>
											
										</div>
									</td>
								</tr>
								<input type="hidden" id="type" name="type" value="${type }"/>
								<input type="hidden" id="orderType" name="entity.orderType" value="2"/>
								<input type="hidden" id="closeFlag" name="entity.closeFlag" value="2"/>
								<input type="hidden" id="closeMode" name="entity.closeMode" value="1"/>
							</table>
						</td>
					</tr>
				</table>
			</div>
			<div class="tab_pad">
				<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center">
					<tr>
						<td align="center">
							<rightButton:rightButton name="ǿ��ת��" onclick="" className="btn_sec" action="/timebargain/authorize/detailForceClose.action" id="add"></rightButton:rightButton>
							&nbsp;&nbsp;
							<button class="btn_sec" onClick="close_close()">�ر�</button>
						</td>
					</tr>
				</table>
			</div>
		</form>
	</body>
</html>
<%@ include file="/mgr/public/jsp/footinc.jsp"%>