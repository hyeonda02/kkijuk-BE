package umc.kkijuk.server.career.controller.exception;

public class CareerNotFoundException extends RuntimeException {
    public CareerNotFoundException(String message) {
        super(message);
    }
}
