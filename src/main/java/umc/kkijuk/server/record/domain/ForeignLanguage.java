package umc.kkijuk.server.record.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.kkijuk.server.common.domian.base.BaseEntity;

import java.time.YearMonth;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="foreign_language")
public class ForeignLanguage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "record_id", nullable = false)
    private Record record;

    @NotNull
    @Size(max = 30)
    private String foreignName;
    @Size(max = 15)
    private String administer;
    @Size(max = 30)
    private String foreignNumber;
    private YearMonth acquireDate;
    @Size(max = 10)
    private String foreignRank;

    public void changeForeignInfo(String foreignName, String administer, String foreignNumber,
                                  YearMonth acquireDate, String foreignRank){
        this.foreignName = foreignName;
        this.administer = administer;
        this.foreignNumber = foreignNumber;
        this.acquireDate = acquireDate;
        this.foreignRank = foreignRank;
    }

}
