package examples

import Circuit
import providers.local.gates.CNOT
import providers.local.gates.X
import providers.local.gates.H

fun main(){
    val circuit = Circuit(3)
    circuit.addGate(X(arrayListOf(0)))
    circuit.addGate(H(arrayListOf(1)))
    circuit.addGate(CNOT(arrayListOf(1,2)))

    println(circuit.getQasm())
}
