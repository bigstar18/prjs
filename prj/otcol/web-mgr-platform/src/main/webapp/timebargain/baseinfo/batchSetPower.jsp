<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>
<%@ page import="java.util.*" %>
<%@ page import="gnnt.MEBS.timebargain.manage.service.*"%>
<%@ page import="gnnt.MEBS.timebargain.manage.util.SysData" %>
<%@ page import="gnnt.MEBS.timebargain.manage.model.*" %>
<%
		LookupManager lookupMgr = (LookupManager)SysData.getBean("lookupManager");
		request.setAttribute("breedSelect", lookupMgr
				.getSelectLabelValueByTable("T_A_BREED", "breedName",
						"breedID"," order by breedID "));
		request.setAttribute("commoditySelect", lookupMgr
				.getSelectLabelValueByTable("T_commodity", "commodityID",
						"commodityID"," order by commodityID "));
%>
<html>
	<head>
	<base target="_self" />
		<LINK REL="stylesheet" type="text/css"
			href="<c:url value="/timebargain/styles/common.css"/>" />
		<script language="JavaScript"
			src="<c:url value="/timebargain/scripts/global.js"/>"></script>
		<script language="javascript" src="<%=request.getContextPath()%>/timebargain/public/jstools/jquery.js"></script>	
		<title>
		批量添加交易商权限
		</title>
<style type="text/css">
<!--
.yin {
	visibility:hidden;
	position:absolute;
}
.xian{
	visibility:visible;
}
-->
</style>
<script type="text/javascript"> 
function window_onload()
{
    
   
}
function all_select(){
	var allselect = document.getElementsByName("allselect");
	var flag = false;
	for(var i=0;i<allselect.length;i++){
        if(allselect[i].checked){
        	flag = true;
      }
   }
	if ( flag ){
		var commodity = document.getElementsByName("commodity");//上市商品获得
		for(var i=0;i<commodity.length;i++){
			commodity[i].checked = true;
	       }
	    } else {
	    	var commodity = document.getElementsByName("commodity");//上市商品获得
			for(var i=0;i<commodity.length;i++){
				commodity[i].checked = false;
		       }

		    }

}


