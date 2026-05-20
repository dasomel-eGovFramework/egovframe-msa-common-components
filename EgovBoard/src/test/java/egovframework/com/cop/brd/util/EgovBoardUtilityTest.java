package egovframework.com.cop.brd.util;

import egovframework.com.cop.brd.entity.Bbs;
import egovframework.com.cop.brd.entity.BbsId;
import egovframework.com.cop.brd.entity.Comment;
import egovframework.com.cop.brd.entity.CommentId;
import egovframework.com.cop.brd.service.BbsVO;
import egovframework.com.cop.brd.service.CommentVO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * EgovBoardUtility 단위 테스트.
 * 외부 의존성(DB, Eureka, RabbitMQ) 없이 순수 변환 로직만 검증한다.
 */
class EgovBoardUtilityTest {

    @Test
    @DisplayName("bbsEntityToVO: Bbs 엔티티의 복합 키가 VO 필드로 올바르게 매핑된다")
    void bbsEntityToVO_shouldMapEmbeddedIdToVO() {
        // given
        BbsId bbsId = new BbsId();
        bbsId.setBbsId("BBSMSTR_000000000001");
        bbsId.setNttId(10L);

        Bbs bbs = new Bbs();
        bbs.setBbsId(bbsId);
        bbs.setNttNo(1);
        bbs.setParntscttNo(0);
        bbs.setAnswerLc(0);
        bbs.setSortOrdr(1);
        bbs.setRdcnt(0);
        bbs.setNttSj("테스트 게시글 제목");
        bbs.setUseAt("Y");
        bbs.setNtcrNm("홍길동");

        // when
        BbsVO vo = EgovBoardUtility.bbsEntityToVO(bbs);

        // then
        assertThat(vo.getBbsId()).isEqualTo("BBSMSTR_000000000001");
        assertThat(vo.getNttId()).isEqualTo(10L);
        assertThat(vo.getNttSj()).isEqualTo("테스트 게시글 제목");
        assertThat(vo.getUseAt()).isEqualTo("Y");
        assertThat(vo.getNtcrNm()).isEqualTo("홍길동");
    }

    @Test
    @DisplayName("bbsVOToEntity: BbsVO에서 Bbs 엔티티로 변환 시 복합 키가 올바르게 설정된다")
    void bbsVOToEntity_shouldSetEmbeddedIdFromVO() {
        // given
        BbsVO vo = new BbsVO();
        vo.setBbsId("BBSMSTR_000000000002");
        vo.setNttId(20L);
        vo.setNttSj("변환 테스트");
        vo.setNttCn("본문 내용");
        vo.setFrstRegisterId("USER001");

        // when
        Bbs entity = EgovBoardUtility.bbsVOToEntity(vo);

        // then
        assertThat(entity.getBbsId().getBbsId()).isEqualTo("BBSMSTR_000000000002");
        assertThat(entity.getBbsId().getNttId()).isEqualTo(20L);
        assertThat(entity.getNttSj()).isEqualTo("변환 테스트");
        assertThat(entity.getNtcrId()).isEqualTo("USER001");
        assertThat(entity.getFrstRegistPnttm()).isNotNull();
        assertThat(entity.getLastUpdtPnttm()).isNotNull();
    }

    @Test
    @DisplayName("commentEntityToVO: Comment 복합 키가 CommentVO 필드로 올바르게 매핑된다")
    void commentEntityToVO_shouldMapCompositeKeyToVO() {
        // given
        CommentId commentId = new CommentId();
        commentId.setBbsId("BBSMSTR_000000000003");
        commentId.setNttId(30L);
        commentId.setAnswerNo(1L);

        Comment comment = new Comment();
        comment.setCommentId(commentId);
        comment.setAnswer("댓글 내용");
        comment.setUseAt("Y");

        // when
        CommentVO vo = EgovBoardUtility.commentEntityToVO(comment);

        // then
        assertThat(vo.getBbsId()).isEqualTo("BBSMSTR_000000000003");
        assertThat(vo.getNttId()).isEqualTo(30L);
        assertThat(vo.getAnswerNo()).isEqualTo(1L);
        assertThat(vo.getAnswer()).isEqualTo("댓글 내용");
        assertThat(vo.getUseAt()).isEqualTo("Y");
    }
}
