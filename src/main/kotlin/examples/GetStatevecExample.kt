package examples

import Circuit
import CircuitState
import providers.Outcome
import providers.local.LocalBackend
import providers.local.gates.H
import providers.local.gates.CNOT

fun main(){
    val circuit = Circuit(3)
    circuit.addGate(H(arrayListOf(0)))
    circuit.addGate(CNOT(arrayListOf(0,1)))
    circuit.addGate(CNOT(arrayListOf(0,2)))

    println("Simple GHZ Example")
    println("---CIRCUIT---")
    println(circuit.getQasm())

    val backend = LocalBackend()
    val result:Outcome = backend.execute(circuit)

    println("---OUTPUT---")

    for((index,prob) in result.toDist().withIndex()){
        val binaryRep = index.toString(radix = 2).padStart(3, '0')
        println("$binaryRep $prob")
    }
}