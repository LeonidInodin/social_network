package ru.inodinln.social_network.facades;


import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.inodinln.social_network.dto.statisticsDTO.StatisticsRequestDTO;
import ru.inodinln.social_network.dto.statisticsDTO.StatisticsUserViewDTO;
import ru.inodinln.social_network.dto.usersDTO.UserCreatingDTO;
import ru.inodinln.social_network.dto.usersDTO.UserUpdatingDTO;
import ru.inodinln.social_network.dto.usersDTO.UserViewDTO;
import ru.inodinln.social_network.exceptions.ValidationService;
import ru.inodinln.social_network.security.AuthenticationRequestDTO;
import ru.inodinln.social_network.security.AuthenticationResponseDTO;
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

    public List<UserViewDTO> getMembersByConversationId(
            Long conversationId,
            Integer page,
            Integer itemsPerPage,
            String eMail){
        return MapperService.mapperForCollectionOfUserViewDTO
                (userService.getMembersByConversationId(conversationId, page, itemsPerPage, eMail));
    }

    public void setRole(Long userId, String role){
        ValidationService.roleValidation(role);
        userService.setRole(userId, role);
    }

    ////////////////////////////Statistics methods section///////////////////////////////////////

    public List<StatisticsUserViewDTO> getMostActiveUsers(
            StatisticsRequestDTO dto,
            Integer page,
            Integer itemsPerPage){
        if (dto.getEndOfPeriod() == null)
            dto.setEndOfPeriod(LocalDate.now().plusDays(1));
        if (dto.getStartOfPeriod().isEqual(dto.getEndOfPeriod()))
            dto.setEndOfPeriod(dto.getEndOfPeriod().plusDays(1));
        ValidationService.statisticsRequestDtoValidation(dto);
        return MapperService.mapperForCollectionOfStatisticsUserViewDTO
                (userService.getMostActiveUsers(dto.getStartOfPeriod(), dto.getEndOfPeriod(), page, itemsPerPage));
    }

    ////////////////////////////Security methods section///////////////////////////////////////

    public UserViewDTO create(UserCreatingDTO newUserDTO, Authentication authentication) {

        ValidationService.userCreatingDtoValidation(newUserDTO);

        return MapperService.mapperForSingleUserViewDTO(userService.create(
                newUserDTO.getFirstName(),
                newUserDTO.getLastName(),
                newUserDTO.getEmail(),
                newUserDTO.getPassword(),
                newUserDTO.getBirthDate(),
                authentication));

    }

    public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO requestDTO, Authentication authentication){
        return MapperService.mapperForAuthResponseDTO(userService.authenticate(
                requestDTO.getPassword(),
                requestDTO.getEmail(),
                authentication));
    }


    ////////////////////////////Basic CRUD methods section///////////////////////////////////////
    public List<UserViewDTO> getAll(Integer page, Integer itemsPerPage) {
        return MapperService.mapperForCollectionOfUserViewDTO(userService.getAll(page, itemsPerPage));
    }

    public UserViewDTO getById(Long userId) {
        return MapperService.mapperForSingleUserViewDTO(userService.getById(userId));
    }

    public UserViewDTO update(UserUpdatingDTO updatingDTO, String currentEmail) {

        ValidationService.userUpdatingDtoValidation(updatingDTO);

        return MapperService.mapperForSingleUserViewDTO(userService.update(updatingDTO.getId(), updatingDTO.getFirstName(),
                updatingDTO.getLastName(), updatingDTO.getEMail(),
                updatingDTO.getPassword(), updatingDTO.getBirthDate(), currentEmail));
    }

    public void delete(Long userId){
        userService.delete(userId);
    }

}
