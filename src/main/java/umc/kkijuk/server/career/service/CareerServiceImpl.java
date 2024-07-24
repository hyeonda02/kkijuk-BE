package umc.kkijuk.server.career.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.kkijuk.server.career.controller.exception.CareerValidationException;
import umc.kkijuk.server.career.controller.response.CareerGroupedByResponse;
import umc.kkijuk.server.career.controller.response.CareerResponseMessage;
import umc.kkijuk.server.career.domain.Career;
import umc.kkijuk.server.career.dto.CareerRequestDto;
import umc.kkijuk.server.career.dto.converter.CareerConverter;
import umc.kkijuk.server.career.repository.CareerRepository;
import umc.kkijuk.server.career.repository.CategoryRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CareerServiceImpl implements CareerService {
    private final CategoryRepository categoryRepository;
    private final CareerRepository careerRepository;

    @Override
    @Transactional
    public Career createCareer(CareerRequestDto.CreateCareerDto request) {
        Career career = CareerConverter.toCareer(request);
        if(career.getCurrent()){
            career.setEnddate(LocalDate.now());
            career.setYear(LocalDate.now().getYear());
        }
        career.setCategory(categoryRepository.findById(Long.valueOf(request.getCategory())).get());
        career.setYear(parsingYear(request));
        return careerRepository.save(career);
    }
    @Override
    @Transactional
    public void deleteCareer(Long careerId) {
        Optional<Career> career = findCareer(careerId);
        careerRepository.delete(career.get());
    }

    @Override
    @Transactional
    public Career updateCareer(Long careerId, CareerRequestDto.UpdateCareerDto request) {
        Career career = findCareer(careerId).get();

        if (request.getCareerName()!=null) {
            career.setName(request.getCareerName());
        }
        if (request.getAlias()!=null) {
            career.setAlias(request.getAlias());
        }
        if (request.getSummary()!=null) {
            career.setSummary(request.getSummary());
        }
        if (request.getIsCurrent()!=null || request.getEndDate()!=null ) {
            updateEndDateAndCurrentStatus(career,request.getIsCurrent(),request.getEndDate());
            validatedPeriod(career);
        }
        if (request.getStartDate()!=null) {
            career.setStartdate(request.getStartDate());
        }
        if(request.getCategory()!=null){
            career.setCategory(categoryRepository.findById(Long.valueOf(request.getCategory())).get());
        }
        return careerRepository.save(career);
    }

    @Override
    public List<? extends CareerGroupedByResponse> getCareerGroupedBy(String status) {
        List<Career> careers = careerRepository.findAll();
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
    public Optional<Career> findCareer(Long careerId) {
        return Optional.ofNullable(careerRepository.findById(careerId).orElseThrow(
                () -> new CareerValidationException(CareerResponseMessage.CAREER_NOT_FOUND.toString())));
    }







    private void updateEndDateAndCurrentStatus(Career career, Boolean isCurrent, LocalDate endDate) {
        if (isCurrent != null) {
            career.setCurrent(isCurrent);
            if (isCurrent) {
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
        if(!request.getIsCurrent()){
            return request.getEndDate().getYear();
        }
        return LocalDate.now().getYear();
    }
}
