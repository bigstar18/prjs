<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../globalDef.jsp"%>

<!--权限判断-->
<%
  String filter="";
  ArrayList variResult=FindData.getVari(JNDI,filter);
  ArrayList proYearResult=FindData.getProYear(JNDI,filter);
  if(variResult==null){
      variResult=new ArrayList();
  }
  if(proYearResult==null){
      proYearResult=new ArrayList();
  }
%>
<span id="variSpan" name="variSpan">
&nbsp;&nbsp;品种：
<select name='vari' onchange='getProYear(this.value)'>
<option value=''>全部</option>
<%
	for(int i=0;i<variResult.size();i++){
	   VariProYear vari =(VariProYear)variResult.get(i);
%>
<option value='<%=vari.getVari().split(":")[0]%>'><%=vari.getVari().split(":")[0]%></option>
<%
    }	
%>
</select>
&nbsp;&nbsp;生产年份：
<select name='proYear'>
<option value=''>全部</option>
<%
    for(int i=0;i<proYearResult.size();i++){
    	VariProYear vari =(VariProYear)proYearResult.get(i);
%>
<option value='<%=vari.getProYear()%>'><%=vari.getProYear()%></option>
<%
    }	
%>
</select>
</span>
<script language="javascript">
    //将品种插入下拉框
    var opts="";
    var proYear="";
    var proYearAll=""
  <%
	  for(int i=0;i<variResult.size();i++){
		  VariProYear vari =(VariProYear)variResult.get(i);
  %>
  opts+="<option value='<%=vari.getVari().split(":")[0]%>'><%=vari.getVari().split(":")[0]%></option>";
  proYear+="<%=vari.getVari()%>"+";";
  <%
	  }
  %>
  //将年份插入下拉框
  var optsProYear=""
  <%
	  for(int i=0;i<proYearResult.size();i++){
		  VariProYear vari =(VariProYear)proYearResult.get(i);
  %>
  optsProYear+="<option value='<%=vari.getProYear()%>'><%=vari.getProYear()%></option>";
  proYearAll+="<%=vari.getProYear()%>"+","
  <%
	  }
  %>

  //得到商品分类对应的生产年份
  function getProYear(v){
	  var proYearObj=frm.proYear;
	  removeOpts(proYearObj);
      if(v==null||v==""){
		  var divProYear=proYearAll.split(",");
		  for(j=0;j<divProYear.length;j++){
		      if(divProYear[j]==null||divProYear[j]=="") continue;
	          var oNewItem = document.createElement("option");
			  proYearObj.options.add(oNewItem);
			  oNewItem.innerText=divProYear[j];
			  oNewItem.value=divProYear[j];
	      }
	  }else{
		  var divVariProYear=compareTo(v).split(":");
	      var divProYear=divVariProYear[1].split(",");
		  for(j=0;j<divProYear.length;j++){
		      if(divProYear[j]==null||divProYear[j]=="") continue;
	          var oNewItem = document.createElement("option");
			  proYearObj.options.add(oNewItem);
			  oNewItem.innerText=divProYear[j];
			  oNewItem.value=divProYear[j];
	      }
	  }
  }

  //字符串比较
  function compareTo(v){
      proYearArray=proYear.split(";");
	  for(i=0;i<proYearArray.length;i++){
	      if(proYearArray[i].indexOf(v)!=-1){
		      return proYearArray[i];
			  break;
		  }
	  }
  }

  //删除下拉框的option
 function removeOpts(obj){
     if(obj.length>1){
	     for(i=obj.length-1;i>0;i--){
		     obj.removeChild(obj[i]);
		 }
	 }
 }
</script>
