package org.usfirst.frc.team5968.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class BallShoot 
{
	
	private JoystickButton isComputer;
	
	private InitializeRobot robotComponents;
	
	private Compressor ballPusher;
	
	private Victor leftDriveMotor;
	private Victor rightDriveMotor;
	
	private VictorSP leftShootMotor;
	private VictorSP rightShootMotor;
	
	private Joystick altStick;
	
	public void ballShootInit() 
	{
		robotComponents = InitializeRobot.GetInstance();
		
		ballPusher = robotComponents.getBallPusher();
		
		leftDriveMotor = robotComponents.getLeftMotor();
		rightDriveMotor = robotComponents.getRightMotor();
		
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
		} 
		else if (altStick.getY() == 0) 
		{
			leftDriveMotor.set(altStick.getX());
			rightDriveMotor.set(altStick.getX());
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