see brains/GBStackBrain.java function executeHardwareWrite for list of writable vs. read-only variables.

see brains/GBStackBrain.java function executeHardwareWrite for variables to write to control constructor

see simulation/GBConstructorState.java for how constructor actually works. The max power of a constructor is how much energe it can put into the baby at one frame (energy/unit time).

Maybe in debugging phase, see what happens if you change type of constructor in the middle of construction.

(0, 0) is bottom left corner


; Learn seek-engine primitive here:
seek-location: ;x y
  position v- dup
  norm radius < if  ; radius is radius of robot
    0 engine-power!
    2drop
  else
    engine-max-power engine-power!
    0.08 vs* rect-to-polar engine-velocity! ;code for difference b/t engine-power and engine-velocity
  then return
; learn how friction and movement work in moveallobjects()
; learn how local memory reads and writes work
; mess around with constructor variable writes explicitly to see how it works frame by frame
; learn how to move around, learn how to sense food
; learn how to eat food, reproduce, outgrow inert in the presence of food
