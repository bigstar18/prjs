package cn.com.agree.sign;

import cn.com.pingan.b2bic.web.SignVo;
import java.util.List;
import java.util.Map;

public abstract interface ISignFactory
{
  public abstract ISign createSignTool(String paramString, Map paramMap);
  
  public abstract List<String> findCerts(SignVo paramSignVo)
    throws Exception;
}
