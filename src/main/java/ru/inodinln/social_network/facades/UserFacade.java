package ru.inodinln.social_network.facades;


import org.springframework.stereotype.Service;
import ru.inodinln.social_network.dto.statisticsDTO.StatisticsRequestDTO;
import ru.inodinln.social_network.dto.statisticsDTO.StatisticsUserViewDTO;
import ru.inodinln.social_network.dto.usersDTO.UserCreatingDTO;
import ru.inodinln.social_network.dto.usersDTO.UserUpdatingDTO;
import ru.inodinln.social_network.dto.usersDTO.UserViewDTO;
import ru.inodinln.social_network.exceptions.ValidationService;
import ru.inodinln.social_network.services.UserService;
import ru.inodinln.social_network.utils.MapperService;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserFacade {

    private final UserService userService;

    public UserFacade(UserService userService) {
        this.userService = userService;
    }

    ////////////////////////////Business methods section///////////////////////////////////////

    public List<UserViewDTO> getMembersByConversationId(Long conversationId, Integer page, Integer itemsPerPage){
        return MapperService.mapperForCollectionOfUserViewDTO
                (userService.getMembersByConversationId(conversationId, page, itemsPerPage));
    }

    ////////////////////////////Statistics methods section///////////////////////////////////////

    public List<StatisticsUserViewDTO> get10mostActive(StatisticsRequestDTO dto){
        if (dto.getEndOfPeriod() == null)
            dto.setEndOfPeriod(LocalDate.now());
        ValidationService.statisticsRequestDtoValidation(dto);
        return MapperService.mapperForCollectionOfStatisticsUserViewDTO
                (userService.get10mostActive(dto.getStartOfPeriod(), dto.getEndOfPeriod()));
    }


    ////////////////////////////Basic CRUD methods section///////////////////////////////////////
    public List<UserViewDTO> getAll(Integer page, Integer itemsPerPage) {
        return MapperService.mapperForCollectionOfUserViewDTO(userService.getAll(page, itemsPerPage));
    }

    public UserViewDTO getById(Long userId) {
        return MapperService.mapperForSingleUserViewDTO(userService.getById(userId));
    }

    public UserViewDTO create(UserCreatingDTO newUserDTO) {

        ValidationService.userCreatingDtoValidation(newUserDTO);

        return MapperService.mapperForSingleUserViewDTO(userService.create(newUserDTO.getFirstName(),
                newUserDTO.getLastName(), newUserDTO.getEmail(), newUserDTO.getPassword(), newUserDTO.getBirthDate()));

    }

    public UserViewDTO update(UserUpdatingDTO updatingDTO) {

        ValidationService.userUpdatingDtoValidation(updatingDTO);

        return MapperService.mapperForSingleUserViewDTO(userService.update(updatingDTO.getId(), updatingDTO.getFirstName(),
                updatingDTO.getLastName(), updatingDTO.getEMail(), updatingDTO.getPassword(), updatingDTO.getBirthDate()));
    }

}
