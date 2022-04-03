package be.ugent.flash.db;

import be.ugent.flash.Question;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class JDBCQuestionsDAO extends JDBCAbstractDAO implements QuestionsDAO{

    public JDBCQuestionsDAO(Connection connection) {
        super(connection);
    }

    @Override
    public Question createQuestion(String title, String textPart, byte[] imagePart, String questionType, String correctAnswer) throws DataAccessException {
        //TODO: implement
        return null;
    }

    @Override
    public void updateQuestion(int id, String title, String textPart, byte[] imagePart, String correctAnswer) throws DataAccessException {
        //TODO: implement
    }

    @Override
    public void removeQuestion(int id) throws DataAccessException {
        try (PreparedStatement ps = prepare("DELETE FROM questions WHERE question_id = ?")){
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException("Could not remove question.", ex);
        }
    }

    //TODO: getQuestion might not be necessary, implementation not yet complete
    @Override
    public Question getQuestion(int id) throws DataAccessException {
        try (PreparedStatement ps = prepare("SELECT title, text_part, image_part, question_type, correct_answer FROM questions WHERE question_id = ?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    return new Question(id,
                            rs.getString("title"),
                            rs.getString("text_part"),
                            rs.getBytes("image_part"),
                            rs.getString("question_type"),
                            rs.getString("correct_answer"));
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException("Could not retrieve question with given id.", ex);
        }
        return null;
    }

    @Override
    public ArrayList<Question> getAllQuestions() throws DataAccessException {
        try (PreparedStatement ps = prepare("SELECT question_id, title, text_part, image_part, question_type, correct_answer FROM questions ORDER BY question_id")) {
            ResultSet rs = ps.executeQuery();
            ArrayList<Question> questions = new ArrayList<>();
            while (rs.next()) {
                Question question = new Question(
                        rs.getInt("question_id"),
                        rs.getString("title"),
                        rs.getString("text_part"),
                        rs.getBytes("image_part"),
                        rs.getString("question_type"),
                        rs.getString("correct_answer"));
                questions.add(question);
            }
            return questions;
        } catch (SQLException ex) {
            throw new DataAccessException("Could not retrieve all questions.", ex);
        }
    }
}
