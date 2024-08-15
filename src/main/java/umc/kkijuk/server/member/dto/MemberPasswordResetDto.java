package umc.kkijuk.server.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberPasswordResetDto {
    @NotNull @Email
    private String email;
    @NotNull
    private String newPassword;
    @NotNull
    private String newPasswordConfirm;

    @Builder
    public MemberPasswordResetDto(String email, String newPassword, String newPasswordConfirm) {
        this.email = email;
        this.newPassword = newPassword;
        this.newPasswordConfirm = newPasswordConfirm;
    }
}
