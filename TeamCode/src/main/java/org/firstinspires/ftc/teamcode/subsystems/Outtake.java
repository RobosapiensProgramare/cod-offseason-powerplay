package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Outtake {
    private static final double POWER = 1;
    public DcMotor motorGlisiera;
    public DcMotor motorGlisiera2;
    public Servo cleste;

    public boolean mergeMotor;
    public double manualTarget = 0;

    public Outtake(HardwareMap hardwareMap){
        motorGlisiera = hardwareMap.dcMotor.get("motorGlisiera");
        cleste = hardwareMap.servo.get("servoCleste");
        motorGlisiera2 = hardwareMap.dcMotor.get("motorGlisiera2");

        //Motor initialization
        motorGlisiera.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorGlisiera.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorGlisiera.setDirection(DcMotorSimple.Direction.REVERSE);

        motorGlisiera2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorGlisiera2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorGlisiera2.setDirection(DcMotorSimple.Direction.REVERSE);

        cleste.setDirection(Servo.Direction.REVERSE);
    }

    public void desfaCleste(){
        cleste.setPosition(0);
    }

    public void strangeCleste(){
        cleste.setPosition(0.4);
    }

    public void setPower(double power){
        motorGlisiera.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorGlisiera.setPower(power);
        motorGlisiera2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorGlisiera2.setPower(power);
    }

    public void manualLevel(double manualTarget) {
        motorGlisiera.setTargetPosition((int) manualTarget);
        motorGlisiera.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorGlisiera2.setTargetPosition((int) manualTarget);
        motorGlisiera2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        if(motorGlisiera.getCurrentPosition() > manualTarget && motorGlisiera2.getCurrentPosition() > manualTarget)
        {
            motorGlisiera.setPower(POWER);
            motorGlisiera2.setPower(POWER);
        }
        else{
            motorGlisiera.setPower(-POWER);
            motorGlisiera2.setPower(-POWER);
        }
    }

    public void setLevel(int target, double multiplier){
        motorGlisiera.setTargetPosition(-target);
        motorGlisiera.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorGlisiera2.setTargetPosition(-target);
        motorGlisiera2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        if(motorGlisiera.getCurrentPosition() > -target && motorGlisiera2.getCurrentPosition() > -target) {
            motorGlisiera.setPower(-POWER);
            motorGlisiera2.setPower(-POWER);
        }
        else {
            motorGlisiera.setPower(POWER * multiplier);
            motorGlisiera2.setPower(POWER * multiplier);
        }
    }
    public void setLevel(int target){
        motorGlisiera.setTargetPosition(-target);
        motorGlisiera.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorGlisiera2.setTargetPosition(-target);
        motorGlisiera2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        if(motorGlisiera.getCurrentPosition() > -target && motorGlisiera2.getCurrentPosition() > -target) {
            motorGlisiera.setPower(-POWER);
            motorGlisiera2.setPower(-POWER);
        }
        else{
            motorGlisiera.setPower(POWER);
            motorGlisiera2.setPower(POWER);
        }
    }

    public boolean isGoing(int target){
        if (motorGlisiera.getCurrentPosition() < target + 20 && motorGlisiera.getCurrentPosition() > target - 20){
            return  false;
        }
        return true;

    }


}


