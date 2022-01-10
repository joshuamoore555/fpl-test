package fpl.service.fixtures;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fpl.model.FplFixtureData;
import fpl.repository.FplRepository;
import fpl.service.teams.FplTeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FplFixtureService implements FixtureService {
  private static final String ALL_FIXTURES_URL = "/fixtures/?future=0";
  private static final ObjectMapper MAPPER = new ObjectMapper();
  private final FplRepository fplRepository;
  private final FplTeamService fplTeamService;
  TypeReference<List<FplFixtureData>> fixturesType = new TypeReference<>() {};

  public List<FplFixtureData> getAllFixtures() throws IOException {
    final String response = fplRepository.getResponse(ALL_FIXTURES_URL);
    final var teams = fplTeamService.getTeams();
    final var teamMap = getTeamData(teams);
    final var fixtures = MAPPER.readValue(response, fixturesType);

    return getFplFixtureData(teamMap, fixtures, null);
  }

  public List<FplFixtureData> getTeamSpecificFixtures(final String teamName) throws IOException {
    final String response = fplRepository.getResponse(ALL_FIXTURES_URL);
    final var teams = fplTeamService.getTeams();
    final var teamMap = getTeamData(teams);
    final var fixtures = MAPPER.readValue(response, fixturesType);

    return getFplFixtureData(teamMap, fixtures, teamName);
  }
}
