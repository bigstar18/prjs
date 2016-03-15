package cn.com.agree.eteller.usermanager.dao.hibernate;

import cn.com.agree.eteller.generic.dao.hibernate.GenericDAOImpl;
import cn.com.agree.eteller.usermanager.dao.NoticeDAO;
import cn.com.agree.eteller.usermanager.persistence.Notice;
import org.springframework.stereotype.Repository;

@Repository("noticeDAO")
public class NoticeDAOImpl
  extends GenericDAOImpl<Notice>
  implements NoticeDAO
{
  private static final long serialVersionUID = 1L;
}
