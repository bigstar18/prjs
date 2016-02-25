<%@ page contentType="text/html;charset=GBK" %>
<html xmlns:MEBS>
<%@ include file="../../globalDef.jsp"%>

<head>
<title>指定交易节</title>
</head>
<%
   java.sql.Date sqlDate=new java.sql.Date(System.currentTimeMillis());
   if(request.getParameter("add")!=null){
   Connection conn = null;
   PreparedStatement ps = null;
   ResultSet rs=null;
   ResultSet rsExt=null;
   StringBuffer sql=new StringBuffer();
   try{
	Context initContext = new InitialContext();
    Context envContext  = (Context)initContext.lookup("java:/comp/env");
    DataSource ds = (DataSource)envContext.lookup(JNDI);
    conn = ds.getConnection();
    conn.setAutoCommit(false);
    String idV=request.getParameter("ids");//商品标的号集合
	boolean modFlag=true;//标志是否有些商品不能修改交易节
    boolean globalFlag=false;//判断本次是否最少删除一个商品,才好记日志
    int operCnt=0;//判断操作当前商品的数量
    String operCode=null;//只操作单个商品记录的标的号
    String returnInfo="";//返回不能修改交易节的标的号
    boolean curSectionFlag=false;//判断输入的交易节是否大于当前正进行交易的交易节,是的话可以指定,标志控制
    String sysdate="";//数据库服务器时间
	String sids="";//需要修改商品标的号
	boolean tradingFlag=true;//判断商品是否正在交易
    int section=Integer.parseInt(request.getParameter("section"));
    String partitionID=request.getParameter("partitionID");
	String[] ids=null;//用数组存放标的号
	//获得远端接口
	KernelEngineRMI dao = (KernelEngineRMI) Naming.lookup("rmi://" + host+":"+port+ "/"+REMOTECLASS+""+partitionID);
    sql.append("select sysdate from dual");
    ps=conn.prepareStatement(sql.toString());
	rs=ps.executeQuery();
	if(rs.next()){
	    sysdate=rs.getString(1).substring(0,18);
	}
	rs.close();
	ps.close();
	if(section!=-1){
	    sql=new StringBuffer();
	    sql.append("select section from v_syscurstatus where tradepartition="+partitionID+" and section>=");
	    sql.append(""+section+"");
	    ps=conn.prepareStatement(sql.toString());
	    rs=ps.executeQuery();
		if(rs.next()){
		    curSectionFlag=true;
		}
    }
	if(curSectionFlag==false){
	    if(idV!=null){
			ids=idV.split(",");
		    for(int i=0;i<ids.length;i++){
			    sids+=ids[i]+",";
                sql=new StringBuffer();
				sql.append("select bargainflag,section from v_curcommodity where code='"+ids[i]+"'");
				ps=conn.prepareStatement(sql.toString());
	            rs=ps.executeQuery();
				if(rs.next()){
				    sql=new StringBuffer();
					sql.append("select section from v_syscurstatus where status=2 and section="+rs.getInt(2)+"");
					sql.append(" and tradepartition="+partitionID+"");
                    ps=conn.prepareStatement(sql.toString());
	                rsExt=ps.executeQuery();
					if(rsExt.next()){
					    tradingFlag=false;
					}
					rsExt.close();
					ps.close();
					if(rs.getInt("bargainflag")==0&&tradingFlag==true){
						sql=new StringBuffer();
						sql.append("update v_curcommodity set section="+section+",modifytime=");
						sql.append("to_date('"+sysdate+"','yyyy-mm-dd hh24:mi:ss') where code=");
						sql.append("'"+ids[i]+"'");
						ps=conn.prepareStatement(sql.toString());
						ps.executeUpdate(sql.toString());
						operCnt+=1;
						operCode=ids[i];
                        globalFlag=true;
					}else{
						modFlag=false;
						returnInfo+=ids[i]+",";
					}
				}
			}
		}
        
		//关闭数据源操作
	    conn.commit();
        conn.setAutoCommit(true);
        conn.close();
	    //刷新内存
        dao.loadCommodity();
    	if(modFlag==true){
	        alert("指定交易节成功!",out);
	    }else{
	        alert(returnInfo+"是成交状态或者正在交易中不能指定交易节!",out);
	    }
	}else{
	    alert("所输入的交易节编号已经交易完成,请重新指定！",out);
	}
}
catch(Exception e)
{
	conn.rollback();
	e.printStackTrace();
    errOpt();
	alert("操作出错，请与管理员联系!",out);
}finally{
	if(rs!=null){try{rs.close();}catch(Exception ex){}rs=null;}
	if(rsExt!=null){try{rsExt.close();}catch(Exception ex){}rsExt=null;}
    if(ps!=null){try{ps.close();}catch(Exception ex){}ps=null;}
    try{
        conn.close();
    }catch (Exception e){}
    conn = null;
}
}
%>
<body>
	<form name=frm id=frm action="" targetType="hidden" callback='closeDialog(1);'>
		<fieldset width="100%">
		<legend>输入交易节编号</legend>
		<BR>
		<span>
		 <table border="0" cellspacing="0" cellpadding="0" width="100%">
		 	  	<tr height="35">
		 	  	    <td align="right">交易节编号：</td>
		 	  		<td align="left">
		 	  		  <input type="text" class="text" name="section" maxlength="5" onchange="checkNumber(this,false,false,'交易节编号')">
		 	  	    </td>
		 	  	</tr>
				<tr height="35">
		 	  	    <td align="right">当前有效交易节起始编号：</td>
		 	  		<td align="left">
					  <db:select var="v_result" columns="status,section" table="v_syscurstatus" where="tradepartition=${param.partitionID}">
					      <c:set var="curParSection" value="${v_result.section}"/>
						  <c:set var="status" value="${v_result.status}"/>
					  </db:select>
					  <c:choose>
						<c:when test="${curParSection==0}">    
							<input type="text" name="now" class="text" value="1" readonly>
						</c:when>
						<c:when test="${status==3}">
							<input type="text" name="now" class="text" value="${curParSection+1}" readonly>
						</c:when>
					    <c:otherwise>  
						 <input type="text" name="now" class="text" value="${curParSection}" readonly>
					    </c:otherwise>
					  
					 </c:choose>

		 	  		  
		 	  	    </td>
		 	  	</tr>
		 	  	<tr height="35">
		 	  	  <td align="right">不属于任何交易节：</td>
		 	  	  <td align="left">
		 	  		  <input type="checkbox" name="noSection" onclick="clickNoSection();">
		 	  	  </td>
		 	  	</tr>
		 </table>
		 </span>
     </fieldset>
		 <table border="0" cellspacing="0" cellpadding="0" width="100%">
          <tr height="35">
            <td width="40%">
            <div align="center">
            <input type="hidden" name="add">
            <input type="hidden" name="partitionID" value="${param.partitionID}">
            <input type="hidden" name="ids" value="${param.ids}">
			      <input type="button" onclick="return frmChk();" class="btn" value="确认">&nbsp;&nbsp;
			      <input name="back" type="button" onclick="window.close()" class="btn" value="取消">&nbsp;&nbsp;
            </div>
            </td>
          </tr>
     </table>
	</form>
