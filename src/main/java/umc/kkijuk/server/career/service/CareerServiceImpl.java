package umc.kkijuk.server.career.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.kkijuk.server.career.controller.exception.CareerNotFoundException;
import umc.kkijuk.server.career.controller.exception.CareerValidationException;
import umc.kkijuk.server.career.controller.response.CareerResponseMessage;
import umc.kkijuk.server.career.domain.Career;
import umc.kkijuk.server.career.dto.CareerRequestDto;
import umc.kkijuk.server.career.dto.converter.CareerConverter;
import umc.kkijuk.server.career.repository.CareerRepository;
import umc.kkijuk.server.career.repository.CategoryRepository;
import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CareerServiceImpl implements CareerService {
    private final CategoryRepository categoryRepository;
    private final CareerRepository careerRepository;

    @Override
    @Transactional
    public Career createCareer(CareerRequestDto.CareerDto request) {
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
    public Optional<Career> findCareer(Long careerId) {
        return Optional.ofNullable(careerRepository.findById(careerId).orElseThrow(
                () -> new CareerNotFoundException(CareerResponseMessage.CAREER_NOT_FOUND.toString())));
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
                throw new CareerValidationException(CareerResponseMessage.CAREER_ENDDATE, "endDate");
            }
        }
    }

    private void validatedPeriod(Career career) {
        if(career.getEnddate().isBefore(career.getStartdate())){
            throw new CareerValidationException(CareerResponseMessage.CAREER_PERIOD_FAIL, "endDate");
        }
    }

    private int parsingYear(CareerRequestDto.CareerDto request){
        if(!request.getIsCurrent()){
            return request.getEndDate().getYear();
        }
        return LocalDate.now().getYear();
    }
}
