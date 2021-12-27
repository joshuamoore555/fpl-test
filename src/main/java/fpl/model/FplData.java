package fpl.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.List;

@Getter
@Data
public class FplData {
  //    {
  //      "id": 1,
  //      "name": "Gameweek 1",
  //      "deadline_time": "2021-08-13T17:30:00Z",
  //      "average_entry_score": 69,
  //      "finished": true,
  //      "data_checked": true,
  //      "highest_scoring_entry": 5059647,
  //      "deadline_time_epoch": 1628875800,
  //      "deadline_time_game_offset": 0,
  //      "highest_score": 150,
  //      "is_previous": false,
  //      "is_current": false,
  //      "is_next": false,
  //      "cup_leagues_created": false,
  //      "h2h_ko_matches_created": false,
  //      "chip_plays": [
  //        {
  //          "chip_name": "bboost",
  //          "num_played": 145658
  //        },
  //        {
  //          "chip_name": "3xc",
  //          "num_played": 225749
  //        }
  //      ],
  //      "most_selected": 275,
  //      "most_transferred_in": 1,
  //      "top_element": 277,
  //      "top_element_info": {
  //        "id": 277,
  //        "points": 20
  //      },
  //      "transfers_made": 0,
  //      "most_captained": 233,
  //      "most_vice_captained": 277
  //    }
  private List<LinkedHashMap<String, Object>> events;

  // {
  //         "id":1,
  //         "name":"Overall",
  //         "start_event":1,
  //         "stop_event":38
  //      },
  //      {
  //         "id":2,
  //         "name":"August",
  //         "start_event":1,
  //         "stop_event":3
  //      },

  private List<LinkedHashMap<String, Object>> phases;



  //{
  //         "code":3,
  //         "draw":0,
  //         "form":null,
  //         "id":1,
  //         "loss":0,
  //         "name":"Arsenal",
  //         "played":0,
  //         "points":0,
  //         "position":0,
  //         "short_name":"ARS",
  //         "strength":4,
  //         "team_division":null,
  //         "unavailable":false,
  //         "win":0,
  //         "strength_overall_home":1190,
  //         "strength_overall_away":1250,
  //         "strength_attack_home":1110,
  //         "strength_attack_away":1140,
  //         "strength_defence_home":1110,
  //         "strength_defence_away":1170,
  //         "pulse_id":1
  //      },
  private List<LinkedHashMap<String, Object>> teams;


  // {
  //         "chance_of_playing_next_round":100,
  //         "chance_of_playing_this_round":75,
  //         "code":80201,
  //         "cost_change_event":-1,
  //         "cost_change_event_fall":1,
  //         "cost_change_start":-5,
  //         "cost_change_start_fall":5,
  //         "dreamteam_count":0,
  //         "element_type":1,
  //         "ep_next":"1.0",
  //         "ep_this":"0.4",
  //         "event_points":0,
  //         "first_name":"Bernd",
  //         "form":"0.0",
  //         "id":1,
  //         "in_dreamteam":false,
  //         "news":"",
  //         "news_added":"2021-12-06T23:00:13.342729Z",
  //         "now_cost":45,
  //         "photo":"80201.jpg",
  //         "points_per_game":"1.3",
  //         "second_name":"Leno",
  //         "selected_by_percent":"0.9",
  //         "special":false,
  //         "squad_number":null,
  //         "status":"a",
  //         "team":1,
  //         "team_code":3,
  //         "total_points":4,
  //         "transfers_in":60915,
  //         "transfers_in_event":163,
  //         "transfers_out":181354,
  //         "transfers_out_event":440,
  //         "value_form":"0.0",
  //         "value_season":"0.9",
  //         "web_name":"Leno",
  //         "minutes":270,
  //         "goals_scored":0,
  //         "assists":0,
  //         "clean_sheets":0,
  //         "goals_conceded":9,
  //         "own_goals":0,
  //         "penalties_saved":0,
  //         "penalties_missed":0,
  //         "yellow_cards":0,
  //         "red_cards":0,
  //         "saves":9,
  //         "bonus":0,
  //         "bps":48,
  //         "influence":"79.0",
  //         "creativity":"0.0",
  //         "threat":"0.0",
  //         "ict_index":"7.9",
  //         "influence_rank":317,
  //         "influence_rank_type":25,
  //         "creativity_rank":545,
  //         "creativity_rank_type":60,
  //         "threat_rank":522,
  //         "threat_rank_type":54,
  //         "ict_index_rank":375,
  //         "ict_index_rank_type":25,
  //         "corners_and_indirect_freekicks_order":null,
  //         "corners_and_indirect_freekicks_text":"",
  //         "direct_freekicks_order":null,
  //         "direct_freekicks_text":"",
  //         "penalties_order":null,
  //         "penalties_text":""
  //      },
  private List<LinkedHashMap<String, Object>> elements;

