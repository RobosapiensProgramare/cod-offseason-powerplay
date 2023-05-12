package org.firstinspires.ftc.teamcode.drive.opmode;

import static java.lang.Math.abs;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.drive.Robot;
//import org.firstinspires.ftc.teamcode.drive.subsystems.Odometrie;

@TeleOp(group = "driver")
public class LinearDriveMode extends LinearOpMode {
    private Robot robot = null;
    public final static int ZERO = 0, GROUND = 100, LOW = 900, MEDIUM = 1550, TALL = 2300;
    public final static double DOWN_MULTIPLIER = 0.7;

    public double calculateThrottle(float x) {
        int sign = -1;
        if (x > 0) sign = 1;
        return sign * Math.pow(100 * (abs(x) / 100), 2);
    }


    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData(">", "Initializing...");
        telemetry.update();

        robot = new Robot(hardwareMap);
        robot.drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        gamepad1.setLedColor(200, 0, 200, Gamepad.LED_DURATION_CONTINUOUS);
        gamepad2.setLedColor(255, 125, 0, Gamepad.LED_DURATION_CONTINUOUS);


        while (robot.isInitialize() && opModeIsActive()) {
            idle();
        }

        telemetry.addData(">", "Initialized");
        telemetry.update();
        waitForStart();
        if (isStopRequested()) return;

        while (opModeIsActive()) {

            if (gamepad1.right_bumper) {
                robot.outtake.strangeCleste();
                gamepad1.rumble(500);
            }
            if (gamepad1.left_bumper) robot.outtake.desfaCleste();

            //Aimbot
            if (gamepad1.touchpad) robot.outtake.setLevel(ZERO, DOWN_MULTIPLIER);
            if (gamepad1.cross) robot.outtake.setLevel(GROUND, DOWN_MULTIPLIER);
            if (gamepad1.circle) robot.outtake.setLevel(LOW, DOWN_MULTIPLIER);
            if (gamepad1.triangle) robot.outtake.setLevel(MEDIUM, DOWN_MULTIPLIER);
            if (gamepad1.square) robot.outtake.setLevel(TALL, DOWN_MULTIPLIER);

            if (gamepad1.left_trigger > 0.1) {
                robot.outtake.manualTarget = robot.outtake.motorGlisiera.getCurrentPosition() + calculateThrottle(gamepad1.left_trigger * 12);
                robot.outtake.manualTarget--;
                robot.outtake.manualLevel(robot.outtake.manualTarget);
            }
            if (gamepad1.right_trigger > 0.1) {
                robot.outtake.manualTarget = robot.outtake.motorGlisiera.getCurrentPosition() - calculateThrottle(gamepad1.right_trigger * 12);
                robot.outtake.manualTarget++;
                robot.outtake.manualLevel(robot.outtake.manualTarget);
            }





            //Drive
            robot.drive.setDrivePower(new Pose2d(calculateThrottle((-gamepad1.left_stick_y)), calculateThrottle((float) (-gamepad1.left_stick_x)), calculateThrottle((float) (-gamepad1.right_stick_x))));

            telemetry.update();
        }
    }
}
