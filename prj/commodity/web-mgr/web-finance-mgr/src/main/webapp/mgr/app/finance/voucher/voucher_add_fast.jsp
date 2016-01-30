<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<%
	List voucherModelList = (List)request.getAttribute("voucherModelList");
%>
<html>
  <head>
	<title>凭证快捷添加</title>
	  <link rel="stylesheet" href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
	  <link rel="stylesheet" href="${skinPath }/css/validationengine/template.css" type="text/css" />
	  <script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
	  <script src="${basePath}/mgr/app/finance/js/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
	  <script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>

	  <script type="text/javascript" src="${mgrPath}/app/finance/voucher/voucher.js"></script>
	  
	  <script type="text/javascript">
	   jQuery(document).ready(function() {
			
			//ajax验证交易商代码是否存在
			jQuery("#frm").validationEngine( {
				ajaxFormValidation : true,
				ajaxFormValidationURL : "${basePath}/ajaxcheck/voucher/formCheckFirmByFirmId.action",
				onAjaxFormComplete : ajaxValidationCallback,
				onBeforeAjaxFormValidation : beforeCall
			});

			//提交前事件
			function beforeCall(form, options) {
				return true;
			}

			//提交后事件
			function ajaxValidationCallback(status, form, json, options) {

			  // 获取金额
			  var money = parseFloat(frm.money.value);
			 
			  if(money != 0){
				
				//如果返回成功
				if (status === true) {
					var vaild = affirm("您确定要操作吗？");
					if(vaild){
						//添加信息URL
						var addUrl = $("#add").attr("action");
						//全 URL 路径
						var url = "${basePath}"+addUrl;
						$("#frm").attr("action",url);
						frm.submit();
					}
				}
			  }else{
				  alert("金额不允许为0值！");
				  frm.money.focus();
			  }
			}

			//添加按钮注册点击事件
			$("#add").click(function(){

				// 判断快捷录入模板需要合同号
				if(frm.type.value != "" && frm.contractNo.value == ""){
						alert("该模板需要合同号，请输入合同号！");
						frm.contractNo.focus();
					return;
				}
				
				//验证信息
				if ($("#frm").validationEngine('validateform')) {
				}
			});
		});

	    // 判断交易商、商品是否不为空
		function changeInput(){
			if(frm.firmId.value == ""){
				$("#firmId").removeClass("validate[custom[onlyLetterNumber],ajax[mouseCheckFirmByFirmId]] input_text");
				$("#firmId").addClass("validate[required,custom[onlyLetterNumber],ajax[mouseCheckFirmByFirmId]] input_text");
			}
			if(frm.commodityId.value == ""){
				$("#commodityId").removeClass("validate[maxSize[16] input_text");
				$("#commodityId").addClass("validate[required,maxSize[16] input_text");
			}
		}
	   </script>
	   
	</head>
	<body>
		<form id="frm" method="post" action="">
			<div class="div_cx">
				<table border="0" width="100%" align="center">
					<tr>
						<td>
							<div class="warning">
								<div class="content">
									温馨提示 :凭证添加
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
												快捷录入
											</div>
											<div class="div_cxtjR"></div>
										</div>
										<div style="clear: both;"></div>
										<div>
											<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center" class="table2_style">
												<tr>
													<td align="right">
														<span class="required">*</span>
														类型：
													</td>
													<td>
														<select id="type" name="entity.type" class="validate[required]" onchange="change(),changeInput()">
															<option value="">请选择</option>
															<c:forEach items="${voucherModelList}" var="map" varStatus="status">
																<option value="${map['CODE']}">${map["NAME"]}</option>
															</c:forEach>
														</select>
													</td>
												</tr>
												<tr>
													<td align="right">凭证摘要号：</td>
													<td>
											  			<input id="summaryNo" name="summaryNo" type="text" style="background-color: #C0C0C0;" class="input_text" readonly="readonly" >
											  		</td>
												</tr>
												<tr>
													<td align="right">凭证摘要名称：</td>
													<td>
														<input id="summary" name="summary" type="text" style="background-color: #C0C0C0;"  class="input_text" readonly="readonly" >
													</td>
												</tr>
												<tr>
													<td align="right">
														借方科目代码：
													</td>
													<td colspan="2">
														<input id="debitCode" name="debitCode" type="text" style="background-color: #C0C0C0;"  class="input_text" readonly="readonly" >
														<input type="hidden" name="debitCodeOld" id="debitCodeOld" value="" >
													</td>
												</tr>
												<tr>
													<td align="right">借方科目名称：</td>
													<td colspan="2">
														<input id="debitCodeName" name="debitCodeName" type="text" style="background-color: #C0C0C0;"  class="input_text" readonly="readonly" >
													</td>
												</tr>
												<tr>
													<td align="right">贷方科目代码：</td>
													<td>
														<input id="creditCode" name="creditCode" type="text" style="background-color: #C0C0C0;"  class="input_text" readonly="readonly" >
														<input type="hidden" name="creditCodeOld" id="creditCodeOld" value="" >
													</td>
												</tr>
												<tr>
													<td align="right">贷方科目名称：</td>
													<td>
														<input id="creditCodeName" name="creditCodeName" type="text" style="background-color: #C0C0C0;"  class="input_text" readonly="readonly" >
														
													</td>
												</tr>
												<tr>
												    
												  <td align="right">
													
													交易商代码：
												  </td>
													<td colspan="2">
														<input type="text" id="firmId" name="firmId" onchange="change1()"  class="validate[custom[onlyLetterNumber],ajax[mouseCheckFirmByFirmId]] input_text "/>
													</td>
												</tr>
												<tr>
													<td align="right">商品代码：</td>
													<td>
														<input type="text" id="commodityId" name="commodityId" onchange="change2()" class="validate[maxSize[16] input_text "/>
													</td>
												</tr>
												<tr>
													<td align="right">
														合同号：
													</td>
													<td colspan="2">
														<input type="text" id="contractNo" name="contractNo" onkeypress="onlyNumberAndCharInput()" class="validate[maxSize[16] input_text "/>
													</td>
												</tr>
												<tr>
													<td align="right">
													<span class="required">*</span>
														金额：
													</td>
													<td colspan="2">
														<input type="text" id="money" name="money" onkeypress="return onlyDoubleInput()" class="validate[required,maxSize[13],custom[onlyDoubleSp]] input_text"/>
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
			<div >
				<table border="0" cellspacing="0" cellpadding="0" width="100%" align="center">
					<tr>
						<td align="center">
							<rightButton:rightButton name="生成凭证" onclick="" className="btn_sec" action="/finance/voucher/addVoucherFast.action" id="add"></rightButton:rightButton>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						</td>
					</tr>
				</table>
			</div>
			
		</form>
	</body>
