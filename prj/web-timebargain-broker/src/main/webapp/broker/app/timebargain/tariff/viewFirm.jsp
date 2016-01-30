<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/broker/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>交易商基本信息</title>
		<link rel="stylesheet" href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationengine/template.css" type="text/css" />
		<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
		<script src="${publicPath }/js/validationengine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
		<script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>
		<SCRIPT type="text/javascript">
		<!-- 

		    $(document).ready(function() {
		    	jQuery("#frm").validationEngine('attach');
				//修改按钮注册点击事件
				$("#update").click(function(){
					//验证信息
					if(jQuery("#frm").validationEngine('validateform')){
						var vaild = affirm("您确定要操作吗？");
						if(vaild){
							//添加信息URL
							var updateDemoUrl = $(this).attr("action");
							//全 URL 路径
							var url = "${basePath}"+updateDemoUrl;
							$("#frm").attr("action",url);
							$("#frm").submit();
							$(this).attr("disabled",true);
						}
					}
				});

				$("#back").click(function(){
					//添加信息URL
					var updateDemoUrl = $(this).attr("action");
					//全 URL 路径
					var url = "${basePath}"+updateDemoUrl+"?sortColumns=order+by+firmID+asc";

					document.location.href = url;
				});

				$("#firmId").attr("readonly", true).css("background-color", "#C0C0C0");
				$("#name").attr("readonly", true).css("background-color", "#C0C0C0");
				$("#fullName").attr("readonly", true).css("background-color", "#C0C0C0");
				$("#email").attr("readonly", true).css("background-color", "#C0C0C0");
				$("#postCode").attr("readonly", true).css("background-color", "#C0C0C0");
				$("#type").val("${entity.type}");
				viewChange();
			});

		function viewChange(){
			if("${entity.type}"==3)
				document.getElementById("viewText").innerText="身份证号 ：";
			else
				document.getElementById("viewText").innerText="组织机构代码 ：";
			
		}

		function showTariff() {
			//获取配置权限的 URL
			var addUrl="/firm/tariff/detailTariffCommodity.action";
			//获取完整跳转URL
			if ($("#firmTID").val() != "none") {
				var url = "${basePath}"+addUrl+"?tariffID="+$("#firmTID").val();

				window.open(url, "", "width=750,height=500");
			}
		}
		//-->
		</SCRIPT>
	</head>
	<body>
		<form id="frm" name="frm" method="post" enctype="multipart/form-data" action="" targetType="hidden">
			<div class="div_cx">
				<table border="0" width="800" align="center">
					<tr>
						<td>
							<div class="warning">
								<div class="content">
									温馨提示 :修改交易商套餐信息
									<br/>
									<span class="required">只可设置订单手续费套餐</span>
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
												交易商基本信息
											</div>
											<div class="div_cxtjR"></div>
										</div>
										<div style="clear: both;"></div>
										<div>
											<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center" class="table2_style">
												  <tr height="35">
									                <td align="right"> 交易商代码 ：</td>
									                <td align="left">
									                	<input class="readonly" id="firmId" name="entity.firmID" value="${entity.firmID }" style="width: 150px;" readonly>&nbsp;<font color="#ff0000">*</font>
									                </td>
									                
									                <td align="right"> 订单手续费套餐 ：
									                <td align="left" >
									                
									                  <select id="firmTID" name="tariffID">
									                    <c:if test="${tariffID == 'none'}">
									                      <option value="none" >请选择</option>
									                    </c:if>
									                    <c:forEach items="${tariffList}" var="result">
							                          		<option value="${result.TARIFFID}" <c:if test="${tariffID == result.TARIFFID}">selected</c:if>>${result.TARIFFNAME }</option>
								                      	</c:forEach>
									                    </select>&nbsp;&nbsp;
									                    <span id="tariffview" name="tariffview" onclick='showTariff()'  style="color:#00008B;text-decoration:underline;cursor: hand;">查看套餐详细信息</span> 
									                </td>
									              </tr>
									              <tr height="35">
									                <td align="right"> 交易商名称 ：</td>
									                <td align="left">
									                	<input id="name" name="entity.name" type="text" value="<c:out value='${entity.name }'/>" class="text" style="width: 150px;" reqfv="required;用户名称">&nbsp;<font color="#ff0000">*</font>
									                </td>
									      
									            	<td align="right"> 交易商全称 ：</td>
									                <td align="left">
									                	<input id="fullName" name="entity.fullName" type="text" class="text" style="width: 150px;" value="<c:out value='${entity.fullName}'/>">
									                </td>
									              </tr>
									              <tr height="35">
									            	<td align="right"> EMail ：</td>
									                <td align="left">
									                	<input id="email" name="entity.email" value="<c:out value='${entity.email}'/>"  type="text" class="text" style="width: 150px;">
									                </td>
									            	<td align="right"> 邮政编码 ：</td>
									                <td align="left">
									                	<input id="postCode" name="entity.postCode" type="text" value="<c:out value='${entity.postCode}'/>" class="text" style="width: 150px;" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="6">
									                </td>
									              </tr>
									             <tr height="35">
									           	 	<td align="right"> 类型 ：</td>
									                <td align="left">
									               	<select id="type" name="entity.type" class="validate[required] normal" onchange="viewChange();" style="width: 150px" reqfv="required;类别">
													<OPTION value=""></OPTION>
													<option value="1">法人</option>
													<option value="2">代理</option>
									           		<option value="3">个人</option>
										    		</select>
										    		&nbsp;<font color="#ff0000">*</font>
									                </td>            	
									            	<td align="right"> 
									                  <div id="viewText" name="viewText">
									                    <c:if test="${entity.type == 3}">
									                                                        身份证号 ：
									                    </c:if>
									                    <c:if test="${entity.type != 3}">
									                                                         组织机构代码 ：
									                    </c:if>                                  
									                  </div>
									                </td>
									                <td align="left">
									                	<input name="entity.organizationCode" value="<c:out value='${entity.organizationCode}'/>"  type="text" class="text" style="width: 150px;" >
									                	&nbsp;<font color="#ff0000">*</font>
									                </td>
									              </tr>
									              <tr height="35">
									            	<td align="right"> 联&nbsp;系&nbsp;人 ：</td>
									                <td align="left">
									                	<input name="entity.contactMan" value="<c:out value='${entity.contactMan}'/>"  type="text" class="text" style="width: 150px;" >
									                </td>
									            	<td align="right"> 手机号码 ：</td>
									                <td align="left">
									                	<input name="entity.mobile" value="<c:out value='${entity.mobile}'/>"  type="text" class="text" style="width: 150px;" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="11">
									                </td>
									              </tr>
									              <tr height="35">
									            	<td align="right"> 联系电话 ：</td>
									                <td align="left">
									                	<input name="entity.phone" type="text" value="<c:out value='${entity.phone}'/>" class="text" style="width: 150px;" >
									                </td>
									            	<td align="right"> 传&nbsp;&nbsp;&nbsp;&nbsp;真 ：</td>
									                <td align="left">
									                	<input name="entity.fax" type="text" value="<c:out value='${entity.fax}'/>" class="text" style="width: 150px;" >
									                </td>
									              </tr>
									              
									              <tr height="35">
									            	<td align="right">详细地址 ：</td>
									                <td align="left" colspan="3">
									                	<textarea name="entity.address" cols="64" rows="3"><c:out value='${entity.address}'/></textarea>
									                </td>
									              </tr>
									              <tr height="35">
									              <td align="right"> 备注 ：</td>
									              <td align="left" colspan="3">
									                <textarea name="entity.note" cols="64" rows="3" ><c:out value='${entity.note}'/></textarea>
									              </td>
									              </tr>
									              <tr height="35">
									              	<td colspan="4" align="center">
									                	<rightButton:rightButton name="提交" onclick="" className="btn_sec" action="/firm/tariff/updateTariff.action" id="update"></rightButton:rightButton>
														&nbsp;&nbsp;
														<rightButton:rightButton name="返回" onclick="" className="btn_sec" action="/firm/tariff/tariffList.action" id="back"></rightButton:rightButton>
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