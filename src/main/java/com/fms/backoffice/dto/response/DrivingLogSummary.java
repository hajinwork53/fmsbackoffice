package com.fms.backoffice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DrivingLogSummary {
    private BigDecimal totalDistance;
    private BigDecimal businessUsageDistance;
    private BigDecimal businessUsageRatio;
}
