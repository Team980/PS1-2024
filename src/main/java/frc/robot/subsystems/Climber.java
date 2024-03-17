// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Climber extends SubsystemBase {
  private CANSparkMax leftClimb;
  private RelativeEncoder leftEncoder;
  private CANSparkMax rightClimb;
  private RelativeEncoder rightEncoder;

  private boolean climbEnable;
  private boolean reverseSafetyOff;

  /** Creates a new Climber. */
  public Climber() {
    leftClimb = new CANSparkMax(50, MotorType.kBrushless);
    leftEncoder = leftClimb.getEncoder();
    leftEncoder.setPositionConversionFactor(1 / 16.0);
    rightClimb = new CANSparkMax(51, MotorType.kBrushless);
    rightEncoder = rightClimb.getEncoder();
    rightEncoder.setPositionConversionFactor(1 / 16.0);

    climbEnable = false;
    reverseSafetyOff = false;
  }

  @Override
  public void periodic() {
    SmartDashboard.putBoolean("Climbers Enabled", climbEnable);
    SmartDashboard.putBoolean("Climber Reverse Safety", reverseSafetyOff);
    SmartDashboard.putNumber("leftClimb", leftEncoder.getPosition());
    SmartDashboard.putNumber("rightClimb", rightEncoder.getPosition());
    // This method will be called once per scheduler run
  }
  public void enableClimber(){
    climbEnable = true;
  }
  public void disableClimber(){
    climbEnable = false;
  }
  public void enableReverse(){
    reverseSafetyOff = true;
  }
  public void disableReverse(){
    reverseSafetyOff = false;
  }

  public double getLeftRotations(){
    return leftEncoder.getPosition();
  }
  public double getRightRotations(){
    return rightEncoder.getPosition();
  }

  public boolean leftDeploy(){
    if(leftEncoder.getPosition() < 3.1){
      leftClimb.set(1);
      return false;
    }
    else{
      leftClimb.set(0);
      return true;
    }
    
  }
  public boolean rightDeploy(){
    if(rightEncoder.getPosition() < 4.5){
      rightClimb.set(1);
      return false;
    }
    else{
      rightClimb.set(0);
      return true;
    }
    
  }

  public void runClimbers(double left , double right , boolean climbEnable , Joystick prajBox){
    if(Math.abs(left) < .1){
      left = 0;
    }
    if(Math.abs(right) < .1){
      right = 0;
    }
    if(!prajBox.getRawButton(4)){
      if(left < 0){
        left = 0;
      }
      if(right < 0){
        right = 0;
      }
    }
    leftClimb.set(left);
    rightClimb.set(right);
  }
}
