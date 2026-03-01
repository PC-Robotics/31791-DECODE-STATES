package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.Servo;

public class Dropper {

    private Servo servo;


    private static final double DROP_POSITION = 0.75;
    private static final double HOLD_POSITION = 0.2;
    private static final double SHOOT_POSITION = 0.1;

    private boolean isDropped = false;

    public Dropper(Servo servo) {
        this.servo = servo;
        hold();
    }

    public void drop() {
        servo.setPosition(DROP_POSITION);
        isDropped = true;
    }

    public void shoot(){
        servo.setPosition(SHOOT_POSITION);
        isDropped = false;
    }

    public void hold() {
        servo.setPosition(HOLD_POSITION);
        isDropped = false;
    }

    public void toggle() {
        if (isDropped) {
            hold();
        } else {
            drop();
        }
    }

    public boolean isDropped() {
        return isDropped;
    }
}
