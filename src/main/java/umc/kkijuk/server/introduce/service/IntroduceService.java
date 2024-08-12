package umc.kkijuk.server.introduce.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.kkijuk.server.common.domian.exception.IntroFoundException;
import umc.kkijuk.server.common.domian.exception.IntroOwnerMismatchException;
import umc.kkijuk.server.common.domian.exception.RecruitOwnerMismatchException;
import umc.kkijuk.server.common.domian.exception.ResourceNotFoundException;
import umc.kkijuk.server.introduce.domain.*;
import umc.kkijuk.server.introduce.dto.*;
import umc.kkijuk.server.member.domain.Member;
import umc.kkijuk.server.recruit.infrastructure.RecruitEntity;
import umc.kkijuk.server.recruit.infrastructure.RecruitJpaRepository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class IntroduceService {

    private final IntroduceRepository introduceRepository;
    private final RecruitJpaRepository recruitJpaRepository;
    private final QuestionRepository questionRepository;

    @Transactional
    public IntroduceResDto saveIntro(Member requestMember, Long recruitId, IntroduceReqDto introduceReqDto){
        RecruitEntity recruit=recruitJpaRepository.findById(recruitId)
                .orElseThrow(()-> new ResourceNotFoundException("recruit ", recruitId));
        if (introduceRepository.findByRecruitId(recruitId).isPresent()) {
            throw new IntroFoundException("이미 자기소개서가 존재합니다");
        }
        List<Question> questions = introduceReqDto.getQuestionList().stream()
                .map(dto -> new Question(dto.getTitle(), dto.getContent(), dto.getNumber()))
                .collect(Collectors.toList());

        Introduce introduce=Introduce.builder()
                .memberId(requestMember.getId())
                .recruit(recruit)
                .questions(questions)
                .state(introduceReqDto.getState())
                .build();

        introduceRepository.save(introduce);
        /*List<String> introduceList=getIntroduceTitles();*/
        return new IntroduceResDto(introduce, introduceReqDto.getQuestionList()/*,introduceList*/);
    }

    @Transactional
    public IntroduceResDto getIntro(Member requestMember, Long introId){
        Introduce introduce=introduceRepository.findById(introId)
                .orElseThrow(()-> new ResourceNotFoundException("introduce ", introId));
        if (!introduce.getMemberId().equals(requestMember.getId())) {
            throw new IntroOwnerMismatchException();
        }

        List<QuestionDto> questionList = introduce.getQuestions()
                .stream()
                .map(question -> new QuestionDto(question.getTitle(), question.getContent(), question.getNumber()))
                .collect(Collectors.toList());

        /*List<String> introduceList=getIntroduceTitles();*/

        return new IntroduceResDto(introduce, questionList/*, introduceList*/);
    }

    @Transactional
    public List<IntroduceListResDto> getIntroList(Member requestMember){
        List<Introduce> introduces = introduceRepository.findAllByMemberId(requestMember.getId())
                .orElseThrow(IntroOwnerMismatchException::new);

        return introduces.stream()
                .map(IntroduceListResDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public IntroduceResDto updateIntro(Member requestMember, Long introId, IntroduceReqDto introduceReqDto) throws Exception{
        Introduce introduce=introduceRepository.findById(introId)
                .orElseThrow(()-> new ResourceNotFoundException("introduce ", introId));

        if (!introduce.getMemberId().equals(requestMember.getId())) {
            throw new IntroOwnerMismatchException();
        }

        introduce.update(introduceReqDto.getState());

        List<Question> existingQuestions = introduce.getQuestions();
        Map<Integer, Question> existingQuestionsMap = existingQuestions.stream()
                .collect(Collectors.toMap(Question::getNumber, q -> q));

        List<QuestionDto> questionDtos = introduceReqDto.getQuestionList();
        List<Question> updatedQuestions = new ArrayList<>();

        for (QuestionDto questionDto : questionDtos) {
            Integer number = questionDto.getNumber();
            Question existingQuestion = existingQuestionsMap.get(number);

            if (existingQuestion != null) {
                existingQuestion.update(questionDto.getTitle(), questionDto.getContent());
                updatedQuestions.add(existingQuestion);
            } else {
                Question newQuestion = new Question();
                newQuestion.setTitle(questionDto.getTitle());
                newQuestion.setContent(questionDto.getContent());
                newQuestion.setNumber(questionDto.getNumber());
                newQuestion.setIntroduce(introduce);
                updatedQuestions.add(newQuestion);
            }
        }

        List<Question> toRemove = existingQuestions.stream()
                .filter(q -> !questionDtos.stream()
                        .anyMatch(dto -> dto.getNumber() == q.getNumber()))
                .collect(Collectors.toList());

        toRemove.forEach(question -> {
            introduce.getQuestions().remove(question);
            questionRepository.delete(question);
        });

        introduce.getQuestions().clear();
        introduce.getQuestions().addAll(updatedQuestions);

        introduceRepository.save(introduce);

        List<QuestionDto> responseQuestionList = introduce.getQuestions().stream()
                .map(question -> QuestionDto.builder()
                        .title(question.getTitle())
                        .content(question.getContent())
                        .number(question.getNumber())
                        .build())
                .collect(Collectors.toList());

        return IntroduceResDto.builder()
                .introduce(introduce)
                .questionList(responseQuestionList)
                .build();
    }


    @Transactional
    public Long deleteIntro(Member requestMember, Long introId){
        Introduce introduce=introduceRepository.findById(introId)
                .orElseThrow(()-> new ResourceNotFoundException("introduce ", introId));
        if (!introduce.getMemberId().equals(requestMember.getId())) {
            throw new IntroOwnerMismatchException();
        }

        introduceRepository.delete(introduce);

        return introduce.getId();
    }

   /* @Transactional
    public List<String> getIntroduceTitles() {
        // Fetch all Introduce entities
        List<Introduce> introduces = introduceRepository.findAll();

        // Map Introduce entities to Recruit titles
        return introduces.stream()
                .map(introduce -> recruitJpaRepository.findById(introduce.getRecruit().toModel().getId())) // Get the Recruit entity
                .filter(Optional::isPresent) // Filter out any empty results
                .map(opt -> opt.get().toModel().getTitle()) // Get the title of the Recruit
                .collect(Collectors.toList()); // Collect titles into a List
    }*/
}
