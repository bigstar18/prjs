<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>

<html>
	<head>
		<title>�޸Ľ�����Ȩ��</title>
		<link rel="stylesheet" href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationengine/template.css" type="text/css" />
		<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
		<script src="${publicPath }/js/validationengine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
		<script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>
		<script type="text/javascript">
			jQuery(document).ready(function() {
				jQuery("#frm").validationEngine('attach');
				//�޸İ�ťע�����¼�
				$("#update").click(function(){
					//��֤��Ϣ
					if(jQuery("#frm").validationEngine('validate')){
						var vaild = affirm("��ȷ��Ҫ������");
						if(vaild){
							//�޸���ϢURL
							var updateUrl = $(this).attr("action");
							//ȫ URL ·��
							var url = "${basePath}"+updateUrl;
							$("#frm").attr("action",url);
							$("#frm").submit();
						}
					}
				});
			});
		</script>
		<style type="text/css">
		   .yin{
		         display: none;
		         position:absolute;
		   }
		   .xian{
		         display: block;
		   }
		 
		</style>
	    <script type="text/javascript">
	    
		  function setDisabled(obj)
		  {
			  obj.disabled = true;
			  obj.style.backgroundColor = "#C0C0C0";
		  }
		  function setEnabled(obj)
		  {
			  obj.disabled = false;
			  obj.style.backgroundColor = "white";
		  }

		  function kind_click(){
				 var kind = document.getElementById("kind").value;
				 
				 if (kind == "") {
					document.getElementById("commodity").className = 'yin';
					document.getElementById("breed").className = 'yin';
			     }
				 else if (kind == "1") {
					document.getElementById("breed").className = 'xian';
					document.getElementById("commodity").className = 'yin';

					document.getElementById("breedID").value = "${entity.kindID}";
					setDisabled(document.getElementById('breedID'));
				
				 }
				 else if (kind == "2") {
				    document.getElementById("commodity").className = 'xian';
					document.getElementById("breed").className = 'yin';

					document.getElementById("commodityID").value = "${entity.kindID}";
					setDisabled(document.getElementById('commodityID'));
				 }	
		  }

		  function window_onLoad(){
			  setDisabled(document.getElementById('kind'));
			  
			  kind_click();
			 
	      }	  
	      
	      
	    </script>
	</head>
	<body onload="window_onLoad()">
		<form id="frm" method="post" enctype="multipart/form-data" action="" targetType="hidden">
			<input type="hidden" name="entity.ID" value="${entity.ID}"/>
			<div class="div_cx">
				<table border="0" width="100%" align="center">
					<tr>
						<td>
							<div class="warning">
								<div class="content">
									��ܰ��ʾ :�޸Ľ�����Ȩ��
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
													<td align="right">
														Ȩ�����ࣺ
													</td>
													<td>
														<select id="kind" name="entity.kind" class="validate[required]">
														  <option value="">��ѡ��</option>
														  <c:forEach items="${tradePrivilege_kindMap}" var="map" >
															<option value="${map.key}">${map.value}</option>
														  </c:forEach>
														</select>
														<span class="required">*</span>
														<script type="text/javascript">
															document.getElementById("kind").value="${entity.kind}";
														</script>
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
													
												</tr>
												<tr id="commodity">
													<td align="right">
														������Ʒ��
													</td>
													<td>
														<select id="commodityID"  class="validate[required]">
														  <option value="">��ѡ��</option>
														  <c:forEach items="${commodityList}" var="result" >
															<option value="${result.COMMODITYID}">${result.NAME}</option>
														  </c:forEach>
														</select>
														<span class="required">*</span>
													</td>
													
												</tr>
												<input type="hidden" id="kindID" name="entity.kindID" value="${entity.kindID }" />
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
														<script type="text/javascript">
															document.getElementById("privilegeCode_B").value="${entity.privilegeCode_B}";
														</script>
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
														<script type="text/javascript">
															document.getElementById("privilegeCode_S").value="${entity.privilegeCode_S}";
														</script>
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
							<rightButton:rightButton name="�޸�" onclick="" className="btn_sec" action="/timebargain/firmSet/tradePrivilege/updateFirmPrivilege.action" id="update"></rightButton:rightButton>
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