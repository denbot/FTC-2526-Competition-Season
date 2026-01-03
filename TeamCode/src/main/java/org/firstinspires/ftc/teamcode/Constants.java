package org.firstinspires.ftc.teamcode;

public class Constants {
    public static final String rightDriveName = "right_drive";
    public static final String leftDriveName = "left_drive";
    public static final String rightShooterServoName = "right_feeder";
    public static final String leftShooterServoName = "left_feeder";
    public static final String launcherName = "launcher";
    public static final double shooterServoStopSpeed = 0.0;
    public static final double shooterServoMaxSpeed = 1;
    public static final double launcherTargetVelocity = 1125;
    public static final double launcherMinVelocity = 1075;
    public static final double launcherStopVelocity = 0.0;
    public static final double feedTimeSeconds = 0.20;
    public static final String intakeName = "intake";
    public static final double outtakePower = -1;
    public static final double intakePower = 1;
    public static final double stopIntake = 0.0;
    public static final double toleranceMM = 10;
    public static final double wheelDiameterMM = 96;
    public static final double encoderTicksPerRev = 537.7;
    public static final double ticksPerMM = encoderTicksPerRev/(wheelDiameterMM * Math.PI);
    public static final double autoMoveSpeed = 0.5;
    public static final double trackWidthMM = 404;
    public static final double timeBetweenLaunchesSeconds = 2;
}
