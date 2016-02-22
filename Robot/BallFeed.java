package org.usfirst.frc.team5968.robot;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class BallFeed {

	private JoystickButton wheelControl;
	private JoystickButton wheelControlSlow;
	
	/*
	Nobody else needs to own the feed motor, just add a central location (a static final int would be good) for the
	port number being used.

	Change to SpeedController type.
	*/
	private Talon feedMotor;
	
	private InitializeRobot robotComponents;
	
	public BallFeed() {
		robotComponents = InitializeRobot.GetInstance();

		/*
		Move all UI stuff to a HumanInterface class that keeps track of button presses for you.
		Consider using getRawButton over JoystickButton as it is less verbose in the long run.
		Consider creating a list of static final ints that correspond to the button configuration (in the HumanInterface class.)
		*/
		wheelControl = new JoystickButton(robotComponents.getAltJoystick(), 1);
		wheelControlSlow = new JoystickButton(robotComponents.getAltJoystick(), 2);
		
		feedMotor = robotComponents.getFeedMotor();
		
	}//end of method
	
	
	public void ballFeed() {

		if(wheelControl.get())
		{		
			feedMotor.set(.75);
		}
		else if (wheelControlSlow.get())
		{
			feedMotor.set(.1);	
		}
		else
		{
			feedMotor.set(0);	
		}
		
	}//end of method

}//end of class
