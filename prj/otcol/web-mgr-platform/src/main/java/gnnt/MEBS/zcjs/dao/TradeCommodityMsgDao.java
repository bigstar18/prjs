package gnnt.MEBS.zcjs.dao;

import gnnt.MEBS.base.copy.CommonRowMapper;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.zcjs.model.TradeCommodityMsg;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback;
import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.jdbc.support.lob.LobHandler;

public class TradeCommodityMsgDao
  extends DaoHelperImpl
{
  private LobHandler lobHandler;
  
  public void setLobHandler(LobHandler paramLobHandler)
  {
    this.lobHandler = paramLobHandler;
  }
  
  public void updateBlob(final TradeCommodityMsg paramTradeCommodityMsg)
  {
    String str = "update Z_tradeCommodityMsg set uploading=?,uploadingName=? where tradeCommodityMsgId=?";
    getJdbcTemplate().execute(str, new AbstractLobCreatingPreparedStatementCallback(this.lobHandler)
    {
      protected void setValues(PreparedStatement paramAnonymousPreparedStatement, LobCreator paramAnonymousLobCreator)
        throws SQLException, DataAccessException
      {
        paramAnonymousLobCreator.setBlobAsBytes(paramAnonymousPreparedStatement, 1, paramTradeCommodityMsg.getUploading());
        paramAnonymousPreparedStatement.setString(2, paramTradeCommodityMsg.getUploadingName());
        paramAnonymousPreparedStatement.setLong(3, paramTradeCommodityMsg.getTradeCommodityMsgId());
      }
    });
  }
  
  public void add(TradeCommodityMsg paramTradeCommodityMsg)
  {
    String str = "insert into Z_tradeCommodityMsg (tradeCommodityMsgId,breedId,commodityProperties,commodityName,deliveryDay,quality,unitVolume,effectiveDays,deliveryPlace,note,expandProperty) values(?,?,?,?,?,?,?,?,?,?,?)";
    Object[] arrayOfObject = { Long.valueOf(paramTradeCommodityMsg.getTradeCommodityMsgId()), Long.valueOf(paramTradeCommodityMsg.getBreedId()), paramTradeCommodityMsg.getCommodityProperties(), paramTradeCommodityMsg.getCommodityName(), Integer.valueOf(paramTradeCommodityMsg.getDeliveryDay()), paramTradeCommodityMsg.getQuality(), Double.valueOf(paramTradeCommodityMsg.getUnitVolume()), Integer.valueOf(paramTradeCommodityMsg.getEffectiveDays()), paramTradeCommodityMsg.getDeliveryPlace(), paramTradeCommodityMsg.getNote(), paramTradeCommodityMsg.getExpandProperty() };
    int[] arrayOfInt = { 2, 2, 12, 12, 2, 12, 2, 2, 12, 12, 12 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public void delete(long paramLong)
  {
    String str = "delete from Z_TradeCommodityMsg where TradeCommodityMsgId=" + paramLong + "";
    updateBySQL(str);
  }
  
  public List<TradeCommodityMsg> getObjectList(QueryConditions paramQueryConditions)
  {
    String str = "select tradeCommodityMsgId,breedId,commodityProperties,commodityName,deliveryDay,quality,unitVolume,effectiveDays,uploadingName,deliveryPlace,note,expandProperty from Z_TradeCommodityMsg where 1=1 ";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " and " + paramQueryConditions.getFieldsSqlClause();
    }
    return queryBySQL(str, arrayOfObject, null, new CommonRowMapper(new TradeCommodityMsg()));
  }
  
  public TradeCommodityMsg getObject(long paramLong)
  {
    String str = "select * from Z_TradeCommodityMsg where TradeCommodityMsgId=" + paramLong + "";
    TradeCommodityMsg localTradeCommodityMsg = new TradeCommodityMsg();
    List localList = queryBySQL(str, null, null, new CommonRowMapper(new TradeCommodityMsg()));
    if ((localList != null) && (localList.size() > 0)) {
      localTradeCommodityMsg = (TradeCommodityMsg)localList.get(0);
    }
    return localTradeCommodityMsg;
  }
  
  public void update(TradeCommodityMsg paramTradeCommodityMsg)
  {
    String str = "update Z_tradeCommodityMsg set breedId=?,commodityProperties=?,commodityName=?,deliveryDay=?,quality=?,unitVolume=?,effectiveDays=?,deliveryPlace=?note=?,expandProperty=? where tradeCommodityMsgId=? ";
    Object[] arrayOfObject = { Long.valueOf(paramTradeCommodityMsg.getBreedId()), paramTradeCommodityMsg.getCommodityProperties(), paramTradeCommodityMsg.getCommodityName(), Integer.valueOf(paramTradeCommodityMsg.getDeliveryDay()), paramTradeCommodityMsg.getQuality(), Double.valueOf(paramTradeCommodityMsg.getUnitVolume()), Integer.valueOf(paramTradeCommodityMsg.getEffectiveDays()), paramTradeCommodityMsg.getDeliveryPlace(), paramTradeCommodityMsg.getNote(), paramTradeCommodityMsg.getExpandProperty(), Long.valueOf(paramTradeCommodityMsg.getTradeCommodityMsgId()) };
    int[] arrayOfInt = { 2, 12, 12, 2, 12, 2, 2, 12, 12, 12, 2 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public long getId()
  {
    long l = 0L;
    String str = "select SEQ_Z_tradeCommodityMsg.nextVal from dual";
    l = queryForInt(str, null);
    return l;
  }
  
  public TradeCommodityMsg getObjectLock(long paramLong)
  {
    String str = "select * from Z_TradeCommodityMsg where TradeCommodityMsgId=" + paramLong + " for update";
    TradeCommodityMsg localTradeCommodityMsg = new TradeCommodityMsg();
    List localList = queryBySQL(str, null, null, new CommonRowMapper(new TradeCommodityMsg()));
    if ((localList != null) && (localList.size() > 0)) {
      localTradeCommodityMsg = (TradeCommodityMsg)localList.get(0);
    }
    return localTradeCommodityMsg;
  }
  
  public TradeCommodityMsg getObjectBig(long paramLong)
  {
    String str = "select Uploading from Z_TradeCommodityMsg where TradeCommodityMsgId=?";
    TradeCommodityMsg localTradeCommodityMsg = null;
    List localList = queryBySQL(str, new Object[] { Long.valueOf(paramLong) }, null, new RowMapper()
    {
      public Object mapRow(ResultSet paramAnonymousResultSet, int paramAnonymousInt)
        throws SQLException
      {
        TradeCommodityMsg localTradeCommodityMsg = new TradeCommodityMsg();
        byte[] arrayOfByte = TradeCommodityMsgDao.this.lobHandler.getBlobAsBytes(paramAnonymousResultSet, 1);
        localTradeCommodityMsg.addUploading(arrayOfByte);
        return localTradeCommodityMsg;
      }
    });
    if (localList.size() > 0) {
      localTradeCommodityMsg = (TradeCommodityMsg)localList.get(0);
    }
    return localTradeCommodityMsg;
  }
}
