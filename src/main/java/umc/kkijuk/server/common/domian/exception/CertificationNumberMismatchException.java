package umc.kkijuk.server.common.domian.exception;

public class CertificationNumberMismatchException extends RuntimeException{
    public CertificationNumberMismatchException() {super("이메일 인증정보가 일치하지 않습니다.");
    }
}
