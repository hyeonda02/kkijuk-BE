package umc.kkijuk.server.member.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import umc.kkijuk.server.member.converter.StringListConverter;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

@Entity
@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @NotNull
    private String email;

    @NotNull
    private String name;

    @NotNull
    private String phoneNumber;

    @NotNull
    private LocalDate birthDate;

    @NotNull
    private String password;

    @Convert(converter = StringListConverter.class)
    private List<String> field  = new ArrayList<>();

    @NotNull
    private Boolean marketingAgree;

    @NotNull
    @Enumerated(EnumType.STRING)
    private State userState;

    public Member(String email, String name, String phoneNumber, LocalDate birthDate, String password, Boolean marketingAgree, State userState) {
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.password = password;
        this.marketingAgree = marketingAgree;
        this.userState = userState;
    }
}

