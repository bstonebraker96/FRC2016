
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
	private boolean shootDriveComplete = false;
	private boolean shootTurnComplete = false;
	private boolean shootTurn2Complete = false;
	
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
    		//THESE DO NOT INCLUDE THE LENGTH OF THE ROBOT!!! TODO FIX!
    		switch(ad.getDefenseToCross())
    		{
    			case 1:
    				if(!shootDriveComplete)
    				{
    					shootDriveComplete = shoot.shootDrive(112.5);
    				}
    				if(shootDriveComplete && !shootTurnComplete)
    				{
    					turn.turn(120, "clockwise");
    				}
    				if(shootDriveComplete && shootTurnComplete)
    				{
    					shoot.ballShootComputer(true);
    				}
    				break;
    			case 2:
    				if(!shootDriveComplete)
    				{
    					shootDriveComplete = shoot.shootDrive(141.5);
    				}
    				if(shootDriveComplete && !shootTurnComplete)
    				{
    					shootTurnComplete = turn.turn(120, "clockwise");
    				}
    				if(shootDriveComplete && shootTurnComplete)
    				{
    					shoot.ballShootComputer(true);
    				}
    				break;
    			case 3:
    				if(!shootTurnComplete)
    				{
    					shootTurnComplete = turn.turn(38, "clockwise");
    				}
    				if(shootTurnComplete && !shootDriveComplete)
    				{
    					shootDriveComplete = shoot.shootDrive(60.2);
    				}
    				if(shootTurnComplete && shootDriveComplete && !shootTurn2Complete)
    				{
    					shootTurn2Complete = turn.turn(38, "counterclockwise");
    				}
    				if(shootTurnComplete && shootDriveComplete && shootTurn2Complete)
    				{
    					shoot.ballShootComputer(true);
    				}
    				break;
    			case 4:
    				if(!shootTurnComplete)
    				{
    					shootTurnComplete = turn.turn(15, "counterclockwise");
    				}
    				if(shootTurnComplete && !shootDriveComplete)
    				{
    					shootDriveComplete = shoot.shootDrive(49.2);
    				}
    				if(shootTurnComplete && shootDriveComplete && !shootTurn2Complete)
    				{
    					shootTurn2Complete = turn.turn(15, "clockwise");
    				}
    				if(shootTurnComplete && shootDriveComplete && shootTurn2Complete)
    				{
    					shoot.ballShootComputer(true);
    				}
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
