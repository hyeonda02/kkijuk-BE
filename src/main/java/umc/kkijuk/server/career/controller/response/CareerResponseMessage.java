package umc.kkijuk.server.career.controller.response;

public class CareerResponseMessage {
    public static final String CAREER_CREATE_SUCCESS = "활동 추가가 정상적으로 이루어졌습니다.";
    public static final String CAREER_NOT_FOUND ="존재하는 활동 Id가 아닙니다.";

    public static final String CAREER_CREATE_FAIL = "활동 추가를 실패했습니다.";
    public static final String CAREER_FOTMAT_INVALID = "올바른 날짜 형식이 아닙니다. YYYY-MM-DD 형식으로 입력해야 합니다.";
    public static final String CAREER_ENDDATE = "현재 진행중이 아니라면, 활동 종료 날짜를 입력해야 합니다.";
    public static final String CAREER_PERIOD_FAIL = "시작 날짜는 종료 날짜보다 앞에 있어야 합니다.";
    public static final String CATEGORY_NOT_FOUND = "존재하는 카테고리 Id가 아닙니다.";
    public static final String CAREER_DELETE_SUCCESS = "활동 삭제가 정상적으로 이루어졌습니다.";
    public static final String CAREER_DELETE_FAIL = "활동 삭제를 실패했습니다.";


}
