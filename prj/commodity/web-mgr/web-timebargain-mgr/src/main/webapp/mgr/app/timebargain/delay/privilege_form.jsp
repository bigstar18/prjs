<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
	    <base target="_self" />
		<title>��Ʒ����Ȩ��</title>
        <link rel="stylesheet" href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationengine/template.css" type="text/css" />
		<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
		<script src="${mgrPath }/app/timebargain/js/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
		<script src="${mgrPath }/app/timebargain/js/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>
		<script type="text/javascript"> 
		jQuery(document).ready(function() {
			if ("${opr}" == "update") {
				$("#typeId").attr("disabled", true).css("background-color", "#C0C0C0");
				$("#kindId").attr("disabled", true).css("background-color", "#C0C0C0");
			}
			
			jQuery("#frm").validationEngine('attach');
			//�޸İ�ťע�����¼�
			$("#add").click(function(){
				var firmId = document.getElementById("typeId").value;
				var kindId = document.getElementById("kindId").value;
				if (firmId != "" && kindId != "") {
					$.ajax({
						type: "post",
						url: "../../ajaxcheck/demo/ajaxAddPrivilege.action",
						data: {
								firmId : firmId,
								kindId : kindId
							  },
						success : function(data){
									if(data.opr=="1"){
									  //$("#typeId").val("");
									  alert("�˽����̺���Ʒ�Ѿ����");
									}else{																		
											//��֤��Ϣ
											if(jQuery("#frm").validationEngine('validateform')){
										    	var vaild = affirm("��ȷ��Ҫ������");
												if(vaild){
													$("#type").val("1");
													$("#kind").val("2");
													//�����ϢURL
													var updateDemoUrl = $("#add").attr("action");					
													//ȫ URL ·��
													var url = "${basePath}"+updateDemoUrl;					
													$("#frm").attr("action",url);
													$("#frm").submit();
													$(this).attr("disabled",true);
												}
											}
										 
										}
						          }
					});
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
		});
		//function getSettleprivilege(){
			//var firmId = document.getElementById("typeId").value;
			//var kindId = document.getElementById("kindId").value;
			//if (firmId != "" && kindId != "") {
				//$.ajax({
					//type: "post",
					//url: "../../ajaxcheck/demo/ajaxAddPrivilege.action",
					//data: {
							//firmId : firmId,
							//kindId : kindId
						  //},
					//success : function(data){
								//if(data.opr=="1"){
								  //$("#typeId").val("");
								  //alert("�˽����̺���Ʒ�Ѿ����");
								//}
					          //}
				//});
			//}
		//}				
</script>
</head>

<body>
	<form id="frm" method="post" enctype="multipart/form-data" action="">
			<div class="div_cx">
				<table border="0" width="100%" align="center">
					<tr>
						<td>
							<div class="warning">
								<div class="content">
									<c:choose>
										<c:when test="${opr == 'create'}">��ܰ��ʾ :��Ʒ����Ȩ�����  </c:when>
										<c:otherwise>��ܰ��ʾ :��Ʒ����Ȩ�޸��� </c:otherwise>
									</c:choose>
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
												<c:choose>
													<c:when test="${opr == 'create'}">�����Ʒ����Ȩ��</c:when>
													<c:otherwise>������Ʒ����Ȩ��</c:otherwise>
												</c:choose>
											</div>
											<div class="div_cxtjR"></div>
										</div>
										<div style="clear: both;"></div>
										<div>
											<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center" class="table2_style">
										        <tr >
													<td align="center">
														�����̴���:
													</td>
													<td align="center">
														<input type="text" id="typeId" name="entity.typeId"
															style="width: 120" maxlength="<fmt:message key='MFirm.firmId_q' bundle='${PropsFieldLength}' />" 
															class="validate[required,onlyLetterNumber,ajax[mouseCheckFirmByFirm]] input_text" value="${entity.typeId }"/>
														<input type="hidden" name="entity.id" value="${entity.id }"/>
														<input type="hidden" id="type" name="entity.type" value="${entity.type }"/>
														<input type="hidden" id="kind" name="entity.kind" value="${entity.kind }"/>
														<span class="required">*</span>
													</td>
											
													<td align="center">
														��Ʒ:
													</td>
													<td align="center">
													    <select id="kindId" name="entity.kindId" style="width: 120" class="validate[required]"
													    	>
															<option value="all" >ȫ����Ʒ</option>							    	
													    	<c:forEach   items="${commoditySelect}" var="result" begin="1">
													    	    <option value="${result.value }" <c:if test="${entity.kindId == result.value }">selected</c:if>>${result.label}</option>
														    </c:forEach>
														    
													    </select>
													    <span class="required">*</span>
													</td>
												</tr>
												<tr>
													<td align="center">
														��Ȩ��:
													</td>
													<td align="center">
														<select id="privilegecode_b"  name="entity.privilegecodeb" style="width: 130" class="validate[required]">
															<option value="101" <c:if test="${entity.privilegecodeb=='101' }">selected</c:if>>ȫȨ</option>
															<option value="102" <c:if test="${entity.privilegecodeb==102 }"> selected="selected"</c:if>>ֻ�ɽ����걨</option>
															<option value="103" <c:if test="${entity.privilegecodeb==103 }"> selected="selected"</c:if>>ֻ���������걨</option>
															<option value="104" <c:if test="${entity.privilegecodeb==104 }"> selected="selected"</c:if>>��Ȩ</option>															
														</select>
														<span class="required">*</span>
													</td>							
													<td align="center">
														����Ȩ��:
													</td>
													<td align="center">
														<select id="privilegecode_s" name="entity.privilegecodes" style="width: 120" class="validate[required]">
															<option value="101" <c:if test="${entity.privilegecodes==101 }"> selected</c:if>>ȫȨ</option>
															<option value="102" <c:if test="${entity.privilegecodes==102 }"> selected="selected"</c:if>>ֻ�ɽ����걨</option>
															<option value="103" <c:if test="${entity.privilegecodes==103 }"> selected="selected"</c:if>>ֻ���������걨</option>
															<option value="104" <c:if test="${entity.privilegecodes==104 }"> selected="selected"</c:if>>��Ȩ</option>														
														</select>
														<span class="required">*</span>
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

			<div class="tab_pad" style="bottom: 250">
				<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center">
					<tr>
						<td align="center">
						    <c:if test="${opr == 'create'}">
								<rightButton:rightButton name="���" onclick="" className="btn_sec" action="/timebargain/delay/addPrivilege.action" id="add"></rightButton:rightButton>
							</c:if>
							<c:if test="${opr == 'update'}">
								<rightButton:rightButton name="�ύ" onclick="" className="btn_sec" action="/timebargain/delay/updatePrivilege.action" id="update"></rightButton:rightButton>
							</c:if>
							&nbsp;&nbsp;
							<button class="btn_sec" onClick="window.close();">�ر�</button>
						</td>
					</tr>
				</table>
			</div>
		</form>
	
</body>
</html>
