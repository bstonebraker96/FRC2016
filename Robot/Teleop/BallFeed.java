package org.usfirst.frc.team5968.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class BallFeed {

	Joystick altStick;
	JoystickButton wheelControl;
	JoystickButton wheelControlSlow;
	
	Victor ballFeedWheel;
	
	public void ballFeedInit(){
		
		PortReference ref = new PortReference();
		
		altStick = new Joystick(ref.getAltJoystick());
		ballFeedWheel = new Victor(ref.getFeedMotor());
		
		wheelControl = new JoystickButton(altStick, 1);
		wheelControlSlow = new JoystickButton(altStick, 2);
	}
	
	
	public void ballFeed(){
		
		if(wheelControl.get()){
			
			ballFeedWheel.set(.75);
		
		}
		
		if(!wheelControl.get()){
			
			ballFeedWheel.set(0);
			
		}
		
		if(wheelControlSlow.get()){
			
			ballFeedWheel.set(.1);
			
		}
		
		if(!wheelControlSlow.get()){
			
			ballFeedWheel.set(0);
			
		}
		
	}
}
