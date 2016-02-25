<%@ page contentType="text/html;charset=GBK" %>
<html>
<%@ include file="../../globalDef.jsp"%>
<head>
<title>添加到当前交易商品</title>
</head>
<%
if(request.getParameter("add")!=null){
    boolean addFlag=true;//提示不能添加商品的信息的标志控制变量
	String code=null;//商品号
    int status=-1;//商品状态
    String firstTime=null;//第一次挂牌时间
    boolean globalFlag=false;//判断本次是否最少添加一个商品,才好记日志
    int operCnt=0;//判断操作商品的数量
    String sysDate=null;//数据库服务器时间
	ResultSet rs=null;
	DBBean bean=null;
	StringBuffer sql=null;
	String idStr=request.getParameter("ids");
	String[] ids=null;
	String partitionRatio=null;//request.getParameter("partitionRatio"); xieying
	String kernelClassName=null;
	String logCode=null;
	String userId=null;//所属交易商
	ArrayList resultInfo=null;//记录提示信息
    String info=null;//单条商品提示信息
	int result=-1;//执行交易核心接口返回值
	String remark=null;;//日志备注
	int parTradeMode=-1;//所属板块的交易模式
	int tradeMode=-1;//商品的交易模式
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
				//创建连接数据对象
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
						//if(tradeMode==parTradeMode){//判断商品的交易模式与板块的交易模式是否相同  xieying
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
								info="远程调用失败";
								resultInfo.add(info);
								conn.rollback();
							}
							if(result==1){
								info="商品:"+code+";所属交易商:"+userId+"交易商资金不足,添加商品失败!";
								resultInfo.add(info);
								conn.rollback();
							}else{
								conn.commit();
							}
						/*}else{  xieying
						    info="商品:"+code+";所属交易商:"+userId+"交易模式与板块的交易模式不同,添加失败!";
							resultInfo.add(info);
						}*/
					}else{
					    info="商品:"+code+";所属交易商:"+userId;
						if(addFlag==false){
						    info+=";已经是当前交易商品,不能重复添加,添加商品失败!";
						}else if(status==1){
						    info+=";商品已经是成交状态,不能添加,添加商品失败!";
						}else if(status==5){
						    info+=";商品已经是废除,不能添加,添加商品失败!";
						}else if(status==3){
						    info+=";商品已经被拆标,不能添加,添加商品失败!";
						}else if(status==4){
						    info+=";商品已经被合标,不能添加,添加商品失败!";
						}
						resultInfo.add(info);
					}
				}catch(Exception e){
					conn.rollback();
				    e.printStackTrace();
					info="商品:"+code+";所属交易商:"+userId+";添加商品出错!";
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
		    out.println("添加商品完毕!");
		}else{
			for(int i=0;i<resultInfo.size();i++){
			    out.println(resultInfo.get(i).toString());
				out.println("<br>");
			}
 //           out.println("添加商品完毕!");
		}
	}catch(Exception e){
	     e.printStackTrace();
	}finally{
         try{if(rs!=null)rs.close();}catch(Exception ex){}
         if(bean!=null)bean.close();
    }
}
%>
<input type="button"  class="bigbtn"  onclick="window.close();" value="关闭"/>
<body>
</body>
</html>