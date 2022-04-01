package be.ugent.flash.db;

import java.sql.SQLException;

public class DataAccessException extends SQLException {
    public DataAccessException(String reason, Throwable cause) {
        super(reason, cause);
    }
}
