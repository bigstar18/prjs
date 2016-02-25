package gnnt.MEBS.delivery.workflow.common;

import gnnt.MEBS.delivery.model.workflow.WorkFlowClone;
import gnnt.MEBS.delivery.workflow.Handle;
import gnnt.MEBS.delivery.workflow.OriginalModel;
import gnnt.MEBS.delivery.workflow.Precondition;
import gnnt.MEBS.delivery.workflow.StatusOperate;
import gnnt.MEBS.delivery.workflow.Step;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class StepImpl
  implements Step
{
  private final transient Log logger = LogFactory.getLog(StepImpl.class);
  private Handle defaultHandle;
  private List<Handle> handleList;
  private List<Precondition> preconditionList;
  private List<Integer> authorityList;
  private int currentStatus;
  private StatusOperate statusOperate;
  private boolean existNote;
  
  public boolean checkAuthority(int paramInt)
  {
    boolean bool = false;
    this.logger.debug("holdAuthority:" + paramInt);
    Iterator localIterator = this.authorityList.iterator();
    while (localIterator.hasNext())
    {
      int i = ((Integer)localIterator.next()).intValue();
      this.logger.debug("authority:" + i);
      if (i == paramInt)
      {
        bool = true;
        break;
      }
    }
    return bool;
  }
  
  public int deal(OriginalModel paramOriginalModel)
  {
    int i = 1;
    int j = 1;
    if (this.handleList != null)
    {
      Iterator localIterator = this.handleList.iterator();
      while (localIterator.hasNext())
      {
        Handle localHandle = (Handle)localIterator.next();
        if (localHandle.checkCondition(paramOriginalModel))
        {
          i = localHandle.checkFilterList(paramOriginalModel);
          if (i == 1)
          {
            localHandle.dealBehaviour(paramOriginalModel.getObject());
            updateStatus(localHandle, paramOriginalModel.getObject());
          }
          j = 0;
          break;
        }
      }
    }
    if (j != 0)
    {
      i = this.defaultHandle.checkFilterList(paramOriginalModel);
      if (i == 1)
      {
        this.defaultHandle.dealBehaviour(paramOriginalModel.getObject());
        updateStatus(this.defaultHandle, paramOriginalModel.getObject());
      }
    }
    return i;
  }
  
  public int getCurrentStatus()
  {
    return this.currentStatus;
  }
  
  public void setAuthorityList(List<Integer> paramList)
  {
    this.authorityList = paramList;
  }
  
  public void setCurrentStatus(int paramInt)
  {
    this.currentStatus = paramInt;
  }
  
  public void setHandleList(List<Handle> paramList)
  {
    this.handleList = paramList;
  }
  
  public void updateStatus(Handle paramHandle, WorkFlowClone paramWorkFlowClone)
  {
    int i = paramHandle.getFinalStatus();
    this.statusOperate.updateStatus(paramWorkFlowClone, i);
  }
  
  public void setDefaultHandle(Handle paramHandle)
  {
    this.defaultHandle = paramHandle;
  }
  
  public void setPreconditionList(List<Precondition> paramList)
  {
    this.preconditionList = paramList;
  }
  
  public int dealPrecondtion(OriginalModel paramOriginalModel)
  {
    int i = 1;
    if (this.preconditionList != null)
    {
      Iterator localIterator = this.preconditionList.iterator();
      while (localIterator.hasNext())
      {
        Precondition localPrecondition = (Precondition)localIterator.next();
        i = localPrecondition.doPrecondition(paramOriginalModel);
        if (i != -1) {
          break;
        }
      }
    }
    return i;
  }
  
  public void setStatusOperate(StatusOperate paramStatusOperate)
  {
    this.statusOperate = paramStatusOperate;
  }
  
  public List getBeanMessageList()
  {
    ArrayList localArrayList = new ArrayList();
    this.logger.debug("defaultHandleBeanName:" + this.defaultHandle.getBeanName());
    Object localObject1;
    if (this.defaultHandle.getBeanName() != null)
    {
      localObject1 = new HashMap();
      ((Map)localObject1).put("value", this.defaultHandle.getBeanName());
      ((Map)localObject1).put("isWriteNote", Boolean.valueOf(this.defaultHandle.isWriteNote()));
      localArrayList.add(localObject1);
    }
    if (this.handleList != null)
    {
      localObject1 = this.handleList.iterator();
      while (((Iterator)localObject1).hasNext())
      {
        Handle localHandle = (Handle)((Iterator)localObject1).next();
        int i = 0;
        Object localObject2;
        if (localArrayList.size() > 0)
        {
          localObject2 = localArrayList.iterator();
          while (((Iterator)localObject2).hasNext())
          {
            Map localMap = (Map)((Iterator)localObject2).next();
            String str = (String)localMap.get("value");
            if (str.equals(localHandle.getBeanName())) {
              i = 1;
            }
          }
        }
        if (i == 0)
        {
          localObject2 = new HashMap();
          ((Map)localObject2).put("value", localHandle.getBeanName());
          ((Map)localObject2).put("isWriteNote", Boolean.valueOf(localHandle.isWriteNote()));
          localArrayList.add(localObject2);
        }
      }
    }
    return localArrayList;
  }
  
  public boolean isExistNote()
  {
    return this.existNote;
  }
  
  public void setExistNote(boolean paramBoolean)
  {
    this.existNote = paramBoolean;
  }
}
