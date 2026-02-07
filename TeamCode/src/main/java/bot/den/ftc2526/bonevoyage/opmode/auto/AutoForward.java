package bot.den.ftc2526.bonevoyage.opmode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import bot.den.ftc2526.bonevoyage.subsystem.Drive;

@Autonomous(name = "AutoForward", group = "DenBot")

public class AutoForward extends OpMode {
    private enum AutoState{
        GO_FORWARD,
        END
    }

    private final Drive drive = new Drive(telemetry);
    private AutoState state = AutoState.GO_FORWARD;

    @Override
    public void init(){
        drive.init(hardwareMap);
        drive.resetEncoders();
    }

    @Override
    public void loop(){
        switch (state){
            case GO_FORWARD:
                if (drive.drive(25, DistanceUnit.INCH)){
                    state = AutoState.END;
                }
                break;
        }

        drive.showTelemetry();
    }
}
