package pl.edu.agh.zti;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.SerializationUtils;
import pl.edu.agh.zti.controller.LeagueController;
import pl.edu.agh.zti.dto.leagues.LeagueReturnDto;
import pl.edu.agh.zti.service.LeagueService;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class LeagueControllerTests {

    @Mock
    private LeagueService leagueServiceMock;

    private MockMvc mvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders
                .standaloneSetup(new LeagueController(leagueServiceMock))
                .build();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testGetInvitations() throws Exception {
        ArrayList<LeagueReturnDto> result = new ArrayList<>();
        LeagueReturnDto leagueDto = new LeagueReturnDto();
        leagueDto.setName("XD");
        leagueDto.setId(1L);
        result.add(leagueDto);
        PageImpl page = new PageImpl<>(result);

        when(leagueServiceMock.getLeagueInvitations(0, 15)).thenReturn(page);
        mvc.perform(get("/leagues/invitations").param("page", "0").param("size", "15"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("XD"))
                .andExpect(jsonPath("$.content[0].id").value("1"));
    }


}
