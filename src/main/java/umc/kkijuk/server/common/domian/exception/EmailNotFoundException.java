package umc.kkijuk.server.common.domian.exception;

public class EmailNotFoundException extends RuntimeException{
    public EmailNotFoundException() {super("이메일을 찾을 수 없습니다.");
    }
}
