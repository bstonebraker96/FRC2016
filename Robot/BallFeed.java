package org.usfirst.frc.team5968.robot;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class BallFeed {
	
	private Talon feedMotor;
	
	public BallFeed() {
		
		feedMotor = new Talon(PortMap.feedMotor);
		
	}//end of method
	
	
	public void ballFeed(int wheelControl){
		
		if(wheelControl == 1)
		{			
			feedMotor.set(.75);
		}
		
		if(wheelControl == 0)
		{
			feedMotor.set(0);
		}
		
		if(wheelControl == 2)
		{	
			feedMotor.set(.1);	
		}
		
		
	}//end of method

}//end of class
