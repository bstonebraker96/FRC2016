package org.usfirst.frc.team5968.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.Talon;

public class BallShoot 
{
	
	private InitializeRobot robotComponents;
	private AutoDriveBase drive;
	
	private Compressor compressor;
	private Solenoid ballPusher;
	private Solenoid shootAngle;
	
	private Victor leftDriveMotor;
	private Victor rightDriveMotor;
	
	private Talon ballFeed;
	
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
	
	private long nanotimeOld;
	
	private Joystick altStick;
	
	public void ballShootInit() 
	{
		robotComponents = InitializeRobot.GetInstance();
		drive = new AutoDriveBase();
		
		/*compressor = robotComponents.getCompressor();
		ballPusher = robotComponents.getBallPusher();
		shootAngle = robotComponents.getAngleSolenoid();
		*/
		leftDriveMotor = robotComponents.getLeftMotor();
		rightDriveMotor = robotComponents.getRightMotor();
		
		leftShootMotor = robotComponents.getLeftShootMotor();
		rightShootMotor = robotComponents.getRightShootMotor();
		
		altStick = robotComponents.getAltJoystick();
		
		leftEncoder = robotComponents.getLeftEncoder();
		rightEncoder = robotComponents.getRightEncoder();
		
		//compressor.setClosedLoopControl(true);
	}
	
	public boolean shootDrive(double distance){
		
		nanotimeOld = System.nanoTime();
		
		leftRate = ((leftEncoder.get() - leftEncoderOld) * robotComponents.getCountsPerRevolution()) / ((System.nanoTime() - nanotimeOld) * 60 * Math.pow(10, 9));
		rightRate = ((rightEncoder.get() - rightEncoderOld) * robotComponents.getCountsPerRevolution()) / ((System.nanoTime() - nanotimeOld) * 60 * Math.pow(10, 9));
		
		leftDistance = leftEncoder.get() * robotComponents.getCountsPerRevolution() * 7.65 * Math.PI;
		rightDistance = rightEncoder.get() * robotComponents.getCountsPerRevolution() * 7.65 * Math.PI;
		
		drive.fixDirection(leftRate, rightRate, false);
		
		if(Math.abs(leftDistance - distance) <= .1 || Math.abs(rightDistance - distance) <= .1){
			
			leftDriveMotor.set(0);
			rightDriveMotor.set(0);
			
			leftEncoder.reset();
			rightEncoder.reset();
			
			return true;
		}
		
		else if(Timer.getMatchTime() >= 10)
		{
			leftDriveMotor.set(0);
			rightDriveMotor.set(0);
			return false;
		}
		
		nanotimeOld = System.nanoTime();
		leftEncoderOld = leftEncoder.get();
		rightEncoderOld = rightEncoder.get();
		
		return false;
	}
	
	//TODO set the timing of the shooting
	public void ballShootHuman() 
	{
		
			leftShootMotor.set(-1);
			rightShootMotor.set(1);
			
			ballFeed.set(.75);
			
			Timer.delay(.1);
			
			ballFeed.set(0);
			
			Timer.delay(1);
			
			leftShootMotor.set(0);
			rightShootMotor.set(0);
		
	}
	//TODO also set timing of shooting.
	public void ballShootComputer(boolean useCamera) 
	{
		
		if(useCamera)
		{
			
		}
		
		else if(!useCamera)
		{
			ballShootHuman();
		}
		
	}
	
	public void platformAngle()
	{
		if(shootAngle.get())
		{
			shootAngle.set(false);
		}
		if(!shootAngle.get())
		{
			shootAngle.set(true);
		}
	}
	
}