package cn.com.pingan.b2bic.web;

import cn.com.agree.sign.ISign;
import cn.com.agree.sign.ISignFactory;
import java.util.Map;

public class SignCheck
{
  private ISignFactory factory;
  
  public String rtrvSubject(String signMode, Map param)
    throws Exception
  {
    ISign sign = this.factory.createSignTool(signMode, param);
    String simple = "abcd1234";
    try
    {
      sign.sign(simple.getBytes());
    }
    catch (Exception e)
    {
      throw new Exception("签名测试异常:" + e.getMessage());
    }
    return sign.getSubject();
  }
  
  public ISignFactory getFactory()
  {
    return this.factory;
  }
  
  public void setFactory(ISignFactory factory)
  {
    this.factory = factory;
  }
}
