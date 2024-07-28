package umc.kkijuk.server.member.controller.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MemberInfoResponse {
    private String email;
    private String name;
    private String phoneNumber;
    private LocalDate birthDate;

    public MemberInfoResponse() {
    }

    public MemberInfoResponse(String email, String name, String phoneNumber, LocalDate birthDate) {
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
    }
}
