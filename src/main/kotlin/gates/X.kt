package gates

import Operator
import Qubit

class X (override val qubits: Array<Qubit>) : Operator {
    override val name = "X"

     override fun apply() {
        if(qubits.size != 1) {
            // TODO: LOG HERE
            return
        };

        val qubit:Qubit = qubits[0]
        qubit.swapAmplitudes()
     }
}