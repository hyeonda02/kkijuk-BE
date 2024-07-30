package umc.kkijuk.server.record.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "record", description = "이력서 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/history/resume/")
public class RecordController {
}
