package umc.kkijuk.server.member.service;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import umc.kkijuk.server.member.domain.Member;
import umc.kkijuk.server.member.domain.State;
import umc.kkijuk.server.member.repository.MemberJpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Test
    public void 유저정보db저장및조회() throws Exception {
        //given
        Member member1 = new Member("asd@naver.com", "홍길동", "010-7444-1768", LocalDate.now(), "passwordTest", Boolean.TRUE, State.ACTIVATE);
        //when
        Long savedId = memberService.join(member1);
        Optional<Member> member2 = memberJpaRepository.findById(savedId);
        //then
        assertEquals(member1, member2.get());
    }
}
