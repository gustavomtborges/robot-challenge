package ca.com.br.robotchallenge.service;

import ca.com.br.robotchallenge.exception.InvalidInstructionException;
import ca.com.br.robotchallenge.exception.InvalidMovementException;
import ca.com.br.robotchallenge.model.Robot;
import ca.com.br.robotchallenge.utils.AreaConstant;
import ca.com.br.robotchallenge.utils.OrientationEnum;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class NavigateMarsService {

    public Robot calculateRobotPosition(String instructions) throws InvalidInstructionException, InvalidMovementException {
        if (validateInstructions(instructions)) {

            // Robot initial values
            Integer initialPosition_x = 0;
            Integer initialPosition_y = 0;
            OrientationEnum initialOrientation = OrientationEnum.NORTH;

            // Convert instructions to list to perform read.
            List<String> instructionsList = new ArrayList<>(Arrays.asList(instructions.split("")));

            Map<String, Object> robotPositions = readInstructions(initialPosition_x, initialPosition_y, initialOrientation, instructionsList);

            Boolean hasErrorMovement = (Boolean) robotPositions.get("errorMovement");

            if (hasErrorMovement) {
                throw new InvalidMovementException("Boundaries of the area was exceed. Limit is 5x5");
            }

            Integer robotPosition_x = (Integer) robotPositions.get("position_x");
            Integer robotPosition_y = (Integer) robotPositions.get("position_y");
            OrientationEnum robotOrientation = (OrientationEnum) robotPositions.get("orientation");

            return new Robot(robotPosition_x, robotPosition_y, robotOrientation);
        } else {
            throw new InvalidInstructionException("You must pass a valid instruction. M(move), L(rotate left) or R(rotate right)");
        }
    }

    private Boolean validateInstructions(String instructions) {
        return instructions.matches("[MLR]*");
    }

    private Map<String, Object> readInstructions(Integer initialPosition_x,
                                                 Integer initialPosition_y,
                                                 OrientationEnum initialOrientation,
                                                 List<String> instructionsList)  {

        Map<String, Object> robotPositions = new HashMap<>();
        final Integer[] position_x = {initialPosition_x};
        final Integer[] position_y = {initialPosition_y};
        final OrientationEnum[] orientation = {initialOrientation};

        instructionsList.forEach(value -> {
            if(value.equals("M")) {
                switch (orientation[0]) {
                    case NORTH: {
                        position_y[0] = position_y[0] + 1;
                    }
                    break;
                    case EAST: {
                        position_x[0] = position_x[0] + 1;
                    }
                    break;
                    case WEST: {
                        position_x[0] = position_x[0] - 1;
                    }
                    break;
                    case SOUTH: {
                        position_y[0] = position_y[0] - 1;
                    }
                }
            } else {
                orientation[0] = getOrientationMovement(orientation[0], value);
            }

            robotPositions.put("position_y", position_y[0]);
            robotPositions.put("position_x", position_x[0]);
            robotPositions.put("orientation", orientation[0]);
        });

        if(position_x[0] > AreaConstant.MAX_X || position_x[0] < AreaConstant.MIN_X || position_y[0] > AreaConstant.MAX_Y || position_y[0] < AreaConstant.MIN_Y) {
            robotPositions.put("errorMovement", true);
        } else {
            robotPositions.put("errorMovement", false);
        }

        return robotPositions;
    }

    private OrientationEnum getOrientationMovement(OrientationEnum orientation, String movement) {
        OrientationEnum newOrientation = orientation;

        if (movement.equals("L")) {
            switch (orientation) {
                case NORTH: newOrientation = OrientationEnum.WEST;
                    break;
                case WEST: newOrientation = OrientationEnum.SOUTH;
                    break;
                case SOUTH: newOrientation = OrientationEnum.EAST;
                    break;
                case EAST: newOrientation = OrientationEnum.NORTH;
            }

        } else {
            switch (orientation) {
                case NORTH: newOrientation = OrientationEnum.EAST;
                    break;
                case EAST: newOrientation = OrientationEnum.SOUTH;
                    break;
                case SOUTH: newOrientation = OrientationEnum.WEST;
                    break;
                case WEST: newOrientation = OrientationEnum.NORTH;
            }
        }


        return newOrientation;
    }
}
