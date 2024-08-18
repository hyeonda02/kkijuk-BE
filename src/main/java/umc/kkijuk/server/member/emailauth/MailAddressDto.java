package umc.kkijuk.server.member.emailauth;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MailAddressDto {
    @NotNull
    private String email;

    @Builder
    public MailAddressDto(String email) {
        this.email = email;
    }
}
