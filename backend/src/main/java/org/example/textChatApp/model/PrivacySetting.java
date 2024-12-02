package org.example.textChatApp.model;

import jakarta.persistence.*;

@Entity
@Table(name = "privacy_settings")
public class PrivacySetting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "allow_private_messages")
    private Boolean allowPrivateMessages;

    @Column(name = "last_seen_visible")
    private Boolean lastSeenVisible;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getAllowPrivateMessages() {
        return allowPrivateMessages;
    }

    public void setAllowPrivateMessages(Boolean allowPrivateMessages) {
        this.allowPrivateMessages = allowPrivateMessages;
    }

    public Boolean getLastSeenVisible() {
        return lastSeenVisible;
    }

    public void setLastSeenVisible(Boolean lastSeenVisible) {
        this.lastSeenVisible = lastSeenVisible;
    }
}
