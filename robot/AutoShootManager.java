package org.usfirst.frc.team5968.robot;

import org.usfirst.frc.team5968.robot.HumanInterface.BallFeedStates;

public class AutoShootManager {
	private final double angle_MAX = 0;
	private final double angle_MIN = 0;
	private final double distance_MIN = 0;
	private final double distance_MAX = 0;
	private double adjDist;
	//private Uart uart;
	private Drive drive;
	private BallShoot shoot;
	private BallFeed feeder;
	
	private int shootState = 0;
	private int waitTime = 0;
	
	public AutoShootManager(Drive drive, BallShoot shoot) {
		this.drive = drive;
		this.shoot = shoot;
		
		//uart = new Uart();
	}
	
	public boolean ballShootComputer() 
	{
		
		if(shootState == 0){
			shoot.turnOnShooter();
			shootState = 1;
		}
		
		if(shootState == 1){
			waitTime +=20;
			
			if(waitTime >= 260){
				shootState = 2;
				waitTime = 0;
			}
		}
		if(shootState == 2){
			feeder.ballFeed(BallFeedStates.FAST);
			shootState = 3;
		}
		if(shootState == 3){
			waitTime += 20;
			
			if(waitTime >= 1000){
				shoot.turnOffShooter();
				feeder.ballFeed(BallFeedStates.STOPPED);
				waitTime = 0;
				return true;
			}
		}
		return false;
	}//end of another method
	/*public boolean angleChecker()
	{
		if ((uart.angle < angle_MAX) && (uart.angle > angle_MIN))
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
		if ((PortMap.distance > distance_MIN) && (PortMap.distance < distance_MAX))
		{
			return; 
		}
		else
		{
			if (PortMap.distance < distance_MIN)
			{
				adjDist = Math.abs(PortMap.distance - distance_MIN);
				drive.scootUp(adjDist);
				return;		
			}
			else
			{
				drive.scootBack(adjDist);
				return;
			}
		}
	}//end of Distance Checker*/
	

}
