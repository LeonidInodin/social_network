package ru.inodinln.social_network.dto.statisticsDTO;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class StatisticsRequestDTO {

    @NotNull(message = "Field \"startOfPeriod\" is required")
    private LocalDate startOfPeriod;

    private LocalDate endOfPeriod;
}
