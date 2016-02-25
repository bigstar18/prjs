<%@ page language="java" pageEncoding="GBK"%>
<%@ page import="java.util.List" %>
<%@ page import="gnnt.MEBS.timebargain.manage.service.SettleManage" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/timebargain/common/taglibs.jsp"%>
<base target="_self">
<html>
<head>
<%	//首次访问显示商品代码
		List cmdList=SettleManage.getCommoditysNew();
    pageContext.setAttribute("cmdList", cmdList);
    //录入
    String opt = request.getParameter("opt");
    if( opt!= null && "create".equals(opt))
    {
		//保存数据
		String CommodityID = request.getParameter("CommodityID");
		String FirmID_B = request.getParameter("FirmID_B");
		String FirmID_S = request.getParameter("FirmID_S");
		int result = Integer.parseInt(request.getParameter("handleResult"));
		long Quantity = Long.parseLong(request.getParameter("Quantity"));
		int returnResult = SettleManage.createSettle(CommodityID,FirmID_B,FirmID_S,Quantity,result);
		if(returnResult==1){
		%>
		<script type="text/javascript">
			alert("交收配对信息录入成功！");
			window.returnValue="-1";
			window.close();
		</script>
		<%
		}else if(returnResult==-1) {
		  long checkResult=SettleManage.checkInsertSettle(CommodityID,FirmID_B,FirmID_S,Quantity);
		%>
		<script type="text/javascript">
			alert("录入数据不符合交收条件！\n实际可交收的数量为：" + <%=checkResult%> );
		</script>
		<%
		}else if(returnResult==-2){
		%>
		<script type="text/javascript">
			alert("操作产生异常！");
		</script>
		<%
		}
    }
%>
</head>
<body>
	<form name="frm" method="post" action="">
	<br>
	<fieldset width="100%">
		<legend class="common"><b>录入</b></legend>
		<BR>
		<span>
			<table class="common" border="0" cellspacing="0" cellpadding="0" width="100%" align="center">
				<!-- commodity -->
				<tr class="common">
					<td align="right" width="50%" height="30">商品代码：<input type="text" name="CommodityID" readonly="readonly">&nbsp;&nbsp;</td>
					<td align="left" width="50%">
						<select name="cmdid" onchange="selectObj('cmdid')">
						<option>请选择</option>
						<c:forEach items="${cmdList}" var="result">
							 <option value="${result.commodityid}">${result.commodityid}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<!-- buyer -->
				<tr>
					<td class="common" align="right" width="50%" height="30">买方代码：<input type="text" name="FirmID_B" readonly="readonly">&nbsp;&nbsp;</td>
					<td align="left" width="50%">
						<!-- <select name="buyerid" onblur="selectObj('buyerid')"> -->
						<div id="div1">
							<select name="buyerid">
								<option>请选择</option>
							</select>
						</div>						
					</td>
				</tr>
				<!-- seller -->
				<tr>
					<td class="common" align="right" width="50%" height="30">卖方代码：<input type="text" name="FirmID_S" readonly="readonly">&nbsp;&nbsp;</td>
					<td align="left" width="50%">						
						<!-- <select name="sellerid" onblur="selectObj('sellerid')"> -->
						<div id="div2">
							<select name="sellerid">
								<option>请选择</option>
							</select>
						</div>						
					</td>
				</tr>
				<!-- 执行结果 -->
				<tr class="common">
					<td align="right" width="50%" height="30">交收结果：<input type="text" name="result" readonly="readonly">&nbsp;&nbsp;</td>
					<td align="left" width="50%">
						<select name="handleResult" id="handleResult" onchange="select(3);">
						<option value="0">请选择</option>
						<option value="1">履约</option>
						<option value="2">买方违约</option>
						<option value="3">卖方违约</option>
						<option value="4">双方违约</option>
						</select>
					</td>
				</tr>
				<!-- amount -->
				<tr class="common">
					<td align="right" width="50%" height="30">交收数量：<input type="text" name="Quantity" onkeyup="this.value=this.value.replace(/\D/g,'')">&nbsp;&nbsp;
					</td>
					<td align="left" width="50%"><div id="div3">&nbsp;<font color="red">*</font></div></td>
				</tr>
				<tr><td colspan="2">&nbsp;</td></tr>
				<!-- submit -->
				<tr class="common">
					<td colspan="2" align="center" height="30">
						<input type="button" name="subbut" class="button" onclick="submitMSG();" value="提交" class="mdlbtn">
						&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" class="button" onclick="window.close();" value="返回" class="mdlbtn">
						<input type="hidden" name="opt">
				</tr>
			</table><br>
		</span>
		</fieldset>
	</form>
