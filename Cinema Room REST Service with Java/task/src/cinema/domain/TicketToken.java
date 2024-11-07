package cinema.domain;

import java.util.UUID;

public class TicketToken {
    UUID token;

    public TicketToken() {}

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }
}
