package umc.kkijuk.server.login.exception;

public class IncorrectPasswordException extends RuntimeException{

    public IncorrectPasswordException() {
        super("비밀번호가 일치하지 않습니다.");
    }
}
