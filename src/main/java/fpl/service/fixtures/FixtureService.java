package fpl.service.fixtures;

import fpl.model.FplFixtureData;

import java.util.*;

public interface FixtureService {
  default List<FplFixtureData> getFplFixtureData(
      final Map<Integer, String> teams,
      final List<FplFixtureData> fixtures,
      final String teamName) {
    final List<FplFixtureData> fixtureList = new ArrayList<>();

    for (final FplFixtureData fplFixtureData : fixtures) {

      final String homeTeamName = getTeamName(fplFixtureData.getHomeTeamIndex(), teams);
      final String awayTeamName = getTeamName(fplFixtureData.getAwayTeamIndex(), teams);

      fplFixtureData.setHomeName(homeTeamName);
      fplFixtureData.setAwayName(awayTeamName);

      if ((fplFixtureData.getEvent() != null)
              && (homeTeamName.equals(teamName) || awayTeamName.equals(teamName))
          || teamName == null) {
        fixtureList.add(fplFixtureData);
      }
    }
    return fixtureList;
  }

  private String getTeamName(final String fplFixtureData, final Map<Integer, String> teams) {
    final int teamIndex = Integer.parseInt(fplFixtureData);
    return String.valueOf(teams.get(teamIndex));
  }

  default Map<Integer, String> getTeamData(final List<LinkedHashMap<String, Object>> teams) {
    final Map<Integer, String> teamMap = new HashMap<>();
    for (final LinkedHashMap<String, Object> team : teams) {
      final Integer id = Integer.parseInt(String.valueOf(team.get("id")));
      final String name = String.valueOf(team.get("name"));
      teamMap.put(id, name);
    }
    return teamMap;
  }
}
