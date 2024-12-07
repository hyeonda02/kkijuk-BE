package umc.kkijuk.server.detail.domain;

public enum CareerType {
    ACTIVITY(1,"대외활동"),
    PROJECT(2,"프로젝트"),
    EDU(3,"교육"),
    EMP(4,"경력"),
    CIRCLE(5,"동아리"),
    COM(6,"대회"),
    ETC(7,"기타");

    private final int id;
    private final String description;

    CareerType(int id,String description) {
        this.id = id;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
    public int getId() {return id;}
}
