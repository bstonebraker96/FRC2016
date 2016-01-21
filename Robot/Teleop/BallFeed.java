package org.usfirst.frc.team5968.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class BallFeed {

	Joystick altStick;
	JoystickButton wheelStart;
	JoystickButton wheelStop;
	
	Victor ballFeedWheel;
	
	public void ballFeedInit(){
		
		PortReference ref = new PortReference();
		
		altStick = new Joystick(ref.getAltJoystick());
		ballFeedWheel = new Victor(ref.getFeedMotor());
		
		wheelStart = new JoystickButton(altStick, 1);
		wheelStop = new JoystickButton(altStick, 2);
	}
	
	
	public void ballFeed(){
		
		if(wheelStart.get()){
			
			ballFeedWheel.set(1);
		
		}
		
		if(wheelStop.get()){
			
			ballFeedWheel.set(0);
			
		}
		
	}
	
}
