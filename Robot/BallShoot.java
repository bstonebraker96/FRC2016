package org.usfirst.frc.team5968.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class BallShoot 
{
	
	Joystick stick;
	JoystickButton isComputer;
	
	Victor leftShootMotor;
	Victor rightShootMotor;
	
	Talon leftDriveMotor;
	Talon rightDriveMotor;
	
	Encoder leftEncoder;
	Encoder rightEncoder;
	
	PortReference ref;
	
	Compressor ballPusher;
	
	public void ballShootInit() 
	{
		
		ref = new PortReference();
		
		stick = new Joystick(ref.getAltJoystick());
		isComputer = new JoystickButton(stick, 4);
		
		leftShootMotor = new Victor(ref.getLeftShootMotor());
		rightShootMotor = new Victor(ref.getRightMotor());
		
		leftDriveMotor = new Talon(ref.getLeftMotor());
		rightDriveMotor = new Talon(ref.getRightMotor());
		
		ballPusher = new Compressor(ref.getBallCompressor());
		
	}
	
	public void ballShoot() 
	{
		
		if (isComputer.get()) 
		{
			ballShootComputer();
		} else 
		{
			ballShootHuman();
		}
		
	}
	
	public void ballShootHuman() 
	{
		
		if (stick.getX() == 0) 
		{
			leftDriveMotor.set(stick.getY() );
			rightDriveMotor.set((stick.getY()) * -1);
		} 
		else if (stick.getY() == 0) 
		{
			leftDriveMotor.set(stick.getX());
			rightDriveMotor.set(stick.getX());
		}
		
		if (stick.getTrigger() && ballPusher.enabled()) 
		{
			leftShootMotor.set(-1);
			rightShootMotor.set(1);
			
			ballPusher.setClosedLoopControl(true);
			ballPusher.setClosedLoopControl(false);
		} 
		else if (stick.getTrigger())
		{
			System.out.println("Compressor (ballPusher) not enabled.");
		} 
		else
		{
			leftShootMotor.set(0);
			rightShootMotor.set(0);
		}
		
	}
	
	public void ballShootComputer() 
	{
		
	}
	
}