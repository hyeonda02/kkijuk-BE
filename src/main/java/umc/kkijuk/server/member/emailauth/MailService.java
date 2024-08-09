package umc.kkijuk.server.member.emailauth;

import umc.kkijuk.server.member.dto.MemberEmailDto;

public interface MailService {

    MailCertificationResponse sendMail(String mail);

    Boolean verifyMail(MailCertificationDto requestDto);

    Boolean confirmDupEmail(MemberEmailDto memberEmailDto);
}
