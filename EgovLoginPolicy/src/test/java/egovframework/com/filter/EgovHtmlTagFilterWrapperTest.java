package egovframework.com.filter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * EgovHtmlTagFilterWrapper 단위 테스트.
 * Spring 컨텍스트 없이 XSS 방어용 HTML 이스케이프 로직을 검증한다.
 */
class EgovHtmlTagFilterWrapperTest {

    private MockHttpServletRequest mockRequest;

    @BeforeEach
    void setUp() {
        mockRequest = new MockHttpServletRequest();
    }

    // ---------------------------------------------------------------
    // getParameter
    // ---------------------------------------------------------------

    @Test
    @DisplayName("getParameter: HTML 특수문자가 이스케이프된다")
    void getParameter_htmlSpecialChars_escaped() {
        mockRequest.setParameter("input", "<script>alert('xss')</script>");
        EgovHtmlTagFilterWrapper wrapper = new EgovHtmlTagFilterWrapper(mockRequest);

        String result = wrapper.getParameter("input");

        // escapeHtml4는 홑따옴표(')를 이스케이프하지 않으므로 그대로 유지된다
        assertThat(result).isEqualTo("&lt;script&gt;alert('xss')&lt;/script&gt;");
    }

    @Test
    @DisplayName("getParameter: 일반 문자열은 변경 없이 반환된다")
    void getParameter_plainText_unchanged() {
        mockRequest.setParameter("name", "홍길동");
        EgovHtmlTagFilterWrapper wrapper = new EgovHtmlTagFilterWrapper(mockRequest);

        assertThat(wrapper.getParameter("name")).isEqualTo("홍길동");
    }

    @Test
    @DisplayName("getParameter: 존재하지 않는 파라미터는 null을 반환한다")
    void getParameter_missingParam_returnsNull() {
        EgovHtmlTagFilterWrapper wrapper = new EgovHtmlTagFilterWrapper(mockRequest);

        assertThat(wrapper.getParameter("notExist")).isNull();
    }

    @Test
    @DisplayName("getParameter: & 문자는 &amp;로 이스케이프된다")
    void getParameter_ampersand_escaped() {
        mockRequest.setParameter("q", "A & B");
        EgovHtmlTagFilterWrapper wrapper = new EgovHtmlTagFilterWrapper(mockRequest);

        assertThat(wrapper.getParameter("q")).isEqualTo("A &amp; B");
    }

    @Test
    @DisplayName("getParameter: 쌍따옴표가 &quot;로 이스케이프된다")
    void getParameter_doubleQuote_escaped() {
        mockRequest.setParameter("attr", "value\"injected");
        EgovHtmlTagFilterWrapper wrapper = new EgovHtmlTagFilterWrapper(mockRequest);

        assertThat(wrapper.getParameter("attr")).isEqualTo("value&quot;injected");
    }

    @Test
    @DisplayName("getParameter: 홑따옴표는 escapeHtml4 규격상 이스케이프되지 않는다")
    void getParameter_singleQuote_notEscaped() {
        mockRequest.setParameter("attr", "it's fine");
        EgovHtmlTagFilterWrapper wrapper = new EgovHtmlTagFilterWrapper(mockRequest);

        assertThat(wrapper.getParameter("attr")).isEqualTo("it's fine");
    }

    // ---------------------------------------------------------------
    // getParameterValues
    // ---------------------------------------------------------------

    @Test
    @DisplayName("getParameterValues: 배열 내 모든 값이 이스케이프된다")
    void getParameterValues_multipleValues_allEscaped() {
        mockRequest.addParameter("tags", "<b>bold</b>");
        mockRequest.addParameter("tags", "<i>italic</i>");
        EgovHtmlTagFilterWrapper wrapper = new EgovHtmlTagFilterWrapper(mockRequest);

        String[] values = wrapper.getParameterValues("tags");

        assertThat(values).containsExactly(
                "&lt;b&gt;bold&lt;/b&gt;",
                "&lt;i&gt;italic&lt;/i&gt;"
        );
    }

    @Test
    @DisplayName("getParameterValues: 존재하지 않는 파라미터는 빈 배열을 반환한다")
    void getParameterValues_missingParam_returnsEmptyArray() {
        EgovHtmlTagFilterWrapper wrapper = new EgovHtmlTagFilterWrapper(mockRequest);

        String[] values = wrapper.getParameterValues("notExist");

        assertThat(values).isNotNull().isEmpty();
    }

    // ---------------------------------------------------------------
    // getParameterMap
    // ---------------------------------------------------------------

    @Test
    @DisplayName("getParameterMap: 맵 내 모든 파라미터 값이 이스케이프된다")
    void getParameterMap_allValuesEscaped() {
        mockRequest.setParameter("title", "<h1>제목</h1>");
        mockRequest.setParameter("normal", "일반값");
        EgovHtmlTagFilterWrapper wrapper = new EgovHtmlTagFilterWrapper(mockRequest);

        var paramMap = wrapper.getParameterMap();

        assertThat(paramMap.get("title")).containsExactly("&lt;h1&gt;제목&lt;/h1&gt;");
        assertThat(paramMap.get("normal")).containsExactly("일반값");
    }

    @Test
    @DisplayName("getParameterMap: 빈 요청이면 빈 맵을 반환한다")
    void getParameterMap_emptyRequest_returnsEmptyMap() {
        EgovHtmlTagFilterWrapper wrapper = new EgovHtmlTagFilterWrapper(mockRequest);

        assertThat(wrapper.getParameterMap()).isEmpty();
    }
}