//save
function save_onclick()
{
  if (confirm("您确定要提交吗？")) {
	  var r=/[0-9 - ,]$/; 
	  var codes = document.getElementById("codes");//代码值获得
	  var code = document.getElementsByName("Code");//代码种类获得
	  var goods = document.getElementsByName("goods");//权限种类种类获得
	  var breed = document.getElementsByName("breed");//上市品种获得
	  var commodity = document.getElementsByName("commodity");//上市商品获得
	  var codeValue = "";
	  var breedValue = "";
	  var goodsValue = "";
	  var commodityValue = "";
	  for(var i=0;i<code.length;i++){
	         if(code[i].checked){
	        	 codeValue += code[i].value;
	       }
	    }
	  for(var i=0;i<goods.length;i++){
	         if(goods[i].checked){
	        	 goodsValue += goods[i].value;
	       }
	    }
	  for(var i=0;i<breed.length;i++){
	         if(breed[i].checked){
	        	 breedValue += breed[i].value + ",";
	       }
	    }
	  for(var i=0;i<commodity.length;i++){
	         if(commodity[i].checked){
	        	 commodityValue += commodity[i].value + ",";
	       }
	    }
	 if ( trim(codeValue) == "" ) {
		 alert("代码种类不能为空！");
		 return false;
		 }
	 if ( trim(codeValue) != "") {
		 if ( trim(codes.value) == "" ) {
			 alert("代码值不能为空！");
			 codes.focus();
			 return false;
			 } else if ( !r.test(codes.value) ) {
				 alert("代码值格式错误，只能是字母，数字，中线，逗号组成！");
				 codes.focus();
				 return false;
				 }

		 }
	 if ( trim(goodsValue) == "" ) {
		 alert("权限种类不能为空！");
		 return false;
		 }
	if ( trim(goodsValue) == "1" ) {
		if (trim(breedValue) == "") {
			 alert("请选择上市品种！");
			 return false;
			}
		}
	if ( trim(goodsValue) == "2" ) {
		if (trim(commodityValue) == "") {
			 alert("请选择上市商品！");
			 return false;
			}
		}
  	if (customerForm.privilegeCode_B.value == "") {
   		alert("买方权限不能为空！");
   		customerForm.privilegeCode_B.focus();
   		return false;
   }
    if (customerForm.privilegeCode_S.value == "") {
   		alert("卖方权限不能为空！");
   		customerForm.privilegeCode_S.focus();
   		return false;
   }
   customerForm.crud.value = "save";
   customerForm.submit();	
   customerForm.save.disabled = true;
  }
   
}
function empty_onclick() {
	if (confirm("您确定要提交吗？")) {
		  var r=/[0-9 - ,]$/; 
		  var codes = document.getElementById("codes");//代码值获得
		  var code = document.getElementsByName("Code");//代码种类获得
		  var goods = document.getElementsByName("goods");//权限种类种类获得
		  var breed = document.getElementsByName("breed");//上市品种获得
		  var commodity = document.getElementsByName("commodity");//上市商品获得
		  var codeValue = "";
		  var breedValue = "";
		  var goodsValue = "";
		  var commodityValue = "";
		  for(var i=0;i<code.length;i++){
		         if(code[i].checked){
		        	 codeValue += code[i].value;
		       }
		    }
		  for(var i=0;i<goods.length;i++){
		         if(goods[i].checked){
		        	 goodsValue += goods[i].value;
		       }
		    }
		  for(var i=0;i<breed.length;i++){
		         if(breed[i].checked){
		        	 breedValue += breed[i].value + ",";
		       }
		    }
		  for(var i=0;i<commodity.length;i++){
		         if(commodity[i].checked){
		        	 commodityValue += commodity[i].value + ",";
		       }
		    }
		 if ( trim(codeValue) == "" ) {
			 alert("代码种类不能为空！");
			 return false;
			 }
		 if ( trim(codeValue) != "") {
			 if ( trim(codes.value) == "" ) {
				 alert("代码值不能为空！");
				 codes.focus();
				 return false;
				 } else if ( !r.test(codes.value) ) {
					 alert("代码值格式错误，只能是字母，数字，中线，逗号组成！");
					 codes.focus();
					 return false;
					 }

			 }
		 if ( trim(goodsValue) == "" ) {
			 alert("权限种类不能为空！");
			 return false;
			 }
		if ( trim(goodsValue) == "1" ) {
			if (trim(breedValue) == "") {
				 alert("请选择上市品种！");
				 return false;
				}
			}
		if ( trim(goodsValue) == "2" ) {
			if (trim(commodityValue) == "") {
				 alert("请选择上市商品！");
				 return false;
				}
			}
	   customerForm.crud.value = "empty";
	   customerForm.submit();	
	   customerForm.empty.disabled = true;
	  }
	
}
//cancel
function cancel_onclick()
{
	window.close();
   //top.mainFrame.location.href = "<c:url value="/timebargain/baseinfo/firm.do?funcflg=firmPrivilege&firmID="/>" + customerForm.typeID.value;
}

		function kind_click(num){
			if (num == "1") {
				document.getElementById("breed").className = 'xian';
				document.getElementById("commodity").className = 'yin';
			}
			if (num == "2") {
				document.getElementById("commodity").className = 'xian';
				document.getElementById("breed").className = 'yin';
			}
			if (num == "") {
				document.getElementById("commodity").className = 'yin';
				document.getElementById("breed").className = 'yin';
			}
			
		}
