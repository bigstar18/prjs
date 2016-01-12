<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="common/util.jsp" %>
<html xmlns:MEBS>
<head>
<%
    String reportTiltle = "分商品成交量统计表";
%>
<%
   List breedList=getList("select breedId from T_A_Breed");
   pageContext.setAttribute("breedList",breedList);

%>
<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
<script language="javascript">

function onloaddate(){
	var month=new Date().getMonth()+1;
	if(month.toString().length==1){
		month="0"+month;
	}
	var day=new Date().getDate();
	if(day.toString().length==1){
		day="0"+day;
	}	
	document.getElementById("zDate").value=new Date().getYear()+'-'+month+'-'+day;
	document.getElementById("zEndDate").value=new Date().getYear()+'-'+month+'-'+day;
	
}
</script>

</head>

<body onload="onloaddate()">
<FORM METHOD=POST ACTION="" name="frm">
<table border="0" height="40%" width="60%" align="center">
			<tr>
				<td>
<fieldset class="pickList" >
	<legend class="common">
		<b>分商品成交量统计表 </b>
	</legend>
	<table border="0" align="center" cellpadding="5" cellspacing="5" class="common">
		<tr>
	        <td align="center" colspan="2" style="color:red">（填空为查询全部）</td>
	      </tr>
	      <tr>
	        <td align="right">品种：</td>
	        <td>
	        <INPUT TYPE="text" NAME="breed" style="ime-mode:disabled"  id="breedId" value="" size="10" maxlength="10" onblur="dealcontentStartBreed(this)" onkeypress="notSpace()"/>
	         <SELECT name="selectStartBreed" onchange="dealcontentBreed(this)"  >
	         <OPTION value="">请选择</OPTION>
	         <c:forEach items="${breedList}" var="breed">
		     <option value="${breed.breedId}">${breed.breedId}</option>
			 </c:forEach>
	          </SELECT>   
			  <span class="req"></span>
	        </td>
	      </tr>	
		 <tr>
        <td align="right">起始商品：</td>
        <td>
        <INPUT TYPE="text" NAME="zStartCommodity" style="ime-mode:disabled"   value="" size="10" maxlength="10" onblur="dealcontentStrartCommodity(this)" onkeypress="notSpace()">
         <SELECT name="selectStartCommodity" onchange="dealcontentCommodity(this)" style="width:100px;" >
         <OPTION value="">请选择</OPTION>
         <c:forEach items="${listAsc}" var="commodity">
	     <option value="${commodity.COMMODITYID}">${commodity.COMMODITYID}</option>
		 </c:forEach>
          </SELECT>   
        </td>
      </tr> 
       <tr>
        <td align="right">结束商品：</td>
        <td>
          <div align="left">
            <INPUT TYPE="text" NAME="zEndCommodity" style="ime-mode:disabled"  value="" size="10" maxlength="10" onblur="dealcontentEndCommodity1(this)" onkeypress="notSpace()">
            <SELECT  name="selectEndCommodity" onchange="dealcontentCommodity1(this)" style="width:100px;">
            <OPTION value="">请选择</OPTION>
            <c:forEach items="${listDESC}" var="commodity">
	        <option value="${commodity.COMMODITYID}">${commodity.COMMODITYID}</option>
		    </c:forEach>
          </SELECT>   
            </div></td>
      </tr>     
		 
      <tr>
        <td align="right">起始日期：</td>
        <td >
        	<input type="text" style="width: 100px" id="zDate"
				class="wdate" maxlength="10" name="zDate"
				value="" onblur=""
				onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
			<span class="required">*</span>
      </tr>
      <tr>
        <td align="right">结束日期：</td>
        <td >
        	<input type="text" style="width: 100px" id="zEndDate"
				class="wdate" maxlength="10" name="zEndDate"
				value="" onblur=""
				onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
			<span class="required">*</span>
      </tr>

	
    </table>
    
	<table border="0" align="center" cellpadding="5" cellspacing="5" class="common">
		<tr>
				<td>
				<rightButton:rightButton name="查看" onclick="return query('pdf')" className="btn_sec" action="${basePath}/timebargain/comTradeReport/comTradeReport.action" id="add"></rightButton:rightButton>
	     		</td>
	     		<td>
	     		<rightButton:rightButton name="保存为excel" onclick="return query('excel')" className="btn_sec1" action="${basePath}/timebargain/comTradeReport/comTradeReportExcel.action" id="saveExcel"></rightButton:rightButton>
	     		</td>
		</tr>
	</table>
	</fieldset>
	</td>
	</tr>
</table>
</FORM>
</body>

</html>
<SCRIPT LANGUAGE="JavaScript">

