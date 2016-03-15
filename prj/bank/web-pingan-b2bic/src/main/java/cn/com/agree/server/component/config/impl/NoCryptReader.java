package cn.com.agree.server.component.config.impl;

import cn.com.agree.server.component.config.IPasswordReader;

public class NoCryptReader
  implements IPasswordReader
{
  public String read(String src)
  {
    return src;
  }
  
  public String write(String src)
  {
    return src;
  }
}
