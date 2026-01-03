package org.firstinspires.ftc.teamcode;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ShooterSubsystem {
    private enum LaunchState {
        IDLE,
        PREPARE,
        LAUNCH,
    }
    private LaunchState launchState;
    private final ElapsedTime feederTimer = new ElapsedTime();
    private final ElapsedTime shotTimer = new ElapsedTime();
    private final Telemetry telemetry;
    private DcMotorEx launcher = null;
    private CRServo leftFeeder = null;
    private CRServo rightFeeder = null;
    private int numberOfArtifacts = 0;
    public ShooterSubsystem (Telemetry telemetry){
        this.telemetry=telemetry;
    }
    public void init (HardwareMap hardwareMap){
        launchState = LaunchState.IDLE;
        launcher = hardwareMap.get(DcMotorEx.class, Constants.launcherName);
        leftFeeder = hardwareMap.get(CRServo.class, Constants. leftShooterServoName);
        rightFeeder = hardwareMap.get(CRServo.class, Constants.rightShooterServoName);
        /*
         * Here we set our launcher to the RUN_USING_ENCODER runmode.
         * If you notice that you have no control over the velocity of the motor, it just jumps
         * right to a number much higher than your set point, make sure that your encoders are plugged
         * into the port right beside the motor itself. And that the motors polarity is consistent
         * through any wiring.
         */
        launcher.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        /*
         * Setting zeroPowerBehavior to BRAKE enables a "brake mode". This causes the motor to
         * slow down much faster when it is coasting. This creates a much more controllable
         * drivetrain. As the robot stops much quicker.
         */

        launcher.setZeroPowerBehavior(BRAKE);

        /*
         * set Feeders to an initial value to initialize the servo controller
         */
        leftFeeder.setPower(Constants.shooterServoStopSpeed);
        rightFeeder.setPower(Constants.shooterServoStopSpeed);

        launcher.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(300, 0, 0, 10));

        /*
         * Much like our drivetrain motors, we set the left feeder servo to reverse so that they
         * both work to feed the ball into the robot.
         */
        leftFeeder.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    public void startLauncher(){
        launcher.setVelocity(Constants.launcherTargetVelocity);
    }
    public void stopLauncher(){
        launcher.setVelocity(Constants.launcherStopVelocity);
    }
    public void showLauncherTelem(){
        telemetry.addData("motorSpeed", launcher.getVelocity());
        telemetry.addData("State", launchState);
    }
    public void launch(){
        switch (launchState) {
            case IDLE:
                if (numberOfArtifacts > 0) {
                    launchState = LaunchState.PREPARE;
                    shotTimer.reset();
                }
                break;
            case PREPARE:
                launcher.setVelocity(Constants.launcherTargetVelocity);
                if (launcher.getVelocity() > Constants.launcherMinVelocity){
                    launchState = LaunchState.LAUNCH;
                    leftFeeder.setPower(1);
                    rightFeeder.setPower(1);
                    feederTimer.reset();
                }
                break;
            case LAUNCH:
                if (feederTimer.seconds() > Constants.feedTimeSeconds) {
                    leftFeeder.setPower(0);
                    rightFeeder.setPower(0);

                    if(shotTimer.seconds() > Constants.timeBetweenLaunchesSeconds){
                        numberOfArtifacts--;
                        launchState = LaunchState.IDLE;
                    }
                }
        }
    }
    public void requestShot(){
        numberOfArtifacts++;
        if (numberOfArtifacts > 3){
            numberOfArtifacts = 3;
        }
    }
    public void setNumberOfArtifacts(int shotsRequested){
        numberOfArtifacts = shotsRequested;
        if (numberOfArtifacts > 3){
            numberOfArtifacts = 3;
        }
    }
    public boolean doneShooting(){
        return numberOfArtifacts == 0;
    }
}
