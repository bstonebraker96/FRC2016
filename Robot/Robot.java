
package org.usfirst.frc.team5968.robot;

import edu.wpi.first.wpilibj.IterativeRobot;

public class Robot extends IterativeRobot {

	TeleopDrive teleopDrive;
	BallFeed ballFeed;
	AutoDrive autoDrive = new AutoDrive();
	InitializeRobot robot;
	
	CrossDefenseStraight mode1 = new CrossDefenseStraight();
	CrossDefenseLeft mode2 = new CrossDefenseLeft();
	CrossDefenseRight mode3 = new CrossDefenseRight();
	ReachDefense mode4 = new ReachDefense();
	DoNothing mode5 = new DoNothing();
	
	private boolean autoStarted = false;
	
	private boolean autoDriveComplete;
	
	int mode;
	
    public void robotInit() {
    	
    	teleopDrive = new TeleopDrive();
    	ballFeed = new BallFeed();
    	autoDrive = new AutoDrive();
    	robot = new InitializeRobot();
    	
    }
    
    public void autonomousInit() {
    	
    	autoDrive.autoDriveInit();
    	
    	mode = autoDrive.getMode();
    	
    	switch (mode)
    	{
    		case 1: mode1.init();
    				break;
    		case 2: mode2.init();
    				break;
    		case 3: mode3.init();
    				break;
    		case 4: mode4.init();
    				break;
    	}
    }

   
    public void autonomousPeriodic() {
    	
    	if(!autoStarted){
    		switch(mode)
    		{
    			case 1: 
    				autoDriveComplete = mode1.crossDefenseStraight();
    				autoStarted = true;
    				break;
    			case 2:
    				autoDriveComplete = mode2.crossDefenseLeft();
    				autoStarted = true;
    				break;
    			case 3:
    				autoDriveComplete = mode3.crossDefenseRight();
    				autoStarted = true;
    				break;
    			case 4:
    				autoDriveComplete = mode4.reachDefense();
    				autoStarted = true;
    			case 5: 
    				autoDriveComplete = mode5.doNothing();
    				autoStarted = true;
    		}
    	}
    	
    	if(autoDriveComplete == true)
    	{
    		//start shooter system
    	}
    	
    	else if(autoDriveComplete == false)
    	{
    		System.out.println("[Error] Auto Error!");
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
