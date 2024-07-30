package umc.kkijuk.server.introduce.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Entity
@Table(name = "master_introduce")
@Getter
@NoArgsConstructor
public class MasterIntroduce {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 24)
    private String oneLiner;
    private String introduction;
    private String motive;
    private String prosAndCons;

    @CreationTimestamp
    private LocalDateTime created_at;

    @UpdateTimestamp
    private LocalDateTime updated_at;

    @Builder
    public MasterIntroduce(String oneLiner, String introduction, String motive, String prosAndCons) {
        this.oneLiner = oneLiner;
        this.introduction = introduction;
        this.motive = motive;
        this.prosAndCons = prosAndCons;
    }

    public String getUpdated_at() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return updated_at != null ? updated_at.format(formatter) : null;
    }

    public void update(String oneLiner, String introduction, String motive, String prosAndCons) {
        this.oneLiner = oneLiner;
        this.introduction = introduction;
        this.motive = motive;
        this.prosAndCons = prosAndCons;
    }

}
