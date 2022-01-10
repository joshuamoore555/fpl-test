package fpl.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class Player implements Comparable<Player> {
  String name;
  String position;
  String id;
  String form;
  Integer transfersIn;
  Integer transfersOut;
  Integer chanceNextRound;
  Integer chanceThisRound;
  String goals;
  String assists;
  String minutesPlayed;
  String saves;
  String yellowCards;
  String redCards;
  String bonusPoints;
  String team;
  String selectedBy;
  boolean isDgw;
  List<FplFixtureData> nextFixtures;
  double ictIndex;
  double playerRating;

  @Override
  public int compareTo(final Player player) {
    return Double.compare(playerRating, player.getPlayerRating());
  }
}
