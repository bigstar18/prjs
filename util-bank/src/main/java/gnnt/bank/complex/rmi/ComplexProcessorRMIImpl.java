package gnnt.bank.complex.rmi;

import gnnt.bank.complex.ComplexProcessor;
import gnnt.bank.complex.util.Util;
import gnnt.bank.platform.vo.RequestMsg;
import gnnt.bank.trade.rmi.TradeProcessorRMI;
import gnnt.trade.bank.vo.ReturnValue;
import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ComplexProcessorRMIImpl extends UnicastRemoteObject
  implements TradeProcessorRMI
{
  private static final long serialVersionUID = 1L;
  ComplexProcessor cp = new ComplexProcessor();

  public ComplexProcessorRMIImpl() throws RemoteException
  {
  }

  public ReturnValue doWork(RequestMsg req) throws RemoteException
  {
    ReturnValue result = new ReturnValue();
    if (req == null) {
      Util.log("传入的请求参数为null");
      result.result = -1L;
      result.remark = "请求参数非法";
      return result;
    }
    String methodName = req.getMethodName();
    if ((methodName == null) || ("".equals(methodName.trim()))) {
      Util.log("请求参数中，方法名为空");
      result.result = -1L;
      result.remark = "请求参数不正确";
      return result;
    }
    if (req.getBankID() == null) {
      Util.log("传入的参数中，BankID为null");
      result.result = -1L;
      result.remark = "调用失败，传入的BankID为空";
      return result;
    }
    Class proc = this.cp.getClass();
    Method method = null;
    Object[] params = req.getParams();
    if ((params == null) || (params.length <= 0)) {
      try {
        method = proc.getMethod(methodName, new Class[0]);
      } catch (SecurityException e) {
        Util.log("得到被调用方法SecurityException：" + Util.getExceptionTrace(e));
        result.result = -1L;
        result.remark = "系统异常";
      } catch (NoSuchMethodException e) {
        Util.log("没有找到对应的方法：" + Util.getExceptionTrace(e));
        result.result = -1L;
        result.remark = "系统异常";
      }
      try {
        result = (ReturnValue)method.invoke(this.cp, new Object[0]);
      } catch (Exception e) {
        Util.log("调用方法异常：" + Util.getExceptionTrace(e));
        result.result = -1L;
        result.remark = "系统异常";
      }
    } else {
      Class[] paramsType = new Class[params.length];
      for (int i = 0; i < params.length; i++) {
        if (params[i] == null) {
          Util.log("传入该方法的第[" + (i + 1) + "]个参数为null");
          result.result = -1L;
          result.remark = "传入的参数不合法";
          return result;
        }
        String classType = params[i].getClass().getSimpleName();
        if ("Long".equalsIgnoreCase(classType))
          paramsType[i] = Long.TYPE;
        else if ("Byte".equalsIgnoreCase(classType))
          paramsType[i] = Byte.TYPE;
        else if ("Short".equalsIgnoreCase(classType))
          paramsType[i] = Short.TYPE;
        else if ("Integer".equalsIgnoreCase(classType))
          paramsType[i] = Integer.TYPE;
        else if ("Float".equalsIgnoreCase(classType))
          paramsType[i] = Float.TYPE;
        else if ("Double".equalsIgnoreCase(classType))
          paramsType[i] = Double.TYPE;
        else if ("Boolean".equalsIgnoreCase(classType))
          paramsType[i] = Boolean.TYPE;
        else if ("Character".equalsIgnoreCase(classType))
          paramsType[i] = Character.TYPE;
        else
          paramsType[i] = params[i].getClass();
      }
      try
      {
        method = proc.getMethod(methodName, paramsType);
      } catch (SecurityException e) {
        Util.log("得到被调用方法SecurityException：" + Util.getExceptionTrace(e));
        result.result = -1L;
        result.remark = "系统异常";
      } catch (NoSuchMethodException e) {
        Util.log("没有找到对应的方法：" + Util.getExceptionTrace(e));
        result.result = -1L;
        result.remark = "系统异常";
      }
      try {
        result = (ReturnValue)method.invoke(this.cp, params);
      } catch (Exception e) {
        Util.log("调用方法异常：" + Util.getExceptionTrace(e));
        result.result = -1L;
        result.remark = "系统异常";
      }
    }

    return result;
  }
}