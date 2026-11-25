package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.Subsystem;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class ShooterSubsystem implements Subsystem {
    private final DcMotorEx shooterMotor;
    private final DcMotorEx transferMotor;
    private double targetVelocity = 0.0;

    public ShooterSubsystem(HardwareMap hardwareMap) {
        this.shooterMotor = hardwareMap.get(DcMotorEx.class, "shooterMotor");
        this.shooterMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        this.shooterMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        transferMotor = hardwareMap.get(DcMotorEx.class, "transferMotor");
        transferMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        transferMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public ShooterSubsystem() {
        this(org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap);
    }

    public void setPower(double power) {
        shooterMotor.setPower(power);
    }

    public void setVelocity(double ticksPerSec) {
        this.targetVelocity = ticksPerSec;
        shooterMotor.setVelocity(ticksPerSec);
    }

    public double getVelocity() {
        return shooterMotor.getVelocity();
    }

    public double getTargetVelocity() {
        return targetVelocity;
    }

    public boolean isAtTargetVelocity(double toleranceTicksPerSec) {
        return Math.abs(getVelocity() - targetVelocity) <= toleranceTicksPerSec;
    }

    public boolean isAtTargetVelocity() {
        return isAtTargetVelocity(50.0);
    }

    public void setTransferPower(double power) {
        if (transferMotor != null) {
            transferMotor.setPower(power);
        }
    }

    public void stopShooter() {
        targetVelocity = 0.0;
        shooterMotor.setPower(0);
    }

    public void stopTransfer() {
        if (transferMotor != null) {
            transferMotor.setPower(0);
        }
    }

    public void stop() {
        stopShooter();
        stopTransfer();
    }

    @Override
    public void periodic() {
        // Periodic updates if required
    }
}
