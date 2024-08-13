package umc.kkijuk.server.career.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.kkijuk.server.career.domain.Category;
import umc.kkijuk.server.career.dto.CareerResponseDto;
import umc.kkijuk.server.career.repository.specification.CareerSpecification;
import umc.kkijuk.server.careerdetail.domain.CareerDetail;
import umc.kkijuk.server.careerdetail.repository.CareerDetailRepository;
import umc.kkijuk.server.common.domian.exception.OwnerMismatchException;
import umc.kkijuk.server.common.domian.exception.CareerValidationException;
import umc.kkijuk.server.career.controller.response.CareerGroupedByResponse;
import umc.kkijuk.server.career.controller.response.CareerResponseMessage;
import umc.kkijuk.server.career.domain.Career;
import umc.kkijuk.server.career.dto.CareerRequestDto;
import umc.kkijuk.server.career.dto.converter.CareerConverter;
import umc.kkijuk.server.career.repository.CareerRepository;
import umc.kkijuk.server.career.repository.CategoryRepository;
import umc.kkijuk.server.common.domian.exception.ResourceNotFoundException;
import umc.kkijuk.server.member.domain.Member;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CareerServiceImpl implements CareerService {
    private final CategoryRepository categoryRepository;
    private final CareerRepository careerRepository;
    private final CareerDetailRepository careerDetailRepository;

    @Override
    @Transactional
    public Career createCareer(Member requestMember, CareerRequestDto.CreateCareerDto request) {
        Career career = CareerConverter.toCareer(request, requestMember.getId());
        if(career.getUnknown()){
            career.setEnddate(LocalDate.now());
            career.setYear(LocalDate.now().getYear());
        }
        career.setCategory(categoryRepository.findById(Long.valueOf(request.getCategory())).orElseThrow(() -> new ResourceNotFoundException("category", request.getCategory())));
        career.setYear(parsingYear(request));
        return careerRepository.save(career);
    }
    @Override
    @Transactional
    public void deleteCareer(Member requestMember, Long careerId) {
        Career career = findCareer(careerId).get();
        if(!career.getMemberId().equals(requestMember.getId())){
            throw new OwnerMismatchException();
        }
        careerRepository.delete(career);
    }

    @Override
    @Transactional
    public Career updateCareer(Member requestMember, Long careerId, CareerRequestDto.UpdateCareerDto request) {
        Career career = findCareer(careerId).get();
        if(!career.getMemberId().equals(requestMember.getId())){
            throw new OwnerMismatchException();
        }

        if ( request.getCareerName()!=null && !request.getCareerName().trim().isEmpty() ) {
            career.setName(request.getCareerName());
        }
        if (request.getAlias()!=null && !request.getAlias().trim().isEmpty() ) {
            career.setAlias(request.getAlias());
        }
        if (request.getSummary()!=null){
            career.setSummary(request.getSummary());
        }
        if (request.getStartDate()!=null) {
            career.setStartdate(request.getStartDate());
        }
        if (request.getIsUnknown()!=null || request.getEndDate()!=null ) {
            updateEndDateAndUnknownStatus(career,request.getIsUnknown(),request.getEndDate());
            validatedPeriod(career);
        }
        if(request.getCategory()!=null){
            Category category = categoryRepository.findById(Long.valueOf(request.getCategory())).orElseThrow(() -> new ResourceNotFoundException("Category",request.getCategory()));
            career.setCategory(category);
        }
        return careerRepository.save(career);

    }

    @Override
    public List<? extends CareerGroupedByResponse> getCareerGroupedBy(Member requestMember, String status) {
        List<Career> careers = careerRepository.findAllCareerByMemberId(requestMember.getId()); //동작하는지 확인해야 됨 ( 멤버 아이디 넣음 -> CareerRepository )

        Map<String,List<Career>> groupedCareers;

        if(status.equalsIgnoreCase("category")){
            groupedCareers = careers.stream().collect(Collectors.groupingBy(value -> value.getCategory().getName()));
            return CareerConverter.toCareerGroupedByCategoryDto(groupedCareers);
        } else if (status.equalsIgnoreCase("year")) {
            groupedCareers = careers.stream().collect(Collectors.groupingBy(value -> String.valueOf(value.getYear())));
            return CareerConverter.toCareerGroupedByYearDto(groupedCareers);
        } else {
            throw new IllegalArgumentException(CareerResponseMessage.CAREER_FINDALL_FAIL);
        }
    }

    @Override
    public List<Career> searchCareer(Member requestMember, CareerRequestDto.SearchCareerDto request) {
        Specification<Career> spec = CareerSpecification.filterCareers(request, requestMember.getId());
        return careerRepository.findAll(spec);
    }

    @Override
    public List<CareerResponseDto.CareerSearchDto> searchCareerDetail(Member requestMember, CareerRequestDto.SearchCareerDto request) {
        Specification<CareerDetail> specDetail = CareerSpecification.filterCareerDetails(request, requestMember.getId());
        List<CareerDetail> detailList = careerDetailRepository.findAll(specDetail);

        if(request.getCareerName()){
            Specification<Career> specCareer = CareerSpecification.filterCareers(request, requestMember.getId());
            List<Career> careerList = careerRepository.findAll(specCareer);
            List<Career> careersWithoutDetails = careerList.stream()
                    .filter(career -> career.getCareerDetailList() == null || career.getCareerDetailList().isEmpty())
                    .collect(Collectors.toList());
            return  CareerConverter.toCareerSearchDto(detailList, careersWithoutDetails);
        }

        return  CareerConverter.toCareerSearchDto(detailList, null);


    }
    @Override
    public Career findCareerDetail(Member requestMember, Long careerId) {
        Career career = findCareer(careerId).get();
        if(!career.getMemberId().equals(requestMember.getId())){
            throw new OwnerMismatchException();
        }
        return career;
    }
    @Override
    public Optional<Career> findCareer(Long careerId) {
        return Optional.ofNullable(careerRepository.findById(careerId).orElseThrow(
                () -> new ResourceNotFoundException("Career",careerId)));
    }







    private void updateEndDateAndUnknownStatus(Career career, Boolean isUnknown, LocalDate endDate) {
        if (isUnknown != null) {
            career.setUnknown(isUnknown);
            if (isUnknown) {
                career.setEnddate(LocalDate.now());
                career.setYear(LocalDate.now().getYear());
            } else {
                setEndDate(career, endDate);
            }
        }else{
            setEndDate(career, endDate);
        }
    }

    private void setEndDate(Career career, LocalDate endDate) {
        if (endDate != null) {
            career.setEnddate(endDate);
            career.setYear(endDate.getYear());
        }else{
            LocalDate existingEndDate = career.getEnddate();
            if (existingEndDate != null) {
                career.setEnddate(existingEndDate);
            }else{
                throw new CareerValidationException(CareerResponseMessage.CAREER_ENDDATE);
            }
        }
    }

    private void validatedPeriod(Career career) {
        if(career.getEnddate().isBefore(career.getStartdate())){
            throw new CareerValidationException(CareerResponseMessage.CAREER_PERIOD_FAIL);
        }
    }

    private int parsingYear(CareerRequestDto.CreateCareerDto request){
        if(!request.getIsUnknown()){
            return request.getEndDate().getYear();
        }
        return LocalDate.now().getYear();
    }
}
