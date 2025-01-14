package org.example.textChatApp.dto;

import org.example.textChatApp.model.Room;

public class RoomDTO {
    private Long id;
    private String name;
    private Long serverId;
    private Boolean isPrivate;

    public RoomDTO() {
    }

    public RoomDTO(Room room) {
        this.id = room.getId();
        this.name = room.getName();
        this.serverId = room.getServer().getId();
        this.isPrivate = room.getPrivate();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getServerId() {
        return serverId;
    }

    public void setServerId(Long serverId) {
        this.serverId = serverId;
    }

    public Boolean getPrivate() {
        return isPrivate;
    }

    public void setPrivate(Boolean aPrivate) {
        isPrivate = aPrivate;
    }
}
