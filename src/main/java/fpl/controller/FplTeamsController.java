package fpl.controller;

import fpl.model.Player;
import fpl.service.teams.FplTeamRankingService;
import fpl.service.teams.FplTeamService;
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
public class FplTeamsController {

  private final FplTeamService fplTeamService;
  private final FplTeamRankingService fplTeamRankingService;

  @GetMapping(value = "/fpl/top/teams", produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<List<Player>> getTopTeam() throws IOException {
    return ResponseEntity.ok(fplTeamRankingService.getTopTeam());
  }

  @GetMapping(value = "/fpl/teams", produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<List<LinkedHashMap<String, Object>>> getTeams() throws IOException {
    return ResponseEntity.ok(fplTeamService.getTeams());
  }

  @GetMapping(value = "/fpl/teams/ratings", produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<Map<String, Integer>> getTeamRatings() throws IOException {
    return ResponseEntity.ok(fplTeamService.getTeamRatings());
  }

  @GetMapping(value = "/fpl/teams/home/ratings", produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<Map<String, Integer>> getHomeTeamRatings() throws IOException {
    return ResponseEntity.ok(fplTeamService.getHomeTeamRatings());
  }

  @GetMapping(value = "/fpl/teams/away/ratings", produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<Map<String, Integer>> getAwayTeamRatings() throws IOException {
    return ResponseEntity.ok(fplTeamService.getAwayTeamRatings());
  }

  @GetMapping(value = "/fpl/teams/attack/ratings", produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<Map<String, Integer>> getAttackTeamRatings() throws IOException {
    return ResponseEntity.ok(fplTeamService.getAttackRatings());
  }

  @GetMapping(value = "/fpl/teams/defence/ratings", produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<Map<String, Integer>> getDefenceTeamRatings() throws IOException {
    return ResponseEntity.ok(fplTeamService.getDefenceRatings());
  }
}
