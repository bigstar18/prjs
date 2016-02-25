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
		������ӽ�����Ȩ��
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
		var commodity = document.getElementsByName("commodity");//������Ʒ���
		for(var i=0;i<commodity.length;i++){
			commodity[i].checked = true;
	       }
	    } else {
	    	var commodity = document.getElementsByName("commodity");//������Ʒ���
			for(var i=0;i<commodity.length;i++){
				commodity[i].checked = false;
		       }

		    }

}


//save
function save_onclick()
{
  if (confirm("��ȷ��Ҫ�ύ��")) {
	  var r=/[0-9 - ,]$/; 
	  var codes = document.getElementById("codes");//����ֵ���
	  var code = document.getElementsByName("Code");//����������
	  var goods = document.getElementsByName("goods");//Ȩ������������
	  var breed = document.getElementsByName("breed");//����Ʒ�ֻ��
	  var commodity = document.getElementsByName("commodity");//������Ʒ���
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
		 alert("�������಻��Ϊ�գ�");
		 return false;
		 }
	 if ( trim(codeValue) != "") {
		 if ( trim(codes.value) == "" ) {
			 alert("����ֵ����Ϊ�գ�");
			 codes.focus();
			 return false;
			 } else if ( !r.test(codes.value) ) {
				 alert("����ֵ��ʽ����ֻ������ĸ�����֣����ߣ�������ɣ�");
				 codes.focus();
				 return false;
				 }

		 }
	 if ( trim(goodsValue) == "" ) {
		 alert("Ȩ�����಻��Ϊ�գ�");
		 return false;
		 }
	if ( trim(goodsValue) == "1" ) {
		if (trim(breedValue) == "") {
			 alert("��ѡ������Ʒ�֣�");
			 return false;
			}
		}
	if ( trim(goodsValue) == "2" ) {
		if (trim(commodityValue) == "") {
			 alert("��ѡ��������Ʒ��");
			 return false;
			}
		}
  	if (customerForm.privilegeCode_B.value == "") {
   		alert("��Ȩ�޲���Ϊ�գ�");
   		customerForm.privilegeCode_B.focus();
   		return false;
   }
    if (customerForm.privilegeCode_S.value == "") {
   		alert("����Ȩ�޲���Ϊ�գ�");
   		customerForm.privilegeCode_S.focus();
   		return false;
   }
   customerForm.crud.value = "save";
   customerForm.submit();	
   customerForm.save.disabled = true;
  }
   
}
function empty_onclick() {
	if (confirm("��ȷ��Ҫ�ύ��")) {
		  var r=/[0-9 - ,]$/; 
		  var codes = document.getElementById("codes");//����ֵ���
		  var code = document.getElementsByName("Code");//����������
		  var goods = document.getElementsByName("goods");//Ȩ������������
		  var breed = document.getElementsByName("breed");//����Ʒ�ֻ��
		  var commodity = document.getElementsByName("commodity");//������Ʒ���
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
			 alert("�������಻��Ϊ�գ�");
			 return false;
			 }
		 if ( trim(codeValue) != "") {
			 if ( trim(codes.value) == "" ) {
				 alert("����ֵ����Ϊ�գ�");
				 codes.focus();
				 return false;
				 } else if ( !r.test(codes.value) ) {
					 alert("����ֵ��ʽ����ֻ������ĸ�����֣����ߣ�������ɣ�");
					 codes.focus();
					 return false;
					 }

			 }
		 if ( trim(goodsValue) == "" ) {
			 alert("Ȩ�����಻��Ϊ�գ�");
			 return false;
			 }
		if ( trim(goodsValue) == "1" ) {
			if (trim(breedValue) == "") {
				 alert("��ѡ������Ʒ�֣�");
				 return false;
				}
			}
		if ( trim(goodsValue) == "2" ) {
			if (trim(commodityValue) == "") {
				 alert("��ѡ��������Ʒ��");
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
								<b>������Ȩ��ά��
								</b>
							</legend>
							<table border="0" align="center" cellpadding="5" cellspacing="5"
								class="common">
								<tr>
										<td align="right">
											�������ࣺ
										</td>
										<td>
									    &nbsp;&nbsp;&nbsp;&nbsp;
									    	<!--  <input type="radio" name="Code" id="codeKind" value="1"  />�����̴���&nbsp;&nbsp;-->
									  		<input type="radio" name="Code" id="codeKind" value="2" checked="checked"/>�����̴���
									  	</td>
									  	
								</tr>
								<tr>
										<td align="right">
											����ֵ��
										</td>
										<td >
												<input type="text" name="codes" id="codes" value=""  />
										</td>
								</tr>
								<tr>
										<td align="right">
											Ȩ�����ࣺ
										</td>
										<td>
									    &nbsp;&nbsp;&nbsp;&nbsp;
									    	<input type="radio" name="goods" id="goodsKind" value="1"  onclick="kind_click(1)"/>Ʒ��&nbsp;&nbsp;
  											<input type="radio" name="goods" id="goodsKind"   value="2" onclick="kind_click(2)"/>��Ʒ
									  	</td>
									  	
								</tr>
								
								<%
									String type = (String)request.getAttribute("type");
								%>
								<tr id="breed" class="<%if("1".equals(type)){%>xian<%}else{%>yin<%}%>">
									<td align="right" >����Ʒ�֣�</td>
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
												<td align="right"  style="width: 70px;">������Ʒ��</td>
												<td>
												<table cellpadding="0" class="common" >
											<tr><td>
													<input type="checkbox" name = "allselect" id="allselect"  onclick="all_select()"/>ȫѡ
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
											��Ȩ�ޣ�
									</td>
									<td>
										<html:select property="privilegeCode_B" style="width:120">
										   <html:option value=""></html:option>
				                           <html:option value="101">ȫȨ</html:option>
					                       <html:option value="102">ֻ�ɶ���</html:option>
					                       <html:option value="103">ֻ��ת��</html:option>
					                       <html:option value="104">��Ȩ</html:option>
			                            </html:select> <span class="req">*</span>
									</td>
								</tr>									
								<tr>
									<td align="right">
											����Ȩ�ޣ�
									</td>
									<td>
										<html:select property="privilegeCode_S" style="width:120">
										   <html:option value=""></html:option>
				                           <html:option value="201">ȫȨ</html:option>
					                       <html:option value="202">ֻ�ɶ���</html:option>
					                       <html:option value="203">ֻ��ת��</html:option>
					                       <html:option value="204">��Ȩ</html:option>
			                            </html:select> <span class="req">*</span>
									</td>
								</tr>
								<tr>
									<td colspan="2" align="center">
										<html:button property="save" styleClass="button"
											onclick="javascript:return save_onclick();">
											�������
										</html:button>
										<html:button property="empty" styleClass="button"
											onclick="javascript:return empty_onclick();">
											����ɾ��
										</html:button>
										<html:button property="cancel" styleClass="button"
											onclick="javascript:return cancel_onclick();">
											�ر�
										</html:button>
									</td>
								</tr>
							</table>
						</fieldset>
						<tr>
				        	<td>
				        		<font color="red">ע������ֵ�������ʽֻ������ĸ�����֣����ߣ����ţ����磬���һ��1001-1005(��ʾ���1001��1005�����ڱ��) �������5,7,9(��ʾ���5�����7�ͱ��9)</font>
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
