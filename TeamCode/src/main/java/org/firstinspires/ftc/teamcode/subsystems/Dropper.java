package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.Servo;

public class Dropper {

    private Servo servo;


    private static final double DROP_POSITION = 0;
    private static final double HOLD_POSITION = 0.24;
    private static final double SHOOT_POSITION = 0.37;

    private boolean isDropped = false;

    public Dropper(Servo servo) {
        this.servo = servo;
        hold();
    }

    public void drop() {
        servo.setPosition(DROP_POSITION);

    }

    public void shoot(){
        servo.setPosition(SHOOT_POSITION);

    }

    public void hold() {
        servo.setPosition(HOLD_POSITION);

    }




}
