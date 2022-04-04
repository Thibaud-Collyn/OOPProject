package be.ugent.flash;

import be.ugent.flash.db.*;

import java.sql.SQLException;
import java.util.ArrayList;

public class ViewerManager {
    private ArrayList<Question> questions = new ArrayList<>();
    private DataAccessProvider dataAccessProvider;

    public ViewerManager(String connection) {
        try {
            dataAccessProvider = new JDBCDataAccessProvider(connection);
            questions = dataAccessProvider.getDataAccessContext().getQuestionsDAO().getAllQuestions();
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }
}
