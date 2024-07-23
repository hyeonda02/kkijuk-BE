package umc.kkijuk.server.career.controller.exception;

public class CareerValidationException extends RuntimeException{
    private String field;

    public CareerValidationException(String message, String field) {
        super(message);
        this.field = field;
    }
}
