package org.example.textChatApp.repository;

import org.example.textChatApp.model.PrivateChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PrivateChatRepository extends JpaRepository<PrivateChat, Long> {
    @Query("SELECT c FROM PrivateChat c WHERE " +
            "(c.userOne.id = :userOneId AND c.userTwo.id = :userTwoId) OR " +
            "(c.userOne.id = :userTwoId AND c.userTwo.id = :userOneId)")
    Optional<PrivateChat> findChatByUsers(@Param("userOneId") Long userOneId,
                                          @Param("userTwoId") Long userTwoId);

    @Query("SELECT c FROM PrivateChat c WHERE " +
            "(c.userOne.id = :userId1 AND c.userTwo.id = :userId2) OR " +
            "(c.userOne.id = :userId2 AND c.userTwo.id = :userId1)")
    Optional<PrivateChat> findExistingChat(@Param("userId1") Long userId1, @Param("userId2") Long userId2);

    @Query("SELECT c FROM PrivateChat c WHERE c.userOne.id = :userId OR c.userTwo.id = :userId")
    List<PrivateChat> findChatsByUserId(@Param("userId") Long userId);
}
