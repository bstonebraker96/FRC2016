// Despite the .cs extension on this file, it is actually pseudocode. I named it .cs for syntax highlighting purposes.

class DriveBase
{
    private MotorContorller motorLeft;
    private MotorController motorRight;
    private Encoder encoderLeft;
    private Encoder encoderRight;
    
    private enum AutomaticState
    {
        Idle,
        DrivingDistance,
        Turning,
        // And so on...
    }

    private AutomaticState state = AutomaticState.Idle;
    private double distanceGoal;
    private double turningGoal;

    public bool GetIsIdle()
    {
        return state == AutomaticState.Idle;
    }

    public void ForceIdle()
    {
        state = AutomaticState.Idle;
    }

    private void DriveRaw(double left, double right)
    {
        motorLeft.Set(left);
        motorRight.Set(-right);
    }

    public void DriveManual(double left, double right)
    {
        // Bail if the robot is performing an automatic action and should not be driven manually.
        // (In another implementation, you might instead force it to idle if the driver pushed far enough - this logic would likely be done at a higher level.)
        if (state != AutomaticState.Idle)
        { return; }

        DriveRaw(left, right);
    }

    public void DriveDistance(double distanceFeet)
    {
        distanceGoal = distanceFeet;
        encoderLeft.Reset();
        encoderRight.Reset();
        state = AutomaticState.DrivingDistance;
    }

    public void Turn(double angleDegrees)
    {
        //TODO:
        state = AuomaticState.Turning;
    }

    public void Update()
    {
        // Bail early if we are idle.
        if (state == AutomaticState.Idle)
        { return; }

        // Process driving a distance
        if (state == AutomaticState.DrivingDistance)
        {
            //TODO: This logic does not properly support negative distances (IE: driving backwards) but could be modified to do so.
            double distanceLeft = encoderLeft.GetDistance();
            double distanceRight = encoderRight.GetDistance();
            double distanceDiff = Math.Abs(distanceLeft - distanceRight);

            // If we went far enough
            if (distanceLeft >= distanceGoal && distanceRight >= distanceGoal)
            {
                state = AutomaticState.Idle;
                DriveRaw(0.0, 0.0);
                return;
            }

            // If we aren't there yet, we need to drive forward
            double leftSpeed = 1.0;
            double rightSpeed = 1.0;
            const double differenceThreshold = 0.5 / 12.0; // 0.5 inch

            // Once the difference passes some threshold, we need to correct it
            if (distanceDiff > differenceThreshold)
            {
                if (distanceLeft < distanceRight) // Basically, we slow down the motor that is "winning".
                { distanceRight -= 0.2; }
                else
                { distanceLeft -= 0.2; }
            }

            return;
        }

        // Process driving an angle
        if (state == AutomaticState.Turning)
        {
            //TODO: Add logic for turning using the gyro. (LPT: Encoders are usually not super reliable for turning on a drivebase like ours.)
            return;
        }

        // If we made it this far, we are in an invalid state
        System.err.println("INVALID STATE IN DriveBase!");
    }
}

class Robot
{
    private DriveBase driveBase;
    private Joystick leftJoystick;
    private Joystick rightJoystick;

    public void Robot()
    {
        driveBase = new DriveBase();
    }

    public void TeleopInit()
    {
        driveBase.ForceIdle(); // We always want to start modes in an idle state, otherwise the robot will continue what it was trying to accomplish during autonomous mode.
    }

    public void TeleopPeriodic()
    {
        // Process manual driving:
        driveBase.DriveManual(leftJoystick.GetY(), rightJoystick.GetY());

        // If the user presses button 2, we want to drive forward 2 feet
        if (leftJoystick.GetButtonPressed(2))
        {
            driveBase.DriveDistance(2.0);
        }

        // Process drive base logic:
        driveBase.Update();
    }

    private enum AutomodeState
    {
        Started,
        DrivingForward1,
        Turning,
        DrivingForward2,
        Done
    }

    private AutomodeState autoState = AutomodeState.Started;

    public void AutoInit()
    {
        // Make sure we are starting in a clean state
        autoState = AutomodeState.Started;
        driveBase.ForceIdle();
    }

    public void AutoPeriodic()
    {
        driveBase.Update(); // We want to update the driveBase first so that the next method call can react ot any changes.
        HandleAutonomousStateTransitions();
    }

    private void HandleAutonomousStateTransitions()
    {
        // This is a basic state machine that drives 3 feet, turns 90 degrees, and drives 6 inches
        if (state == AutomodeState.Started)
        {
            // The Started state instantly transfers to the DrivingForward1 state.
            driveBase.DriveDistance(3.0);
            state = AutomodeState.DrivingForward1;
        }
        else if (state == AutomodeState.DrivingForward1)
        {
            // Once we are idle, we move to the Turning state.
            if (driveBase.GetIsIdle())
            {
                driveBase.Turn(90.0);
                state = AutomodeState.Turning;
            }
        }
        else if (state == AutomodeState.Turning)
        {
            // Once we are idle, we move to the DrivingForward2 state.
            if (driveBase.GetIsIdle())
            {
                driveBase.DriveDistance(0.5);
                state = AutomodeState.DrivingForward2;
            }
        }
        else if (state == AutomodeState.DrivingForward2)
        {
            // Once we are idle, we move to the Done state. (Not strictly necessary, but it is nice to give the state a clear definition. Especially helps if you end up printing the current state for debugging purposes.)
            if (driveBase.GetIsIdle())
            {
                state = AutomodeState.Done;
            }
        }
        else if (state == AutomodeState.Done)
        {
            // Do nothing.
        }
        else
        {
            System.err.println("Invalid autonomous mode state reached!");
        }
    }
}
