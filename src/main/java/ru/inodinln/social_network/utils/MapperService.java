package ru.inodinln.social_network.utils;

import org.springframework.beans.BeanUtils;
import ru.inodinln.social_network.dto.commentsDTO.CommentViewDTO;
import ru.inodinln.social_network.dto.commentsDTO.CommentsTreeViewDTO;
import ru.inodinln.social_network.dto.conversationsDTO.ConversationViewDTO;
import ru.inodinln.social_network.dto.dialogsDTO.DialogReducedViewDTO;
import ru.inodinln.social_network.dto.dialogsDTO.DialogViewDTO;
import ru.inodinln.social_network.dto.likesDTO.LikeViewDTO;
import ru.inodinln.social_network.dto.mediaDTO.MediaViewDTO;
import ru.inodinln.social_network.dto.messagesDTO.MessageViewDTO;
import ru.inodinln.social_network.dto.postsDTO.PostViewDTO;
import ru.inodinln.social_network.dto.statisticsDTO.StatisticsUserViewDTO;
import ru.inodinln.social_network.dto.subscriptionsDTO.SubscriptionViewDTO;
import ru.inodinln.social_network.dto.usersDTO.UserReducedViewDTO;
import ru.inodinln.social_network.dto.usersDTO.UserViewDTO;
import ru.inodinln.social_network.entities.*;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MapperService {


    /**
     * Don't let anyone instantiate this class.
     */
    private MapperService() {
    }

    ////////////////////////////User DTO methods section///////////////////////////////////////

    public static List<UserViewDTO> mapperForCollectionOfUserViewDTO(List<User> list) {
        return list.stream().map(MapperService::mapperForSingleUserViewDTO).collect(Collectors.toList());
    }

    public static UserViewDTO mapperForSingleUserViewDTO(User user) {
        UserViewDTO dto = new UserViewDTO();
        BeanUtils.copyProperties(user, dto);
        return dto;
    }

    public static List<StatisticsUserViewDTO> mapperForCollectionOfStatisticsUserViewDTO(List<Map.Entry<User, Long>> list) {
        return list.stream().map(MapperService::mapperForSingleStatisticsUserViewDTO).collect(Collectors.toList());
    }

    public static StatisticsUserViewDTO mapperForSingleStatisticsUserViewDTO(Map.Entry<User, Long> entry) {
        StatisticsUserViewDTO dto = new StatisticsUserViewDTO();
        dto.setUser(mapperForSingleUserReducedViewDTO(entry.getKey()));
        dto.setValue(entry.getValue());
        return dto;
    }

    public static List<UserReducedViewDTO> mapperForCollectionOfUserReducedViewDTO(List<User> list) {
        return list.stream().map(MapperService::mapperForSingleUserReducedViewDTO).collect(Collectors.toList());
    }

    public static UserReducedViewDTO mapperForSingleUserReducedViewDTO(User user) {
        UserReducedViewDTO dto = new UserReducedViewDTO();
        BeanUtils.copyProperties(user, dto);
        return dto;
    }

    ////////////////////////////Comment DTO methods section///////////////////////////////////////

    public static List<CommentsTreeViewDTO> mapperForCommentsTreeDTO(List<Comment> list) {
        return list.stream().map(e -> {
            CommentsTreeViewDTO dto = new CommentsTreeViewDTO();
            BeanUtils.copyProperties(e, dto);
            dto.setPost(e.getPost().getId());
            dto.setAuthor(e.getAuthor().getId());
            if (e.getParentComment() != null)
                dto.setComment(e.getParentComment().getId());
            else dto.setComment(null);
            if (!(e.getComments().isEmpty()))
                dto.setComments(mapperForCommentsTreeDTO(e.getComments())
                        .stream().sorted(Comparator.comparing(CommentsTreeViewDTO::getTimestamp))
                        .collect(Collectors.toList()));
            return dto;
        }).collect(Collectors.toList());
    }

    public static List<CommentViewDTO> mapperForCollectionOfCommentViewDTO(List<Comment> list) {
        return list.stream().map(MapperService::mapperForSingleCommentViewDTO).collect(Collectors.toList());
    }

    public static CommentViewDTO mapperForSingleCommentViewDTO(Comment comm) {
        CommentViewDTO dto = new CommentViewDTO();
        BeanUtils.copyProperties(comm, dto);
        dto.setPost(comm.getPost().getId());
        dto.setAuthor(comm.getAuthor().getId());
        if (comm.getParentComment() != null)
            dto.setParentComment(comm.getParentComment().getId());
        else dto.setParentComment(null);
        return dto;
    }

    ////////////////////////////Post DTO methods section///////////////////////////////////////

    public static List<PostViewDTO> mapperForCollectionOfPostViewDTO(List<Post> list) {
        return list.stream().map(MapperService::mapperForSinglePostViewDTO).collect(Collectors.toList());
    }

    public static PostViewDTO mapperForSinglePostViewDTO(Post post) {
        PostViewDTO dto = new PostViewDTO();
        BeanUtils.copyProperties(post, dto);
        dto.setAuthor(mapperForSingleUserReducedViewDTO(post.getAuthor()));
        if (post.getMedia() == null || post.getMedia().isEmpty())
            dto.setMediaList(null);
        else dto.setMediaList(mapperForCollectionOfMediaViewDTO(post.getMedia()));
        return dto;
    }

    ////////////////////////////Conversation DTO methods section///////////////////////////////////////

    public static List<ConversationViewDTO> mapperForCollectionOfConversationViewDTO(List<Conversation> list) {

        return list.stream().map(MapperService::mapperForSingleConversationViewDTO).collect(Collectors.toList());
    }

    public static ConversationViewDTO mapperForSingleConversationViewDTO(Conversation conversation) {
        ConversationViewDTO dto = new ConversationViewDTO();
        BeanUtils.copyProperties(conversation, dto);
        dto.setMembersCount((long) (conversation.getMembers().size()));
        dto.setMembers(mapperForCollectionOfUserReducedViewDTO(conversation.getMembers())
                .stream().limit(3).collect(Collectors.toList()));
        if (conversation.getMessages() != null)
            dto.setLastMessage(mapperForSingleMessageViewDTO(conversation.getMessages()
                    .stream().max(Comparator.comparing(Message::getTimestamp)).orElse(null)));
        else dto.setLastMessage(null);
        return dto;
    }

    ////////////////////////////Dialog DTO methods section///////////////////////////////////////

    public static List<DialogViewDTO> mapperForCollectionOfDialogViewDTO(List<Dialog> list) {

        return list.stream().map(MapperService::mapperForSingleDialogViewDTO).collect(Collectors.toList());
    }

    public static DialogViewDTO mapperForSingleDialogViewDTO(Dialog dialog) {
        DialogViewDTO dto = new DialogViewDTO();
        BeanUtils.copyProperties(dialog, dto);
        dto.setAuthorId(dialog.getAuthor().getId());
        dto.setCompanionId(dialog.getCompanion().getId());
        if (dialog.getMessages() != null) {
            dto.setLastMessage(mapperForSingleMessageViewDTO(dialog.getMessages()
                    .stream().max(Comparator.comparing(Message::getTimestamp)).orElse(null)));
        } else
            dto.setLastMessage(null);
        return dto;
    }

    public static List<DialogReducedViewDTO> mapperForCollectionOfDialogReducedViewDTO(List<Dialog> list) {
        return list.stream().map(MapperService::mapperForSingleDialogReducedViewDTO).collect(Collectors.toList());
    }

    public static DialogReducedViewDTO mapperForSingleDialogReducedViewDTO(Dialog dialog) {
        DialogReducedViewDTO dto = new DialogReducedViewDTO();
        BeanUtils.copyProperties(dialog, dto);
        if (dialog.getAuthor() == null)
            dto.setCompanion(mapperForSingleUserReducedViewDTO(dialog.getCompanion()));
        else dto.setCompanion(mapperForSingleUserReducedViewDTO(dialog.getAuthor()));
        if (dialog.getMessages() != null) {
            dto.setLastMessage(mapperForSingleMessageViewDTO(dialog.getMessages()
                    .stream().max(Comparator.comparing(Message::getTimestamp)).orElse(null)));
        } else
            dto.setLastMessage(null);
        return dto;
    }

    ////////////////////////////Message DTO methods section///////////////////////////////////////

    public static List<MessageViewDTO> mapperForCollectionOfMessageViewDTO(List<Message> list) {

        return list.stream().map(MapperService::mapperForSingleMessageViewDTO).collect(Collectors.toList());
    }

    public static MessageViewDTO mapperForSingleMessageViewDTO(Message message) {
        MessageViewDTO dto = new MessageViewDTO();
        BeanUtils.copyProperties(message, dto);//id, timestamp, timestampOfUpdating, text
        dto.setSenderId(message.getSender().getId());
        if (message.getConversation() != null)
            dto.setConversationId(message.getConversation().getId());
        else dto.setDialogId(message.getDialog().getId());
        if (message.getRecipient() != null)
            dto.setRecipientId(message.getRecipient().getId());
        else dto.setRecipientId(null);
        if (message.getMedia() == null || message.getMedia().isEmpty())
            dto.setMedia(null);
        else dto.setMedia(mapperForCollectionOfMediaViewDTO(message.getMedia()));
        return dto;
    }

    ////////////////////////////Like DTO methods section///////////////////////////////////////

    public static List<LikeViewDTO> mapperForCollectionOfLikeViewDTO(List<Like> list) {
        return list.stream().map(MapperService::mapperForSingleLikeViewDTO).collect(Collectors.toList());
    }

    public static LikeViewDTO mapperForSingleLikeViewDTO(Like like) {
        LikeViewDTO dto = new LikeViewDTO();
        BeanUtils.copyProperties(like, dto);
        dto.setAuthorId(like.getAuthor().getId());
        dto.setPostId(like.getPost().getId());
        return dto;
    }

    ////////////////////////////Subscription DTO methods section///////////////////////////////////////

    public static List<SubscriptionViewDTO> mapperForCollectionOfSubscriptionViewDTO(List<Subscription> list) {

        return list.stream().map(MapperService::mapperForSingleSubscriptionViewDTO).collect(Collectors.toList());
    }

    public static SubscriptionViewDTO mapperForSingleSubscriptionViewDTO(Subscription subscription) {
        SubscriptionViewDTO dto = new SubscriptionViewDTO();
        BeanUtils.copyProperties(subscription, dto);
        dto.setSubscriber(mapperForSingleUserReducedViewDTO(subscription.getSubscriber()));
        dto.setTarget(mapperForSingleUserReducedViewDTO(subscription.getTarget()));
        return dto;
    }

    ////////////////////////////Media DTO methods section///////////////////////////////////////

    public static List<MediaViewDTO> mapperForCollectionOfMediaViewDTO(List<Media> list) {
        return list.stream().map(MapperService::mapperForSingleMediaViewDTO).collect(Collectors.toList());
    }

    public static MediaViewDTO mapperForSingleMediaViewDTO(Media media) {
        MediaViewDTO dto = new MediaViewDTO();
        BeanUtils.copyProperties(media, dto);
        if (media.getPost() != null)
            dto.setPostId(media.getPost().getId());
        else dto.setMessageId(media.getMessage().getId());
        return dto;
    }

}
