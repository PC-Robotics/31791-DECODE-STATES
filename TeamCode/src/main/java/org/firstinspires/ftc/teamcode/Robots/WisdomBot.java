package org.firstinspires.ftc.teamcode.Robots;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

public class WisdomBot extends DriveBasePID{
    private Servo rgbIndicator;

    public WisdomBot(LinearOpMode opMode, boolean isFC) {
        super(opMode, isFC);
    }

    public void init() {
        super.init();

        AprilTagProcessor aprilTag = AprilTagProcessor.easyCreateWithDefaults();
        WebcamName camera = myOpMode.hardwareMap.get(WebcamName.class, "Webcam 1");
        VisionPortal visionPortal = VisionPortal.easyCreateWithDefaults(camera, aprilTag);

        // Initialize RGB Indicator as Servo
        try {
            rgbIndicator = myOpMode.hardwareMap.get(Servo.class, "rgb_indicator");
            myOpMode.telemetry.addData("RGB Indicator", "Found");
        } catch (Exception e) {
            myOpMode. telemetry.addData("RGB Indicator", "NOT FOUND");
            rgbIndicator = null;
        }

        FtcDashboard.getInstance().startCameraStream(visionPortal, 60);
    }


}
