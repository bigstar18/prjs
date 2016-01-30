<%-----------------------------------------------------------------------------
	Copyright (c) 2004 Actuate Corporation and others.
	All rights reserved. This program and the accompanying materials 
	are made available under the terms of the Eclipse Public License v1.0
	which accompanies this distribution, and is available at
	http://www.eclipse.org/legal/epl-v10.html
	
	Contributors:
		Actuate Corporation - Initial implementation.
-----------------------------------------------------------------------------%>
<%@ page contentType="text/html; charset=utf-8"%>
<%@ page session="false" buffer="none"%>
<%@ page
	import="org.eclipse.birt.report.presentation.aggregation.IFragment"%>
<%
	String skinPath = request.getSession().getAttribute("skinPath")
			.toString();
%>

<%-----------------------------------------------------------------------------
	Expected java beans
-----------------------------------------------------------------------------%>
<jsp:useBean id="fragment"
	type="org.eclipse.birt.report.presentation.aggregation.IFragment"
	scope="request" />

<%-----------------------------------------------------------------------------
	Report content fragment
-----------------------------------------------------------------------------%>
<TR VALIGN='top'>
	<TD id="documentView">
		<TABLE cellpadding="0" cellspacing="0" border="0">
			<TR>
				<TD style="vertical-align: top;">
					<%
						if (fragment != null) {
							fragment.callBack(request, response);
						}
					%>
				</TD>
				<TD style="vertical-align: top;">
					<LINK REL="stylesheet" HREF="<%=skinPath%>/css/app/integrated/birt/birt.css"	TYPE="text/css">
			 <!-- <LINK REL="stylesheet" HREF="birt/styles/aaa.css" TYPE="text/css">
			  <textarea rows="10" cols="100" name="aaaaa" id="aaaaa">aa</textarea>
			 <textarea rows="10" cols="100" name="bbbbb" id="bbbbb">bb</textarea> 
				<DIV ID="Document" CLASS="birtviewer_document_fragment" onpropertychange="DocumentChange();">-->
				<DIV ID="Document" CLASS="birtviewer_document_fragment"	onpropertychange="DocumentChange(this);"></DIV></TD>
			</TR>
		</TABLE></TD>
</TR>
<script type="text/javascript">
		 flag =  true;
 function DocumentChange(obj){
 	if(document.getElementById("Document").innerHTML.length!=0&&flag){
	    flag=false;
 		var scorlength = (parseInt(document.getElementById("Document").scrollHeight)-parseInt(document.getElementById("Document").clientHeight));
	  	 scorlength =scorlength>0?scorlength:0;
	    var birtTRHeight = window.parent.document.getElementById("birtTRHeight").offsetHeight;
	    window.parent.document.getElementById("birtTRHeight").height=birtTRHeight+scorlength;
	}
 }
 <%-- 
	alert('<%=skinPath%>');
function DocumentChange(){
	if(document.getElementById("Document").innerHTML.length!=0){
	document.getElementById("aaaaa").value = 	document.getElementById("Document").innerHTML;
	}
}
	str='';
	c=document.styleSheets;
	for(i=0;i<c.length;i++){
	o=c[i];
	if (o.href=='')continue;
	str+='========== ';
	str+=o.href;
	str+='<br><xmp>\n';
	str+=o.cssText;
	str+='</xmp><br><br>\n';
	};
	document.getElementById("bbbbb").value=str;
	 --%>
</script>

