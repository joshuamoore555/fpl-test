package fpl.service.teams;

import com.fasterxml.jackson.databind.ObjectMapper;
import fpl.model.FplData;
import fpl.repository.FplRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class FplTeamService {
  private static final String BOOTSTRAP_STATIC = "/bootstrap-static/";
  private static final ObjectMapper MAPPER = new ObjectMapper();
  private final FplRepository fplRepository;

  public List<LinkedHashMap<String, Object>> getTeams() throws IOException {
    final String response = fplRepository.getResponse(BOOTSTRAP_STATIC);
    final var fplData = MAPPER.readValue(response, FplData.class);
    return fplData.getTeams();
  }

  public Map<String, Integer> getTeamRatings() throws IOException {
    final var teams = getTeams();
    final Map<String, Integer> teamRankings = new HashMap<>();

    for (final var team : teams) {
      final String name = String.valueOf(team.get("name"));
      final Integer rank =
          Integer.parseInt(team.get("strength_overall_home").toString())
              + Integer.parseInt(team.get("strength_overall_away").toString());
      teamRankings.put(name, rank);
    }
    return orderTeams(teamRankings);
  }

  public Map<String, Integer> getHomeTeamRatings() throws IOException {
    final var teams = getTeams();
    final Map<String, Integer> teamRankings = new HashMap<>();

    for (final var team : teams) {
      final String name = String.valueOf(team.get("name"));
      final Integer rank = Integer.parseInt(team.get("strength_overall_home").toString());
      teamRankings.put(name, rank);
    }
    return orderTeams(teamRankings);
  }

  public Map<String, Integer> getAwayTeamRatings() throws IOException {
    final var teams = getTeams();
    final Map<String, Integer> teamRankings = new HashMap<>();

    for (final var team : teams) {
      final String name = String.valueOf(team.get("name"));
      final Integer rank = Integer.parseInt(team.get("strength_overall_away").toString());
      teamRankings.put(name, rank);
    }
    return orderTeams(teamRankings);
  }

  public Map<String, Integer> getAttackRatings() throws IOException {
    final var teams = getTeams();
    final Map<String, Integer> teamRankings = new HashMap<>();

    for (final var team : teams) {
      final String name = String.valueOf(team.get("name"));
      final Integer rank =
          Integer.parseInt(team.get("strength_attack_home").toString())
              + Integer.parseInt(team.get("strength_attack_away").toString());
      teamRankings.put(name, rank);
    }
    return orderTeams(teamRankings);
  }

  public Map<String, Integer> getDefenceRatings() throws IOException {
    final var teams = getTeams();
    final Map<String, Integer> teamRankings = new HashMap<>();

    for (final var team : teams) {
      final String name = String.valueOf(team.get("name"));
      final Integer rank =
          Integer.parseInt(team.get("strength_defence_home").toString())
              + Integer.parseInt(team.get("strength_defence_away").toString());
      teamRankings.put(name, rank);
    }
    return orderTeams(teamRankings);
  }

  private LinkedHashMap<String, Integer> orderTeams(final Map<String, Integer> teamRankings) {
    return teamRankings.entrySet().stream()
        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
        .collect(
            Collectors.toMap(
                Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
  }
}
