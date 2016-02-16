package org.usfirst.frc.team5968.robot;


import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;

public class InitializeRobot {
	
	
	private static InitializeRobot instance = null;
	
	private Victor leftMotor;
	private Victor rightMotor;
	private Talon feedMotor;
	private VictorSP leftShootMotor;
	private VictorSP rightShootMotor;
	
	private Encoder leftEncoder;
	private Encoder rightEncoder;
	private DigitalInput driveSwitch;
	private DigitalInput shootSwitch;
	private DigitalInput modeSwitch1;
	private DigitalInput modeSwitch2;
	private DigitalInput modeSwitch3;
	
	private Joystick leftJoystick;
	private Joystick rightJoystick;
	private Joystick altJoystick;
	
	private Compressor compressor;
	private Solenoid ballPusher;
	private Solenoid shootAngle;

	private Gyro gyro;
	
	private int countsPerRevolution;
	
	public static InitializeRobot GetInstance()
	{
		if (instance == null)
		{instance = new InitializeRobot();}
		return instance;
	}
	
	private InitializeRobot(){
		
		leftMotor = new Victor(0); //PWM 0
		rightMotor = new Victor(1); //PWM 1
		
		feedMotor = new Talon(2); //PWM 2
		leftShootMotor = new VictorSP(3); // PWM 3
		rightShootMotor = new VictorSP(4); // PWM 4
		
		leftEncoder = new Encoder(0,1,false,EncodingType.k1X); //Digital IN 0 1
		rightEncoder = new Encoder(2,3,false,EncodingType.k1X); //Digital IN 2 3
		driveSwitch = new DigitalInput(4); //Digital IN 4
		shootSwitch = new DigitalInput(5); //Digital IN 5
		modeSwitch1 = new DigitalInput(6); //Digital IN 6
		modeSwitch2 = new DigitalInput(7); //Digital IN 7
		modeSwitch3 = new DigitalInput(8); //Digital IN 8
		
		leftEncoder.setSamplesToAverage(5);
		rightEncoder.setSamplesToAverage(5);
		
		leftJoystick = new Joystick(0); //USB 0
		rightJoystick = new Joystick(1); //USB 1
		altJoystick = new Joystick(2); // USB 2
		
		compressor = new Compressor(0); //PCM 0
		ballPusher = new Solenoid(1); //PCM 1
		shootAngle = new Solenoid(2); //PCM 2
		
		gyro = new ADXRS450_Gyro(); //SPI
		
		countsPerRevolution = 512;
		
		
	}
	
	public Victor getLeftMotor(){
		return leftMotor;
	}
	public Victor getRightMotor(){
		return rightMotor;
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
	public DigitalInput getDriveSwitch(){
		return driveSwitch;
	}
	public DigitalInput getShootSwitch(){
		return shootSwitch;
	}
	public DigitalInput getModeSwitch1(){
		return modeSwitch1;
	}
	public DigitalInput getModeSwitch2(){
		return modeSwitch2;
	}
	public DigitalInput getModeSwitch3(){
		return modeSwitch3;
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
	public Compressor getCompressor(){
		return compressor;
	}
	public Solenoid getBallPusher(){
		return ballPusher;
	}
	public Solenoid getAngleSolenoid(){
		return shootAngle;
	}
	
	public int getCountsPerRevolution(){
		return countsPerRevolution;
	}
	public Gyro getGyro(){
		return gyro;
	}
}
