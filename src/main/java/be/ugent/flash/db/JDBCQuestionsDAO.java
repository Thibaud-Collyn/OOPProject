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
        try (PreparedStatement ps = prepare("INSERT INTO questions(title, text_part, image_part, question_type, correct_answer) VALUES (?, ?, ?, ?, ?)")){
            ps.setString(1, title);
            ps.setString(2, textPart);
            ps.setBytes(3, imagePart);
            ps.setString(4, questionType);
            ps.setString(5, correctAnswer);
            ps.executeUpdate();
            return getQuestion(ps.getGeneratedKeys().getInt(1));
        } catch (SQLException ex) {
            throw new DataAccessException("Could not remove question.", ex);
        }
    }

    @Override
    public void updateGeneralQuestion(int id, String title, String textPart, byte[] imagePart) throws DataAccessException {
        try (PreparedStatement ps = prepare("UPDATE questions SET title = ?, text_part = ?, image_part = ? WHERE question_id = ?")){
            ps.setString(1, title);
            ps.setString(2, textPart);
            ps.setBytes(3, imagePart);
            ps.setInt(4, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException("Could not remove question.", ex);
        }
    }

    @Override
    public void updateCorrectAnswer(int qId, String correctAnswer) throws DataAccessException {
        try (PreparedStatement ps = prepare("UPDATE questions SET correct_answer = ? WHERE question_id = ?")){
            ps.setString(1, correctAnswer);
            ps.setInt(2, qId);
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException("Could not remove question.", ex);
        }
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
