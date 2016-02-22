package gnnt.MEBS.announcement.noticeHandler;

import org.dom4j.Element;

public abstract interface NoticeTypeHandler
{
  public abstract int handleRequest(Long paramLong, Element paramElement);
}
