package fpl.controller;

import fpl.model.Player;
import fpl.service.FplElementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@RequiredArgsConstructor
public class FplElementsController {

    private final FplElementService fplElementService;

    @GetMapping(value ="/fpl/elements/players/{playerName}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Player>> getPlayer(@PathVariable String playerName) throws IOException {
        return ResponseEntity.ok(fplElementService.getPlayerDataByName(playerName));
    }

    @GetMapping(value ="/fpl/elements/players", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Player>> getAllPlayers() throws IOException {
        return ResponseEntity.ok(fplElementService.getPlayers());
    }

    @GetMapping(value ="/fpl/elements/top/goalkeepers", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Player>> getTopGoalkeepers() throws IOException {
        return ResponseEntity.ok(fplElementService.getTopGoalkeepers());
    }

    @GetMapping(value ="/fpl/elements/top/defenders", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Player>> getTopDefenders() throws IOException {
        return ResponseEntity.ok(fplElementService.getTopDefenders());
    }

    @GetMapping(value ="/fpl/elements/top/midfielders", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Player>> getTopMidfielders() throws IOException {
        return ResponseEntity.ok(fplElementService.getTopMidfielders());
    }

    @GetMapping(value ="/fpl/elements/top/forwards", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Player>> getTopForwards() throws IOException {
        return ResponseEntity.ok(fplElementService.getTopForwards());
    }

    @GetMapping(value ="/fpl/elements/top/team", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Player>> getTopTeam() throws IOException {
        return ResponseEntity.ok(fplElementService.getTopTeam());
    }

}
