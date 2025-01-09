package gates

import Operator
import Qubit
import State
import math.Complex

class Z (override val qubits: Array<Qubit>) : Operator {
    override val name = "Z"

     override fun apply() {
        if(qubits.size != 1) {
            // TODO: LOG HERE
            return
        };

         val qubit:Qubit = qubits[0]
         val qubitState:State = qubit.getState();

         val realOneAmplitude:Double = qubitState.oneAmplitude.real;
         val imaginaryOneAmplitude:Double = qubitState.oneAmplitude.imaginary;

         qubitState.setAmplitudeOfOne(Complex(
             if(realOneAmplitude == 0.0) 0.0 else -1.0 * realOneAmplitude,
             if(imaginaryOneAmplitude == 0.0) 0.0 else -1.0 * imaginaryOneAmplitude
         ))

     }
}