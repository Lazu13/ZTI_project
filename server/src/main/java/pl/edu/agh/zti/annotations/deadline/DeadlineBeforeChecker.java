package pl.edu.agh.zti.annotations.deadline;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.agh.zti.dao.DeadlineDao;
import pl.edu.agh.zti.exceptions.TimeExceededException;
import pl.edu.agh.zti.model.schedulers.Deadline;

import java.util.Date;
import java.util.List;

/**
 * Spring component being an Aspect
 */
@Component
@Aspect
public class DeadlineBeforeChecker {

    private final DeadlineDao deadlineDao;

    /**
     * Constructor with autowired arguments
     *
     * @param deadlineDao data access object for deadline
     */
    @Autowired
    public DeadlineBeforeChecker(DeadlineDao deadlineDao) {
        this.deadlineDao = deadlineDao;
    }

    /**
     * Before invoke method.
     * It checks for deadline date before current date to make sure action is taken
     * only in certain point of time
     *
     * @throws Throwable if deadline date is after current date
     */
    @Before("@annotation(pl.edu.agh.zti.annotations.deadline.DeadlineBeforeCheckerable)")
    public void beforeInvoke() throws Throwable {
        Date date = new Date();
        List<Deadline> deadlines = deadlineDao.findAllByDateBefore(date);
        if (deadlines.isEmpty()) throw new TimeExceededException();
    }

}
