import math.Complex
import math.HALF_PROB


val ZERO_STATE:State = State(arrayOf( Complex(1.0, 0.0), Complex(0.0, 0.0)))
val ONE_STATE:State = State(arrayOf( Complex(0.0, 0.0), Complex(1.0, 0.0)))
val PLUS_STATE:State = State(arrayOf( Complex(HALF_PROB, 0.0), Complex(HALF_PROB, 0.0)))
val MINUS_STATE:State = State(arrayOf( Complex(HALF_PROB, 0.0), Complex(-HALF_PROB, 0.0)))
