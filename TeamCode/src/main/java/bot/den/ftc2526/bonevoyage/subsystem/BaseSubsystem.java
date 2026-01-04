package bot.den.ftc2526.bonevoyage.subsystem;

import com.qualcomm.robotcore.hardware.HardwareMap;

public interface BaseSubsystem {
    public void init(HardwareMap hardwareMap);
    public void showTelemetry();
}
