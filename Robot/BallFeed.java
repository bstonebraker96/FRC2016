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
			feedMotor.set(-1);
		}
		else if(state == BallFeedStates.SLOW)
		{	
			feedMotor.set(-0.4);	
		}
		else
		{
			feedMotor.set(0);
		}
		
		
	}//end of method

}//end of class
