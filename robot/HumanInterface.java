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
	
	private boolean altControlChecker;
	private boolean reverseControlChecker;
	private boolean manualShootChecker;
	private boolean angleChecker;
	private boolean ballFeedFastChecker;
	private boolean ballFeedSlowChecker;
	private boolean runBallShooter;
	
	public HumanInterface(Drive drive, BallShoot shoot){
		
		this.drive = drive;
		this.shoot = shoot;
			
		leftStick = new Joystick(PortMap.leftJoystick);
		rightStick = new Joystick(PortMap.rightJoystick);
		altStick = new Joystick(PortMap.altJoystick);
		feed = new BallFeed();
		piston = new Pneumatics();
		//REVERSE IS 7 LEFT
		//ALT IS 8 ALT
		//MANUAL SHOOT IS ALT 3
		//PNEUMATICS IS ALT 5
	}
	public void getAllButtons() {
		altControlChecker = altStick.getRawButton(8);
		reverseControlChecker = leftStick.getRawButton(7);
		manualShootChecker = altStick.getRawButton(3);
		angleChecker = altStick.getRawButton(5);
		ballFeedFastChecker = altStick.getRawButton(10);
		ballFeedSlowChecker = altStick.getRawButton(11);
		runBallShooter = altStick.getRawButton(4);
		shootPlatformRaised = altStick.getRawButton(5);
		
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
		
		if(runBallShooter)
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
		if(shootPlatformRaised)
		{
			piston.togglePlatformAngle();
		}
		
	}//end of method
	
	public void joystickControls() {
		if(altControlsEnabled)
		{
			
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
	}//end of method
	

}
