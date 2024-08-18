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
    public static Specification<Career> filterCareers(CareerRequestDto.SearchCareerDto request, Long memberId){
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicateList = new ArrayList<>();

            Predicate memberPredicate = criteriaBuilder.equal(root.get("memberId"),memberId);
            predicateList.add(memberPredicate);

            if(request.getSearch()!=null && !request.getSearch().isEmpty()){
                String searchTerm = "%" + request.getSearch() +"%";

                Predicate careerNamePredicate = criteriaBuilder.like(root.get("name"), searchTerm);
                predicateList.add(careerNamePredicate);

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
    public static Specification<CareerDetail> filterCareerDetails(CareerRequestDto.SearchCareerDto request, Long memberId){
        if (!request.getCareerName() && !request.getCareerDetail() && !request.getTag()) {
            request.setCareerDetail(true);
            request.setCareerName(true);
        }

        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicateList = new ArrayList<>();

            Join<CareerDetail, CareerTag> careerTagJoin = root.join("careerTagList", JoinType.LEFT);
            Join<CareerTag, Tag> tagJoin = careerTagJoin.join("tag", JoinType.LEFT);
            Join<CareerDetail, Career> careerJoin = root.join("career", JoinType.LEFT);

            Predicate memberPredicate = criteriaBuilder.equal(careerJoin.get("memberId"), memberId);
            predicateList.add(memberPredicate);

            if(request.getSearch() != null && !request.getSearch().isEmpty()){
                String searchTerm = "%" + request.getSearch() + "%";
                List<Predicate> orPredicateList = new ArrayList<>();

                if(request.getCareerDetail()){
                    Predicate careerDetailTitlePredicate = criteriaBuilder.like(root.get("title"), searchTerm);
                    orPredicateList.add(careerDetailTitlePredicate);
                }

                if(request.getCareerName()){
                    Predicate careerNamePredicate = criteriaBuilder.like(careerJoin.get("name"), searchTerm);
                    orPredicateList.add(careerNamePredicate);
                }

                if(request.getTag()){
                    Predicate tagNamePredicate = criteriaBuilder.like(tagJoin.get("name"), searchTerm);
                    orPredicateList.add(tagNamePredicate);
                }

                if(!orPredicateList.isEmpty()) {
                    Predicate orPredicate = criteriaBuilder.or(orPredicateList.toArray(new Predicate[0]));
                    predicateList.add(orPredicate);
                }
            }

            if (request.getStartDate() != null) {
                Predicate startDateWithRange = criteriaBuilder.between(root.get("startDate"), request.getStartDate(), request.getEndDate());
                Predicate endDateWithRange = criteriaBuilder.between(root.get("endDate"), request.getStartDate(), request.getEndDate());

                Predicate periodContainsRange = criteriaBuilder.and(
                        criteriaBuilder.lessThanOrEqualTo(root.get("startDate"), request.getStartDate()),
                        criteriaBuilder.greaterThanOrEqualTo(root.get("endDate"), request.getEndDate())
                );

                Predicate datePradicate = criteriaBuilder.or(startDateWithRange, endDateWithRange, periodContainsRange);
                predicateList.add(datePradicate);
            }

            if (request.getSort() != null) {
                if(request.getSort().equalsIgnoreCase("desc")){
                    query.orderBy(criteriaBuilder.desc(root.get("startDate")));
                }else{
                    query.orderBy(criteriaBuilder.asc(root.get("startDate")));
                }
            }

            if(predicateList.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
        };
    }
}
