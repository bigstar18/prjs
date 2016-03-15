package cn.com.pingan.b2bic.web;

public abstract interface IConfigManager
{
  public abstract void load(BankVo paramBankVo)
    throws Exception;
  
  public abstract void save(BankVo paramBankVo)
    throws Exception;
  
  public abstract void load(CorpVo paramCorpVo)
    throws Exception;
  
  public abstract void save(CorpVo paramCorpVo)
    throws Exception;
  
  public abstract void load(SignVo paramSignVo)
    throws Exception;
  
  public abstract void save(SignVo paramSignVo)
    throws Exception;
  
  public abstract void load(LogLvLVo paramLogLvLVo)
    throws Exception;
  
  public abstract void save(LogLvLVo paramLogLvLVo)
    throws Exception;
  
  public abstract void commCheck(String paramString, int paramInt)
    throws Exception;
}
