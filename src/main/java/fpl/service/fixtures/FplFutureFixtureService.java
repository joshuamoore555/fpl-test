package fpl.service.fixtures;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fpl.model.FplFixtureData;
import fpl.model.FplFutureFixture;
import fpl.repository.FplRepository;
import fpl.service.teams.FplTeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class FplFutureFixtureService implements FixtureService {
  public static final int SINGLE_FIXTURE_COUNT = 1;
  public static final String DELIMITER = ":";
  private static final String FUTURE_FIXTURES_URL = "/fixtures/?future=1";
  private static final ObjectMapper MAPPER = new ObjectMapper();
  private final FplRepository fplRepository;
  private final FplTeamService fplTeamService;
  TypeReference<List<FplFixtureData>> fixturesType = new TypeReference<>() {};

  public List<FplFixtureData> getFutureFixtures() throws IOException {
    final String response = fplRepository.getResponse(FUTURE_FIXTURES_URL);
    final var teams = fplTeamService.getTeams();
    final var teamMap = getTeamData(teams);
    final var fixtures = MAPPER.readValue(response, fixturesType);

    return getFplFixtureData(teamMap, fixtures, null);
  }

  public List<FplFutureFixture> getFutureDgwFixtures() throws IOException {
    final String response = fplRepository.getResponse(FUTURE_FIXTURES_URL);
    final var teams = fplTeamService.getTeams();
    final var teamMap = getTeamData(teams);
    final var fixtures = MAPPER.readValue(response, fixturesType);

    return getFplDgwFixtureData(teamMap, fixtures);
  }

  public List<FplFixtureData> getFutureFixturesByTeam(final String teamName) throws IOException {
    final String response = fplRepository.getResponse(FUTURE_FIXTURES_URL);
    final var teams = fplTeamService.getTeams();

    final var teamMap = getTeamData(teams);
    final var fixtures = MAPPER.readValue(response, fixturesType);

    return getFplFixtureData(teamMap, fixtures, teamName);
  }

  private List<FplFutureFixture> getFplDgwFixtureData(
      final Map<Integer, String> teams, final List<FplFixtureData> fixtures) {
    final Map<String, Integer> gameweekMap = new HashMap<>();

    for (final FplFixtureData fplFixtureData : fixtures) {

      final String homeTeamName = getTeamName(fplFixtureData.getHomeTeamIndex(), teams);
      final String awayTeamName = getTeamName(fplFixtureData.getAwayTeamIndex(), teams);

      fplFixtureData.setHomeName(homeTeamName);
      fplFixtureData.setAwayName(awayTeamName);
    }
    final var minimumGameweekIndex =
        fixtures.stream()
            .mapToInt(fplFixtureData -> Integer.parseInt(fplFixtureData.getEvent()))
            .min()
            .orElseThrow(NoSuchElementException::new);
    return getDoubleGameweeks(fixtures, gameweekMap, minimumGameweekIndex);
  }

  private List<FplFutureFixture> getDoubleGameweeks(
      final List<FplFixtureData> fixtures,
      final Map<String, Integer> gameweekMap,
      final int minimumGameweekIndex) {
    final List<FplFutureFixture> doubleGameweeks = new ArrayList<>();

    for (final FplFixtureData fplFixtureData : fixtures) {
      final var homeKey = fplFixtureData.getHomeName() + ":" + fplFixtureData.getEvent();
      doubleGameweeks.addAll(
          getDoubleGameweeks(gameweekMap, homeKey, minimumGameweekIndex, fixtures));
      final var awayKey = fplFixtureData.getAwayName() + ":" + fplFixtureData.getEvent();
      doubleGameweeks.addAll(
          getDoubleGameweeks(gameweekMap, awayKey, minimumGameweekIndex, fixtures));
    }
    return doubleGameweeks;
  }

  private List<FplFutureFixture> getDoubleGameweeks(
      final Map<String, Integer> gameweekMap,
      final String gameweekKey,
      final int minimumGameweekIndex,
      final List<FplFixtureData> fixtures) {
    final List<FplFutureFixture> doubleGameweeks = new ArrayList<>();

    final int oldFixtureCount = gameweekMap.getOrDefault(gameweekKey, 0);
    final int newFixtureCount = oldFixtureCount + 1;
    gameweekMap.put(gameweekKey, newFixtureCount);
    if (newFixtureCount > SINGLE_FIXTURE_COUNT) {
      final var gameweekParts = gameweekKey.split(DELIMITER);
      final String teamName = gameweekParts[0];
      final String gameweekIndex = gameweekParts[1];
      final boolean isNextGameweek = minimumGameweekIndex == Integer.parseInt(gameweekParts[1]);

      final List<String> fixturesForNextGameweek =
          getNextFixtures(fixtures, teamName, gameweekIndex);

      final FplFutureFixture fplFutureFixture =
          FplFutureFixture.builder()
              .teamName(teamName)
              .gameweek(Integer.parseInt(gameweekIndex))
              .isNextGameweek(isNextGameweek)
              .fixtures(fixturesForNextGameweek.toString())
              .build();
      doubleGameweeks.add(fplFutureFixture);
    }
    return doubleGameweeks;
  }

  private List<String> getNextFixtures(
      final List<FplFixtureData> fixtures, final String teamName, final String gameweekIndex) {
    return fixtures.stream()
        .filter(
            fplFixtureData ->
                fplFixtureData.getEvent().equals(gameweekIndex)
                    && (fplFixtureData.getHomeName().equals(teamName)
                        || fplFixtureData.getAwayName().equals(teamName)))
        .map(this::getPrettyFixtureName)
        .collect(Collectors.toList());
  }

  private String getPrettyFixtureName(final FplFixtureData fplFixtureData) {
    return fplFixtureData.getHomeName() + " vs " + fplFixtureData.getAwayName();
  }

  private String getTeamName(final String fplFixtureData, final Map<Integer, String> teams) {
    final int teamIndex = Integer.parseInt(fplFixtureData);
    return String.valueOf(teams.get(teamIndex));
  }
}
