package umc.kkijuk.server.common.domian.exception;

public class ReviewRecruitNotMatchException extends RuntimeException{
    public ReviewRecruitNotMatchException(long reviewId, long recruitId, long requestedRecruitId) {
        super("Review " + reviewId + "의 Recruit ID " + recruitId + "가 요청한 " + requestedRecruitId + "와 일치하지 않습니다.");
    }
}
