package be.ugent.flash.db;

import java.sql.Connection;

public class JDBCPartsDAO extends JDBCAbstractDAO implements PartsDAO{

    public JDBCPartsDAO(Connection connection) {
        super(connection);
    }

    @Override
    public int addPart(int id, String text) throws DataAccessException {
        //TODO: implement
        return 0;
    }

    @Override
    public void updatePart(int id, String text) throws DataAccessException {
        //TODO: implement
    }

    @Override
    public void removePart(int id) throws DataAccessException {
        //TODO: implement
    }
}
