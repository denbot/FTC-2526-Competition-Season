package bot.den.ftc2526.bonevoyage.subsystem;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import bot.den.ftc2526.bonevoyage.Constants;

public class Spinnythingy implements BaseSubsystem {
    private CRServo spinnything = null;
    private static Telemetry telemetry;

    private boolean reverse = false;

    public Spinnythingy(Telemetry telemetry){
        this.telemetry=telemetry;
    }

    @Override
    public void init(HardwareMap hardwareMap) {
        spinnything = hardwareMap.get(CRServo.class, Constants.Robot.ConfigNames.spinnything);
    }

    @Override
    public void showTelemetry() {
        telemetry.addData("motorSpeed", spinnything.getPower());
    }

    public void startSpinning(){
        spinnything.setPower(Constants.EpicSpinnyThingy.spinnyThingyGoBrrr);
    }
    public void reverseDirection(){
        spinnything.setPower(-spinnything.getPower());
    }
}
