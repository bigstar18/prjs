<%@ include file="util.jsp" %>
<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page contentType="text/html;charset=GBK"%> 



<%
  // List firmAscList=getList("select firmId from t_firm order by firmId");
  // List firmDescList=getList("select firmId from t_firm order by firmId desc");
  // pageContext.setAttribute("firmAscList",firmAscList);
  // pageContext.setAttribute("firmDescList",firmDescList);
   List breedList=getList("select breedId from e_breed");
   pageContext.setAttribute("breedList",breedList);
   List brokerIdList=getList("select brokerId from m_b_broker");
   pageContext.setAttribute("brokerIdList",brokerIdList);
   List commodityStartList = getList("select commodityID from (select commodityID from t_commodity) union (select distinct commodityID from t_settlecommodity) order by commodityID asc");
   List commodityEndList = getList("select commodityID from (select commodityID from t_commodity) union (select distinct commodityID from t_settlecommodity) order by commodityID desc");
   pageContext.setAttribute("commodityStartList",commodityStartList);
   pageContext.setAttribute("commodityEndList",commodityEndList);
   List CateGoryIdList=getList("select id,name from m_firmcategory order by id");  
   pageContext.setAttribute("CateGoryIdList",CateGoryIdList);
   	Date sysdate = new Date();
	SimpleDateFormat df =new SimpleDateFormat("yyyy-MM-dd");
	String date = df.format(sysdate);
	System.out.println("date:"+date);
%>
<html xmlns:MEBS>
<head>
<%@ include file="/timebargain/widgets/calendar/calendar.jsp" %>
<IMPORT namespace="MEBS" implementation="<c:url value="/timebargain/scripts/calendar2.htc"/>">

<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
<script language="javascript">
function window_onload(){
	highlightFormElements();
	document.forms[0].zDate.value = '<%=date%>';
}
</script>
<script language="javascript" for="document" event="onkeydown">
<!--
  if(event.keyCode==13)
     query();
-->
</script>

</head>

<body  onload="return window_onload()">
<FORM METHOD=POST ACTION="" name="frm">
<table border="0" height="40%" width="60%" align="center">
			<tr>
				<td>
