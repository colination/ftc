package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

/**
 * Created by Navya Janga on 10/21/2017.
 */

@Autonomous
//@Disabled

public class AutoValuesTesting extends AutoMethods {


    public void runOpMode() {

        initVariables();
        // Testing for distance

        while (opModeIsActive()) {

            // testing for distance and power values
            motorEncoder(0.5, -8, 8, 8, -8);

            // testing for jewelCode : do this after we find the correct distance values
            // hitJewel;

        }

    }


    public void motorEncoder(double speed, double inchesFR, double inchesFL, double inchesBR, double inchesBL){

        int newFLTarget;
        int newFRTarget;
        int newBLTarget;
        int newBRTarget;

        newFLTarget = motorFL.getCurrentPosition() + (int)(inchesFL * COUNTS_PER_INCH);
        newFRTarget = motorFR.getCurrentPosition() + (int)(inchesFR * COUNTS_PER_INCH);
        newBLTarget = motorBL.getCurrentPosition() + (int)(inchesBL * COUNTS_PER_INCH);
        newBRTarget = motorFR.getCurrentPosition() + (int)(inchesBR * COUNTS_PER_INCH);
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

    public void hitJewel() {
        jewelHit.setPosition(0.5); // value of servo to be open

        // move robot sideways until it senses the jewel
        while (jewelColor.red() > 10 && jewelColor.red() < 25) {

            motorPower(0.5); // test how fast we can go with accuracy
        }

        motorStop();

        // color sense one jewel and knock off the opposite color
        if (jewelColor.red() < 10) // blue value
        {
            motorEncoder(0.5, 20, 20, 20, 20); // find the correct distance we need to move for this
        }

        else {
            motorEncoder(0.5, -20, -20, -20, -20); // find the correct distance we need to move for this
            // create a boolean for if the robot moves backwards because we need to add additional distance when it goes to the glyphCode
        }

        // put arm back in
        jewelHit.setPosition(0);
    }

    public void motorStop() {
        motorFR.setPower(0);
        motorBR.setPower(0);
        motorFL.setPower(0);
        motorBL.setPower(0);
    }

    public void motorPower(double power) {
        motorFR.setPower(power);
        motorBR.setPower(power);
        motorFL.setPower(power);
        motorBL.setPower(power);
    }

}
