package cn.com.agree.eteller.usermanager.spring.implement;

import cn.com.agree.eteller.generic.spring.implement.BaseManagerImpl;
import cn.com.agree.eteller.usermanager.dao.NoticeDAO;
import cn.com.agree.eteller.usermanager.persistence.Notice;
import cn.com.agree.eteller.usermanager.spring.NoticeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("noticeManagerTarget")
public class NoticeManagerImpl
  extends BaseManagerImpl<Notice>
  implements NoticeManager
{
  private static final long serialVersionUID = 1L;
  
  @Autowired
  public NoticeManagerImpl(NoticeDAO noticeDAO)
  {
    super(noticeDAO);
  }
}
