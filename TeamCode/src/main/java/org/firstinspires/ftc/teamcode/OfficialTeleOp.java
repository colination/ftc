package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import for_camera_opmodes.CameraPreview;

@TeleOp(name="OfficialTeleOp", group="Linear Opmode")
//@Disabled

public class OfficialTeleOp extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();

//<<<<<<< HEAD
    //Manip servo
    //Servo manipServo;
//=======
    //MANIPILATOR SERVO
    Servo manipServo;
    Servo jewelHit;
//>>>>>>> 7286f2e7664925c52135da553a117d8a0fe9d92e


    // BASE
    DcMotor motorFL;
    DcMotor motorBL;
    DcMotor motorFR;
    DcMotor motorBR;

    // MANIPULATOR
    DcMotor manipulator;

    // COLLECTION (OMNI WHEELS)
    DcMotor collectLeft;
    DcMotor collectRight;

    // LIFT
    DcMotor lift;

    // COLOR SENSOR FOR AUTO
    ColorSensor jewelColor;

    public double fLPower = 0.0;
    public double bLPower = 0.0;
    public double fRPower = 0.0;
    public double bRPower = 0.0;

    private double speed = .5;
    private double stickCenterThreshold = .1;
    private double stickPushSmall = .2;
    private double stickPushLarge = .8;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        motorFL = hardwareMap.get(DcMotor.class, "motorFL");
        motorBL = hardwareMap.get(DcMotor.class, "motorBL");
        motorFR = hardwareMap.get(DcMotor.class, "motorFR");
        motorBR = hardwareMap.get(DcMotor.class, "motorBR");

        manipServo = hardwareMap.get(Servo.class, "manipServo");

        lift = hardwareMap.get(DcMotor.class, "lift");

        manipServo = hardwareMap.get(Servo.class, "manipServo");
        jewelHit = hardwareMap.get(Servo.class, "jewelHit");

        collectLeft = hardwareMap.get(DcMotor.class, "collectLeft");
        collectRight = hardwareMap.get(DcMotor.class, "collectRight");

        manipulator = hardwareMap.get(DcMotor.class, "manipulator");

        manipServo.setDirection(Servo.Direction.FORWARD);


        motorFL.setDirection(DcMotor.Direction.FORWARD);
        motorBL.setDirection(DcMotor.Direction.FORWARD);
        motorFR.setDirection(DcMotor.Direction.REVERSE);
        motorBR.setDirection(DcMotor.Direction.REVERSE);

        lift.setDirection(DcMotor.Direction.REVERSE);

        collectLeft.setDirection(DcMotor.Direction.FORWARD);
        collectRight.setDirection(DcMotor.Direction.FORWARD);

        manipulator.setDirection(DcMotor.Direction.FORWARD);

        jewelColor = hardwareMap.get(ColorSensor.class, "jewelColor");

        // Set all motors to zero power
        motorFL.setPower(fLPower);
        motorFR.setPower(fRPower);
        motorBL.setPower(bLPower);
        motorBR.setPower(bRPower);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            jewelColor.enableLed(false);

            // BASE : Gamepad 1, Joysticks
            double stickLX = valueConvert(gamepad1.left_stick_x);
            double stickLY = valueConvert(gamepad1.left_stick_y);
            double stickRX = valueConvert(gamepad1.right_stick_x);
            double stickRY = valueConvert(gamepad1.right_stick_y);
            /*telemetry.addData("Status", "Run Time: " + stickLX + stickLY + stickRX + stickRY + Math.abs(motorFR.getCurrentPosition() ) + Math.abs(motorFL.getCurrentPosition() ) + Math.abs(motorBL.getCurrentPosition() ) + Math.abs(motorBR.getCurrentPosition() ));
            telemetry.update();*/

            // BASE : Strafe
            fRPower = moveEquation(stickRY, strafe(stickLX, stickRX));
            fLPower = moveEquation(stickLY, -strafe(stickLX, stickRX));
            bRPower = moveEquation(stickRY, -strafe(stickLX, stickRX));
            bLPower = moveEquation(stickLY, strafe(stickLX, stickRX));
            navSetPower();

            jewelHit.setPosition(0);

            double stickLY2 = gamepad2.left_stick_y;
            double stickRY2 = gamepad2.right_stick_y;

            // COLLECTION : Left and Right Trigger
            // Moving Forwards
            if (gamepad1.left_trigger >= .5)
            {
                collectLeft.setPower(1);
            }
            else {
                collectLeft.setPower(0);
            }

            if (gamepad1.right_trigger >= .5) {
                collectRight.setPower(-1);
            }
            else {
                collectRight.setPower(0);
            }


            // Moving Backwards
            if ((gamepad1.left_bumper)) {
                collectLeft.setPower(-1);
            }

            else {
                collectLeft.setPower(0);
            }

            if ((gamepad1.right_bumper)) {
                collectRight.setPower(1);
            }

            else {
                collectRight.setPower(0);
            }


            // MANIPULATOR : Gamepad 2, Right Joystick
            if (Math.abs(stickRY2) >= stickPushSmall)
                manipulator.setPower(gamepad2.right_stick_y);
            else{
                manipulator.setPower(0);
            }


            // LIFT CODE : Gamepad 2, Left Joystick
            if (Math.abs(stickLY2) >= stickPushSmall)
                lift.setPower(-gamepad2.left_stick_y);
            else{
                lift.setPower(0);
            }
