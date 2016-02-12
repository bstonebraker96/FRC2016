
package org.usfirst.frc.team5968.robot;

import edu.wpi.first.wpilibj.IterativeRobot;

public class Robot extends IterativeRobot {

	TeleopDrive teleopDrive;
	BallFeed ballFeed;
	AutoDriveBase ad;
	InitializeRobot robot;
	BallShoot shoot;
	Turn turn;
	
	Drive autoDrive;
	
	private boolean defenseCrossed = false;
	
	int mode;
	
    public void robotInit() {
    	
    	teleopDrive = new TeleopDrive();
    	ballFeed = new BallFeed();
    	ad = new AutoDriveBase();
    	robot = InitializeRobot.GetInstance();
    	autoDrive = new Drive();
    	turn = new Turn();
    	
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
    		
    		switch(ad.getDefenseToCross())
    		{
    			case 1:
    				
    				break;
    			case 2:
    				
    				break;
    			case 3:
    				
    				break;
    			case 4:
    				
    				break;
    			case 5:
    				
    				break;
    			case 0:
    				mode = 0;
    				break;
    		}
    		
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
