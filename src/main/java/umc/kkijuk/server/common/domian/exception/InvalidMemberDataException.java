package umc.kkijuk.server.common.domian.exception;

public class InvalidMemberDataException extends RuntimeException{
    public InvalidMemberDataException() {super("멤버 정보는 null일 수 없습니다.");
    }
}
