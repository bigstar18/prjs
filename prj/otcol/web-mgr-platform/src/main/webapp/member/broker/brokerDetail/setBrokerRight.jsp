<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../public/headInc.jsp"%>
<base target="_self">
<c:if test="${not empty modSuccess }">
	<script>
		window.returnValue=1;
		window.close();
	</script>
</c:if>
<style>
.cls
{
	display:none;
}
.opn
{
	display:;
}
</style>

<html>
  <head>
	<title>设置权限</title>
  </head>
  <body>
    <form name="rightForm" method="post" action="<%=brokerRightControllerPath %>saveBrokerRight">
			<fieldset width="100%">
		<legend>设置加盟商权限</legend>
		<BR>
		<span>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr height="35">
            		<td align="" width="20%">
            			加盟商编号：${brokerid }<input name="brokerid" type="hidden" value="${brokerid}">
            		</td>               
				</tr>			  
				<tr height="35">
				<td align="left" style='font-size: 9pt;'>	
				  <c:forEach items="${resultList}" var="result">
				    <span style="font-size: 9pt;">
				      <div id="div_ck_grand${result.menuId }" name="div_f" >
				        <input type="checkbox" style="margin-left: 40px;" id="${result.menuId }" name="ck" onclick="selectAll(${result.menuId })" value="${result.menuId }" />
				        <b>${result.title }</b>
				        <input type="hidden" name="pid" value="" />
				        <input type="hidden" id="inff" name="div_st" value="1"/>
				        <br/>
				      </div>
				    </span>
				    <c:forEach items="${result.brokerMenuList}"  var="results">
				      <span style="font-size: 12pt;">
				        <div id="div_ck_parent${results.menuId }" >
				          <input type="checkbox" style="margin-left: 70px;" id="${results.parentId }" currentId = "${results.menuId }" name="ckf" onclick="selectTop(this);selectChildren(this)" value="${results.menuId }" />
				          <font style='font-size: 12px;'>${results.title }</font>
				          <input type="hidden" name="pid" value="" />
				          <input type="hidden" id="inss" name="div_st" value="1"/>
				          <br/>
				        </div>
				      </span>
						<c:forEach items="${results.brokerMenuList}"  var="brokerMenus">
					      <span style="font-size: 12pt;">
					        <div id="div_ck_child${brokerMenus.parentId }" >
					          <input type="checkbox" id="${results.parentId }" style="margin-left: 100px;" realPid = "${brokerMenus.parentId }" name="ckf" onclick="selectAllTop(this)" value="${brokerMenus.menuId }" />
					          <font style='font-size: 12px;'>${brokerMenus.title }</font>
					          <input type="hidden" name="pid" value="" />
					          <input type="hidden" id="inss" name="div_st" value="1"/>
					          <br/>
					        </div>
					      </span>
				    	</c:forEach>
				    </c:forEach>
				    
				  </c:forEach>
				  
				</td>
			  </tr>
        	</table>
			<BR>
          </span>
		</fieldset>
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
				  <tr height="35">
					<td width="40%"><div align="center">
					  <input type="hidden" name="opt">	
				      <input type="hidden" name="rights" value="${rights }">
					  <input type="button" name="btn" onclick="doSubmit()" class="btn" value="提交">&nbsp;&nbsp;
					  <input name="cls" type="button" onclick="window.close()" class="btn" value="返回">&nbsp;&nbsp;
					</div></td>
				  </tr>
			 </table>  
    </form>
  </body>
</html>
<SCRIPT LANGUAGE="JavaScript">


var cks = document.all("ck");
var ckf=document.all("ckf");
var pids = document.all("pid");
//var div_cks = document.all("div_ck");
//var div_sts = document.all("div_st");
var obj  =  rightForm.rights.value;
if(obj !="" && obj !=null)
{	
	
	
	var s = obj.split(",")
	var ckfs = document.getElementsByName("ckf");  
    var cks = document.getElementsByName("ck");  
      for (var i = 0; i < ckfs.length; i++)  
     {  
     	for(var j=0;j<s.length;j++){
				   if(ckfs[i].value ==s[j] ) 
				    	ckfs[i].checked=true;
				    
		}
     }	
    for (var i = 0; i < cks.length; i++)  
     {  
     	for(var j=0;j<s.length;j++){
				   if(cks[i].value ==s[j]) 
				    	cks[i].checked=true;
		 }
	}
}
function frmChk()
{
	var sign=false;
	if(confirm("确认保存？")){
			frm.btn.disabled=true;
			sign=true;
			frm.opt.value = "mod";
		}
		if(sign){
			frm.submit();
		}
}

function chgSysType()
{
	frm.submit();
}
function selectAll(vid)
{
	var topSel = document.getElementById(vid);
	var selList = document.getElementsByName(vid);
	var selLen = selList.length;
	var mark ;//true为全选  false为全不选
	if(topSel.checked)
	{
		mark = true;
	}
	else
	{
		mark = false;
	}
	
	for(var a=0;a<selLen;a++)
	{
		if(mark)
		{
			if(!selList[a].checked)
			{
				selList[a].checked = true ;
			}
		}
		else
		{
			if(selList[a].checked)
			{
				selList[a].checked = false ;
			}
		}
		if(a>0 && selList[a].checked ==true){
			selList[0].checked ==true;
		}
		
			
	}
}

function selectTop(c){
	//alert(c.checked);
	var selList = document.getElementsByName(c.id);
	var selLen = selList.length;
	if(c.checked==true){
		c.checked = true;
		//alert(selList[0].id);
		selList[0].checked =true;
		
	}
	else{
		c.checked = false;
	}
		
	

	
	
}
function doSubmit(){
     var boxes = document.getElementsByName("ckf");  
     var boxe = document.getElementsByName("ck");  
     var str="";
   
 	  for (var i = 0; i < boxe.length; i++)  
     {  
     if (boxe[i].checked)  
     {  
         str+= boxe[i].value+",";
     }  
 	} 
 	  for (var i = 0; i < boxes.length; i++)  
     {  
     if (boxes[i].checked)  
     {  
         str+= boxes[i].value+",";
     }  
 	} 
   
    rightForm.rights.value = str;
    rightForm.submit();
    
}

function selectChildren(cb){
	var selList = document.getElementsByName("div_ck_child"+cb.currentId);
	if(cb.checked){
		for(var i = 0; i < selList.length; i++){
			selList[i].firstChild.checked = true;
		}
	}else{
		for(var i = 0; i < selList.length; i++){
			selList[i].firstChild.checked = false;
		}
	}
}

function selectAllTop(c){
	var parent = document.getElementsByName("div_ck_parent"+c.realPid)[0].firstChild;
	var grand = document.getElementsByName("div_ck_grand"+c.id)[0].firstChild;
	if(c.checked){
		parent.checked =true;
		grand.checked =true;
	}
	//else{
	//	parent.checked =false;
	//	grand.checked =false;
	//}
}
</SCRIPT>