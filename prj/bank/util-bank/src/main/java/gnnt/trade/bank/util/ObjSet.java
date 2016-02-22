package gnnt.trade.bank.util;

import java.util.*;


/**
 * <p>Title: 分页传数据对象。</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: GNNT</p>
 *
 * @author zhangzl
 * @version 1.0
 */

public class ObjSet extends Vector<Object> 
{
  
	  /**
	   * versionID
	   */
	  private static final long serialVersionUID = 1000;
	
	  /**
	   * ObjSet
	   */
	  private ObjSet() 
	  {
		  super();
	  }


	  /**
	   * 总记录数
	   */
	  private int totalCount = 0;
	
	  /**
	   * 每页记录数
	   */
	  private int pageSize = 0;
	
	  /**
	   * 总页数
	   */
	  private int pageCount = 0;
	
	  /**
	   * 当前页码
	   */
	  private int pageIndex = 1;
	
	  /**
	   * 取得总记录数
	   * @return int
	   */
	  public int getTotalCount() 
	  {
	    return totalCount;
	  }
	
	  /**
	   * 取得每页记录数
	   * @return int
	   */
	  public int getPageSize() 
	  {
	    return pageSize;
	  }
	
	  /**
	   * 取得当前页包含的记录数。
	   * @return int
	   */
	  public int getCurNum() 
	  {
	    return this.size();
	  }
	
	  /**
	   * 取得总页数
	   * @return int
	   */
	  public int getPageCount() 
	  {
	    if(pageSize > 0) 
	    {
	      if((totalCount % pageSize) != 0) 
	      {
	        pageCount = (int) totalCount / pageSize + 1;
	      }
	      else 
	      {
	        pageCount = (int) totalCount / pageSize;
	      }
	    }
	    return pageCount;
	  }
	
	  /**
	   * 取得当前页码
	   * @return int
	   */
	  public int getPageIndex() 
	  {
	    return pageIndex;
	  }
	
	  /**
	   * 创建ObjSet
	   * @param objs Vector 要分页的记录集
	   * @param totalCount int 总记录数
	   * @param pageSize int 每页记录数
	   * @param pageIndex int 当前页码
	   * @return ObjSet
	   */
	  public static ObjSet getInstance(Vector<Object> objs,int pageSize,int pageIndex) 
	  {
	    ObjSet objSet = new ObjSet();
	    if(objs != null) 
	    {
	    	objSet.totalCount = objs.size();
		    objSet.pageSize = pageSize;
		    objSet.pageIndex = pageIndex;
	    	
	    	for(int i=0;i<objs.size();i++)
	    	{
	    		if((pageIndex-1)*pageSize <= i && i < pageIndex*pageSize)
	    		{
	    			objSet.add(objs.get(i));
	    		}
	    	}
	    }    
	    return objSet;
	  }
	  
	  public static ObjSet getInstance(List<Object> objs,int pageSize,int pageIndex) 
	  {
	    ObjSet objSet = new ObjSet();
	    if(objs != null) 
	    {
	    	objSet.totalCount = objs.size();
		    objSet.pageSize = pageSize;
		    objSet.pageIndex = pageIndex;
	    	
	    	for(int i=0;i<objs.size();i++)
	    	{
	    		if((pageIndex-1)*pageSize <= i && i < pageIndex*pageSize)
	    		{
	    			objSet.add(objs.get(i));
	    		}
	    	}
	    }    
	    return objSet;
	  }
}
