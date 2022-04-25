package be.ugent.flash.db;

import be.ugent.flash.Question;
import java.sql.SQLException;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.Queue;

public interface QuestionsDAO {

//    Maakt een nieuwe vraag(question) aan met een optionele foto(image) en een onveranderlijk type
    Question createQuestion(String title, String textPart, byte[] imagePart, String questionType, String correctAnswer) throws DataAccessException;

//    Update een bestaande vraag met bepaalde question_id
    void updateQuestion(int id, String title, String textPart, byte[] imagePart, String correctAnswer) throws DataAccessException;

//    Verwijdert een bestaande vraag met bepaalde question_id
    void removeQuestion(int id) throws DataAccessException;

//   Return een vraag op basis van zijn question_id
    Question getQuestion(int id) throws DataAccessException;

//   Return een array list met alle vragen in de databank(gesorteerd op question_id)
    ArrayList<Question> getAllQuestions() throws DataAccessException;

}
