package ru.inodinln.social_network.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.inodinln.social_network.entities.Dialog;
import ru.inodinln.social_network.entities.User;

import java.util.List;


public interface DialogRepository extends JpaRepository<Dialog, Long> {

    /**Find dialogs when users participate*/
    List<Dialog> findDialogsByAuthorAndCompanion(User author, User companion, Pageable pageable);

    Dialog findDialogByAuthorAndCompanion(User author, User companion);


    Dialog findDialogByIdAndAuthor(Long dialogId, User user);

    Dialog findDialogByIdAndCompanion(Long dialogId, User user);


}
