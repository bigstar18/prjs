<%@ page contentType="text/html;charset=GBK" %>
<html>
<%@ include file="../../globalDef.jsp"%>
<head>
<title>��ӵ���ǰ������Ʒ</title>
</head>
<%
if(request.getParameter("add")!=null){
    boolean addFlag=true;//��ʾ���������Ʒ����Ϣ�ı�־���Ʊ���
	String code=null;//��Ʒ��
    int status=-1;//��Ʒ״̬
    String firstTime=null;//��һ�ι���ʱ��
    boolean globalFlag=false;//�жϱ����Ƿ��������һ����Ʒ,�źü���־
    int operCnt=0;//�жϲ�����Ʒ������
    String sysDate=null;//���ݿ������ʱ��
	ResultSet rs=null;
	DBBean bean=null;
	StringBuffer sql=null;
	String idStr=request.getParameter("ids");
	String[] ids=null;
	String partitionRatio=null;//request.getParameter("partitionRatio"); xieying
	String kernelClassName=null;
	String logCode=null;
	String userId=null;//����������
	ArrayList resultInfo=null;//��¼��ʾ��Ϣ
    String info=null;//������Ʒ��ʾ��Ϣ
	int result=-1;//ִ�н��׺��Ľӿڷ���ֵ
	String remark=null;;//��־��ע
	int parTradeMode=-1;//�������Ľ���ģʽ
	int tradeMode=-1;//��Ʒ�Ľ���ģʽ
	try{
	    bean=new DBBean(JNDI);
		resultInfo=new ArrayList();
        sql=new StringBuffer();
		DeliveryAction delivery=(DeliveryAction)Class.forName(DELIVERYCLASS).newInstance();
        sql.append("select sysdate from dual");
		rs=bean.executeQuery(sql.toString());
		if(rs.next()){
		    sysDate=rs.getString("sysdate").substring(0,19);
		}
		rs.close();
		sql=new StringBuffer();
		sql.append("select trademode from v_syspartition where partitionid="+partitionRatio+"");
		rs=bean.executeQuery(sql.toString());
		if(rs.next()){
		    parTradeMode=rs.getInt("trademode");
		}
		rs.close();
		if(idStr!=null){
			ids=idStr.split(",");
		    for(int i=0;i<ids.length;i++){
				//�����������ݶ���
                Connection conn = null;
                PreparedStatement ps = null;
			    try{
				    Context initContext = new InitialContext();
                    Context envContext  = (Context)initContext.lookup("java:/comp/env");
                    DataSource ds = (DataSource)envContext.lookup(JNDI);
                    conn = ds.getConnection();
					conn.setAutoCommit(false);
					sql=new StringBuffer();
					code=null;
					status=-1;
					firstTime=null;
					userId=null;
					tradeMode=-1;
					sql.append("select code,status,firsttime,userid,trademode from v_commodity where id=");
					sql.append(""+ids[i]+"");
					ps=conn.prepareStatement(sql.toString());
					rs=ps.executeQuery();
					if(rs.next()){
					    code=rs.getString("code");
						status=rs.getInt("status");
						firstTime=rs.getString("firsttime");
						userId=rs.getString("userid");
						tradeMode=rs.getInt("trademode");
					}
					rs.close();
                    addFlag=true;
					sql=new StringBuffer();
					sql.append("select code from v_curcommodity where code='"+code+"' or commodityid=");
					sql.append(""+ids[i]+"");
					ps=conn.prepareStatement(sql.toString());
					rs=ps.executeQuery();
					if(rs.next()){
					    addFlag=false;
					}
					rs.close();
					//-------------------------------------------------  xieying
					sql=new StringBuffer();
					sql.append("select partitionid from v_syspartition where trademode="+tradeMode+"");
					ps=conn.prepareStatement(sql.toString());
					rs=ps.executeQuery();
					if(rs.next()){
					    partitionRatio = rs.getString("partitionid");
					}
					rs.close();
					//-------------------------------------------------
					//-------------------------------------------------  xieying
					sql=new StringBuffer();
					sql.append("select partitionid, engineclass, quotationclass, submitactionclass, validflag, description from v_syspartition where partitionid="+partitionRatio);
					ps=conn.prepareStatement(sql.toString());
					rs=ps.executeQuery();
					if(rs.next()){
					    kernelClassName = rs.getString("engineclass");
					}
					rs.close();
					//-------------------------------------------------
                    if(addFlag==true&&status!=1&&status!=5&&status!=3&&status!=4){
						//if(tradeMode==parTradeMode){//�ж���Ʒ�Ľ���ģʽ����Ľ���ģʽ�Ƿ���ͬ  xieying
						sql=new StringBuffer();
						sql.append("insert into v_curcommodity(tradepartition,code,commodityid,section,");
						sql.append("lpflag,bargainflag,modifytime) values("+partitionRatio+",'"+code+"'");
                        sql.append(","+ids[i]+",-1,0,0,to_date('"+sysDate+"','yyyy-mm-dd hh24:mi:ss'))");
                        ps=conn.prepareStatement(sql.toString());
						ps.executeUpdate();
                        operCnt++;
						logCode=code;
						globalFlag=true;

						//KernelEngineRMI dao = (KernelEngineRMI) Naming.lookup("rmi://" + host +":"+port+ "/"+REMOTECLASS+""+Integer.parseInt(partitionRatio));
						KernelEngine dao = (KernelEngine)Class.forName(kernelClassName).newInstance();
						TradeDAO dao2 = TradeDAOFactory.getDAO();
						dao.setTradeDAO(dao2);
						dao.setPartition(Integer.parseInt(partitionRatio));
                        //result=delivery.addCommodityCharge(userId,Long.parseLong(ids[i]),					Integer.parseInt(partitionRatio),conn);
						result=dao.addCommodityCharge(userId,Long.parseLong(ids[i]),conn);
							if(result==-1)
							{
								info="Զ�̵���ʧ��";
								resultInfo.add(info);
								conn.rollback();
							}
							if(result==1){
								info="��Ʒ:"+code+";����������:"+userId+"�������ʽ���,�����Ʒʧ��!";
								resultInfo.add(info);
								conn.rollback();
							}else{
								conn.commit();
							}
						/*}else{  xieying
						    info="��Ʒ:"+code+";����������:"+userId+"����ģʽ����Ľ���ģʽ��ͬ,���ʧ��!";
							resultInfo.add(info);
						}*/
					}else{
					    info="��Ʒ:"+code+";����������:"+userId;
						if(addFlag==false){
						    info+=";�Ѿ��ǵ�ǰ������Ʒ,�����ظ����,�����Ʒʧ��!";
						}else if(status==1){
						    info+=";��Ʒ�Ѿ��ǳɽ�״̬,�������,�����Ʒʧ��!";
						}else if(status==5){
						    info+=";��Ʒ�Ѿ��Ƿϳ�,�������,�����Ʒʧ��!";
						}else if(status==3){
						    info+=";��Ʒ�Ѿ������,�������,�����Ʒʧ��!";
						}else if(status==4){
						    info+=";��Ʒ�Ѿ����ϱ�,�������,�����Ʒʧ��!";
						}
						resultInfo.add(info);
					}
				}catch(Exception e){
					conn.rollback();
				    e.printStackTrace();
					info="��Ʒ:"+code+";����������:"+userId+";�����Ʒ����!";
					resultInfo.add(info);
				}
				finally{
					conn.setAutoCommit(true);
		            if(rs!=null){try{rs.close();}catch(Exception ex){}rs=null;}
                    if(ps!=null){try{ps.close();}catch(Exception ex){}ps=null;}
	                try{
                        conn.close();
                    }catch (Exception e){}
                    conn = null;
	            }
			}
		}
		
		if(resultInfo.size()==0){
		    out.println("�����Ʒ���!");
		}else{
			for(int i=0;i<resultInfo.size();i++){
			    out.println(resultInfo.get(i).toString());
				out.println("<br>");
			}
 //           out.println("�����Ʒ���!");
		}
	}catch(Exception e){
	     e.printStackTrace();
	}finally{
         try{if(rs!=null)rs.close();}catch(Exception ex){}
         if(bean!=null)bean.close();
    }
}
%>
<input type="button"  class="bigbtn"  onclick="window.close();" value="�ر�"/>
<body>
</body>
</html>