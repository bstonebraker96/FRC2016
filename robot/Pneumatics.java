package org.usfirst.frc.team5968.robot;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Compressor;

public class Pneumatics {
	private Compressor compressor;
	private Solenoid shootAngle;
	
	public Pneumatics() {
		compressor = new Compressor(PortMap.compressor);
		shootAngle = new Solenoid(PortMap.shootAngle);
		compressor.setClosedLoopControl(true);
		
	}
	
	public void togglePlatformAngle()
	{
		if(shootAngle.get())
		{
			shootAngle.set(false);
		}
		if(!shootAngle.get())
		{
			shootAngle.set(true);
		}
	}//end of platformAngle
}
