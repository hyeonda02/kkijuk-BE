package umc.kkijuk.server.member.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmailAddressDto {
    private String email;

    @Builder
    public EmailAddressDto(String email) {
        this.email = email;
    }
}
