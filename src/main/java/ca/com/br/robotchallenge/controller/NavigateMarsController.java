package ca.com.br.robotchallenge.controller;

import ca.com.br.robotchallenge.exception.InvalidInstructionException;
import ca.com.br.robotchallenge.exception.InvalidMovementException;
import ca.com.br.robotchallenge.model.Robot;
import ca.com.br.robotchallenge.service.NavigateMarsService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/mars")
public class NavigateMarsController {

    private NavigateMarsService navigateMarsService;

    public NavigateMarsController(NavigateMarsService navigateMarsService) {
        this.navigateMarsService = navigateMarsService;
    }

    @RequestMapping(value = "/{instructions}", method = RequestMethod.GET)
    public ResponseEntity navigate(@PathVariable String instructions) {

        try {
          Robot robot = navigateMarsService.calculateRobotPosition(instructions);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

          return new ResponseEntity<>(robot, headers, HttpStatus.OK);

        } catch (InvalidInstructionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (InvalidMovementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