  // "game_settings":{
//      "league_join_private_max":25,
//      "league_join_public_max":5,
//      "league_max_size_public_classic":20,
//      "league_max_size_public_h2h":16,
//      "league_max_size_private_h2h":16,
//      "league_max_ko_rounds_private_h2h":3,
//      "league_prefix_public":"League",
//      "league_points_h2h_win":3,
//      "league_points_h2h_lose":0,
//      "league_points_h2h_draw":1,
//      "league_ko_first_instead_of_random":false,
//      "cup_start_event_id":null,
//      "cup_stop_event_id":null,
//      "cup_qualifying_method":null,
//      "cup_type":null,
//      "squad_squadplay":11,
//      "squad_squadsize":15,
//      "squad_team_limit":3,
//      "squad_total_spend":1000,
//      "ui_currency_multiplier":10,
//      "ui_use_special_shirts":false,
//      "ui_special_shirt_exclusions":[
//
//      ],
//      "stats_form_days":30,
//      "sys_vice_captain_enabled":true,
//      "transfers_cap":1000,
//      "transfers_sell_on_fee":0.5,
//      "league_h2h_tiebreak_stats":[
//         "+goals_scored",
//         "-goals_conceded"
//      ],
//      "timezone":"UTC"
//   },
  @JsonProperty("game_settings")
  private LinkedHashMap<String, Object> gameSettings;

  //   "total_players":8821197,
  @JsonProperty("total_players")
  private Integer totalPlayers;

  //"element_stats":[
//      {
//         "label":"Minutes played",
//         "name":"minutes"
//      },
//      {
//         "label":"Goals scored",
//         "name":"goals_scored"
//      },
//      {
//         "label":"Assists",
//         "name":"assists"
//      },
//      {
//         "label":"Clean sheets",
//         "name":"clean_sheets"
//      },
//      {
//         "label":"Goals conceded",
//         "name":"goals_conceded"
//      },
//      {
//         "label":"Own goals",
//         "name":"own_goals"
//      },
//      {
//         "label":"Penalties saved",
//         "name":"penalties_saved"
//      },
//      {
//         "label":"Penalties missed",
//         "name":"penalties_missed"
//      },
//      {
//         "label":"Yellow cards",
//         "name":"yellow_cards"
//      },
//      {
//         "label":"Red cards",
//         "name":"red_cards"
//      },
//      {
//         "label":"Saves",
//         "name":"saves"
//      },
//      {
//         "label":"Bonus",
//         "name":"bonus"
//      },
//      {
//         "label":"Bonus Points System",
//         "name":"bps"
//      },
//      {
//         "label":"Influence",
//         "name":"influence"
//      },
//      {
//         "label":"Creativity",
//         "name":"creativity"
//      },
//      {
//         "label":"Threat",
//         "name":"threat"
//      },
//      {
//         "label":"ICT Index",
//         "name":"ict_index"
//      }
//   ],
  @JsonProperty("element_stats")
  private List<LinkedHashMap<String, Object>> elementStats;
  // "element_types":[
//      {
//         "id":1,
//         "plural_name":"Goalkeepers",
//         "plural_name_short":"GKP",
//         "singular_name":"Goalkeeper",
//         "singular_name_short":"GKP",
//         "squad_select":2,
//         "squad_min_play":1,
//         "squad_max_play":1,
//         "ui_shirt_specific":true,
//         "sub_positions_locked":[
//            12
//         ],
//         "element_count":78
//      },
  @JsonProperty("element_types")
  private List<LinkedHashMap<String, Object>> elementTypes;
}
