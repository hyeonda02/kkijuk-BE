package umc.kkijuk.server.common.domian.exception;

public class ReviewRecruitMismatchException extends RuntimeException{
    public ReviewRecruitMismatchException(long recruitId, long reviewId) {
        super("Recruit "+ recruitId + "에서 Review " + reviewId + "를 찾을수 없습니다.");
    }
}
