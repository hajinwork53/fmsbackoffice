package com.fms.backoffice.dto.response;

import com.fms.backoffice.domain.DrivingLog;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DrivingLogResponse {
    private List<DrivingLog> content;
    private DrivingLogSummary summary;
    private long totalElements;
    private int totalPages;
    private int currentPage;
}
