package umc.kkijuk.server.common.domian.exception;

public class ReviewRecruitNotMatchException extends RuntimeException{
    public ReviewRecruitNotMatchException(long recruitId, long reviewId) {
        super("Recruit "+ recruitId + "에서 Review " + reviewId + "를 찾을수 없습니다.");
    }
}
