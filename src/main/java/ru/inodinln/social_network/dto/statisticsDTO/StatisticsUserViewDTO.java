package ru.inodinln.social_network.dto.statisticsDTO;

import lombok.Data;
import ru.inodinln.social_network.dto.usersDTO.UserReducedViewDTO;

@Data
public class StatisticsUserViewDTO {

    private UserReducedViewDTO user;

    private Double value;

}
