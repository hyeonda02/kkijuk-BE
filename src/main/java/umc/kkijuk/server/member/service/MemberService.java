package umc.kkijuk.server.member.service;

import umc.kkijuk.server.member.controller.response.MemberInfoResponse;
import umc.kkijuk.server.member.domain.Member;
import umc.kkijuk.server.member.dto.*;

import java.util.List;

public interface MemberService {
    Member getById(Long memberId);
    Member join(MemberJoinDto memberJoinDto);
    MemberInfoResponse getMemberInfo(Long memberId);
    List<String> getMemberField(Long memberId);
    Member updateMemberField(Long memberId, MemberFieldDto memberFieldDto);
    Member updateMemberInfo(Long memberId, MemberInfoChangeDto memberInfoChangeDto);
    Member changeMemberPassword(Long memberId, MemberPasswordChangeDto memberPasswordChangeDto);
    Member myPagePasswordAuth(Long memberId, MyPagePasswordAuthDto myPagePasswordAuthDto);
}
