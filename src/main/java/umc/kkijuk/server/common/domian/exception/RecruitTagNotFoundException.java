package umc.kkijuk.server.common.domian.exception;

public class RecruitTagNotFoundException extends RuntimeException {
    public RecruitTagNotFoundException(String tag) {
        super("RecruitTag [" + tag + "] not found");
    }
}
