package be.ugent.flash.db;

public interface PartsDAO {

//    Add a part of a multiple choice question based on the id of that question
    int addPart(int id, String text) throws DataAccessException;

//    Update an existing part based on it's id
    void updatePart(int id, String text) throws DataAccessException;

//    Remove existing question based on it's id
    void removePart(int id) throws DataAccessException;

}
