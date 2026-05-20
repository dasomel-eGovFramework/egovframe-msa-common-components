package egovframework.com.uat.uia.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import egovframework.com.uat.uia.entity.LoginPolicy;
import egovframework.com.uat.uia.repository.EgovEmployMemberRepository;
import egovframework.com.uat.uia.repository.EgovEnterpriseMemberRepository;
import egovframework.com.uat.uia.repository.EgovGeneralMemberRepository;
import egovframework.com.uat.uia.repository.EgovLoginPolicyRepository;
import egovframework.com.uat.uia.service.impl.EgovLoginManageServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

/**
 * EgovLoginManageServiceImpl 단위 테스트.
 * Mockito만 사용하므로 DB·Eureka·Redis 연결 없이 실행된다.
 */
@ExtendWith(MockitoExtension.class)
class EgovLoginManageServiceImplTest {

    @Mock
    private EgovGeneralMemberRepository genRepository;

    @Mock
    private EgovEnterpriseMemberRepository entRepository;

    @Mock
    private EgovEmployMemberRepository empRepository;

    @Mock
    private EgovLoginPolicyRepository loginPolicyRepository;

    @Mock
    private JPAQueryFactory queryFactory;

    @InjectMocks
    private EgovLoginManageServiceImpl service;

    // ---------------------------------------------------------------
    // loginPolicy
    // ---------------------------------------------------------------

    @Test
    @DisplayName("loginPolicy: 해당 employer ID가 DB에 존재하면 정책 정보를 채워 반환한다")
    void loginPolicy_existingEmployer_returnsPolicyData() {
        // given
        LoginPolicy policy = new LoginPolicy();
        policy.setEmployerId("USER001");
        policy.setLmttAt("Y");
        policy.setIpInfo("192.168.0.1");
        given(loginPolicyRepository.findById("USER001")).willReturn(Optional.of(policy));

        LoginPolicyVO vo = new LoginPolicyVO();
        vo.setEmployerId("USER001");

        // when
        LoginPolicyVO result = service.loginPolicy(vo);

        // then
        assertThat(result.getEmployerId()).isEqualTo("USER001");
        assertThat(result.getLmttAt()).isEqualTo("Y");
        assertThat(result.getIpInfo()).isEqualTo("192.168.0.1");
    }

    @Test
    @DisplayName("loginPolicy: 해당 employer ID가 DB에 없으면 VO를 그대로 반환한다")
    void loginPolicy_notFoundEmployer_returnsUnchangedVO() {
        // given
        given(loginPolicyRepository.findById("UNKNOWN")).willReturn(Optional.empty());

        LoginPolicyVO vo = new LoginPolicyVO();
        vo.setEmployerId("UNKNOWN");

        // when
        LoginPolicyVO result = service.loginPolicy(vo);

        // then
        assertThat(result.getEmployerId()).isEqualTo("UNKNOWN");
        assertThat(result.getLmttAt()).isNull();
        assertThat(result.getIpInfo()).isNull();
    }

    // ---------------------------------------------------------------
    // loginIncorrectList
    // ---------------------------------------------------------------

    @Test
    @DisplayName("loginIncorrectList: userId 또는 userSe가 비어 있으면 null을 반환한다")
    void loginIncorrectList_emptyUserIdOrSe_returnsNull() {
        LoginVO loginVO1 = new LoginVO();
        loginVO1.setUserId("");
        loginVO1.setUserSe("GNR");
        assertThat(service.loginIncorrectList(loginVO1)).isNull();

        LoginVO loginVO2 = new LoginVO();
        loginVO2.setUserId("USER001");
        loginVO2.setUserSe("");
        assertThat(service.loginIncorrectList(loginVO2)).isNull();
    }

    @Test
    @DisplayName("loginIncorrectList: 알 수 없는 userSe이면 null을 반환한다")
    void loginIncorrectList_unknownUserSe_returnsNull() {
        LoginVO loginVO = new LoginVO();
        loginVO.setUserId("USER001");
        loginVO.setUserSe("UNKNOWN");

        assertThat(service.loginIncorrectList(loginVO)).isNull();
    }

    @Test
    @DisplayName("loginIncorrectList: GNR 사용자가 존재하지 않으면 null을 반환한다")
    void loginIncorrectList_gnrUserNotFound_returnsNull() {
        given(genRepository.findById("MISSING")).willReturn(Optional.empty());

        LoginVO loginVO = new LoginVO();
        loginVO.setUserId("MISSING");
        loginVO.setUserSe("GNR");

        assertThat(service.loginIncorrectList(loginVO)).isNull();
    }

    // ---------------------------------------------------------------
    // loginIncorrectProcess
    // ---------------------------------------------------------------

    @Test
    @DisplayName("loginIncorrectProcess: userId 또는 userSe가 비어 있으면 'C'를 반환한다")
    void loginIncorrectProcess_emptyInput_returnsC() {
        LoginVO loginVO = new LoginVO();
        loginVO.setUserId("");
        loginVO.setUserSe("GNR");
        loginVO.setUserPw("pw");

        LoginIncorrectVO incorrectVO = new LoginIncorrectVO("", "encoded", "홍길동", "GNR", "ESNTL001", "N", 0);

        String result = service.loginIncorrectProcess(loginVO, incorrectVO, "5");
        assertThat(result).isEqualTo("C");
    }

    @Test
    @DisplayName("loginIncorrectProcess: 계정이 이미 잠겨 있으면 'L'을 반환한다")
    void loginIncorrectProcess_lockedAccount_returnsL() {
        LoginVO loginVO = new LoginVO();
        loginVO.setUserId("USER001");
        loginVO.setUserSe("GNR");
        loginVO.setUserPw("wrongPw");

        // lockAt=Y이면 비밀번호 불일치와 관계없이 'L' 반환
        LoginIncorrectVO incorrectVO = new LoginIncorrectVO("USER001", "somethingElse", "홍길동", "GNR", "ESNTL001", "Y", 3);

        String result = service.loginIncorrectProcess(loginVO, incorrectVO, "5");
        assertThat(result).isEqualTo("L");
    }
}
