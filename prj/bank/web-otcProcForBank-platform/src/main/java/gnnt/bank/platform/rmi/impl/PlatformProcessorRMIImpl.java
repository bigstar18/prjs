package gnnt.bank.platform.rmi.impl;

import gnnt.bank.platform.contrl.ICBCCapitalProcessor;
import gnnt.bank.platform.contrl.PaltformProcessor;
import gnnt.bank.platform.rmi.PlatformProcessorRMI;
import gnnt.bank.platform.util.Tool;
import gnnt.bank.platform.vo.RequestMsg;
import gnnt.trade.bank.vo.ReturnValue;
import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class PlatformProcessorRMIImpl
  extends UnicastRemoteObject
  implements PlatformProcessorRMI
{
  private static final long serialVersionUID = 1L;
  Object cp = null;
  
  public PlatformProcessorRMIImpl()
    throws RemoteException
  {}
  
  public ReturnValue doWork(RequestMsg req)
    throws RemoteException
  {
    ReturnValue result = new ReturnValue();
    if (req == null)
    {
      Tool.log("传入的请求参数为null");
      result.result = -1L;
      result.remark = "请求参数非法";
      return result;
    }
    String methodName = req.getMethodName();
    if ((methodName == null) || ("".equals(methodName.trim())))
    {
      Tool.log("请求参数中，方法名为空");
      result.result = -1L;
      result.remark = "请求参数不正确";
      return result;
    }
    if (req.getBankID() == null)
    {
      Tool.log("传入的参数中，BankID为null");
      result.result = -1L;
      result.remark = "调用失败，传入的BankID为空";
      return result;
    }
    try
    {
      if ("10".equals(req.getBankID())) {
        this.cp = ((ICBCCapitalProcessor)Class.forName("gnnt.bank.platform.contrl.ICBCCapitalProcessor").newInstance());
      } else {
        this.cp = ((PaltformProcessor)Class.forName("gnnt.bank.platform.contrl.PaltformProcessor").newInstance());
      }
    }
    catch (Exception e)
    {
      Tool.log("创建处理器对象异常：" + Tool.getExceptionTrace(e));
      result.result = -1L;
      result.remark = "系统异常";
      return result;
    }
    if (this.cp == null)
    {
      Tool.log("处理器主类对象为空了");
      result.result = -1L;
      result.remark = "系统异常";
      return result;
    }
    Class proc = this.cp.getClass();
    Method method = null;
    Object[] params = req.getParams();
    if ((params == null) || (params.length <= 0))
    {
      try
      {
        method = proc.getMethod(methodName, new Class[0]);
      }
      catch (SecurityException e)
      {
        Tool.log("得到被调用方法SecurityException：" + Tool.getExceptionTrace(e));
        result.result = -1L;
        result.remark = "系统异常";
      }
      catch (NoSuchMethodException e)
      {
        Tool.log("没有找到对应的方法：" + Tool.getExceptionTrace(e));
        result.result = -1L;
        result.remark = "系统异常";
      }
      try
      {
        result = (ReturnValue)method.invoke(this.cp, new Object[0]);
      }
      catch (Exception e)
      {
        Tool.log("调用方法异常：" + Tool.getExceptionTrace(e));
        result.result = -1L;
        result.remark = "系统异常";
      }
    }
    else
    {
      Class[] paramsType = new Class[params.length];
      for (int i = 0; i < params.length; i++)
      {
        if (params[i] == null)
        {
          Tool.log("传入该方法的第[" + (i + 1) + "]个参数为null");
          result.result = -1L;
          result.remark = "传入的参数不合法";
          return result;
        }
        String classType = params[i].getClass().getSimpleName();
        if ("Long".equalsIgnoreCase(classType)) {
          paramsType[i] = Long.TYPE;
        } else if ("Byte".equalsIgnoreCase(classType)) {
          paramsType[i] = Byte.TYPE;
        } else if ("Short".equalsIgnoreCase(classType)) {
          paramsType[i] = Short.TYPE;
        } else if ("Integer".equalsIgnoreCase(classType)) {
          paramsType[i] = Integer.TYPE;
        } else if ("Float".equalsIgnoreCase(classType)) {
          paramsType[i] = Float.TYPE;
        } else if ("Double".equalsIgnoreCase(classType)) {
          paramsType[i] = Double.TYPE;
        } else if ("Boolean".equalsIgnoreCase(classType)) {
          paramsType[i] = Boolean.TYPE;
        } else if ("Character".equalsIgnoreCase(classType)) {
          paramsType[i] = Character.TYPE;
        } else {
          paramsType[i] = params[i].getClass();
        }
      }
      try
      {
        method = proc.getMethod(methodName, paramsType);
      }
      catch (SecurityException e)
      {
        Tool.log("得到被调用方法SecurityException：" + Tool.getExceptionTrace(e));
        result.result = -1L;
        result.remark = "系统异常";
      }
      catch (NoSuchMethodException e)
      {
        Tool.log("没有找到对应的方法：" + Tool.getExceptionTrace(e));
        result.result = -1L;
        result.remark = "系统异常";
      }
      try
      {
        result = (ReturnValue)method.invoke(this.cp, params);
      }
      catch (Exception e)
      {
        Tool.log("调用方法异常：" + Tool.getExceptionTrace(e));
        result.result = -1L;
        result.remark = "系统异常";
      }
    }
    return result;
  }
}
