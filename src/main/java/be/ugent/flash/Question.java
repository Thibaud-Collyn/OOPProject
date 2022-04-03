package be.ugent.flash;

public record Question(int questionId, String title, String textPart, byte[] imagePart, String questionType, String correctAnswer) {

}