function ajaxCommodityIdByBreedrId(breedId){
	$.ajax({
		type: "post",
		url: "../../ajaxcheck//tAPLStatisticReport/checkBreed.action",
		data: {
    	breedId: breedId
			  },
		success : function(data){
				    var selectStartCommodity=document.getElementById("selectStartCommodity");
		    		var selectEndCommodity=document.getElementById("selectEndCommodity");
		    		selectStartCommodity.length=1;
		    		selectEndCommodity.length=1;

		    		var datas=data.split("|");
		    		for(var i=0;i<datas.length-1;i++){
		    			var str=datas[i].split("=")[1].substring(0,datas[i].split("=")[1].length-1);
		    			var option=document.createElement("option");
		    			option.text=str;
		    			option.value=str;
		    			selectStartCommodity.add(option);
		    		}
		    		for(var i=datas.length-2;i>=0;i--){
		    			var str=datas[i].split("=")[1].substring(0,datas[i].split("=")[1].length-1);
		    			var option=document.createElement("option");
		    			option.text=str;
		    			option.value=str;
		    			selectEndCommodity.add(option);
		    		}
		          }
	});
}
function dealcontentBreed(sel)
{
    document.forms[0].breed.value=sel.options[sel.selectedIndex].value;
    ajaxCommodityIdByBreedrId(sel.options[sel.selectedIndex].value);
}
function dealcontentStartBreed(sel)
{
    var len=document.forms[0].selectStartCommodity.options.length;
    
    for(var i=0;i<len;i++)
    {
        if(document.forms[0].selectStartCommodity.options[i].value-sel.value==0)
        {
            document.forms[0].selectStartCommodity.options[i].selected=true;
            break;
        }
    }
    var breedId=document.getElementById("breed").value;
    ajaxCommodityIdByBreedrId(breedId);
}

function dealcontentCommodity(sel)
{
    document.forms[0].zStartCommodity.value=sel.options[sel.selectedIndex].value;
}
function dealcontentStrartCommodity(sel)
{

    var len=document.forms[0].selectStartCommodity.options.length;
    
    for(var i=0;i<len;i++)
    {
        if(document.forms[0].selectStartCommodity.options[i].value-sel.value==0)
        {
            document.forms[0].selectStartCommodity.options[i].selected=true;
            break;
        }
    }
}

function dealcontentCommodity1(sel)
{
    document.forms[0].zEndCommodity.value=sel.options[sel.selectedIndex].value;
}
function dealcontentEndCommodity1(sel)
{

    var len=document.forms[0].selectEndCommodity.options.length;
    
    for(var i=0;i<len;i++)
    {
        if(document.forms[0].selectEndCommodity.options[i].value-sel.value==0)
        {
            document.forms[0].selectEndCommodity.options[i].selected=true;
            break;
        }
    }
}
function query(exportTo)
{
	var zStartCommodity=document.forms[0].zStartCommodity.value;
	var zEndCommodity=document.forms[0].zEndCommodity.value;
    var zDate=document.forms[0].zDate.value;
	var zEndDate=document.forms[0].zEndDate.value;	
	var breedId =document.forms[0].breed.value;

	if (zStartCommodity == "" && zEndCommodity !="") {
		alert("起始商品不能为空！");
		return false;
	}
	if (zEndCommodity == "" && zStartCommodity != "") {
		alert("结束商品不能为空！");
		return false;
	}
	if (zDate == "") {
		alert("起始日期不能为空！");
		return false;
	}
	if (zEndDate == "") {
		alert("结束日期不能为空！");
		return false;
	}
	if (zDate > zEndDate) {
		alert("起始日期不能大于结束日期！");
		return false;
	}
	if(zStartCommodity == "" && zEndCommodity ==""){
		zStartCommodity =null;
		zEndCommodity = null;
	}
	if(breedId ==""){
		breedId = null;
	}
	if(exportTo=="excel"){
		//获取配置权限的 URL
		var url = "${mgrPath}/app/timebargain/printReport/comTradeReportExcel.jsp?startCommodityID=" + zStartCommodity + "&endCommodityID=" + zEndCommodity +"&startClearDate="+zDate+"&endClearDate="+ zEndDate +"&title=分商品成交量统计表&breedId="+breedId;
		document.location.href = url;
	 }else{
		//获取配置权限的 URL
		var url = "${mgrPath}/app/timebargain/printReport/comTradeReport.jsp?startCommodityID=" + zStartCommodity + "&endCommodityID=" + zEndCommodity +"&startClearDate="+zDate+"&endClearDate="+ zEndDate +"&title=分商品成交量统计表&breedId="+breedId;
		showDialog(url, "", 900, 650);
		
	}
}
</SCRIPT>