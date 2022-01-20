package fpl.service.players;

import fpl.model.FplFutureFixture;
import fpl.model.Player;
import fpl.service.base.FplElementService;
import fpl.service.fixtures.FplFutureFixtureService;
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
public class FplPlayerService {
  public static final String ALL_POSITIONS = "all";
  private final FplElementService fplElementService;
  private final FplFutureFixtureService fplFutureFixtureService;
  private final FplTeamService fplTeamService;

  public List<Player> getPlayers() throws IOException {
    return getPlayers(ALL_POSITIONS);
  }

  public List<Player> getPlayerDataByName(final String name) throws IOException {
    final var players = getPlayers(ALL_POSITIONS);
    return players.stream()
        .filter(player -> player.getName().equals(name))
        .collect(Collectors.toList());
  }

  public List<Player> getTopForwards() throws IOException {
    final var forwards = getPlayers("Forward");
    return forwards.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
  }

  public List<Player> getTopMidfielders() throws IOException {
    final var midfielders = getPlayers("Midfielder");
    return midfielders.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
  }

  public List<Player> getTopDefenders() throws IOException {
    final var defenders = getPlayers("Defender");
    return defenders.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
  }

  public List<Player> getTopGoalkeepers() throws IOException {
    final var goalkeepers = getPlayers("Goalkeeper");
    return goalkeepers.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
  }

  public List<Player> getPlayers(final String position) throws IOException {
    final var elements = fplElementService.getFplData().getElements();
    final var elementTypes = fplElementService.getFplData().getElementTypes();
    final List<FplFutureFixture> fixtureList = fplFutureFixtureService.getFutureDgwFixtures();
    final var teams = fplFutureFixtureService.getTeamData(fplTeamService.getTeams());

    final List<Player> players = new ArrayList<>();

    for (final var element : elements) {
      final Player player = getPlayer(element, elementTypes, teams);
      setDgw(fixtureList, player);
      setPlayerRating(player);

      if (player.getPosition().equals(position)) {
        players.add(player);
      }

      if (position.equals("all")) {
        players.add(player);
      }
    }
    return players.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
  }

  private void setDgw(final List<FplFutureFixture> fixtureList, final Player player) {
    final var isDgw =
        fixtureList.stream().anyMatch(fixture -> fixture.getTeamName().equals(player.getTeam()));
    player.setDgw(isDgw);
  }

  private void setPlayerRating(final Player player) {
    final double index = player.getIctIndex();
    final double form = Double.parseDouble(player.getForm()) * 100;
    final double selectedBy = Double.parseDouble(player.getSelectedBy()) * 100;
    // final double chanceThisRound = player.getChanceThisRound();
    final double chanceNextRound = player.getChanceNextRound();
    final double chance = (chanceNextRound) / 1000;
    final String logValue =
        "Name {}  "
            + "Index {} "
            + "Form {} "
            + "Selected By {} "
            + "Player Rating {} "
            + "Chance {}";

    double playerRating = (index + form + selectedBy) * chance;
    if (player.isDgw()) {
      playerRating *= 2;
    }
    player.setPlayerRating(playerRating);
    //    log.info(
    //        logValue,
    //        player.getName(),
    //        player.getIctIndex(),
    //        player.getForm(),
    //        player.getSelectedBy(),
    //        player.getPlayerRating(),
    //        chance);
  }

  public Player getPlayer(
      final Map<String, Object> element,
      final List<LinkedHashMap<String, Object>> elementTypes,
      final Map<Integer, String> teams) {
    final String firstName = String.valueOf(element.get("first_name"));
    final String secondName = String.valueOf(element.get("second_name"));
    final double ictIndex = Double.parseDouble(String.valueOf(element.get("ict_index")));
    final int elementType = Integer.parseInt(String.valueOf(element.get("element_type")));
    final String position = String.valueOf(elementTypes.get(elementType - 1).get("singular_name"));
    final String id = String.valueOf(element.get("id"));
    final String form = String.valueOf(element.get("form"));
    final Integer transfersIn = Integer.parseInt(String.valueOf(element.get("transfers_in")));
    final Integer transfersOut = Integer.parseInt(String.valueOf(element.get("transfers_out")));
    final String goals = String.valueOf(element.get("goals_scored"));
    final String assists = String.valueOf(element.get("assists"));
    final String minutesPlayed = String.valueOf(element.get("minutes_played"));
    final String saves = String.valueOf(element.get("saves"));
    final String yellowCards = String.valueOf(element.get("yellow_cards"));
    final String redCards = String.valueOf(element.get("red_cards"));
    final String bonusPoints = String.valueOf(element.get("bonus_points"));
    final String selectedBy = String.valueOf(element.get("selected_by_percent"));
    final Integer teamIndex = Integer.parseInt(String.valueOf(element.get("team")));
    final var chanceNextRoundString = String.valueOf(element.get("chance_of_playing_next_round"));
    final var chanceThisRoundString = String.valueOf(element.get("chance_of_playing_this_round"));
    final String name = firstName + " " + secondName;

    final Integer chanceNextRound = getChance(String.valueOf(element), chanceNextRoundString);

    final String teamName = teams.get(teamIndex);
    if (secondName.equals("Salah")) {
      log.info(String.valueOf(element));
    }

    return Player.builder()
        .name(name)
        .position(position)
        .ictIndex(ictIndex)
        .chanceNextRound(chanceNextRound)
        // .chanceThisRound(chanceThisRound)
        .assists(assists)
        .bonusPoints(bonusPoints)
        .form(form)
        .goals(goals)
        .id(id)
        .transfersIn(transfersIn)
        .transfersOut(transfersOut)
        .minutesPlayed(minutesPlayed)
        .redCards(redCards)
        .yellowCards(yellowCards)
        .saves(saves)
        .selectedBy(selectedBy)
        .team(teamName)
        .build();
  }

  private Integer getChance(final String playerName, final String chance) {
    final int chanceNextRound;
    if (chance.equals("null")) {
      // log.info(playerName);
      chanceNextRound = 100;
    } else {
      chanceNextRound = Integer.parseInt(chance);
    }
    return chanceNextRound;
  }
}
