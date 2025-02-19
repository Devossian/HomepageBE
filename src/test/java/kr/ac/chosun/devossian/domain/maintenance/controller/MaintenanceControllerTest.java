package kr.ac.chosun.devossian.domain.maintenance.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.ac.chosun.devossian.domain.maintenance.dto.MaintenanceCreateRequestDto;
import kr.ac.chosun.devossian.domain.maintenance.dto.MaintenanceResponseDto;
import kr.ac.chosun.devossian.domain.maintenance.dto.MaintenanceUpdateRequestDto;
import kr.ac.chosun.devossian.domain.maintenance.service.MaintenanceService;
import kr.ac.chosun.devossian.domain.user.domain.User;
import kr.ac.chosun.devossian.global.result.ResultCodeMessage;
import kr.ac.chosun.devossian.global.result.ResultResponseDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 응답 데이터, 예외 처리, 응답 상태 코드 중점으로 테스트
 */
@WebMvcTest(MaintenanceController.class)
class MaintenanceControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private User user;

    @MockitoBean
    private MaintenanceService maintenanceService;

    private MaintenanceCreateRequestDto requestDto;
    private MaintenanceResponseDto responseDto;

    @BeforeEach
    public void setUp() {
        // 클래스 전역에서 한 번만 실행
        requestDto = new MaintenanceCreateRequestDto();
        requestDto.setTitle("title");
        requestDto.setContent("content1234");
        requestDto.setTags(List.of("Spring", "Django"));

        responseDto = new MaintenanceResponseDto();
        responseDto.setTitle("title");
        responseDto.setContent("content1234");
        responseDto.setTags(List.of("Spring", "Django"));
    }
    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testCreateMaintenance() throws Exception {
        Mockito.when(maintenanceService.create(any(MaintenanceCreateRequestDto.class), any(User.class)))
                        .thenReturn(responseDto);

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/maintenance")
                .with(csrf().asHeader())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))  // CSRF 토큰을 헤더로 설정
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(ResultCodeMessage.POST_SUCCESS.getMessage()));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testReadMaintenance() throws Exception {
        // given
        Long maintenanceId = 1L;
        MaintenanceResponseDto responseDto = new MaintenanceResponseDto();
        // responseDto 필드 설정...

        Mockito.when(maintenanceService.findById(maintenanceId))
                .thenReturn(responseDto);

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/maintenance/{id}", maintenanceId)
                .with(csrf().asHeader()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(ResultCodeMessage.USER_POST_VIEW_SUCCESS.getMessage()))
                .andExpect(jsonPath("$.data").exists());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testUpdateMaintenance() throws Exception {
        // given
        Long maintenanceId = 1L;

        Mockito.when(maintenanceService.update(any(MaintenanceUpdateRequestDto.class), anyLong()))
                .thenReturn(responseDto);

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/maintenance/{id}", maintenanceId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto))
                        .with(csrf().asHeader()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(ResultCodeMessage.POST_UPDATE_SUCCESS.getMessage()))
                .andExpect(jsonPath("$.data").exists());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testDeleteMaintenance() throws Exception {
        // given
        Long maintenanceId = 1L;

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/maintenance/{id}", maintenanceId)
                .with(csrf().asHeader()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(ResultCodeMessage.POST_DELETE_SUCCESS.getMessage()));
    }
}