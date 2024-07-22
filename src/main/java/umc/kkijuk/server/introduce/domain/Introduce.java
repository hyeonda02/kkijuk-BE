package umc.kkijuk.server.introduce.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Entity
@Table(name="introduce")
@Getter
public class Introduce {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
