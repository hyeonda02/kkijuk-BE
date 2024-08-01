package umc.kkijuk.server.common.domian.exception;

public class OwnerMismatchException extends RuntimeException{
    public OwnerMismatchException() {
        super("해당 활동에 대한 소유권을 갖고 있지 않습니다.");
    }

}
