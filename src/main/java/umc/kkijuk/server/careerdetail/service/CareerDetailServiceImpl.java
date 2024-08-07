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
import umc.kkijuk.server.careerdetail.repository.CareerTagRepository;
import umc.kkijuk.server.common.domian.exception.OwnerMismatchException;
import umc.kkijuk.server.common.domian.exception.ResourceNotFoundException;
import umc.kkijuk.server.member.domain.Member;
import umc.kkijuk.server.tag.domain.Tag;
import umc.kkijuk.server.tag.repository.TagRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CareerDetailServiceImpl implements CareerDetailService{
    private final CareerRepository careerRepository;
    private final TagRepository tagRepository;
    private final CareerDetailRepository careerDetailRepository;
    private final CareerTagRepository careerTagRepositosy;

    @Override
    @Transactional
    public CareerDetail create(Member requestMember, CareerDetailRequestDto.CareerDetailCreate request, Long careerId)  {
        Career career = careerRepository.findById(careerId).orElseThrow(() -> new ResourceNotFoundException("Career", careerId));
        if(!career.getMemberId().equals(requestMember.getId())){
            throw new OwnerMismatchException();
        }

        CareerDetail newCareerDetail = CareerDetailConverter.toCareerDetail(requestMember.getId(),request);
        newCareerDetail.setCareer(career);
        career.getCareerDetailList().add(newCareerDetail);

        List<CareerTag> careerTagList = returnCareerTagList(request.getTagList());
        careerTagList.forEach(careerTag -> careerTag.setCareerDetail(newCareerDetail));
        return careerDetailRepository.save(newCareerDetail);


    }

    @Override
    @Transactional
    public void delete(Member reqeustMember ,Long detailId) {
        CareerDetail careerDetail = careerDetailRepository.findById(detailId).orElseThrow(() -> new ResourceNotFoundException("CareerDetail", detailId));
        if(!careerDetail.getMemberId().equals(reqeustMember.getId())){
            throw new OwnerMismatchException();
        }
        careerDetailRepository.delete(careerDetail);
    }
    @Override
    @Transactional
    public CareerDetail update(Member requestMember, CareerDetailRequestDto.CareerDetailUpdate request, Long careerId, Long detailId){
        CareerDetail updateCareerDetail = careerDetailRepository.findById(detailId).orElseThrow(() -> new ResourceNotFoundException("CareerDetail", detailId));
        if(!updateCareerDetail.getMemberId().equals(requestMember.getId())){
            throw new OwnerMismatchException();
        }

        List<CareerTag> existTags = new ArrayList<>(updateCareerDetail.getCareerTagList());
        updateCareerDetail.getCareerTagList().clear();
        existTags.forEach(careerTag -> careerTagRepositosy.delete(careerTag));

        updateCareerDetail.setTitle(request.getTitle());
        updateCareerDetail.setContent(request.getContent());
        updateCareerDetail.setStartDate(request.getStartDate());
        updateCareerDetail.setEndDate(request.getEndDate());

        List<CareerTag> careerTagList = returnCareerTagList(request.getTagList());
        careerTagList.forEach(careerTag -> careerTag.setCareerDetail(updateCareerDetail));

        return careerDetailRepository.save(updateCareerDetail);


    }
    private List<CareerTag> returnCareerTagList(List<Long> tagIdList) {
        List<Tag> tagList = tagIdList.stream().map(tagId -> {
            return tagRepository.findById(tagId).orElseThrow(() -> new ResourceNotFoundException("Tag", tagId));
        }).collect(Collectors.toList());
        List<CareerTag> careerTagList = CareerTagConverter.toCareerTagList(tagList);
        return careerTagList;
    }
}
