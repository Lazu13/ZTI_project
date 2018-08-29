package pl.edu.agh.zti.schedulers.team;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.agh.zti.annotations.logging.Loggable;
import pl.edu.agh.zti.dao.GroupDao;
import pl.edu.agh.zti.dao.TeamDao;
import pl.edu.agh.zti.model.Group;
import pl.edu.agh.zti.model.Team;
import pl.edu.agh.zti.schedulers.Parsing;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Spring component for parsing and inserting team data
 */
@Component
public class TeamParsing extends Parsing {

    private TeamDao teamDao;
    private GroupDao groupDao;
    private TeamJMapper teamJMapper;

    private int maxNumberOfGroups = 8;
    private int maxNumberOfTeamsInGroup = 4;
    private Map<Long, Long> groupIds = new TreeMap<>();

    /**
     * Constructor with autowired arguments
     *
     * @param teamDao     data access object of team
     * @param groupDao    data access object of group
     * @param teamJMapper mapper performing object mapping
     */
    @Autowired
    TeamParsing(TeamDao teamDao,
                GroupDao groupDao,
                TeamJMapper teamJMapper
    ) {
        this.teamDao = teamDao;
        this.groupDao = groupDao;
        this.teamJMapper = teamJMapper;

        for (long i = 0; i < maxNumberOfGroups; i++) {
            for (long j = 1; j <= maxNumberOfTeamsInGroup; j++) {
                groupIds.put(i * maxNumberOfTeamsInGroup + j, i + 1);
            }
        }
    }

    private long getGroupId(long teamId) {
        return groupIds.get(teamId);
    }

    /**
     * Method parsing file in byte[] and inserting data to db
     *
     * @param bytes of file
     * @throws Exception if something goes wrong
     */
    @Override
    @Loggable
    public void parse(byte[] bytes) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        TeamJ[] teamJs = objectMapper.readValue(bytes, TeamJ[].class);

        List<Team> teams = new ArrayList<>();
        for (TeamJ teamJ : teamJs) {
            Team teamEntity = teamJMapper.jToEntity(teamJ);
            Long groupId = getGroupId(teamJ.getId());
            Group group = groupDao.findById(groupId).get();
            teamEntity.setGroup(group);
            teams.add(teamEntity);
        }

        teamDao.save(teams);
    }


}