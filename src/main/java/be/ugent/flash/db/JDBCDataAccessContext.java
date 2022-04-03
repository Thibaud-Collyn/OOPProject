package be.ugent.flash.db;

import java.sql.Connection;
import java.sql.SQLException;

public class JDBCDataAccessContext implements DataAccessContext {
    private final Connection connection;

    public JDBCDataAccessContext(Connection connection) {
        this.connection = connection;
    }

    @Override
    public QuestionsDAO getQuestionsDAO() {
        return new JDBCQuestionsDAO(connection);
    }

    @Override
    public PartsDAO getPartsDAO() {
        return new JDBCPartsDAO(connection);
    }

    @Override
    public void close() throws DataAccessException {
        try {
            connection.close();
        } catch (SQLException ex) {
            throw new DataAccessException("Could not close context.", ex);
        }
    }
}
