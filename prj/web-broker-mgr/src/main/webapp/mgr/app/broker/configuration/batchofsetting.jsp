<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>����Ӷ����������</title>
		<link rel="stylesheet"
			href="${skinPath }/css/validationengine/validationEngine.jquery.css"
			type="text/css" />
		<link rel="stylesheet"
			href="${skinPath }/css/validationengine/template.css" type="text/css" />
		<script type="text/javascript"
			src="<c:url value="/mgr/public/js/global.js"/>">
</script>
		<script src="${publicPath }/js/jquery-1.6.min.js"
			type="text/javascript">
</script>
		<script
			src="${publicPath }/js/validationengine/languages/jquery.validationEngine-zh_CN.js"
			type="text/javascript" charset="GBK">
</script>
		<script
			src="${publicPath }/js/validationengine/jquery.validationEngine.js"
			type="text/javascript" charset="GBK">
</script>

		<SCRIPT type="text/javascript">
		<!-- 
		    $(document).ready(function() {
			   
		    	jQuery("#frm").validationEngine('attach');
				//�޸İ�ťע�����¼�
				$("#add").click(function(){
					//��֤��Ϣ
					var moduleId = document.getElementById("moduleId").value;
					var rewardType = document.getElementById("rewardType").value;
					var commodityId = document.getElementById("commodityId").value;
					if (frm.lbRight.length == 0) {
					alert("���Ӷ�����õļ����̲���Ϊ�գ�");
					return false;
					}else{
					var brokerids = "";
					for(var t=0;t<frm.lbRight.length;t++){						
						brokerids+=frm.lbRight.options[t].value+",";
					}
					if(brokerids != "")
				    {
				      brokerids =  brokerids.substr(0, brokerids.length - 1) ;
				    }
					frm.brokerids.value=brokerids;
					
					}
	
					if(jQuery("#frm").validationEngine('validateform')){
				
						var vaild = affirm("��ȷ��Ҫ������");
						if(vaild){
							//�����ϢURL
							var updateDemoUrl = $(this).attr("action");
							//ȫ URL ·��
							var url = "${basePath}"+updateDemoUrl;
							$("#frm").attr("action",url);
							$("#frm").submit();
							
						}
					}
				});
				$("#update").click(function(){
					//��֤��Ϣ
					if(jQuery("#frm").validationEngine('validateform')){
						var vaild = affirm("��ȷ��Ҫ������");
						if(vaild){
							//�����ϢURL
							var updateDemoUrl = $(this).attr("action");
							//ȫ URL ·��
							var url = "${basePath}"+updateDemoUrl;
							$("#frm").attr("action",url);
							$("#frm").submit();
							$(this).attr("disabled",true);
						}
					}
				});
				$("#back").click(function(){
					//�����ϢURL
					var updateDemoUrl = $(this).attr("action");
					//ȫ URL ·��
					var url = "${basePath}"+updateDemoUrl;
					document.location.href = url;
				});
				
				$("#moduleId").change(function(){
					if ("${crud}" != "update") {
						var moduleId = $(this).val();
						$("#commodityId option:gt(0)").remove(); 
						if(moduleId != null && moduleId != ""){
							$.ajax({
								type: "post",
								url: "../../ajaxcheck/broker/getCMDByModuleID.action",
								data: {
									moduleId: moduleId
									  },
								success : function(data){
											var cmd = data[0];
											if(cmd != null && cmd.length>0){
												for(var i=0;i<cmd.length;i++){
													var cmdid = cmd[i].COMMODITYID;
													var name = cmd[i].NAME;
													$("#commodityId").append("<option value='"+cmdid+"'>"+name+"</option>"); 
												}
											}
								          }
							});
						}
					}
				});
				
				
			});
		
		
				
		function vali_FirstPayRate(){
		var firstPayRate = parseFloat($("#firstPayRate").val());
				
				if(firstPayRate > 100){
					alert("������ɱ������ܴ���100%");
					$("#secondPayRate").attr("value",0);
					$("#firstPayRate").attr("value",0);
				}
				else if(firstPayRate != 0){
				$("#secondPayRate").attr("value",100-firstPayRate);
				}
		}
	
		function vali_SecondPayRate(){
		var firstPayRate = parseFloat($("#firstPayRate").val());
		var secondPayRate = parseFloat($("#secondPayRate").val());
				if(firstPayRate + secondPayRate > 100){
					alert("������ɱ������ܴ���100%");
					$("#secondPayRate").attr("value",0);
					$("#firstPayRate").attr("value",0);		
				}else if(firstPayRate + secondPayRate < 100 && firstPayRate != 0 ){
					alert("������ɱ�������С��100%");
					$("#secondPayRate").attr("value",0);
					$("#firstPayRate").attr("value",0);	
				}else if(firstPayRate == 0){
					$("#firstPayRate").attr("value",100-secondPayRate);	
				}
		}	





		</SCRIPT>
	</head>
	<body>
		<form id="frm" method="post" enctype="multipart/form-data" action=""
			targetType="hidden">
			<div class="div_cx">
				<table border="0" width="800" align="center">
					<tr>
						<td>
							<div class="warning">
								<div class="content">
									��ܰ��ʾ :�����޸�����Ӷ�����(�������ã���Ḳ�ǵ������ò���)
								</div>
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<table border="0" width="800" align="center">

								<tr>
									<td>
										<div class="div_cxtj">
											<div class="div_cxtjL"></div>
											<div class="div_cxtjC">
												����Ӷ���������
											</div>
											<div class="div_cxtjR"></div>
										</div>
										<div style="clear: both;"></div>
										<div>
											<table border="0" cellspacing="0" cellpadding="4"
												width="100%" align="center" class="table2_style">
												<tr align="center" valign="top">
													<td align="center" class="common" width="30%">
														<table border="0" class="common" width="200">
															<tr>
																<td align="center">
																	δ��
																</td>
															</tr>
															<tr>
																<td>
																	<select name="lbLeft" size="16"
																		onDblClick="moveSelected(lbLeft,lbRight);"
																		style="width: 100%" multiple>
																		<%
																			List list = (List) request.getAttribute("brokerList");
																			if (list != null && list.size() > 0) {
																				for (int i = 0; i < list.size(); i++) {
																					Map map = (Map) list.get(i);
																					String BROKERID = map.get("BROKERID") + "";
																		%>
																		<option value="<%=BROKERID%>"><%=BROKERID%></option>
																		<%
																			}
																			}
																		%>
																	</select>
																</td>
															</tr>
														</table>
													</td>
													<td align="center" valign="center" width="15%">

														<table border="0" class="common" height="140" width="100">
															<tr>
																<td align="center" valign="bottom">
																	<input type="button" name="lbAdd" value="  >   "
																		onclick="moveSelected(lbLeft,lbRight);" class="button">
																</td>
															</tr>
															<tr>
																<td align="center" valign="top">
																	<input type="button" name="lbAddAll" value="  >>  "
																		onclick="return moveSelectedAll(lbLeft,lbRight);"
																		class="button">
																</td>
															</tr>
															<tr>
																<td align="center" valign="bottom">
																	<input type="button" name="lbDel" value="  <   "
																		onclick="moveSelected(lbRight,lbLeft);" class="button">
																</td>
															</tr>
															<tr>
																<td align="center" valign="top">
																	<input type="button" name="lbDelAll" value="  <<  "
																		onclick="moveSelectedAll(lbRight,lbLeft);"
																		class="button">
																</td>
															</tr>
														</table>
													</td>
													<td align="center" class="common" width="30%">
														<table border="0" class="common" width="200">
															<tr>
																<td class="common" align="center">
																	����
																</td>
															</tr>
															<tr>
																<td>
																	<select name="lbRight" size="16"
																		onDblClick="moveSelected(lbRight,lbLeft);"
																		style="width: 100%" multiple
																		onchange="checkBrokerid();">

																	</select>
																</td>
															</tr>
														</table>
													</td>
												</tr>
												<tr>
													<td align="right">
														ģ�飺
													</td>
													<td>
														<select id="moduleId" name="moduleId"
															class="validate[required]" style="width: 120">
															<%--
															<option value=""></option>
															<option value="15"
																<c:if test="${entity.moduleId == 15}">selected</c:if>>
																${request.shortName }
															</option>
															 --%>
															 
															  <option value=""></option>
																 <c:forEach items="${brTradeModule }" var="d">
																 
																		<option value="${d.moduleId }" >${d.cnName }</option>
																</c:forEach>			
														</select>
														<span class="required">*</span>
													</td>
												</tr>
												
												<input type="hidden" id="rewardType" name="entity.rewardType" value="0" />
																				
												<tr>
													<td align="right">
														��Ʒ��
													</td>
													<td>
														<select id="commodityId" name="commodityId" class="validate[required]" style="width:120">
									                		<option value=""></option>
									                		<c:forEach items="${list}" var="list">
									                			<option value="${list.COMMODITYID }" <c:if test="${entity.commodityId == list.COMMODITYID}">selected</c:if>>${list.NAME }</option>
									                		</c:forEach>
														</select>
														<span class="required">*</span>
													</td>
												</tr>
												<tr>
													<td align="right">
														������Ӷ�������
													</td>
													<td>
														<input id="rewardRate" name="rewardRate" maxlength="11"
															value="${entity.rewardRate}"
															class="validate[required,custom[double]] input_text"
															style="width: 120" />
														<span class="required">%&nbsp;*</span>
													</td>
												</tr>
												<tr>
													<td align="right">
														����׸�������
													</td>
													<td>
														<input id="firstPayRate" name="firstPayRate"
															maxlength="11" value="${entity.firstPayRate}"
															class="validate[required,custom[double]] input_text"
															style="width: 120" onblur="vali_FirstPayRate();" />
														<span class="required">%&nbsp;*</span>
													</td>
												</tr>
												<tr>
													<td align="right">
														���β�������
													</td>
													<td>
														<input id="secondPayRate" name="secondPayRate"
															maxlength="11" value="${entity.secondPayRate}"
															class="validate[required,custom[double]] input_text"
															style="width: 120" onblur="vali_SecondPayRate();" />
														<span class="required">%&nbsp;*</span>
													</td>

												</tr>
												<input type="hidden" id="commit_flag" value="" />
												<tr>
													<td colspan="3" align="center">
														<c:if test="${crud == 'create'}">
															<rightButton:rightButton name="���" onclick=""
																className="btn_sec"
																action="/config/special/add.action?flag=batch" id="add"></rightButton:rightButton>
														</c:if>
														<c:if test="${crud == 'update'}">
															<rightButton:rightButton name="�ύ" onclick=""
																className="btn_sec"
																action="/config/special/update.action?flag=batch"
																id="update"></rightButton:rightButton>
														</c:if>
														&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
														<rightButton:rightButton name="����" onclick=""
															className="btn_sec"
															action="/config/special/specialParamList.action"
															id="back"></rightButton:rightButton>
														<input type="hidden" name="brokerids" value="" />
													</td>
												</tr>
											</table>
										</div>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</div>
		</form>
	</body>
</html>