package org.usfirst.frc.team5968.robot;


import org.usfirst.frc.team5968.robot.Drive.CrossingStates;

import edu.wpi.first.wpilibj.DigitalInput;
public class AutoManager {
	public Drive drive;
	private AutoShootManager shoot;
	
	private DigitalInput driveSwitch;
	private DigitalInput shootSwitch;
	private DigitalInput defenseSwitch1;
	private DigitalInput defenseSwitch2;
	private DigitalInput defenseSwitch3;
	
	private int mode;
	private int defenseToCross;
	
	public AutoManager(AutoShootManager shoot2) {
		driveSwitch = new DigitalInput(PortMap.driveSwitch); //Digital IN 4
		shootSwitch = new DigitalInput(PortMap.shootSwitch); //Digital IN 5
		defenseSwitch1 = new DigitalInput(PortMap.modeSwitch1); //Digital IN 6
		defenseSwitch2 = new DigitalInput(PortMap.modeSwitch2); //Digital IN 7
		defenseSwitch3 = new DigitalInput(PortMap.modeSwitch3); //Digital IN 8
		drive = new Drive();
		this.shoot = shoot2;
		
		mode = getMode();
		defenseToCross = getDefenseToCross();
		
		
	}
	
	private enum AutonomousProgress {
		DEFENSE_CROSSED, DRIVE_1_COMPLETE, TURN_COMPLETE, TURN_2_COMPLETE, DRIVE_2_COMPLETE
	}
	
	private AutonomousProgress autoProgress;
	
	
	public void autonomousMain(){
		
		if(mode == 0)
    	{
    		
    	}
    	
    	if(mode >= 1 && autoProgress != AutonomousProgress.DEFENSE_CROSSED)
    	{
    		
    		if(drive.driveAcrossDefense() == CrossingStates.CROSSED)
    		{
    			autoProgress = AutonomousProgress.DEFENSE_CROSSED;
    		}
    		
    		
    	}
    	
    	if(mode == 2 && autoProgress == AutonomousProgress.DEFENSE_CROSSED)
    	{
    		//THESE DO NOT INCLUDE THE LENGTH OF THE ROBOT!!! TODO FIX!
    		switch(defenseToCross)
    		{
    			case 1:
    				if(autoProgress == AutonomousProgress.DEFENSE_CROSSED)
    				{
    					if(drive.driveDistance(112.5))
    					{
    						autoProgress = AutonomousProgress.DRIVE_1_COMPLETE;
    					}
    				}
    				if(autoProgress == AutonomousProgress.DRIVE_1_COMPLETE)
    				{
    					if(drive.turn(120, "clockwise"))
    					{
    						autoProgress = AutonomousProgress.TURN_COMPLETE;
    					}
    				}
    				if(autoProgress == AutonomousProgress.TURN_COMPLETE)
    				{
    					shoot.ballShootComputer();
    				}
    				break;
    			case 2:
    				if(autoProgress == AutonomousProgress.DEFENSE_CROSSED)
    				{
    					if(drive.driveDistance(141.5))
    					{
    						autoProgress = AutonomousProgress.DRIVE_1_COMPLETE;
    					}
    				}
    				if(autoProgress == AutonomousProgress.TURN_COMPLETE)
    				{
    					if(drive.turn(120, "clockwise"))
    					{
    						autoProgress = AutonomousProgress.TURN_COMPLETE;
    					}
    				}
    				if(autoProgress == AutonomousProgress.TURN_COMPLETE)
    				{
    					shoot.ballShootComputer();
    				}
    				break;
    			case 3:
    				if(autoProgress == AutonomousProgress.DEFENSE_CROSSED)
    				{
    					if(drive.turn(38, "clockwise"))
    					{
    						autoProgress = AutonomousProgress.TURN_COMPLETE;
    					}
    				}
    				if(autoProgress == AutonomousProgress.TURN_COMPLETE)
    				{
    					if(drive.driveDistance(60.2))
    					{
    						autoProgress = AutonomousProgress.DRIVE_1_COMPLETE;
    					}
    				}
    				if(autoProgress == AutonomousProgress.DRIVE_1_COMPLETE)
    				{
    					if(drive.turn(38, "counterclockwise"))
    					{
    						autoProgress = AutonomousProgress.TURN_2_COMPLETE;
    					}
    				}
    				if(autoProgress == AutonomousProgress.TURN_2_COMPLETE)
    				{
    					shoot.ballShootComputer();
    				}
    				break;
    			case 4:
    				if(autoProgress == AutonomousProgress.DEFENSE_CROSSED)
    				{
    					if(drive.turn(15, "counterclockwise"))
    					{
    						autoProgress = AutonomousProgress.TURN_COMPLETE;
    					}
    					
    				}
    				if(autoProgress == AutonomousProgress.TURN_COMPLETE)
    				{
    					if(drive.driveDistance(49.2))
    					{
    						autoProgress = AutonomousProgress.DRIVE_1_COMPLETE;
    					}
    				}
    				if(autoProgress == AutonomousProgress.DRIVE_1_COMPLETE)
    				{
    					if(drive.turn(15, "clockwise"))
    					{
    						autoProgress = AutonomousProgress.TURN_2_COMPLETE;
    					}
    				}
    				if(autoProgress == AutonomousProgress.TURN_2_COMPLETE)
    				{
    					shoot.ballShootComputer();
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
	
	
		
	public int getMode()
	{		
		if(driveSwitch.get() && shootSwitch.get())
		{
			return 2;
		}
		
		if(driveSwitch.get() && !shootSwitch.get())
		{
			return 1;
		}
		
		if(!driveSwitch.get() && !shootSwitch.get())
		{
			return 0;
		}
		
		else{
			return 0;
		}
	}
	public int getDefenseToCross(){
		
		if(defenseSwitch1.get())
		{
			return 1;
		}
		if(defenseSwitch2.get())
		{
			return 2;
		}
		if(defenseSwitch3.get())
		{
			return 3;
		}
		if(defenseSwitch1.get() && defenseSwitch2.get() && defenseSwitch3.get())
		{
			return 4;
		}
		if(!defenseSwitch1.get() && !defenseSwitch2.get() && !defenseSwitch3.get())
		{
			return 5;
		}
		return 0;
	}
		
}