</script>
	</head>

	<body leftmargin="0" topmargin="0" onLoad="return window_onload()"
		onkeypress="keyEnter(event.keyCode);">
		<table border="0" height="500" width="500" align="center">
			<tr>
				<td>
					<html:form action="/timebargain/baseinfo/firm.do?funcflg=batchSetSaveFirmPrivilege"
						method="POST" styleClass="form" >
						<fieldset class="pickList" >
							<legend class="common">
								<b>交易商权限维护
								</b>
							</legend>
							<table border="0" align="center" cellpadding="5" cellspacing="5"
								class="common">
								<tr>
										<td align="right">
											代码种类：
										</td>
										<td>
									    &nbsp;&nbsp;&nbsp;&nbsp;
									    	<!--  <input type="radio" name="Code" id="codeKind" value="1"  />加盟商代码&nbsp;&nbsp;-->
									  		<input type="radio" name="Code" id="codeKind" value="2" checked="checked"/>交易商代码
									  	</td>
									  	
								</tr>
								<tr>
										<td align="right">
											代码值：
										</td>
										<td >
												<input type="text" name="codes" id="codes" value=""  />
										</td>
								</tr>
								<tr>
										<td align="right">
											权限种类：
										</td>
										<td>
									    &nbsp;&nbsp;&nbsp;&nbsp;
									    	<input type="radio" name="goods" id="goodsKind" value="1"  onclick="kind_click(1)"/>品种&nbsp;&nbsp;
  											<input type="radio" name="goods" id="goodsKind"   value="2" onclick="kind_click(2)"/>商品
									  	</td>
									  	
								</tr>
								
								<%
									String type = (String)request.getAttribute("type");
								%>
								<tr id="breed" class="<%if("1".equals(type)){%>xian<%}else{%>yin<%}%>">
									<td align="right" >上市品种：</td>
									<td>
									
									<table cellpadding="0" class="common">
										<tr><td>
									
										<%
											if (request.getAttribute("breedSelect") != null) {
												List list = (List)request.getAttribute("breedSelect");
												int num = 1;
												for (int i = 0; i < list.size(); i++) {
													LabelValue lv = (LabelValue)list.get(i);
													String breedID = lv.getValue();
													String breedName = lv.getLabel();
													
													if ( !"".equals( breedID )){
															if  ( num > 4 ) {
																num = 1;
															%>
																<br/>
															<%
															}
												%>
													<input type="checkbox" name = "breed" value="<%=breedID%>" id="<%=breedID  %>"><%=breedName%></input>
												<%
													}
													num++;
												}
											}
										%><span class="req">*</span>
									</td></tr>
									</table>
									
									</td>
								</tr>
								<tr id="commodity" class="<%if("2".equals(type)){%>xian<%}else{%>yin<%}%>">
												<td align="right"  style="width: 70px;">上市商品：</td>
												<td>
												<table cellpadding="0" class="common" >
											<tr><td>
													<input type="checkbox" name = "allselect" id="allselect"  onclick="all_select()"/>全选
													<%
														if (request.getAttribute("commoditySelect") != null) {
															List list = (List)request.getAttribute("commoditySelect");
															int num = 1;
															for (int i = 0; i < list.size(); i++) {
																
																LabelValue lv2 = (LabelValue)list.get(i);
																String commodityID = lv2.getValue();
																if ( !"".equals( commodityID )){
																	if  ( num > 4 ) {
																		num = 1;
																	%>
																		<br/>
																	<%
																	}
																	%>
																<input type="checkbox" name ="commodity" value="<%=commodityID%>" id="<%=commodityID  %>"><%=commodityID%></input>
															<%
																}
																num++;
															}
														}
													%>
												<span class="req">*</span>
									</td></tr>
									</table>
												</td>
											</tr>
								<tr>
									<td align="right">
											买方权限：
									</td>
									<td>
										<html:select property="privilegeCode_B" style="width:120">
										   <html:option value=""></html:option>
				                           <html:option value="101">全权</html:option>
					                       <html:option value="102">只可订立</html:option>
					                       <html:option value="103">只可转让</html:option>
					                       <html:option value="104">无权</html:option>
			                            </html:select> <span class="req">*</span>
									</td>
								</tr>									
								<tr>
									<td align="right">
											卖方权限：
									</td>
									<td>
										<html:select property="privilegeCode_S" style="width:120">
										   <html:option value=""></html:option>
				                           <html:option value="201">全权</html:option>
					                       <html:option value="202">只可订立</html:option>
					                       <html:option value="203">只可转让</html:option>
					                       <html:option value="204">无权</html:option>
			                            </html:select> <span class="req">*</span>
									</td>
								</tr>
								<tr>
									<td colspan="2" align="center">
										<html:button property="save" styleClass="button"
											onclick="javascript:return save_onclick();">
											批量添加
										</html:button>
										<html:button property="empty" styleClass="button"
											onclick="javascript:return empty_onclick();">
											批量删除
										</html:button>
										<html:button property="cancel" styleClass="button"
											onclick="javascript:return cancel_onclick();">
											关闭
										</html:button>
									</td>
								</tr>
							</table>
						</fieldset>
						<tr>
				        	<td>
				        		<font color="red">注：代码值的输入格式只能是字母，数字，中线，逗号，例如，情况一：1001-1005(表示编号1001到1005区间内编号) 情况二：5,7,9(表示编号5、编号7和编号9)</font>
				        	</td>
        				</tr>
						<html:hidden property="crud"/>
						<html:hidden property="id"/>
						<html:hidden property="typeID" />
					</html:form>
				</td>
			</tr>
			
		</table>

		<%@ include file="/timebargain/common/messages.jsp"%>
	</body>
</html>
