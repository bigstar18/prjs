package gnnt.MEBS.monitor.action;

import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.monitor.model.QMemberVO;
import gnnt.MEBS.monitor.model.VCustomercloseSimple;
import gnnt.MEBS.monitor.model.VCustomercloseTip;
import gnnt.MEBS.monitor.model.VCustomerclosesumSimple;
import gnnt.MEBS.monitor.model.VCustomerclosesumTip;
import gnnt.MEBS.monitor.service.QMemberVOService;
import gnnt.MEBS.monitor.service.VCustomercloseService;
import gnnt.MEBS.monitor.service.VCustomercloseSimpleService;
import gnnt.MEBS.monitor.service.VCustomercloseTipService;
import gnnt.MEBS.monitor.service.VCustomerclosesumService;
import gnnt.MEBS.monitor.service.VCustomerclosesumSimpleService;
import gnnt.MEBS.monitor.service.VCustomerclosesumTipService;
import gnnt.MEBS.packaging.action.BaseAction;
import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.InService;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("customerCloseAction")
@Scope("request")
public class CustomerCloseAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(CustomerCloseAction.class);
  @Autowired
  @Qualifier("vCustomercloseService")
  protected VCustomercloseService vCustomercloseService;
  @Autowired
  @Qualifier("vCustomerclosesumService")
  protected VCustomerclosesumService vCustomerclosesumService;
  @Autowired
  @Qualifier("vCustomercloseSimpleService")
  protected VCustomercloseSimpleService vCustomercloseSimpleService;
  @Autowired
  @Qualifier("vCustomercloseTipService")
  protected VCustomercloseTipService vCustomercloseTipService;
  @Autowired
  @Qualifier("vCustomerclosesumSimpleService")
  protected VCustomerclosesumSimpleService vCustomerclosesumSimpleService;
  @Autowired
  @Qualifier("vCustomerclosesumTipService")
  protected VCustomerclosesumTipService vCustomerclosesumTipService;
  @Autowired
  @Qualifier("qMemberVOService")
  protected QMemberVOService qMemberVOService;
  private static Map<String, String> commodityMap = new HashMap();
  
  public InService getService()
  {
    return this.vCustomercloseService;
  }
  
  public String list()
  {
    this.request.setAttribute("test", "1000");
    return "list";
  }
  
  public String sum()
  {
    HttpServletResponse response = ServletActionContext.getResponse();
    HttpServletRequest request = ServletActionContext.getRequest();
    try
    {
      String mid = request.getParameter("mid");
      String cstid = request.getParameter("cstid");
      String prd = request.getParameter("prd");
      String dir = request.getParameter("dir");
      if (prd != null) {
        prd = URLDecoder.decode(prd, "UTF-8");
      }
      if (dir != null) {
        dir = URLDecoder.decode(dir, "UTF-8");
      }
      String closetype = request.getParameter("closetype");
      if (closetype != null) {
        closetype = URLDecoder.decode(closetype, "UTF-8");
      }
      List data = this.vCustomerclosesumService.getListAll(mid, cstid, prd, dir, closetype);
      

      response.setContentType("application/json; charset=UTF-8");
      response.setCharacterEncoding("UTF-8");
      PrintWriter out = response.getWriter();
      out.println(JSONArray.fromObject(data).toString());
      out.flush();
    }
    catch (IOException ex)
    {
      this.logger.error("", ex);
    }
    return null;
  }
  
  public String close()
  {
    HttpServletResponse response = ServletActionContext.getResponse();
    HttpServletRequest request = ServletActionContext.getRequest();
    try
    {
      String page = request.getParameter("page");
      String pagesize = request.getParameter("pagesize");
      String mid = request.getParameter("mid");
      String cstid = request.getParameter("cstid");
      String orderField = request.getParameter("order");
      String desc = request.getParameter("desc");
      String prd = request.getParameter("prd");
      String dir = request.getParameter("dir");
      if (prd != null) {
        prd = URLDecoder.decode(prd, "UTF-8");
      }
      if (dir != null) {
        dir = URLDecoder.decode(dir, "UTF-8");
      }
      String closetype = request.getParameter("closetype");
      if (closetype != null) {
        closetype = URLDecoder.decode(closetype, "UTF-8");
      }
      desc = Integer.parseInt(desc) == 1 ? "true" : "false";
      
      QueryConditions qc = new QueryConditions();
      
      Rights rts = new Rights();
      Rights.addsearch(qc, "memberno", mid);
      if ((cstid != null) && (cstid.trim().length() > 0) && (!cstid.equals("all"))) {
        qc.addCondition("customerno", " like ", "%" + cstid + "%");
      }
      if ((prd != "") && (prd != null) && (prd.trim().length() > 0)) {
        qc.addCondition("commodityname", " = ", prd);
      }
      if ((dir != "") && (dir != null) && (dir.trim().length() > 0)) {
        qc.addCondition("close_bs_flag", " = ", dir);
      }
      if ((closetype != "") && (closetype != null) && (closetype.trim().length() > 0)) {
        qc.addCondition("tradetype", " = ", closetype);
      }
      qc = Rights.addqc(qc);
      PageInfo pg = new PageInfo();
      pg.addOrderField(orderField, Boolean.valueOf(desc).booleanValue());
      pg.setPageSize(Integer.parseInt((pagesize != null) && (pagesize.trim().length() > 0) ? pagesize : "40"));
      pg.setPageNo(Integer.parseInt((page != null) && (page.trim().length() > 0) ? page : "1"));
      
      List data = this.vCustomercloseService.getList(qc, pg);
      
      Map map = new HashMap();
      map.put("pageInfo", pg);
      map.put("data", data);
      
      response.setContentType("application/json; charset=UTF-8");
      response.setCharacterEncoding("UTF-8");
      PrintWriter out = response.getWriter();
      out.println(JSONObject.fromObject(map).toString());
      out.flush();
    }
    catch (IOException ex)
    {
      this.logger.error("", ex);
    }
    return null;
  }
  
  public String sumSimple()
  {
    HttpServletResponse response = ServletActionContext.getResponse();
    HttpServletRequest request = ServletActionContext.getRequest();
    try
    {
      String prd = request.getParameter("prd");
      String mid = request.getParameter("mid");
      String cstid = request.getParameter("cstid");
      String dir = request.getParameter("dir");
      if (dir != null) {
        dir = URLDecoder.decode(dir, "UTF-8");
      }
      String closetype = request.getParameter("closetype");
      if (closetype != null) {
        closetype = URLDecoder.decode(closetype, "UTF-8");
      }
      List data = this.vCustomerclosesumSimpleService.getListAll(prd, mid, cstid, dir, closetype);
      if (data != null) {
        for (int i = 0; i < data.size(); i++)
        {
          VCustomerclosesumSimple vcs = (VCustomerclosesumSimple)data.get(i);
          if (commodityMap.get(vcs.getCommodityid()) == null) {
            initCommodity();
          }
          vcs.setCommodityname((String)commodityMap.get(vcs.getCommodityid()));
        }
      }
      response.setContentType("application/json; charset=UTF-8");
      response.setCharacterEncoding("UTF-8");
      PrintWriter out = response.getWriter();
      out.println(JSONArray.fromObject(data).toString());
      out.flush();
    }
    catch (IOException ex)
    {
      this.logger.error("", ex);
    }
    return null;
  }
  
  public String sumInfo()
  {
    HttpServletResponse response = ServletActionContext.getResponse();
    HttpServletRequest request = ServletActionContext.getRequest();
    try
    {
      Map<String, Object> data = new HashMap();
      
      String prd = request.getParameter("prd");
      String mid = request.getParameter("mid");
      String cstid = request.getParameter("cstid");
      String dir = request.getParameter("dir");
      if (dir != null) {
        dir = URLDecoder.decode(dir, "UTF-8");
      }
      String closetype = request.getParameter("closetype");
      if (closetype != null) {
        closetype = URLDecoder.decode(closetype, "UTF-8");
      }
      VCustomerclosesumTip tip = this.vCustomerclosesumTipService.getVCustomerclosesumTip(prd, mid, cstid, dir, closetype);
      if (tip != null)
      {
        data.put("commodityid", tip.getCommodityid());
        data.put("buyopenprice", fmtDouble(tip.getBuyopenprice().doubleValue()));
        data.put("buyholdprice", fmtDouble(tip.getBuyholdprice().doubleValue()));
        data.put("sellopenprice", fmtDouble(tip.getSellopenprice().doubleValue()));
        data.put("sellholdprice", fmtDouble(tip.getSellholdprice().doubleValue()));
      }
      String json = JSONObject.fromObject(data).toString();
      
      System.out.println(json);
      response.setContentType("application/json; charset=UTF-8");
      response.setCharacterEncoding("UTF-8");
      PrintWriter out = response.getWriter();
      out.println(json);
      out.flush();
    }
    catch (IOException e)
    {
      this.logger.error("查询客户平仓汇总附加信息IO异常", e);
    }
    return null;
  }
  
  public String closeSimple()
  {
    HttpServletResponse response = ServletActionContext.getResponse();
    HttpServletRequest request = ServletActionContext.getRequest();
    try
    {
      String page = request.getParameter("page");
      String pagesize = request.getParameter("pagesize");
      String mid = request.getParameter("mid");
      String cstid = request.getParameter("cstid");
      String orderField = request.getParameter("order");
      String desc = request.getParameter("desc");
      String prd = request.getParameter("prd");
      String dir = request.getParameter("dir");
      if (dir != null) {
        dir = URLDecoder.decode(dir, "UTF-8");
      }
      String closetype = request.getParameter("closetype");
      if (closetype != null) {
        closetype = URLDecoder.decode(closetype, "UTF-8");
      }
      desc = Integer.parseInt(desc) == 1 ? "true" : "false";
      
      QueryConditions qc = new QueryConditions();
      
      Rights.addsearch(qc, "memberno", mid);
      if ((cstid != null) && (cstid.trim().length() > 0) && (!cstid.equals("all"))) {
        qc.addCondition("customerno", " like ", "%" + cstid + "%");
      }
      if ((prd != "") && (prd != null) && (prd.trim().length() > 0)) {
        qc.addCondition("commodityid", " = ", prd);
      }
      if ((dir != "") && (dir != null) && (dir.trim().length() > 0)) {
        qc.addCondition("close_bs_flag", " = ", dir);
      }
      if ((closetype != "") && (closetype != null) && (closetype.trim().length() > 0)) {
        qc.addCondition("tradetype", " = ", closetype);
      }
      qc = Rights.addqc(qc);
      PageInfo pg = new PageInfo();
      pg.addOrderField(orderField, Boolean.valueOf(desc).booleanValue());
      pg.setPageSize(Integer.parseInt((pagesize != null) && (pagesize.trim().length() > 0) ? pagesize : "40"));
      pg.setPageNo(Integer.parseInt((page != null) && (page.trim().length() > 0) ? page : "1"));
      
      List data = this.vCustomercloseSimpleService.getList(qc, pg);
      if (data != null) {
        for (int i = 0; i < data.size(); i++)
        {
          VCustomercloseSimple vc = (VCustomercloseSimple)data.get(i);
          if (commodityMap.get(vc.getCommodityid()) == null) {
            initCommodity();
          }
          vc.setCommodityname((String)commodityMap.get(vc.getCommodityid()));
        }
      }
      Map map = new HashMap();
      map.put("pageInfo", pg);
      map.put("data", data);
      
      response.setContentType("application/json; charset=UTF-8");
      response.setCharacterEncoding("UTF-8");
      PrintWriter out = response.getWriter();
      out.println(JSONObject.fromObject(map).toString());
      out.flush();
    }
    catch (IOException ex)
    {
      this.logger.error("", ex);
    }
    return null;
  }
  
  public String closeInfo()
  {
    HttpServletResponse response = ServletActionContext.getResponse();
    HttpServletRequest request = ServletActionContext.getRequest();
    try
    {
      String tradeno = request.getParameter("tradeno");
      Map<String, Object> data = new HashMap();
      
      VCustomercloseTip tip = new VCustomercloseTip();
      tip.setTradeno(Long.valueOf(strToLong(tradeno, -1L)));
      tip = (VCustomercloseTip)this.vCustomercloseTipService.getDao().getById(tip.getId());
      if (tip != null)
      {
        data.put("tradeno", tip.getTradeno());
        data.put("opentradeno", tip.getOpentradeno());
        data.put("openBsFlag", tip.getOpen_bs_flag());
        data.put("openprice", fmtDouble(tip.getOpenprice().doubleValue()));
        data.put("holdprice", fmtDouble(tip.getHoldprice().doubleValue()));
        data.put("customername", tip.getCustomername());
        data.put("membername", tip.getMembername());
        data.put("organizationname", tip.getOrganizationname());
        data.put("managerno", tip.getManagerno());
      }
      String json = JSONObject.fromObject(data).toString();
      
      response.setContentType("application/json; charset=UTF-8");
      response.setCharacterEncoding("UTF-8");
      PrintWriter out = response.getWriter();
      out.println(json);
      out.flush();
    }
    catch (IOException e)
    {
      this.logger.error("查询平仓明细附加信息IO异常", e);
    }
    return null;
  }
  
  private void initCommodity()
  {
    List<QMemberVO> data = this.qMemberVOService.getProductList();
    if (data != null) {
      for (QMemberVO qm : data) {
        commodityMap.put(qm.getMemberno(), qm.getName());
      }
    }
  }
  
  private String fmtDouble(double num)
  {
    String result = "0.00";
    try
    {
      DecimalFormat nf = (DecimalFormat)NumberFormat.getNumberInstance();
      nf.applyPattern("###0.00");
      result = nf.format(num);
    }
    catch (Exception localException) {}
    return result;
  }
  
  private long strToLong(String str, long defaultV)
  {
    long result = defaultV;
    try
    {
      if ((str != null) && (str.length() != 0))
      {
        if ((str.endsWith("L")) || (str.endsWith("l"))) {
          str = str.substring(0, str.length() - 1);
        }
        result = Long.parseLong(str);
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return result;
  }
}
