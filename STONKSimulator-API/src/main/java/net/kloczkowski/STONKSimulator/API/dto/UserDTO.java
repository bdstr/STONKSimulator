package net.kloczkowski.STONKSimulator.API.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kloczkowski.STONKSimulator.API.model.User;

@Getter
@RequiredArgsConstructor
public class UserDTO {

    private final Long id;
    private final String username;
    private final String authorities;
    private final Long walletId;

    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.authorities = user.getAuthorities();
        this.walletId = user.getWallet().getId();
    }
}
