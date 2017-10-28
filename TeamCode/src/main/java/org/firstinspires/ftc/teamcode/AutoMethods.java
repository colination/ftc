package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Navya Janga on 10/23/2017.
 */

public abstract class AutoMethods extends AutoInit {

    public void motorEncoder(double speed, double inchesFR, double inchesFL, double inchesBR, double inchesBL) {

        int newFLTarget;
        int newFRTarget;
        int newBLTarget;
        int newBRTarget;

        newFLTarget = motorFL.getCurrentPosition() + (int) (inchesFL * COUNTS_PER_INCH);
        newFRTarget = motorFR.getCurrentPosition() + (int) (inchesFR * COUNTS_PER_INCH);
        newBLTarget = motorBL.getCurrentPosition() + (int) (inchesBL * COUNTS_PER_INCH);
        newBRTarget = motorFR.getCurrentPosition() + (int) (inchesBR * COUNTS_PER_INCH);
        motorFL.setTargetPosition(newFLTarget);
        motorFR.setTargetPosition(newFRTarget);
        motorBL.setTargetPosition(newBLTarget);
        motorBR.setTargetPosition(newBRTarget);

        // Turn On RUN_TO_POSITION
        motorFL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorFR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorBL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorBR.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        motorFL.setPower(Math.abs(speed));
        motorFR.setPower(Math.abs(speed));
        motorBL.setPower(Math.abs(speed));
        motorBR.setPower(Math.abs(speed));


    }

    public void motorPower(double speed) {
        motorFR.setPower(speed);
        motorFL.setPower(speed);
        motorBR.setPower(speed);
        motorBL.setPower(speed);
    }

    public void motorStrafeRight(double speed) {
        motorFR.setPower(speed);
        motorFL.setPower(-speed);
        motorBR.setPower(-speed);
        motorBL.setPower(speed);
    }

    public void motorStrafeLeft(double speed) {
        motorFR.setPower(-speed);
        motorFL.setPower(speed);
        motorBR.setPower(speed);
        motorBL.setPower(-speed);
    }

    public void motorStop() {
        motorFR.setPower(0);
        motorBR.setPower(0);
        motorFL.setPower(0);
        motorBL.setPower(0);
    }

    public void manipMove(double speed) {
        manipulator.setPower(speed);
    }

}
