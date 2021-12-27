package fpl.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import fpl.model.FplData;
import fpl.model.FplFixtureData;
import fpl.model.Player;
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
public class FplElementService {
    private static final String BOOTSTRAP_STATIC = "/bootstrap-static/";
    private static final ObjectMapper MAPPER = new ObjectMapper();
    public static final String ALL_POSITIONS = "all";
    private final FplRepository fplRepository;
    private final FplFixtureService fplFixtureService;
    
    public FplData getFplData() throws IOException {
        String response = fplRepository.getResponse(BOOTSTRAP_STATIC);
        return MAPPER.readValue(response, FplData.class);
    }

    public List<Player> getPlayers() throws IOException {
       return getPlayers(null, ALL_POSITIONS);
    }

    public List<Player> getPlayerDataByName(String name) throws IOException {
        return getPlayers(name, ALL_POSITIONS);
    }
    public List<Player> getTopForwards() throws IOException {
        var forwards = getPlayers(null, "Forward");
        return forwards.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
    }

    public List<Player> getTopMidfielders() throws IOException {
        var midfielders = getPlayers(null, "Midfielder");
        return midfielders.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
    }

    public List<Player> getTopDefenders() throws IOException {
        var defenders = getPlayers(null, "Defender");
        return defenders.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
    }

    public List<Player> getTopGoalkeepers() throws IOException {
        var goalkeepers = getPlayers(null, "Goalkeeper");
        return goalkeepers.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
    }

    public List<Player> getPlayers(String playerName, String position) throws IOException {
        var elements = getFplData().getElements();
        var elementTypes =getFplData().getElementTypes();
        var teams = fplFixtureService.getTeamData();
        List<Player> players = new ArrayList<>();

        for(var element : elements) {
            Player player = getPlayer(element, elementTypes, teams);


            if (player.getPosition().equals(position)) {
                players.add(player);
            }

            if (position.equals("all")){
                players.add(player);
            }

        }

        if( playerName != null){
            for(var player : players){
                if(player.getName().equals(playerName)){
                    List<FplFixtureData> nextFixtures = fplFixtureService.getTeamSpecificFutureFixtures(player.getTeam()).stream().limit(5).collect(Collectors.toList());
                    player.setNextFixtures(nextFixtures);
                    return List.of(player);
                }
            }
        }
        return players.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
    }

    public Player getPlayer(Map<String, Object> element, List<LinkedHashMap<String, Object>> elementTypes, Map<Integer, String> teams) {
        String firstName = String.valueOf(element.get("first_name"));
        String secondName = String.valueOf(element.get("second_name"));
        double ictIndex = Double.parseDouble(String.valueOf(element.get("ict_index")));
        int elementType = Integer.parseInt(String.valueOf(element.get("element_type")));
        String position = String.valueOf(elementTypes.get(elementType - 1).get("singular_name"));
        String id = String.valueOf(element.get("id"));
        String form = String.valueOf(element.get("form"));
        String transfersIn = String.valueOf(element.get("transfers_in"));
        String transfersOut = String.valueOf(element.get("transfers_out"));
        String goals = String.valueOf(element.get("goals_scored"));
        String assists = String.valueOf(element.get("assists"));
        String minutesPlayed = String.valueOf(element.get("minutes_played"));
        String saves = String.valueOf(element.get("saves"));
        String yellowCards = String.valueOf(element.get("yellow_cards"));
        String redCards = String.valueOf(element.get("red_cards"));
        String bonusPoints = String.valueOf(element.get("bonus_points"));
        String selectedBy = String.valueOf(element.get("selected_by_percent"));
        Integer teamIndex = Integer.parseInt(String.valueOf(element.get("team")));
        String teamName =  teams.get(teamIndex);
        String name = firstName + " " + secondName;

        return Player.builder()
                .name(name)
                .position(position)
                .ictIndex(ictIndex)
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



    public List<Player> getTopTeam() throws IOException {
        var goalkeepers = getTopGoalkeepers().stream().limit(2).collect(Collectors.toList());
        var defenders = getTopDefenders().stream().limit(5).collect(Collectors.toList());
        var midfielders = getTopMidfielders().stream().limit(5).collect(Collectors.toList());
        var forwards = getTopForwards().stream().limit(3).collect(Collectors.toList());
        List<Player> team = new ArrayList<>();
        team.addAll(goalkeepers);
        team.addAll(defenders);
        team.addAll(midfielders);
        team.addAll(forwards);

        return team;
    }
}
