
package org.usfirst.frc.team5968.robot;

import edu.wpi.first.wpilibj.IterativeRobot;

public class Robot extends IterativeRobot {

	private PortMap robot;
	private BallShoot shoot;
	private Drive drive;
	private Uart pi;
	private AutoManager auto;
	private HumanInterface humanInterface;
	
	int mode;
	
    public void robotInit() {
    	
    	robot = new PortMap();
    	shoot = new BallShoot();
    	drive = new Drive();
    	pi = new Uart();
    	auto = new AutoManager();
    	humanInterface = new HumanInterface();
    	
    }
    
    public void autonomousInit() {}

   
    public void autonomousPeriodic() {
    	
    	auto.autonomousMain();
    	
    }
    
    public void teleopInit(){
    	
    	
    	
    }
    
    public void teleopPeriodic() {
    	
    	humanInterface.buttonControls();
    	
    	pi.checkEm("check em");
    	
    }
    
    
    public void testPeriodic() {
    
    }
    
}
