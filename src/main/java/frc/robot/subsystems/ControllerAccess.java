// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class ControllerAccess extends SubsystemBase {
  private CommandXboxController driver;
  private CommandXboxController op;
  private double dInt;
  private double oInt;
  private boolean alternate;
  private int counter;

  /** Creates a new ControllerFeedback. */
  public ControllerAccess(CommandXboxController driver , CommandXboxController op) {
    this.driver = driver;
    this.op = op;
    dInt = 0;
    oInt = 0;
    alternate = false;
    counter  = 20;
  }

  @Override
  public void periodic() {
    if(alternate){
      if(counter > 10){
        driver.getHID().setRumble(RumbleType.kLeftRumble, dInt);
        op.getHID().setRumble(RumbleType.kLeftRumble, oInt);
      }
      else{
        driver.getHID().setRumble(RumbleType.kRightRumble, dInt);
        op.getHID().setRumble(RumbleType.kRightRumble, oInt);

      }
    }
    else{
      driver.getHID().setRumble(RumbleType.kBothRumble, dInt);
      op.getHID().setRumble(RumbleType.kBothRumble, oInt);

    }
    if(counter <= 0){
      counter  = 20;
    }
    // This method will be called once per scheduler run
  }

  public void setDriver(double dInt){
    this.dInt = dInt;
  }
  public void setOperator(double oInt){
    this.oInt = oInt;
  }
  public void setAlternate(boolean alternate){
    this.alternate = alternate;
  }

  public double[] getDriverSticks(){
    double LX = driver.getLeftX();
    double LY = driver.getLeftY();
    double RX = driver.getRightX();
    if(Math.abs(LX) < .1){
      LX = 0;
    }
    if(Math.abs(LY) < .1){
      LX = 0;
    }
    if(Math.abs(RX) < .1){
      LX = 0;
    }
    double[] sticks = {LX , LY , RX};
    return sticks;
  }

}
