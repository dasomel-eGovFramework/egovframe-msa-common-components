package egovframework.com.sym.ccm.cca.web;

import egovframework.com.sym.ccm.cca.service.CmmnClCodeVO;
import egovframework.com.sym.ccm.cca.service.EgovCmmnClCodeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * EgovCmmnClCodeAPIController 단위 테스트.
 * MockMvc standalone 구성으로 DB·Eureka·Spring 컨텍스트 없이 실행된다.
 */
@ExtendWith(MockitoExtension.class)
class EgovCmmnClCodeAPIControllerTest {

    @Mock
    private EgovCmmnClCodeService service;

    @InjectMocks
    private EgovCmmnClCodeAPIController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    @DisplayName("POST /sym/ccm/cca/cmmnClCodeList: 목록이 있으면 HTTP 200과 cmmnClCodeList 키를 반환한다")
    void cmmnClCodeList_withData_returnsOkAndList() throws Exception {
        CmmnClCodeVO vo = new CmmnClCodeVO();
        vo.setClCode("A01");
        vo.setClCodeNm("공통코드분류");
        vo.setUseAt("Y");

        given(service.list()).willReturn(List.of(vo));

        mockMvc.perform(post("/sym/ccm/cca/cmmnClCodeList")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cmmnClCodeList").isArray())
                .andExpect(jsonPath("$.cmmnClCodeList[0].clCode").value("A01"))
                .andExpect(jsonPath("$.cmmnClCodeList[0].clCodeNm").value("공통코드분류"));
    }

    @Test
    @DisplayName("POST /sym/ccm/cca/cmmnClCodeList: 목록이 비어 있으면 빈 배열을 반환한다")
    void cmmnClCodeList_emptyResult_returnsOkAndEmptyList() throws Exception {
        given(service.list()).willReturn(Collections.emptyList());

        mockMvc.perform(post("/sym/ccm/cca/cmmnClCodeList")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cmmnClCodeList").isArray())
                .andExpect(jsonPath("$.cmmnClCodeList").isEmpty());
    }

    @Test
    @DisplayName("GET /sym/ccm/cca/cmmnClCodeList: POST 전용 엔드포인트에 GET 요청 시 405를 반환한다")
    void cmmnClCodeList_getMethod_returns405() throws Exception {
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                        .get("/sym/ccm/cca/cmmnClCodeList"))
                .andExpect(status().isMethodNotAllowed());
    }
}
