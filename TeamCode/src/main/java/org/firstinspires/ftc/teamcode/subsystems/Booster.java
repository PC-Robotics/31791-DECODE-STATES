package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class Booster {

    private CRServo leftServo;
    private CRServo rightServo;

    // Tune these
    private static final double BOOST_POWER = 1.0;
    private static final double REVERSE_POWER = -1.0;
    private static final double STOP_POWER = 0.0;

    public Booster(CRServo leftServo, CRServo rightServo) {

        this.leftServo = leftServo;
        this.rightServo = rightServo;

        // Reverse one if needed so they spin same direction physically
        rightServo.setDirection(CRServo.Direction.REVERSE);
        leftServo.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    public void run() {
        leftServo.setPower(BOOST_POWER);
        rightServo.setPower(BOOST_POWER);
    }

    public void reverse() {
        leftServo.setPower(REVERSE_POWER);
        rightServo.setPower(REVERSE_POWER);
    }

    public void stop() {
        leftServo.setPower(STOP_POWER);
        rightServo.setPower(STOP_POWER);
    }

    public void runAt(double power) {
        leftServo.setPower(power);
        rightServo.setPower(power);
    }
}
