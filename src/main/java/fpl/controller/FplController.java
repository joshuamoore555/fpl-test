package fpl.controller;

import fpl.model.FplData;
import fpl.service.FplService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@RequiredArgsConstructor
public class FplController {
    private final FplService fplService;

    @GetMapping(value ="/fpl", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<FplData> getAllData() throws IOException {
        return ResponseEntity.ok(fplService.findAll());
    }

    @GetMapping(value ="/fpl/player/count", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> getPlayerCount() throws IOException {
        return ResponseEntity.ok(fplService.getPlayerCount());
    }

}
