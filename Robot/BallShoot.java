package org.usfirst.frc.team5968.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class BallShoot 
{
	
	private JoystickButton isComputer;
	
	private InitializeRobot robotComponents;
	
	private Compressor ballPusher;
	
	private Talon leftDriveMotor;
	private Talon rightDriveMotor;
	private Talon leftDriveMotor2;
	private Talon rightDriveMotor2;
	
	private Victor leftShootMotor;
	private Victor rightShootMotor;
	
	private Joystick altStick;
	
	public void ballShootInit() 
	{
		robotComponents = new InitializeRobot();
		
		ballPusher = robotComponents.getBallPusher();
		
		leftDriveMotor = robotComponents.getLeftMotor();
		rightDriveMotor = robotComponents.getRightMotor();
		leftDriveMotor2 = robotComponents.getLeftMotor2();
		rightDriveMotor2 = robotComponents.getRightMotor2();
		
		leftShootMotor = robotComponents.getLeftShootMotor();
		rightShootMotor = robotComponents.getRightShootMotor();
		
		altStick = robotComponents.getAltJoystick();
		
		
		isComputer = new JoystickButton(altStick, 4);
		
		
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
		
		if (altStick.getX() == 0) 
		{
			leftDriveMotor.set(altStick.getY());
			rightDriveMotor.set((altStick.getY()) * -1);
			leftDriveMotor2.set(altStick.getY());
			rightDriveMotor2.set((altStick.getY()) * -1);
		} 
		else if (altStick.getY() == 0) 
		{
			leftDriveMotor.set(altStick.getX());
			rightDriveMotor.set(altStick.getX());
			leftDriveMotor2.set(altStick.getX());
			rightDriveMotor2.set(altStick.getX());
		}
		
		if (altStick.getTrigger() && ballPusher.enabled()) 
		{
			leftShootMotor.set(-1);
			rightShootMotor.set(1);
			
			ballPusher.setClosedLoopControl(true);
			ballPusher.setClosedLoopControl(false);
		} 
		else if (altStick.getTrigger())
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