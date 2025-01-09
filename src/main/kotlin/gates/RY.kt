package gates

import Operator
import Qubit
import State
import math.Complex
import math.notMinusZero
import kotlin.math.cos
import kotlin.math.sin

class RY (override val qubits: Array<Qubit>, private val theta: Double) : Operator {
    override val name = "RY"

     override fun apply() {
        if(qubits.size != 1) {
            // TODO: LOG HERE
            return
        };

         val qubit:Qubit = qubits[0]
         val qubitState:State = qubit.getState();

         if(qubitState.isZero()){
             qubitState.setAmplitudeOfZero(Complex(cos(this.theta/2.0), 0.0))
             qubitState.setAmplitudeOfOne(Complex(sin(this.theta/2.0), 0.0))
         }else if(qubitState.isOne()){
             qubitState.setAmplitudeOfZero(Complex(-1.0 * sin(this.theta/2.0), 0.0))
             qubitState.setAmplitudeOfOne(Complex(cos(this.theta/2.0), 0.0))
         }else{

             // Once the operator is easy to map for |0⟩ and |1⟩, we have separated statements for it
             // but for unknown states, it's required to do the calculation below
             // example:
             // [ cos(θ/2) -sin(θ/2) ] [ 1/√2 + i ]  --> [ (1/√2 + i)cos(θ/2) - (1/√2 - i)sin(θ/2) ]
             // [ sin(θ/2)  cos(θ/2) ] [ 1/√2 - i ]      [ (1/√2 + i)sin(θ/2) + (1/√2 - i)cos(θ/2) ]

             val newZeroAmplitudeReal:Double = notMinusZero( qubitState.zeroAmplitude.real * cos(theta/2) -
                     qubitState.oneAmplitude.real * sin(theta/2) )
             val newZeroAmplitudeImaginary:Double = notMinusZero( qubitState.zeroAmplitude.imaginary * cos(theta/2) -
                     qubitState.oneAmplitude.imaginary * sin(theta/2) )

             val newOneAmplitudeReal:Double = notMinusZero( qubitState.zeroAmplitude.real * sin(theta/2) +
                     qubitState.oneAmplitude.real * cos(theta/2))
             val newOneAmplitudeImaginary:Double = notMinusZero( qubitState.zeroAmplitude.imaginary * sin(theta/2) +
                     qubitState.oneAmplitude.imaginary * cos(theta/2) )

             qubitState.setAmplitudeOfZero(Complex(newZeroAmplitudeReal, newZeroAmplitudeImaginary))
             qubitState.setAmplitudeOfOne(Complex(newOneAmplitudeReal, newOneAmplitudeImaginary))
         }

     }
}