package kr.ac.chosun.devossian.domain.maintenance.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import kr.ac.chosun.devossian.domain.maintenance.domain.Maintenance;
import kr.ac.chosun.devossian.domain.user.domain.User;
import kr.ac.chosun.devossian.global.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
public class MaintenanceCreateRequestDto {
    // 메뉴얼 및 유지보수 작성 요청
    // 이미지, 태그, 본문, 제목 포함

    @Size(max = 255)
    private String title;

    @NotBlank
    private String content;

    private List<String> tags;
    private List<String> images;


    public static Maintenance toEntity(MaintenanceCreateRequestDto dto, User user){
        return Maintenance.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .user(user)
                .build();
    }

}
