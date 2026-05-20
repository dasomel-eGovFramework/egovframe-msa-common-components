package egovframework.com.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * EgovMainManageController 단위 테스트.
 * MockMvc를 standalone으로 구성하여 DB·Eureka·Spring 컨텍스트 없이 실행된다.
 */
@ExtendWith(MockitoExtension.class)
class EgovMainManageControllerTest {

    @InjectMocks
    private EgovMainManageController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    @DisplayName("GET / : HTTP 200을 반환하고 뷰 이름이 'index'이다")
    void index_get_returnsOkAndIndexView() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    @DisplayName("POST / : HTTP 200을 반환하고 뷰 이름이 'index'이다")
    void index_post_returnsOkAndIndexView() throws Exception {
        mockMvc.perform(post("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    @DisplayName("GET /unknown : 매핑되지 않은 경로는 404를 반환한다")
    void unknownPath_get_returns404() throws Exception {
        mockMvc.perform(get("/unknown"))
                .andExpect(status().isNotFound());
    }
}
