package umc.kkijuk.server.career.domain;

public enum ProjectType {
    ON_CAMPUS("교내"),
    OFF_CAMPUS("교외"),
    OTHER("기타");

    private final String description;

    ProjectType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
