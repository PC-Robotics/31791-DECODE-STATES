package org.firstinspires.ftc.teamcode.SubSystems;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import java.util.List;
import java.util.Locale;

public class FlyWheel{
    public enum FLYWHEEL_STATE{
        IDLE,
        Spinning
    }

    public enum FLYWHEEL_SPEEDS{
        CLOSE(0.5),
        FAR(1);

        double power;
        void FLYWHEEL_SPIN_POSITION(double power) {
            this.power = power;
        }
    }

    private LinearOpMode opMode;

    public DcMotorEx flywheel;

    public FLYWHEEL_STATE flywheelState = FLYWHEEL_STATE.IDLE;

    public FLYWHEEL_SPEEDS spinSpeed = FLYWHEEL_SPEEDS.FAR;


    public FlyWheel(LinearOpMode opMode) {
        this.opMode = opmode;
    }

    @Override
    public void init(){
        flywheel = motorInit(opMode.hardwareMap, "flywheel", DcMotorSimple.Direction.FORWARD);

        flywheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }

    public void setPosition(FLYWHEEL_SPEEDS speed){
        if(speed != spinSpeed){
            spinSpeed = speed;

            if(flywheelState == FLYWHEEL_STATE.Spinning) {
                spinToSpeed();
            }
        }
    }

    public void spinToSpeed(double power) {
        flywheelLeft.setPower(power);
        flywheelRight.setPower(power);
        flywheelState = FLYWHEEL_STATE.SPINNING;
    }

    public void spinToSpeed() {
        spinToSpeed(spinPosition.power);
    }


    @Override
    public void stop() {
        flywheelLeft.setPower(0);
        flywheelRight.setPower(0);
        flywheelState = FLYWHEEL_STATE.IDLE;
    }


    @Override
    public List<String> getTelemetry() {
        double avgPower = (flywheelLeft.getPower() + flywheelRight.getPower()) / 2.0;

        return List.of(
                "Flywheel State: " + flywheelState.toString(),
                "Flywheel Position: " + spinPosition.toString(),
                "Flywheel Power: " + String.format(Locale.ROOT, "%.2f", avgPower)
        );
    }

}
