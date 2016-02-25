<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../public/session.jsp"%>
<html xmlns:MEBS>
  <head>
	<IMPORT namespace="MEBS" implementation="<%=basePath%>public/jstools/calendar.htc">
	<title><%=TITLE%></title>
	<style type="text/css">
	<!--
	.cd_bt {
		font-size: 12px;
		line-height: 16px;
		font-weight: bold;
		text-decoration: none;
		text-align: right;
		padding-right: 5px;
	}
	-->
    </style>
</head>

<body>
        <form action="" name="frm" method="POST" targetType="hidden">
	
		<input type="hidden" name="firmId" id="firmId"/>
		<input type="hidden" name="warehouseId" id="warehouseId" />
		<input type="hidden" name="commodityId" id="commodityId"/>
		<input type="hidden" name="availablenum" id="availablenum"/>
		<input type="hidden" name="operate" id="operate" value="${value}">
	

		<fieldset width="100%">
		<legend>转出库单</legend>
			<table width="100%" border="0" align="center" cellpadding="0"
					cellspacing="0" bordercolor="#333333"
					style="border-collapse:collapse;">
					<tr height="40px">
						<td colspan="5" class="cd_hr">
							<br>
							&nbsp;<font color=red></font>
						</td>
					</tr>
				</table>
				<table width="100%" border="0" align="center" cellpadding="0px"
					cellspacing="0" bordercolor="#333333"
					style="border-collapse:collapse;">
										<tr height="30px">
						<td  width="20%" align="left" class="cd_bt">
							入库单单号：<input type="text" name="enterWareId" id="enterWareId" size=12 onblur="enterWareIdajaxChange();" maxlength="10" onkeyup="this.value=this.value.replace(/\D/g,'')"/>
							<font color="red"><span id="enterWareIdflag"></span></font>
						</td>
						
						<td colspan="3" width="60%">
							&nbsp;
						</td>
					</tr>
				</table>
				<table id="tableList" width="100%" border="1" align="center"
					cellpadding="0px" cellspacing="0" bordercolor="#333333"
					style="border-collapse:collapse;">

					<tr height="25px">
						<td width="15%" class="cd_bt">
							${FIRMID}：
						</td>
						<td width="15%" class="cd_list1">
								<span id="_firmId"></span>
						</td>
						<td class="cd_bt">
							${FIRMNAME}：
						</td>
						<td  class="cd_list1" colspan=3>
								<span id="_firmname"></span>
						</td>
						
					</tr>
					<tr height="25px">
						<td width="15%" class="cd_bt">
							仓库代码：
						</td>
						<td width="15%" class="cd_list1">
								<span id="_warehouseId"></span>
						</td>
						<td class="cd_bt" >
							仓库名称：
						</td>
						<td  class="cd_list1"  colspan=3>
								<span id="_warehouseName"></span>
						</td>
						
					</tr>
					<tr height="25px">
						<td width="15%" class="cd_bt">
							品种名称：
						</td>
						<td width="15%" class="cd_list1" >
							<span id="_commodityName"></span>
						</td>
						
						<td class="cd_bt">
							出库重量：
						</td>
						<td class="cd_list1" >
							&nbsp;<input type="text" name="weight" id="weight" maxlength="10">
						</td>
								<td class="cd_bt">拟出库时间：</td>
							<td class="cd_list1" >
								<MEBS:calendar eltID="planOutDate" eltName="planOutDate"
									eltCSS="date" eltStyle="width:80px"
									eltImgPath="<%=skinPath%>/images/"
									eltValue="<fmt:formatDate value="<%=new java.util.Date()%>" pattern="yyyy-MM-dd" />"
								/>
							</td>
					</tr>
					  <tr height="25px">
					 			<td class="cd_bt">联系电话：	</td>
							<td class="cd_list1" >
								&nbsp;<input type="text" name="tel" id="tel" maxlength="20">
							</td>
					 </tr>
					</table>
			<br>
					<table width="100%" border="0" align="center" cellpadding="0px"
						cellspacing="0" bordercolor="#333333"
						style="border-collapse:collapse;" >
						<tr height="25px">
							<td width="15%" class="cd_bt" align="right">
								仓库经办人：
							</td>
							<td width="15%" class="cd_list1" align="left">
								<input name="agency" id="agency" type="text" size="15"
									value="${outWare.agency}" class="form_kr"
									reqfv="required;仓库经办人" readOnly>
							</td>
							<td width="15%" class="cd_bt" align="right">
								仓库负责人：
							</td>
							<td width="15%" class="cd_list1" align="left">
								<input name="responsibleman" id="responsibleman" type="text" size="15"
									value="${outWare.responsibleman}" class="form_kr"
									reqfv="required;仓库负责人" readOnly>
							</td>
							<td width="15%" class="cd_bt" align="right">
								交易商经办人：
							</td>
							<td width="15%" class="cd_list1" align="left">
								<input name="dealerAgency" id="dealerAgency" type="text" size="15"
									value="${outWare.dealerAgency}" class="form_kr"
									reqfv="required;交易商经办人" readOnly>
							</td>
						</tr>
					</table>
			<br>
			<div align="center">
				<input class="lgrbtn" type="button" onClick="outWareAdd()" value="入库单转出库单">&nbsp;&nbsp;
				<input class="smlbtn" type="button" value="返回" onclick="returnListPage();">
			</div>
		</fieldset>
        </form>
