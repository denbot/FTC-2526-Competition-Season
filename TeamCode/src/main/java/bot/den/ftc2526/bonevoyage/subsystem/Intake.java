package bot.den.ftc2526.bonevoyage.subsystem;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import bot.den.ftc2526.bonevoyage.Constants;

public class Intake {
    private final Telemetry telemetry;
    private DcMotor intakeMotor = null;

    public Intake(Telemetry telemetry){
        this.telemetry=telemetry;
    }

    public void init(HardwareMap hardwareMap){
        intakeMotor = hardwareMap.get(DcMotor.class, Constants.Robot.ConfigNames.intake);
        intakeMotor.setDirection(DcMotor.Direction.REVERSE);
        intakeMotor.setZeroPowerBehavior(BRAKE);
    }

    public void showIntakeTelem(){
        telemetry.addData("Intake", "Power (%.2f)", intakeMotor.getPower());
    }

    public void intake(){
         intakeMotor.setPower(Constants.Intake.intakePower);
    }

    public void outtake(){
        intakeMotor.setPower(Constants.Intake.outtakePower);
    }

    public void stopIntake(){
        intakeMotor.setPower(Constants.Intake.stopPower);
    }
}
