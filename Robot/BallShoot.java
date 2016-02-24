package org.usfirst.frc.team5968.robot;

import edu.wpi.first.wpilibj.VictorSP;

public class BallShoot
{
	private VictorSP leftShootMotor;
	private VictorSP rightShootMotor;
	
	public BallShoot() 
	{		
		leftShootMotor = new VictorSP(PortMap.leftShootMotor);
		rightShootMotor = new VictorSP(PortMap.rightShootMotor);
	}
	
	
	//TODO set the timing of the shooting
	public void turnOnShooter() {
	
		leftShootMotor.set(-1);
		rightShootMotor.set(1);
	}
	public void turnOffShooter() {
		leftShootMotor.set(0);
		rightShootMotor.set(0);		
	}	
}