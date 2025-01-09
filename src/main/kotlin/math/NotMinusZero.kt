package math

fun notMinusZero(value:Double) : Double{
    return if(value == -0.0) 0.0 else value
}