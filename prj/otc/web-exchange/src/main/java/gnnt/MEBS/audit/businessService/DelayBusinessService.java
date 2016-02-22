package gnnt.MEBS.audit.businessService;

import gnnt.MEBS.base.copy.MapToObject;
import gnnt.MEBS.base.util.SpringContextHelper;
import gnnt.MEBS.commodity.model.StepDictionary;
import gnnt.MEBS.commodity.model.TCDelayFee;
import gnnt.MEBS.commodity.model.vo.TCDelayFeeVO;
import gnnt.MEBS.commodity.service.StepDictionaryService;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class DelayBusinessService
  implements BusinessService
{
  private final transient Log logger = LogFactory.getLog(DelayBusinessService.class);
  @Autowired
  @Qualifier("stepDictionaryService")
  private StepDictionaryService stepDictionaryService;
  private String serrviceName;
  private String methodName;
  private String classFullName;
  
  public void setSerrviceName(String serrviceName)
  {
    this.serrviceName = serrviceName;
  }
  
  public void setMethodName(String methodName)
  {
    this.methodName = methodName;
  }
  
  public void setClassFullName(String classFullName)
  {
    this.classFullName = classFullName;
  }
  
  public int business(Map<String, String> businessObjectMap)
  {
    int returnValue = 0;
    Object object = SpringContextHelper.getBean(this.serrviceName);
    Method method = null;
    try
    {
      Class classType = object.getClass();
      Method[] methods = classType.getMethods();
      if ((methods != null) && (methods.length > 0)) {
        for (Method met : methods) {
          if ((met.getName().equals(this.methodName)) && (met.getParameterTypes().length == 1))
          {
            method = met;
            break;
          }
        }
      }
      TCDelayFeeVO tcDelayFeeVo = new TCDelayFeeVO();
      Object ladderList = this.stepDictionaryService.getFundsList(null, null);
      Object list = new ArrayList();
      for (String key : businessObjectMap.keySet()) {
        if (key.indexOf("mkt_stepNO") >= 0)
        {
          TCDelayFee delayFee = new TCDelayFee();
          delayFee.setCommodityId((String)businessObjectMap.get("commodityId"));
          delayFee.setFirmId((String)businessObjectMap.get("firmId"));
          delayFee.setFeeAlgr_v(Integer.valueOf(Integer.parseInt((String)businessObjectMap.get("feeAlgr_v"))));
          long stepNo = Long.parseLong(key.replaceAll("mkt_stepNO", ""));
          for (int i = 0; i < ((List)ladderList).size(); i++)
          {
            long ladderListStepNo = ((StepDictionary)((List)ladderList).get(i)).getStepNo().intValue();
            if (stepNo == ladderListStepNo)
            {
              delayFee.setStepNo(Long.valueOf(ladderListStepNo));
              delayFee.setDelayFee_v(new BigDecimal(Double.parseDouble((String)businessObjectMap.get("stepNO" + stepNo))));
            }
          }
          delayFee.setMkt_delayFeeRate_v(new BigDecimal(Double.parseDouble((String)businessObjectMap.get(key))));
          ((List)list).add(delayFee);
        }
      }
      if (((List)list).size() == 0) {
        list = null;
      }
      MapToObject.bindData(businessObjectMap, tcDelayFeeVo);
      tcDelayFeeVo.setTcDelayFeeList((List)list);
      Object clone = tcDelayFeeVo;
      Object o = method.invoke(object, new Object[] { clone });
      returnValue = ((Integer)o).intValue();
    }
    catch (Exception e)
    {
      e.printStackTrace();
      returnValue = -1;
    }
    return returnValue;
  }
}
