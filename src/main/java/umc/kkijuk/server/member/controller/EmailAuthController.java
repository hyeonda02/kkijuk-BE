//package umc.kkijuk.server.member.controller;
//
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import umc.kkijuk.server.member.controller.response.EmailAuthResponse;
//import umc.kkijuk.server.member.dto.EmailAddressDto;
//import umc.kkijuk.server.member.emailauth.MailService;
//import umc.kkijuk.server.member.service.MemberService;
//
//@Tag(name = "auth", description = "이메일 인증 API")
//@RestController
//@RequestMapping("/auth")
//@RequiredArgsConstructor
//public class EmailAuthController {
//
//    private final MemberService memberService;
//    private final MailService mailService;
//    private int authNumber;
//
//
//    @Operation(
//            summary = "회원가입용 이메일 인증번호 요청",
//            description = "회원가입을 할 때 이메일에 인증번호를 요청하여 전송합니다.")
//    @PostMapping
//    public ResponseEntity<EmailAuthResponse> emailAuthSend(@RequestBody @Valid EmailAddressDto emailAddressDto) {
//
//        authNumber = mailService.sendMail(emailAddressDto.getEmail());
//        EmailAuthResponse emailAuthResponse = memberService.getEmailAuth(emailAddressDto, authNumber);
//
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body(emailAuthResponse);
//    }
//

//    @Operation(
//            summary = "이메일 인증번호 확인",
//            description = "이메일 인증번호의 일치 여부를 확인합니다.")
//    @PostMapping("/confirm")
//    public ResponseEntity<?> emailAuthCheck(@RequestBody @Valid EmailAuthCheckDto emailAuthCheckDto) {
//
//        boolean isMatch = userNumber.equals(String.valueOf(authNumber));
//
//        return ResponseEntity.ok(isMatch);
//    }
//}
