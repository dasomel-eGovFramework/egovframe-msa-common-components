package egovframework.com.uss.olp.qtm.util;

import egovframework.com.uss.olp.qtm.entity.QustnrTmplat;
import egovframework.com.uss.olp.qtm.service.QustnrTmplatVO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * EgovQustnrTmplatUtility 단위 테스트.
 * Spring 컨텍스트 없이 설문 템플릿 변환 유틸리티 메서드를 검증한다.
 */
@ExtendWith(MockitoExtension.class)
class EgovQustnrTmplatUtilityTest {

    @Test
    @DisplayName("qustnrTmplatVOToEntity: VO 필드가 엔티티에 올바르게 복사된다")
    void qustnrTmplatVOToEntity_fieldsAreCopiedCorrectly() {
        QustnrTmplatVO vo = new QustnrTmplatVO();
        vo.setQustnrTmplatId("TMPLAT-001");
        vo.setQustnrTmplatTy("01");
        vo.setQustnrTmplatDc("기본 설문 템플릿");
        vo.setQustnrTmplatPathNm("/template/basic.png");

        QustnrTmplat entity = EgovQustnrTmplatUtility.qustnrTmplatVOToEntity(vo);

        assertThat(entity).isNotNull();
        assertThat(entity.getQustnrTmplatId()).isEqualTo("TMPLAT-001");
        assertThat(entity.getQustnrTmplatTy()).isEqualTo("01");
        assertThat(entity.getQustnrTmplatDc()).isEqualTo("기본 설문 템플릿");
        assertThat(entity.getQustnrTmplatPathNm()).isEqualTo("/template/basic.png");
    }

    @Test
    @DisplayName("qustnrTmplatEntityToVO: 엔티티 필드가 VO에 올바르게 복사된다")
    void qustnrTmplatEntityToVO_fieldsAreCopiedCorrectly() {
        QustnrTmplat entity = new QustnrTmplat();
        entity.setQustnrTmplatId("TMPLAT-002");
        entity.setQustnrTmplatTy("02");
        entity.setQustnrTmplatDc("커스텀 템플릿");
        entity.setQustnrTmplatPathNm("/template/custom.png");
        entity.setFrstRegisterId("USR001");
        entity.setLastUpdusrId("USR002");
        entity.setFrstRegistPnttm(LocalDateTime.of(2024, 3, 1, 9, 0));
        entity.setLastUpdtPnttm(LocalDateTime.of(2024, 9, 1, 12, 0));

        QustnrTmplatVO vo = EgovQustnrTmplatUtility.qustnrTmplatEntityToVO(entity);

        assertThat(vo).isNotNull();
        assertThat(vo.getQustnrTmplatId()).isEqualTo("TMPLAT-002");
        assertThat(vo.getQustnrTmplatTy()).isEqualTo("02");
        assertThat(vo.getQustnrTmplatDc()).isEqualTo("커스텀 템플릿");
        assertThat(vo.getQustnrTmplatPathNm()).isEqualTo("/template/custom.png");
        assertThat(vo.getFrstRegisterId()).isEqualTo("USR001");
        assertThat(vo.getLastUpdusrId()).isEqualTo("USR002");
    }

    @Test
    @DisplayName("qustnrTmplatEntityToVO: 엔티티의 이미지 바이트가 VO에 복사된다")
    void qustnrTmplatEntityToVO_imageByteCopied() {
        byte[] imageBytes = new byte[]{0x01, 0x02, 0x03};
        QustnrTmplat entity = new QustnrTmplat();
        entity.setQustnrTmplatId("TMPLAT-003");
        entity.setQustnrTmplatImageInfo(imageBytes);

        QustnrTmplatVO vo = EgovQustnrTmplatUtility.qustnrTmplatEntityToVO(entity);

        assertThat(vo.getQustnrTmplatImageByte()).isEqualTo(imageBytes);
    }

    @Test
    @DisplayName("qustnrTmplatEntityToVO: 이미지가 null인 경우 VO의 이미지 바이트도 null이다")
    void qustnrTmplatEntityToVO_nullImageHandledGracefully() {
        QustnrTmplat entity = new QustnrTmplat();
        entity.setQustnrTmplatId("TMPLAT-004");
        entity.setQustnrTmplatImageInfo(null);

        QustnrTmplatVO vo = EgovQustnrTmplatUtility.qustnrTmplatEntityToVO(entity);

        assertThat(vo).isNotNull();
        assertThat(vo.getQustnrTmplatImageByte()).isNull();
    }
}
