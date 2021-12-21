package fpl.controller;

import fpl.model.Player;
import fpl.service.FplElementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@RequiredArgsConstructor
public class FplElementsController {

    private final FplElementService fplElementService;

    @GetMapping(value ="/fpl/elements", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<LinkedHashMap<String, Object>>> getElements() throws IOException {
        return ResponseEntity.ok(fplElementService.getFplData().getElements());
    }

    @GetMapping(value ="/fpl/elements/top/players", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<Double, Player>> getIctIndex() throws IOException {
        return ResponseEntity.ok(fplElementService.getIctIndex());
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
