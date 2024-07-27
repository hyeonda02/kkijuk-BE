package umc.kkijuk.server.introduce.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import umc.kkijuk.server.introduce.domain.*;
import umc.kkijuk.server.introduce.dto.*;
import umc.kkijuk.server.introduce.error.BaseException;
import umc.kkijuk.server.recruit.infrastructure.RecruitEntity;
import umc.kkijuk.server.recruit.infrastructure.RecruitJpaRepository;


import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class IntroduceService {

    private final IntroduceRepository introduceRepository;
    private final RecruitJpaRepository recruitJpaRepository;
    private final QuestionRepository questionRepository;

    @Transactional
    public IntroduceResDto saveIntro(Long recruitId, IntroduceReqDto introduceReqDto){
        RecruitEntity recruit=recruitJpaRepository.findById(recruitId)
                .orElseThrow(()-> new BaseException(HttpStatus.NOT_FOUND.value(), "해당 공고를 찾을 수 없습니다"));
        if (introduceRepository.findByRecruitId(recruitId).isPresent()) {
            throw new BaseException(HttpStatus.CONFLICT.value(), "이미 자기소개서가 존재합니다");
        }
        List<Question> questions = introduceReqDto.getQuestionList().stream()
                .map(dto -> new Question(dto.getTitle(), dto.getContent()))
                .collect(Collectors.toList());

        Introduce introduce=Introduce.builder()
                .recruit(recruit)
                .questions(questions)
                .state(introduceReqDto.getState())
                .build();

        introduceRepository.save(introduce);

        return new IntroduceResDto(introduce, introduceReqDto.getQuestionList());
    }

    @Transactional
    public IntroduceResDto getIntro(Long introId){
        Introduce introduce=introduceRepository.findById(introId)
                .orElseThrow(()-> new BaseException(HttpStatus.NOT_FOUND.value(), "해당 자기소개서를 찾을 수 없습니다"));

        List<QuestionDto> questionList = introduce.getQuestions()
                .stream()
                .map(question -> new QuestionDto(question.getTitle(), question.getContent()))
                .collect(Collectors.toList());

        return new IntroduceResDto(introduce, questionList);
    }

    /*@Transactional
    public IntroduceResDto updateIntro(Long introId, IntroduceReqDto introduceReqDto){
        Introduce introduce=introduceRepository.findById(introId)
                .orElseThrow(()-> new BaseException(HttpStatus.NOT_FOUND.value(), "해당 자기소개서를 찾을 수 없습니다"));

        List<Question> updatedQuestions = introduceReqDto.getQuestionList().stream()
                .map(questionDto -> {
                    if (questionDto.getId() != null) {
                        // 기존 질문을 찾아서 업데이트
                        Question question = questionRepository.findById(questionDto.getId())
                                .orElseThrow(() -> new BaseException(HttpStatus.NOT_FOUND.value(), "해당 질문을 찾을 수 없습니다"));
                        question.update(questionDto.getTitle(), questionDto.getContent());
                        return question;
                    } else {
                        // 새 질문 생성
                        Question newQuestion = new Question(questionDto.getTitle(), questionDto.getContent());
                        newQuestion.setIntroduce(introduce); // 자기소개서와 연관 설정
                        return newQuestion;
                    }
                })
                .collect(Collectors.toList());

        introduce.getQuestions().forEach(question -> {
            if (updatedQuestions.stream().noneMatch(updatedQuestion -> updatedQuestion.getId().equals(question.getId()))) {
                questionRepository.delete(question);
            }
        });

        introduce.setQuestions(updatedQuestions);

        introduceRepository.save(introduce);

        List<QuestionDto> questionList = introduce.getQuestions()
                .stream()
                .map(question -> new QuestionDto(question.getId(), question.getTitle(), question.getContent()))
                .collect(Collectors.toList());

        return new IntroduceResDto(introduce, questionList);
    }*/

    @Transactional
    public Long deleteIntro(Long introId){
        Introduce introduce = introduceRepository.findById(introId)
                .orElseThrow(()-> new BaseException(HttpStatus.NOT_FOUND.value(), "해당 자기소개서를 찾을 수 없습니다"));

        introduceRepository.delete(introduce);

        return introduce.getId();
    }
}
