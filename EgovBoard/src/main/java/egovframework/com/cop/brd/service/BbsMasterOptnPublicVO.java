package egovframework.com.cop.brd.service;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 게시판 옵션 조회 API 응답용 — 등록·수정 메타데이터(PII) 제외.
 */
@Getter
@AllArgsConstructor
public class BbsMasterOptnPublicVO {

    private final String bbsId;
    private final String answerAt;
    private final String stsfdgAt;

    public static BbsMasterOptnPublicVO from(BbsMasterOptnVO src) {
        if (src == null) {
            return null;
        }
        return new BbsMasterOptnPublicVO(
                src.getBbsId(),
                src.getAnswerAt(),
                src.getStsfdgAt()
        );
    }
}
