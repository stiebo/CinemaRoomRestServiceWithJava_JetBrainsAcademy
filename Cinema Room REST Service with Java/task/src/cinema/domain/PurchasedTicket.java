package cinema.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PurchasedTicket {
    UUID token;
    Seat ticket;

    public PurchasedTicket (UUID token, Seat seat) {
        this.token = token;
        this.ticket = seat;
    }

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }

    public Seat getTicket() {
        return ticket;
    }

    public void setTicket(Seat ticket) {
        this.ticket = ticket;
    }

}
