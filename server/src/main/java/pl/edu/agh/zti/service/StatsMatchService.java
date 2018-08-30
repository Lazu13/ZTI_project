package pl.edu.agh.zti.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.zti.annotations.logging.Loggable;
import pl.edu.agh.zti.dao.*;
import pl.edu.agh.zti.model.Team;
import pl.edu.agh.zti.model.User;
import pl.edu.agh.zti.model.match.Match;
import pl.edu.agh.zti.model.match.StatsMatch;
import pl.edu.agh.zti.model.predictions.MatchPrediction;
import pl.edu.agh.zti.model.predictions.PointsUser;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Spring service responsible for stats_match-specific data loading
 */
@Service
public class StatsMatchService {

    private final MatchDao matchDao;
    private final StatsMatchDao statsMatchDao;
    private final PointsUserDao pointsUserDao;
    private final MatchPredictionDao matchPredictionDao;
    private final UserDao userDao;
    private final KnockoutMatchDao knockoutMatchDao;

    /**
     * Constructor with autowired arguments
     *
     * @param matchDao           data access object of match
     * @param statsMatchDao      data access object of match stats
     * @param matchPredictionDao data access object of match predictions
     * @param pointsUserDao      data access object of user points
     * @param userDao            data access object of user
     * @param knockoutMatchDao   data access object of knockout match
     */
    @Autowired
    public StatsMatchService(MatchDao matchDao,
                             StatsMatchDao statsMatchDao,
                             MatchPredictionDao matchPredictionDao,
                             PointsUserDao pointsUserDao,
                             UserDao userDao,
                             KnockoutMatchDao knockoutMatchDao) {
        this.matchDao = matchDao;
        this.statsMatchDao = statsMatchDao;
        this.matchPredictionDao = matchPredictionDao;
        this.pointsUserDao = pointsUserDao;
        this.userDao = userDao;
        this.knockoutMatchDao = knockoutMatchDao;
    }


    /**
     * Method allowing to add match statistic.
     * It calculates points for every user and every passed match stats
     * based on user's predictions
     *
     * @param statsMatches match stats to insert
     * @return inserted match stats
     */
    @Loggable
    @Transactional
    public Iterable<StatsMatch> add(List<StatsMatch> statsMatches) {
        List<User> users = userDao.findAll();

        Collections.sort(statsMatches);
        StatsMatch earliestMatch = statsMatches.get(0);
        List<Match> matchesBeforeEarliestMatch = matchDao.findAllByDateBeforeOrderByDate(earliestMatch.getMatch().getDate());

        System.out.println(earliestMatch);

        List<PointsUser> pointsUsers = new ArrayList<>();

        for (User user : users) {
            Long totalPoints = 0L;
            Long userId = user.getId();

            if (matchesBeforeEarliestMatch.size() > 1) {
                Match matchBeforeEarliestMatch = matchesBeforeEarliestMatch.get(matchesBeforeEarliestMatch.size() - 1);

                PointsUser.Id earliestPointsUserId = new PointsUser.Id(userId, matchBeforeEarliestMatch.getId());
                PointsUser earliestPointsUser = pointsUserDao.findOne(earliestPointsUserId);
                if (earliestPointsUser != null)
                    totalPoints = earliestPointsUser.getTotalPoints();
            }

            for (StatsMatch statsMatch : statsMatches) {
                MatchPrediction.Id matchPredictionId = new MatchPrediction.Id(userId, statsMatch.getId());
                MatchPrediction matchPrediction = matchPredictionDao.findOne(matchPredictionId);
                Long roundPoints = 0L;

                if (matchPrediction != null) {
                    roundPoints = calculateRoundPoints(statsMatch, matchPrediction);
                }
                totalPoints += roundPoints;

                PointsUser.Id id = new PointsUser.Id(userId, statsMatch.getId());
                PointsUser pointsUser = PointsUser.builder()
                        .id(id)
                        .points(roundPoints)
                        .totalPoints(totalPoints)
                        .build();
                pointsUsers.add(pointsUser);
            }
        }

        pointsUserDao.save(pointsUsers);
        return statsMatchDao.save(statsMatches);
    }

    private Long calculateRoundPoints(StatsMatch statsMatch, MatchPrediction matchPrediction) {
        long roundPoints = 0L;

        if (matchPrediction.isKnockoutStage()) {
            Team predictedTeamH = matchPrediction.getTeamH();
            Team predictedTeamA = matchPrediction.getTeamA();

            Team teamH = knockoutMatchDao.findOne(statsMatch.getId()).getTeamH();
            Team teamA = knockoutMatchDao.findOne(statsMatch.getId()).getTeamA();

            if (predictedTeamH.getId().equals(teamH.getId()))
                roundPoints += 1L;
            if (predictedTeamA.getId().equals(teamA.getId()))
                roundPoints += 1L;
        }

        int predictedScoreH = matchPrediction.getResultH();
        int predictedScoreA = matchPrediction.getResultA();

        int scoreH = statsMatch.getResultH();
        int scoreA = statsMatch.getResultA();

        if (scoreH > scoreA && predictedScoreH > predictedScoreA)
            roundPoints += 1L;
        else if (scoreH == scoreA && predictedScoreH == predictedScoreA)
            roundPoints += 1L;
        else if (scoreH < scoreA && predictedScoreH < predictedScoreA)
            roundPoints += 1L;

        if (scoreH == predictedScoreH)
            roundPoints += 1L;

        if (scoreA == predictedScoreA)
            roundPoints += 1L;
        return roundPoints;
    }

}
