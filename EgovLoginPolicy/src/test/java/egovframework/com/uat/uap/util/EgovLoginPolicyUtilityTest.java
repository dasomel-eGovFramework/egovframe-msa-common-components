package egovframework.com.uat.uap.util;

import egovframework.com.uat.uap.entity.LoginPolicy;
import egovframework.com.uat.uap.service.LoginPolicyVO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * EgovLoginPolicyUtility 단위 테스트.
 * Spring 컨텍스트 없이 entity-VO 양방향 변환 로직을 검증한다.
 */
class EgovLoginPolicyUtilityTest {

    // ---------------------------------------------------------------
    // entityToVO
    // ---------------------------------------------------------------

    @Test
    @DisplayName("entityToVO: 모든 필드가 VO로 복사된다")
    void entityToVO_allFields_copiedCorrectly() {
        LocalDateTime now = LocalDateTime.of(2024, 1, 15, 9, 30, 0);

        LoginPolicy entity = new LoginPolicy();
        entity.setEmployerId("user001");
        entity.setIpInfo("192.168.1.100");
        entity.setDplctPermAt("N");
        entity.setLmttAt("Y");
        entity.setFrstRegisterId("admin");
        entity.setFrstRegisterPnttm(now);
        entity.setLastUpdusrId("admin");
        entity.setLastUpdtPnttm(now);

        LoginPolicyVO vo = EgovLoginPolicyUtility.entityToVO(entity);

        assertThat(vo.getEmployerId()).isEqualTo("user001");
        assertThat(vo.getIpInfo()).isEqualTo("192.168.1.100");
        assertThat(vo.getDplctPermAt()).isEqualTo("N");
        assertThat(vo.getLmttAt()).isEqualTo("Y");
        assertThat(vo.getFrstRegisterId()).isEqualTo("admin");
        assertThat(vo.getFrstRegisterPnttm()).isEqualTo(now);
        assertThat(vo.getLastUpdusrId()).isEqualTo("admin");
        assertThat(vo.getLastUpdtPnttm()).isEqualTo(now);
    }

    @Test
    @DisplayName("entityToVO: null 필드는 VO에도 null로 매핑된다")
    void entityToVO_nullFields_remainNull() {
        LoginPolicy entity = new LoginPolicy();
        entity.setEmployerId("user002");
        // 나머지 필드 null

        LoginPolicyVO vo = EgovLoginPolicyUtility.entityToVO(entity);

        assertThat(vo.getEmployerId()).isEqualTo("user002");
        assertThat(vo.getIpInfo()).isNull();
        assertThat(vo.getDplctPermAt()).isNull();
        assertThat(vo.getLmttAt()).isNull();
    }

    @Test
    @DisplayName("entityToVO: 반환 객체는 항상 새 인스턴스다")
    void entityToVO_returnsNewInstance() {
        LoginPolicy entity = new LoginPolicy();
        entity.setEmployerId("user003");

        LoginPolicyVO vo1 = EgovLoginPolicyUtility.entityToVO(entity);
        LoginPolicyVO vo2 = EgovLoginPolicyUtility.entityToVO(entity);

        assertThat(vo1).isNotSameAs(vo2);
    }

    // ---------------------------------------------------------------
    // vOToEntity
    // ---------------------------------------------------------------

    @Test
    @DisplayName("vOToEntity: 모든 필드가 entity로 복사된다")
    void vOToEntity_allFields_copiedCorrectly() {
        LocalDateTime now = LocalDateTime.of(2024, 6, 1, 12, 0, 0);

        LoginPolicyVO vo = new LoginPolicyVO();
        vo.setEmployerId("user010");
        vo.setIpInfo("10.0.0.1");
        vo.setDplctPermAt("Y");
        vo.setLmttAt("N");
        vo.setFrstRegisterId("system");
        vo.setFrstRegisterPnttm(now);
        vo.setLastUpdusrId("system");
        vo.setLastUpdtPnttm(now);

        LoginPolicy entity = EgovLoginPolicyUtility.vOToEntity(vo);

        assertThat(entity.getEmployerId()).isEqualTo("user010");
        assertThat(entity.getIpInfo()).isEqualTo("10.0.0.1");
        assertThat(entity.getDplctPermAt()).isEqualTo("Y");
        assertThat(entity.getLmttAt()).isEqualTo("N");
        assertThat(entity.getFrstRegisterId()).isEqualTo("system");
        assertThat(entity.getFrstRegisterPnttm()).isEqualTo(now);
        assertThat(entity.getLastUpdusrId()).isEqualTo("system");
        assertThat(entity.getLastUpdtPnttm()).isEqualTo(now);
    }

    @Test
    @DisplayName("vOToEntity: VO 전용 필드(userNm, regYn)는 entity에 복사되지 않는다")
    void vOToEntity_voOnlyFields_notCopied() {
        LoginPolicyVO vo = new LoginPolicyVO();
        vo.setEmployerId("user011");
        vo.setUserNm("홍길동");
        vo.setRegYn("Y");

        LoginPolicy entity = EgovLoginPolicyUtility.vOToEntity(vo);

        // entity에는 userNm, regYn 필드가 없으므로 employerId만 확인
        assertThat(entity.getEmployerId()).isEqualTo("user011");
    }

    @Test
    @DisplayName("vOToEntity → entityToVO: 라운드트립 시 공통 필드 값이 보존된다")
    void roundTrip_voToEntityToVO_preservesCommonFields() {
        LocalDateTime ts = LocalDateTime.of(2024, 3, 20, 8, 0, 0);

        LoginPolicyVO original = new LoginPolicyVO();
        original.setEmployerId("user020");
        original.setIpInfo("172.16.0.5");
        original.setDplctPermAt("N");
        original.setLmttAt("N");
        original.setFrstRegisterId("admin");
        original.setFrstRegisterPnttm(ts);
        original.setLastUpdusrId("admin");
        original.setLastUpdtPnttm(ts);

        LoginPolicyVO restored = EgovLoginPolicyUtility.entityToVO(
                EgovLoginPolicyUtility.vOToEntity(original));

        assertThat(restored.getEmployerId()).isEqualTo(original.getEmployerId());
        assertThat(restored.getIpInfo()).isEqualTo(original.getIpInfo());
        assertThat(restored.getDplctPermAt()).isEqualTo(original.getDplctPermAt());
        assertThat(restored.getLmttAt()).isEqualTo(original.getLmttAt());
        assertThat(restored.getFrstRegisterId()).isEqualTo(original.getFrstRegisterId());
        assertThat(restored.getFrstRegisterPnttm()).isEqualTo(original.getFrstRegisterPnttm());
    }
}
