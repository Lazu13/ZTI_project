package pl.edu.agh.zti.schedulers.matches.knockout;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.agh.zti.dao.KnockoutMatchDao;
import pl.edu.agh.zti.dao.MatchDao;
import pl.edu.agh.zti.dao.TeamDao;
import pl.edu.agh.zti.model.Team;
import pl.edu.agh.zti.model.match.KnockoutMatch;
import pl.edu.agh.zti.model.match.Match;
import pl.edu.agh.zti.model.match.StatsMatch;
import pl.edu.agh.zti.schedulers.Parsing;
import pl.edu.agh.zti.schedulers.matches.MatchJ;
import pl.edu.agh.zti.schedulers.matches.MatchJMapper;
import pl.edu.agh.zti.service.StatsMatchService;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Spring component for parsing and inserting group data
 */
@Component
public class KnockoutParsing extends Parsing {

    private MatchDao matchDao;
    private TeamDao teamDao;
    private KnockoutMatchDao knockoutMatchDao;
    private StatsMatchService statsMatchService;
    private MatchJMapper matchJMapper;

    /**
     * Constructor with autowired arguments
     *
     * @param matchDao data access object of match
     * @param teamDao data access object of team
     * @param statsMatchService service performing match stats specific actions
     * @param knockoutMatchDao data access object of knockout matches
     * @param matchJMapper mapper performing object mapping
     */
    @Autowired
    KnockoutParsing(MatchDao matchDao,
                    TeamDao teamDao,
                    StatsMatchService statsMatchService,
                    KnockoutMatchDao knockoutMatchDao,
                    MatchJMapper matchJMapper) {
        this.matchDao = matchDao;
        this.teamDao = teamDao;
        this.statsMatchService = statsMatchService;
        this.knockoutMatchDao = knockoutMatchDao;
        this.matchJMapper = matchJMapper;
    }

    @Override
    protected void parse(byte[] bytes) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        KnockoutJ[] knockoutJs = objectMapper.readValue(bytes, KnockoutJ[].class);

        List<Match> matches = new ArrayList<>();
        List<KnockoutMatch> knockoutMatches = new ArrayList<>();

        for (KnockoutJ knockoutJ : knockoutJs) {
            String knockoutStage = knockoutJ.getName();
            MatchJ[] matchJs = knockoutJ.getMatches();

            for (MatchJ matchJ : matchJs) {
                Match match = matchJMapper.jToEntity(matchJ);
                Long matchId = matchJ.getName();

                match.setId(matchId);
                matches.add(match);

                Team teamH = teamDao.findOne(matchJ.getHome_team());
                Team teamA = teamDao.findOne(matchJ.getAway_team());

                KnockoutMatch groupMatch = KnockoutMatch.builder()
                        .id(matchId)
                        .teamH(teamH)
                        .teamA(teamA)
                        .teamHDescription(matchJ.getTeamHDescription())
                        .teamADescription(matchJ.getTeamADescription())
                        .stage(knockoutStage)
                        .build();
                knockoutMatches.add(groupMatch);

            }
        }

        matchDao.save(matches);
        knockoutMatchDao.save(knockoutMatches);
    }

    /**
     * Method parsing url with match stats
     *
     * @param url url of file
     * @throws Exception
     */
    @Override
    public void parse(URL url) throws Exception {
        byte[] jsonData = urlToByteArray.convert(url);
        parseWithStats(jsonData);
    }

    protected void parseWithStats(byte[] bytes) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        KnockoutJ[] knockoutJs = objectMapper.readValue(bytes, KnockoutJ[].class);

        List<Match> matches = new ArrayList<>();
        List<KnockoutMatch> knockoutMatches = new ArrayList<>();
        List<StatsMatch> statsMatches = new ArrayList<>();

        for (KnockoutJ knockoutJ : knockoutJs) {
            String knockoutStage = knockoutJ.getName();
            MatchJ[] matchJs = knockoutJ.getMatches();

            for (MatchJ matchJ : matchJs) {
                Match match = matchJMapper.jToEntity(matchJ);
                Long matchId = matchJ.getName();

                match.setId(matchId);
                matches.add(match);

                Team teamH = teamDao.findOne(matchJ.getHome_team());
                Team teamA = teamDao.findOne(matchJ.getAway_team());

                KnockoutMatch groupMatch = KnockoutMatch.builder()
                        .id(matchId)
                        .match(match)
                        .teamH(teamH)
                        .teamA(teamA)
                        .teamHDescription(matchJ.getTeamHDescription())
                        .teamADescription(matchJ.getTeamADescription())
                        .stage(knockoutStage)
                        .build();
                knockoutMatches.add(groupMatch);

                StatsMatch statsMatch = StatsMatch.builder()
                        .id(matchId)
                        .match(match)
                        .resultH(matchJ.getHome_result())
                        .resultA(matchJ.getAway_result())
                        .build();
                statsMatches.add(statsMatch);
            }
        }

        matchDao.save(matches);
        knockoutMatchDao.save(knockoutMatches);
        if (!statsMatches.isEmpty())
            statsMatchService.add(statsMatches);

    }
}
