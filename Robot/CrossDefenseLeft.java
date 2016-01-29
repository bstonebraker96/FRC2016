package org.usfirst.frc.team5968.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;

public class CrossDefenseLeft {
	
	private double leftDistance;
	private double rightDistance;
	
	private long nanotime;
	private long nanotimeOld;
	private long timeAccumulative;
	
	private PortReference ref;
	private AutoDrive ad;
	
	public void init(){
		
		nanotimeOld = System.nanoTime();
		
		ref = new PortReference();
		ad = new AutoDrive();
		
		
		ad.getLeftMotor().set(0.25);
		ad.getRightMotor().set(0.25);
		
	}
	
	public void crossDefenseLeft(){
		
		nanotime = System.nanoTime();
		timeAccumulative = System.nanoTime();
		
		leftDistance = ad.getLeftEncoder().get() * ref.getCountsPerRevolution() * 7.65 * Math.PI;
		rightDistance = ad.getRightEncoder().get() * ref.getCountsPerRevolution() * 7.65 * Math.PI;
		
		if(leftDistance != rightDistance) ad.fixDirection(nanotime, nanotimeOld, false);
		
		
		
	}
}
