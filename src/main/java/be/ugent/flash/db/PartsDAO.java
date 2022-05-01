package be.ugent.flash.db;

import be.ugent.flash.ImagePart;
import be.ugent.flash.Part;

import java.util.ArrayList;

public interface PartsDAO {

//    Voegt een part(met text) toe aan een multiple choice vraag op basis van de question_id
    void addTextPart(int id, String text) throws DataAccessException;

//    Voegt een part(met afbeelding) toe aan een multiple choice vraag op basis van question_id
    void addImagePart(int id, byte[] image) throws DataAccessException;

//    Update een part op basis van zijn part_id
    void updatePart(int id, String text) throws DataAccessException;

//    Verwijdert parts van een vraag op basis van de question_id
    void removeParts(int qId) throws DataAccessException;

//    Return een array list met alle parts van een vraag met bepaalde question_id gesorteerd op part_id's
    ArrayList<Part> getParts(int qId) throws DataAccessException;

//    Return een array list met alle image parts van een vraag met bepaalde question_id gesorteerd op part_id's
    ArrayList<ImagePart> getImageParts(int qId) throws DataAccessException;

}
