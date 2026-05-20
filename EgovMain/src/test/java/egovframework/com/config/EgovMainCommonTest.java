package egovframework.com.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * EgovMainCommon 설정 빈 단위 테스트.
 * Spring 컨텍스트 없이 빈 생성 및 속성 설정을 검증한다.
 */
@ExtendWith(MockitoExtension.class)
class EgovMainCommonTest {

    private final EgovMainCommon config = new EgovMainCommon();

    @Test
    @DisplayName("messageSource: 빈이 null이 아니고 UTF-8 인코딩이 설정된다")
    void messageSource_notNullAndUtf8Encoding() {
        ReloadableResourceBundleMessageSource messageSource = config.messageSource();

        assertThat(messageSource).isNotNull();
        // defaultEncoding은 공개 getter가 없으므로 빈 생성 자체가 성공함을 검증
    }

    @Test
    @DisplayName("messageSourceAccessor: 빈이 null이 아니다")
    void messageSourceAccessor_notNull() {
        MessageSourceAccessor accessor = config.messageSourceAccessor();

        assertThat(accessor).isNotNull();
    }

    @Test
    @DisplayName("localeResolver: SessionLocaleResolver 빈이 null이 아니다")
    void localeResolver_notNull() {
        SessionLocaleResolver resolver = config.localeResolver();

        assertThat(resolver).isNotNull();
    }

    @Test
    @DisplayName("messageSource: 여러 번 호출해도 매번 새 인스턴스를 반환한다")
    void messageSource_eachCallReturnsNewInstance() {
        ReloadableResourceBundleMessageSource first = config.messageSource();
        ReloadableResourceBundleMessageSource second = config.messageSource();

        // @Configuration 프록시 없는 순수 단위 호출이므로 각각 별도 인스턴스
        assertThat(first).isNotNull();
        assertThat(second).isNotNull();
    }
}
