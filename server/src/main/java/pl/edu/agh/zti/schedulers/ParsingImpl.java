package pl.edu.agh.zti.schedulers;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.edu.agh.zti.annotations.logging.Loggable;
import pl.edu.agh.zti.dao.DeadlineDao;
import pl.edu.agh.zti.dao.MatchDao;
import pl.edu.agh.zti.dao.NextMatchDao;
import pl.edu.agh.zti.model.match.Match;
import pl.edu.agh.zti.model.schedulers.Deadline;
import pl.edu.agh.zti.model.schedulers.NextMatch;
import pl.edu.agh.zti.schedulers.group.GroupParsing;
import pl.edu.agh.zti.schedulers.matches.group.GroupMatchParsing;
import pl.edu.agh.zti.schedulers.matches.knockout.KnockoutParsing;
import pl.edu.agh.zti.schedulers.team.TeamParsing;

import java.net.URL;
import java.util.Date;
import java.util.List;

/**
 * Spring component run on application start
 */
@Data
@Component
public class ParsingImpl implements ApplicationRunner {

    private TeamParsing teamParsing;
    private GroupParsing groupParsing;
    private GroupMatchParsing groupMatchParsing;
    private KnockoutParsing knockoutParsing;
    private NextMatchDao nextMatchDao;
    private MatchDao matchDao;
    private DeadlineDao deadlineDao;
    private Boolean hasUpdated = false;

    private final String basicPath = "https://raw.githubusercontent.com/Lazu13/ZTI_data/master/";

    /**
     * Constructor with autowired arguments
     *
     * @param teamParsing parsing class for team data
     * @param groupParsing parsing class for group data
     * @param groupMatchParsing parsing class for group_match data
     * @param knockoutParsing parsing class for knockout match data
     * @param nextMatchDao parsing class for next_match data
     * @param matchDao parsing class for match data
     * @param deadlineDao parsing class for deadline data
     */
    @Autowired
    ParsingImpl(TeamParsing teamParsing, GroupParsing groupParsing,
                GroupMatchParsing groupMatchParsing, KnockoutParsing knockoutParsing,
                NextMatchDao nextMatchDao, MatchDao matchDao, DeadlineDao deadlineDao) {
        this.teamParsing = teamParsing;
        this.groupParsing = groupParsing;
        this.groupMatchParsing = groupMatchParsing;
        this.knockoutParsing = knockoutParsing;
        this.nextMatchDao = nextMatchDao;
        this.matchDao = matchDao;
        this.deadlineDao = deadlineDao;
    }

    /**
     * Method run on application start.
     * It parses necessary files, inserts them to db and specifies next match
     *
     * @param applicationArguments application arguments
     * @throws Exception if something goes wrong
     */
    @Override
    @Loggable
    public void run(ApplicationArguments applicationArguments) throws Exception {
        groupParsing.parse("groups.json");
        teamParsing.parse("teams.json");
        groupMatchParsing.parse("groups.json");
        knockoutParsing.parse("knockouts.json");

        Match match = matchDao.findByFinishedFalseOrderByDate().get(0);
        NextMatch nextMatch = new NextMatch(1L, match);
        nextMatchDao.save(nextMatch);
    }

    private String buildPath(String fileName) {
        return basicPath + fileName;
    }


    /**
     * Method scheduled for every minute to check if it is after deadline.
     * If so it loads necessary data from specified url (only once!).
     *
     * @throws Exception if something goes wrong
     */
    @Loggable
    @Scheduled(cron = "0 * * * * *")
    public void scheduledRun() throws Exception {
        Deadline deadline = deadlineDao.findAll().get(0);

        if (deadline.getDate().before(new Date())) {
            if (!this.hasUpdated) {
                groupMatchParsing.parse(new URL(buildPath("groups.json")));
                knockoutParsing.parse(new URL(buildPath("knockouts.json")));

                List<Match> nextMatches = matchDao.findByFinishedFalseOrderByDate();
                if (!nextMatches.isEmpty()) {
                    Match match = nextMatches.get(0);
                    NextMatch nextMatch = new NextMatch(1L, match);
                    nextMatchDao.save(nextMatch);
                } else {
                    List<Match> matchesOldest = matchDao.findTop10ByOrderByDateDesc();
                    Match match = matchesOldest.get(0);
                    NextMatch nextMatch = new NextMatch(1L, match);
                    nextMatchDao.save(nextMatch);
                }

                this.hasUpdated = true;
            }
        }

    }
}
