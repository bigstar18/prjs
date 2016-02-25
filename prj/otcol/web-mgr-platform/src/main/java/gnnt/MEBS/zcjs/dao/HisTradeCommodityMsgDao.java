package gnnt.MEBS.zcjs.dao;

import gnnt.MEBS.base.copy.CommonRowMapper;
import gnnt.MEBS.zcjs.model.HisTradeCommodityMsg;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback;
import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.jdbc.support.lob.LobHandler;

public class HisTradeCommodityMsgDao
  extends DaoHelperImpl
{
  private LobHandler lobHandler;
  
  public void setLobHandler(LobHandler paramLobHandler)
  {
    this.lobHandler = paramLobHandler;
  }
  
  public void updateBlob(final HisTradeCommodityMsg paramHisTradeCommodityMsg)
  {
    String str = "update Z_H_tradeCommodityMsg set uploading=?,uploadingName=? where tradeCommodityMsgId=?";
    getJdbcTemplate().execute(str, new AbstractLobCreatingPreparedStatementCallback(this.lobHandler)
    {
      protected void setValues(PreparedStatement paramAnonymousPreparedStatement, LobCreator paramAnonymousLobCreator)
        throws SQLException, DataAccessException
      {
        paramAnonymousLobCreator.setBlobAsBytes(paramAnonymousPreparedStatement, 1, paramHisTradeCommodityMsg.getUploading());
        paramAnonymousPreparedStatement.setString(2, paramHisTradeCommodityMsg.getUploadingName());
        paramAnonymousPreparedStatement.setLong(3, paramHisTradeCommodityMsg.getTradeCommodityMsgId());
      }
    });
  }
  
  public void add(HisTradeCommodityMsg paramHisTradeCommodityMsg)
  {
    String str = "insert into Z_H_TradeCommodityMsg (tradeCommodityMsgId,clearDate,breedId,commodityProperties,commodityName,deliveryDay,quality,unitVolume,effectiveDays,uploadingName,deliveryPlace,note,expandProperty) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
    Object[] arrayOfObject = { Long.valueOf(paramHisTradeCommodityMsg.getTradeCommodityMsgId()), paramHisTradeCommodityMsg.getClearDate(), Long.valueOf(paramHisTradeCommodityMsg.getBreedId()), paramHisTradeCommodityMsg.getCommodityProperties(), paramHisTradeCommodityMsg.getCommodityName(), Integer.valueOf(paramHisTradeCommodityMsg.getDeliveryDay()), paramHisTradeCommodityMsg.getQuality(), Double.valueOf(paramHisTradeCommodityMsg.getUnitVolume()), Integer.valueOf(paramHisTradeCommodityMsg.getEffectiveDays()), paramHisTradeCommodityMsg.getUploadingName(), paramHisTradeCommodityMsg.getDeliveryPlace(), paramHisTradeCommodityMsg.getNote(), paramHisTradeCommodityMsg.getExpandProperty() };
    int[] arrayOfInt = { 2, 93, 2, 12, 12, 2, 12, 2, 2, 12, 12, 12, 12 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public List getObject(long paramLong)
  {
    String str = "select * from Z_H_TradeCommodityMsg where TradeCommodityMsgId=" + paramLong + "";
    List localList = queryBySQL(str, null, null, new CommonRowMapper(new HisTradeCommodityMsg()));
    return localList;
  }
}
