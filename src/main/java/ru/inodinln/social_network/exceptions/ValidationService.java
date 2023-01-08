package ru.inodinln.social_network.exceptions;

import ru.inodinln.social_network.dto.commentsDTO.CommentCreatingDTO;
import ru.inodinln.social_network.dto.conversationsDTO.ConversationCreatingDTO;
import ru.inodinln.social_network.dto.conversationsDTO.ConversationUpdatingDTO;
import ru.inodinln.social_network.dto.dialogsDTO.DialogCreatingDTO;
import ru.inodinln.social_network.dto.likesDTO.LikeCreatingDTO;
import ru.inodinln.social_network.dto.mediaDTO.MediaCreatingDTO;
import ru.inodinln.social_network.dto.messagesDTO.MessageCreatingDTO;
import ru.inodinln.social_network.dto.messagesDTO.MessageUpdatingDTO;
import ru.inodinln.social_network.dto.postsDTO.PostCreatingDTO;
import ru.inodinln.social_network.dto.postsDTO.PostUpdatingDTO;
import ru.inodinln.social_network.dto.statisticsDTO.StatisticsRequestDTO;
import ru.inodinln.social_network.dto.subscriptionsDTO.SubscriptionCreatingDTO;
import ru.inodinln.social_network.dto.usersDTO.UserCreatingDTO;
import ru.inodinln.social_network.dto.usersDTO.UserUpdatingDTO;
import ru.inodinln.social_network.exceptions.validationException.ValidationException;
import ru.inodinln.social_network.security.Roles;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.regex.Pattern;

public class ValidationService {

    private ValidationService(){}

    ///////////////UserCreatingDTO and UserUpdatingDTO validation section/////////////////////////
    public static void userCreatingDtoValidation(UserCreatingDTO userDto){
        firstNameValidation(userDto.getFirstName());
        lastNameValidation(userDto.getLastName());
        birthDateValidation(userDto.getBirthDate());

        String concat = userDto.getFirstName()+userDto.getLastName();
        if (!((concat.matches("[A-z -?]*$")) || (concat.matches("[А-я -?]*$"))))
            throw new ValidationException("Fields \"firstName\" and \"lastName\" must contains symbols from the same language");
    }

    public static void userUpdatingDtoValidation(UserUpdatingDTO userDto){
        idValidation(userDto.getId());
        firstNameValidation(userDto.getFirstName());
        lastNameValidation(userDto.getLastName());
        birthDateValidation(userDto.getBirthDate());

        String concat = userDto.getFirstName()+userDto.getLastName();
        if (!((concat.matches("[A-z]*$")) || (concat.matches("[А-я]*$"))))
            throw new ValidationException("Fields \"firstName\" and \"lastName\" must contains symbols from the same language");
    }


    public static void birthDateValidation(LocalDate birthDate) {
        if (birthDate == null)
            throw new ValidationException("Birth date field can't be empty");

        if (birthDate.isAfter(LocalDate.now()))
            throw new ValidationException("Birth date can't be in the future");

        long age = ChronoUnit.YEARS.between(birthDate, LocalDate.now());
        if (age < 14 || age > 100)
            throw new ValidationException("Age must be between 14 and 100");

    }

    public static void firstNameValidation(String firstName){
        if (firstName.contains(" "))
            throw new ValidationException("field \"firstName\" must contains the only one word");
        if (!firstName.matches("[A-zА-я]*$"))
            throw new ValidationException("field \"firstName\" must contains a letters only");
    }

    public static void lastNameValidation(String lastName){
        if (lastName.contains(" "))
            throw new ValidationException("field \"lastName\" must contains the only one word");
        if (!Pattern.matches("[A-zА-я -?]*$", lastName))
            throw new ValidationException("field \"lastName\" must contains a letters only");
    }
    ///////////////CommentCreatingDTO and CommentUpdatingDTO validation section/////////////////////////
    public static void commentCreatingDtoValidation(CommentCreatingDTO dto){
        postValidation(dto.getPost());
        commentValidation(dto.getParentComment());
        authorValidation(dto.getAuthor());
        levelValidation(dto.getLevel());
        if (((dto.getParentComment() == null) & (dto.getLevel() > 1))
                || (dto.getParentComment() != null) & (dto.getLevel() == 1))
            throw new ValidationException("forbidden combination of fields \"comment\" and \"level\" values");
    }

    public static void postValidation(Long post){
        if (post <= 0)
            throw new ValidationException("field \"post\" must be greater then 0");
    }

    public static void commentValidation(Long comment){
        if (comment != null && comment <= 0)
            throw new ValidationException("field \"comment\" must be greater then 0");
    }

    public static void authorValidation(Long author){
        if (author <= 0)
            throw new ValidationException("field \"author\" must be greater then 0");
    }

