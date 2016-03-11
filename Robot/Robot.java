
package org.usfirst.frc.team5968.robot;

import edu.wpi.first.wpilibj.IterativeRobot;

public class Robot extends IterativeRobot {

	private Drive drive;
	private AutoManager auto;
	private AutoShootManager autoShootManager;
	private HumanInterface humanInterface;
	private BallShoot shoot;
	private SmartDash dash;
    
    public void robotInit() {
    	
    	drive = new Drive();
    	shoot = new BallShoot();
    	autoShootManager = new AutoShootManager(drive, shoot);
    	auto = new AutoManager(autoShootManager, drive);
    	humanInterface = new HumanInterface(drive, shoot, autoShootManager);
    	dash = new SmartDash(drive, humanInterface);
    	
    }
    
    public void autonomousInit() {
    	
    }

   
    public void autonomousPeriodic() {
    	
    	auto.autonomousMain();
    	dash.writeToDash();
    	
    }
    
    public void teleopInit(){
    	
    }
    
    public void teleopPeriodic() {

    	humanInterface.buttonControls();
    	humanInterface.joystickControls();
    	dash.writeToDash();
        
    }
    
    public void testPeriodic() {
    
        humanInterface.buttonControls();
    	humanInterface.joystickControls();
        
    }
}
