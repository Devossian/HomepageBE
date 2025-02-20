package kr.ac.chosun.devossian.domain.maintenance.controller;

import jakarta.validation.Valid;
import kr.ac.chosun.devossian.domain.maintenance.domain.Type;
import kr.ac.chosun.devossian.domain.maintenance.dto.MaintenanceCreateRequestDto;
import kr.ac.chosun.devossian.domain.maintenance.dto.MaintenanceResponseDto;
import kr.ac.chosun.devossian.domain.maintenance.dto.MaintenanceSearchRequestDto;
import kr.ac.chosun.devossian.domain.maintenance.dto.MaintenanceUpdateRequestDto;
import kr.ac.chosun.devossian.domain.maintenance.service.MaintenanceService;
import kr.ac.chosun.devossian.domain.user.domain.User;
import kr.ac.chosun.devossian.global.result.ResultCodeMessage;
import kr.ac.chosun.devossian.global.result.ResultResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;



@RestController
@RequiredArgsConstructor
@RequestMapping("/api/maintenance")
public class MaintenanceController {
    private final MaintenanceService maintenanceService;


    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid MaintenanceCreateRequestDto maintenanceCreateRequestDto,
                                    @AuthenticationPrincipal User user){
        // 게시글 생성
        MaintenanceResponseDto dto = maintenanceService.create(maintenanceCreateRequestDto, user);

        return ResponseEntity.ok(ResultResponseDTO.of(ResultCodeMessage.POST_SUCCESS, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResultResponseDTO> read(@PathVariable Long id){
        MaintenanceResponseDto dto = maintenanceService.findById(id);

        // 게시글 DTO로 변환
        return ResponseEntity.ok(ResultResponseDTO.of(ResultCodeMessage.USER_POST_VIEW_SUCCESS, dto));
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody @Valid MaintenanceUpdateRequestDto maintenanceUpdateRequestDto,
                                    @PathVariable Long id){
        // 다른 관리자 계정으로도 수정 가능하므로, user 검증 x
        MaintenanceResponseDto dto = maintenanceService.update(maintenanceUpdateRequestDto, id);

        // 게시글 DTO로 변환
        return ResponseEntity.ok(ResultResponseDTO.of(ResultCodeMessage.POST_UPDATE_SUCCESS, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResultResponseDTO> delete(@PathVariable Long id){
        // 관리자 권한만 확인 되면 삭제 가능
        maintenanceService.deleteById(id);

        return ResponseEntity.ok(ResultResponseDTO.of(ResultCodeMessage.POST_DELETE_SUCCESS));
    }

    @GetMapping("/list")
    public ResponseEntity<?> getList(@ModelAttribute MaintenanceSearchRequestDto dto,
                                     Pageable pageable){
        Page<MaintenanceResponseDto> dtoList = maintenanceService.getList(dto, pageable);

        return ResponseEntity.ok(ResultResponseDTO.of(ResultCodeMessage.POST_VIEW_SUCCESS, dtoList));

    }

}
