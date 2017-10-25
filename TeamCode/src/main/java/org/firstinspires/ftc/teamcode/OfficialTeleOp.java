package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="OfficialTeleOp", group="Linear Opmode")
@Disabled

public class OfficialTeleOp extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();

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

    // BALANCE BEAM CLAWS
    public Servo clawLeft;
    public Servo clawRight;

    // LIFT
    DcMotor lift;

    public double fLPower = 0.0;
    public double bLPower = 0.0;
    public double fRPower = 0.0;
    public double bRPower = 0.0;
    public double oLPower = 0.0;
    public double oRPower = 0.0;

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

        lift = hardwareMap.get(DcMotor.class, "lift");

        collectLeft = hardwareMap.get(DcMotor.class, "collectLeft");
        collectRight = hardwareMap.get(DcMotor.class, "collectRight");

        manipulator = hardwareMap.get(DcMotor.class, "manipulator");

        clawRight = hardwareMap.get(Servo.class, "clawRight");
        clawLeft = hardwareMap.get(Servo.class, "clawLeft");

        motorFL.setDirection(DcMotor.Direction.FORWARD);
        motorBL.setDirection(DcMotor.Direction.FORWARD);
        motorFR.setDirection(DcMotor.Direction.REVERSE);
        motorBR.setDirection(DcMotor.Direction.REVERSE);

        clawLeft.setDirection(Servo.Direction.FORWARD);
        clawRight.setDirection(Servo.Direction.FORWARD);

        collectLeft.setDirection(DcMotor.Direction.FORWARD);
        collectRight.setDirection(DcMotor.Direction.FORWARD);

        manipulator.setDirection(DcMotor.Direction.FORWARD);


        // Set all motors to zero power
        motorFL.setPower(fLPower);
        motorFR.setPower(fRPower);
        motorBL.setPower(bLPower);
        motorBR.setPower(bRPower);


        clawLeft.setPosition(0);
        clawRight.setPosition(0);


        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            // Setup a variable for each drive wheel to save power level for telemetry
            double fLPower;
            double bLPower;
            double fRPower;
            double bRPower;

            // Tank with Strafe mode : Uses joysticks on Gamepad 1
            double stickLX = gamepad1.left_stick_x;
            double stickLY = gamepad1.left_stick_y;
            double stickRX = gamepad1.right_stick_x;
            double stickRY = gamepad1.right_stick_y;
            if (stickLX <= -stickPushLarge && stickRX <= -stickPushLarge
                    && Math.abs(stickLY) <= stickPushSmall && Math.abs(stickRY) <= stickPushSmall) {
                //strafe left
                navStrafe(speed, true);
                /*fLPower = speed;
                bRPower = speed;
                bLPower = -speed;
                fRPower = -speed;*/
            } else if (stickLX >= stickPushLarge && stickRX >= stickPushLarge
                    && Math.abs(stickLY) <= stickPushSmall && Math.abs(stickRY) <= stickPushSmall) {
                //strafe right
                navStrafe(speed, false);
                /*fLPower = -speed;
                bRPower = -speed;
                bLPower = speed;
                fRPower = speed;*/
            } else {
                //tank mode
                double leftSpeed = 0;
                double rightSpeed = 0;

                if (Math.abs(stickLY) > stickCenterThreshold) {
                    leftSpeed = stickLY;
                    //fLPower = stickLY;
                    //bLPower = stickLY;
                }
                if (Math.abs(stickRY) > stickCenterThreshold) {
                    //fRPower = stickRY;
                    //bRPower = stickRY;
                    rightSpeed = stickRY;
                }
                navTank(leftSpeed, rightSpeed);

            }

            //Joystick collection logic
            double stickLY2 = gamepad2.left_stick_y;
            double stickRY2 = gamepad2.right_stick_y;

            //double triggerLeft = gamepad1.left_trigger;
            //double triggerRight = gamepad1.right_trigger;
            if (stickLY2 > stickCenterThreshold) {
                oLPower = stickLY2;
                navSetPower();
            }
            if (stickRY2 > stickCenterThreshold) {
                oRPower = stickRY2;
                navSetPower();
            }

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
            }

            //Collector
            if (gamepad1.left_trigger >= .5)
            {
                collectLeft.setPower(1);

            }


            if ((gamepad1.right_trigger >= .5)) {

                collectRight.setPower(0);
            }


            //manipulator
            if (Math.abs(stickRY2) >= stickPushSmall)
                manipulator.setPower(gamepad2.right_stick_y);
            else{
                manipulator.setPower(0);
            }

            //lift
            if (Math.abs(stickLY2) >= stickPushSmall)
                lift.setPower(gamepad2.left_stick_y);
            else{
                lift.setPower(0);
            }



            // Send calculated power to wheels
            /*fLMotor.setPower(fLPower);
            fRMotor.setPower(fRPower);
            bLMotor.setPower(bLPower);
            bRMotor.setPower(bRPower);*/

            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            //robot.navTelemetry();
            //telemetry.addData("Front Motors", "left (%.2f), right (%.2f)", fLPower, fRPower);
            //telemetry.addData("Back Motors ", "left (%.2f), right (%.2f)", bLPower, bRPower);
            telemetry.update();


        }
        telemetry.addData("Status", "STOPPED Time: " + runtime.toString());
        telemetry.update();
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
        if (isLeft == false) {
            speed = -speed;     // strafe right
        }
        fLPower = speed;
        bRPower = speed;
        bLPower = -speed;
        fRPower = -speed;
        navSetPower();
    }

}