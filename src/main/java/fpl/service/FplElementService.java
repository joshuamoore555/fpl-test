package fpl.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import fpl.model.FplData;
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
    private final FplRepository fplRepository;
    

    public FplData getFplData() throws IOException {
        String response = fplRepository.getResponse(BOOTSTRAP_STATIC);
        return  MAPPER.readValue(response, FplData.class);
    }


    public Map<Double, Player> getIctIndex() throws IOException {
        var elements =getFplData().getElements();
        var elementTypes =getFplData().getElementTypes();

        Map<Double, Player> ictRanking = new TreeMap<>(Collections.reverseOrder());

        for(var element : elements){
            String firstName = String.valueOf(element.get("first_name"));
            String secondName = String.valueOf(element.get("second_name"));
            String ictIndexString = String.valueOf(element.get("ict_index"));
            int elementType = Integer.parseInt(String.valueOf(element.get("element_type")));
            var position = elementTypes.get(elementType-1).get("singular_name");
            Double ictIndex = Double.valueOf(ictIndexString);
            Player player = Player.builder().name(firstName + " " + secondName).position(position.toString()).build();
            ictRanking.put(ictIndex, player);
        }
        return ictRanking;
    }

    public List<Player> getTopForwards() throws IOException {
        var forwards = getTopPlayers("Forward");
        return forwards.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());

    }

    public List<Player> getTopMidfielders() throws IOException {
        var midfielders = getTopPlayers("Midfielder");
        return midfielders.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
    }

    public List<Player> getTopDefenders() throws IOException {
        var defenders = getTopPlayers("Defender");
        return defenders.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
    }

    public List<Player> getTopGoalkeepers() throws IOException {
        var goalkeepers = getTopPlayers("Goalkeeper");
        return goalkeepers.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
    }

    public List<Player> getTopPlayers(String position) throws IOException {
        var elements =getFplData().getElements();
        var elementTypes =getFplData().getElementTypes();
        List<Player> playersByPosition = new ArrayList<>();

        for(var element : elements) {
            Player player = getPlayer(element, elementTypes);

            if (player.getPosition().equals(position)) {
                playersByPosition.add(player);
            }

        }
        return playersByPosition.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
    }

    public Player getPlayer(Map<String, Object> element, List<LinkedHashMap<String, Object>> elementTypes){
        String firstName = String.valueOf(element.get("first_name"));
        String secondName = String.valueOf(element.get("second_name"));
        String ictIndexString = String.valueOf(element.get("ict_index"));
        int elementType = Integer.parseInt(String.valueOf(element.get("element_type")));
        String position = String.valueOf(elementTypes.get(elementType - 1).get("singular_name"));
        double ictIndex = Double.parseDouble(ictIndexString);

        return Player.builder().name(firstName + " " + secondName).position(position).ictIndex(ictIndex).build();
    }



    public   List<Player> getTopTeam() throws IOException {
        var goalkeepers = getTopGoalkeepers().stream().limit(1).collect(Collectors.toList());
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
