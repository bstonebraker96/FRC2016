package org.usfirst.frc.team5968.robot;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class BallFeed {

	private JoystickButton wheelControl;
	private JoystickButton wheelControlSlow;
	
	private Talon feedMotor;
	
	
	private InitializeRobot robotComponents;
	
	public void ballFeedInit(){
		
		robotComponents = InitializeRobot.GetInstance();
		wheelControl = new JoystickButton(robotComponents.getAltJoystick(), 1);
		wheelControlSlow = new JoystickButton(robotComponents.getAltJoystick(), 2);
		
		feedMotor = robotComponents.getFeedMotor();
		
	}//end of method
	
	
	public void ballFeed(){
		
		if(wheelControl.get())
		{			
			feedMotor.set(.75);
		}
		
		if(!wheelControl.get())
		{
			feedMotor.set(0);
		}
		
		if(wheelControlSlow.get())
		{	
			feedMotor.set(.1);	
		}
		
		if(!wheelControlSlow.get())
		{	
			feedMotor.set(0);	
		}
		
	}//end of method

}//end of class
