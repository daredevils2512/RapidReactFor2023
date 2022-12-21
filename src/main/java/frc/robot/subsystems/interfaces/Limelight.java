package frc.robot.subsystems.interfaces;

import frc.robot.subsystems.vision.LimelightLEDMode;
import frc.robot.subsystems.vision.Pipeline;

/**
 * returned 228 in at 208 inches
 */

/**
 * Limelight manager for power cell target tracking
 */
public interface Limelight {

  /** @return The current pipline being used */
  Pipeline getDefaultPipeline(); 
  
  /** Sets the pipeline to use */
  void setPipeline(Pipeline pipeline);
  
  /** Sets the LED mode for the limelight */
  void setLEDMode(LimelightLEDMode ledMode);
  
  /** @return The LED mode being used */
  LimelightLEDMode getLEDMode();
  
  /** @return tv == 1 */
  boolean hasTarget();
  
  /** @return tx */
  double tx();
  
  /** @return ty */
  double ty();
  
  /** @return ta */
  double ta();
  
  /** @return ts */
  double ts();
  
  /** @return tl */
  double tl();
  
  /** @return tshort */
  int tshort();
  
  /** @return tlong */
  int tlong();
  
  /** @return thor */
  int thor();
  
  /** @return tvert */
  int tvert();
  
  /** @return The last position */
  double getLastPosition();

}