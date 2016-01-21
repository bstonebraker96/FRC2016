
package org.usfirst.frc.team5968.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
    
	public TeleopDrive drive;
	
    public void robotInit() {
        
    	drive = new TeleopDrive();
    	
    }
    
    public void autonomousInit() {
    	
    }

   
    public void autonomousPeriodic() {
    	
    }
    
    public void teleopInit(){
    	
    }
    
    public void teleopPeriodic() {
    	drive.driveBase();
    }
    
    
    public void testPeriodic() {
    
    }
    
}
