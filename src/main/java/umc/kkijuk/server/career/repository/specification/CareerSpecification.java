package umc.kkijuk.server.career.repository.specification;

import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import umc.kkijuk.server.career.domain.Career;
import umc.kkijuk.server.career.dto.CareerRequestDto;
import umc.kkijuk.server.careerdetail.domain.CareerDetail;
import umc.kkijuk.server.careerdetail.domain.mapping.CareerTag;
import umc.kkijuk.server.tag.domain.Tag;

import java.util.ArrayList;
import java.util.List;

public class CareerSpecification {
    public static Specification<Career> filterCareers(CareerRequestDto.SearchCareerDto request){
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicateList = new ArrayList<>();
            Join<Career, CareerDetail> careerDetailJoin = root.join("careerDetailList", JoinType.LEFT);
            Join<CareerDetail, CareerTag> careerTagJoin = careerDetailJoin.join("careerTagList", JoinType.LEFT);
            Join<CareerTag, Tag> tagJoin = careerTagJoin.join("tag", JoinType.LEFT);


            if(request.getSearch()!=null && !request.getSearch().isEmpty()){
                String searchTerm = "%" + request.getSearch() +"%";

                if(request.getCareerName()){
                    Predicate careerNamePredicate = criteriaBuilder.like(root.get("name"), searchTerm);
                    Predicate summaryPredicate = criteriaBuilder.like(root.get("summary"), searchTerm);
                    Predicate combinedPredicate = criteriaBuilder.or(careerNamePredicate,summaryPredicate);
                    predicateList.add(combinedPredicate);

                }
                if(request.getCareerDetail()){
                    Predicate careerDetailTitlePredicate = criteriaBuilder.like(careerDetailJoin.get("title"), searchTerm);
                    predicateList.add(careerDetailTitlePredicate);
                }

                if (request.getTag()) {
                    Predicate tagNamePredicate = criteriaBuilder.like(tagJoin.get("name"), searchTerm);
                    predicateList.add(tagNamePredicate);
                }

            }
            if (request.getStartDate() != null) {
                Predicate startDateWithRange = criteriaBuilder.between(root.get("startdate"), request.getStartDate(), request.getEndDate());
                Predicate endDateWithRange = criteriaBuilder.between(root.get("enddate"), request.getStartDate(), request.getEndDate());

                Predicate periodContainsRange = criteriaBuilder.and(
                        criteriaBuilder.lessThanOrEqualTo(root.get("startdate"), request.getStartDate()),
                        criteriaBuilder.greaterThanOrEqualTo(root.get("enddate"), request.getEndDate())
                );

                Predicate datePradicate = criteriaBuilder.or(startDateWithRange, endDateWithRange, periodContainsRange);
                predicateList.add(datePradicate);
            }

            if (request.getSort() != null) {
                if(request.getSort().equalsIgnoreCase("desc")){
                    query.orderBy(criteriaBuilder.desc(root.get("enddate")));
                }else{
                    query.orderBy(criteriaBuilder.asc(root.get("enddate")));
                }
            }


            if (predicateList.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));

        };
    }
}
