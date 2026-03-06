package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

@TeleOp(name = "FlyWheelPIDF", group = "Test")

public class FlyWheelPIDF extends OpMode {
    public DcMotorEx flywheel1;
    public DcMotorEx flywheel2;
    double highVelocity = 1800;
    double lowVelocity = 1540;
    double currTargetVelocity;
    double f = 0;
    double p = 150;

    double[] stepSizes = {10.0, 1.0, 0.1, 0.001, 0.0001};

    int stepIndex = 1;


    @Override
    public void init(){
        flywheel1 = hardwareMap.get(DcMotorEx.class, "flywheel1");
        flywheel2 = hardwareMap.get(DcMotorEx.class, "flywheel2");
        flywheel1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        flywheel2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        flywheel1.setDirection(DcMotorSimple.Direction.REVERSE);
        flywheel2.setDirection(DcMotorSimple.Direction.REVERSE);
        PIDFCoefficients pidfCoefficients = new PIDFCoefficients(p,0,0,f);
        flywheel1.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfCoefficients);
        flywheel2.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfCoefficients);
        telemetry.addLine("Init Complete");

    }

    @Override
    public void loop(){
        if(gamepad1.triangleWasPressed()){
            if(currTargetVelocity == highVelocity){
                currTargetVelocity = lowVelocity;
            }
            else{
                currTargetVelocity = highVelocity;
            }
        }

        if(gamepad1.crossWasPressed()){
            stepIndex = (stepIndex+1) % stepSizes.length;
        }

        if(gamepad1.dpadLeftWasPressed()){
            f -= stepSizes[stepIndex];
        }
        if(gamepad1.dpadRightWasPressed()){
            f += stepSizes[stepIndex];
        }
        if(gamepad1.dpadUpWasPressed()){
            p -= stepSizes[stepIndex];
        }
        if(gamepad1.dpadDownWasPressed()){
            p += stepSizes[stepIndex];
        }

        PIDFCoefficients pidfCoefficients = new PIDFCoefficients(p,0,0,f);
        flywheel1.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfCoefficients);
        flywheel2.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfCoefficients);

        flywheel1.setVelocity(currTargetVelocity);
        flywheel2.setVelocity(currTargetVelocity);

        double currVelocity1 = flywheel1.getVelocity();

        double error = currTargetVelocity - currVelocity1;

        telemetry.addData("Target Velocity  ::  ", currTargetVelocity);
        telemetry.addData("Current Velocity  ::  ", "%.2f", currVelocity1);
        telemetry.addData("Error  ::  ", "%.2f" , error);
        telemetry.addLine("=============================================");
        telemetry.addData("Tuning P  ::  ", "%.4f (D-pad U/D)", p);
        telemetry.addData("Tuning F  ::  ", "%.4f (D-pad L/R)", f);
        telemetry.addData("Step Size  ::  ", "%.4f (Cross Button)", stepSizes[stepIndex]);


    }
}
