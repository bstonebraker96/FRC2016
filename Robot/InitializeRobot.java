package org.usfirst.frc.team5968.robot;


import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;

public class InitializeRobot {
	
	private Talon leftMotor;
	private Talon rightMotor;
	
	private Victor feedMotor;
	private Victor leftShootMotor;
	private Victor rightShootMotor;
	
	private Encoder leftEncoder;
	private Encoder rightEncoder;
	
	private Joystick leftJoystick;
	private Joystick rightJoystick;
	private Joystick altJoystick;
	
	private Compressor ballPusher;
	
	private Gyro gyro = new AnalogGyro(1);
	
	private int countsPerRevolution;
	
	InitializeRobot(){
		
		leftMotor = new Talon(1);
		rightMotor = new Talon(2);
		
		feedMotor = new Victor(3);
		leftShootMotor = new Victor(9);
		rightShootMotor = new Victor(10);
		
		leftEncoder = new Encoder(3,4,false,EncodingType.k1X);
		rightEncoder = new Encoder(5,6,false,EncodingType.k1X);
		
		leftJoystick = new Joystick(1);
		rightJoystick = new Joystick(2);
		altJoystick = new Joystick(7);
		
		ballPusher = new Compressor(8);
		
		countsPerRevolution = 2048;
		
		
	}
	
	public Talon getLeftMotor(){
		return leftMotor;
	}
	public Talon getRightMotor(){
		return rightMotor;
	}
	public Victor getFeedMotor(){
		return feedMotor;
	}
	public Victor getLeftShootMotor(){
		return leftShootMotor;
	}
	public Victor getRightShootMotor(){
		return rightShootMotor;
	}
	public Encoder getLeftEncoder(){
		return leftEncoder;
	}
	public Encoder getRightEncoder(){
		return rightEncoder;
	}
	public Joystick getLeftJoystick(){
		return leftJoystick;
	}
	public Joystick getRightJoystick(){
		return rightJoystick;
	}
	public Joystick getAltJoystick(){
		return altJoystick;
	}
	public Compressor getBallPusher(){
		return ballPusher;
	}
	public int getCountsPerRevolution(){
		return countsPerRevolution;
	}
	public Gyro getGyro(){
		return gyro;
	}
}
