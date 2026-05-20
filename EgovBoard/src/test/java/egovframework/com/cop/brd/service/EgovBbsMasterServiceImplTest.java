package egovframework.com.cop.brd.service;

import egovframework.com.cop.brd.entity.BbsMaster;
import egovframework.com.cop.brd.repository.EgovBbsMasterOptnRepository;
import egovframework.com.cop.brd.repository.EgovBbsMasterRepository;
import egovframework.com.cop.brd.service.impl.EgovBbsMasterServiceImpl;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

/**
 * EgovBbsMasterServiceImpl 단위 테스트.
 * Mockito만 사용하므로 DB·Eureka·RabbitMQ 연결이 불필요하다.
 */
@ExtendWith(MockitoExtension.class)
class EgovBbsMasterServiceImplTest {

    @Mock
    private EgovBbsMasterOptnRepository optnRepository;

    @Mock
    private EgovBbsMasterRepository masterRepository;

    @Mock
    private JPAQueryFactory queryFactory;

    @InjectMocks
    private EgovBbsMasterServiceImpl service;

    @Test
    @DisplayName("isBbsMasterOptnAccessible: bbsId 또는 uniqId가 null/공백이면 false를 반환한다")
    void isBbsMasterOptnAccessible_nullOrBlankArgs_returnsFalse() {
        assertThat(service.isBbsMasterOptnAccessible(null, "USER01")).isFalse();
        assertThat(service.isBbsMasterOptnAccessible("", "USER01")).isFalse();
        assertThat(service.isBbsMasterOptnAccessible("   ", "USER01")).isFalse();
        assertThat(service.isBbsMasterOptnAccessible("BBSMSTR_0001", null)).isFalse();
        assertThat(service.isBbsMasterOptnAccessible("BBSMSTR_0001", "")).isFalse();
    }

    @Test
    @DisplayName("isBbsMasterOptnAccessible: useAt=Y인 게시판이면 true를 반환한다")
    void isBbsMasterOptnAccessible_activeBoard_returnsTrue() {
        // given
        BbsMaster master = new BbsMaster();
        master.setUseAt("Y");
        given(masterRepository.findById("BBSMSTR_0001")).willReturn(Optional.of(master));

        // when
        boolean result = service.isBbsMasterOptnAccessible("BBSMSTR_0001", "USER01");

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("isBbsMasterOptnAccessible: useAt=N인 게시판이면 false를 반환한다")
    void isBbsMasterOptnAccessible_inactiveBoard_returnsFalse() {
        // given
        BbsMaster master = new BbsMaster();
        master.setUseAt("N");
        given(masterRepository.findById("BBSMSTR_0002")).willReturn(Optional.of(master));

        // when
        boolean result = service.isBbsMasterOptnAccessible("BBSMSTR_0002", "USER01");

        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("isBbsMasterOptnAccessible: 존재하지 않는 게시판 ID이면 false를 반환한다")
    void isBbsMasterOptnAccessible_notFoundBoard_returnsFalse() {
        // given
        given(masterRepository.findById("BBSMSTR_NONE")).willReturn(Optional.empty());

        // when
        boolean result = service.isBbsMasterOptnAccessible("BBSMSTR_NONE", "USER01");

        // then
        assertThat(result).isFalse();
    }
}
