package umc.kkijuk.server.career.domain;

public enum JobType {
    PART_TIME("아르바이트"),
    INTERNSHIP("인턴"),
    FULL_TIME("정규직"),
    CONTRACT("계약직"),
    FREELANCE("프리랜서");

    private final String description;

    JobType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
