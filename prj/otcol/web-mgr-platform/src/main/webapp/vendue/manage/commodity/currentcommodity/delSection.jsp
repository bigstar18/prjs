<%@ page contentType="text/html;charset=GBK" %>
<html xmlns:MEBS>
<%@ include file="../../globalDef.jsp"%>

<head>
<title>ɾ�����׽�</title>
</head>
<%
  java.sql.Date sqlDate=new java.sql.Date(System.currentTimeMillis());
  if(request.getParameter("add")!=null){
  String partitionID=request.getParameter("partitionID");
  String numJ=delNull(request.getParameter("num"));
  StringBuffer sql=new StringBuffer();
  DBBean bean=null;
  ResultSet rs=null;
  try{
		  boolean modFlag=true;
		  bean=new DBBean(JNDI);
	      sql.append("select code from v_curcommodity where section="+numJ+" and tradepartition=");
		  sql.append(""+partitionID+"");
          rs=bean.executeQuery(sql.toString());
		  if(rs.next()){
		      modFlag=false;
		  }
		  rs.close();
		  bean.closeStmt();
		  if(modFlag==true){
		  //��ʼ�����ض���
		  TradeStatusValue tsv =null;
		  //���Զ�˽ӿ�
		  KernelEngineRMI dao = (KernelEngineRMI) Naming.lookup("rmi://" + host+":" +port+ "/"+REMOTECLASS+""+partitionID);
		  tsv=dao.getTradeStatus();
		  if(Integer.parseInt(numJ)<=tsv.getLastPartID()){
		      pageContext.setAttribute("modFlag","false");
			  alert("���׽�"+numJ+"�ǵ�ǰ���ڽ��׵Ľ��׽ڻ����Ѿ��������,����ɾ�����׽�!",out);
		  }else{
			String dataBaseDate=null;//ȡ���ݿ������ʱ��
            rs=bean.executeQuery("select sysdate from dual");
	        if(rs.next()){
	            dataBaseDate=rs.getString("sysdate");
	        }
	        rs.close();
	        bean.closeStmt();
		    sql=new StringBuffer();
		    String tradepartition=delNull(request.getParameter("partitionID"));
			int operCnt=0;//������Ʒ����
            String operCode=null;//����������Ʒ��ĺ�
			sql.append("select count(code) as n from v_curcommodity where");
			sql.append(" section>"+numJ+" and tradepartition="+tradepartition+"");
			rs=bean.executeQuery(sql.toString());
			if(rs.next()){
			    operCnt=rs.getInt("n");
			}
			rs.close();
			bean.closeStmt();
			if(operCnt==1){
			    sql=new StringBuffer();
                sql.append("select code from v_curcommodity where");
			    sql.append(" section>"+numJ+" and tradepartition="+tradepartition+"");
				rs=bean.executeQuery(sql.toString());
				if(rs.next()){
				    operCode=rs.getString("code");
				}
			    rs.close();
			    bean.closeStmt();
			}
			sql=new StringBuffer();
		    sql.append("update v_curcommodity set section=section-1,modifytime=to_date('"+disDate(Timestamp.valueOf(dataBaseDate))+"'");
		    sql.append(",'yyyy-mm-dd hh24:mi:ss') where section>"+numJ+" and tradepartition="+tradepartition+"");
			bean.executeUpdate(sql.toString());
		    //�ر�����Դ
		    bean.close();
			
		    //ˢ���ڴ�
            dao.loadCommodity();
			alert("���׽�ɾ���ɹ�!",out);
		  }
	  }else{
          alert("���׽�"+numJ+"������Ʒ,����ɾ��!",out);
	  }
  }
  catch(Exception e)
  {
  	  e.printStackTrace();
      errOpt();
	  alert("���������������Ա��ϵ!",out);
  }finally{
      try{if(rs!=null)rs.close();}catch(Exception ex){}
      if(bean!=null)bean.close();
  }
}
%>
<body>
	<form name=frm id=frm action="" method="post" targetType="hidden" callback='closeDialog(1);'>
		<fieldset width="100%">
		<legend>���뽻�׽ڱ��</legend>
		<BR>
		<span>
		<input type="hidden" name="partitionID" value="${param.partitionID}">
		 <table border="0" cellspacing="0" cellpadding="0" width="100%">
		 	  	<tr height="35">
		 	  		<td align="center">
		 	  		  <input type="text" class="text" maxlength="5" name="num" onchange="checkNumber(this,false,false,'���׽ڱ��')">
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
            <input type="hidden" name="ids" value="${param.ids}">
			      <input type="button" onclick="return frmChk();" class="btn" value="ȷ��">&nbsp;&nbsp;
			      <input name="back" type="button" onclick="window.close()" class="btn" value="ȡ��">&nbsp;&nbsp;
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
	var unitid = frm.num.value;
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
	oneAjax();
	if (add == true) {
		alert("�޴˽��׽ڣ�");
		return false;
	}
  	if(Trim(frm.num.value) == "")
	{
		alert("���׽ڱ�Ų���Ϊ�գ�");
		frm.num.focus();
		return false;
	}else if(parseInt(frm.num.value)<=0){
	    alert("���׽ڱ�ű������0��");
		frm.num.focus();
		return false;
	}else{
	    if(userConfirm()){
  	      frm.add.value="����";
  	      frm.submit();
  	      //return true;
  	    }else{
  	      return false;
  	    }
    }
  }
</script>
</body>
</html>
<%@ include file="../../public/footInc.jsp" %>