package be.ugent.flash.db;

import java.sql.Connection;

public class JDBCDataAccessContext implements DataAccessContext {
    private Connection connection;

    public JDBCDataAccessContext(Connection connection) {
        this.connection = connection;
    }

    @Override
    public QuestionsDAO getQuestionsDAO() {
        return null;
    }

    @Override
    public PartsDAO getPartsDAO() {
        return null;
    }

    @Override
    public void close() throws DataAccessException {

    }
}
