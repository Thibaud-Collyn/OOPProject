package be.ugent.flash.db;
import java.sql.SQLException;

public interface DataAccessContext extends AutoCloseable{

    QuestionsDAO getQuestionsDAO();

    PartsDAO getPartsDAO();

    @Override
    void close() throws DataAccessException;
}