    public static void levelValidation(Integer level){
        if (level <= 0)
            throw new ValidationException("field \"level\" must be greater then 0");
    }

    ///////////////PostCreatingDTO and PostUpdatingDTO validation section/////////////////////////

    public static void postCreatingDtoValidation(PostCreatingDTO dto) {
        idValidation(dto.getAuthorId());
    }

    public static void postUpdatingDtoValidation(PostUpdatingDTO dto) {
        idValidation(dto.getId());
    }

    ///////////////ConversationCreatingDTO and ConversationUpdatingDTO validation section/////////////////////////

    public static void conversationCreatingDtoValidation(ConversationCreatingDTO dto) {
        idValidation(dto.getAuthorId());
    }

    public static void conversationUpdatingDtoValidation(ConversationUpdatingDTO dto) {
        idValidation(dto.getId());
    }

    ///////////////DialogCreatingDTO validation section/////////////////////////

    public static void dialogCreatingDtoValidation(DialogCreatingDTO dto) {
        idValidation(dto.getAuthorId());
        idValidation(dto.getCompanionId());
        if (dto.getAuthorId().equals(dto.getCompanionId()))
            throw new ValidationException("Values of fields \"authorId\" and \"companionId\" can't be equals");
    }

    ///////////////MessageCreatingDTO and MessageUpdatingDTO validation section/////////////////////////

    public static void messageCreatingDtoValidation(MessageCreatingDTO dto) {
        idValidation(dto.getContainerId());
        idValidation(dto.getSenderId());
        if (dto.getRecipientId() !=null){
            idValidation(dto.getRecipientId());
            if (dto.getSenderId().equals(dto.getRecipientId()))
                throw new ValidationException("Values of fields \"authorId\" and \"companionId\" can't be equals");
        }

        if (!(dto.getType().equals("Conversation") || !(dto.getType().equals("Dialog"))))
            throw new ValidationException("Fields \"type\" values must be \"Conversation\" or \"Dialog\" only");

        if ((dto.getType().equals("Dialog") && (dto.getRecipientId() == null)))
            throw new ValidationException("Field \"recipientId\" is required when type is dialog");
    }

    public static void messageUpdatingDtoValidation(MessageUpdatingDTO dto) {
        idValidation(dto.getId());
    }

    ///////////////LikeCreatingDTO validation section/////////////////////////

    public static void likeCreatingDtoValidation(LikeCreatingDTO dto) {
        idValidation(dto.getAuthorId());
        idValidation(dto.getPostId());
    }

    ///////////////SubscriptionCreatingDTO validation section/////////////////////////

    public static void subscriptionCreatingDtoValidation(SubscriptionCreatingDTO dto) {
        idValidation(dto.getSubscriberId());
        idValidation(dto.getTargetId());

        if (dto.getSubscriberId().equals(dto.getTargetId()))
            throw new ValidationException("Values of fields \"subscriberId\" and \"targetId\" can't be equals");
    }

    ///////////////MediaCreatingDTO validation section/////////////////////////

    public static void mediaCreatingDtoValidation(MediaCreatingDTO dto) {
        String[] fileTypes = new String[] {"jpg", "png", "jpeg", "bmp", "mp4", "mpg", "avi"};
        if (dto.getPostId() !=null)
            idValidation(dto.getPostId());
        else idValidation(dto.getMessageId());
        if (dto.getPostId() == null && dto.getMessageId() == null)
            throw new ValidationException("Fields \"postId\" and \"messageId\" can't be null at the same time");
        if (dto.getPostId() != null && dto.getMessageId() != null)
            throw new ValidationException("Fields \"postId\" and \"messageId\" can't have a value at the same time");
        if (!(Arrays.asList(fileTypes).contains(dto.getExtension().toLowerCase())))
            throw new ValidationException("Field \"extension\" has an unsupported file format");

    }

    ///////////////StatisticsRequestDTO validation section/////////////////////////

    public static void statisticsRequestDtoValidation(StatisticsRequestDTO dto){
        if (dto.getStartOfPeriod().isAfter(LocalDate.now()))
            throw new ValidationException("Period's start date can't be in the future");
        if (dto.getEndOfPeriod().isAfter(LocalDate.now().plusDays(1)))
            throw new ValidationException("Period's end date can't be in the future");
    }


    ///////////////Service methods section/////////////////////////
    public static void idValidation(Long id){
        if (id <= 0)
            throw new ValidationException("fields with id values must be greater then 0");
    }

    public static void roleValidation(String role){
        if (!(role.equals(Roles.ROLE_ADMIN.toString())))
            throw new ValidationException("Role must be equals ROLE_ADMIN value");
    }

}
