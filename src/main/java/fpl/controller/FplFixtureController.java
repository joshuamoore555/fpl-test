package fpl.controller;

import fpl.service.FplFixtureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
public class FplFixtureController {
  private final FplFixtureService fplFixtureService;

  @GetMapping(value = "/fpl/future/fixtures", produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<Object> getFutureFixtures() throws IOException {
    return ResponseEntity.ok(fplFixtureService.getFutureFixtures());
  }

  @GetMapping(value = "/fpl/future/dgw/fixtures", produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<Object> getFutureDgwFixtures() throws IOException {
    return ResponseEntity.ok(fplFixtureService.getFutureDgwFixtures());
  }

  @GetMapping(value = "/fpl/fixtures", produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<Object> getAllFixtures() throws IOException {
    return ResponseEntity.ok(fplFixtureService.getAllFixtures());
  }

  @GetMapping(value = "/fpl/fixtures/{teamName}", produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<Object> getTeamSpecificFixtures(@PathVariable final String teamName)
      throws IOException {
    return ResponseEntity.ok(fplFixtureService.getTeamSpecificFixtures(teamName));
  }

  @GetMapping(value = "/fpl/future/fixtures/{teamName}", produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<Object> getTeamSpecificFutureFixtures(@PathVariable final String teamName)
      throws IOException {
    return ResponseEntity.ok(fplFixtureService.getTeamSpecificFutureFixtures(teamName));
  }
}
