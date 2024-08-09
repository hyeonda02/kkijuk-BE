package umc.kkijuk.server.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import umc.kkijuk.server.member.emailauth.MailAddressDto;
import umc.kkijuk.server.member.emailauth.MailCertificationDto;
import umc.kkijuk.server.member.emailauth.MailCertificationResponse;
import umc.kkijuk.server.member.emailauth.MailServiceImpl;

@Tag(name = "auth", description = "이메일 인증 API")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class EmailAuthController {

    private final MailServiceImpl mailService;

    @Operation(
            summary = "이메일 인증번호 요청",
            description = "이메일 인증정보를 요청합니다.")
    @PostMapping
    public ResponseEntity<MailCertificationResponse> mailSend(@RequestBody @Valid MailAddressDto mailAddressDto) {

        MailCertificationResponse mailCertificationResponse = mailService.sendMail(mailAddressDto.getEmail());
        return ResponseEntity.ok(mailCertificationResponse);
    }

    @Operation(
            summary = "이메일 인증번호 인증",
            description = "이메일 인증번호를 인증합니다.")
    @PostMapping("/confirm")
    public ResponseEntity<Boolean> confirmMailNumber(@RequestBody @Valid MailCertificationDto mailCertificationDto){
        return ResponseEntity.ok(mailService.verifyMail(mailCertificationDto));
    }
}
