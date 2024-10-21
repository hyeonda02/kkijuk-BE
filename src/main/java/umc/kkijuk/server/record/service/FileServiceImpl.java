package umc.kkijuk.server.record.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;
import umc.kkijuk.server.member.domain.Member;
import umc.kkijuk.server.record.controller.response.FileResponse;
import umc.kkijuk.server.record.domain.File;
import umc.kkijuk.server.record.dto.FileReqDto;
import umc.kkijuk.server.record.repository.FileRepository;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FileServiceImpl implements FileService{
    private final FileRepository fileRepository;
    private final S3Presigner s3Presigner;
    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;
    @Value("${cloud.aws.s3.bucket-path}")
    private String bucketPath;

    @Override
    public Map<String, String> getSignUrl (Member requestMember, String fileName){
        String keyName = bucketPath + "/" + UUID.randomUUID().toString() + "-" + fileName;
        PutObjectRequest objecrRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(keyName)
                .contentType("application/pdf") //일단 pdf 파일만 업로드
                .build();
        PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(10))
                .putObjectRequest(objecrRequest)
                .build();
        String presignedUrl = s3Presigner.presignPutObject(presignRequest).url().toString();

        Map<String, String> response = new HashMap<>();
        response.put("signedURL",presignedUrl);
        response.put("keyName",keyName);
        return response;
    }

    @Override
    @Transactional
    public FileResponse createFile(Member requestMember, FileReqDto request) {
        File file = File.builder()
                .memberId(requestMember.getId())
                .title(request.getTitle())
                .keyName(request.getKeyName())
                .build();
        return new FileResponse(fileRepository.save(file));
    }

}
