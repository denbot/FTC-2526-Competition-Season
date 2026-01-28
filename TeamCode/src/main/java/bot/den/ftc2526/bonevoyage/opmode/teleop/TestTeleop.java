package bot.den.ftc2526.bonevoyage.opmode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import bot.den.ftc2526.bonevoyage.subsystem.Drive;
import bot.den.ftc2526.bonevoyage.subsystem.Limelight;

@TeleOp
public class TestTeleop extends OpMode {
    private final Drive drive = new Drive(telemetry);
    private Limelight limelight = new Limelight(telemetry);
    @Override
    public void init() {
        limelight.init(hardwareMap);
        drive.init(hardwareMap);
        telemetry.addData("Status", "Initialized");

    }

    @Override
    public void loop() {
        drive.showTelemetry();
        limelight.showTelemetry();
        if (gamepad1.circle) turnBot();

    }
    public void turnBot(){
        double offset = limelight.getOffset();
        double distance = limelight.getDistance();
        if (Math.abs(offset)>1){
            double direction = Math.abs(offset)/offset;
            drive.arcadeDrive(0,.2*direction);
            telemetry.addData("offset", offset);
            telemetry.addData("direction", direction);
        } else if (Math.abs(distance)>1) {
            drive.arcadeDrive(.2,0);
        } else{
            drive.arcadeDrive(0,0);
        }
        telemetry.addData("distance", distance);
    }
}
