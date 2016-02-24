package org.usfirst.frc.team5968.robot;

public class AutoShootManager {
	private final double angle_MAX = 0;
	private final double angle_MIN = 0;
	private final double distance_MIN = 0;
	private final double distance_MAX = 0;
	private double adjDist;
	//private Uart uart;
	private Drive drive;
	private BallShoot shoot;
	
	public AutoShootManager(Drive drive, BallShoot shoot) {
		this.drive = drive;
		this.shoot = shoot;
		
		//uart = new Uart();
	}
	
	/*public void ballShootComputer() 
	{
		
		if(uart.checkEm())
		{
			uart.aquireTarget();;
			if (!angleChecker())
			{
				System.out.print("She won't make it captain! Try again!");
			}
			else
			{
				distanceChecker();
				//ballShootHuman();
			}
			
				
		}
		
		else
		{
			//ballShootHuman();
		}
		
	}//end of another method
	public boolean angleChecker()
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
