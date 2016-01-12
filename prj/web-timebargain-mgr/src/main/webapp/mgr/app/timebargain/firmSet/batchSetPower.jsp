<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>

<html>
	<head>
		<title>������ӽ�����Ȩ��</title>
		<link rel="stylesheet" href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationengine/template.css" type="text/css" />
		<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
		<script src="${publicPath }/js/validationengine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
		<script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>
		
		<script src="${basePath }/mgr/app/timebargain/js/tool.js" type="text/javascript" charset="GBK"></script>
		
		<script type="text/javascript">
	
			jQuery(document).ready(function() {
				jQuery("#frm").validationEngine('attach');
				// ��Ӱ�ť����¼�
				$("#add").click(function(){

			      var flag = onSave();
			      if(flag){
				      
					// ��֤��Ϣ
					if(jQuery("#frm").validationEngine('validate')){
						
						var vaild = affirm("��ȷ��Ҫ������");
						if(vaild){
							// �����ϢURL
							var addUrl = $(this).attr("action");
							// ȫ URL ·��
							var url = "${basePath}"+addUrl;
							$("#frm").attr("action",url);
							$("#frm").submit();
						}
					}
			      }
				});

				// ɾ����ť����¼�
				$("#delete").click(function(){

			      var flag = onSave();
			      if(flag){
				      
					// ��֤��Ϣ
					if(jQuery("#frm").validationEngine('validate')){
						
						var vaild = affirm("��ȷ��Ҫ������");
						if(vaild){
							// �����ϢURL
							var addUrl = $(this).attr("action");
							// ȫ URL ·��
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

	      function onSave(){
	    	  if(frm.typeID.value == "" && frm.startFirm.value == "" && frm.endFirm.value == ""){
					frm.typeID.focus();
					alert("�����뽻���̴��룡");
					return false;
				}

              if(frm.typeID.value != ""){
              	$("#typeID").addClass("validate[required]");
              }else{
              	$("#typeID").removeClass("validate[required]");	
              }
				
			    if(frm.startFirm.value != "" || frm.endFirm.value != ""){
			    	$("#startFirm").removeClass("validate[maxSize[10],custom[onlyNumberSp]] input_text");	
          	    $("#startFirm").addClass("validate[required,maxSize[10],custom[onlyNumberSp]] input_text");
          	    $("#endFirm").removeClass("validate[maxSize[10],custom[onlyNumberSp]] input_text");	
          	    $("#endFirm").addClass("validate[required,maxSize[10],custom[onlyNumberSp]] input_text");
              }else{
          	    $("#startFirm").removeClass("validate[required,maxSize[10],custom[onlyNumberSp]] input_text");	
			        $("#startFirm").addClass("validate[maxSize[10],custom[onlyNumberSp]] input_text");
			        $("#endFirm").removeClass("validate[required,maxSize[10],custom[onlyNumberSp]] input_text");	
			        $("#endFirm").addClass("validate[maxSize[10],custom[onlyNumberSp]] input_text");
              }

			    // ����ֵ���ڿ�ʼֵ
              if(frm.startFirm.value != "" && frm.endFirm.value != "" && frm.startFirm.value >= frm.endFirm.value){
              	frm.endFirm.focus();
                  alert("����ֵ������ڿ�ʼֵ��");
                  return false;
              }

              var kind = document.getElementById("kind").value;
				var breedID = document.getElementById("breedID").value;
				var commodityID = document.getElementById("commodityID").value;
				if(kind == "1" && breedID != ""){                   
              	document.getElementById("kindID").value = breedID;
				}
              else if(kind == "2" && commodityID != "" ){
              	document.getElementById("kindID").value = commodityID;
              }

	         return true;
	      }
	    
		  function kind_click(){
			 var kind = document.getElementById("kind").value;
			 
			 if (kind == "") {				
				 $("#commodity").hide();
				 $("#breed").hide();			
		     }
			 else if (kind == "1") {
				 $("#commodity").hide();
				 $("#breed").show();
			
			 }
			 else if (kind == "2") {			  
				 $("#commodity").show();
				 $("#breed").hide();
			 }	
		  }
		  
		  function window_onload(){
			  kind_click();
		  }

		  function myReset(){
			  frm.typeID.value="";
			  frm.startFirm.value="";
			  frm.endFirm.value="";
			  frm.kind.value="";
			  frm.breedID.value="";
			  frm.commodityID.value="";
			  frm.privilegeCode_B.value="";
			  frm.privilegeCode_S.value="";

			  }
		    
	    </script>
	</head>
	<body onload="window_onload()">
		<form id="frm" name="frm" method="post" enctype="multipart/form-data" action="" targetType="hidden">
			<input type="hidden" name="entity.type" value="1"/>
			
			
			<div class="div_cx">
				<table border="0" width="100%" align="center">
					<tr>
						<td>
							<div class="warning">
								<div class="content">
									��ܰ��ʾ :������ӽ�����Ȩ��
									<br/>
									<span class="required">ע������ֵ�������ʽ֧��������������磬���һ��5,7,9(��ʾ���5�����7�ͱ��9) �������1001-1005(��ʾ���1001��1005�����ڱ��)</span>
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
												������Ȩ��ά��
											</div>
											<div class="div_cxtjR"></div>
										</div>
										<div style="clear: both;"></div>
										<div>
											<table border="0" cellspacing="0" cellpadding="10" width="100%" align="center" class="table2_style">
												<tr>
													<td align="right" >
														�����̴��룺
													</td>
													<td>
													  <textarea id="typeID" name="typeID" style="width: 300px;" onkeypress="notSpace()"  rows="5" cols="10"></textarea>
													</td>
													
												</tr>
												<tr>
													<td align="right">
														��ʼ�����̣�
														 
													</td>
													
													<td>
													    <input type="text" id="startFirm" name="startFirm" style="width: 100px;" class="validate[maxSize[10],custom[onlyNumberSp]] input_text" onkeypress="onlyNumberInput()"/>
														&nbsp;&nbsp;���������̣� 
														<input type="text" id="endFirm" name="endFirm" style="width: 100px;" class="validate[maxSize[10],custom[onlyNumberSp]] input_text" onkeypress="onlyNumberInput()"/>
													</td>
													
													
												</tr>
												<tr>
													<td align="right">
														Ȩ�����ࣺ
													</td>
													<td>
														<select id="kind" name="entity.kind" class="validate[required]" onchange="kind_click()">
														  <option value="">��ѡ��</option>
														  <c:forEach items="${tradePrivilege_kindMap}" var="map" >
															<option value="${map.key}">${map.value}</option>
														  </c:forEach>
														</select>
														<span class="required">*</span>
													</td>
													<td>
														<div class="onfocus">����Ϊ�գ�</div>
													</td>
												</tr>
												<tr id="breed">
													<td align="right">
														����Ʒ�֣�
													</td>
													<td>
														<select id="breedID"  class="validate[required]">
														  <option value="">��ѡ��</option>
														  <c:forEach items="${breedList}" var="result" >
															<option value="${result.BREEDID}">${result.BREEDNAME}</option>
														  </c:forEach>
														</select>
														<span class="required">*</span>
													</td>
													<td>
														<div class="onfocus">����Ϊ�գ�</div>
													</td>
												</tr>
												
												<tr id="commodity">
													<td align="right">
														������Ʒ��
													</td>
													<td>
														<select id="commodityID"  class="validate[required]">
														  <option value="">��ѡ��</option>
														  <c:forEach items="${commodityList}" var="map" >
															<option value="${map['COMMODITYID']}">${map['NAME']}</option>
														  </c:forEach>
														</select>
														<span class="required">*</span>
													</td>
													<td>
														<div class="onfocus">����Ϊ�գ�</div>
													</td>
												</tr>
												<input type="hidden" id="kindID" name="entity.kindID" />
												
												<tr>
													<td align="right">
														��Ȩ�ޣ�
													</td>
													<td>
														<select id="privilegeCode_B" name="entity.privilegeCode_B" class="validate[required]">
														  <option value="">��ѡ��</option>
														  <option value="101">ȫȨ</option>
														  <option value="102">ֻ�ɶ���</option>
														  <option value="103">ֻ��ת��</option>
														  <option value="104">��Ȩ</option>
														</select>
														<span class="required">*</span>
													</td>
													<td>
														<div class="onfocus">����Ϊ�գ�</div>
													</td>
												</tr>
												<tr>
													<td align="right">
														����Ȩ�ޣ�
													</td>
													<td>
														<select id="privilegeCode_S" name="entity.privilegeCode_S" class="validate[required]">
														  <option value="">��ѡ��</option>
														  <option value="201">ȫȨ</option>
														  <option value="202">ֻ�ɶ���</option>
														  <option value="203">ֻ��ת��</option>
														  <option value="204">��Ȩ</option>
														</select>
														<span class="required">*</span>
													</td>
													<td>
														<div class="onfocus">����Ϊ�գ�</div>
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
			<div class="tab_pad">
				<table border="0" cellspacing="0" cellpadding="0" width="100%" align="center">
					<tr>
						<td align="center">
							<rightButton:rightButton name="�������" onclick="" className="btn_sec" action="/timebargain/firmSet/tradePrivilege/batchSetSaveFirmPrivilege.action" id="add"></rightButton:rightButton>
							&nbsp;&nbsp;
							<rightButton:rightButton name="�������" onclick="" className="btn_sec" action="/timebargain/firmSet/tradePrivilege/batchSetClearFirmPrivilege.action" id="delete"></rightButton:rightButton>
							&nbsp;&nbsp;
							<button class="btn_cz" onclick="myReset();">����</button>
							&nbsp;&nbsp;
							<button class="btn_sec" onClick="window.close();">�ر�</button>
						</td>
					</tr>
				</table>
			</div>
		</form>
	</body>
</html>
<%@ include file="/mgr/public/jsp/footinc.jsp"%>