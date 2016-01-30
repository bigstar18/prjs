package gnnt.MEBS.integrated.mgr.action.usermanage;

import gnnt.MEBS.common.mgr.action.EcsideAction;
import gnnt.MEBS.common.mgr.common.PageRequest;
import gnnt.MEBS.common.mgr.common.QueryConditions;
import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.service.StandardService;
import gnnt.MEBS.common.mgr.statictools.Tools;
import gnnt.MEBS.integrated.mgr.model.CertificateType;
import gnnt.MEBS.integrated.mgr.model.FirmCategory;
import gnnt.MEBS.integrated.mgr.model.Industry;
import gnnt.MEBS.integrated.mgr.model.Zone;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("mfirmAttributeAction")
@Scope("request")
public class MfirmAttributeAction
  extends EcsideAction
{
  private static final long serialVersionUID = 1L;
  @Resource(name="isvisibalMap")
  protected Map<String, String> isvisibalMap;
  
  public Map<String, String> getIsvisibalMap()
  {
    return this.isvisibalMap;
  }
  
  public String firmAttrList()
    throws Exception
  {
    this.logger.debug("enter firmAttrList");
    PageRequest localPageRequest = getPageRequest(this.request);
    QueryConditions localQueryConditions = (QueryConditions)localPageRequest.getFilters();
    localQueryConditions.addCondition("isvisibal", "<>", "D");
    localPageRequest.setSortColumns("order by sortNo desc");
    listByLimit(localPageRequest);
    return "success";
  }
  
  public String updateZoneIsvisibal()
  {
    this.logger.debug("enter updateZoneIsvisibal");
    String str1 = (String)this.entity.fetchPKey().getValue();
    Zone localZone = new Zone();
    localZone.setCode(str1);
    localZone = (Zone)getService().get(localZone);
    String str2 = "";
    String str3 = "";
    try
    {
      if (localZone.getIsvisibal().equalsIgnoreCase("Y"))
      {
        localZone.setIsvisibal("N");
        getService().update(localZone);
        str2 = "可选择";
        str3 = "不可选择";
      }
      else
      {
        localZone.setIsvisibal("Y");
        getService().update(localZone);
        str2 = "不可选择";
        str3 = "可选择";
      }
      addReturnValue(1, 119902L);
      writeOperateLog(1021, "修改地域：" + str1 + "是否可选择操作成功：由【" + str2 + "】修改为【" + str3 + "】", 1, "");
    }
    catch (Exception localException)
    {
      writeOperateLog(1021, "修改地域：" + str1 + "是否可选择操作失败", 0, localException.getMessage());
    }
    return "success";
  }
  
  public String deleteZone()
  {
    this.logger.debug("enter deleteZone");
    String str = (String)this.entity.fetchPKey().getValue();
    try
    {
      Zone localZone = new Zone();
      localZone.setCode(str);
      localZone = (Zone)getService().get(localZone);
      getService().delete(localZone);
      addReturnValue(1, 101003L, new Object[] { "地域编号：" + str });
      writeOperateLog(1021, "删除地域：【" + str + "】成功", 1, "");
    }
    catch (Exception localException)
    {
      writeOperateLog(1021, "删除地域：【" + str + "】失败", 0, localException.getMessage());
    }
    return "success";
  }
  
  public String updateZoneStatus()
  {
    this.logger.debug("enter updateCertificateTypeStatus");
    String[] arrayOfString1 = this.request.getParameterValues("ids");
    String str1 = "";
    try
    {
      for (String str2 : arrayOfString1)
      {
        Zone localZone1 = new Zone();
        localZone1.setCode(str2);
        Zone localZone2 = new Zone();
        localZone2 = (Zone)getService().get(localZone1);
        localZone2.setIsvisibal("D");
        getService().update(localZone2);
        str1 = str1 + str2 + ",";
      }
      if (!"".equals(str1)) {
        str1 = str1.substring(0, str1.length() - 1);
      }
      addReturnValue(1, 101003L, new Object[] { "地域" + str1 });
      writeOperateLog(1021, "删除地域：【" + str1 + "】成功", 1, "");
    }
    catch (Exception localException)
    {
      writeOperateLog(1021, "删除地域：【" + str1 + "】失败", 0, localException.getMessage());
    }
    return "success";
  }
  
  public String updateIndustryIsvisibal()
  {
    this.logger.debug("enter updateIndustryIsvisibal");
    String str1 = (String)this.entity.fetchPKey().getValue();
    Industry localIndustry = new Industry();
    localIndustry.setCode(str1);
    localIndustry = (Industry)getService().get(localIndustry);
    String str2 = "";
    String str3 = "";
    try
    {
      if (localIndustry.getIsvisibal().equalsIgnoreCase("Y"))
      {
        localIndustry.setIsvisibal("N");
        getService().update(localIndustry);
        str2 = "可选择";
        str3 = "不可选择";
      }
      else
      {
        localIndustry.setIsvisibal("Y");
        getService().update(localIndustry);
        str2 = "不可选择";
        str3 = "可选择";
      }
      addReturnValue(1, 119902L);
      writeOperateLog(1021, "修改行业： " + str1 + "是否可选择操作成功：由【" + str2 + "】【" + str3 + "】", 1, "");
    }
    catch (Exception localException)
    {
      writeOperateLog(1021, "修改行业： " + str1 + "是否可选择操作失败", 0, localException.getMessage());
    }
    return "success";
  }
  
  public String deleteIndustry()
  {
    this.logger.debug("enter deleteIndustry");
    String str = (String)this.entity.fetchPKey().getValue();
    try
    {
      Industry localIndustry = new Industry();
      localIndustry.setCode(str);
      localIndustry = (Industry)getService().get(localIndustry);
      getService().delete(localIndustry);
      addReturnValue(1, 101003L, new Object[] { "行业编号：" + str });
      writeOperateLog(1021, "删除行业：【" + str + "】成功", 1, "");
    }
    catch (Exception localException)
    {
      writeOperateLog(1021, "删除行业：【" + str + "】失败", 0, localException.getMessage());
    }
    return "success";
  }
  
  public String updateIndustryStatus()
  {
    this.logger.debug("enter updateCertificateTypeStatus");
    String[] arrayOfString1 = this.request.getParameterValues("ids");
    String str1 = "";
    try
    {
      for (String str2 : arrayOfString1)
      {
        Industry localIndustry1 = new Industry();
        localIndustry1.setCode(str2);
        Industry localIndustry2 = new Industry();
        localIndustry2 = (Industry)getService().get(localIndustry1);
        localIndustry2.setIsvisibal("D");
        getService().update(localIndustry2);
        str1 = str1 + str2 + ",";
      }
      if (!"".equals(str1)) {
        str1 = str1.substring(0, str1.length() - 1);
      }
      addReturnValue(1, 101003L, new Object[] { "行业" + str1 });
      writeOperateLog(1021, "删除行业：【" + str1 + "】成功", 1, "");
    }
    catch (Exception localException)
    {
      writeOperateLog(1021, "删除行业：【" + str1 + "】失败", 0, localException.getMessage());
    }
    return "success";
  }
  
  public String updateCertificateTypeIsvisibal()
  {
    this.logger.debug("enter updateCertificateTypeIsvisibal");
    String str1 = (String)this.entity.fetchPKey().getValue();
    String str2 = "";
    String str3 = "";
    try
    {
      CertificateType localCertificateType = new CertificateType();
      localCertificateType.setCode(str1);
      localCertificateType = (CertificateType)getService().get(localCertificateType);
      if (localCertificateType.getIsvisibal().equalsIgnoreCase("Y"))
      {
        localCertificateType.setIsvisibal("N");
        getService().update(localCertificateType);
      }
      else
      {
        localCertificateType.setIsvisibal("Y");
        getService().update(localCertificateType);
      }
      addReturnValue(1, 119902L);
      writeOperateLog(1021, "修改证件类型：【" + str1 + "】是否可选择操作成功，由【" + str2 + "】修改为【" + str3 + "】", 1, "");
    }
    catch (Exception localException)
    {
      writeOperateLog(1021, "修改证件类型：【" + str1 + "】是否可选择操作失败", 0, localException.getMessage());
    }
    return "success";
  }
  
  public String deleteCertificateType()
  {
    this.logger.debug("enter deleteCertificateType");
    String str = (String)this.entity.fetchPKey().getValue();
    try
    {
      CertificateType localCertificateType = new CertificateType();
      localCertificateType.setCode(str);
      localCertificateType = (CertificateType)getService().get(localCertificateType);
      getService().delete(localCertificateType);
      addReturnValue(1, 101003L, new Object[] { "证件类型编号：" + str });
      writeOperateLog(1021, "删除证件类型：【" + str + "】成功", 1, "");
    }
    catch (Exception localException)
    {
      writeOperateLog(1021, "删除证件类型：【" + str + "】失败", 0, localException.getMessage());
    }
    return "success";
  }
  
  public String updateCertificateTypeStatus()
  {
    this.logger.debug("enter updateCertificateTypeStatus");
    String[] arrayOfString1 = this.request.getParameterValues("ids");
    String str1 = "";
    try
    {
      for (String str2 : arrayOfString1)
      {
        CertificateType localCertificateType1 = new CertificateType();
        localCertificateType1.setCode(str2);
        CertificateType localCertificateType2 = new CertificateType();
        localCertificateType2 = (CertificateType)getService().get(localCertificateType1);
        localCertificateType2.setIsvisibal("D");
        getService().update(localCertificateType2);
        str1 = str1 + str2 + ",";
      }
      if (!"".equals(str1)) {
        str1 = str1.substring(0, str1.length() - 1);
      }
      addReturnValue(1, 101003L, new Object[] { "证件类型" + str1 });
      writeOperateLog(1021, "删除证件类型：【" + str1 + "】成功", 1, "");
    }
    catch (Exception localException)
    {
      writeOperateLog(1021, "删除证件类型：【" + str1 + "】失败", 0, localException.getMessage());
    }
    return "success";
  }
  
  public String updateFirmCategoryIsvisibal()
  {
    this.logger.debug("enter updateFirmCategoryIsvisibal");
    Long localLong = (Long)this.entity.fetchPKey().getValue();
    String str1 = "";
    String str2 = "";
    try
    {
      FirmCategory localFirmCategory = new FirmCategory();
      localFirmCategory.setId(localLong);
      localFirmCategory = (FirmCategory)getService().get(localFirmCategory);
      if (localFirmCategory.getIsvisibal().equalsIgnoreCase("Y"))
      {
        localFirmCategory.setIsvisibal("N");
        getService().update(localFirmCategory);
      }
      else
      {
        localFirmCategory.setIsvisibal("Y");
        getService().update(localFirmCategory);
      }
      addReturnValue(1, 119902L);
      writeOperateLog(1021, "修改交易商类别：【" + localLong + "】是否可选择操作成功，由【" + str1 + "】修改为【" + str2 + "】", 1, "");
    }
    catch (Exception localException)
    {
      writeOperateLog(1021, "修改交易商类别：【" + localLong + "】是否可选择操作失败", 0, localException.getMessage());
    }
    return "success";
  }
  
  public String deleteFirmCategory()
  {
    this.logger.debug("enter deleteFirmCategory");
    Long localLong = (Long)this.entity.fetchPKey().getValue();
    try
    {
      FirmCategory localFirmCategory = new FirmCategory();
      localFirmCategory.setId(localLong);
      localFirmCategory = (FirmCategory)getService().get(localFirmCategory);
      getService().delete(localFirmCategory);
      addReturnValue(1, 101003L, new Object[] { "交易商类别编号：" + localLong });
      writeOperateLog(1021, "删除交易商类别：【" + localLong + "】成功", 1, "");
    }
    catch (Exception localException)
    {
      writeOperateLog(1021, "删除交易商类别：【" + localLong + "】失败", 1, localException.getMessage());
    }
    return "success";
  }
  
  public String updateFirmCategoryStatus()
  {
    this.logger.debug("enter updateCertificateTypeStatus");
    String[] arrayOfString1 = this.request.getParameterValues("ids");
    String str1 = "";
    try
    {
      for (String str2 : arrayOfString1)
      {
        FirmCategory localFirmCategory1 = new FirmCategory();
        localFirmCategory1.setId(Long.valueOf(Tools.strToLong(str2, -1000L)));
        FirmCategory localFirmCategory2 = new FirmCategory();
        localFirmCategory2 = (FirmCategory)getService().get(localFirmCategory1);
        localFirmCategory2.setIsvisibal("D");
        getService().update(localFirmCategory2);
        str1 = str1 + str2 + ",";
      }
      if (!"".equals(str1)) {
        str1 = str1.substring(0, str1.length() - 1);
      }
      addReturnValue(1, 101003L, new Object[] { "交易商类别" + str1 });
      writeOperateLog(1021, "删除交易商类别：【" + str1 + "】成功", 1, "");
    }
    catch (Exception localException)
    {
      writeOperateLog(1021, "删除交易商类别：【" + str1 + "】失败", 0, localException.getMessage());
    }
    return "success";
  }
}
