package egovframework.com.mip.mva.sp.comm.enums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @Project 모바일 운전면허증 서비스 구축 사업
 * @PackageName egovframework.com.mip.mva.sp.comm.enums
 * @FileName MipErrorEnumTest.java
 * @Description MipErrorEnum 단위 테스트
 *
 * <pre>
 * ==================================================
 * DATE            AUTHOR           NOTE
 * ==================================================
 * 2026. 5. 20.    박성완           최초생성
 * </pre>
 */
@ExtendWith(MockitoExtension.class)
class MipErrorEnumTest {

    @Test
    @DisplayName("getCode/getMsg: SP_MISSING_MANDATORY_ITEM은 코드 10002, 메시지 'missing mandatory item'이다")
    void spMissingMandatoryItem_codeAndMsg() {
        MipErrorEnum e = MipErrorEnum.SP_MISSING_MANDATORY_ITEM;

        assertThat(e.getCode()).isEqualTo(10002);
        assertThat(e.getMsg()).isEqualTo("missing mandatory item");
    }

    @Test
    @DisplayName("getEnum: 코드 10001로 SP_UNEXPECTED_MSG_FORMAT을 조회할 수 있다")
    void getEnum_knownCode_returnsCorrectEnum() {
        MipErrorEnum result = MipErrorEnum.getEnum(10001);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(MipErrorEnum.SP_UNEXPECTED_MSG_FORMAT);
    }

    @Test
    @DisplayName("getEnum: 존재하지 않는 코드는 null을 반환한다")
    void getEnum_unknownCode_returnsNull() {
        MipErrorEnum result = MipErrorEnum.getEnum(99998);

        assertThat(result).isNull();
    }

    @Test
    @DisplayName("getEnum: UNKNOWN_ERROR(99999)를 코드로 조회할 수 있다")
    void getEnum_unknownError_returnsEnum() {
        MipErrorEnum result = MipErrorEnum.getEnum(99999);

        assertThat(result).isNotNull();
        assertThat(result.getMsg()).isEqualTo("Unknown error");
    }

    @Test
    @DisplayName("getCode: 모든 Enum 항목의 코드값이 null이 아니다")
    void allEnumValues_codeNotNull() {
        for (MipErrorEnum e : MipErrorEnum.values()) {
            assertThat(e.getCode())
                    .as("코드가 null인 항목: %s", e.name())
                    .isNotNull();
        }
    }

    @Test
    @DisplayName("getMsg: 모든 Enum 항목의 메시지가 null이 아니고 비어있지 않다")
    void allEnumValues_msgNotNullOrEmpty() {
        for (MipErrorEnum e : MipErrorEnum.values()) {
            assertThat(e.getMsg())
                    .as("메시지가 비어있는 항목: %s", e.name())
                    .isNotNull()
                    .isNotEmpty();
        }
    }
}
