<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>

<html>
	<head>
	    <base target="_self"/>
		<title>�������������</title>
		 <link rel="stylesheet" href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationengine/template.css" type="text/css" />
		<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
		<script src="${publicPath }/js/validationengine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
		<script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>
		
   
  <script type="text/javascript">
			jQuery(document).ready(function() {
				jQuery("#frm").validationEngine('attach');
				//��Ӱ�ťע�����¼�
				$("#add").click(function(){
					//��֤��Ϣ
					if(jQuery("#frm").validationEngine('validate')){
						
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
				});
			});
		</script>


	</head>
	
	<body>
		<form id="frm" method="post" >
			<div class="div_cx">
				<table border="0" width="100%" align="center">
					<tr>
						<td>
							<div class="warning">
								<div class="content">
									��ܰ��ʾ :�������������
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
												��Ӽ���������
											</div>
											<div class="div_cxtjR"></div>
										</div>
										<div style="clear: both;"></div>
												<div>
											<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center" class="table2_style">
												
												<tr>
													<td align="right">
														<span class="required">*</span>
														�������ƣ�
													</td>
													<td>
													    <input type="text" id="name" name="entity.name" 
															class="validate[required] input_text datepicker"/>
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
				<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center">
					<tr>
						<td align="center">
							<rightButton:rightButton name="���" onclick="" className="btn_sec" action="/broker/brokerManagement/addBrokerArea.action" id="add"></rightButton:rightButton>
							&nbsp;&nbsp;
							<button class="btn_sec" onClick="window.close();">�ر�</button>
						</td>
					</tr>
				</table>
		</div>
	</form>
</body>
												
	</html>