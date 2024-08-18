package umc.kkijuk.server.member.emailauth;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@RequiredArgsConstructor
@Repository
public class MailCertification {
    private final String PREFIX = "email:"; // key값이 중복되지 않도록 상수 선언
    private final int LIMIT_TIME = 3 * 60; // 인증번호 유효 시간

    private final StringRedisTemplate stringRedisTemplate;

    // Redis에 저장
    public void createMailCertification(String email, String certificationNumber) {
        stringRedisTemplate.opsForValue()
                .set(PREFIX + email, certificationNumber, Duration.ofSeconds(LIMIT_TIME));
    }

    // 이메일에 해당하는 인증번호 불러오기
    public String getMailCertification(String email) {
        return stringRedisTemplate.opsForValue().get(PREFIX + email);
    }

    // 인증 완료 시, 인증번호 Redis에서 삭제
    public void deleteMailCertification(String email) {
        stringRedisTemplate.delete(PREFIX + email);
    }

    // Redis에 해당 이메일로 저장된 인증번호가 존재하는지 확인
    public boolean hasKey(String email) {
        return stringRedisTemplate.hasKey(PREFIX + email);
    }
}