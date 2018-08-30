package pl.edu.agh.zti.schedulers.matches.group;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.agh.zti.dao.GroupDao;
import pl.edu.agh.zti.dao.GroupMatchDao;
import pl.edu.agh.zti.dao.MatchDao;
import pl.edu.agh.zti.dao.TeamDao;
import pl.edu.agh.zti.model.Group;
import pl.edu.agh.zti.model.Team;
import pl.edu.agh.zti.model.match.GroupMatch;
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
@Data
@Component
public class GroupMatchParsing extends Parsing {

    private MatchDao matchDao;
    private GroupDao groupDao;
    private TeamDao teamDao;
    private GroupMatchDao groupMatchDao;
    private StatsMatchService statsMatchService;
    private MatchJMapper matchJMapper;

    /**
     * Constructor with autowired arguments
     *
     * @param matchDao data access object of match
     * @param groupDao data access object of group
     * @param teamDao data access object of team
     * @param groupMatchDao data access object of group matches
     * @param statsMatchService service performing match stats specific actions
     * @param matchJMapper mapper performing object mapping
     */
    @Autowired
    GroupMatchParsing(MatchDao matchDao,
                      GroupDao groupDao,
                      TeamDao teamDao,
                      GroupMatchDao groupMatchDao,
                      StatsMatchService statsMatchService,
                      MatchJMapper matchJMapper) {
        this.matchDao = matchDao;
        this.groupDao = groupDao;
        this.teamDao = teamDao;
        this.groupMatchDao = groupMatchDao;
        this.statsMatchService = statsMatchService;
        this.matchJMapper = matchJMapper;
    }

    @Override
    protected void parse(byte[] bytes) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        GroupMatchesJ[] groupMatchesJs = objectMapper.readValue(bytes, GroupMatchesJ[].class);

        List<Match> matches = new ArrayList<>();
        List<GroupMatch> groupMatches = new ArrayList<>();
        for (GroupMatchesJ groupMatchesJ : groupMatchesJs) {
            String groupName = groupMatchesJ.getName();
            MatchJ[] matchJs = groupMatchesJ.getMatches();

            for (MatchJ matchJ : matchJs) {
                Match match = matchJMapper.jToEntity(matchJ);
                Long matchId = matchJ.getName();

                match.setId(matchId);
                matches.add(match);

                Group group = groupDao.findByName(groupName).get();
                Team teamH = teamDao.findOne(matchJ.getHome_team());
                Team teamA = teamDao.findOne(matchJ.getAway_team());
                GroupMatch groupMatch = GroupMatch.builder()
                        .id(matchId)
                        .matchGroup(group)
                        .teamH(teamH)
                        .teamA(teamA)
                        .build();
                groupMatches.add(groupMatch);
            }
        }

        matchDao.save(matches);
        groupMatchDao.save(groupMatches);
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

    private void parseWithStats(byte[] bytes) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        GroupMatchesJ[] groupMatchesJs = objectMapper.readValue(bytes, GroupMatchesJ[].class);

        List<Match> matches = new ArrayList<>();
        List<GroupMatch> groupMatches = new ArrayList<>();
        List<StatsMatch> statsMatches = new ArrayList<>();

        for (GroupMatchesJ groupMatchesJ : groupMatchesJs) {
            String groupName = groupMatchesJ.getName();
            MatchJ[] matchJs = groupMatchesJ.getMatches();

            for (MatchJ matchJ : matchJs) {
                Match match = matchJMapper.jToEntity(matchJ);
                Long matchId = matchJ.getName();

                match.setId(matchId);
                matches.add(match);

                Group group = groupDao.findByName(groupName).get();
                Team teamH = teamDao.findOne(matchJ.getHome_team());
                Team teamA = teamDao.findOne(matchJ.getAway_team());
                GroupMatch groupMatch = GroupMatch.builder()
                        .id(matchId)
                        .match(match)
                        .matchGroup(group)
                        .teamH(teamH)
                        .teamA(teamA)
                        .build();
                groupMatches.add(groupMatch);

                StatsMatch statsMatch = StatsMatch.builder()
                        .id(matchId)
                        .match(match)
                        .resultH(matchJ.getHome_result())
                        .resultA(matchJ.getAway_result())
                        .build();
                System.out.println(statsMatch.getResultH());
                System.out.println(statsMatch.getResultA());

                statsMatches.add(statsMatch);
            }
        }

        matchDao.save(matches);
        groupMatchDao.save(groupMatches);
        if (!statsMatches.isEmpty())
            statsMatchService.add(statsMatches);

    }
}
