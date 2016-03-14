package gnnt.MEBS.vendue.manage.right;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class CheckRight {
	public String JNDI = null;

	public long addRight(String[] paramArrayOfString) {
		if ((paramArrayOfString == null) || (paramArrayOfString.length <= 0)) {
			return 0L;
		}
		if (paramArrayOfString.length == 1) {
			return Long.parseLong(paramArrayOfString[0]);
		}
		long l = Long.parseLong(paramArrayOfString[0]);
		for (int i = 1; i < paramArrayOfString.length; i++) {
			l = Long.parseLong(String.valueOf(l)) | Long.parseLong(paramArrayOfString[i]);
		}
		return l;
	}

	public boolean checkRight(long paramLong, int paramInt) {
		Connection localConnection = null;
		PreparedStatement localPreparedStatement = null;
		ResultSet localResultSet = null;
		try {
			InitialContext localInitialContext = new InitialContext();
			Context localContext = (Context) localInitialContext.lookup("java:/comp/env");
			DataSource localDataSource = (DataSource) localContext.lookup(this.JNDI);
			localConnection = localDataSource.getConnection();
			StringBuffer localStringBuffer = new StringBuffer("select marknum from v_accessright where ");
			localStringBuffer.append("id=" + paramInt + "");
			localPreparedStatement = localConnection.prepareStatement(localStringBuffer.toString());
			localResultSet = localPreparedStatement.executeQuery();
			long l = 0L;
			if (localResultSet.next()) {
				l = localResultSet.getLong("marknum");
			}
			localResultSet.close();
			localPreparedStatement.close();
			if ((paramLong & l) > 0L) {
				return true;
			}
			boolean bool2 = false;
			return bool2;
		} catch (Exception localException1) {
			localException1.printStackTrace();
			boolean bool1 = false;
			return bool1;
		} finally {
			if (localResultSet != null) {
				try {
					localResultSet.close();
				} catch (Exception localException11) {
				}
			}
			if (localPreparedStatement != null) {
				try {
					localPreparedStatement.close();
				} catch (Exception localException12) {
				}
				localPreparedStatement = null;
			}
			try {
				localConnection.close();
			} catch (Exception localException13) {
			}
			localConnection = null;
		}
	}
}
