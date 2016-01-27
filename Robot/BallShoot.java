package org.usfirst.frc.team5968.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class BallShoot {
	
	Joystick stick;
	//JoystickButton isHuman;
	JoystickButton isComputer;
	Victor leftShootMotor;
	Victor rightShootMotor;
	Talon leftDriveMotor;
	Talon rightDriveMotor;
	
	public void ballShootInit() {
		
		PortReference ref = new PortReference();
		
		stick = new Joystick(ref.getAltJoystick());
		//isHuman = new JoystickButton(stick, 3);
		isComputer = new JoystickButton(stick, 4);
	}
	
	public void ballShoot() {
		
		if (isComputer.get()) {
			ballShootComputer();
		} else {
			ballShootHuman();
		}
		
	}
	
	public void ballShootHuman() {
		
		
		
	}
	
	public void ballShootComputer() {
		
	}
	
}