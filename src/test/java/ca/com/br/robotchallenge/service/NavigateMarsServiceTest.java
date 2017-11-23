package ca.com.br.robotchallenge.service;

import ca.com.br.robotchallenge.exception.InvalidInstructionException;
import ca.com.br.robotchallenge.exception.InvalidMovementException;
import ca.com.br.robotchallenge.model.Robot;
import ca.com.br.robotchallenge.utils.OrientationEnum;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NavigateMarsServiceTest {

    @Autowired
    private NavigateMarsService navigateMarsService;


    @Test
    public void should_return_valid_robot_positon() {
        Robot robot = navigateMarsService.calculateRobotPosition("MML");

        Assertions.assertThat(robot.getPosition_x()).isEqualTo(0);
        Assertions.assertThat(robot.getPosition_y()).isEqualTo(2);
        Assertions.assertThat(robot.getOrientation()).isEqualTo(OrientationEnum.WEST);
    }

    @Test
    public void should_return_valid_robot_positon2() {
        Robot robot = navigateMarsService.calculateRobotPosition("MMRMMRMM");

        Assertions.assertThat(robot.getPosition_x()).isEqualTo(2);
        Assertions.assertThat(robot.getPosition_y()).isEqualTo(0);
        Assertions.assertThat(robot.getOrientation()).isEqualTo(OrientationEnum.SOUTH);
    }

    @Test
    public void should_throw_invalid_instruction_exception() {
        Assertions.assertThatThrownBy(() -> navigateMarsService.calculateRobotPosition("AAA"))
                .isInstanceOf(InvalidInstructionException.class)
                .withFailMessage("You must pass a valid instruction. M(move), L(rotate left) or R(rotate right)");
    }

    @Test
    public void should_throw_invalid_movement_exception() {
        Assertions.assertThatThrownBy(() -> navigateMarsService.calculateRobotPosition("MMMMMM"))
                .isInstanceOf(InvalidMovementException.class)
                .withFailMessage("Boundaries of the area was exceed. Limit is 5x5");
    }
}
