package egovframework.com.cop.brd.service;

import org.egovframe.rte.fdl.cmmn.exception.FdlException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface EgovBoardService {

    List<BoardDTO> noticeList(BbsVO bbsVO);

    Map<String, Object> list(BbsVO bbsVO);

    BoardDTO detail(BbsVO bbsVO, Map<String, String> userInfo);

    BbsVO insert(BbsVO bbsVO, List<MultipartFile> files, Map<String, String> userInfo) throws IOException, FdlException;

    BbsVO update(BbsVO bbsVO, List<MultipartFile> files, Map<String, String> userInfo) throws IOException, FdlException;

    BbsVO delete(BbsVO bbsVO, Map<String, String> userInfo);

    /**
     * 수정 화면 진입 및 수정 API에서 호출 — 등록자와 요청자 일치 여부 검증.
     */
    void assertUpdateAuthorized(BbsVO bbsVO, Map<String, String> userInfo);

}
