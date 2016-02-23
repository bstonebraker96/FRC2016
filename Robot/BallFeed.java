package org.usfirst.frc.team5968.robot;

import org.usfirst.frc.team5968.robot.HumanInterface.BallFeedStates;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class BallFeed {
	
	private Talon feedMotor;
	
	public BallFeed() {
		
		feedMotor = new Talon(PortMap.feedMotor);
		
	}//end of method
	
	
	public void ballFeed(BallFeedStates state){
		
		if(state == BallFeedStates.FAST)
		{			
			feedMotor.set(1);
		}
		
		if(state == BallFeedStates.STOPPED)
		{
			feedMotor.set(0);
		}
		
		if(state == BallFeedStates.SLOW)
		{	
			feedMotor.set(.1);	
		}
		
		
	}//end of method

}//end of class
