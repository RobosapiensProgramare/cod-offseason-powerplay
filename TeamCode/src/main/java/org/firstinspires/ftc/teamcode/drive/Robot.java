package org.firstinspires.ftc.teamcode.drive;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.Outtake;

public class Robot {
    private boolean initialize;
    public SampleMecanumDrive drive;
    public Outtake outtake;


    public Robot(HardwareMap hardwareMap){
        outtake = new Outtake(hardwareMap);
        initialize = true;
        drive = new SampleMecanumDrive(hardwareMap);
        initialize = false;
    }
    public boolean isInitialize() {return initialize;}
}