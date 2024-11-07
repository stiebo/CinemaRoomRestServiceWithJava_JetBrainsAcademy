package cinema.controller;

import cinema.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class CinemaController {
    TicketToken[][] soldSeats = new TicketToken[9][9];

    @GetMapping("/seats")
    public CinemaInfo getSeats() {
        List<Seat> seats = new ArrayList<>();
        for (int row = 1; row <= 9; row++) {
            for (int column = 1; column <= 9; column++) {
                int price = row <= 4 ? 10 : 8;
                seats.add(new Seat(row, column, price));
            }
        }
        return new CinemaInfo(9, 9, seats);
    }

    @PostMapping("/purchase")
    public ResponseEntity<?> purchaseSeat(@RequestBody Seat seat) {
        int row = seat.getRow();
        int column = seat.getColumn();
        if (row < 1 || row > 9 || column < 1 || column > 9) {
            return ResponseEntity.badRequest().body(new ErrorMsg(ErrorMessage.OUTOFBOUNDS.getMessage()));
        }
        if (soldSeats[row - 1][column - 1] != null) {
            return ResponseEntity.badRequest().body(new ErrorMsg(ErrorMessage.SEATUNAVAILABLE.getMessage()));
        }
        TicketToken token = new TicketToken();
        token.setToken(UUID.randomUUID());
        soldSeats[row - 1][column - 1] = token;
        int price = row <= 4 ? 10 : 8;
        return ResponseEntity.ok().body(new PurchasedTicket(token.getToken(), new Seat(row, column, price)));
    }

    @PostMapping("/return")
    public ResponseEntity<?> returnTicket(@RequestBody TicketToken token) {
        for (int row = 1; row <= 9; row++) {
            for (int column = 1; column <= 9; column++) {
                try {
                    if (soldSeats[row - 1][column - 1].getToken().equals(token.getToken())) {
                        soldSeats[row - 1][column - 1] = null;
                        int price = row <= 4 ? 10 : 8;
                        return ResponseEntity.ok().body(new PurchasedTicket(null, new Seat(row, column, price)));
                    }
                } catch (NullPointerException e) {
                }
            }
        }
        return ResponseEntity.badRequest().body(new ErrorMsg(ErrorMessage.WRONGTOKEN.getMessage()));
    }

    @GetMapping("/stats")
    public ResponseEntity<?> getStats(@RequestParam(required = false) String password) {
        if ("super_secret".equals(password)) {
            int income = 0;
            int purchased = 0;
            for (int row = 1; row <= 9; row++) {
                for (int column = 1; column <= 9; column++) {
                    if (soldSeats[row - 1][column - 1] != null) {
                        income += row <= 4 ? 10 : 8;
                        purchased++;
                    }
                }
            }
            return ResponseEntity.ok().body(new Stats(income, 81 - purchased, purchased));
        } else {
            return new ResponseEntity<>(new ErrorMsg(ErrorMessage.WRONGPASSWORD.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }
}
