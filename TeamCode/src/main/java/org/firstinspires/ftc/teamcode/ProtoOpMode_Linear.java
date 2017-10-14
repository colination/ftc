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

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;


/**
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all linear OpModes contain.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name="Proto: Linear OpMode", group="Linear Opmode")
@Disabled
public class ProtoOpMode_Linear extends LinearOpMode {
//ideas: variable strafe speed on other joystick, lock in tread speed in tank mode
    // Declare OpMode members.
    HardwareProtobot   robot   = new HardwareProtobot();   // Use a Pushbot's hardware

    private ElapsedTime runtime = new ElapsedTime();
    //private DcMotor fLMotor = null;
    //private DcMotor bLMotor = null;
    //private DcMotor fRMotor = null;
    //private DcMotor bRMotor = null;

    private double speed = .5;
    private double stickCenterThreshold = .1;
    private double stickPushSmall = .2;
    private double stickPushLarge = .8;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        robot.init(hardwareMap);
        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        //fLMotor  = hardwareMap.get(DcMotor.class, "FLdrive");
        //bLMotor = hardwareMap.get(DcMotor.class, "BLdrive");
        //fRMotor  = hardwareMap.get(DcMotor.class, "FRdrive");
        //bRMotor = hardwareMap.get(DcMotor.class, "BRdrive");

        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        //fLMotor.setDirection(DcMotor.Direction.FORWARD);
        //bLMotor.setDirection(DcMotor.Direction.FORWARD);
        //fRMotor.setDirection(DcMotor.Direction.REVERSE);
        //bRMotor.setDirection(DcMotor.Direction.REVERSE);

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
            if(stickLX <= -stickPushLarge && stickRX <= -stickPushLarge
                    && Math.abs(stickLY) <= stickPushSmall && Math.abs(stickRY) <= stickPushSmall){
                //strafe left
                robot.navStrafe(speed, true);
                /*fLPower = speed;
                bRPower = speed;
                bLPower = -speed;
                fRPower = -speed;*/
            }
            else if(stickLX >= stickPushLarge && stickRX >= stickPushLarge
                    && Math.abs(stickLY) <= stickPushSmall && Math.abs(stickRY) <= stickPushSmall){
                //strafe right
                robot.navStrafe(speed, false);
                /*fLPower = -speed;
                bRPower = -speed;
                bLPower = speed;
                fRPower = speed;*/
            }
            else {
                //tank mode
                double leftSpeed = 0;
                double rightSpeed = 0;

                if(Math.abs(stickLY) > stickCenterThreshold){
                    leftSpeed = stickLY;
                    //fLPower = stickLY;
                    //bLPower = stickLY;
                }
                if(Math.abs(stickRY) > stickCenterThreshold){
                    //fRPower = stickRY;
                    //bRPower = stickRY;
                    rightSpeed = stickRY;
                }
                robot.navTank(leftSpeed, rightSpeed);
            }

            // Send calculated power to wheels
            /*fLMotor.setPower(fLPower);
            fRMotor.setPower(fRPower);
            bLMotor.setPower(bLPower);
            bRMotor.setPower(bRPower);*/

            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            //robot.navTelemetry();
            telemetry.addData("Front Motors", "left (%.2f), right (%.2f)", robot.fLPower, robot.fRPower);
            telemetry.addData("Back Motors ", "left (%.2f), right (%.2f)", robot.bLPower, robot.bRPower);
            telemetry.update();
        }
        telemetry.addData("Status", "STOPPED Time: " + runtime.toString());
        telemetry.update();
    }
}
