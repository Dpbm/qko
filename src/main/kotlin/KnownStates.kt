import math.Complex
import math.HALF_PROB


val ZeroState:State = State(arrayOf( Complex(1.0, 0.0), Complex(0.0, 0.0)))
val OneState:State = State(arrayOf( Complex(0.0, 0.0), Complex(1.0, 0.0)))
val PlusState:State = State(arrayOf( Complex(HALF_PROB, 0.0), Complex(HALF_PROB, 0.0)))
val MinusState:State = State(arrayOf( Complex(HALF_PROB, 0.0), Complex(-HALF_PROB, 0.0)))
