package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;

public class Transfer {

    private DcMotorEx motor;

    public Transfer(DcMotorEx motor) {
        this.motor = motor;
    }

    public void run() {
        motor.setPower(1.0);
    }

    public void run(double power) {
        motor.setPower(power);
    }

    public void reverse() {
        motor.setPower(-1.0);
    }

    public void stop() {
        motor.setPower(0);
    }
}
