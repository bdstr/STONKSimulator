package net.kloczkowski.STONKSimulator.API.dto;

import lombok.Getter;
import net.kloczkowski.STONKSimulator.API.model.User;

@Getter
public class UserDTO {

    private final Long id;
    private final String username;
    private final String authorities;
    private final Long walletId;

    public UserDTO(Long id, String username, String authorities, Long walletId) {
        this.id = id;
        this.username = username;
        this.authorities = authorities;
        this.walletId = walletId;
    }

    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.authorities = user.getAuthorities();
        this.walletId = user.getWallet().getId();
    }
}
