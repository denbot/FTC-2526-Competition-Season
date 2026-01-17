package bot.den.ftc2526.bonevoyage.subsystem;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;

import bot.den.ftc2526.bonevoyage.Constants;

public class Limelight implements BaseSubsystem {

    private final Telemetry telemetry;
    private Limelight3A limelight = null;

    public Limelight(Telemetry telemetry) {
        this.telemetry = telemetry;
    }

    public void init(HardwareMap hardwareMap) {
        limelight = hardwareMap.get(Limelight3A.class, Constants.Robot.ConfigNames.limelight);
        telemetry.setMsTransmissionInterval(11);
        limelight.pipelineSwitch(0);
        limelight.start();
    }

    @Override
    public void showTelemetry() {
        LLResult result = limelight.getLatestResult();
        if (result != null) {
            if (result.isValid()) {
                Pose3D botpose = result.getBotpose();
                telemetry.addData("tx", result.getTx());
                telemetry.addData("ty", result.getTy());
                telemetry.addData("Botpose", botpose.toString());
                telemetry.addData("Distance", result.getBotposeAvgDist());
            }
        }
    }

    public double getOffset() {
        LLResult result = limelight.getLatestResult();
        if (result != null) {
            if (result.isValid()) {
                return result.getTx();
            }
        }
        return 0;
    }

    public double getDistance() {
        LLResult result = limelight.getLatestResult();
        if (result != null) {
            if (result.isValid()) {
                return result.getBotposeAvgDist();
            }
        }
        return 0;
    }
}
