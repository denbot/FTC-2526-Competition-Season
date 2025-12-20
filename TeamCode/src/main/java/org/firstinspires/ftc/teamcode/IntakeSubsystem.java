package org.firstinspires.ftc.teamcode;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class IntakeSubsystem {
    private final Telemetry telemetry;
    private DcMotor intakeMotor = null;

    public IntakeSubsystem (Telemetry telemetry){
        this.telemetry=telemetry;
    }

    public void init(HardwareMap hardwareMap){
        intakeMotor = hardwareMap.get(DcMotor.class, Constants.intakeName);
        intakeMotor.setDirection(DcMotor.Direction.FORWARD);
        intakeMotor.setZeroPowerBehavior(BRAKE);
    }
    public void showIntakeTelem(){
        telemetry.addData("Intake", "Power (%.2f)", intakeMotor.getPower());
    }
     public void intake(){
         intakeMotor.setPower(Constants.intakePower);
    }
    public void outtake(){
        intakeMotor.setPower(Constants.intakePower);
    }
    public void stopeIntake(){
        intakeMotor.setPower(Constants.intakePower);
    }
}
