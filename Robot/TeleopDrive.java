package org.usfirst.frc.team5968.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class TeleopDrive{
	
	private Joystick leftStick;
	private Joystick rightStick;
	private Joystick altStick;
	
	private Victor leftMotor;
	private Victor rightMotor;
	private Encoder leftEncoder;
	private Encoder rightEncoder;
	
	private double leftFactor = 1;
	private double rightFactor = 1;
	
	private JoystickButton reverseControls;
	private JoystickButton resetControls;
	private JoystickButton toggleAltSteering;
	private JoystickButton humanShoot;
	private JoystickButton computerShoot;
	private JoystickButton toggleShootAngle;
	
	private boolean reversedControls = false;
	private boolean altControlsEnabled = false;
	
	private InitializeRobot robotComponents;
	private BallShoot shoot;
	private RobotDrive drive;
	
	public void driveInit(){
		
		robotComponents = InitializeRobot.GetInstance();
		
		leftStick = robotComponents.getLeftJoystick();
		rightStick = robotComponents.getRightJoystick();
		altStick = robotComponents.getAltJoystick();
		
		leftMotor = robotComponents.getLeftMotor();
		rightMotor = robotComponents.getRightMotor();
		
		leftEncoder = robotComponents.getLeftEncoder();
		rightEncoder = robotComponents.getRightEncoder();
		
		leftEncoder.reset();
		rightEncoder.reset();
		
		reverseControls = new JoystickButton(altStick, 1);
		resetControls = new JoystickButton(altStick, 1);
		toggleAltSteering = new JoystickButton(altStick, 3);
		humanShoot = new JoystickButton(altStick, 4);
		computerShoot = new JoystickButton(altStick, 5);
		toggleShootAngle = new JoystickButton(altStick, 6);
		
	}//end of method
	
	
	public void driveBase(){
		
		if(!altControlsEnabled){
			if(!reversedControls)
			{
			
				leftMotor.set(Math.pow((leftFactor * leftStick.getY()), 3));
				rightMotor.set(Math.pow((rightFactor * -1 * rightStick.getY()), 3));
		
			}
		
			if(reversedControls)
			{
			
				leftMotor.set(Math.pow((leftFactor * -1 * leftStick.getY()), 3));
				rightMotor.set(Math.pow((rightFactor * rightStick.getY()), 3));
			
			}
		}
		
		if(altControlsEnabled)
		{
			
			if(altStick.getDirectionRadians() <= Math.PI/2)
			{
				leftMotor.set(0);
				rightMotor.set(altStick.getY());
			}
			else if (altStick.getDirectionRadians() <= Math.PI)
			{
				rightMotor.set(0);
				leftMotor.set(altStick.getY());
			}
			else if (altStick.getDirectionRadians() <= (3*Math.PI)/2)
			{
				leftMotor.set(-altStick.getY());
				rightMotor.set(0);
			}
			else
			{
				leftMotor.set(0);
				rightMotor.set(-altStick.getY());
			}
			System.out.println(leftMotor.get() + " " + rightMotor.get());
		}
		
		if(reverseControls.get())
		{
			
			reversedControls = true;
			
		}
		
		if(resetControls.get())
		{
		
			reversedControls = false;
			
		}
		
		if(toggleAltSteering.get() && !altControlsEnabled)
		{
			
			altControlsEnabled = true;
			
		}
		
		if(toggleAltSteering.get() && altControlsEnabled)
		{
			
			altControlsEnabled = false;
			
		}
		
		
		if(altStick.getTrigger())
		{
			shoot.ballShootHuman();
		}
		
		if(computerShoot.get())
		{
			shoot.ballShootComputer(true); //change this for camera or no
		}
		if(toggleShootAngle.get())
		{
			shoot.platformAngle();
		}
		
	}//end of method
	
}
