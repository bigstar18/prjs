<%@ page contentType="text/html;charset=GBK" %>
<html xmlns:MEBS>
<%@ include file="../../globalDef.jsp"%> 

<!--����-->
<style type="text/css">
.list_h {
	position:absolute;
	visibility:hidden;
	z-index:-1;
}
.list_v {
	visibility:visible;
	z-index:1;
}
.form_k2 {
	background-color: #FFFFFF;
	border: 1px solid #FFFFFF;
	font-size: 12px;
	font-weight: normal;
	color: #000000;
	text-decoration: none;
	width:70%;
}
.form_k1 {
	border:0;
	font-size: 12px;
	font-weight: normal;
	color: #000000;
	text-decoration: none;
	text-align:center;
}
.td_list {
	font-size: 12px;
	font-weight: normal;
	color: #000000;
	text-decoration: none;
	background-color: #ACC5EE;
	text-align: center;
	border-right-width: 1px;
	border-bottom-width: 1px;
	border-right-style: solid;
	border-bottom-style: solid;
	border-right-color: #999999;
	border-bottom-color: #999999;
	padding:1px;
	height:20px;
}
.td_list1 {
	font-size: 12px;
	font-weight: normal;
	color: #000000;
	text-decoration: none;
	background-color: #CCCCFF;
	text-align: center;
	border-right-width: 1px;
	border-bottom-width: 1px;
	border-right-style: solid;
	border-bottom-style: solid;
	border-right-color: #999999;
	border-bottom-color: #999999;
	padding:1px;
	height:20px;
}
</style>
<title>��Ʒ����</title>
<%
try
{
	//String cname = delNull(request.getParameter("cname"));
%>
<body>
<c:if test="${param.tag!=''}">
	<c:if test="${param.tag=='add'}">
		<db:select var="row" table="dual" columns="SP_v_commodityproperty.nextval as seqID">
		<c:set var="ids" value="${row.seqID}"/>
		</db:select>
		<db:tranAddCommodityProperty
		id="${ids}"
		name="${param.qName}"
		cpid="${param.cpid}"
		isnull="${param.validateNull}"
		charvartext="${param.selOption}"
		type="${param.validateType}"
		/>
		 <script language="javascript">
 	    	 	  <!--
 	  		 	  alert("������ӳɹ�!");
 	  		 	  //-->
 	  		 </script>
	</c:if>
	<c:if test="${param.tag=='update'}">
		<db:tranModCommodityProperty 
			id="${param.updateId}"
			name="${param.qName}"
			isnull="${param.validateNull}"
			charvartext="${param.selOption}"
			type="${param.validateType}"
			reservedchar1=""
			reservedchar2=""
			reservedchar3=""
			reservedchar4=""
			reservedchar5=""
		  />
		   <script language="javascript">
						  <!--
						  alert("�����޸ĳɹ�!");
						  //-->
					 </script>
	</c:if>
	<c:if test="${param.tag=='del'}">
		<db:delCommodityProperty id="${param.updateId}"/>
	 <script language="javascript">
 	    	 	  <!--
 	  		 	  alert("����ɾ���ɹ�!");
 	  		 	  //-->
 	  		 </script>
	</c:if>
</c:if>
<form name=frm method=post>
<table width="780"  border="0" align="center" cellpadding="0" cellspacing="0" >
  <tr>
    <td height="347" valign="top" ><table width="100%" height="24"  border="0" cellpadding="0" cellspacing="0" class="line">
      <tr>
        <td width="82%" height="10"><div align="right">&nbsp;</div></td>
        
      </tr>
    </table>
	  <fieldset width="100%">
	  <legend>��Ʒ����</legend>
	  <table width=""  border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td height="24" class="bt"><div align="center">�༭��Ʒ <%=request.getParameter("cname")%> ����</div></td>
        </tr>
      </table>
      <br>
	  <table width="55%"  border="0" align="center" cellpadding="0" cellspacing="1">
		<db:selectCommodityProperty var="row" where="cpid=${param.cpid}" orderBy="id">
		<c:set var="str" value="${row.id}#${row.name}#${row.type}#${row.isnull}"/>
		<tr id="up${row.id}" class="list_v">
			<td width="7%" class="td_list"><INPUT TYPE="radio" NAME="ra" value="${row.id}" onClick="updateID(this.value)" ></td>
			<td width="28%" class="td_list">${row.name}</td>
			<td width="65%" class="td_list">&nbsp;
		<c:if test="${row.type==3}">
			<c:set var="str" value="${str}#${row.charvartext}"/>
			<input readonly class="form_k2" value="${row.charvartext}">
		
		</c:if>
		<c:choose>
		  <c:when test="${row.isnull==1}">
			<c:if test="${row.type!=3}">
			 <input value="������Ϊ��"" readOnly class="form_k2">
			 </c:if>
		  </c:when>
		  <c:otherwise>
			 <input value="����Ϊ��"" readOnly class="form_k2">
		  </c:otherwise>
		</c:choose>
		<c:choose>
		  <c:when test="${row.type==2}">
			&nbsp;%
		  </c:when>
		  <c:otherwise>
			 &nbsp;&nbsp;
		  </c:otherwise>
		</c:choose>
		
			<INPUT TYPE="hidden" NAME="v${row.id}" value="${str}">
			</td>
			</tr>
			</db:selectCommodityProperty>

	  </table>
	  <table width="55%"  border="0" align="center" cellpadding="0" cellspacing="1">
		<tr id="in1" class="list_h">
		  <td width="7%" class="td_list1">&nbsp;</td>
          <td width="28%" class="td_list1">��������</td>
          <td width="65%" class="td_list1">&nbsp;
              <input type="text" name="qName" value="" maxlength="126" class="form_k2">&nbsp;<font weight="normal" color="red">*</font>
		  </td>
        </tr>
		<tr id="in2" class="list_h">
			<td class="td_list1">&nbsp;</td>
			<td class="td_list1">��֤��׼</td>
			<td class="td_list1">&nbsp;
				<INPUT TYPE="radio" NAME="validateType" value="0" onClick="addOp(false)" checked>����&nbsp;
				<INPUT TYPE="radio" NAME="validateType" value="1" onClick="addOp(false)">����&nbsp;
				<INPUT TYPE="radio" NAME="validateType" value="2" onClick="addOp(false)">�ٷֱ�&nbsp;
				<INPUT TYPE="radio" NAME="validateType" value="3" onClick="addOp(true)">������&nbsp;
				<br><hr>
				<INPUT TYPE="radio" NAME="validateNull" value="0" checked>����Ϊ��
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<INPUT TYPE="radio" NAME="validateNull" value="1" >������Ϊ��
				<!--<INPUT TYPE="radio" NAME="validateType" value="4">����&nbsp;-->
		    </td>
		</tr>
		<tr id="in3" class="list_h">
			<td class="td_list1">&nbsp;</td>
			<td height="60px" class="td_list1">
				����ѡ��<br>
				<font weight="normal" color="red">(��ѡ����","�ָ�)</font>
			</td>
			<td height="60px" class="td_list1">
				<TEXTAREA NAME="selOption" ROWS="5" COLS="25" class="form_k2" onFocus="select()">��,��</TEXTAREA>
			</td>
		</tr>
	  </table>
	  </fieldset>
      <table width="55%"  border="0" align="center" cellpadding="0" cellspacing="1">
        <tr>
			<td height="36" width="100%" >
			<div id="first" style="position:absolute;visibility:visible;">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input name="Submit22" type="button" class="btn" value="���" onClick="add();">
				&nbsp;&nbsp;
				<input name="up" type="button" class="btn" value="�޸�"  disabled onClick="fupdate();"/>
				&nbsp;&nbsp;
				<input name="de" type="button" class="btn" value="ɾ��" disabled onClick="fdelete();"/>
				&nbsp;&nbsp;
				<input name="re" type="button" class="btn" value="����" onClick="window.location='commodityType.jsp'"/>
			</div>
			<div id="second" style="position:absolute;visibility:hidden;">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input name="put" type="button" class="btn" value="�ύ" onClick="fsubmit();"/>
				&nbsp;&nbsp;
				<input name="return" type="button" class="btn" value="����" onClick="fexit();"/>
			</div>
			</td>
        </tr>
      </table></td>
  </tr>
</table>

<INPUT TYPE="hidden" NAME="tag" value="">
<INPUT TYPE="hidden" NAME="ID" value="">
<INPUT TYPE="hidden" NAME="upID" value="">
<INPUT TYPE="hidden" NAME="updateId" value="">
<INPUT TYPE="hidden" NAME="cpid" value="${param.cpid}">
<INPUT TYPE="hidden" NAME="cname" value="${param.cname}">
</form>
</body>
</html>

<SCRIPT LANGUAGE="JavaScript">
function fdelete(){
	document.frm.tag.value = "del";
	var upva = "document.frm.v"+document.frm.upID.value+".value";
	upva = eval(upva);
	var upvas = upva.split("#");
	document.frm.updateId.value = upvas[0];
	if(confirm("ȷ��ɾ������Ʒ������"))document.frm.submit();
}
function add(){
	document.frm.tag.value = "add";
	document.getElementById("in1").className="list_v";
	document.getElementById("in2").className="list_v";
	document.getElementById("first").style.visibility="hidden";
	document.getElementById("second").style.visibility="visible";
	document.frm.qName.focus();
}
function addOp(va){
	if(va){
		document.getElementById("in3").className="list_v";
		document.frm.validateNull[1].checked = true;
		document.frm.validateNull[0].disabled = true;
	}else{
		document.getElementById("in3").className="list_h";
		document.frm.validateNull[0].checked = true;
		document.frm.validateNull[0].disabled = false;
		document.frm.selOption.value = "��,��";
	}
}
function fexit(){
	document.frm.tag.value = "";
	document.getElementById("in1").className="list_h";
	document.getElementById("in2").className="list_h";
	document.getElementById("in3").className="list_h";
	document.frm.qName.value = "";
	document.frm.selOption.value = "��,��";
	document.frm.validateNull[0].checked = true;
	document.frm.validateType[0].checked = true;
	document.frm.validateNull[0].disabled = false;
	document.getElementById("second").style.visibility="hidden";
	document.getElementById("first").style.visibility="visible";
	if (document.frm.upID.value!="") {
		var upv = "up"+document.frm.upID.value;
		document.getElementById(upv).className="list_v";
	}
}
function fsubmit(){
	if(document.frm.qName.value == ""){
		alert("���Ե����Ʋ���Ϊ�գ�");
		document.frm.qName.focus();
	}
	else
	{
		if(confirm("ȷ���ύ������"))
		document.frm.submit();
	}
}
function fupdate(){
	var upv = "up"+document.frm.upID.value;
	document.getElementById(upv).className="list_h";
	document.frm.tag.value = "update";
	document.getElementById("in1").className="list_v";
	document.getElementById("in2").className="list_v";
	document.getElementById("first").style.visibility="hidden";
	document.getElementById("second").style.visibility="visible";
	var upva = "document.frm.v"+document.frm.upID.value+".value";
	upva = eval(upva);
	var upvas = upva.split("#");
	document.frm.updateId.value = upvas[0];
	document.frm.qName.value = upvas[1];
	document.frm.validateType[upvas[2]].checked = true;
	document.frm.validateNull[upvas[3]].checked = true;
	if(upvas[2]=="3"){
		addOp(true);
		document.frm.selOption.value=upvas[4];
	}
	document.frm.qName.select();
	document.frm.qName.focus();
}
function updateID(va){
	document.frm.upID.value=va;
	frm.up.disabled = false;
	frm.de.disabled = false;
}
</SCRIPT>
<%
}
catch(Exception e)
{
	System.out.println(e.toString());
}
%>