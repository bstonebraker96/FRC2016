package org.usfirst.frc.team5968.robot;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Compressor;

public class Pneumatics {
	private Compressor compressor;
	private DoubleSolenoid shootAngle;
	
	public Pneumatics() {
		compressor = new Compressor(PortMap.compressor);
		shootAngle = new DoubleSolenoid(PortMap.shootAngle, PortMap.shootAngle2);
		compressor.setClosedLoopControl(true);
		
	}
	
	public void togglePlatformAngle()
	{ 
		if(shootAngle.get() == DoubleSolenoid.Value.kForward)
		{
			shootAngle.set(DoubleSolenoid.Value.kReverse);
		}
		if(shootAngle.get() != DoubleSolenoid.Value.kForward)
		{
			shootAngle.set(DoubleSolenoid.Value.kForward);
		}
	}//end of platformAngle
}
