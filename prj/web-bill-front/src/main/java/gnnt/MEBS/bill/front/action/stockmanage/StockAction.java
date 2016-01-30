package gnnt.MEBS.bill.front.action.stockmanage;

import gnnt.MEBS.bill.core.bo.StockOutApplyBO;
import gnnt.MEBS.bill.core.service.IKernelService;
import gnnt.MEBS.bill.core.vo.ResultVO;
import gnnt.MEBS.bill.front.model.commoditymanage.Breed;
import gnnt.MEBS.bill.front.model.commoditymanage.Category;
import gnnt.MEBS.bill.front.model.commoditymanage.PropertyType;
import gnnt.MEBS.bill.front.model.stockmanage.BiInvoiceinform;
import gnnt.MEBS.bill.front.model.stockmanage.BusinessRelationship;
import gnnt.MEBS.bill.front.model.stockmanage.Dismantle;
import gnnt.MEBS.bill.front.model.stockmanage.FrozenStock;
import gnnt.MEBS.bill.front.model.stockmanage.Invoiceinform;
import gnnt.MEBS.bill.front.model.stockmanage.PledgeStock;
import gnnt.MEBS.bill.front.model.stockmanage.Stock;
import gnnt.MEBS.bill.front.model.stockmanage.StockGoodsProperty;
import gnnt.MEBS.bill.front.model.stockmanage.TradeStock;
import gnnt.MEBS.bill.front.model.stockmanage.VOutStock;
import gnnt.MEBS.bill.front.service.StockConfirmService;
import gnnt.MEBS.common.front.action.StandardAction;
import gnnt.MEBS.common.front.common.Page;
import gnnt.MEBS.common.front.common.PageRequest;
import gnnt.MEBS.common.front.common.QueryConditions;
import gnnt.MEBS.common.front.dao.StandardDao;
import gnnt.MEBS.common.front.model.StandardModel;
import gnnt.MEBS.common.front.model.integrated.MFirm;
import gnnt.MEBS.common.front.model.integrated.User;
import gnnt.MEBS.common.front.service.AbstractService;
import gnnt.MEBS.common.front.service.StandardService;
import gnnt.MEBS.common.front.statictools.ActionUtil;
import gnnt.MEBS.common.front.statictools.Tools;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Controller("stockAction")
@Scope("request")
public class StockAction
  extends StandardAction
{
  private static final long serialVersionUID = 8998786414629350012L;
  @Autowired
  @Qualifier("billKernelService")
  private IKernelService kernelService;
  @Autowired
  @Qualifier("stockConfirmService")
  private StockConfirmService stockConfirmService;
  private String oprationId;
  private String stockStatus;
  private String received;
  
  public IKernelService getKernelService()
  {
    return this.kernelService;
  }
  
  public String getOprationId()
  {
    return this.oprationId;
  }
  
  public void setOprationId(String oprationId)
  {
    this.oprationId = oprationId;
  }
  
  public String getNotUseStock()
  {
    this.logger.debug("获取当前用户没有使用的仓单信息");
    
    User user = (User)this.request.getSession().getAttribute(
      "CurrentUser");
    
    String sql = "from Stock s where mFirm.firmID='" + 
      user.getBelongtoFirm().getFirmID() + 
      "' and status=1 and 0=(select count(c.operationID) from s.operations c)";
    Page<StandardModel> page = null;
    try
    {
      page = getService().getDao().queryByHQL(sql, null, 
        ActionUtil.getPageRequest(this.request), null);
      if ((page.getResult() != null) && (page.getResult().size() > 0)) {
        for (StandardModel model : page.getResult())
        {
          Stock stock = (Stock)model;
          PageRequest<String> pageRequest = new PageRequest(
            " and primary.categoryId=" + 
            stock.getBreed()
            .getCategory()
            .getCategoryId());
          getService().getPage(pageRequest, new Category());
        }
      }
    }
    catch (Exception e)
    {
      addReturnValue(-1, -10006L, new Object[] { "未使用仓单信息获取" });
      this.logger.error(Tools.getExceptionTrace(e));
    }
    this.request.setAttribute("pageInfo", page);
    
    return "success";
  }
  
  public void setStockStatus(String stockStatus)
  {
    this.stockStatus = stockStatus;
  }
  
  public String stockList()
  {
    this.logger.debug("仓单列表！！！" + this.stockStatus);
    try
    {
      if ((this.stockStatus != null) && (this.stockStatus.length() > 0))
      {
        User user = (User)this.request.getSession().getAttribute(
          "CurrentUser");
        this.logger.debug("enter ====:" + user.getBelongtoFirm().getFirmID());
        
        PageRequest<QueryConditions> pageRequest = 
          ActionUtil.getPageRequest(this.request);
        QueryConditions qc = (QueryConditions)pageRequest.getFilters();
        qc.addCondition("mFirm.firmID", "=", String.valueOf(user.getBelongtoFirm().getFirmID()));
        qc.addCondition("stockStatus", "=", Integer.valueOf(this.stockStatus));
        if ("".equals(pageRequest.getSortColumns())) {
          pageRequest.setSortColumns(" order by  createTime desc,to_number(stockId)  desc");
        }
        Page<StandardModel> page = super.getAbstractService(pageRequest).getPage(
          pageRequest, new Stock());
        
        this.request.setAttribute("stockStatus", this.stockStatus);
        this.request.setAttribute("pageInfo", page);
        this.request.setAttribute("oldParams", 
          ActionUtil.getParametersStartingWith(this.request, 
          "gnnt_"));
        if ((this.stockStatus.trim().equals("0")) || (this.stockStatus.trim().equals("2")) || (this.stockStatus.trim().equals("5")))
        {
          int HaveWarehouse = Tools.strToInt(getKernelService().getConfigInfo("HaveWarehouse"), 0);
          this.request.setAttribute("haveWarehouse", Integer.valueOf(HaveWarehouse));
          this.logger.debug("有无仓库系统(0:默认没有仓库1:有仓库)===：" + HaveWarehouse);
        }
      }
    }
    catch (Exception e)
    {
      this.logger.error(Tools.getExceptionTrace(e));
    }
    return "success";
  }
  
  public String stockListNow()
  {
    this.logger.debug("仓单列表！！！" + this.stockStatus);
    try
    {
      if ((this.stockStatus != null) && (this.stockStatus.length() > 0))
      {
        User user = (User)this.request.getSession().getAttribute(
          "CurrentUser");
        PageRequest<QueryConditions> pageRequest = 
          ActionUtil.getPageRequest(this.request);
        QueryConditions qc = (QueryConditions)pageRequest.getFilters();
        qc.addCondition("ownerfirm", "=", user.getBelongtoFirm().getFirmID());
        Page<StandardModel> page = super.getAbstractService(pageRequest).getPage(pageRequest, new VOutStock());
        
        this.request.setAttribute("stockStatus", this.stockStatus);
        this.request.setAttribute("pageInfo", page);
        this.request.setAttribute("oldParams", 
          ActionUtil.getParametersStartingWith(this.request, 
          "gnnt_"));
      }
      return "success";
    }
    catch (Exception e)
    {
      this.logger.error(Tools.getExceptionTrace(e));
    }
    return "error";
  }
  
  public String registerStock()
  {
    String stockId = this.request.getParameter("stockId");
    try
    {
      this.logger.debug("注册仓单！！！" + stockId);
      if ((stockId != null) && (stockId.length() > 0))
      {
        ResultVO result = this.kernelService.registerStock(stockId);
        if (result.getResult() == 1L)
        {
          addReturnValue(1, 130102L, new Object[] { stockId });
          
          writeOperateLog(1310, "注册仓单" + stockId, 1, null);
        }
        else
        {
          addReturnValue(-1, 9930092L, new Object[] { result.getErrorInfo().replaceAll("\n", "") });
          
          writeOperateLog(1310, "注册仓单" + stockId, 0, result.getErrorInfo().replaceAll("\n", ""));
        }
      }
      return "success";
    }
    catch (Exception e)
    {
      addReturnValue(-1, -90001L, new Object[] { "注册仓单操作" });
      
      writeOperateLog(1310, "注册仓单" + stockId, 0, e.getMessage());
      this.logger.error(Tools.getExceptionTrace(e));
    }
    return "error";
  }
  
  public String dismantleStock()
  {
    this.logger.debug("仓单拆单！！！");
    String stockIdStr = this.request.getParameter("stockId");
    
    String[] amountArrStr = this.request.getParameterValues("amount");
    if ((stockIdStr != null) && (amountArrStr != null))
    {
      Double[] amountArr = new Double[amountArrStr.length];
      for (int i = 0; i < amountArrStr.length; i++)
      {
        this.logger.debug("amountArr::" + amountArr);
        amountArr[i] = Double.valueOf(amountArrStr[i]);
      }
      try
      {
        ResultVO result = this.kernelService.dismantleStock(stockIdStr, amountArr);
        this.logger.debug("进了没！！" + result);
        if (result.getResult() == 1L)
        {
          this.logger.debug("拆单成功！！！");
          addReturnValue(1, 130103L, new Object[] { stockIdStr });
          
          writeOperateLog(1310, "仓单号为：" + stockIdStr + "的仓单拆单申请", 1, null);
        }
        else
        {
          this.logger.debug("拆单失败！！" + result.getErrorCode());
          addReturnValue(-1, -130103L, new Object[] {result
            .getErrorCode() });
          
          writeOperateLog(1310, "仓单号为：" + stockIdStr + "的仓单拆单申请", 0, result.getErrorInfo().replaceAll("\n", ""));
        }
        return "success";
      }
      catch (Exception e)
      {
        addReturnValue(-1, -90001L, new Object[] { "拆仓单操作" });
        
        writeOperateLog(1310, "仓单号为：" + stockIdStr + "的仓单拆单申请", 0, e.getMessage());
        this.logger.error(Tools.getExceptionTrace(e));
        return "SysException";
      }
    }
    this.logger.debug(((stockIdStr != null) && (amountArrStr != null)) + 
      "==================");
    return "SysException";
  }
  
  public String forwardStockOut()
    throws Exception
  {
    this.logger.debug("enter forwardStockOut");
    viewById();
    
    int HaveWarehouse = Tools.strToInt(getKernelService().getConfigInfo(
      "HaveWarehouse"), 0);
    this.request.setAttribute("HaveWarehouse", Integer.valueOf(HaveWarehouse));
    return "success";
  }
  
  public String exitStock()
  {
    String stockId = this.request.getParameter("stockId");
    
    int HaveWarehouse = Tools.strToInt(getKernelService().getConfigInfo("HaveWarehouse"), 0);
    try
    {
      if (HaveWarehouse == 1) {
        stockOut();
      } else {
        stockOutApply();
      }
      return "success";
    }
    catch (Exception e)
    {
      addReturnValue(-1, -10006L, new Object[] { "仓单出库操作" });
      
      writeOperateLog(1310, "仓单号为：" + stockId + "的仓单出库", 0, e.getMessage());
      this.logger.error(Tools.getExceptionTrace(e));
    }
    return "error";
  }
  
  public void stockOut()
  {
    String stockId = this.request.getParameter("stockId");
    
    String userId = this.request.getParameter("userId");
    String userName = this.request.getParameter("userName");
    String password = this.request.getParameter("password");
    this.logger.debug("有仓库版仓单出库===仓单号：" + stockId + "编号：" + userId + 
      "账户号：" + userName + "验证码：" + password);
    if ((stockId != null) && (stockId.length() > 0) && (userId != null) && 
      (userId.length() > 0) && (userName != null) && 
      (userName.length() > 0) && (password != null) && 
      (password.length() > 0))
    {
      ResultVO result = this.kernelService.stockOut(stockId, userId, 
        userName, password);
      this.logger.debug("=============== key:" + result.getResult());
      if (result.getResult() > 0L)
      {
        addReturnValue(1, 130104L, new Object[] { stockId });
        
        writeOperateLog(1310, "仓单号为：" + 
          stockId + "的仓单出库", 1, null);
      }
      else
      {
        addReturnValue(-1, 9930092L, new Object[] { result.getErrorInfo().replace("\n", "") });
        
        writeOperateLog(1310, "仓单号为：" + stockId + "的仓单出库", 0, result.getErrorInfo().replace("\n", ""));
      }
      this.logger.debug("=============== key:::" + 
        result.getErrorCode() + ",Map::" + 
        result.getInfoMap());
    }
  }
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=false)
  public void stockOutApply()
  {
    String stockId = this.request.getParameter("stockId");
    this.logger.debug("无仓库版仓单出库申请===仓单号：" + stockId);
    if ((stockId != null) && (stockId.length() > 0))
    {
      String deliveryPerson = this.request.getParameter("deliveryPerson");
      
      String idnumber = this.request.getParameter("idnumber");
      
      String address = this.request.getParameter("address");
      String phone = this.request.getParameter("phone");
      String deliveryStatus = this.request.getParameter("deliveryStatus");
      
      BiInvoiceinform invoice = getInvoiceInfo();
      String errorinfo = "";
      if (deliveryStatus.equals("0")) {
        errorinfo = errorinfo + validateId(idnumber);
      }
      errorinfo = errorinfo + validateInvoice(invoice);
      if (!errorinfo.equals(""))
      {
        addReturnValue(-1, 999999L, new Object[] { errorinfo });
        return;
      }
      StockOutApplyBO stockOutApplyBO = new StockOutApplyBO();
      if (deliveryStatus.equals("0"))
      {
        stockOutApplyBO.setStockID(stockId);
        stockOutApplyBO.setIdnumber(idnumber);
        stockOutApplyBO.setDeliveryPerson(deliveryPerson);
        stockOutApplyBO.setAddress(null);
        stockOutApplyBO.setPhone(null);
        stockOutApplyBO.setDeliveryStatus(deliveryStatus);
      }
      else
      {
        stockOutApplyBO.setStockID(stockId);
        stockOutApplyBO.setIdnumber(null);
        stockOutApplyBO.setDeliveryPerson(deliveryPerson);
        stockOutApplyBO.setAddress(address);
        stockOutApplyBO.setPhone(phone);
        stockOutApplyBO.setDeliveryStatus(deliveryStatus);
      }
      ResultVO result = this.kernelService.stockOutApply(stockOutApplyBO);
      if ((result.getResult() != -1L) && (result.getResult() != 0L))
      {
        saveInvoiceInfo(invoice, stockId);
        
        addReturnValue(1, 131104L, new Object[] { stockId, 
          Long.valueOf(result.getResult()) });
      }
      else
      {
        addReturnValue(-1, 9930092L, new Object[] { result.getErrorInfo().replace("\n", "") });
      }
      this.logger.debug("=============== key:::" + result.getErrorCode() + ",Map::" + result.getInfoMap());
    }
  }
  
  private String validateId(String idnumber)
  {
    String errorinfo = "";
    
    String regx = "([0-9]{17}([0-9]|X|x))|([0-9]{15})";
    Pattern pattern = Pattern.compile(regx);
    if (!pattern.matcher(idnumber).matches()) {
      errorinfo = errorinfo + " 身份证号码不合法！";
    }
    return errorinfo;
  }
  
  private String validateInvoice(BiInvoiceinform invoice)
  {
    if (invoice == null) {
      return "";
    }
    String errorinfo = "";
    if ((invoice.getName() == null) || (invoice.getName() == ""))
    {
      String regx = "(1[3|4|5|7|8][0-9]{9})|([0-9]{3,4}-[0-9]{7,8})";
      Pattern pattern = Pattern.compile(regx);
      if (!pattern.matcher(invoice.getPhone()).matches()) {
        errorinfo = errorinfo + " 注册号码不合法！";
      }
    }
    else
    {
      String regx = "(1[3|4|5|7|8][0-9]{9})";
      Pattern pattern = Pattern.compile(regx);
      if (!pattern.matcher(invoice.getPhone()).matches()) {
        errorinfo = errorinfo + " 手机号码不合法！";
      }
    }
    return errorinfo;
  }
  
  private BiInvoiceinform getInvoiceInfo()
  {
    BiInvoiceinform invoice = null;
    String invoiceStatusStr = this.request.getParameter("invoiceStatus");
    int invoiceStatus = Integer.valueOf(invoiceStatusStr).intValue();
    if (invoiceStatus != 0) {
      if (invoiceStatus == 1)
      {
        invoice = new BiInvoiceinform();
        invoice.setName(this.request.getParameter("invoicePerson").trim());
        invoice.setAddress(this.request.getParameter("invoicePersonAddress").trim());
        invoice.setPhone(this.request.getParameter("invoicePersonPhone").trim());
        invoice.setInvoicetype("0");
      }
      else if (invoiceStatus == 2)
      {
        invoice = new BiInvoiceinform();
        invoice.setCompanyname(this.request.getParameter("invoiceCompanyName").trim());
        invoice.setAddress(this.request.getParameter("invoiceCompanyAddress").trim());
        invoice.setBank(this.request.getParameter("invoiceCompanyBank").trim());
        invoice.setBankaccount(this.request.getParameter("invoiceCompanyBankAccount").trim());
        invoice.setDutyparagraph(this.request.getParameter("invoiceDutyParagraph").trim());
        invoice.setPhone(this.request.getParameter("invoiceCompanyPhone").trim());
        invoice.setInvoicetype("1");
      }
    }
    return invoice;
  }
  
  private void saveInvoiceInfo(BiInvoiceinform invoice, String stockid)
  {
    if (invoice != null)
    {
      invoice.setStockid(stockid);
      getService().add(invoice);
    }
  }
  
  public String stockRegisterList()
  {
    this.logger.debug("注册仓单管理！！！");
    try
    {
      String operation = this.request.getParameter("operation");
      System.out.println("==========: " + operation);
      String stockId = this.request.getParameter("stockIdPra");
      System.out.println("=========:" + stockId);
      if (operation == null) {
        operation = "";
      }
      User user = (User)this.request.getSession().getAttribute(
        "CurrentUser");
      
      Page<StandardModel> page = null;
      String sql = null;
      if (!"".equals(operation))
      {
        if (operation.equals("1")) {
          sql = 
          
            "from  FinancingStock s where  status='Y' and stock.mFirm.firmID='" + user.getBelongtoFirm().getFirmID() + "'";
        } else if (operation.equals("2")) {
          sql = 
          
            "from  PledgeStock s where  status=0 and stock.mFirm.firmID='" + user.getBelongtoFirm().getFirmID() + "'";
        } else if (operation.equals("3")) {
          sql = 
          
            "from  TradeStock s where  status=0 and stock.mFirm.firmID='" + user.getBelongtoFirm().getFirmID() + "'";
        } else if (operation.equals("4")) {
          sql = 
          
            "from  FrozenStock s where  status=0 and stock.mFirm.firmID='" + user.getBelongtoFirm().getFirmID() + "'";
        }
        if ((stockId != null) && (stockId.length() > 0)) {
          sql = sql + " and stock.stockId='" + stockId + "'";
        }
        PageRequest<QueryConditions> pageRequest = 
          ActionUtil.getPageRequest(this.request);
        
        String sortColums = pageRequest.getSortColumns();
        System.out.println("排序条件=======" + sortColums);
        if (sortColums != null) {
          if (sortColums.indexOf("stockId") > 0) {
            pageRequest.setSortColumns(sortColums.replace("stockId", 
              "stock.stockId"));
          } else if (sortColums.indexOf("breedName") > 0) {
            pageRequest.setSortColumns(sortColums.replace("breed.breedName", "stock.breed.breedName"));
          }
        }
        page = getService().getDao().queryByHQL(sql, null, pageRequest, 
          null);
      }
      else
      {
        sql = 
        
          "from Stock s where ownerFirm='" + user.getBelongtoFirm().getFirmID() + "' and stockStatus=1 and 0=(select count(c.operationId) from s.operations c)";
        if ((stockId != null) && (stockId.length() > 0)) {
          sql = sql + " and stockId='" + stockId + "'";
        }
        this.logger.debug("SortColumns=====" + 
          ActionUtil.getPageRequest(this.request).getSortColumns());
        
        page = getService().getDao().queryByHQL(sql, null, 
          ActionUtil.getPageRequest(this.request), null);
      }
      String sqlForQuery = "select t.moduleid,t.cnname from C_TRADEMODULE t";
      this.logger.debug("=====系统模块SQL:=========：" + sqlForQuery);
      List<Map<Object, Object>> moduleSysList = getService().getDao().queryBySql(sqlForQuery);
      this.request.setAttribute("moduleSysList", moduleSysList);
      
      this.request.setAttribute("operation", operation);
      
      this.request.setAttribute("pageInfo", page);
      
      this.request.setAttribute("stockId", stockId);
      
      this.logger.debug("======" + page.getResult().size() + ":operation:" + 
        operation);
    }
    catch (Exception e)
    {
      addReturnValue(-1, -10006L, new Object[] { "仓单信息查询" });
      this.logger.error(Tools.getExceptionTrace(e));
    }
    return "success";
  }
  
  public String logoutStock()
  {
    String stockId = this.request.getParameter("stockId");
    this.logger.debug("注销仓单！::" + stockId);
    try
    {
      if ((stockId != null) && (stockId.length() > 0))
      {
        ResultVO result = this.kernelService.logoutStock(stockId);
        if (result.getResult() == 1L)
        {
          addReturnValue(1, 130109L, new Object[] { stockId });
          
          writeOperateLog(1310, "仓单号为：" + stockId + "的仓单注销", 1, null);
        }
        else
        {
          addReturnValue(-1, -130109L, new Object[] { result.getErrorInfo().replaceAll("\n", "") });
          
          writeOperateLog(1310, "仓单号为：" + stockId + "的仓单注销", 0, result
            .getErrorInfo());
        }
      }
      return "success";
    }
    catch (Exception e)
    {
      addReturnValue(-1, -10006L, new Object[] { "仓单注销" });
      
      writeOperateLog(1310, "仓单号为：" + stockId + "的仓单注销", 0, e.getMessage());
      this.logger.error(Tools.getExceptionTrace(e));
    }
    return "error";
  }
  
  public String stockSplitList()
  {
    try
    {
      String status = this.request.getParameter("status");
      if (status == null) {
        status = "1";
      }
      User user = (User)this.request.getSession().getAttribute(
        "CurrentUser");
      PageRequest<QueryConditions> pageRequest = 
        ActionUtil.getPageRequest(this.request);
      
      QueryConditions qc = (QueryConditions)pageRequest.getFilters();
      qc.addCondition("stock.mFirm.firmID", "=", user
        .getBelongtoFirm().getFirmID());
      qc.addCondition("status", "=", status);
      Page<StandardModel> page = super.getService().getPage(pageRequest, 
        new Dismantle());
      
      this.request.setAttribute("pageInfo", page);
      this.request.setAttribute("oldParams", 
        ActionUtil.getParametersStartingWith(this.request, 
        "gnnt_"));
      
      this.request.setAttribute("status", status);
      
      return "success";
    }
    catch (Exception e)
    {
      addReturnValue(-1, -10006L, new Object[] { "待拆仓单查询" });
      this.logger.error(Tools.getExceptionTrace(e));
    }
    return "SysException";
  }
  
  public String stockDetail()
  {
    String stockId = this.request.getParameter("entity.stockId");
    Stock stock = new Stock();
    try
    {
      super.viewById();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    Object object;
    if (this.oprationId == null)
    {
      stock = (Stock)this.entity;
    }
    else
    {
      String sqlForQuery = "select t.moduleid,t.cnname from C_TRADEMODULE t";
      this.logger.debug("=====系统模块SQL:=========：" + sqlForQuery);
      List<Map<Object, Object>> moduleSysList = getService().getDao().queryBySql(sqlForQuery);
      this.request.setAttribute("moduleSysList", moduleSysList);
      
      this.request.setAttribute("opration", this.oprationId);
      if (this.oprationId.equals("2"))
      {
        String pledgeId = this.request.getParameter("pledgestock");
        PledgeStock ps = new PledgeStock();
        ps.setPledgestock(Long.valueOf(Tools.strToLong(pledgeId)));
        ps = (PledgeStock)getService().get(ps);
        this.request.setAttribute("moduleId", ps.getModuleId());
        this.request.setAttribute("orderId", ps.getOrderId());
      }
      else if (this.oprationId.equals("3"))
      {
        String tradestock = this.request.getParameter("tradestock");
        TradeStock ps = new TradeStock();
        ps.setTradeStockId(Long.valueOf(Tools.strToLong(tradestock)));
        ps = (TradeStock)getService().get(ps);
        this.request.setAttribute("moduleId", ps.getModuleId());
        this.request.setAttribute("tradeNo", ps.getTradeNo());
      }
      else if (this.oprationId.equals("4"))
      {
        String frozenstock = this.request.getParameter("frozenstock");
        FrozenStock ps = new FrozenStock();
        ps.setFrozenStockId(Long.valueOf(Tools.strToLong(frozenstock)));
        ps = (FrozenStock)getService().get(ps);
        this.request.setAttribute("moduleId", ps.getModuleId());
      }
      else if (this.oprationId.equals("5"))
      {
        String status = this.request.getParameter("status");
        String outStatus = "";
        if ((status != null) && (status.length() > 0))
        {
          if (status.equals("2")) {
            outStatus = "2";
          } else if (status.equals("5")) {
            outStatus = "0";
          }
          String sql = "select * from BI_OUTSTOCK t where t.stockid = '" + stockId + "' and t.status = " + outStatus;
          List<Map<Object, Object>> list = getService().getListBySql(sql);
          if (list.size() == 1)
          {
            object = list.get(0);
            this.request.setAttribute("outStock", object);
          }
        }
      }
      if ((stockId != null) && (stockId.trim().length() > 0))
      {
        stock.setStockId(stockId);
        stock = (Stock)getService().get(stock);
      }
    }
    if ((stock != null) && (stock.getGoodsProperties() != null) && (stock.getGoodsProperties().size() > 0))
    {
      Map<PropertyType, List<StockGoodsProperty>> map = new LinkedHashMap();
      
      QueryConditions ptqc = new QueryConditions();
      ptqc.addCondition("status", "=", Integer.valueOf(0));
      PageRequest<QueryConditions> ptpageRequest = new PageRequest(1, 100, ptqc, " order by sortNo ");
      Page<StandardModel> ptpage = getService().getPage(ptpageRequest, new PropertyType());
      if ((ptpage != null) && (ptpage.getResult() != null)) {
        for (int i = 0; i < ptpage.getResult().size(); i++)
        {
          PropertyType pt = (PropertyType)ptpage.getResult().get(i);
          map.put(pt, new ArrayList());
        }
      }
      List<StockGoodsProperty> other = new ArrayList();
      List<StockGoodsProperty> list;
      for (object = stock.getGoodsProperties().iterator(); ((Iterator)object).hasNext();)
      {
        StockGoodsProperty sgp = (StockGoodsProperty)((Iterator)object).next();
        list = null;
        for (PropertyType key : map.keySet()) {
          if ((sgp.getPropertyTypeID() != null) && (sgp.getPropertyTypeID().equals(key.getPropertyTypeID()))) {
            list = (List)map.get(key);
          }
        }
        if (list == null) {
          list = other;
        }
        list.add(sgp);
      }
      Map<PropertyType, List<StockGoodsProperty>> tpmap = new LinkedHashMap();
      for (PropertyType key : map.keySet()) {
        if (((List)map.get(key)).size() > 0) {
          tpmap.put(key, (List)map.get(key));
        }
      }
      if (other.size() > 0)
      {
        PropertyType pt = new PropertyType();
        pt.setName("其它属性");
        pt.setPropertyTypeID(Long.valueOf(-1L));
        tpmap.put(pt, other);
      }
      this.request.setAttribute("tpmap", tpmap);
    }
    String backUrl = this.request.getParameter("backUrl");
    if ((backUrl == null) || ("".equals(backUrl))) {
      backUrl = this.request.getHeader("referer");
    }
    this.logger.debug("================backUrl :" + backUrl);
    this.request.setAttribute("backUrl", backUrl);
    return "success";
  }
  
  public String stockOutCancel()
  {
    String stockId = this.request.getParameter("stockId");
    if ((stockId != null) && (stockId.length() > 0))
    {
      ResultVO result = this.kernelService.withdrowStockOutApply(stockId);
      if ((result.getResult() != -1L) && (result.getResult() != 0L))
      {
        if (result.getResult() > 0L)
        {
          BiInvoiceinform invoice = new BiInvoiceinform(stockId);
          List<Map<Object, Object>> list = getService().getListBySql("select * from BI_invoiceInform where stockid = " + stockId);
          if ((list != null) && (list.size() > 0)) {
            getService().delete(invoice);
          }
          addReturnValue(1, 130111L, new Object[] { stockId });
          
          writeOperateLog(1310, "仓单号为：" + 
            stockId + "的仓单出库申请撤销", 1, null);
        }
        else
        {
          addReturnValue(-1, 999999L, new Object[] {result
            .getErrorInfo().replace("\n", "") });
          
          writeOperateLog(1310, "仓单号为：" + 
            stockId + "的仓单出库申请撤销", 0, result.getErrorInfo()
            .replace("\n", ""));
        }
        this.logger.debug("=============== key:::" + 
          result.getErrorCode() + ",Map::" + 
          result.getInfoMap());
      }
    }
    return "success";
  }
  
  public void setReceived(String received)
  {
    this.received = received;
  }
  
  public String stockBySeller()
  {
    try
    {
      User user = (User)this.request.getSession().getAttribute("CurrentUser");
      if (user == null) {
        return "error";
      }
      PageRequest<QueryConditions> pageRequest = ActionUtil.getPageRequest(this.request);
      QueryConditions qc = (QueryConditions)pageRequest.getFilters();
      
      qc.addCondition("seller", "=", user.getBelongtoFirm().getFirmID());
      Page<StandardModel> page = super.getService().getPage(pageRequest, new BusinessRelationship());
      this.request.setAttribute("reciveid", this.received);
      this.request.setAttribute("pageInfo", page);
      return "success";
    }
    catch (Exception e)
    {
      this.logger.debug(Tools.getExceptionTrace(e));
    }
    return "error";
  }
  
  public String showInvoice()
  {
    String stockid = this.request.getParameter("stockId");
    if ((stockid == null) || (stockid.equals(""))) {
      return "error";
    }
    try
    {
      PageRequest<QueryConditions> pageRequest = ActionUtil.getPageRequest(this.request);
      QueryConditions qc = (QueryConditions)pageRequest.getFilters();
      
      qc.addCondition("stockId", "=", stockid);
      Page<StandardModel> page = super.getService().getPage(pageRequest, new Invoiceinform());
      
      this.request.setAttribute("invoiceinform", page.getResult().get(0));
      return "success";
    }
    catch (Exception e)
    {
      this.logger.debug(Tools.getExceptionTrace(e));
    }
    return "error";
  }
  
  public String stockConfirm()
    throws Exception
  {
    this.logger.debug("enter stockConfirm 进入确认收货action!");
    User user = (User)this.request.getSession().getAttribute("CurrentUser");
    String stockId = this.request.getParameter("stockId");
    int result = this.stockConfirmService.stockConfirm(stockId, user);
    if (result == 1) {
      addReturnValue(1, 9910001L);
    } else if (result == -1) {
      addReturnValue(1, 9920001L, new Object[] { "操作失败" });
    } else if (result == 0) {
      addReturnValue(1, 9920001L, new Object[] { "没有查询到此仓单交收配对信息!" });
    }
    return "success";
  }
}
