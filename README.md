Note - all encoders are tentative - some can be removed (i.e. vertical aim)
* Autonomous
  * Drive Base
    * Six wheels
      * Two DC PWM CIM motors for each side
      * Two joysticks - motors mapped to Y
      * Encoders - speed control and distance
  * Arm (Theoretically)
    * One DC PWM motor
    * Encoder
  * Ball shooter
    * Vertical angle aim
      * Some sort of motor
    * Screw shaft to lift platform
      * Encoder
    * Horizontal angle aim
      * Encoder/potentiometer
      * Drive base motors
    * Shooting wheels
      * Two DC PWM motors
        * Start when shooting sequence begins
        * Encoders
    * Camera
    * Multi-position switch - change auto modes
      * Some other auto modes that cross a defense and shoot in the high goal
      * Reach defense
      * Do nothing
* Teleop 
  * Drive Base
    * Six wheels
      * Two DC PWM CIM motors for each side
      * Drive
      * Encoders - speed control and distance
  * Ball Collection - Human controlled
    * One DC PWM CIM motor
      * Start on ball collection sequence
      * Pneumatic piston - raise angle
  * Ball Shooter
    * Auto
    * Vertical angle aim
      * Some sort of motor
        * Screw shaft to lift platform
        * Encoder
    * Horizontal angle aim
      * Drive base motors
    * Shooting wheels
      * Two DC PWM motors
        * Start when shooting sequence begins
        * Encoders
    * Human override - button on Ben’s joystick
      * Shooting wheels - start with button on Ben’s joystick
        * Two DC PWM motors
        * Encoders
      * Horizontal aim
        * Drive base motors
        * Mapped to x axis on Ben’s joystick
      * Vertical aim
        * Some sort of motor & screw system
        * Mapped to y axis on Ben’s joystick
    * Camera
  * Arm (Theoretically)
    * One DC PWM CIM motor
    * Encoder

