package be.ugent.flash.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCDataAccessProvider implements DataAccessProvider{
    private final String connection;
    public JDBCDataAccessProvider(String connection){
        this.connection = connection;
    }
    @Override
    public DataAccessContext getDataAccessContext() throws DataAccessException {
        try {
            return new JDBCDataAccessContext(getConnection());
        } catch (SQLException ex) {
            throw new DataAccessException("Kon geen verbinding maken met databank", ex);
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(connection);
    }
}
