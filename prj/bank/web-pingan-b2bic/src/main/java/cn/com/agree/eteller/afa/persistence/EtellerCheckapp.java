package cn.com.agree.eteller.afa.persistence;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;

public class EtellerCheckapp
  implements Serializable
{
  private String serinalno;
  private String usrid;
  private String checkusrid;
  private String changdate;
  private String tablename;
  private String changfield;
  private String changsql;
  private String checkflag;
  private String optflag;
  private String note;
  private String noteview;
  private String changfieldview;
  private String optflagview;
  private String checkflagview;
  
  public void flagToChinese()
  {
    if (this.optflag != null) {
      if (this.optflag.equals("1")) {
        this.optflagview = "增加";
      } else if (this.optflag.equals("2")) {
        this.optflagview = "删除";
      } else if (this.optflag.equals("3")) {
        this.optflagview = "修改";
      } else {
        this.optflagview = "未知";
      }
    }
    if (this.checkflag != null) {
      if (this.checkflag.equals("1")) {
        this.checkflagview = "未复核";
      } else if (this.checkflag.equals("2")) {
        this.checkflagview = "复核通过";
      } else if (this.checkflag.equals("3")) {
        this.checkflagview = "复核未通过";
      } else {
        this.checkflagview = "未知";
      }
    }
  }
  
  public String getChangfieldview()
  {
    return this.changfieldview;
  }
  
  public void setChangfieldview(String changfieldview)
  {
    this.changfieldview = changfieldview;
  }
  
  public String getNoteview()
  {
    return this.noteview;
  }
  
  public void setNoteview(String noteview)
  {
    this.noteview = noteview;
  }
  
  public EtellerCheckapp(String serinalno, String usrid, String changdate, String tablename, String changfield, String changsql, String checkflag, String optflag, String note)
  {
    this.serinalno = serinalno;
    this.usrid = usrid;
    this.changdate = changdate;
    this.tablename = tablename;
    this.changfield = changfield;
    this.changsql = changsql;
    this.checkflag = checkflag;
    this.optflag = optflag;
    this.note = note;
  }
  
  public EtellerCheckapp() {}
  
  public EtellerCheckapp(String serinalno)
  {
    this.serinalno = serinalno;
  }
  
  public String getSerinalno()
  {
    return this.serinalno;
  }
  
  public void setSerinalno(String serinalno)
  {
    this.serinalno = serinalno;
  }
  
  public String getUsrid()
  {
    return this.usrid;
  }
  
  public void setUsrid(String usrid)
  {
    this.usrid = usrid;
  }
  
  public String getChangdate()
  {
    return this.changdate;
  }
  
  public void setChangdate(String changdate)
  {
    this.changdate = changdate;
  }
  
  public String getTablename()
  {
    return this.tablename;
  }
  
  public void setTablename(String tablename)
  {
    this.tablename = tablename;
  }
  
  public String getChangfield()
  {
    return this.changfield;
  }
  
  public void setChangfield(String changfield)
  {
    this.changfield = changfield;
  }
  
  public String getChangsql()
  {
    return this.changsql;
  }
  
  public void setChangsql(String changsql)
  {
    this.changsql = changsql;
  }
  
  public String getCheckflag()
  {
    return this.checkflag;
  }
  
  public void setCheckflag(String checkflag)
  {
    this.checkflag = checkflag;
  }
  
  public String getOptflag()
  {
    return this.optflag;
  }
  
  public void setOptflag(String optflag)
  {
    this.optflag = optflag;
  }
  
  public String getNote()
  {
    return this.note;
  }
  
  public void setNote(String note)
  {
    this.note = note;
  }
  
  public String toString()
  {
    return 
    
      new ToStringBuilder(this).append("serinalno", getSerinalno()).toString();
  }
  
  public String getOptflagview()
  {
    return this.optflagview;
  }
  
  public void setOptflagview(String optflagview)
  {
    this.optflagview = optflagview;
  }
  
  public String getCheckusrid()
  {
    return this.checkusrid;
  }
  
  public void setCheckusrid(String checkusrid)
  {
    this.checkusrid = checkusrid;
  }
  
  public String getCheckflagview()
  {
    return this.checkflagview;
  }
  
  public void setCheckflagview(String checkflagview)
  {
    this.checkflagview = checkflagview;
  }
}
