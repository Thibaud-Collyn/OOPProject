package be.ugent.flash.db;

public interface DataAccessContext extends AutoCloseable{

    QuestionsDAO getQuestionsDAO();

    PartsDAO getPartsDAO();

    @Override
    void close() throws Exception;
}
