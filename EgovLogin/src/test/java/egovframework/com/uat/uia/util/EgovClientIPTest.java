package egovframework.com.uat.uia.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * EgovClientIP 단위 테스트.
 * 외부 의존성 없이 순수 로직만 검증한다.
 */
class EgovClientIPTest {

    @Test
    @DisplayName("getClientIp: 반환값이 null이 아니고 빈 문자열이 아니다")
    void getClientIp_returnsNonBlankString() {
        String ip = EgovClientIP.getClientIp();
        assertThat(ip).isNotNull().isNotBlank();
    }

    @Test
    @DisplayName("getClientIp: 반환값이 IP 형식이거나 네트워크 접근 불가 시 '0.0.0.0'이다")
    void getClientIp_returnsValidIpOrFallback() {
        String ip = EgovClientIP.getClientIp();
        // IPv4, IPv6, 또는 fallback '0.0.0.0' 중 하나여야 한다
        boolean isIpv4 = ip.matches("\\d{1,3}(\\.\\d{1,3}){3}");
        boolean isIpv6 = ip.contains(":");
        boolean isFallback = "0.0.0.0".equals(ip);
        assertThat(isIpv4 || isIpv6 || isFallback)
                .as("IP 형식이거나 fallback '0.0.0.0'이어야 한다: %s", ip)
                .isTrue();
    }
}
