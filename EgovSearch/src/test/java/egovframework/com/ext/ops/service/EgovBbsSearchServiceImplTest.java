package egovframework.com.ext.ops.service;

import egovframework.com.config.ConfigUtils;
import egovframework.com.config.EgovSearchConfig;
import egovframework.com.ext.ops.service.impl.EgovBbsSearchServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch.core.SearchRequest;
import org.opensearch.client.opensearch.core.SearchResponse;
import org.opensearch.client.opensearch.core.search.Hit;
import org.opensearch.client.opensearch.core.search.HitsMetadata;
import org.opensearch.client.opensearch.core.search.TotalHits;
import org.springframework.data.domain.Page;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * EgovBbsSearchServiceImpl 단위 테스트.
 * OpenSearchClient 및 ConfigUtils를 Mock 처리하여 외부 의존성 없이 실행한다.
 */
@ExtendWith(MockitoExtension.class)
class EgovBbsSearchServiceImplTest {

    @Mock
    private OpenSearchClient openSearchClient;

    @Mock
    private ConfigUtils configUtils;

    @InjectMocks
    private EgovBbsSearchServiceImpl service;

    @BeforeEach
    void setUp() {
        // @Value 필드를 ReflectionTestUtils로 주입
        ReflectionTestUtils.setField(service, "textIndexName", "bbs-text-index");
        ReflectionTestUtils.setField(service, "vectorIndexName", "bbs-vector-index");
        ReflectionTestUtils.setField(service, "textSearchCount", 100);
        ReflectionTestUtils.setField(service, "textSearchPageSize", 10);
        ReflectionTestUtils.setField(service, "vectorSearchCount", 50);
        ReflectionTestUtils.setField(service, "vectorSearchPageSize", 10);

        // afterPropertiesSet: modelPath/tokenizerPath가 없으면 embeddingModel은 null 유지
        when(configUtils.loadConfig()).thenReturn(null);
        service.afterPropertiesSet();
    }

    @Test
    @DisplayName("textSearch: 검색어가 없으면 빈 쿼리로 요청하고 결과를 Page로 반환한다")
    void textSearch_emptyKeyword_returnsPage() throws IOException {
        BoardVO boardVO = new BoardVO();
        boardVO.setPageIndex(1);
        boardVO.setPageSize(10);
        boardVO.setSearchWrd("");

        Page<BoardVO> mockPage = buildMockSearchResponse(boardVO);
        when(openSearchClient.search(any(SearchRequest.class), eq(BoardVO.class)))
                .thenReturn(buildRawSearchResponse(Collections.emptyList(), 0L));

        Page<BoardVO> result = service.textSearch(boardVO);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).isEmpty();
        assertThat(result.getTotalElements()).isZero();
    }

    @Test
    @DisplayName("textSearch: 검색조건 1(제목)로 검색하면 Page 결과를 반환한다")
    void textSearch_byTitle_returnsPage() throws IOException {
        BoardVO boardVO = new BoardVO();
        boardVO.setPageIndex(1);
        boardVO.setPageSize(10);
        boardVO.setSearchCnd("1");
        boardVO.setSearchWrd("테스트");

        BoardVO hit1 = new BoardVO();
        hit1.setNttId(1L);
        hit1.setNttSj("테스트 게시물");

        when(openSearchClient.search(any(SearchRequest.class), eq(BoardVO.class)))
                .thenReturn(buildRawSearchResponse(List.of(hit1), 1L));

        Page<BoardVO> result = service.textSearch(boardVO);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getNttSj()).isEqualTo("테스트 게시물");
    }

    @Test
    @DisplayName("textSearch: OpenSearch IO 예외 발생 시 null을 반환한다")
    void textSearch_ioException_returnsNull() throws IOException {
        BoardVO boardVO = new BoardVO();
        boardVO.setPageIndex(1);
        boardVO.setPageSize(10);
        boardVO.setSearchCnd("1");
        boardVO.setSearchWrd("오류테스트");

        when(openSearchClient.search(any(SearchRequest.class), eq(BoardVO.class)))
                .thenThrow(new IOException("connection refused"));

        Page<BoardVO> result = service.textSearch(boardVO);

        assertThat(result).isNull();
    }

    @Test
    @DisplayName("textSearch: 2페이지 요청 시 해당 페이지 데이터를 반환한다")
    void textSearch_secondPage_returnsPageContent() throws IOException {
        BoardVO boardVO = new BoardVO();
        boardVO.setPageIndex(2);
        boardVO.setPageSize(2);
        boardVO.setSearchCnd("2");
        boardVO.setSearchWrd("내용검색");

        // 3건 반환 — 페이지 크기 2, 2페이지이면 마지막 1건만 포함
        BoardVO item1 = new BoardVO(); item1.setNttId(1L);
        BoardVO item2 = new BoardVO(); item2.setNttId(2L);
        BoardVO item3 = new BoardVO(); item3.setNttId(3L);

        when(openSearchClient.search(any(SearchRequest.class), eq(BoardVO.class)))
                .thenReturn(buildRawSearchResponse(List.of(item1, item2, item3), 3L));

        Page<BoardVO> result = service.textSearch(boardVO);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getNttId()).isEqualTo(3L);
    }

    // ---------------------------------------------------------------
    // helpers
    // ---------------------------------------------------------------

    private Page<BoardVO> buildMockSearchResponse(BoardVO boardVO) {
        return null; // unused placeholder
    }

    @SuppressWarnings("unchecked")
    private <T> SearchResponse<T> buildRawSearchResponse(List<T> items, long total) {
        SearchResponse<T> response = mock(SearchResponse.class);
        HitsMetadata<T> hitsMetadata = mock(HitsMetadata.class);
        TotalHits totalHits = mock(TotalHits.class);

        List<Hit<T>> hits = items.stream().map(item -> {
            Hit<T> hit = (Hit<T>) mock(Hit.class);
            when(hit.source()).thenReturn(item);
            when(hit.score()).thenReturn(1.0);
            return hit;
        }).toList();

        when(response.hits()).thenReturn(hitsMetadata);
        when(hitsMetadata.hits()).thenReturn(hits);
        when(hitsMetadata.total()).thenReturn(totalHits);
        when(totalHits.value()).thenReturn(total);

        return response;
    }
}
