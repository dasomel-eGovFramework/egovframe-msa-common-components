package egovframework.com.sym.ccm.cca.service.impl;

import egovframework.com.sym.ccm.cca.entity.CmmnClCode;
import egovframework.com.sym.ccm.cca.entity.CmmnCode;
import egovframework.com.sym.ccm.cca.reposiroty.EgovCmmnCodeManageRepository;
import egovframework.com.sym.ccm.cca.service.CmmnCodeVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 * EgovCmmnCodeManageServiceImpl 단위 테스트.
 * DB·Spring 컨텍스트 없이 Mockito만으로 실행된다.
 */
@ExtendWith(MockitoExtension.class)
class EgovCmmnCodeManageServiceImplTest {

    @Mock
    private EgovCmmnCodeManageRepository repository;

    @InjectMocks
    private EgovCmmnCodeManageServiceImpl service;

    private CmmnCode sampleEntity;
    private CmmnCodeVO sampleVO;

    @BeforeEach
    void setUp() {
        CmmnClCode clCode = new CmmnClCode();
        clCode.setClCode("A01");
        clCode.setClCodeNm("공통코드분류");

        sampleEntity = new CmmnCode();
        sampleEntity.setCodeId("CODE001");
        sampleEntity.setCodeIdNm("테스트코드");
        sampleEntity.setCodeIdDc("테스트용 코드 설명");
        sampleEntity.setUseAt("Y");
        sampleEntity.setClCode("A01");
        sampleEntity.setCmmnClCode(clCode);
        sampleEntity.setFrstRegistPnttm(LocalDateTime.now());
        sampleEntity.setFrstRegisterId("testUser");
        sampleEntity.setLastUpdtPnttm(LocalDateTime.now());
        sampleEntity.setLastUpdusrId("testUser");

        sampleVO = new CmmnCodeVO();
        sampleVO.setCodeId("CODE001");
        sampleVO.setCodeIdNm("테스트코드");
        sampleVO.setCodeIdDc("테스트용 코드 설명");
        sampleVO.setUseAt("Y");
        sampleVO.setClCode("A01");
    }

    @Test
    @DisplayName("list: 검색 조건 없이 호출하면 repository.findAll(Pageable)을 사용한다")
    void list_noSearchKeyword_callsFindAllWithPageable() {
        CmmnCodeVO searchVO = new CmmnCodeVO();
        searchVO.setFirstIndex(0);
        searchVO.setPageSize(10);

        Page<CmmnCode> page = new PageImpl<>(List.of(sampleEntity));
        given(repository.findAll(any(Pageable.class))).willReturn(page);

        Page<CmmnCodeVO> result = service.list(searchVO);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getCodeId()).isEqualTo("CODE001");
        verify(repository).findAll(any(Pageable.class));
    }

    @Test
    @DisplayName("list: searchCondition=1(codeId 검색)이면 Specification을 사용한다")
    void list_searchByCodeId_callsFindAllWithSpec() {
        CmmnCodeVO searchVO = new CmmnCodeVO();
        searchVO.setFirstIndex(0);
        searchVO.setPageSize(10);
        searchVO.setSearchCondition("1");
        searchVO.setSearchKeyword("CODE");

        Page<CmmnCode> page = new PageImpl<>(List.of(sampleEntity));
        given(repository.findAll(any(Specification.class), any(Pageable.class))).willReturn(page);

        Page<CmmnCodeVO> result = service.list(searchVO);

        assertThat(result.getContent()).hasSize(1);
        verify(repository).findAll(any(Specification.class), any(Pageable.class));
    }

    @Test
    @DisplayName("detail: 존재하는 codeId 조회 시 VO를 반환한다")
    void detail_existingCodeId_returnsVO() {
        given(repository.findById("CODE001")).willReturn(Optional.of(sampleEntity));

        CmmnCodeVO result = service.detail(sampleVO);

        assertThat(result).isNotNull();
        assertThat(result.getCodeId()).isEqualTo("CODE001");
        assertThat(result.getCodeIdNm()).isEqualTo("테스트코드");
    }

    @Test
    @DisplayName("detail: 존재하지 않는 codeId 조회 시 null을 반환한다")
    void detail_notExistingCodeId_returnsNull() {
        given(repository.findById("NOTEXIST")).willReturn(Optional.empty());

        CmmnCodeVO searchVO = new CmmnCodeVO();
        searchVO.setCodeId("NOTEXIST");
        CmmnCodeVO result = service.detail(searchVO);

        assertThat(result).isNull();
    }

    @Test
    @DisplayName("insert: 저장 후 VO를 반환한다")
    void insert_validVO_returnsSavedVO() {
        CmmnCode savedEntity = new CmmnCode();
        savedEntity.setCodeId("CODE001");
        savedEntity.setCodeIdNm("테스트코드");
        savedEntity.setCodeIdDc("테스트용 코드 설명");
        savedEntity.setUseAt("Y");
        savedEntity.setClCode("A01");
        given(repository.save(any(CmmnCode.class))).willReturn(savedEntity);

        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("uniqId", "user01");

        CmmnCodeVO result = service.insert(sampleVO, userInfo);

        assertThat(result).isNotNull();
        assertThat(result.getCodeId()).isEqualTo("CODE001");
        verify(repository).save(any(CmmnCode.class));
    }

    @Test
    @DisplayName("delete: useAt을 N으로 변경하고 저장한다")
    void delete_existingCode_setsUseAtToN() {
        CmmnCode entityToDelete = new CmmnCode();
        entityToDelete.setCodeId("CODE001");
        entityToDelete.setUseAt("Y");

        CmmnCode savedEntity = new CmmnCode();
        savedEntity.setCodeId("CODE001");
        savedEntity.setUseAt("N");

        given(repository.findById("CODE001")).willReturn(Optional.of(entityToDelete));
        given(repository.save(any(CmmnCode.class))).willReturn(savedEntity);

        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("uniqId", "user01");

        CmmnCodeVO result = service.delete(sampleVO, userInfo);

        assertThat(result).isNotNull();
        assertThat(result.getUseAt()).isEqualTo("N");
    }
}
