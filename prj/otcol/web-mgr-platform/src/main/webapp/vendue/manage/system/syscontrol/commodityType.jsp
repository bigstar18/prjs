<%@ page contentType="text/html;charset=GBK" %>
<html xmlns:MEBS>
<%@ include file="../../globalDef.jsp"%> 

<!--����-->
<style type="text/css">
.xbt {
	font-size: 12px;
	color: #000066;
	text-decoration: none;
}
</style>



<head>
	<title>��Ʒ����</title>
</head>
<body>
<%
try
{
	
%>
<!--��ҳ��������-->
<c:choose> 
 <c:when test="${empty param.pageIndex}"> 
   <c:set var="pageIndex" value="1"/> 
 </c:when> 
 <c:otherwise> 
   <c:set var="pageIndex" value="${param.pageIndex}"/> 
 </c:otherwise> 
</c:choose>
<c:choose>
  <c:when test="${empty param.pageSize}">
  	 <c:set var="pageSize" value="${PAGESIZE}"/>
  </c:when>
  <c:otherwise>
  	 <c:set var="pageSize" value="${param.pageSize}"/>
  </c:otherwise>
</c:choose>
<c:if test="${param.opt=='add'}">
	<db:select var="row" table="dual" columns="SP_v_commoditytype.nextval as seqID">
	<c:set var="ids" value="${row.seqID}"/>
	</db:select>
	<db:tranAddCommodityType
		id="${ids}"
		name="${param.cname}"
		reservedchar1="${param.dw}"
		reservedchar2=""
		reservedchar3=""
		reservedchar4=""
		/>
		 <script language="javascript">
 	    	 	  <!--
 	  		 	  alert("��Ʒ��ӳɹ�!");
 	  		 	  //-->
 	  	 </script>
</c:if>
<c:if test="${param.opt=='delete'}">
	<db:delCommodityType id="${param.id}"/>
	 <script language="javascript">
 	    	 	  <!--
 	  		 	  alert("��Ʒɾ���ɹ�!");
 	  		 	  //-->
 	  		 </script>
</c:if>
<c:if test="${param.opt=='mod'}">
	<db:tranModCommodityType 
  id="${param.id}"
  name="${param.cname}"
  reservedchar1="${param.dw}"
  reservedchar2=""
  reservedchar3=""
  reservedchar4=""
  />
   <script language="javascript">
 	    	 	  <!--
 	  		 	  alert("��Ʒ�޸ĳɹ�!");
 	  		 	  //-->
 	  		 </script>
</c:if>
<form name="frm" action="" method="post">
	<fieldset width="100%">
		<legend>��Ʒ���Թ���</legend>
	</fieldset>
	<br>
	
  <table id="tb" width="100%"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tHead>
    <tr height="25" align="center">
	  <td  class="panel_tHead_LB">&nbsp;</td>
	  <td  class="panel_tHead_MB">&nbsp;</td>
      <td  class="panel_tHead_MB">��Ʒ���</td>
      <td  class="panel_tHead_MB">��Ʒ����</td>
	  <td  class="panel_tHead_MB">������λ</td>
      <td  class="panel_tHead_MB">��������</td>
	  <td  class="panel_tHead_RB">&nbsp;</td>
    </tr>
  </tHead>
  <tBody>
	<db:selectPG_CommodityType var="row" orderBy="id" pageIndex="${pageIndex}" pageSize="${pageSize}" where="">
	<tr align="center">
	  <td class="panel_tBody_LB">&nbsp;</td>
	  <td class="underLine"><input type="radio" name="ck" id="ck" value="${row.id}" onClick="sel(this.value,'${row.name}','${row.reservedchar1}')"></td>
      <td class="underLine"><div align="center" class="xbt">${row.id}</div></td>
      <td class="underLine"><div align="center" class="xbt">
      	${row.name}
      	<input name="commodityName" type="hidden" value="${row.name }"/>
      	</div>
      </td>
	  <td class="underLine"><div align="center" class="xbt">${row.reservedchar1}&nbsp;</div></td>
      <td class="underLine"><div align="center" class="xbt"><A HREF="commodityProperty.jsp?cpid=${row.id}&cname=${row.name}">����</A></div></td>
	  <td class="panel_tBody_RB">&nbsp;</td>
    </tr>
	</db:selectPG_CommodityType>
	</tBody>
	<!--������ҳ��-->
	<db:cnt_CommodityType var="c" where="">
			 <c:set var="totalCnt" value="${c.n}"/>
	</db:cnt_CommodityType>
	<c:choose> 
			 <c:when test="${totalCnt%pageSize==0}"> 
			   <c:set var="pageCnt" value="${totalCnt/pageSize}"/> 
			 </c:when> 
			 <c:otherwise>
			   <c:choose> 
				 <c:when test="${(totalCnt%pageSize)*10>=5*pageSize}"> 
				   <c:set var="pageCnt" value="${totalCnt/pageSize}"/> 
				 </c:when> 
				 <c:otherwise>
				   <c:set var="pageCnt" value="${totalCnt/pageSize+1}"/>
				 </c:otherwise> 
				</c:choose>
			 </c:otherwise> 
			</c:choose>
	<jsp:include page="../../public/pageTurn1.jsp">
				<jsp:param name="colspan" value="5"/>
				<jsp:param name="pageIndex" value="${pageIndex}"/>
				<jsp:param name="totalCnt" value="${totalCnt}"/>
				<jsp:param name="pageCnt" value="${pageCnt}"/>
				<jsp:param name="pageSize" value="${pageSize}"/>
			</jsp:include>	
  </table>
  <br>
  <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="1">
  	<tr align="center">
      <td class="underLine" align="right">��Ʒ���ƣ�</td>
	  <td class="underLine" align="center"><input type="text" name="cname" id="cname" onkeypress="onlyNumberAndCharInput()" maxlength="16"><input type="hidden" name="id"></td>
	  <td class="underLine" align="right">������λ��</td>
	  <td class="underLine" align="center"><input type="text" name="dw" onkeypress="notSpace()"></td>
    </tr>
  </table>
  <br>
  <table width="80%"  border="0" align="center" cellpadding="0" cellspacing="1" align="right">
  	<tr align="right">
      <td height="26" align="right">
        <input type="button" name="add" class="btn" value="���" onClick="addn()">&nbsp;
		<input type="button" name="mod" onclick="modn()" class="btn" value="�޸�">&nbsp;
		<input type="button" name="del" onclick="return deleteCommodity(frm,tb,'ck')" class="btn" value="ɾ��">
		
	  </td>
    </tr>
	
  </table>
  <input type="hidden" name="opt">

</form>
</body>
</html>
<%@ include file="../../public/pageTurn2.jsp"%>
<SCRIPT LANGUAGE="JavaScript">
function checkName2(){
	//���Ʒ�������Ƿ��ظ�
	var cname = document.getElementById("cname").value;
	var commodityNames = document.getElementsByName("commodityName");
	var radio = document.getElementsByName("ck");
	for (var j=0;j<radio.length;j++) {
		for(var i=0;i<j;i++){
			if (radio[i].checked) {
				if (cname == commodityNames[i].value) {
					return false;
				}
			}
			if(cname == commodityNames[i].value){
				return true;
			}
		}
			 
	}
	return false;
}
function modn() {
	var sign;
	var radio = document.getElementsByName("ck");
	for (var i=0;i<radio.length;i++) {
		if (radio[i].checked) {
			sign = true;
		} 
	}
	if(frm.cname.value=="" || !sign) {
		alert("��ѡ��Ҫ�޸ĵļ�¼");
		frm.cname.focus();
		return false;
	}
	if(checkName2()){
		alert("��Ʒ���Ʋ����ظ�");
		frm.cname.value="";
		return false;
	}
	if(frm.dw.value=="") {
		alert("������λ����Ϊ��");
		frm.dw.focus();
		return false;
	}
	if(confirm("ȷ���޸���Ʒ��")) {
		frm.opt.value="mod";
		frm.submit();
		return true;
	}
}
function checkName(){
	//���Ʒ�������Ƿ��ظ�
	var cname = document.getElementById("cname").value;
	var commodityNames = document.getElementsByName("commodityName");
	for(var i=0;i<commodityNames.length;i++){
		if(cname == commodityNames[i].value){
			return true;
		}
	}
	return false;
}
function sel(id,name,str1)
{
	frm.id.value=id;
	frm.cname.value=name;
	frm.dw.value = str1;
	//frm.mod.disabled="true";
	//frm.del.disabled="false";
}
function addn()
{
	if(frm.cname.value=="")
	{
		alert("��Ʒ���Ʋ���Ϊ��");
		frm.cname.focus();
		return false;
	}
	if(frm.dw.value=="")
	{
		alert("������λ����Ϊ��");
		frm.dw.focus();
		return false;
	}
	if(checkName()){
		alert("��Ʒ���Ʋ����ظ�");
		frm.cname.value="";
		return false;
	}
	frm.opt.value="add";
	frm.submit();
	return true;
}


function deln()
{
	if(frm.id.value=="")
	{
		alert("��ѡ��Ҫɾ���ļ�¼");
		return false;
	}
	frm.dotype.value="del";
	//frm.mod.disabled="true";
	//frm.del.disabled="true";
	if(confirm("ȷ��ɾ������Ʒ��"))document.frm.submit();
	//frm.submit();
	//return true;
}
var request = new ActiveXObject("Microsoft.XMLHTTP");
var del = true;
function oneAjax(){
	var cname = frm.cname.value;
	request.onreadystatechange = stateChanged;
	request.open("post","haveCommodity.jsp?cname="+cname,false);
	request.send();
	request.abort();
}
function stateChanged(){
	if (request.readyState == 4){
		var result = request.responseText;
		var bs = result.split("[]");
		var number = bs[0];
		var no = Number(number);
		if(no != 0){
			del = false;
		} else if (no == 0) {
			del = true;
		}
	}
}
function deleteCommodity(frm_delete,tableList,checkName) {
	oneAjax();
	if (del == false) {
		alert("��ǰ��Ʒ������ɾ����");
		return false;
	}
	if(isSelNothing(tableList,checkName) == -1) {
		alert ( "û�п��Բ��������ݣ�" );
		return false;
	}
	if(isSelNothing(tableList,checkName)) {
		alert ( "��ѡ����Ҫ���������ݣ�" );
		return false;
	}
	if(confirm("�˲�����ͬʱɾ��������Ʒ�ֵ���Ʒ��ȷ��ɾ����")) {   
		frm.opt.value="delete";
		frm.submit();
		//return true;
	} else {
		return false;
	}
}


</SCRIPT>
<%
}
catch(Exception e)
{
	System.out.println(e.toString());
}
%>