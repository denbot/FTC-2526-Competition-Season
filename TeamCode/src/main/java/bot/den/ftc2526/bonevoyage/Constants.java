package bot.den.ftc2526.bonevoyage;

public class Constants {
    public static class Drive {
        public static class Auto {
            public static final double toleranceMM = 10;
            public static final double moveFastSpeed = 0.60;
            public static final double moveSlowSpeed = 0.18;
            public static final double turnSpeed = 0.2;
            public static final double holdSeconds = 0.5;
        }

        public static final double maxSpeed = 1.0;
    }

    public static class Game {
        public static final int maxArtifacts = 3;
    }

    public static class Intake {
        public static final double slowIntakePower = 0.7;
        public static final double outtakePower = -5.0;
        public static final double intakePower = 1.0;
        public static final double stopPower = 0.0;
    }

    public static class Robot {
        public static class ConfigNames {
            public static final String rightDrive = "right_drive";
            public static final String leftDrive = "left_drive";
            public static final String rightFeederServo = "right_feeder";
            public static final String leftFeederServo = "left_feeder";
            public static final String launcher = "launcher";
            public static final String intake = "intake";
            public static final String limelight = "limelight";
        }

        private static final double wheelDiameterMM = 96;
        private static final double encoderTicksPerRev = 537.7;
        public static final double ticksPerMM = encoderTicksPerRev / (wheelDiameterMM * Math.PI);
        public static final double trackWidthMM = 404;
    }

    public static class Shooter {
        public static final double launcherReverseSpeed = -540;
        public static final double feederReversePower = -0.4;
        public static final double feederSlowReversePower = -0.2;
        public static final double feederSlowPower = 0.4;
        public static final double feederStopPower = 0.0;
        public static final double feederRunPower = 1.0;
        public static final double launcherTargetVelocityRpm = 1125;
        public static final double launcherMinVelocityRpm = 1075;
        public static final double launcherStopVelocityRpm = 0.0;
        public static final double feedTimeSeconds = 0.25;
        public static final double launchTimeSeconds = 1;
    }
}
