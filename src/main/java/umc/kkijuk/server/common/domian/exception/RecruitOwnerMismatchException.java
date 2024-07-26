package umc.kkijuk.server.common.domian.exception;

public class RecruitOwnerMismatchException extends RuntimeException {
    public RecruitOwnerMismatchException() {
        super("해당 공고에 대한 소유권을 가지고 있지 않습니다.");
    }
}
