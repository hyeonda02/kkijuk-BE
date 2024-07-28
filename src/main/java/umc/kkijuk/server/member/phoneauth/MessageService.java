package umc.kkijuk.server.member.phoneauth;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.Random;

@Service
public class MessageService {
    private final SmsCertification smsCertification;
    private final DefaultMessageService messageService;


    public MessageService(SmsCertification smsCertification, @Value("${coolsms.apikey}") String apiKey, @Value("${coolsms.apisecret}") String apiSecret) {
        this.smsCertification = smsCertification;
        // 반드시 계정 내 등록된 유효한 API 키, API Secret Key를 입력해주셔야 합니다!
        this.messageService = NurigoApp.INSTANCE.initialize(apiKey, apiSecret, "https://api.coolsms.co.kr");
    }

    @Value("${coolsms.fromnumber}")
    private String fromNumber;

    private String createRandomNumber() {
        Random rand = new Random();
        String randomNum = "";
        for (int i = 0; i < 4; i++) {
            String random = Integer.toString(rand.nextInt(10));
            randomNum += random;
        }

        return randomNum;
    }

    private HashMap<String, String> makeParams(String to, String randomNum) {
        HashMap<String, String> params = new HashMap<>();
        params.put("from", fromNumber);
        params.put("type", "SMS");
        params.put("app_version", "test app 1.2");
        params.put("to", to);
        params.put("text", randomNum);
        return params;
    }


    // 인증번호 전송하기
    public SmsCertificationResponse sendSMS(String phoneNumber) {
        Message coolsms = new Message();
        coolsms.setFrom(fromNumber);
        coolsms.setTo(phoneNumber);
        // 랜덤한 인증 번호 생성
        String randomNum = createRandomNumber();
        System.out.println(randomNum);
        coolsms.setText("인증번호: "+randomNum);


        // 발신 정보 설정
        HashMap<String, String> params = makeParams(phoneNumber, randomNum);

//        try {
//            JSONObject obj = (JSONObject) coolsms.send(params);
//            System.out.println(obj.toString());
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            System.out.println(e.getCode());
//        }

        SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(coolsms));
        System.out.println(response);


        // DB에 발송한 인증번호 저장
        smsCertification.createSmsCertification(phoneNumber,randomNum);


        return new SmsCertificationResponse(phoneNumber, randomNum);


//        return "문자 전송이 완료되었습니다.";
    }

    // 인증 번호 검증
    public String verifySms(SmsCertificationDto requestDto) {
        if (isVerify(requestDto)) {
            throw new IllegalArgumentException("인증번호가 일치하지 않습니다.");
        }
        smsCertification.deleteSmsCertification(requestDto.getPhoneNumber());

        return "인증 완료되었습니다.";
    }

    private boolean isVerify(SmsCertificationDto requestDto) {
        return !(smsCertification.hasKey(requestDto.getPhoneNumber()) &&
                smsCertification.getSmsCertification(requestDto.getPhoneNumber())
                        .equals(requestDto.getRandomNumber()));
    }
}