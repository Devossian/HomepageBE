package kr.ac.chosun.devossian.domain.maintenance.dto;

import kr.ac.chosun.devossian.domain.maintenance.domain.Type;
import lombok.Data;

@Data
public class MaintenanceSearchRequestDto {
    private Type type;
    private String content;
}
