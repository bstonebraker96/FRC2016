
package org.usfirst.frc.team5968.robot;

import edu.wpi.first.wpilibj.IterativeRobot;

public class Robot extends IterativeRobot {

	TeleopDrive teleopDrive;
	BallFeed ballFeed;
	AutoDriveBase ad;
	InitializeRobot robot;
	
	Drive autoDrive;
	
	private boolean defenseCrossed = false;
	
	int mode;
	
    public void robotInit() {
    	
    	teleopDrive = new TeleopDrive();
    	ballFeed = new BallFeed();
    	ad = new AutoDriveBase();
    	robot = new InitializeRobot();
    	autoDrive = new Drive();
    	
    }
    
    public void autonomousInit() {
    	
    	ad.autoDriveInit();
    	
    	mode = ad.getMode();
    	
    }

   
    public void autonomousPeriodic() {
    	
    	if(mode == 0)
    	{
    		
    	}
    	
    	if(mode >= 1 && !defenseCrossed)
    	{
    		
    		if(autoDrive.autoDrive())
    		{
    			defenseCrossed = true;
    		}
    		
    		
    	}
    	
    	if(mode == 2 && defenseCrossed)
    	{
    		
    		//start ballshoot
    		
    	}
    	
    }
    
    public void teleopInit(){
    	
    	teleopDrive.driveInit();
    	
    	ballFeed.ballFeedInit();
    	
    }
    
    public void teleopPeriodic() {
    	
    	teleopDrive.driveBase();
    	
    	ballFeed.ballFeed();
    	
    }
    
    
    public void testPeriodic() {
    
    }
    
}
