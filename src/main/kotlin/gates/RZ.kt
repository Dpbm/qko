package gates

import Operator
import Qubit
import State
import math.Complex
import math.notMinusZero
import kotlin.math.cos
import kotlin.math.sin

class RZ (override val qubits: Array<Qubit>, private val lambda: Double) : Operator {
    override val name = "RZ"

     override fun apply() {
        if(qubits.size != 1) {
            // TODO: LOG HERE
            return
        };

         if(lambda == 0.0){
             // if lambda is zero, the whole matrix will be the same as Identity
             return
         }

         val qubit:Qubit = qubits[0]
         val qubitState:State = qubit.getState();

         // the 'x' value for Euler's formula e^(ix) = cos(x) + isin(x)
         // see https://en.wikipedia.org/wiki/Euler%27s_formula for more details

         // the -1 or 1 multiplication factor is due the components of RZ matrix
         // [ e^(-λ/2)  0       ]
         // [ 0         e^(λ/2) ]
         // if the current state is |0⟩ the raised vector will be
         // [ e^(-λ/2) ]
         // [ 0        ]
         // so x has a -1 factor
         val xForZero : Double = -1 * (lambda/2)
         val xForOne : Double = lambda/2

         if(qubitState.isZero()){
             // Euler's formula can be converted from e^(ix) to cos(x) + isin(x), this way is simple to get the real and imaginary parts
             qubitState.setAmplitudeOfZero(Complex(notMinusZero(cos(xForZero)), notMinusZero(sin(xForZero))))
             qubitState.setAmplitudeOfOne(Complex(0.0, 0.0))
         }else if(qubitState.isOne()){
             qubitState.setAmplitudeOfZero(Complex(0.0, 0.0))
             qubitState.setAmplitudeOfOne(Complex(notMinusZero(cos(xForOne)), notMinusZero(sin(xForOne))))
         }else{

             // The logic here is the same as implemented for RY and RX
             // however, we now have less factors, once the secondary diagonal is 0
             // these calculations done below can be seen in the following example:
             // [ e^(-λ/2)  0       ] [ 1/√2 + i ] --> [ e^(-λ/2)(1/√2 + i) ] --> [ (cos(-λ/2) + isin(-λ/2))(1/√2 + i) ]
             // [ 0         e^(λ/2) ] [ 1/√2 - i ]     [ e^(λ/2)(1/√2 - i)  ]     [ (cos(λ/2) + isin(λ/2))(1/√2 - i)   ]

             val newZeroAmplitudeReal:Double = notMinusZero(qubitState.zeroAmplitude.real * cos(xForZero)
                     - qubitState.zeroAmplitude.imaginary * sin(xForZero))
             val newZeroAmplitudeImaginary:Double =  notMinusZero(qubitState.zeroAmplitude.real * sin(xForZero)
                     + qubitState.zeroAmplitude.imaginary * cos(xForZero))

             val newOneAmplitudeReal:Double = notMinusZero(qubitState.oneAmplitude.real * cos(xForOne)
                     - qubitState.oneAmplitude.imaginary * sin(xForOne))
             val newOneAmplitudeImaginary:Double = notMinusZero(qubitState.oneAmplitude.real * sin(xForOne)
                     + qubitState.oneAmplitude.imaginary * cos(xForOne))

             qubitState.setAmplitudeOfZero(Complex(newZeroAmplitudeReal, newZeroAmplitudeImaginary))
             qubitState.setAmplitudeOfOne(Complex(newOneAmplitudeReal, newOneAmplitudeImaginary))
         }

     }
}