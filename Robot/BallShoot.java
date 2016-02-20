package org.usfirst.frc.team5968.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.Talon;

public class BallShoot extends uART
{
	
	private InitializeRobot robotComponents;
	private AutoDriveBase drive;
	private Compressor compressor;
	private Solenoid shootAngle;

	private Drive scooter;
	private Victor leftDriveMotor;
	private Victor rightDriveMotor;
	
	private Talon ballFeed;
	
	private VictorSP leftShootMotor;
	private VictorSP rightShootMotor;
	//THESE DOUBLES ALL NEED VALUES BEFORE TESTING COMPUTER SHOOTING!!!(except adjDist)
	public double angle_MAX, angle_MIN, distance_MIN, distance_MAX, adjDist;
	private Encoder leftEncoder;
	private Encoder rightEncoder;
	
	private double leftRate;
	private double rightRate;
	private double leftEncoderOld;
	private double rightEncoderOld;
	private double leftDistance = 0;
	private double rightDistance = 0;
	
	private long nanotimeOld;
	
	
	public void ballShootInit() 
	{
		robotComponents = InitializeRobot.GetInstance();
		drive = new AutoDriveBase();
		
		compressor = robotComponents.getCompressor();
		shootAngle = robotComponents.getAngleSolenoid();
		
		leftDriveMotor = robotComponents.getLeftMotor();
		rightDriveMotor = robotComponents.getRightMotor();
		
		leftShootMotor = robotComponents.getLeftShootMotor();
		rightShootMotor = robotComponents.getRightShootMotor();
		
		
		leftEncoder = robotComponents.getLeftEncoder();
		rightEncoder = robotComponents.getRightEncoder();
		
		compressor.setClosedLoopControl(true);
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
			
			ballFeed.set(.5);
			
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
			piWriter("fire mah boudler");
			if (!angleChecker())
			{
				System.out.print("She won't make it captain! Try again!");
			}
			else
			{
				distanceChecker();
				ballShootHuman();
			}
			
				
		}
		
		else
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
	}//end of platformAngle
	
	public boolean angleChecker()
	{
		if ((angle < angle_MAX) && (angle > angle_MIN))
		{
			System.out.println("Nice angle");
			return true;
		}
		else
		{
			//scooter.scootAngle();
			System.out.println("Angle function not available yet");
			return true;
		}
		
	}
	public void distanceChecker()
	{
		if ((distance > distance_MIN) && (distance < distance_MAX))
		{
			return; 
		}
		else
		{
			if (distance < distance_MIN)
			{
				adjDist = Math.abs(distance - distance_MIN);
				scooter.scootUp(adjDist);
				return;		
			}
			else
			{
				adjDist = Math.abs(distance_MAX - distance);
				scooter.scootBack(adjDist);
				return;
			}
		}
	}//end of Distance Checker
}