package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;

public class Intake {

    private DcMotorEx motor;

    public Intake(DcMotorEx motor) {
        this.motor = motor;
    }

    public void intake() {
        motor.setPower(1.0);
    }

    public void reverse() {
        motor.setPower(-1.0);
    }

    public void stop() {
        motor.setPower(0);
    }
}
