<%@ page contentType="text/html;charset=GBK" %>
<html xmlns:MEBS>
<%@ include file="../../globalDef.jsp"%>

<head>
<title>�Զ����佻�׽�</title>
</head>
<%
java.sql.Date sqlDate=new java.sql.Date(System.currentTimeMillis());
if(request.getParameter("add")!=null){
Connection conn = null;
PreparedStatement ps = null;
ResultSet rs=null;
int startSection=Integer.parseInt(request.getParameter("startSection"));//��ʼ���׽�
String partitionID=request.getParameter("partitionID");//����
boolean curSectionFlag=false;//�ж��������ʼ���׽��Ƿ���ڵ�ǰ�����н��׵Ľ��׽�,�ǵĻ�����ָ��,��־����
StringBuffer sql=new StringBuffer();
try{
	Context initContext = new InitialContext();
    Context envContext  = (Context)initContext.lookup("java:/comp/env");
    DataSource ds = (DataSource)envContext.lookup(JNDI);
    conn = ds.getConnection();
    conn.setAutoCommit(false);
	sql.append("select section from v_syscurstatus where tradepartition="+partitionID+" and section>=");
	sql.append(""+(startSection)+"");
	ps=conn.prepareStatement(sql.toString());
	rs=ps.executeQuery();
	if(rs.next()){
	    curSectionFlag=true;
	}
	rs.close();
	ps.close();
	if(curSectionFlag==false){//�ж��������ʼ���׽��Ƿ���ڵ�ǰ�����н��׵Ľ��׽�,�ǵĻ�����ָ��
    String ids=request.getParameter("ids");//��ĺż���
    int num=Integer.parseInt(request.getParameter("num"));//ÿ�ڱ����
    String startCode=request.getParameter("startCode");
    String endCode=request.getParameter("endCode");
    //String[] idsArray=ids.split(",");
	String errorMess="";
    int status=-1;//��Ʒ��״̬
    ArrayList optResult=new ArrayList();//��ſ����޸Ľ��׽ڵ���Ʒ
    String returnInfo="";//��Ų����޸Ľ��׽ڵ���Ʒ,���ظ��ͻ�
	int operCnt=0;//������Ʒ����
    String operCode=null;//����������Ʒ��ĺ�
    String w="(";//sql�������
	String dataBaseDate=null;//ȡ���ݿ������ʱ��
	//���Զ�˽ӿ�
	KernelEngineRMI dao = (KernelEngineRMI) Naming.lookup("rmi://" + host +":"+port+ "/"+REMOTECLASS+""+partitionID);
	ps=conn.prepareStatement("select sysdate from dual");
    rs=ps.executeQuery();
	if(rs.next()){
	    dataBaseDate=rs.getString("sysdate");
	}
	rs.close();
	ps.close();
    sql=new StringBuffer("select code,bargainflag,section from v_curcommodity");
    sql.append(" where code>='"+startCode+"' and code<='"+endCode+"' and tradepartition");
    sql.append("="+partitionID+" order by code asc");
	ps=conn.prepareStatement(sql.toString());
    rs=ps.executeQuery(sql.toString());
    while(rs.next()){
       String code=rs.getString("code");
       status=rs.getInt("bargainflag");
	   if(FindData.judgeCommTrading(JNDI,Integer.parseInt(partitionID),rs.getInt("section"))){
           if(status==0){
               optResult.add(code);
           }else if(status==1){
               returnInfo=returnInfo+code+",";
           }
	   }else{
	       returnInfo=returnInfo+code+",";
           errorMess+=code+",";
	   }
    }
    //�ر�����Դ
    rs.close();
    ps.close();
    if(optResult.size()>=num){
    for(int i=0;i<optResult.size();i++){
      if((i+1)%num==0||(i+1)==optResult.size()){
        w=w+"'"+optResult.get(i).toString()+"'"+")";
		ps=conn.prepareStatement("update v_curcommodity set section="+startSection+",modifytime=to_date('"+disDate(Timestamp.valueOf(dataBaseDate))+"','yyyy-mm-dd hh24:mi:ss') where code in "+w);
		ps.executeUpdate();
		ps.close();
        w="(";
        startSection=startSection+1;
      }else{
        w=w+"'"+optResult.get(i).toString()+"'"+",";
      }
    }
	operCnt=optResult.size();
	if(operCnt==1){
	    operCode=startCode;
	}
	
	//�ر�����Դ����
	conn.commit();
    conn.setAutoCommit(true);
    conn.close();
	//ˢ���ڴ�
    dao.loadCommodity();
    if("".equals(returnInfo)){
		alert("�Զ����佻�׽ڳɹ�!",out);
    }else{
        alert("�������:\r\n"+"��Ʒ "+returnInfo+"�ǳɽ�״̬�������ڽ�����,���ܷ��佻�׽�!",out);
    }
    }else{
	    if(!"".equals(errorMess)){
	        errorMess+="���ڽ�����,���ܷ��佻�׽�;";
	    }
		alert(errorMess+"��Ʒ����С��ÿ�ڱ����,������ȷ����!",out);
    }
 }else{
     alert("���������ʼ���׽ڱ���Ѿ��������,���������룡",out);
 }
}

catch(Exception e)
{   
	conn.rollback();
  	e.printStackTrace();
    errOpt();
	alert("���������������Ա��ϵ!",out);
}finally{
	if(rs!=null){try{rs.close();}catch(Exception ex){}rs=null;}
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
		<legend>���뽻�׽ڱ��</legend>
		<BR>
		<span>
		 <table border="0" cellspacing="0" cellpadding="0" width="100%">
		       <tr height="35">
		 	  	    <td align="right">��ʼ��ĺţ�</td>
		 	  		<td align="left">
		 	  		  <input type="text" maxlength="64" class="text" name="startCode">
		 	  	    </td>
		 	  	</tr>
		 	  	<tr height="35">
		 	  	    <td align="right">������ĺţ�</td>
		 	  		<td align="left">
		 	  		  <input type="text" maxlength="64" class="text" name="endCode">
		 	  	    </td>
		 	  	</tr>
		 	  	<tr height="35">
		 	  	    <td align="right">��ʼ���׽ڱ�ţ�</td>
		 	  		<td align="left">
		 	  		  <input type="text" maxlength="5" class="text" name="startSection" onchange="checkNumber(this,false,false,'��ʼ���׽ڱ��')">
		 	  	    </td>
		 	  	</tr>
		 	  	<tr height="35">
		 	  	  <td align="right">ÿ�ڱ��������</td>
		 	  	  <td align="left">
		 	  		  <input type="text" maxlength="10" class="text" name="num" onchange="checkNumber(this,false,false,'ÿ�ڱ������')">
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
			<input type="button" onclick="return frmChk();" class="btn" value="ȷ��">&nbsp;&nbsp;
			<input name="back" type="button" onclick="window.close()" class="btn" value="ȡ��">&nbsp;&nbsp;
            </div>
            </td>
          </tr>
     </table>
	</form>
<script language="javascript">
  function frmChk(){
      if(Trim(frm.startCode.value) == "")
	  {
		  alert("��ʼ��ĺŲ���Ϊ�գ�");
		  frm.startCode.focus();
		  return false;
	  }else if(Trim(frm.endCode.value) == "")
	  {
		  alert("������ĺŲ���Ϊ�գ�");
		  frm.endCode.focus();
		  return false;
	  }
  	  else if(Trim(frm.startSection.value) == "")
	  {
		  alert("��ʼ���׽ڱ�Ų���Ϊ�գ�");
		  frm.startSection.focus();
		  return false;
	  }
	  else if(parseInt(frm.startSection.value)<=0)
	  {
		  alert("��ʼ���׽ڱ�ű������0��");
		  frm.startSection.focus();
		  return false;
	  }
	  else if(Trim(frm.num.value) == "")
	  {
		  alert("ÿ�ڱ��������Ϊ�գ�");
		  frm.num.focus();
		  return false;
	  }else if(parseInt(frm.num.value)<=0)
	  {
		  alert("ÿ�ڱ�����������0��");
		  frm.num.focus();
		  return false;
	  }
	  else{
	    if(userConfirm()){
  	      frm.add.value="����";
  	      frm.submit();
  	      //return true;
  	    }else{
  	      return false;
  	    }
    }
  }
  
  //���Ƶ�����������κν��׽�ʱ
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