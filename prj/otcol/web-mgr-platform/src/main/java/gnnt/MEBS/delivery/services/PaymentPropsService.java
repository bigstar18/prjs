package gnnt.MEBS.delivery.services;

import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.delivery.dao.LogDao;
import gnnt.MEBS.delivery.dao.PaymentPropsDao;
import gnnt.MEBS.delivery.model.LogValue;
import gnnt.MEBS.delivery.model.PaymentProps;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("w_paymentPropsService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class PaymentPropsService
{
  @Autowired
  @Qualifier("w_paymentPropsDao")
  private PaymentPropsDao paymentPropsDao;
  @Autowired
  @Qualifier("w_logDao")
  private LogDao logDao;
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public List getPaymentPropsList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    return this.paymentPropsDao.getPaymentPropsList(paramQueryConditions, paramPageInfo);
  }
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public PaymentProps getPaymentPropsByCondition(String paramString1, String paramString2, String paramString3)
  {
    return this.paymentPropsDao.getPaymentPropsByCondition(paramString1, paramString2, paramString3);
  }
  
  public void insertPaymentProps(PaymentProps paramPaymentProps, LogValue paramLogValue)
  {
    this.paymentPropsDao.insertPaymentProps(paramPaymentProps);
    if ((paramLogValue != null) && (paramLogValue.getOperator() != null) && (!"".equals(paramLogValue.getOperator()))) {
      this.logDao.addLog(paramLogValue);
    }
  }
  
  public void updatePaymentProps(PaymentProps paramPaymentProps, LogValue paramLogValue)
  {
    this.paymentPropsDao.updatePaymentProps(paramPaymentProps);
    if ((paramLogValue != null) && (paramLogValue.getOperator() != null) && (!"".equals(paramLogValue.getOperator()))) {
      this.logDao.addLog(paramLogValue);
    }
  }
  
  public void deletePaymentProps(PaymentProps paramPaymentProps, LogValue paramLogValue)
  {
    this.paymentPropsDao.deletePaymentProps(paramPaymentProps);
    if ((paramLogValue != null) && (paramLogValue.getOperator() != null) && (!"".equals(paramLogValue.getOperator()))) {
      this.logDao.addLog(paramLogValue);
    }
  }
}
