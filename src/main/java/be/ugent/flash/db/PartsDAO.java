package be.ugent.flash.db;

import be.ugent.flash.ImagePart;
import be.ugent.flash.Part;

import java.util.ArrayList;

public interface PartsDAO {

//    Add a part of a multiple choice question based on the id of that question
    int addPart(int id, String text) throws DataAccessException;

//    Update an existing part based on it's id
    void updatePart(int id, String text) throws DataAccessException;

//    Remove existing question based on it's id
    void removePart(int id) throws DataAccessException;

//    Returns an array list with all parts of a question sorted by partId
    ArrayList<Part> getParts(int qId) throws DataAccessException;

//    Returns an array list with all image parts of a question sorted by partId
    ArrayList<ImagePart> getImageParts(int qId) throws DataAccessException;

}
