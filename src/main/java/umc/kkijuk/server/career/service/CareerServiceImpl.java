package umc.kkijuk.server.career.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.kkijuk.server.career.controller.exception.CareerNotFoundException;
import umc.kkijuk.server.career.controller.response.CareerResponseMessage;
import umc.kkijuk.server.career.domain.Career;
import umc.kkijuk.server.career.dto.CareerRequestDto;
import umc.kkijuk.server.career.dto.converter.CareerConverter;
import umc.kkijuk.server.career.repository.CareerRepository;
import umc.kkijuk.server.career.repository.CategoryRepository;

import java.beans.PropertyDescriptor;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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
    public Career updateCareer(Long careerId, CareerRequestDto.UpdateCareerDto request) {
        Career career = findCareer(careerId).get();
        if(request.getCategory()!=0){
            career.setCategory(categoryRepository.findById(Long.valueOf(request.getCategory())).get());
        }
        return careerRepository.save(career);
    }


    @Override
    public Optional<Career> findCareer(Long careerId) {
        return Optional.ofNullable(careerRepository.findById(careerId).orElseThrow(
                () -> new CareerNotFoundException(CareerResponseMessage.CAREER_NOT_FOUND.toString())));
    }

    private int parsingYear(CareerRequestDto.CareerDto request){
        if(!request.getIsCurrent()){
            return request.getEndDate().getYear();
        }
        return LocalDate.now().getYear();
    }
}
