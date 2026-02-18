package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DigitalChannel;

import java.util.LinkedList;
import java.util.Queue;

public class ArtifactQueueManager {

    public enum ArtifactType {
        GREEN,
        PURPLE,
        UNKNOWN
    }

    private ColorSensor chamberSensor;

    private DigitalChannel ledRed;
    private DigitalChannel ledGreen;
    private DigitalChannel ledBlue;

    private Queue<ArtifactType> artifactQueue = new LinkedList<>();
    private boolean lastDetected = false;

    public ArtifactQueueManager(ColorSensor chamberSensor,
                                DigitalChannel ledRed,
                                DigitalChannel ledGreen,
                                DigitalChannel ledBlue) {

        this.chamberSensor = chamberSensor;
        this.ledRed = ledRed;
        this.ledGreen = ledGreen;
        this.ledBlue = ledBlue;

        ledRed.setMode(DigitalChannel.Mode.OUTPUT);
        ledGreen.setMode(DigitalChannel.Mode.OUTPUT);
        ledBlue.setMode(DigitalChannel.Mode.OUTPUT);
    }

    public void update() {
        boolean detected = detectArtifact();

        // Rising edge detection (add once)
        if (detected && !lastDetected) {
            ArtifactType type = classify();
            artifactQueue.add(type);
        }

        lastDetected = detected;
        updateLED();
    }

    private boolean detectArtifact() {
        int total = chamberSensor.red() + chamberSensor.green() + chamberSensor.blue();
        return total > 220; // tune this for sensitivity
    }

    private ArtifactType classify() {
        int r = chamberSensor.red();
        int g = chamberSensor.green();
        int b = chamberSensor.blue();

        // Green if green is dominant
        if (g > r && g > b) {
            return ArtifactType.GREEN;
        }
        // Purple if red and blue are both strong relative to green
        else if (r > g && b > g) {
            return ArtifactType.PURPLE;
        } else {
            return ArtifactType.UNKNOWN;
        }
    }

    private void updateLED() {
        if (artifactQueue.isEmpty()) {
            setLED(false, false, false);
            return;
        }

        ArtifactType first = artifactQueue.peek();

        switch (first) {
            case GREEN:
                setLED(false, true, false);  // green LED
                break;
            case PURPLE:
                setLED(true, false, true);   // red + blue = purple
                break;
            case UNKNOWN:
                setLED(true, true, false);   // yellow for unknown
                break;
        }
    }

    private void setLED(boolean r, boolean g, boolean b) {
        // invert if your hardware uses common-anode LEDs
        ledRed.setState(!r);
        ledGreen.setState(!g);
        ledBlue.setState(!b);
    }

    public ArtifactType peek() {
        return artifactQueue.peek();
    }

    public void removeFirst() {
        if (!artifactQueue.isEmpty()) {
            artifactQueue.poll();
        }
    }

    public int size() {
        return artifactQueue.size();
    }
}
