/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * This OpMode scans a single servo back and forwards until Stop is pressed.
 * The code is structured as a LinearOpMode
 * INCREMENT sets how much to increase/decrease the servo position each cycle
 * CYCLE_MS sets the update period.
 *
 * This code assumes a Servo configured with the name "left claw" as is found on a pushbot.
 *
 * NOTE: When any servo position is set, ALL attached servos are activated, so ensure that any other
 * connected servos are able to move freely before running this test.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */
@Autonomous(name = "AutoMTest", group = "Concept")
//@Disabled
public class servoMTest extends LinearOpMode {

    //static final double INCREMENT   = 0.01;     // amount to slew servo each CYCLE_MS cycle
    static final int    CYCLE_MS    =   5000;     // period of each cycle
    static final double MAX_POS     =  1.0;     // Maximum rotational position
    static final double MIN_POS     =  0.0;     // Minimum rotational position
    static final double     COUNTS_PER_MOTOR_REV    = 2240 ;     //REV 41 1301 Encoders
    static final double     DRIVE_GEAR_REDUCTION    = 2.0 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 5.0 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.1415);

    // Define class members
    //double  position = (MAX_POS - MIN_POS) / 2; // Start at halfway position
    //boolean rampUp = true;
    DcMotor motorFR;
    DcMotor motorFL;
    DcMotor motorBR;
    DcMotor motorBL;

    DcMotor collectLeft;
    DcMotor collectRight;

    DcMotor manipulator;

    ColorSensor sensorColor;
    DcMotor lift;

    Servo jewelHit;
    Servo manipServo;


    @Override
    public void runOpMode() {

        // Connect to servo (Assume PushBot Left Hand)
        // Change the text in quotes to match any servo name on your robot.
        jewelHit = hardwareMap.get(Servo.class, "jewelHit");
        manipServo = hardwareMap.get(Servo.class, "manipServo");
        motorFL = hardwareMap.get(DcMotor.class, "motorFL");
        motorBL = hardwareMap.get(DcMotor.class, "motorBL");
        motorFR = hardwareMap.get(DcMotor.class, "motorFR");
        motorBR = hardwareMap.get(DcMotor.class, "motorBR");
        sensorColor = hardwareMap.get(ColorSensor.class, "jewelColor");
        manipulator = hardwareMap.dcMotor.get("manipulator");

        motorFL.setDirection(DcMotor.Direction.REVERSE);
        motorBL.setDirection(DcMotor.Direction.REVERSE);


        motorFR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);



        motorFR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorFL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorBR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorBL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);



        collectLeft = hardwareMap.dcMotor.get("collectLeft");
        collectRight = hardwareMap.dcMotor.get("collectRight");

        lift = hardwareMap.dcMotor.get("lift");

        // Wait for the start button
        // Wait for the start button
        telemetry.addLine().addData(">", "Press Start to scan Servo." );
        telemetry.update();
        waitForStart();


        // Scan servo till stop pressed.
        while(opModeIsActive()){//1cm = 17 ticks forward

            // Display the current value
            telemetry.addLine().addData("Servo Position", "%5.2f", jewelHit.getPosition());
            telemetry.addLine().addData(">", "Press Stop to end test." );
            telemetry.update();

            // Set the servo to the new position and pause;
            coolEncoderForward(.3, 100);
            sleep(1000);
            jewelHit.setPosition(0);
            sleep(1000);
            idle();
            jewelHit.setPosition(.84);
            sleep(CYCLE_MS);
            idle();

            if(sensorColor.red() > 30) {
                coolEncoderForward(-.5, 200);
                sleep(300);
                jewelHit.setPosition(0);
                sleep(1000);
                coolEncoderForward(.5, 700);
            }
            else {
                coolEncoderForward(.5, 200);
                jewelHit.setPosition(0);
                sleep(1000);
            }
            sleep(1000);
            coolEncoderForward(.5, 500);
            idle();

            turnLeft();
            coolEncoderForward(.5, 100);

            sleep(1000);
            sleep(1700);
            idle();
            sleep(20000);
        }
        telemetry.addLine().addData(">", "Done");
        telemetry.update();



        // Signal done;

    }

    public void encoderReset() {
        motorFL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        motorFR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorFL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorBR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorBL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void coolEncoderForward(double speed, int distance) {
        encoderReset();
        while ((Math.abs(motorFR.getCurrentPosition()) < distance) && (opModeIsActive())) {//encoders may be moving forward
            motorFL.setPower(speed);
            motorFR.setPower(speed);
            motorBL.setPower(speed);
            motorBR.setPower(speed);
            telemetry.addData("Encoder", (Math.abs(motorFR.getCurrentPosition())));
            telemetry.update();
        }
        motorFL.setPower(0);
        motorFR.setPower(0);
        motorBL.setPower(0);
        motorBR.setPower(0);
        /*motorFL.setTargetPosition(newFLTarget);
        motorFR.setTargetPosition(newFRTarget);
        motorBL.setTargetPosition(newBLTarget);
        motorBR.setTargetPosition(newBRTarget);

        // Turn On RUN_TO_POSITION
        motorFL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorFR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorBL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorBR.setMode(DcMotor.RunMode.RUN_TO_POSITION);*/
         /* other stuff int newFRTarget;
        int newBLTarget;
        int newBRTarget;
        int displacementFL;
        int displacementFR;current pos-starting pos
        int displacementBL;
        int displacementBR;*/
//every motor set to stop and reset
        /*int startingPositionFL = motorFL.getCurrentPosition();
        telemetry.addLine().addData("", startingPositionFL);
        telemetry.update();*/
        /*newFRTarget = motorFR.getCurrentPosition() + (int) (inchesFR * COUNTS_PER_INCH);
        newBLTarget = motorBL.getCurrentPosition() + (int) (inchesBL * COUNTS_PER_INCH);
        newBRTarget = motorFR.getCurrentPosition() + (int) (inchesBR * COUNTS_PER_INCH);

        displacementFL = newFLTarget - motorFL.getCurrentPosition();
        displacementFR = newFLTarget - motorFR.getCurrentPosition();
        displacementBL = newFLTarget - motorBL.getCurrentPosition();
        displacementBR = newFLTarget - motorBR.getCurrentPosition();*/
    }//RED SIDE AUTO IS UP TO HERE!!!

    public void leftEncoder(double speed, int distance){
        encoderReset();
        while ((Math.abs(motorFR.getCurrentPosition()) < distance) && (opModeIsActive())) {//encoders may be moving forward
            motorFL.setPower(speed);
            motorFR.setPower(-speed);
            motorBL.setPower(-speed);
            motorBR.setPower(speed);
            telemetry.addData("Encoder", (Math.abs(motorFR.getCurrentPosition())));
            telemetry.update();
        }
        motorFL.setPower(0);
        motorFR.setPower(0);
        motorBL.setPower(0);
        motorBR.setPower(0);
    }

    public void rightEncoder(double speed, int distance) {
        encoderReset();
        while ((Math.abs(motorFR.getCurrentPosition()) < distance) && (opModeIsActive())) {
            motorFL.setPower(-speed);
            motorFR.setPower(speed);
            motorBL.setPower(speed);
            motorBR.setPower(-speed);
            telemetry.addData("Encoder", (Math.abs(motorFR.getCurrentPosition())));
            telemetry.update();
        }
        motorFL.setPower(0);
        motorFR.setPower(0);
        motorBL.setPower(0);
        motorBR.setPower(0);
    }

    public void turnAround() {
        encoderReset();
        while ((Math.abs(motorFR.getCurrentPosition()) < 2700) && (opModeIsActive())) {//clockwise
            motorFL.setPower(.5);
            motorFR.setPower(-.5);
            motorBL.setPower(.5);
            motorBR.setPower(-.5);
            telemetry.addData("Encoder", (Math.abs(motorFR.getCurrentPosition())));
            telemetry.update();
        }
        motorFL.setPower(0);
        motorFR.setPower(0);
        motorBL.setPower(0);
        motorBR.setPower(0);
    }

    public void turnLeft() {
        encoderReset();
        while ((Math.abs(motorFR.getCurrentPosition()) < 1350) && (opModeIsActive())) {//clockwise
            motorFL.setPower(.5);
            motorFR.setPower(-.5);
            motorBL.setPower(.5);
            motorBR.setPower(-.5);
            telemetry.addData("Encoder", (Math.abs(motorFR.getCurrentPosition())));
            telemetry.update();
        }
        motorFL.setPower(0);
        motorFR.setPower(0);
        motorBL.setPower(0);
        motorBR.setPower(0);
    }

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
}
