package umc.kkijuk.server.unitTest.career.controller;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import umc.kkijuk.server.career.controller.CareerController;
import umc.kkijuk.server.career.service.CareerServiceImpl;
import umc.kkijuk.server.member.service.MemberServiceImpl;

@ExtendWith(MockitoExtension.class)
public class CareerControllerTest {
    @InjectMocks
    private CareerController careerController;
    @Mock
    private CareerServiceImpl careerService;
    @Mock
    private MemberServiceImpl memberService;
}
