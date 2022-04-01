package be.ugent.flash.db;

import be.ugent.flash.Question;
import java.sql.SQLException;

import java.sql.Blob;

public interface QuestionsDAO {

//    creates a new question in the database with optional image and fixed question type(not update-able)
    Question createQuestion(String title, String textPart, byte[] imagePart, String questionType, String correctAnswer) throws DataAccessException;

//    Updates an existing question
    void updateQuestion(int id, String title, String textPart, byte[] imagePart, String correctAnswer) throws DataAccessException;

//    Removes an existing question
    void removeQuestion(int id) throws DataAccessException;

//   returns a question object based on it's id
    Question getQuestion(int id) throws DataAccessException;

}
