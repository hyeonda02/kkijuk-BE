package umc.kkijuk.server.common.domian.exception;

public class RecruitTagAlreadyExistException extends RuntimeException {
    public RecruitTagAlreadyExistException(String tag) {
        super("RecruitTag [" + tag + "] already exists");
    }
}
