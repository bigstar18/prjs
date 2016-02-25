<%@ include file="util.jsp" %>
<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page contentType="text/html;charset=GBK"%> 



<%
   //List firmAscList=getList("select firmId from t_firm order by firmId");
   //List firmDescList=getList("select firmId from t_firm order by firmId desc");
  // pageContext.setAttribute("firmAscList",firmAscList);
   //pageContext.setAttribute("firmDescList",firmDescList);
   List breedList=getList("select breedId from e_breed");
   pageContext.setAttribute("breedList",breedList);
   List commodityStartList = getList("select commodityID from (select commodityID from t_commodity) union (select distinct commodityID from t_settlecommodity) order by commodityID asc");
   List commodityEndList = getList("select commodityID from (select commodityID from t_commodity) union (select distinct commodityID from t_settlecommodity) order by commodityID desc");
   pageContext.setAttribute("commodityStartList",commodityStartList);
   pageContext.setAttribute("commodityEndList",commodityEndList);

%>
<html>
<head>
<%@ include file="/timebargain/widgets/calendar/calendar.jsp" %>

<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
<script language="javascript">
function window_onload(){
	highlightFormElements();
	//initDocument();
	//document.frm.zDate.setAttribute("dropDown_fixed", 1);
	//document.frm.zDate.setAttribute("autoDropDown", 1);
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
		<b>抵顶记录表</b>
	</legend>
	<table border="0" align="center" cellpadding="5" cellspacing="5" class="common">
	<tr>
        <td align="center" colspan="2" style="color:red">（填空为查询全部）</td>
      	</tr>
	<tr>
        <td align="right">品种：</td>
        <td>
        <INPUT TYPE="text" NAME="breed" style="ime-mode:disabled"  id="breedId" value="" size=8 maxlength=8 onchange="dealcontentChangeBreed(this)" onkeypress="notSpace()"/>
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
        <INPUT TYPE="text" NAME="zStartCommodity" style="ime-mode:disabled"  value="" size=8 maxlength=8 onchange="dealcontentStrartCommodity(this)"onkeypress="notSpace()" >
         <SELECT name="selectStartCommodity" onchange="dealcontentCommodity(this)"  >
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
            <INPUT TYPE="text" NAME="zEndCommodity" style="ime-mode:disabled"   value="" size=8 maxlength=8 onchange="dealcontentEndCommodity1(this)" onkeypress="notSpace()">
            <SELECT  name="selectEndCommodity" onchange="dealcontentCommodity1(this)">
            <OPTION value="">请选择</OPTION>
            <c:forEach items="${commodityEndList}" var="commodity">
	        <option value="${commodity.commodityID}">${commodity.commodityID}</option>
		    </c:forEach>
          </SELECT>   
		  <span class="req"></span>
            </div></td>
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
    	//alert(breedId);
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

function dealcontentChangeBreed(sel)
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
	var zStartCommodity=document.forms[0].zStartCommodity.value;
	var zEndCommodity=document.forms[0].zEndCommodity.value;
	var breedId =document.forms[0].breed.value;
	if (zStartCommodity == "" && zEndCommodity != "") {
		alert("起始商品不能为空！");
		return false;
	}
	if (zEndCommodity == "" && zStartCommodity !="") {
		alert("结束商品不能为空！");
		return false;
	}
	if(zStartCommodity == "" && zEndCommodity == ""){
		zStartCommodity = null;
		zEndCommodity = null;
	}
	if(breedId == ""){
		breedId=null;
	}
	if(exportTo=="excel")
		window.open("reportContainerExcel.jsp?sign=diDingResult&startCommodityID=" + zStartCommodity + "&endCommodityID=" + zEndCommodity+ "&title=抵顶记录表&breedId="+breedId);
		else 
	window.showModalDialog("reportContainer.jsp?sign=diDingResult"  + "&startCommodityID=" + zStartCommodity + "&endCommodityID=" + zEndCommodity + "&title=抵顶记录表&breedId="+breedId,"", "dialogWidth=900px; dialogHeight=650px; status=no;scroll=yes;help=no;resizable=yes");

}

//-->
</SCRIPT>