package bot.den.ftc2526.bonevoyage.opmode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import bot.den.ftc2526.bonevoyage.subsystem.Drive;
import bot.den.ftc2526.bonevoyage.subsystem.Limelight;

@TeleOp
public class FollowLimelight extends OpMode {
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
        turnBot(-1);
    }
    public void turnBot(double metersToStayAway){
        double offset = limelight.getOffset();
        double distance = limelight.getDistance()-metersToStayAway;
        double forwardPower = 0;
        double turnPower = 0;
        double offsetDirection = Math.abs(offset)/offset;
        double distanceDirection = Math.abs(distance)/distance;
        if (Math.abs(offset)>20){
            turnPower = 0.5 * offsetDirection;
        }
        else if (Math.abs(offset)>10){
            turnPower = 0.3 * offsetDirection;
        }
        else if (Math.abs(offset)>1){
            turnPower = 0.15 * offsetDirection;
        }
        if (distance != -metersToStayAway){
            if (Math.abs(distance)>1){
                forwardPower = 0.7 * distanceDirection;
            }
            else if (Math.abs(distance)>0.5){
                forwardPower = 0.5 * distanceDirection;
            }
            else if (Math.abs(distance)>0.1){
                forwardPower = 0.1 * distanceDirection;
            }
        }
        drive.arcadeDrive (forwardPower, turnPower);
        telemetry.addData("offset", offset);
        telemetry.addData("distance", distance);
    }
}
