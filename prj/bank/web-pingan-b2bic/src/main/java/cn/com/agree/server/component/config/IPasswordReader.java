package cn.com.agree.server.component.config;

public abstract interface IPasswordReader
{
  public abstract String read(String paramString);
  
  public abstract String write(String paramString);
}
