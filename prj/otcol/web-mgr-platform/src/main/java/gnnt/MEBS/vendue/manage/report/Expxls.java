package gnnt.MEBS.vendue.manage.report;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class Expxls
{
  public String jndi;
  public int type;
  public String starttime;
  public String endtime;
  public int proType;
  public int marketType;
  public String defaultKey;
  public String allMarket;
  public int newStatus;
  
  public void doDownload(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException, SQLException
  {
    try
    {
      HSSFWorkbook localHSSFWorkbook = new HSSFWorkbook();
      HSSFSheet localHSSFSheet = localHSSFWorkbook.createSheet("new sheet");
      Connection localConnection = null;
      PreparedStatement localPreparedStatement = null;
      ResultSet localResultSet = null;
      try
      {
        InitialContext localInitialContext = new InitialContext();
        localObject1 = (Context)localInitialContext.lookup("java:/comp/env");
        localObject2 = (DataSource)((Context)localObject1).lookup(this.jndi);
        localConnection = ((DataSource)localObject2).getConnection();
        String str1;
        String str2;
        String str3;
        String str4;
        String str5;
        Object localObject5;
        Object localObject6;
        Object localObject7;
        if (this.type == 0)
        {
          localObject3 = "";
          localObject4 = paramHttpServletRequest.getParameter("userCode");
          str1 = paramHttpServletRequest.getParameter("name");
          str2 = paramHttpServletRequest.getParameter("balance");
          str3 = paramHttpServletRequest.getParameter("province");
          str4 = paramHttpServletRequest.getParameter("tradePartition");
          str5 = paramHttpServletRequest.getParameter("key");
          if ((localObject4 != null) && (!"".equals(localObject4))) {
            localObject3 = (String)localObject3 + " and u2.usercode like '" + (String)localObject4 + "'";
          }
          if ((str1 != null) && (!"".equals(str1))) {
            localObject3 = (String)localObject3 + " and u1.name like '" + str1 + "'";
          }
          if ((str4 != null) && (!"".equals(str4))) {
            localObject3 = (String)localObject3 + " and u1.str2 = '" + str4 + "'";
          }
          if ((str2 != null) && (!"".equals(str2))) {
            if (Integer.parseInt(str2) == 1) {
              localObject3 = (String)localObject3 + " and u2.balance > 0";
            } else if (Integer.parseInt(str2) == 2) {
              localObject3 = (String)localObject3 + " and u2.balance <= 0";
            }
          }
          if ((str5 != null) && (!"".equals(str5))) {
            if (Integer.parseInt(str5) == 1) {
              localObject3 = (String)localObject3 + " and (u2.keycode is not null or u2.keycode='" + this.defaultKey + "')";
            } else if (Integer.parseInt(str5) == 2) {
              localObject3 = (String)localObject3 + " and  u2.keycode='" + this.defaultKey + "'";
            } else if (Integer.parseInt(str5) == 3) {
              localObject3 = (String)localObject3 + " and u2.keycode is null";
            }
          }
          if ((str3 != null) && (!"".equals(str3))) {
            localObject3 = (String)localObject3 + " and u1.str1 = '" + str3 + "'";
          }
          localObject5 = new StringBuffer();
          ((StringBuffer)localObject5).append("select u1.id,u1.name,u1.modifytime,u2.usercode,u2.password,");
          ((StringBuffer)localObject5).append(" u2.status, u2.overdraft, u2.feecut,u2.balance,u2.frozencaptial,u2.category1,");
          ((StringBuffer)localObject5).append("u2.category2,u2.tradecount,u2.totalsecurity,u3.name as province,u4.name as market from tradeuserext u1,tradeuser u2");
          ((StringBuffer)localObject5).append(",dictable u3,dictable u4 where u1.usercode=u2.usercode and u2.status<>3 and u3.type=" + this.proType + " and u1.str1=u3.value and u1.str2=");
          ((StringBuffer)localObject5).append("u4.value and u4.type=" + this.marketType + "");
          localObject5 = ((StringBuffer)localObject5).append((String)localObject3 + " order by u2.usercode desc");
          localPreparedStatement = localConnection.prepareStatement(((StringBuffer)localObject5).toString());
          localResultSet = localPreparedStatement.executeQuery();
          localObject6 = localHSSFSheet.createRow(0);
          localObject7 = ((HSSFRow)localObject6).createCell((short)0);
          ((HSSFCell)localObject7).setEncoding((short)1);
          ((HSSFCell)localObject7).setCellValue("交易商代码");
          localObject7 = ((HSSFRow)localObject6).createCell((short)1);
          ((HSSFCell)localObject7).setEncoding((short)1);
          ((HSSFCell)localObject7).setCellValue("企业全称");
          localObject7 = ((HSSFRow)localObject6).createCell((short)2);
          ((HSSFCell)localObject7).setEncoding((short)1);
          ((HSSFCell)localObject7).setCellValue("交易商状态");
          localObject7 = ((HSSFRow)localObject6).createCell((short)3);
          ((HSSFCell)localObject7).setEncoding((short)1);
          ((HSSFCell)localObject7).setCellValue("虚拟资金");
          localObject7 = ((HSSFRow)localObject6).createCell((short)4);
          ((HSSFCell)localObject7).setEncoding((short)1);
          ((HSSFCell)localObject7).setCellValue("费用折扣");
          localObject7 = ((HSSFRow)localObject6).createCell((short)5);
          ((HSSFCell)localObject7).setEncoding((short)1);
          ((HSSFCell)localObject7).setCellValue("资金余额");
          localObject7 = ((HSSFRow)localObject6).createCell((short)6);
          ((HSSFCell)localObject7).setEncoding((short)1);
          ((HSSFCell)localObject7).setCellValue("冻结资金");
          localObject7 = ((HSSFRow)localObject6).createCell((short)7);
          ((HSSFCell)localObject7).setEncoding((short)1);
          ((HSSFCell)localObject7).setCellValue("交易商分类1");
          localObject7 = ((HSSFRow)localObject6).createCell((short)8);
          ((HSSFCell)localObject7).setEncoding((short)1);
          ((HSSFCell)localObject7).setCellValue("交易商分类2");
          localObject7 = ((HSSFRow)localObject6).createCell((short)9);
          ((HSSFCell)localObject7).setEncoding((short)1);
          ((HSSFCell)localObject7).setCellValue("本次交易累计扣除保证金");
          localObject7 = ((HSSFRow)localObject6).createCell((short)10);
          ((HSSFCell)localObject7).setEncoding((short)1);
          ((HSSFCell)localObject7).setCellValue("成交次数");
          localObject7 = ((HSSFRow)localObject6).createCell((short)11);
          ((HSSFCell)localObject7).setEncoding((short)1);
          ((HSSFCell)localObject7).setCellValue("省份");
          localObject7 = ((HSSFRow)localObject6).createCell((short)12);
          ((HSSFCell)localObject7).setEncoding((short)1);
          ((HSSFCell)localObject7).setCellValue("所属市场");
          for (int i = 1; localResultSet.next(); i++)
          {
            localObject6 = localHSSFSheet.createRow(i);
            localObject7 = ((HSSFRow)localObject6).createCell((short)0);
            ((HSSFCell)localObject7).setEncoding((short)1);
            ((HSSFCell)localObject7).setCellValue(localResultSet.getString("usercode"));
            localObject7 = ((HSSFRow)localObject6).createCell((short)1);
            ((HSSFCell)localObject7).setEncoding((short)1);
            ((HSSFCell)localObject7).setCellValue(localResultSet.getString("name"));
            localObject7 = ((HSSFRow)localObject6).createCell((short)2);
            ((HSSFCell)localObject7).setEncoding((short)1);
            int j = localResultSet.getInt("status");
            if (j == 1) {
              ((HSSFCell)localObject7).setCellValue("正常");
            } else if (j == 2) {
              ((HSSFCell)localObject7).setCellValue("禁止交易");
            } else if (j == 3) {
              ((HSSFCell)localObject7).setCellValue("删除");
            }
            localObject7 = ((HSSFRow)localObject6).createCell((short)3);
            ((HSSFCell)localObject7).setEncoding((short)1);
            ((HSSFCell)localObject7).setCellValue(localResultSet.getString("overdraft"));
            localObject7 = ((HSSFRow)localObject6).createCell((short)4);
            ((HSSFCell)localObject7).setEncoding((short)1);
            ((HSSFCell)localObject7).setCellValue(localResultSet.getString("feecut"));
            localObject7 = ((HSSFRow)localObject6).createCell((short)5);
            ((HSSFCell)localObject7).setEncoding((short)1);
            ((HSSFCell)localObject7).setCellValue(localResultSet.getString("balance"));
            localObject7 = ((HSSFRow)localObject6).createCell((short)6);
            ((HSSFCell)localObject7).setEncoding((short)1);
            ((HSSFCell)localObject7).setCellValue(localResultSet.getString("frozencaptial"));
            localObject7 = ((HSSFRow)localObject6).createCell((short)7);
            ((HSSFCell)localObject7).setEncoding((short)1);
            ((HSSFCell)localObject7).setCellValue(localResultSet.getString("category1"));
            localObject7 = ((HSSFRow)localObject6).createCell((short)8);
            ((HSSFCell)localObject7).setEncoding((short)1);
            ((HSSFCell)localObject7).setCellValue(localResultSet.getString("category2"));
            localObject7 = ((HSSFRow)localObject6).createCell((short)9);
            ((HSSFCell)localObject7).setEncoding((short)1);
            ((HSSFCell)localObject7).setCellValue(localResultSet.getString("totalsecurity"));
            localObject7 = ((HSSFRow)localObject6).createCell((short)10);
            ((HSSFCell)localObject7).setEncoding((short)1);
            ((HSSFCell)localObject7).setCellValue(localResultSet.getString("tradeCount"));
            localObject7 = ((HSSFRow)localObject6).createCell((short)11);
            ((HSSFCell)localObject7).setEncoding((short)1);
            ((HSSFCell)localObject7).setCellValue(localResultSet.getString("province"));
            localObject7 = ((HSSFRow)localObject6).createCell((short)12);
            ((HSSFCell)localObject7).setEncoding((short)1);
            ((HSSFCell)localObject7).setCellValue(localResultSet.getString("market"));
          }
        }
        else if (this.type == 3)
        {
          localObject3 = "select u1.*,u2.name as province,u3.name as ";
          localObject3 = (String)localObject3 + "market from tradeuserext u1,dictable u2,dictable ";
          localObject3 = (String)localObject3 + "u3,operpartuser u4 where u2.type=" + this.proType + " and ";
          localObject3 = (String)localObject3 + "u2.value=u1.str1 and u3.type=" + this.marketType + " and ";
          localObject3 = (String)localObject3 + "u1.str2=u3.value and u1.id=u4.userid and u4.newstatus=";
          localObject3 = (String)localObject3 + "" + this.newStatus + "";
          localObject4 = "";
          str1 = paramHttpServletRequest.getParameter("userCode");
          str2 = paramHttpServletRequest.getParameter("name");
          str3 = paramHttpServletRequest.getParameter("yesNotTrader");
          str4 = paramHttpServletRequest.getParameter("province");
          str5 = paramHttpServletRequest.getParameter("tradePartition");
          localObject5 = paramHttpServletRequest.getParameter("market");
          localObject6 = paramHttpServletRequest.getParameter("key");
          localObject7 = paramHttpServletRequest.getParameter("balance");
          if ((str1 != null) && (!"".equals(str1))) {
            localObject4 = (String)localObject4 + " and u1.usercode like '" + str1 + "'";
          }
          if ((str2 != null) && (!"".equals(str2))) {
            localObject4 = (String)localObject4 + " and u1.name like '" + str2 + "'";
          }
          if ((str3 != null) && (!"".equals(str3))) {
            if (Integer.parseInt(str3) == 1) {
              localObject4 = (String)localObject4 + " and u1.userflag=1";
            } else if (Integer.parseInt(str3) == 2) {
              localObject4 = (String)localObject4 + " and u1.userflag=0";
            }
          }
          if ((str4 != null) && (!"".equals(str4))) {
            localObject4 = (String)localObject4 + " and u2.value='" + str4 + "'";
          }
          if ((str5 != null) && (!"".equals(str5))) {
            localObject4 = (String)localObject4 + " and u1.str2='" + str5 + "'";
          }
          if ((localObject5 != null) && (!"".equals(localObject5)) && (!((String)localObject5).equals(this.allMarket))) {
            localObject4 = (String)localObject4 + " and u1.str2='" + (String)localObject5 + "'";
          }
          if ((localObject7 != null) && (!"".equals(localObject7))) {
            if (Integer.parseInt((String)localObject7) == 1)
            {
              if (((String)localObject5).equals(this.allMarket)) {
                localObject4 = (String)localObject4 + " and u1.usercode in (select usercode from tradeuser where  balance>0)";
              } else {
                localObject4 = (String)localObject4 + " and u1.usercode in (select usercode from tradeusermarket where  balance>0 and marketid=" + (String)localObject5 + ") ";
              }
            }
            else if (Integer.parseInt((String)localObject7) == 2) {
              if (((String)localObject5).equals(this.allMarket)) {
                localObject4 = (String)localObject4 + " and u1.usercode in (select usercode from tradeuser where  balance<=0)";
              } else {
                localObject4 = (String)localObject4 + " and u1.usercode in (select usercode from tradeusermarket where  balance<=0 and marketid=" + (String)localObject5 + ") ";
              }
            }
          }
          if ((localObject6 != null) && (!"".equals(localObject6))) {
            if (Integer.parseInt((String)localObject6) == 1) {
              localObject4 = (String)localObject4 + " and u1.usercode in (select usercode from tradeuser where (keycode is not null or keycode='" + this.defaultKey + "'))";
            } else if (Integer.parseInt((String)localObject6) == 2) {
              localObject4 = (String)localObject4 + " and u1.usercode in (select usercode from tradeuser where  keycode='" + this.defaultKey + "')";
            } else if (Integer.parseInt((String)localObject6) == 3) {
              localObject4 = (String)localObject4 + " and u1.usercode in (select usercode from tradeuser where  keycode is null)";
            }
          }
          localObject3 = (String)localObject3 + (String)localObject4;
          localObject3 = (String)localObject3 + " order by u1.createtime desc";
          localPreparedStatement = localConnection.prepareStatement((String)localObject3);
          localResultSet = localPreparedStatement.executeQuery();
          HSSFRow localHSSFRow = localHSSFSheet.createRow(0);
          HSSFCell localHSSFCell = localHSSFRow.createCell((short)0);
          localHSSFCell.setEncoding((short)1);
          localHSSFCell.setCellValue("交易商代码");
          localHSSFCell = localHSSFRow.createCell((short)1);
          localHSSFCell.setEncoding((short)1);
          localHSSFCell.setCellValue("交易用户标志");
          localHSSFCell = localHSSFRow.createCell((short)2);
          localHSSFCell.setEncoding((short)1);
          localHSSFCell.setCellValue("企业全称");
          localHSSFCell = localHSSFRow.createCell((short)3);
          localHSSFCell.setEncoding((short)1);
          localHSSFCell.setCellValue("省份");
          localHSSFCell = localHSSFRow.createCell((short)4);
          localHSSFCell.setEncoding((short)1);
          localHSSFCell.setCellValue("企业详细地址");
          localHSSFCell = localHSSFRow.createCell((short)5);
          localHSSFCell.setEncoding((short)1);
          localHSSFCell.setCellValue("邮编");
          localHSSFCell = localHSSFRow.createCell((short)6);
          localHSSFCell.setEncoding((short)1);
          localHSSFCell.setCellValue("法人姓名");
          localHSSFCell = localHSSFRow.createCell((short)7);
          localHSSFCell.setEncoding((short)1);
          localHSSFCell.setCellValue("email");
          localHSSFCell = localHSSFRow.createCell((short)8);
          localHSSFCell.setEncoding((short)1);
          localHSSFCell.setCellValue("身份证号");
          localHSSFCell = localHSSFRow.createCell((short)9);
          localHSSFCell.setEncoding((short)1);
          localHSSFCell.setCellValue("电话");
          localHSSFCell = localHSSFRow.createCell((short)10);
          localHSSFCell.setEncoding((short)1);
          localHSSFCell.setCellValue("传真");
          localHSSFCell = localHSSFRow.createCell((short)11);
          localHSSFCell.setEncoding((short)1);
          localHSSFCell.setCellValue("手机");
          localHSSFCell = localHSSFRow.createCell((short)12);
          localHSSFCell.setEncoding((short)1);
          localHSSFCell.setCellValue("交易代表姓名");
          localHSSFCell = localHSSFRow.createCell((short)13);
          localHSSFCell.setEncoding((short)1);
          localHSSFCell.setCellValue("email");
          localHSSFCell = localHSSFRow.createCell((short)14);
          localHSSFCell.setEncoding((short)1);
          localHSSFCell.setCellValue("身份证号");
          localHSSFCell = localHSSFRow.createCell((short)15);
          localHSSFCell.setEncoding((short)1);
          localHSSFCell.setCellValue("电话");
          localHSSFCell = localHSSFRow.createCell((short)16);
          localHSSFCell.setEncoding((short)1);
          localHSSFCell.setCellValue("传真");
          localHSSFCell = localHSSFRow.createCell((short)17);
          localHSSFCell.setEncoding((short)1);
          localHSSFCell.setCellValue("手机");
          localHSSFCell = localHSSFRow.createCell((short)18);
          localHSSFCell.setEncoding((short)1);
          localHSSFCell.setCellValue("营业执照编号");
          localHSSFCell = localHSSFRow.createCell((short)19);
          localHSSFCell.setEncoding((short)1);
          localHSSFCell.setCellValue("有效期");
          localHSSFCell = localHSSFRow.createCell((short)20);
          localHSSFCell.setEncoding((short)1);
          localHSSFCell.setCellValue("企业开户行");
          localHSSFCell = localHSSFRow.createCell((short)21);
          localHSSFCell.setEncoding((short)1);
          localHSSFCell.setCellValue("企业帐号");
          localHSSFCell = localHSSFRow.createCell((short)22);
          localHSSFCell.setEncoding((short)1);
          localHSSFCell.setCellValue("信誉度");
          localHSSFCell = localHSSFRow.createCell((short)23);
          localHSSFCell.setEncoding((short)1);
          localHSSFCell.setCellValue("注册时间");
          localHSSFCell = localHSSFRow.createCell((short)24);
          localHSSFCell.setEncoding((short)1);
          localHSSFCell.setCellValue("更新时间");
          localHSSFCell = localHSSFRow.createCell((short)25);
          localHSSFCell.setEncoding((short)1);
          localHSSFCell.setCellValue("所属市场");
          for (int k = 1; localResultSet.next(); k++)
          {
            localHSSFRow = localHSSFSheet.createRow(k);
            localHSSFCell = localHSSFRow.createCell((short)0);
            localHSSFCell.setEncoding((short)1);
            localHSSFCell.setCellValue(localResultSet.getString("usercode"));
            localHSSFCell = localHSSFRow.createCell((short)1);
            localHSSFCell.setEncoding((short)1);
            int m = localResultSet.getInt("userflag");
            if (m == 1) {
              localHSSFCell.setCellValue("交易用户");
            } else if (m == 0) {
              localHSSFCell.setCellValue("非交易用户");
            }
            localHSSFCell = localHSSFRow.createCell((short)2);
            localHSSFCell.setEncoding((short)1);
            localHSSFCell.setCellValue(localResultSet.getString("name"));
            localHSSFCell = localHSSFRow.createCell((short)3);
            localHSSFCell.setEncoding((short)1);
            localHSSFCell.setCellValue(localResultSet.getString("province"));
            localHSSFCell = localHSSFRow.createCell((short)4);
            localHSSFCell.setEncoding((short)1);
            localHSSFCell.setCellValue(localResultSet.getString("address"));
            localHSSFCell = localHSSFRow.createCell((short)5);
            localHSSFCell.setEncoding((short)1);
            localHSSFCell.setCellValue(localResultSet.getString("postid"));
            localHSSFCell = localHSSFRow.createCell((short)6);
            localHSSFCell.setEncoding((short)1);
            localHSSFCell.setCellValue(localResultSet.getString("managername"));
            localHSSFCell = localHSSFRow.createCell((short)7);
            localHSSFCell.setEncoding((short)1);
            localHSSFCell.setCellValue(localResultSet.getString("manageremail"));
            localHSSFCell = localHSSFRow.createCell((short)8);
            localHSSFCell.setEncoding((short)1);
            localHSSFCell.setCellValue(localResultSet.getString("managerid"));
            localHSSFCell = localHSSFRow.createCell((short)9);
            localHSSFCell.setEncoding((short)1);
            localHSSFCell.setCellValue(localResultSet.getString("mgtele"));
            localHSSFCell = localHSSFRow.createCell((short)10);
            localHSSFCell.setEncoding((short)1);
            localHSSFCell.setCellValue(localResultSet.getString("mgfax"));
            localHSSFCell = localHSSFRow.createCell((short)11);
            localHSSFCell.setEncoding((short)1);
            localHSSFCell.setCellValue(localResultSet.getString("mgmobile"));
            localHSSFCell = localHSSFRow.createCell((short)12);
            localHSSFCell.setEncoding((short)1);
            localHSSFCell.setCellValue(localResultSet.getString("tradername"));
            localHSSFCell = localHSSFRow.createCell((short)13);
            localHSSFCell.setEncoding((short)1);
            localHSSFCell.setCellValue(localResultSet.getString("traderemail"));
            localHSSFCell = localHSSFRow.createCell((short)14);
            localHSSFCell.setEncoding((short)1);
            localHSSFCell.setCellValue(localResultSet.getString("traderid"));
            localHSSFCell = localHSSFRow.createCell((short)15);
            localHSSFCell.setEncoding((short)1);
            localHSSFCell.setCellValue(localResultSet.getString("tdtele"));
            localHSSFCell = localHSSFRow.createCell((short)16);
            localHSSFCell.setEncoding((short)1);
            localHSSFCell.setCellValue(localResultSet.getString("tdfax"));
            localHSSFCell = localHSSFRow.createCell((short)17);
            localHSSFCell.setEncoding((short)1);
            localHSSFCell.setCellValue(localResultSet.getString("tdmobile"));
            localHSSFCell = localHSSFRow.createCell((short)18);
            localHSSFCell.setEncoding((short)1);
            localHSSFCell.setCellValue(localResultSet.getString("enterpriseid"));
            localHSSFCell = localHSSFRow.createCell((short)19);
            localHSSFCell.setEncoding((short)1);
            localHSSFCell.setCellValue(localResultSet.getString("validperiod").replace(';', 33267));
            localHSSFCell = localHSSFRow.createCell((short)20);
            localHSSFCell.setEncoding((short)1);
            localHSSFCell.setCellValue(localResultSet.getString("bank"));
            localHSSFCell = localHSSFRow.createCell((short)21);
            localHSSFCell.setEncoding((short)1);
            localHSSFCell.setCellValue(localResultSet.getString("account"));
            localHSSFCell = localHSSFRow.createCell((short)22);
            localHSSFCell.setEncoding((short)1);
            localHSSFCell.setCellValue(localResultSet.getString("userlevel"));
            localHSSFCell = localHSSFRow.createCell((short)23);
            localHSSFCell.setEncoding((short)1);
            localHSSFCell.setCellValue(localResultSet.getString("createtime").substring(0, 10));
            localHSSFCell = localHSSFRow.createCell((short)24);
            localHSSFCell.setEncoding((short)1);
            localHSSFCell.setCellValue(localResultSet.getString("modifytime").substring(0, 10));
            localHSSFCell = localHSSFRow.createCell((short)25);
            localHSSFCell.setEncoding((short)1);
            localHSSFCell.setCellValue(localResultSet.getString("market"));
          }
        }
        if (localResultSet != null) {
          localResultSet.close();
        }
        if (localPreparedStatement != null) {
          localPreparedStatement.close();
        }
        localPreparedStatement = null;
        localResultSet = null;
      }
      catch (Exception localException4)
      {
        localException4.printStackTrace();
      }
      finally
      {
        if (localPreparedStatement != null)
        {
          try
          {
            localPreparedStatement.close();
          }
          catch (Exception localException7) {}
          localPreparedStatement = null;
        }
        try
        {
          if (localConnection != null) {
            localConnection.close();
          }
          if (localResultSet != null) {
            localResultSet.close();
          }
        }
        catch (Exception localException8) {}
        localConnection = null;
      }
      FileOutputStream localFileOutputStream = new FileOutputStream("d:\\workbook.xls");
      localHSSFWorkbook.write(localFileOutputStream);
      localFileOutputStream.close();
      Object localObject1 = new FileInputStream(new File("d:\\workbook.xls"));
      Object localObject2 = new byte[10240000];
      ((FileInputStream)localObject1).read((byte[])localObject2);
      Object localObject3 = paramHttpServletResponse.getOutputStream();
      paramHttpServletResponse.setHeader("Content-Type", "application/vnd.ms-excel");
      paramHttpServletResponse.setHeader("Content-Disposition", "Attachment;filename=data.xls");
      Object localObject4 = null;
      localObject4 = new BufferedOutputStream((OutputStream)localObject3);
      ((BufferedOutputStream)localObject4).write((byte[])localObject2);
      ((BufferedOutputStream)localObject4).flush();
      ((BufferedOutputStream)localObject4).close();
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
    }
  }
}
