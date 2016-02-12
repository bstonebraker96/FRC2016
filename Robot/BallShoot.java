package org.usfirst.frc.team5968.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.VictorSP;

public class BallShoot 
{
	
	private InitializeRobot robotComponents;
	private AutoDriveBase drive;
	
	private Compressor ballPusher;
	
	private Victor leftDriveMotor;
	private Victor rightDriveMotor;
	
	private VictorSP leftShootMotor;
	private VictorSP rightShootMotor;
	
	private Encoder leftEncoder;
	private Encoder rightEncoder;
	
	private double leftRate;
	private double rightRate;
	private double leftEncoderOld;
	private double rightEncoderOld;
	private double leftDistance = 0;
	private double rightDistance = 0;
	
	private long nanotimeOld = System.nanoTime();
	
	private Joystick altStick;
	
	public void ballShootInit() 
	{
		robotComponents = InitializeRobot.GetInstance();
		drive = new AutoDriveBase();
		
		ballPusher = robotComponents.getBallPusher();
		
		leftDriveMotor = robotComponents.getLeftMotor();
		rightDriveMotor = robotComponents.getRightMotor();
		
		leftShootMotor = robotComponents.getLeftShootMotor();
		rightShootMotor = robotComponents.getRightShootMotor();
		
		altStick = robotComponents.getAltJoystick();
		
		leftEncoder = robotComponents.getLeftEncoder();
		rightEncoder = robotComponents.getRightEncoder();
		
		ballPusher.setClosedLoopControl(true);
	}
	
	public void shootDrive(){
		
		leftRate = ((leftEncoder.get() - leftEncoderOld) * robotComponents.getCountsPerRevolution()) / ((System.nanoTime() - nanotimeOld) * 60 * Math.pow(10, 9));
		rightRate = ((rightEncoder.get() - rightEncoderOld) * robotComponents.getCountsPerRevolution()) / ((System.nanoTime() - nanotimeOld) * 60 * Math.pow(10, 9));
		
		leftDistance = leftEncoder.get() * robotComponents.getCountsPerRevolution() * 7.65 * Math.PI;
		rightDistance = rightEncoder.get() * robotComponents.getCountsPerRevolution() * 7.65 * Math.PI;
		
		drive.fixDirection(leftRate, rightRate, false);
		
		if(leftDistance == 144 || rightDistance == 144){
			
			ballShootComputer(true);
			
			leftDriveMotor.set(0);
			rightDriveMotor.set(0);
			
		}
		
		
		nanotimeOld = System.nanoTime();
		leftEncoderOld = leftEncoder.get();
		rightEncoderOld = rightEncoder.get();
	}
	
	public void ballShootHuman() 
	{
		
		if (altStick.getTrigger() && ballPusher.enabled()) 
		{
			leftShootMotor.set(-1);
			rightShootMotor.set(1);
	
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
	
	public void ballShootComputer(boolean useCamera) 
	{
		
		if(useCamera)
		{
			
		}
		
		else if(!useCamera)
		{
			
			leftShootMotor.set(1);
			rightShootMotor.set(-1);
			
			Timer.delay(.250);
			
		}
		
	}
	
}