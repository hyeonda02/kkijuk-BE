package umc.kkijuk.server.member.emailauth;

import umc.kkijuk.server.member.dto.MemberEmailDto;

public interface MailService {

    public MailCertificationResponse sendMailPasswordReset(String email);
    public MailCertificationResponse sendMailJoin(String email);

    Boolean verifyMail(MailCertificationDto requestDto);

    Boolean confirmDupEmail(MemberEmailDto memberEmailDto);
}
