package umc.kkijuk.server.common.domian.exception;

public class ConfirmPasswordMismatchException extends RuntimeException {
    public ConfirmPasswordMismatchException() {
        super("Passwords do not match");
    }
}
