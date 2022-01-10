package fpl.service.teams;

import fpl.model.Player;
import fpl.service.players.FplPlayerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class FplTeamRankingService {
  private final FplPlayerService fplPlayerService;

  public List<Player> getTopTeam() throws IOException {
    final var goalkeepers =
        fplPlayerService.getTopGoalkeepers().stream().limit(2).collect(Collectors.toList());
    final var defenders =
        fplPlayerService.getTopDefenders().stream().limit(5).collect(Collectors.toList());
    final var midfielders =
        fplPlayerService.getTopMidfielders().stream().limit(5).collect(Collectors.toList());
    final var forwards =
        fplPlayerService.getTopForwards().stream().limit(3).collect(Collectors.toList());
    final List<Player> team = new ArrayList<>();
    team.addAll(goalkeepers);
    team.addAll(defenders);
    team.addAll(midfielders);
    team.addAll(forwards);

    return team;
  }
}
