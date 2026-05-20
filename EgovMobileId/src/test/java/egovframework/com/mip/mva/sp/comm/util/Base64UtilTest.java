package egovframework.com.mip.mva.sp.comm.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @Project 모바일 운전면허증 서비스 구축 사업
 * @PackageName egovframework.com.mip.mva.sp.comm.util
 * @FileName Base64UtilTest.java
 * @Description Base64Util 단위 테스트
 *
 * <pre>
 * ==================================================
 * DATE            AUTHOR           NOTE
 * ==================================================
 * 2026. 5. 20.    박성완           최초생성
 * </pre>
 */
@ExtendWith(MockitoExtension.class)
class Base64UtilTest {

    @Test
    @DisplayName("encode(String): 문자열을 Base64 URL 인코딩한 결과가 null이 아니고 비어있지 않다")
    void encode_string_returnsNonEmptyBase64() {
        String encoded = Base64Util.encode("hello");

        assertThat(encoded).isNotNull().isNotEmpty();
    }

    @Test
    @DisplayName("encode(String) → decode: 인코딩 후 디코딩하면 원래 문자열과 동일하다")
    void encode_then_decode_returnsOriginal() {
        String original = "mobile-id-test-value";

        String encoded = Base64Util.encode(original);
        String decoded = Base64Util.decode(encoded);

        assertThat(decoded).isEqualTo(original);
    }

    @Test
    @DisplayName("encode(byte[]) → decodeToByte: 바이트 배열 인코딩 후 디코딩하면 원본과 동일하다")
    void encode_bytes_then_decodeToByte_returnsOriginal() {
        byte[] original = "trxcode-payload".getBytes();

        String encoded = Base64Util.encode(original);
        byte[] decoded = Base64Util.decodeToByte(encoded);

        assertThat(decoded).isEqualTo(original);
    }

    @Test
    @DisplayName("encode: 빈 문자열도 정상적으로 인코딩·디코딩된다")
    void encode_emptyString_roundTrip() {
        String original = "";

        String encoded = Base64Util.encode(original);
        String decoded = Base64Util.decode(encoded);

        assertThat(decoded).isEqualTo(original);
    }

    @Test
    @DisplayName("encode(String): 반환값에 URL-unsafe 문자(+, /)가 포함되지 않는다")
    void encode_string_usesUrlSafeAlphabet() {
        String encoded = Base64Util.encode("any test input 12345!@#");

        assertThat(encoded).doesNotContain("+", "/");
    }
}
