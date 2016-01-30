
package org.usfirst.frc.team5968.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;

public class Robot extends IterativeRobot {

	TeleopDrive teleopDrive;
	BallFeed ballFeed;
	AutoDrive autoDrive = new AutoDrive();
	InitializeRobot robot;
	CrossDefenseStraight mode1 = new CrossDefenseStraight();
	CrossDefenseLeft mode2 = new CrossDefenseLeft();
	
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
    	}
    }

   
    public void autonomousPeriodic() {
    	
    	if(autoStarted){
    		switch(mode)
    		{
    			case 1: 
    				mode1.crossDefenseStraight();
    				autoStarted = true;
    				break;
    			case 2:
    				mode2.crossDefenseLeft();
    				autoStarted = true;
    				break;
    		}
    	}
    	//start shooter system
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
