package ca.com.br.robotchallenge.model;

import ca.com.br.robotchallenge.utils.OrientationEnum;

public class Robot {

    private Integer position_x;
    private Integer position_y;
    private OrientationEnum orientation;

    public Robot(Integer position_x, Integer position_y, OrientationEnum orientation) {
        this.position_x = position_x;
        this.position_y = position_y;
        this.orientation = orientation;
    }

    public Integer getPosition_x() {
        return position_x;
    }

    public void setPosition_x(Integer position_x) {
        this.position_x = position_x;
    }

    public Integer getPosition_y() {
        return position_y;
    }

    public void setPosition_y(Integer position_y) {
        this.position_y = position_y;
    }

    public OrientationEnum getOrientation() {
        return orientation;
    }

    public void setOrientation(OrientationEnum orientation) {
        this.orientation = orientation;
    }
}
