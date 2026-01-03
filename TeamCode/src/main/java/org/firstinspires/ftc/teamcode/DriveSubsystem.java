package org.firstinspires.ftc.teamcode;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class DriveSubsystem {
    private final Telemetry telemetry;
    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;
    // Setup a variable for each drive wheel to save power level for telemetry
    private double leftPower;
    private double rightPower;
    private ElapsedTime holdTimer = new ElapsedTime();

    public DriveSubsystem (Telemetry telemetry){
        this.telemetry=telemetry;
    }

    public void init(HardwareMap hardwareMap){

        leftDrive = hardwareMap.get(DcMotor.class, Constants.leftDriveName);
        rightDrive = hardwareMap.get(DcMotor.class, Constants.rightDriveName);
        /*
         * To drive forward, most robots need the motor on one side to be reversed,
         * because the axles point in opposite directions. Pushing the left stick forward
         * MUST make robot go forward. So adjust these two lines based on your first test drive.
         * Note: The settings here assume direct drive on left and right wheels. Gear
         * Reduction or 90 Deg drives may require direction flips
         */
        leftDrive.setDirection(DcMotor.Direction.REVERSE);
        rightDrive.setDirection(DcMotor.Direction.FORWARD);
        /*
         * Setting zeroPowerBehavior to BRAKE enables a "brake mode". This causes the motor to
         * slow down much faster when it is coasting. This creates a much more controllable
         * drivetrain. As the robot stops much quicker.
         */
        leftDrive.setZeroPowerBehavior(BRAKE);
        rightDrive.setZeroPowerBehavior(BRAKE);
    }

    public void arcadeDrive(double forward, double rotate) {
        leftPower = forward + rotate;
        rightPower = forward - rotate;

        /*
         * Send calculated power to wheels
         */
        leftDrive.setPower(leftPower);
        rightDrive.setPower(rightPower);
    }
    public void showDriveTelem(){
        telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
    }

    public boolean drive(double distance, DistanceUnit unit, double holdTime){
        double targetPosition = (unit.toMm(distance) * Constants.ticksPerMM);

        leftDrive.setTargetPosition((int) targetPosition);
        rightDrive.setTargetPosition((int) targetPosition);

        leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftDrive.setPower(Constants.autoMoveSpeed);
        rightDrive.setPower(Constants.autoMoveSpeed);

        if(Math.abs(targetPosition - leftDrive.getCurrentPosition()) > (Constants.toleranceMM * Constants.ticksPerMM)){
            holdTimer.reset();
        }

        return (holdTimer.seconds() > holdTime);
    }
    public void resetEncoders(){
        leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    public boolean rotate(double angle, AngleUnit unit, double holdTime){

        double targetMm = unit.toRadians(angle)*(Constants.trackWidthMM/2);

        /*
         * We need to set the left motor to the inverse of the target so that we rotate instead
         * of driving straight.
         */
        double leftTargetPosition = -(targetMm*Constants.ticksPerMM);
        double rightTargetPosition = targetMm*Constants.ticksPerMM;

        leftDrive.setTargetPosition((int) leftTargetPosition);
        rightDrive.setTargetPosition((int) rightTargetPosition);

        leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftDrive.setPower(Constants.autoMoveSpeed);
        rightDrive.setPower(Constants.autoMoveSpeed);

        if((Math.abs(leftTargetPosition - leftDrive.getCurrentPosition())) > (Constants.toleranceMM * Constants.ticksPerMM)){
            holdTimer.reset();
        }

        return (holdTimer.seconds() > holdTime);
    }
}