<script language="javascript">
var request = new ActiveXObject("Microsoft.XMLHTTP");
var add = true;
function oneAjax(){
	var tradepartition = frm.partitionID.value;
	var unitid = frm.section.value;
	request.onreadystatechange = stateChanged;
	request.open("post","haveSection.jsp?tradepartition="+tradepartition+"&unitid="+unitid,false);
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
			add = false;
		} else if (no == 0) {
			add = true;
		}
	}
}

function frmChk(){
	if(frm.noSection.checked==true){
	} else {
		oneAjax();
		if (add == true) {
			alert("无此交易节！");
			return false;
		}
	}
	if(Trim(frm.section.value) == "") {
		alert("交易节编号不能为空！");
		frm.section.focus();
		return false;
	} else if(parseInt(frm.section.value)<=0&&frm.noSection.checked==false) {
		  alert("交易节编号必须大于0！");
		  frm.section.focus();
		  return false;
	} else if(frm.section.value<frm.now.value&&frm.noSection.checked==false) {
		  alert("交易节 "+frm.section.value+" 不是有效的交易节，请重新指定");
		  frm.section.focus();
		  return false;
	} else {
	    if(userConfirm()){
  	      frm.add.value="保存";
  	      frm.submit();
  	      //return true;
  	    }else{
  	      return false;
  	    }
    }
  }
  
  //控制当点击不属于任何交易节时
  function clickNoSection(){
    if(frm.noSection.checked==true){
      frm.section.value="-1";
      frm.section.readOnly=true;
    }else{
      frm.section.value="";
      frm.section.readOnly=false;
    }
  }
</script>
</body>
</html>
<%@ include file="/vendue/manage/public/footInc.jsp" %>