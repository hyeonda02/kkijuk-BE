package umc.kkijuk.server.member.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MyPagePasswordAuthDto {

    @NotNull
    private String currentPassword;

    public MyPagePasswordAuthDto(String currentPassword) {
        this.currentPassword = currentPassword;
    }
}
