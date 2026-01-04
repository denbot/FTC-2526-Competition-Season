package bot.den.ftc2526.bonevoyage.subsystem;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import bot.den.ftc2526.bonevoyage.Constants;

public class Shooter implements BaseSubsystem{
    private enum LaunchState {
        IDLE,
        PREPARE,
        LAUNCH,
    }

    private final Telemetry telemetry;
    private LaunchState launchState;
    private final ElapsedTime feederTimer = new ElapsedTime();
    private final ElapsedTime shotTimer = new ElapsedTime();
    private DcMotorEx launcher = null;
    private CRServo leftFeeder = null;
    private CRServo rightFeeder = null;
    private int numberOfArtifacts = 0;

    public Shooter(Telemetry telemetry){
        this.telemetry=telemetry;
    }

    public void init (HardwareMap hardwareMap){
        launchState = LaunchState.IDLE;
        launcher = hardwareMap.get(DcMotorEx.class, Constants.Robot.ConfigNames.launcher);
        leftFeeder = hardwareMap.get(CRServo.class, Constants.Robot.ConfigNames.leftFeederServo);
        rightFeeder = hardwareMap.get(CRServo.class, Constants.Robot.ConfigNames.rightFeederServo);

        launcher.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        launcher.setZeroPowerBehavior(BRAKE);

        leftFeeder.setPower(Constants.Shooter.feederStopPower);
        rightFeeder.setPower(Constants.Shooter.feederStopPower);

        launcher.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(300, 0, 0, 10));

        leftFeeder.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void startLauncher(){
        launcher.setVelocity(Constants.Shooter.launcherTargetVelocityRpm);
    }

    public void stopLauncher(){
        launcher.setVelocity(Constants.Shooter.launcherStopVelocityRpm);
    }

    public void showTelemetry(){
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
                launcher.setVelocity(Constants.Shooter.launcherTargetVelocityRpm);
                if (launcher.getVelocity() > Constants.Shooter.launcherMinVelocityRpm){
                    launchState = LaunchState.LAUNCH;
                    leftFeeder.setPower(Constants.Shooter.feederRunPower);
                    rightFeeder.setPower(Constants.Shooter.feederRunPower);
                    feederTimer.reset();
                }
                break;
            case LAUNCH:
                if (feederTimer.seconds() > Constants.Shooter.feedTimeSeconds) {
                    leftFeeder.setPower(Constants.Shooter.feederStopPower);
                    rightFeeder.setPower(Constants.Shooter.feederStopPower);

                    if(shotTimer.seconds() > Constants.Shooter.launchTimeSeconds){
                        numberOfArtifacts--;
                        launchState = LaunchState.IDLE;
                    }
                }
        }
    }

    public void requestShot(){
        numberOfArtifacts++;
        if (numberOfArtifacts > Constants.Game.maxArtifacts){
            numberOfArtifacts = Constants.Game.maxArtifacts;
        }
    }

    public void setNumberOfArtifacts(int shotsRequested){
        numberOfArtifacts = shotsRequested;
        if (numberOfArtifacts > Constants.Game.maxArtifacts){
            numberOfArtifacts = Constants.Game.maxArtifacts;
        }
    }

    public boolean doneShooting(){
        return numberOfArtifacts == 0;
    }
}