</body>
</html>
<script type="text/javascript">

	function submitMSG()
	{
		var CommodityID = frm.CommodityID.value;
		var FirmID_B = frm.FirmID_B.value;
		var FirmID_S = frm.FirmID_S.value;
		var Quantity = frm.Quantity.value;
		var result = frm.handleResult.value;
		var resultMSG = "";
		if( result == 1 )
		{
			resultMSG = "履约";	
		}
		else if( result == 2 )
		{
			resultMSG = "买方违约";	
		}
		else if( result == 3 )
		{
			resultMSG = "卖方违约";	
		}
		else if( result == 4 )
		{
			resultMSG = "双方违约";	
		}
		if(CommodityID!=""&&FirmID_B!=""&&FirmID_S!=""&&Quantity!=""&&result!=""){
		
		     if(Quantity.search("^[1-9]*[1-9][0-9]*$")!=0 || Quantity  <= 0)
             {
                  alert("请确认交收数量为正整数！");
                  return false;
             }else{
		    
			   //alert("开始录入******************");
			   var dismark = false;
			   if(confirm("要提交的数据为：\n商品："+CommodityID+
								"\n买方："+FirmID_B+"，卖方："+FirmID_S+
								"\n交收结果："+resultMSG+"，交收数量："+Quantity+
								"\n确认要提交么？"))
			   {
				  frm.subbut.disabled=true;
				  dismark = true;
			   }
			   if(dismark){
				  frm.opt.value="create";
				  frm.submit();				
			   }
			}
		}else{
			alert("交收配对数据不完整,请重新录入！");
			return false;
		}		
	}
	
	var request = new ActiveXObject("Microsoft.XMLHTTP");
	var buyList;// = new Array(); 
	var sellerList;// = new Array();
	var responseResult;
	function selectObj(v)
	{
		var filter ;
		//选择后给商品代码赋值
		var objValue1 = frm.cmdid.value;
		frm.CommodityID.value = objValue1;
		filter = "CommodityID="+objValue1;				
			
		if(v == "cmdid")
		{//(第一次使用AJAX)
			//alert("开始调用*************firstRequest");
			frm.FirmID_B.value="";
			frm.FirmID_S.value="";
			frm.Quantity.value="";
			frm.result.value="";
			document.getElementById("div3").innerHTML="&nbsp;<font color='red'>*</font>";
			firstRequest(filter);
		}
		else if(v == "Quantity")
		{
			//alert("开始调用*************secondRequest");
			//填写数量后验证(第二次使用AJAX)
				var objValue2 = frm.FirmID_B.value;
				var objValue3 = frm.FirmID_S.value;
				var objValue4 = trim(frm.Quantity.value);
				var objValue5	= frm.result.value;
				var resultStutas  = true;
				if(objValue5 == ""){
					resultStutas  = false;
					alert("请选择交收的执行结果！");
				}
				//objValue4.search("^[0-9]*[1-9][0-9]*$")!=0
				if(objValue4.search("^[1-9]*[1-9][0-9]*$")!=0 || objValue4 <= 0){
					resultStutas  = false;
					alert("请确认交收数量为正整数！");
				}
				if(resultStutas){
					filter = filter+"&FirmID_B="+objValue2+"&FirmID_S="+objValue3+"&Quantity="+objValue4;
					secondRequest(filter);								
				}
			}
	}
	
	
	function firstRequest(v)
	{
		request.onreadystatechange = fisrtResponse;
		request.open("post","getData.jsp?"+v,false);
		request.send();
		request.abort();
		firstCopyValues(buyList,sellerList);
	}
	function secondRequest(v)
	{
		request.onreadystatechange = secondResponse;
		request.open("post","checkData.jsp?"+v,false);
		request.send();
		request.abort();
		
		//secondCopyValues(responseResult);
	}
	function fisrtResponse()
	{
		// only if req shows "loaded"
		if (request.readyState == 4) {
		   // only if "OK"
		   //if (request.status == 200) {
			var result = request.responseText;
			var bs = result.split("[]");
			buyList = bs[0];			
			sellerList = bs[1];
			//}	
		}
	}
	function secondResponse()
	{
		// only if req shows "loaded"
		if (request.readyState == 4) {
		   // only if "OK"
		   //if (request.status == 200) {
			var responseResult = request.responseText;
			var sign = trim(responseResult);
				//-1检查通过 可以进行提交录入，大于等于0不通过，返回值是实际可交收的数量
				if(sign == -1){
					//alert("交收配对成功,可以录入！");
				}else if(sign >= 0){
					alert("您提交的数量大于最大交收数量["+sign+"],请重新录入数据！");
					document.getElementById("div3").innerHTML="<font color='red'>最大交收数量["+sign+"]</font>";
					frm.Quantity.value="";
					frm.Quantity.focus();
				}
			//}	
		}
	}
	function firstCopyValues(bl,sl)
	{
		var blist = bl.split(";");
		var slist = sl.split(";");
		var buymsg="<select name='buyerid' id='buyerid' onChange='select(1);'><option>请选择</option>";
		var sellmsg="<select name='sellerid' id='sellerid' onChange='select(2);'><option>请选择</option>";
		for(var b = 0 ; b < blist.length ; b ++)
		{
			if(blist[b]!=""){
				buymsg = buymsg+"<option value='"+blist[b]+"'>"+blist[b]+"</option>";
			}
		}
		for(var s = 0 ; s < slist.length ; s ++)
		{
			if(slist[s]!=""){
				sellmsg = sellmsg+"<option value='"+slist[s]+"'>"+slist[s]+"</option>";
			}
		}
		buymsg = buymsg+"</select>";
		sellmsg = sellmsg+"</select>";
		document.getElementById("div1").innerHTML=buymsg;
		document.getElementById("div2").innerHTML=sellmsg;
	}
	
	function select(v)
	{
		var selectValue ;
			switch (v)
			{
				case 1:
					selectValue = document.getElementById("buyerid").value;
					frm.FirmID_B.value = selectValue;
					frm.FirmID_B.readOnly = true;
					break;
				case 2:
					selectValue = document.getElementById("sellerid").value;
					frm.FirmID_S.value = selectValue;
					frm.FirmID_S.readOnly = true;
					break;
				case 3:
					selectValue = document.getElementById("handleResult").value;
					var strValue = "";
					if(selectValue == 1){
						strValue="履约";
					}else if(selectValue == 2){
						strValue="买方违约";
					}else if(selectValue == 3){
						strValue="卖方违约";
					}else if(selectValue == 4){
						strValue="双方违约";
					}
					frm.result.value = strValue;
					frm.result.readOnly = true;
					break;	
			}
	}
	//清除前后空格
	function trim ( str ) {
		regExp = /\S/
		
		if ( regExp.test(str) == false )
			return "";
		
		regExp = /(^\s*)(.*\S)(\s*$)/
		regExp.exec(str);
		
		return RegExp.$2;
	}
</script>
