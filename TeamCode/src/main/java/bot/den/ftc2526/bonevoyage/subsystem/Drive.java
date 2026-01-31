package bot.den.ftc2526.bonevoyage.subsystem;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import bot.den.ftc2526.bonevoyage.Constants;

public class Drive implements BaseSubsystem{
    private final Telemetry telemetry;
    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;
    private double leftPower;
    private double rightPower;
    private final ElapsedTime holdTimer = new ElapsedTime();

    public Drive(Telemetry telemetry){
        this.telemetry=telemetry;
    }

    public void init(HardwareMap hardwareMap){
        leftDrive = hardwareMap.get(DcMotor.class, Constants.Robot.ConfigNames.leftDrive);
        rightDrive = hardwareMap.get(DcMotor.class, Constants.Robot.ConfigNames.rightDrive);

        leftDrive.setDirection(DcMotor.Direction.REVERSE);
        rightDrive.setDirection(DcMotor.Direction.FORWARD);

        leftDrive.setZeroPowerBehavior(BRAKE);
        rightDrive.setZeroPowerBehavior(BRAKE);
    }

    public void arcadeDrive(double forward, double rotate) {
        leftPower = forward + rotate;
        rightPower = forward - rotate;

        leftDrive.setPower(leftPower);
        rightDrive.setPower(rightPower);
    }

    public void showTelemetry(){
        telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
    }

    private boolean driveInternal(double distance, DistanceUnit unit, double power){

        double targetPosition = (unit.toMm(distance) * Constants.Robot.ticksPerMM);

        leftDrive.setTargetPosition((int) targetPosition);
        rightDrive.setTargetPosition((int) targetPosition);

        leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftDrive.setPower(power);
        rightDrive.setPower(power);

        if(Math.abs(targetPosition - leftDrive.getCurrentPosition()) > (Constants.Drive.Auto.toleranceMM * Constants.Robot.ticksPerMM)){
            holdTimer.reset();
        }

        return (holdTimer.seconds() > Constants.Drive.Auto.holdSeconds);
    }

    public boolean drive(double distance, DistanceUnit unit) {
        return driveFast(distance, unit);
    }
    public boolean driveFast (double distance, DistanceUnit unit) {
        return driveInternal(distance, unit, Constants.Drive.Auto.moveFastSpeed);
    }
    public boolean driveSlow (double distance, DistanceUnit unit) {
        return driveInternal(distance, unit, Constants.Drive.Auto.moveSlowSpeed);
    }

    public void resetEncoders(){
        leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public boolean rotate(double angle, AngleUnit unit){
        double targetMm = unit.toRadians(angle)*(Constants.Robot.trackWidthMM/2);

        /*
         * We need to set the left motor to the inverse of the target so that we rotate instead
         * of driving straight.
         */
        double leftTargetPosition = -(targetMm*Constants.Robot.ticksPerMM);
        double rightTargetPosition = targetMm*Constants.Robot.ticksPerMM;

        leftDrive.setTargetPosition((int) leftTargetPosition);
        rightDrive.setTargetPosition((int) rightTargetPosition);

        leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftDrive.setPower(Constants.Drive.Auto.turnSpeed);
        rightDrive.setPower(Constants.Drive.Auto.turnSpeed);

        if((Math.abs(leftTargetPosition - leftDrive.getCurrentPosition())) > (Constants.Drive.Auto.toleranceMM * Constants.Robot.ticksPerMM)){
            holdTimer.reset();
        }

        return (holdTimer.seconds() > Constants.Drive.Auto.holdSeconds);
    }
}
