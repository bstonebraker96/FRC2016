package org.usfirst.frc.team5968.robot;


import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;

public class InitializeRobot {
	
	private Victor leftMotor;
	private Victor rightMotor;
	private Victor leftMotor2;
	private Victor rightMotor2;
	
	private Talon feedMotor;
	private VictorSP leftShootMotor;
	private VictorSP rightShootMotor;
	
	private Encoder leftEncoder;
	private Encoder rightEncoder;
	
	private Joystick leftJoystick;
	private Joystick rightJoystick;
	private Joystick altJoystick;
	
	private Compressor ballPusher;
	
	private Gyro gyro;
	
	private int countsPerRevolution;
	
	InitializeRobot(){
		
		leftMotor = new Victor(1); //PWM 1
		rightMotor = new Victor(2); //PWM 2
		leftMotor2 = new Victor(4); // PWM
		rightMotor2 = new Victor(5); // PWM
		
		feedMotor = new Talon(3); //PWM 3
		leftShootMotor = new VictorSP(9); // PWM 9
		rightShootMotor = new VictorSP(10); // PWM 10
		
		leftEncoder = new Encoder(3,4,false,EncodingType.k1X); //Digital IN 3 4
		rightEncoder = new Encoder(5,6,false,EncodingType.k1X); //Digital IN 5 6
		leftEncoder.setSamplesToAverage(5);
		rightEncoder.setSamplesToAverage(5);
		
		leftJoystick = new Joystick(1); //Digital IN 1
		rightJoystick = new Joystick(2); //Digital IN 2
		altJoystick = new Joystick(7); // Digital IN 7
		
		ballPusher = new Compressor(8); //PWM 8
		
		gyro = new AnalogGyro(1); //Analog 1
		
		countsPerRevolution = 2048;
		
		
	}
	
	public Victor getLeftMotor(){
		return leftMotor;
	}
	public Victor getRightMotor(){
		return rightMotor;
	}
	public Victor getLeftMotor2(){
		return leftMotor2;
	}
	public Victor getRightMotor2(){
		return rightMotor2;
	}
	public Talon getFeedMotor(){
		return feedMotor;
	}
	public VictorSP getLeftShootMotor(){
		return leftShootMotor;
	}
	public VictorSP getRightShootMotor(){
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