</body>
</html>
<script>
	function enterWareIdajaxChange() {
	  var enterWareId = $("#enterWareId").val();
   		$.ajax({   
    				  type : "POST",     //HTTP 请求方法,默认: "GET"   
    				  url : "<%=basePath%>servlet/ajaxController.${POSTFIX}?funcflg=enterWareAjax",   //发送请求的地址   
    				  data : "enterWareId=" + enterWareId, //发送到服务器的数据   
    				  dataType : "xml",         //预期服务器返回的数据类型   
    				  error: function(xml) { alert('Error loading XML document:' +xml); }, 
    				  success : function(data){
    				  				if($(data).find("result").text()==-1){
    				  					$("#enterWareIdflag").html("没有该入库单");
    				  					$("#enterWareId").val("");
    				  					$("#enterWareId").focus();
    				  				}else if($(data).find("result").text()==-2) {
										$("#enterWareIdflag").html("此入库单状态不符");
										$("#enterWareId").val("");
										$("#enterWareId").focus();
									}else{
    				  					$("#enterWareIdflag").html("可用重量:"+$(data).find("availablenum").text());
    				  					$("#availablenum").val($(data).find("availablenum").text());
    				  					$("#_firmId").html($(data).find("dealerid").text());
    				  					$("#firmId").val($(data).find("dealerid").text());
    				  					$("#_firmname").html($(data).find("dealername").text());
    				  					$("#_warehouseId").html($(data).find("warehouseid").text());
    				  					$("#warehouseId").val($(data).find("warehouseid").text());
    				  					$("#_warehouseName").html($(data).find("warehousename").text());
    				  					$("#_commodityName").html($(data).find("commodityname").text());	
    				  					$("#commodityId").val($(data).find("commodityid").text());	
    				  					
    				  					$("#agency").val($(data).find("agency").text());		
    				  					$("#responsibleman").val($(data).find("responsibleman").text());		
    				  					$("#dealerAgency").val($(data).find("dealerAgency").text());			
    				  				}
    				  			}        //请求成功后回调函数   
 					 });
			}
	
	
	function outWareAdd(){
		if(frm.enterWareId.value==""){
			frm.enterWareId.focus();
			alert("入库单单号不能为空！");
			return false;
		}
		if(frm.tel.value==""){
			frm.tel.focus();
			alert("联系电话不能为空");
			return false;
		}
		if(frm.weight.value==""){
			frm.weight.focus();
			alert("出库重量不能为空");
			return false;
		}
		if(!IsIntOrFloat(frm.weight.value)){
			alert("出库重量必须为大于0的数字");
			frm.weight.value="";
			frm.weight.focus();
			return false;
		}
		if(parseFloat(frm.weight.value)>parseFloat(frm.availablenum.value)){
			frm.weight.focus();
			frm.weight.value="";
			alert("出库重量不能大于可用重量");
			return false;
		}
	
		frm.action = "<%=basePath%>servlet/outWareController.${POSTFIX}?funcflg=outWareAdd";
		if(confirm("确定执行此操作？")){
			frm.submit();
		}
	}
	
	function returnListPage(){
		frm.action = "<%=basePath%>servlet/outWareController.${POSTFIX}?funcflg=outWareReturn";
		frm.submit();
	}
</script>
