package fpl.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fpl.model.FplFixtureData;
import fpl.repository.FplRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;


@Service
@Slf4j
@RequiredArgsConstructor
public class FplFixtureService {
    private static final String FUTURE_FIXTURES_URL = "/fixtures/?future=1";
    private static final String ALL_FIXTURES_URL = "/fixtures/?future=0";

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private final FplRepository fplRepository;
    private final FplTeamService fplTeamService;
    TypeReference<List<FplFixtureData>> fixturesType = new TypeReference<>() {
    };


    public List<FplFixtureData> getFutureFixtures() throws IOException {
        String response = fplRepository.getResponse(FUTURE_FIXTURES_URL);
        var teams = getTeamData();

        var fixtures =  MAPPER.readValue(response, fixturesType);

        return getFplFixtureData(teams, fixtures, null);

    }

    public List<FplFixtureData> getAllFixtures() throws IOException {
        String response = fplRepository.getResponse(ALL_FIXTURES_URL);
        var teams = getTeamData();

        var fixtures =  MAPPER.readValue(response, fixturesType);

        return getFplFixtureData(teams, fixtures, null);

    }

    public List<FplFixtureData> getTeamSpecificFixtures(String teamName) throws IOException {
        String response = fplRepository.getResponse(ALL_FIXTURES_URL);
        var teams = getTeamData();

        var fixtures =  MAPPER.readValue(response, fixturesType);

        return getFplFixtureData(teams, fixtures, teamName);

    }

    public List<FplFixtureData> getTeamSpecificFutureFixtures(String teamName) throws IOException {
        String response = fplRepository.getResponse(FUTURE_FIXTURES_URL);
        var teams = getTeamData();

        var fixtures =  MAPPER.readValue(response, fixturesType);

        return getFplFixtureData(teams, fixtures, teamName);

    }

    private List<FplFixtureData> getFplFixtureData(Map<Integer, String> teams, List<FplFixtureData> fixtures, String teamName) {
        List<FplFixtureData> fixtureList = new ArrayList<>();

        for(FplFixtureData fplFixtureData : fixtures){

            String homeTeamName = getTeamName(fplFixtureData.getHomeTeamIndex(), teams);
            String awayTeamName = getTeamName(fplFixtureData.getAwayTeamIndex(), teams);

            fplFixtureData.setHomeName(homeTeamName);
            fplFixtureData.setAwayName(awayTeamName);

            if((fplFixtureData.getEvent() != null) && (homeTeamName.equals(teamName) || awayTeamName.equals(teamName)) || teamName == null){
                fixtureList.add(fplFixtureData);
            }

        }
        return fixtureList;
    }

    private String getTeamName(String fplFixtureData, Map<Integer, String> teams) {
        int teamIndex = Integer.parseInt(fplFixtureData);
        return String.valueOf(teams.get(teamIndex));
    }

    public Map<Integer, String> getTeamData() throws IOException {
        var teams  = fplTeamService.getTeams();
        Map<Integer, String> teamMap = new HashMap<>();
        for(LinkedHashMap<String, Object> team : teams){
            Integer id = Integer.parseInt(String.valueOf(team.get("id")));
            String name = String.valueOf(team.get("name"));
            teamMap.put(id, name);
        }
        return teamMap;
    }


}
