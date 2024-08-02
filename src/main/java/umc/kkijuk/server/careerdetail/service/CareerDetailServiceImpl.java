package umc.kkijuk.server.careerdetail.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.kkijuk.server.career.domain.Career;
import umc.kkijuk.server.career.repository.CareerRepository;
import umc.kkijuk.server.careerdetail.domain.CareerDetail;
import umc.kkijuk.server.careerdetail.domain.mapping.CareerTag;
import umc.kkijuk.server.careerdetail.dto.CareerDetailRequestDto;
import umc.kkijuk.server.careerdetail.dto.converter.CareerDetailConverter;
import umc.kkijuk.server.careerdetail.dto.converter.CareerTagConverter;
import umc.kkijuk.server.careerdetail.repository.CareerDetailRepository;
import umc.kkijuk.server.common.domian.exception.ResourceNotFoundException;
import umc.kkijuk.server.tag.domain.Tag;
import umc.kkijuk.server.tag.repository.TagRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CareerDetailServiceImpl implements CareerDetailService{
    private final CareerRepository careerRepository;
    private final TagRepository tagRepository;
    private final CareerDetailRepository careerDetailRepository;

    @Override
    @Transactional
    public CareerDetail create(CareerDetailRequestDto.CareerDetailCreate request, Long careerId)  {
        Career career = careerRepository.findById(careerId).orElseThrow(() -> new ResourceNotFoundException("Career", careerId));
        CareerDetail newCareerDetail = CareerDetailConverter.toCareerDetail(request);
        newCareerDetail.setCareer(career);

        List<Tag> tagList = request.getTagList().stream().map(tagId -> {
            return tagRepository.findById(tagId).orElseThrow(() -> new ResourceNotFoundException("Tag", tagId));
        }).collect(Collectors.toList());
        List<CareerTag> careerTagList = CareerTagConverter.toCareerTagList(tagList);
        careerTagList.forEach(careerTag -> careerTag.setCareerDetail(newCareerDetail));

        return careerDetailRepository.save(newCareerDetail);


    }


}