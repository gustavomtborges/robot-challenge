package ca.com.br.robotchallenge.controller;

import ca.com.br.robotchallenge.exception.InvalidInstructionException;
import ca.com.br.robotchallenge.exception.InvalidMovementException;
import ca.com.br.robotchallenge.model.Robot;
import ca.com.br.robotchallenge.service.NavigateMarsService;
import ca.com.br.robotchallenge.utils.OrientationEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(NavigateMarsController.class)
public class NavigateMarsControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private NavigateMarsService navigateMarsService;


    @Test
    public void should_return_status_ok_and_valid_robot_position() throws Exception {
        BDDMockito.given(this.navigateMarsService.calculateRobotPosition("MML"))
        .willReturn(new Robot(0, 2, OrientationEnum.WEST));


        this.mvc.perform(MockMvcRequestBuilders.get("/rest/mars/MML"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.position_x").value(0))
                .andExpect(jsonPath("$.position_y").value(2))
                .andExpect(jsonPath("$.orientation").value("WEST"));
    }

    @Test
    public void should_return_status_ok_and_valid_robot_position2() throws Exception {
        BDDMockito.given(this.navigateMarsService.calculateRobotPosition("MMRMMRMM"))
                .willReturn(new Robot(2, 0, OrientationEnum.SOUTH));


        this.mvc.perform(MockMvcRequestBuilders.get("/rest/mars/MMRMMRMM"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.position_x").value(2))
                .andExpect(jsonPath("$.position_y").value(0))
                .andExpect(jsonPath("$.orientation").value("SOUTH"));
    }

    @Test
    public void should_return_status_bad_request_because_wrong_instructions_was_passed() throws Exception {
        BDDMockito.given(this.navigateMarsService.calculateRobotPosition("AAA"))
                .willThrow(InvalidInstructionException.class);

        this.mvc.perform(MockMvcRequestBuilders.get("/rest/mars/AAA"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_return_status_bad_request_because_boundaries_of_the_area_was_exceed() throws Exception {
        BDDMockito.given(this.navigateMarsService.calculateRobotPosition("MMMMMM"))
                .willThrow(InvalidMovementException.class);

        this.mvc.perform(MockMvcRequestBuilders.get("/rest/mars/MMMMMM"))
                .andExpect(status().isBadRequest());
    }
}
