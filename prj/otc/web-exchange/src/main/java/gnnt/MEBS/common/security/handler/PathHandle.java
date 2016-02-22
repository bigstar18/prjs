package gnnt.MEBS.common.security.handler;

import gnnt.MEBS.base.util.ThreadStore;
import gnnt.MEBS.common.model.Right;
import gnnt.MEBS.common.model.User;
import gnnt.MEBS.common.security.memoryModel.RightMemory;
import gnnt.MEBS.common.security.util.LikeHashMap;
import gnnt.MEBS.config.constant.ThreadStoreConstant;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PathHandle
  extends AbstractSecurityHandler
{
  private final transient Log logger = LogFactory.getLog(PathHandle.class);
  private SecurityHandler sixthSecurityHandler;
  private List<String> urlWildcardCharacterList;
  
  public void setSixthSecurityHandler(SecurityHandler sixthSecurityHandler)
  {
    this.sixthSecurityHandler = sixthSecurityHandler;
  }
  
  public void setUrlWildcardCharacterList(List<String> urlWildcardCharacterList)
  {
    this.logger.debug("urlWildcardCharacterList set");
    this.urlWildcardCharacterList = urlWildcardCharacterList;
  }
  
  public int handleRequest(String key, User user)
  {
    int result = -1;
    this.logger.debug("查找key--------------------------------:" + key);
    try
    {
      LikeHashMap likeHashMap = (LikeHashMap)
        RightMemory.getNOWILDCARDRIGHTMAP();
      long rightId = likeHashMap.checkRight(key, false);
      if (rightId >= 0L) {
        result = checkUser(user, rightId);
      }
      if (result < 0)
      {
        likeHashMap = (LikeHashMap)RightMemory.getWILDCARDRIGHTMAP();
        rightId = likeHashMap.checkRight(key, true);
        this.logger.debug("进入模糊查找key:" + key + "    rightId:" + rightId);
        if (rightId >= 0L) {
          result = checkUser(user, rightId);
        } else {
          result = toNextHandle(user, key, this.sixthSecurityHandler);
        }
      }
      if ((result >= 0) && (result != 100))
      {
        Map<Long, Right> allLikeHashMap = RightMemory.getALLRIGHTMAP();
        Right right = (Right)allLikeHashMap.get(Long.valueOf(rightId));
        this.logger.debug("right.key:" + right.getUrl());
        ThreadStore.put(ThreadStoreConstant.ISLOG, right.getIsLog());
        this.logger.debug("right.log:" + right.getIsLog());
        this.logger.debug(Boolean.valueOf("right:" + right == null));
        if ((right != null) && (right.getType().intValue() == 0))
        {
          this.logger.debug("三级菜单");
          ThreadStore.put(ThreadStoreConstant.RIGHT, right);
          ThreadStore.put("rightName", right.getName());
          


          Map<String, Boolean> rightMap = new HashMap();
          for (String wildcardCharacter : this.urlWildcardCharacterList) {
            rightMap.put(wildcardCharacter, Boolean.valueOf(false));
          }
          if ((right.getRightSet() != null) && (!right.getRightSet().isEmpty()))
          {
            this.logger.debug("检查权限");
            Iterator it = right.getRightSet().iterator();
            while (it.hasNext())
            {
              Right childRight = (Right)it.next();
              if (childRight.getType().intValue() == 1)
              {
                this.logger.debug("childRight.url=" + childRight.getUrl());
                for (String wildcardCharacter : rightMap.keySet()) {
                  if (childRight.getUrl().contains(wildcardCharacter + "*"))
                  {
                    boolean flag = false;
                    if (checkUser(user, childRight.getId().longValue()) == 0) {
                      flag = true;
                    }
                    rightMap.put(wildcardCharacter, Boolean.valueOf(flag));
                  }
                }
              }
            }
          }
          this.logger.debug("-------------------");
          HttpServletRequest request = (HttpServletRequest)
            ThreadStore.get(ThreadStoreConstant.REQUEST);
          
          HttpSession session = request.getSession();
          this.logger.debug("rightMap:" + rightMap);
          JSONObject jsonObject = (JSONObject)JSONSerializer.toJSON(rightMap);
          this.logger.debug(jsonObject);
          


          session.setAttribute("rightMap", jsonObject);
          session.setAttribute("rightMapReal", rightMap);
          this.logger.debug("rightMap:" + jsonObject);
        }
        else if ((right != null) && (right.getType().intValue() == 1) && 
          (right.getRight() != null))
        {
          ThreadStore.put(ThreadStoreConstant.OPERATE, right.getRight().getName() + right.getName().substring(0, right.getName().length() - 2));
          this.logger.debug("operate:" + right.getRight().getName() + right.getName().substring(0, right.getName().length() - 2));
        }
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return result;
  }
}
