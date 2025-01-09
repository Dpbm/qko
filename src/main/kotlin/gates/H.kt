package gates

import Operator
import Qubit
import State
import math.Complex
import math.HALF_PROB
import math.notMinusZero

class H (override val qubits: Array<Qubit>) : Operator {
    override val name = "H"

    override fun apply(){
        if(qubits.size != 1) {
            // TODO: LOG HERE
            return
        };

        val qubit:Qubit = qubits[0]
        val qubitState:State = qubit.getState()

        if(qubitState.isZero()){
            qubitState.changeToPlus()
        }else if(qubitState.isOne()){
            qubitState.changeToMinus()
        }else if(qubitState.isPlus()){
            qubitState.changeToZero()
        }else if(qubitState.isMinus()){
            qubitState.changeToOne()
        }else{
            // the first four if statements ensure a more easy and quick way to update the amplitudes
            // however, after applying many operators in the circuit, it may be in a state that we don't know
            // due to that, we need to calculate the new amplitudes of the state
            // once we're focusing on single qubit, after applying a Hadamard gate to it the state will change to
            // α/√2 (|0⟩ + |1⟩) +  β/√2 (|0⟩ - |1⟩)
            // this way, the new amplitudes for |0⟩ and |1⟩ are respectively (α/√2 + β/√2) and (α/√2 - β/√2)

            val newZeroAmplitudeReal:Double = notMinusZero( HALF_PROB * (qubitState.zeroAmplitude.real + qubitState.oneAmplitude.real))
            val newZeroAmplitudeImaginary:Double = notMinusZero( HALF_PROB * (qubitState.zeroAmplitude.imaginary + qubitState.oneAmplitude.imaginary))

            val newOneAmplitudeReal:Double = notMinusZero( HALF_PROB * (qubitState.zeroAmplitude.real - qubitState.oneAmplitude.real))
            val newOneAmplitudeImaginary:Double = notMinusZero( HALF_PROB * (qubitState.zeroAmplitude.imaginary - qubitState.oneAmplitude.imaginary))

            qubitState.setAmplitudeOfZero(Complex(newZeroAmplitudeReal, newZeroAmplitudeImaginary))
            qubitState.setAmplitudeOfOne(Complex(newOneAmplitudeReal, newOneAmplitudeImaginary))
        }
    }
}