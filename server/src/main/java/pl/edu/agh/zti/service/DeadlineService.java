package pl.edu.agh.zti.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.zti.annotations.logging.Loggable;
import pl.edu.agh.zti.dao.DeadlineDao;
import pl.edu.agh.zti.dto.DeadlineDto;
import pl.edu.agh.zti.mapper.DeadlineMapper;
import pl.edu.agh.zti.model.schedulers.Deadline;

import javax.transaction.Transactional;

/**
 * Spring service responsible for deadline-specific data loading
 */
@Service
@Transactional
public class DeadlineService {

    private final DeadlineDao deadlineDao;
    private final DeadlineMapper deadlineMapper;

    /**
     * Constructor with autowired arguments
     *
     * @param deadlineDao data access object of deadline
     * @param deadlineMapper deadline mapper
     */
    @Autowired
    public DeadlineService(
            DeadlineDao deadlineDao,
            DeadlineMapper deadlineMapper
    ) {
        this.deadlineDao = deadlineDao;
        this.deadlineMapper = deadlineMapper;
    }

    /**
     * Method allowing to gather deadline
     *
     * @return deadline data transfer object
     */
    @Loggable
    public DeadlineDto get() {
        Long deadlineId = 1L;
        Deadline deadline = deadlineDao.findOne(deadlineId);
        return deadlineMapper.entityToDto(deadline);
    }

}
