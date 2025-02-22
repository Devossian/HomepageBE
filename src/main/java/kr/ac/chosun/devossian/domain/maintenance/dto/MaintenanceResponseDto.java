package kr.ac.chosun.devossian.domain.maintenance.dto;

import kr.ac.chosun.devossian.domain.maintenance.domain.Maintenance;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Data
public class MaintenanceResponseDto {
    private Long maintenance_id;
    private String title;
    private String content;
    private String createdAt;
    private String updatedAt;
    private Long userId;
    private List<String> tags;

    @Builder
    public static MaintenanceResponseDto toDto(Maintenance maintenance, List<String> tags) {
        return MaintenanceResponseDto.builder()
                .maintenance_id(maintenance.getId())
                .title(maintenance.getTitle())
                .content(maintenance.getContent())
                .createdAt(maintenance.getCreatedDate())
                .updatedAt(maintenance.getModifiedDate())
                .userId(maintenance.getUser().getId())
                .tags(tags)
                .build();
    }
}