<fieldset class="pickList" >
	<legend class="common">
		<b>订货明细表</b>
	</legend>
	<table border="0" align="center" cellpadding="5" cellspacing="5" class="common">
		<tr>
        <td align="center" colspan="2" style="color:red">（填空为查询全部）</td>
      </tr>
	<tr>
	<tr>
        <td align="right">加盟商：</td>
        <td>
        <INPUT TYPE="text" NAME="BrokerId" value="" style="ime-mode:disabled" size=8 maxlength=10  onkeypress="notSpace()" id="brokerId" onblur="fandealcontentBroker(this)">
         <SELECT name="select7"  onchange="dealcontentBroker(this)">
         <OPTION value="">请选择</OPTION>
         <c:forEach items="${brokerIdList}" var="result">
	     <option value="${result.brokerId}">${result.brokerId}</option>
		 </c:forEach>
          </SELECT>   
		  <span class="req"></span>
        </td>
      </tr>  
       <tr>
        <td align="right">交易商类别：</td>
        <td>
        <INPUT TYPE="text" NAME="cateGoryId" value="" style="ime-mode:disabled" size=8 maxlength=10  onkeypress="notSpace()" id="cateGoryId" onblur="fandealcontentBroker(this)">
         <SELECT name="select8" id="cateGorySel"  onchange="dealcontentCateGory(this)">
         <OPTION value="">请选择</OPTION>
         <c:forEach items="${CateGoryIdList}" var="result">
	     <option value="${result.id}">${result.name}</option>
		 </c:forEach>
          </SELECT>   
		  <span class="req"></span>
        </td>
      </tr> 
		<tr>
        <td align="right">起始交易商：</td>
        <td>
        <INPUT TYPE="text" NAME="zStart" value="" style="ime-mode:disabled" size=8 maxlength=10 onblur="fandealcontent(this)"onkeypress="notSpace()" >
         <div id="divContent" style="display:none; position:absolute; background-color:#ffffff;border: solid 1px #9DCEE8;"></div>  
		  <span class="req"></span>
        </td>
      </tr>    
      <tr>
        <td align="right">结束交易商：</td>
        <td>
          <div align="left">
            <INPUT TYPE="text" NAME="zEnd" value=""  style="ime-mode:disabled"  size=8 maxlength=10 onblur="fandealcontent1(this)" onkeypress="notSpace()" >
            <div id="divContentEnd" style="display:none; position:absolute; background-color:#ffffff;border: solid 1px #9DCEE8;"></div>  
		  <span class="req"></span>
            </div></td>
      </tr>  
	<tr>
        <td align="right">品种：</td>
        <td>
        <INPUT TYPE="text" NAME="breed" style="ime-mode:disabled"  id="breedId" value="" size="8" maxlength="8" onchange="dealcontentStartBreed(this)" onkeypress="notSpace()"/>
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
        <INPUT TYPE="text" NAME="zStartCommodity" style="ime-mode:disabled"   value="" size=8 maxlength=8 onblur="dealcontentStrartCommodity(this)"onkeypress="notSpace()" >
         <SELECT name="selectStartCommodity" onchange="dealcontentCommodity(this)" style="width:100px;" >
         <OPTION value="">请选择</OPTION>
         <c:forEach items="${commodityStartList}" var="commodity">
	     <option value="${commodity.commodityID}">${commodity.commodityID}</option>
		 </c:forEach>
          </SELECT>   
		  <span class="req"></span>
        </td>
      </tr>    
      <tr>
        <td align="right">结束商品：</td>
        <td>
          <div align="left">
            <INPUT TYPE="text" NAME="zEndCommodity" style="ime-mode:disabled"  value="" size=8 maxlength=8 onblur="dealcontentEndCommodity1(this)"onkeypress="notSpace()">
            <SELECT  name="selectEndCommodity" onchange="dealcontentCommodity1(this)" style="width:100px;">
            <OPTION value="">请选择</OPTION>
            <c:forEach items="${commodityEndList}" var="commodity">
	        <option value="${commodity.commodityID}">${commodity.commodityID}</option>
		    </c:forEach>
          </SELECT>   
		  <span class="req"></span>
            </div></td>
      </tr>    
		<tr>
        <td align="right">买卖：</td>
        <td>
          <div align="left">
            <SELECT  name="bs">
            <OPTION value="">请选择</OPTION>
	        <option value="1">买</option>
			<option value="2">卖</option>
			<option value="3">全部</option>
          </SELECT>   
		  <span class="req">*</span>
            </div></td>
      </tr>    
      <tr>
        <td align="right">查询日期：</td>
        <td >
			<MEBS:calendar eltID="zDate" eltName="zDate" eltCSS="date" eltStyle="width:88px" eltImgPath="<%=skinPath%>/images/"  />
			<span class="req">*</span>
      </tr>
    </table>
    
	<table border="0" align="center" cellpadding="5" cellspacing="5" class="common">
		<tr>
				<td>
				<input name="Submit22" type="button" class="button" value="查看" onclick="return query('pdf')">
	     		</td>
	     		<td>
				<input name="Submit22" type="button" class="button" value="保存为excel" onclick="return query('excel')">
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
<!--
function dealcontentCateGory(sel)
{
    document.forms[0].cateGoryId.value=sel.options[sel.selectedIndex].innerHTML;
}
function fandealcontentCateGory(sel)
{

    var len=document.forms[0].select8.options.length;
    
    for(var i=0;i<len;i++)
    {
        if(document.forms[0].select8.options[i].value-sel.value==0)
        {
            document.forms[0].select8.options[i].selected=true;
            break;
        }
        //document.forms[0].FIRM_ID.value=sel.options[sel.selectedIndex].value;
    }
}
function ajaxCommodityIdByBreedrId(breedId){
	var xmlHttp;
    try{
    	xmlHttp=new ActiveXobject("Msxml2.XMLHTTP");
    }catch(e){
    	try{
    		xmlHttp=new XMLHttpRequest();
    	}catch(e){
    		try{
    			xmlHttp=new ActiveXobject("Microsoft.XMLHTTP");
    		}catch(e){
    			alert("您的浏览器不支持Ajax");
    			return false;
    		}
    		
    	}
    }
    if(breedId == ""){
    	 xmlHttp.open("post","getBreed.jsp",true);
    }else{
    	xmlHttp.open("post","getBreed.jsp?breedId="+breedId,true);
    	
    }
    xmlHttp.send(null);
    xmlHttp.onreadystatechange=function(){
    	if(xmlHttp.readyState==4){
    		var selectStartCommodity=document.getElementById("selectStartCommodity");
    		var selectEndCommodity=document.getElementById("selectEndCommodity");
    		selectStartCommodity.length=1;
    		selectEndCommodity.length=1;
    		var data=xmlHttp.responseText;
    		var datas=data.split("-");
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
    }
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
function dealcontentBroker(sel)
{
    document.forms[0].BrokerId.value=sel.options[sel.selectedIndex].value;
}
function fandealcontentBroker(sel)
{

    var len=document.forms[0].select5.options.length;
    
    for(var i=0;i<len;i++)
    {
        if(document.forms[0].select5.options[i].value-sel.value==0)
        {
            document.forms[0].select5.options[i].selected=true;
            break;
        }
        //document.forms[0].FIRM_ID.value=sel.options[sel.selectedIndex].value;
    }
}
function dealcontent(sel)
{
    document.forms[0].zStart.value=sel.options[sel.selectedIndex].value;
}
function fandealcontent(sel)
{

    var len=document.forms[0].select5.options.length;
    
    for(var i=0;i<len;i++)
    {
        if(document.forms[0].select5.options[i].value-sel.value==0)
        {
            document.forms[0].select5.options[i].selected=true;
            break;
        }
        //document.forms[0].FIRM_ID.value=sel.options[sel.selectedIndex].value;
    }
}


function dealcontent1(sel)
{
    document.forms[0].zEnd.value=sel.options[sel.selectedIndex].value;
}
function fandealcontent1(sel)
{

    var len=document.forms[0].select6.options.length;
    
    for(var i=0;i<len;i++)
    {
        if(document.forms[0].select6.options[i].value-sel.value==0)
        {
            document.forms[0].select6.options[i].selected=true;
            break;
        }
        //document.forms[0].FIRM_ID.value=sel.options[sel.selectedIndex].value;
    }
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
    var zStart=document.forms[0].zStart.value;
    var zEnd=document.forms[0].zEnd.value;
	var zDate=document.forms[0].zDate.value;
	var zStartCommodity=document.forms[0].zStartCommodity.value;
	var zEndCommodity=document.forms[0].zEndCommodity.value;
	var bs=document.forms[0].bs.value;
	var brokerId =document.forms[0].BrokerId.value;
	var breedId =document.forms[0].breed.value;
	var cateGoryId = document.getElementById("cateGorySel").value;
	if (zStart == "" && zEnd != "") {
		alert("起始交易商不能为空！");
		return false;
	}
	if (zEnd == "" && zStart != "") {
		alert("结束交易商不能为空！");
		return false;
	}
	if (zStartCommodity == "" && zEndCommodity !="") {
		alert("起始商品不能为空！");
		return false;
	}
	if (zEndCommodity == "" && zStartCommodity != "") {
		alert("结束商品不能为空！");
		return false;
	}
	if (bs == "") {
		alert("买卖不能为空！");
		return false;
	}
	if (zDate == "") {
		alert("查询日期不能为空！");
		return false;
	}
	if(!isDateFomat(zDate))
    {
        alert("查询日期格式不正确！\n如：" + '<%=date%>');
        document.forms[0].zDate.value = "";
        return false;
    }
	if(zStart =="" && zEnd ==""){
		zStart = null;
		zEnd = null;
	}
	if(zStartCommodity == "" && zEndCommodity ==""){
		zStartCommodity =null;
		zEndCommodity = null;
	}
	if(breedId ==""){
		breedId=null;
	}
	if(brokerId ==""){
		brokerId = null;
	}
	if(cateGoryId == ""){
		cateGoryId=null;
	}
	if(exportTo=="excel")
		window.open("reportContainerExcel.jsp?sign=indentDetail&startFirmID=" + zStart + "&endFirmID=" + zEnd + "&cleardate=" + zDate + "&startCommodityID=" + zStartCommodity + "&endCommodityID=" + zEndCommodity + "&bs=" + bs + "&title=订货明细表&brokerId="+brokerId+"&breedId="+breedId+"&cateGoryId="+cateGoryId);
		else 
	window.showModalDialog("reportContainer.jsp?sign=indentDetail&startFirmID=" + zStart + "&endFirmID=" + zEnd + "&cleardate=" + zDate + "&startCommodityID=" + zStartCommodity + "&endCommodityID=" + zEndCommodity + "&bs=" + bs + "&title=订货明细表&brokerId="+brokerId+"&breedId="+breedId+"&cateGoryId="+cateGoryId,"", "dialogWidth=900px; dialogHeight=600px; status=no;scroll=yes;help=no;resizable=yes");

}

//-->
</SCRIPT>