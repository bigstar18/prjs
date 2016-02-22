package org.springframework.jdbc.core;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import org.springframework.util.Assert;

public class RowMapperResultSetExtractor
  implements ResultSetExtractor
{
  private final RowMapper rowMapper;
  private final int rowsExpected;
  
  public RowMapperResultSetExtractor(RowMapper rowMapper)
  {
    this(rowMapper, 0);
  }
  
  public RowMapperResultSetExtractor(RowMapper rowMapper, int rowsExpected)
  {
    Assert.notNull(rowMapper, "RowMapper is required");
    this.rowMapper = rowMapper;
    this.rowsExpected = rowsExpected;
  }
  
  public Object extractData(ResultSet rs)
    throws SQLException
  {
    List results = new LinkedList();
    int rowNum = 0;
    while (rs.next()) {
      results.add(this.rowMapper.mapRow(rs, rowNum++));
    }
    return results;
  }
}
