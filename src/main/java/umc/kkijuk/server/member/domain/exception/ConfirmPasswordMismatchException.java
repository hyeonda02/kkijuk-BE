package umc.kkijuk.server.member.domain.exception;

public class ConfirmPasswordMismatchException extends RuntimeException {
    public ConfirmPasswordMismatchException() {
        super("Passwords do not match");
    }
}
