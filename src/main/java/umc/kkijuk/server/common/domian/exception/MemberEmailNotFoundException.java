package umc.kkijuk.server.common.domian.exception;

public class MemberEmailNotFoundException extends RuntimeException{
    public MemberEmailNotFoundException() {super("이메일로 등록된 사용자를 찾을 수 없습니다.");
    }
}
