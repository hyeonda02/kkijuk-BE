package umc.kkijuk.server.common.domian.exception;

public class RecordNotFoundException extends RuntimeException{
    public RecordNotFoundException() {
        super("해당 유저의 이력서가 존재하지 않습니다.");
    }
}
