package org.usfirst.frc.team5968.robot;

import edu.wpi.first.wpilibj.Joystick;

public class HumanInterface {
	private Joystick leftStick;
	private Joystick rightStick;
	private Joystick altStick;
	
	private Drive drive;
	private BallFeed feed;
	private Pneumatics piston;
	private BallShoot shoot;
	
	private boolean reverseControls;
	private boolean manualShoot;
	private boolean shootPlatformRaised;	
	private boolean altControlsEnabled;
	private boolean ballFeedFast;
	private boolean ballFeedSlow;
	private boolean shootBall;
	
	private boolean instanceChecker = false;
	private boolean altControlChecker;
	private boolean reverseControlChecker;
	private boolean manualShootChecker;
	private boolean angleChecker;
	private boolean ballFeedFastChecker;
	private boolean ballFeedSlowChecker;
	
	public HumanInterface(){
		
		if(!instanceChecker)
		{
			leftStick = new Joystick(PortMap.leftJoystick);
			rightStick = new Joystick(PortMap.rightJoystick);
			altStick = new Joystick(PortMap.altJoystick);
			
			drive = new Drive();
			feed = new BallFeed();
			piston = new Pneumatics();
			shoot = new BallShoot();
			//REVERSE IS 7 LEFT
			//ALT IS 8 ALT
			//MANUAL SHOOT IS ALT 3
			//PNEUMATICS IS ALT 5
			instanceChecker = true;
		}
	}
	public void getAllButtons() {
		altControlChecker = altStick.getRawButton(8);
		reverseControlChecker = leftStick.getRawButton(7);
		manualShootChecker = altStick.getRawButton(3);
		angleChecker = altStick.getRawButton(5);
		ballFeedFastChecker = altStick.getRawButton(10);
		ballFeedSlowChecker = altStick.getRawButton(11);
		shootBall = altStick.getRawButton(4);
		
	}
	
	public enum BallFeedStates {
		FAST, SLOW, STOPPED
	}
	
	public void buttonControls(){
		getAllButtons();
		if(altControlChecker)
		{
			if(altControlsEnabled)
			{
				altControlsEnabled = false;
			}
			else
			{
				altControlsEnabled = true;
			}
		}
		if(reverseControlChecker)
		{
			if(reverseControls)
			{
				reverseControls =  false;
			}
			else
			{
				reverseControls = true;
			}
		}
		if(manualShootChecker)
		{
			if(manualShoot)
			{
				manualShoot = false;
			}
			else
			{
				manualShoot = true;
			}
		}
		if(shootBall)
		{
			shoot.turnOnShooter();
		}
		else 
		{
			shoot.turnOffShooter();
		}
		if(angleChecker)
		{
			piston.togglePlatformAngle();
		}
		
		if(ballFeedFastChecker)
		{
			feed.ballFeed(BallFeedStates.FAST);	
		}
		else if (ballFeedSlowChecker)
		{
			feed.ballFeed(BallFeedStates.SLOW);
		}
		else
		{
			feed.ballFeed(BallFeedStates.STOPPED);
		}
		
	}//end of method
	
	public void joystickControls() {
		if(altControlsEnabled)
		{
			
			/*if(altStick.getDirectionRadians() <= Math.PI/2)
			{
				drive.humanDrive(0, altStick.getY());	
			}
			else if (altStick.getDirectionRadians() <= Math.PI)
			{
				drive.humanDrive(altStick.getY(), 0);
			}
			else if (altStick.getDirectionRadians() <= (3*Math.PI)/2)
			{
				drive.humanDrive(-1 * altStick.getY(), 0);
			}
			else
			{
				drive.humanDrive(0, -1 * altStick.getY());
			}*/
			
			if(altStick.getX() > 0)
			{
				drive.humanDrive(altStick.getY() - altStick.getX(), altStick.getY());
			}
			
			if(altStick.getX() < 0)
			{
				drive.humanDrive(altStick.getY(), altStick.getY() - altStick.getX());
			}
			
			if(altStick.getX() == 0)
			{
				drive.humanDrive(altStick.getY(), altStick.getY());
			}
		}
		
		if(!altControlsEnabled)
		{
			if(reverseControls)
			{
				drive.humanDrive(-1 * leftStick.getY(), rightStick.getY());
			}
			if(!reverseControls)
			{
				drive.humanDrive(leftStick.getY(), -1 * rightStick.getY());
			}
		}
	}

}
