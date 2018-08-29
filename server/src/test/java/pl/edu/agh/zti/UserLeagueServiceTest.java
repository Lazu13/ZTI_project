package pl.edu.agh.zti;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.edu.agh.zti.dao.UserLeagueDao;
import pl.edu.agh.zti.enums.UserLeagueStatus;
import pl.edu.agh.zti.exceptions.IllegalUserGroupStatus;
import pl.edu.agh.zti.model.UserLeague;
import pl.edu.agh.zti.service.UserLeagueService;

import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class UserLeagueServiceTest {

    @MockBean
    UserLeagueDao userLeagueDaoMock;

    @Autowired
    UserLeagueService userLeagueService;

    @Test(expected = IllegalUserGroupStatus.class)
    public void inviteFailTest() {
        UserLeague.Id id = new UserLeague.Id(1L, 1L);
        UserLeague userLeague = UserLeague.builder().id(id).status(UserLeagueStatus.PARTICIPANT).build();

        when(userLeagueDaoMock.findById(id)).thenReturn(Optional.of(userLeague));
        userLeagueService.invite(1L,1L,false);
    }

    @Test
    public void inviteSuccessTest() {
        UserLeague.Id id = new UserLeague.Id(1L, 1L);
        UserLeague userLeague = UserLeague.builder().id(id).status(UserLeagueStatus.REJECTED).build();

        when(userLeagueDaoMock.findById(id)).thenReturn(Optional.of(userLeague));
        userLeagueService.invite(1L,1L,false);
        verify(userLeagueDaoMock, times(1)).save(userLeague);
    }
}
