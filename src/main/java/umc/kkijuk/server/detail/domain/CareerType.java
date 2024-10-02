package umc.kkijuk.server.detail.domain;

public enum CareerType {
    ACTIVITY("대외활동"),
    PROJECT("프로젝트"),
    EDU("교육"),
    EMP("경력"),
    CIRCLE("동아리"),
    COM("대회");

    private final String description;

    CareerType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
