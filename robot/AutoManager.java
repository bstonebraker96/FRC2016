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
    
    //DJM: Using two booleans is clearer than the increasing value variable.
    //     If you had a lot of states and still wanted to use one variable without the >= stuff, you'd want to use a bitfield.
    private boolean shouldDrive;
    private boolean shouldShoot;
    
	private int defenseToCross;
	
	public AutoManager(AutoShootManager shoot, Drive drive) {
			
			this.drive = drive;
			this.shoot = shoot;
			
			driveSwitch = new DigitalInput(PortMap.driveSwitch); //Digital IN 4
			shootSwitch = new DigitalInput(PortMap.shootSwitch); //Digital IN 5
			defenseSwitch1 = new DigitalInput(PortMap.modeSwitch1); //Digital IN 6
			defenseSwitch2 = new DigitalInput(PortMap.modeSwitch2); //Digital IN 7
			defenseSwitch3 = new DigitalInput(PortMap.modeSwitch3); //Digital IN 8
		
			shouldDrive = !driveSwitch.get();
            shouldShoot = shouldDrive && !shootSwitch.get();
            
			defenseToCross = getDefenseToCross();
		
	}
	
	private enum AutonomousProgress {
		DEFENSE_CROSSED, DRIVE_1_COMPLETE, TURN_COMPLETE, TURN_2_COMPLETE, DRIVE_2_COMPLETE,DRIVE_3_COMPLETE, FINISHED
	}
	
	private AutonomousProgress autoProgress;
	
	public void autonomousMain(){
		if(shouldDrive && autoProgress == null)
    	{
    		
    		if(drive.driveAcrossDefense() == CrossingStates.CROSSED)
    		{
    			autoProgress = AutonomousProgress.DEFENSE_CROSSED;
    		}
    		
    	}
        else if (shouldShoot) //DJM There was a bug here where you would stop processing autonomous after entering the DRIVE_1_COMPLETE state.
    	{
    		//THESE DO NOT INCLUDE THE LENGTH OF THE ROBOT!!! TODO FIX!
            //TODO: It is normally not recommended to have switch statements with case clauses this long.
            //TODO: Additionally, most of these case clauses are the same with different numbers.
            //      It would've been easier to read if you had broken out the numbers somewhere else ande made re-usable code.
    		switch(defenseToCross)
    		{
    			case 1:
    				if(autoProgress == AutonomousProgress.DEFENSE_CROSSED)
    				{
    					if(drive.driveDistance(53, true))
    					{
    						autoProgress = AutonomousProgress.DRIVE_1_COMPLETE;
    					}
    				}
    				else if(autoProgress == AutonomousProgress.DRIVE_1_COMPLETE)
    				{
    					if(drive.turn(120, "clockwise"))
    					{
    						autoProgress = AutonomousProgress.TURN_COMPLETE;
    					}
    				}
    				else if(autoProgress == AutonomousProgress.TURN_COMPLETE)
    				{
    					if(drive.driveDistance(65.22, true)){
    						autoProgress = AutonomousProgress.DRIVE_2_COMPLETE;
    					}
    				}
    				else if(autoProgress == AutonomousProgress.DRIVE_2_COMPLETE)
    				{
    					if(shoot.ballShootComputer())
    					{
    						autoProgress = AutonomousProgress.FINISHED;
    					}
    				}
    				break;
    			case 2:
    				if(autoProgress == AutonomousProgress.DEFENSE_CROSSED)
    				{
    					if(drive.driveDistance(82,true))
    					{
    						autoProgress = AutonomousProgress.DRIVE_1_COMPLETE;
    					}
    				}
    				else if(autoProgress == AutonomousProgress.TURN_COMPLETE)
    				{
    					if(drive.turn(120, "clockwise"))
    					{
    						autoProgress = AutonomousProgress.TURN_COMPLETE;
    					}
    				}
    				else if(autoProgress == AutonomousProgress.TURN_COMPLETE)
    				{
    					if(drive.driveDistance(7.22, true)){
    						autoProgress = AutonomousProgress.DRIVE_2_COMPLETE;
    					}
    				}
    				else if(autoProgress == AutonomousProgress.DRIVE_2_COMPLETE)
    				{
    					if(shoot.ballShootComputer())
    					{
    						autoProgress = AutonomousProgress.FINISHED;
    					}
    				}
    				break;
    			case 3:
    				if(autoProgress == AutonomousProgress.DEFENSE_CROSSED)
    				{
    					if(drive.driveDistance(59.5, true))
    					{
    						autoProgress = AutonomousProgress.DRIVE_1_COMPLETE;
    					}
    					else if(autoProgress == AutonomousProgress.DRIVE_1_COMPLETE)
    					{
    						if(drive.turn(90, "clockwise"))
    						{
    							autoProgress = AutonomousProgress.TURN_COMPLETE;
    						}
    					}
    					else if(autoProgress == AutonomousProgress.TURN_COMPLETE)
    					{
    						if(drive.driveDistance(37, true))
    						{
    							autoProgress = AutonomousProgress.DRIVE_2_COMPLETE;
    						}
    					}
    					else if(autoProgress == AutonomousProgress.DRIVE_2_COMPLETE)
    					{
    						if(drive.turn(90, "counterclockwise"))
    						{
    							autoProgress = AutonomousProgress.TURN_2_COMPLETE;
    						}
    					}
    					else if(autoProgress == AutonomousProgress.TURN_2_COMPLETE)
    					{
    						if(drive.driveDistance(60, true))
    						{
    							autoProgress = AutonomousProgress.DRIVE_3_COMPLETE;
    						}
    					}
    					else if(autoProgress == AutonomousProgress.DRIVE_3_COMPLETE) 
    					{
    						if(shoot.ballShootComputer())
    						{
    							autoProgress = AutonomousProgress.FINISHED;
    						}
    					}
    				}
    				
    				break;
    			case 4:
    				if(autoProgress == AutonomousProgress.DEFENSE_CROSSED)
    				{
    					if(drive.driveDistance(59.5, true))
    					{
    						autoProgress = AutonomousProgress.DRIVE_1_COMPLETE;
    					}
    				}
    				else if(autoProgress == AutonomousProgress.DRIVE_1_COMPLETE)
    				{
    					if(drive.turn(90, "counterclockwise"))
    					{
    						autoProgress = AutonomousProgress.TURN_COMPLETE;
    					}
    				}
    				else if(autoProgress == AutonomousProgress.TURN_COMPLETE)
    				{
    					if(drive.driveDistance(13, true))
    					{
    						autoProgress = AutonomousProgress.DRIVE_2_COMPLETE;
    					}
    				}
    				else if(autoProgress == AutonomousProgress.DRIVE_2_COMPLETE)
    				{
    					if(drive.turn(90, "clockwise"))
    					{
    						autoProgress = AutonomousProgress.TURN_2_COMPLETE;
    					}
    				}
    				else if(autoProgress == AutonomousProgress.TURN_2_COMPLETE)
    				{
    					if(drive.driveDistance(60, true))
    					{
    						autoProgress = AutonomousProgress.DRIVE_3_COMPLETE;
    					}
    				}
    				else if(autoProgress == AutonomousProgress.DRIVE_3_COMPLETE)
    				{
    					if(shoot.ballShootComputer())
    					{
    						autoProgress = AutonomousProgress.FINISHED;
    					}
    				}
    				break;
    			case 5:
    				if(autoProgress == AutonomousProgress.DEFENSE_CROSSED)
    				{
    					if(drive.driveDistance(59.5, true))
    					{
    						autoProgress = AutonomousProgress.DRIVE_1_COMPLETE;
    					}
    				}
    				else if(autoProgress == AutonomousProgress.DRIVE_1_COMPLETE)
    				{
    					if(drive.turn(90, "counterclockwise"))
    					{
    						autoProgress = AutonomousProgress.TURN_COMPLETE;
    					}
    				}
    				else if(autoProgress == AutonomousProgress.TURN_COMPLETE)
    				{
    					if(drive.driveDistance(61, true))
    					{
    						autoProgress = AutonomousProgress.DRIVE_2_COMPLETE;
    					}
    				}
    				else if(autoProgress == AutonomousProgress.DRIVE_2_COMPLETE)
    				{
    					if(drive.turn(90, "clockwise"))
    					{
    						autoProgress = AutonomousProgress.TURN_2_COMPLETE;
    					}
    				}
    				else if(autoProgress == AutonomousProgress.TURN_2_COMPLETE)
    				{
    					if(drive.driveDistance(60, true))
    					{
    						autoProgress = AutonomousProgress.DRIVE_3_COMPLETE;
    					}
    				}
    				else if(autoProgress == AutonomousProgress.DRIVE_3_COMPLETE)
    				{
    					if(shoot.ballShootComputer())
    					{
    						autoProgress = AutonomousProgress.FINISHED;
    					}
    				}
    				break;
    			default: //DJM: This catches all bad values, not just 0 (In case of a more nefarious bug.)
    				shouldShoot = false;
    				break;
    		}
    		
    	}
		
	}
	
	public int getDefenseToCross(){
		
		if(!defenseSwitch1.get() && !defenseSwitch2.get() && !defenseSwitch3.get())
		{
			return 4;
		}
		if(!defenseSwitch1.get() && !defenseSwitch2.get() && defenseSwitch3.get())
		{
			return 5;
		}
		if(!defenseSwitch1.get())
		{
			return 1;
		}
		if(!defenseSwitch2.get())
		{
			return 2;
		}
		if(!defenseSwitch3.get())
		{
			return 3;
		}
		return 0;
	}
		
}
