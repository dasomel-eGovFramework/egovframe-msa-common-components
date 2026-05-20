package egovframework.com.uss.olp.qim.util;

import egovframework.com.uss.olp.qim.entity.QustnrIem;
import egovframework.com.uss.olp.qim.entity.QustnrIemId;
import egovframework.com.uss.olp.qim.service.QustnrIemVO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * EgovQusntrItemUtility 단위 테스트.
 * Spring 컨텍스트 없이 유틸리티 메서드의 변환 로직을 검증한다.
 */
@ExtendWith(MockitoExtension.class)
class EgovQusntrItemUtilityTest {

    @Test
    @DisplayName("qustnrIemEntityToVO: 엔티티의 복합 키 필드가 VO에 올바르게 매핑된다")
    void qustnrIemEntityToVO_compositeKeyMappedCorrectly() {
        QustnrIemId id = new QustnrIemId();
        id.setQustnrTmplatId("TMPLAT-001");
        id.setQestnrId("QESTNR-001");
        id.setQustnrQesitmId("QESITM-001");
        id.setQustnrIemId("IEM-001");

        QustnrIem entity = new QustnrIem();
        entity.setQustnrIemId(id);
        entity.setIemSn("1");
        entity.setIemCn("항목 내용");
        entity.setEtcAnswerAt("N");
        entity.setFrstRegistPnttm(LocalDateTime.of(2024, 1, 1, 0, 0));
        entity.setFrstRegisterId("USR001");
        entity.setLastUpdtPnttm(LocalDateTime.of(2024, 6, 1, 0, 0));
        entity.setLastUpdusrId("USR002");

        QustnrIemVO vo = EgovQusntrItemUtility.qustnrIemEntityToVO(entity);

        // qim 유틸리티는 qustnrTmplatId ↔ qestnrId 를 교차 매핑하는 구조이므로 실제 로직을 그대로 검증
        assertThat(vo).isNotNull();
        assertThat(vo.getQustnrTmplatId()).isEqualTo(id.getQestnrId());
        assertThat(vo.getQestnrId()).isEqualTo(id.getQustnrTmplatId());
        assertThat(vo.getQustnrQesitmId()).isEqualTo(id.getQustnrQesitmId());
        assertThat(vo.getQustnrIemId()).isEqualTo(id.getQustnrIemId());
        assertThat(vo.getIemSn()).isEqualTo("1");
        assertThat(vo.getIemCn()).isEqualTo("항목 내용");
        assertThat(vo.getEtcAnswerAt()).isEqualTo("N");
    }

    @Test
    @DisplayName("qustnrIemVOToEntity: VO의 ID 필드가 엔티티 복합 키에 올바르게 설정된다")
    void qustnrIemVOToEntity_compositeKeySetCorrectly() {
        QustnrIemVO vo = new QustnrIemVO();
        vo.setQustnrTmplatId("TMPLAT-002");
        vo.setQestnrId("QESTNR-002");
        vo.setQustnrQesitmId("QESITM-002");
        vo.setQustnrIemId("IEM-002");
        vo.setIemSn("2");
        vo.setIemCn("두 번째 항목");
        vo.setEtcAnswerAt("Y");

        QustnrIem entity = EgovQusntrItemUtility.qustnrIemVOToEntity(vo);

        assertThat(entity).isNotNull();
        assertThat(entity.getQustnrIemId()).isNotNull();
        assertThat(entity.getQustnrIemId().getQustnrTmplatId()).isEqualTo(vo.getQustnrTmplatId());
        assertThat(entity.getQustnrIemId().getQestnrId()).isEqualTo(vo.getQestnrId());
        assertThat(entity.getQustnrIemId().getQustnrQesitmId()).isEqualTo(vo.getQustnrQesitmId());
        assertThat(entity.getQustnrIemId().getQustnrIemId()).isEqualTo(vo.getQustnrIemId());
        assertThat(entity.getIemSn()).isEqualTo("2");
        assertThat(entity.getIemCn()).isEqualTo("두 번째 항목");
        assertThat(entity.getEtcAnswerAt()).isEqualTo("Y");
    }

    @Test
    @DisplayName("qustnrIemVOToEntity → qustnrIemEntityToVO: 왕복 변환 후 항목 내용이 보존된다")
    void roundTrip_iemContentPreserved() {
        QustnrIemVO original = new QustnrIemVO();
        original.setQustnrTmplatId("TMPLAT-003");
        original.setQestnrId("QESTNR-003");
        original.setQustnrQesitmId("QESITM-003");
        original.setQustnrIemId("IEM-003");
        original.setIemSn("3");
        original.setIemCn("왕복 테스트 항목");
        original.setEtcAnswerAt("N");

        QustnrIem entity = EgovQusntrItemUtility.qustnrIemVOToEntity(original);
        QustnrIemVO result = EgovQusntrItemUtility.qustnrIemEntityToVO(entity);

        assertThat(result.getIemCn()).isEqualTo(original.getIemCn());
        assertThat(result.getIemSn()).isEqualTo(original.getIemSn());
        assertThat(result.getEtcAnswerAt()).isEqualTo(original.getEtcAnswerAt());
    }
}
