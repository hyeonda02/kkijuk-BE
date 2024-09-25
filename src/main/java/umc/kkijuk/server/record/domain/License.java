package umc.kkijuk.server.record.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.kkijuk.server.career.domain.base.BaseEntity;

import java.time.YearMonth;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="license")
public class License extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "record_id", nullable = false)
    private Record record;

    @NotNull
    private String licenseName;
    @Size(max = 15)
    private String administer;
    @Size(max = 30)
    private String licenseNumber;
    private YearMonth acquireDate;


    public void changeLicenseInfo(String licenseName, String administer, String licenseNumber, YearMonth acquireDate){
        this.licenseName = licenseName;
        this.administer = administer;
        this.licenseNumber = licenseNumber;
        this.acquireDate = acquireDate;
    }

}
