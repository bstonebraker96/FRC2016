package org.usfirst.frc.team5968.robot;

public class DoNothing {
	
	public boolean doNothing(){
		
		try {
			Thread.sleep(15000);
			return true;
		} catch (InterruptedException e) {
			System.out.println("DON'T INTERRUPT MY NAP!!!");
		}
		return false;
	}
	
}