</html>
	  <script type="text/javascript">
			function change(){
	         	if(frm.type.value==-1) {
    		   		frm.reset();
    		   		frm.firmId.disabled=false;
			   		frm.contractNo.disabled=false;
			   		frm.commodityId.disabled=false;
	         	}
            	<%
            		try{
               			if(voucherModelList!=null&&voucherModelList.size()>0) {
			       			for(int i=0;i<voucherModelList.size();i++) {
			        			String code=(String)((Map)voucherModelList.get(i)).get("CODE");
			        			String debitCode=(String)((Map)voucherModelList.get(i)).get("DEBITCODE");
			        			String creditCode=(String)((Map)voucherModelList.get(i)).get("CREDITCODE");
			        			String needContractNo=((Map)voucherModelList.get(i)).get("NEEDCONTRACTNO").toString();
			        			String summaryNo=((Map)voucherModelList.get(i)).get("SUMMARYNO").toString();
			        			String summary=((Map)voucherModelList.get(i)).get("SUMMARY").toString();
			        			boolean firmIdSign=true;
			        			if(debitCode.indexOf("-")>-1||creditCode.indexOf("-")>-1) {
			             			firmIdSign=false;
			        			}
			        			boolean commoditySign=true;
			        			if(debitCode.indexOf("*")>-1||creditCode.indexOf("*")>-1) {
			             			commoditySign=false;
			        			}
			        			boolean contract=false;
			        			if("N".equals(needContractNo)) { 
			             			contract=true;
			        			}
				%>
		  		if(frm.type.value==<%=code%>) {
		        	frm.firmId.disabled=false;
		            frm.contractNo.disabled=false;
		            frm.commodityId.disabled=false;
		            frm.reset();
         		    frm.type.value=<%=code%>
		            frm.debitCode.value="<%=debitCode%>";
		            frm.creditCode.value="<%=creditCode%>";
		            frm.debitCodeOld.value="<%=debitCode%>";
		            frm.creditCodeOld.value="<%=creditCode%>";
		            frm.summaryNo.value="<%=summaryNo%>";
		            frm.summary.value="<%=summary%>";
		            
		            if(<%=firmIdSign%>) {
		              	frm.firmId.value="无";
		              	frm.firmId.disabled=true;
		            }
		            if(<%=commoditySign%>) {
		              	frm.commodityId.value="无";
		              	frm.commodityId.disabled=true;
		            }
		            if(<%=contract%>) {
		              	frm.contractNo.value="无";
		              	frm.contractNo.disabled=true;
		            }
		            changeAccountCodeFast('<%=debitCode%>','debitCodeName');
		            changeAccountCodeFast('<%=creditCode%>','creditCodeName');
		     	}   
			    <%
			       			}
			      		}
			      	}catch(Exception e) {
			        		out.println(e);
			      	}
           		%>
			}

			function changeAccountCodeFast( vCode,fieldName ){
		        if(!(vCode.indexOf('-')>=0||vCode.indexOf('*')>=0)) {
		    		if(vCode != null && vCode.length > 0){
			    		var url = "../../ajaxcheck/finance/getAccountName.action?accountCode="+vCode+"&t="+Math.random();
						$.getJSON(url,null,function call(result){
							if(result=="-1"){
								alert("交易商不存在或者有下级的科目代码！");	
								
							}else{
								frm.debitCodeName.value == "";
								frm.creditCodeName.value == "";
								setAccountNameFast(vCode,result,fieldName);
								
							}

							 //  判断借、贷方科目名称是否不为空
							var debitCode=frm.debitCodeOld.value;
							var creditCode=frm.creditCodeOld.value;
				          	
							if (result=="-1" || frm.debitCodeName.value == "") {
								frm.debitCode.value=debitCode;
								

								$("#debitCodeName").removeClass("input_text");
								$("#debitCodeName").addClass("validate[required] input_text");
							}
							if (result=="-1" || frm.creditCodeName.value == "") {
								frm.creditCode.value=creditCode;
								

								$("#creditCodeName").removeClass("input_text");
								$("#creditCodeName").addClass("validate[required] input_text");
							}
						});
	        		}
	        	}
		    }
			
			function setAccountNameFast(vCode,vName,fieldName) {
			 	document.getElementById(fieldName).value=vName;		 	
		    }

			function change1(){
				var firmId=frm.firmId.value;
				var debitCode=frm.debitCodeOld.value;
				var creditCode=frm.creditCodeOld.value;
				var debitCode1;
				var creditCode1;
				if (debitCode.indexOf('-')>=0) {
					debitCode1=debitCode.replace('-',firmId);
					frm.debitCode.value=debitCode1;
					changeAccountCodeFast(debitCode1,'debitCodeName');
					
				}
				if (creditCode.indexOf('-')>=0) {
					creditCode1=creditCode.replace('-',firmId);
					frm.creditCode.value=creditCode1;
					changeAccountCodeFast(creditCode1,'creditCodeName');
					
				}
				
			}

			function change2(){
				var commodityId=frm.commodityId.value;
				var debitCode=frm.debitCodeOld.value;
				var creditCode=frm.creditCodeOld.value;
				var debitCode1;
				var creditCode1;
				if (debitCode.indexOf('*')>=0) {
					debitCode1=debitCode.replace('*',commodityId);
					frm.debitCode.value=debitCode1;
					changeAccountCodeFast(debitCode1,'debitCodeName');
					
				}
				if (creditCode.indexOf('*')>=0) {
					creditCode1=creditCode.replace('*',commodityId);
					frm.creditCode.value=creditCode1;
					changeAccountCodeFast(creditCode1,'creditCodeName');

					
				}
			}

			function addFast() {
				
				if(frm.type.value==-1) {
			      	alert("类型必须填写");
			      	return false;
			    }
			   	var debitCode=frm.debitCode.value;
			   	var creditCode=frm.creditCode.value;
			   
			   	if(debitCode.indexOf('-')>=0||debitCode.indexOf('*')>=0||creditCode.indexOf('-')>=0||creditCode.indexOf('*')>0) {
			     	alert("借方科目或贷方科目还有交易商代码或商品代码没有填写");
			     	return false;
			   	}
			     var money=frm.money.value;
			   	if(money==''||isNaN(money)) {
			     	alert("金额没有填写或者不是数字");
			     	return false;
			   	}
			   	return true;  
			}

			
		</script>
<%@ include file="/mgr/public/jsp/footinc.jsp"%>