/*<<<<<<< HEAD

            //MANIPULATOR SERVO
            telemetry.addLine().addData(" ",manipServo.getPosition());
            if (gamepad2.left_bumper)
            {
                    manipServo.setPosition(.5);
=======*/
            //MANIPULATOR SERVO
            telemetry.addLine().addData(" ", manipServo.getPosition());
            while (gamepad2.left_bumper)
            {
                if(manipServo.getPosition()== 0.0){
                    manipServo.setPosition(1);
                }
//>>>>>>> 7286f2e7664925c52135da553a117d8a0fe9d92e
            }
            manipServo.setPosition(0.0);
        }
    }

    public double valueConvert(double controllerValue) {//note: this needs to be reversed larger coefficient for higher motor value

        if(Math.abs(controllerValue) <= .03){
            return 0;
        }
        if(Math.abs(controllerValue) > .03 && Math.abs(controllerValue) <= .6){
            if (controllerValue < 0) {
                return (1/4 * controllerValue - .02);
            }
            else {
                return (1/4 * controllerValue + .02);
            }
        }
        if(Math.abs(controllerValue) > .6 && Math.abs(controllerValue) <= .95){
            if (controllerValue < 0) {
                return (2/3 * controllerValue - .15);
            }
            else {
                return (2/3 * controllerValue + .15);
            }
        }
        if(Math.abs(controllerValue) > .95 && Math.abs(controllerValue) <= 1){
            if (controllerValue < 0) {
                return (3 * controllerValue - 2);
            }
            else {
                return (3 * controllerValue + 2);
            }
        }
        return 0;
    }

    public double strafe(double leftX, double rightX){
        return (leftX + rightX)/2;
    }

    public void navSetPower() {
        motorFL.setPower(fLPower);
        motorFR.setPower(fRPower);
        motorBL.setPower(bLPower);
        motorBR.setPower(bRPower);
    }

    public void navTank(double leftSpeed, double rightSpeed) {
        fLPower = bLPower = leftSpeed;
        fRPower = bRPower = rightSpeed;
        navSetPower();
    }

    public void navStrafe(double speed, boolean isLeft) {
        if (isLeft == true) {
            speed = -speed;     // strafe left
        }
        fLPower = speed;
        bRPower = speed;
        bLPower = -speed;
        fRPower = -speed;
        navSetPower();
    }

    public double moveEquation(double yValue, double strafeValue) {
        return (yValue * Math.abs(yValue) + strafeValue * Math.abs(strafeValue))
                /(Math.abs(yValue) + Math.abs(strafeValue));
    }

}


            /*telemetry.addLine().addData("", (Math.abs(motorFR.getCurrentPosition() )));
            telemetry.update();
            telemetry.addLine().addData("", (Math.abs(motorFL.getCurrentPosition() )));
            telemetry.update();
            telemetry.addLine().addData("", (Math.abs(motorBL.getCurrentPosition() )));
            telemetry.update();
            telemetry.addLine().addData("", (Math.abs(motorBR.getCurrentPosition() )));
            telemetry.update();*/

            /*if (stickLX <= -stickPushLarge && stickRX <= -stickPushLarge
                    && Math.abs(stickLY) <= stickPushSmall && Math.abs(stickRY) <= stickPushSmall) {
                //strafe left
                navStrafe(speed, true);
            } else if (stickLX >= stickPushLarge && stickRX >= stickPushLarge
                    && Math.abs(stickLY) <= stickPushSmall && Math.abs(stickRY) <= stickPushSmall) {
                //strafe right
                navStrafe(speed, false);

            } else {
                //tank mode
                double leftSpeed = 0;
                double rightSpeed = 0;

                if (Math.abs(stickLY) > stickCenterThreshold) {
                    leftSpeed = stickLY;

                }
                if (Math.abs(stickRY) > stickCenterThreshold) {
                    rightSpeed = stickRY;
                }
                navTank(leftSpeed, rightSpeed);

            }*/


            /*/ BALANCE BEAM CLAWS
            clawLeft.setPosition(0);
            clawRight.setPosition(0);

            if (gamepad2.a)
            {
                if (clawLeft.getPosition() == 0) {
                    clawLeft.setPosition(1);
                    clawRight.setPosition(1);
                } else {
                    clawLeft.setPosition(0);
                    clawRight.setPosition(0);
                }
            }*/