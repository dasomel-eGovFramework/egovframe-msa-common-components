package egovframework.com.sec.ram.service.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import egovframework.com.sec.ram.entity.AuthorInfo;
import egovframework.com.sec.ram.repository.EgovAuthorInfoRepository;
import egovframework.com.sec.ram.service.AuthorInfoVO;
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

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 * EgovAuthorInfoServiceImpl 단위 테스트.
 * DB·Eureka·Spring 컨텍스트 없이 Mockito만으로 서비스 로직을 검증한다.
 */
@ExtendWith(MockitoExtension.class)
class EgovAuthorInfoServiceImplTest {

    @Mock
    private EgovAuthorInfoRepository repository;

    @Mock
    private JPAQueryFactory queryFactory;

    @InjectMocks
    private EgovAuthorInfoServiceImpl service;

    private AuthorInfo sampleEntity;

    @BeforeEach
    void setUp() {
        sampleEntity = new AuthorInfo();
        sampleEntity.setAuthorCode("AUTH-001");
        sampleEntity.setAuthorNm("테스트 권한");
        sampleEntity.setAuthorDc("테스트 권한 설명");
        sampleEntity.setAuthorCreatDe("2024-01-01 00:00:00");
    }

    @Test
    @DisplayName("list: 검색 조건 없을 때 findAll(Pageable)을 호출하고 Page를 반환한다")
    void list_noSearchCondition_callsFindAllWithPageable() {
        AuthorInfoVO searchVO = new AuthorInfoVO();
        searchVO.setFirstIndex(0);
        searchVO.setRecordCountPerPage(10);

        Page<AuthorInfo> page = new PageImpl<>(List.of(sampleEntity));
        given(repository.findAll(any(Pageable.class))).willReturn(page);

        Page<AuthorInfoVO> result = service.list(searchVO);

        assertThat(result).isNotNull();
        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent().get(0).getAuthorCode()).isEqualTo("AUTH-001");
        verify(repository).findAll(any(Pageable.class));
    }

    @Test
    @DisplayName("list: 검색 조건 1 + 키워드가 있을 때 Specification을 사용해 findAll을 호출한다")
    @SuppressWarnings("unchecked")
    void list_withSearchCondition_callsFindAllWithSpec() {
        AuthorInfoVO searchVO = new AuthorInfoVO();
        searchVO.setFirstIndex(0);
        searchVO.setRecordCountPerPage(10);
        searchVO.setSearchCondition("1");
        searchVO.setSearchKeyword("테스트");

        Page<AuthorInfo> page = new PageImpl<>(List.of(sampleEntity));
        given(repository.findAll(any(Specification.class), any(Pageable.class))).willReturn(page);

        Page<AuthorInfoVO> result = service.list(searchVO);

        assertThat(result).isNotNull();
        assertThat(result.getTotalElements()).isEqualTo(1);
        verify(repository).findAll(any(Specification.class), any(Pageable.class));
    }

    @Test
    @DisplayName("detail: authorCode로 조회 성공 시 AuthorInfoVO를 반환한다")
    void detail_found_returnsVO() {
        AuthorInfoVO query = new AuthorInfoVO();
        query.setAuthorCode("AUTH-001");

        given(repository.findById("AUTH-001")).willReturn(Optional.of(sampleEntity));

        AuthorInfoVO result = service.detail(query);

        assertThat(result).isNotNull();
        assertThat(result.getAuthorCode()).isEqualTo("AUTH-001");
        assertThat(result.getAuthorNm()).isEqualTo("테스트 권한");
    }

    @Test
    @DisplayName("detail: authorCode가 없을 때 null을 반환한다")
    void detail_notFound_returnsNull() {
        AuthorInfoVO query = new AuthorInfoVO();
        query.setAuthorCode("NONE");

        given(repository.findById("NONE")).willReturn(Optional.empty());

        AuthorInfoVO result = service.detail(query);

        assertThat(result).isNull();
    }

    @Test
    @DisplayName("insert: 저장 성공 시 authorCreatDe가 설정된 AuthorInfoVO를 반환한다")
    void insert_success_returnsVOWithCreatDe() {
        AuthorInfo savedEntity = new AuthorInfo();
        savedEntity.setAuthorCode("AUTH-002");
        savedEntity.setAuthorNm("신규 권한");
        savedEntity.setAuthorDc("신규 권한 설명");
        savedEntity.setAuthorCreatDe("2024-06-01 10:00:00");

        given(repository.save(any(AuthorInfo.class))).willReturn(savedEntity);

        AuthorInfoVO input = new AuthorInfoVO();
        input.setAuthorCode("AUTH-002");
        input.setAuthorNm("신규 권한");

        AuthorInfoVO result = service.insert(input);

        assertThat(result).isNotNull();
        assertThat(result.getAuthorCode()).isEqualTo("AUTH-002");
        assertThat(result.getAuthorCreatDe()).isNotNull();
        verify(repository).save(any(AuthorInfo.class));
    }
}
