package pl.edu.agh.zti.schedulers.group;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.agh.zti.dao.GroupDao;
import pl.edu.agh.zti.model.Group;
import pl.edu.agh.zti.schedulers.Parsing;

import java.util.ArrayList;
import java.util.List;

/**
 * Spring component for parsing and inserting group data
 */
@Component
public class GroupParsing extends Parsing {

    private GroupDao groupDao;
    private pl.edu.agh.zti.schedulers.group.GroupJMapper groupJMapper;

    /**
     * Constructor with autowired arguments
     *
     * @param groupDao     data access object of group
     * @param groupJMapper mapper performing object mapping
     */
    @Autowired
    GroupParsing(GroupDao groupDao, pl.edu.agh.zti.schedulers.group.GroupJMapper groupJMapper) {
        this.groupDao = groupDao;
        this.groupJMapper = groupJMapper;
    }

    @Override
    protected void parse(byte[] bytes) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        GroupJ[] groupJs = objectMapper.readValue(bytes, GroupJ[].class);

        List<Group> groups = new ArrayList<>();
        for (GroupJ groupJ : groupJs) {
            Group groupEntity = groupJMapper.jToEntity(groupJ);
            groups.add(groupEntity);
        }

        groupDao.save(groups);
    }
}
