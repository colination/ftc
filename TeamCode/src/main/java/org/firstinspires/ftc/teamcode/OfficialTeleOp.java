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
//@Disabled
public class OfficialTeleOp extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();

    public DcMotor motorFL;
    public DcMotor motorBL;
    public DcMotor motorFR;
    public DcMotor motorBR;
    public DcMotor omniLeft;
    public DcMotor omniRight;

    public Servo collectLeft;
    public Servo collectRight;

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
        omniLeft = hardwareMap.get(DcMotor.class, "omniLeft");
        omniRight = hardwareMap.get(DcMotor.class, "omniRight");

        collectLeft = hardwareMap.get(Servo.class, "collectLeft");
        collectRight = hardwareMap.get(Servo.class, "collectRight");

        motorFL.setDirection(DcMotor.Direction.FORWARD);
        motorBL.setDirection(DcMotor.Direction.FORWARD);
        motorFR.setDirection(DcMotor.Direction.REVERSE);
        motorBR.setDirection(DcMotor.Direction.REVERSE);
        omniLeft.setDirection(DcMotor.Direction.FORWARD);
        omniRight.setDirection(DcMotor.Direction.REVERSE);

        collectLeft.setDirection(Servo.Direction.FORWARD);
        collectRight.setDirection(Servo.Direction.REVERSE);


        // Set all motors to zero power
        motorFL.setPower(fLPower);
        motorFR.setPower(fRPower);
        motorBL.setPower(bLPower);
        motorBR.setPower(bRPower);
        omniLeft.setPower(oLPower);
        omniRight.setPower(oRPower);

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

            // Choose to drive using either Tank Mode, or POV Mode
            // Comment out the method that's not used.  The default below is POV.

            // POV Mode uses left stick to go forward, and right stick to turn.
            // - This uses basic math to combine motions and is easier to drive straight.
            //double drive = -gamepad1.left_stick_y;
            //double turn  =  gamepad1.right_stick_x;
            //leftPower    = Range.clip(drive + turn, -1.0, 1.0) ;
            //rightPower   = Range.clip(drive - turn, -1.0, 1.0) ;

            // Tank Mode uses one stick to control each wheel.
            // - This requires no math, but it is hard to drive forward slowly and keep straight.
            // leftPower  = -gamepad1.left_stick_y ;
            // rightPower = -gamepad1.right_stick_y ;

            // Tank with Strafe mode
            // joystick logic
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
            //double stickLX2 = gamepad2.left_stick_x;
            double stickLY2 = gamepad2.left_stick_y;
            //double stickRX2 = gamepad2.right_stick_x;
            double stickRY2 = gamepad2.right_stick_y;
            if (Math.abs(stickLY2) > stickCenterThreshold) {
                collectLeft.setPosition(collectLeft.getPosition() + .1);
                //fLPower = stickLY;
                //bLPower = stickLY;
            }
            if (Math.abs(stickRY) > stickCenterThreshold) {
                //fRPower = stickRY;
                //bRPower = stickRY;
                collectRight.setPosition(collectRight.getPosition() + .1);
            }
            //Omni wheel motor moves based on amount the trigger pressed
            oLPower = 0.0;
            oRPower = 0.0;
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
        omniLeft.setPower(oLPower);
        omniRight.setPower(oRPower);

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