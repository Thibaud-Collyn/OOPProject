package be.ugent.flash.db;

public interface DataAccessProvider {
    public DataAccessContext getDataAccessContext() throws DataAccessException;
}
