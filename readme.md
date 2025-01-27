# QKO
## A quantum library for Kotlin

![build and test workflow](https://github.com/Dpbm/qko/actions/workflows/build-test.yml/badge.svg)
![publish workflow](https://github.com/Dpbm/qko/actions/workflows/release-publish.yml/badge.svg)

Qko is an open source library for creating quantum circuits and executing them easily.

Its usage is inspired by `Qiskit` and `Cirq`. So creating your own circuit must be easy as using one of the mainstream frameworks.

To Create a Bell State for example just run:

```kotlin
package examples

import Circuit
import providers.Outcome
import providers.local.LocalBackend
import providers.local.gates.H
import providers.local.gates.CNOT

fun main(){
    val circuit = Circuit(2) //circuit with 2 qubits
    circuit.addGate(H(arrayListOf(0)))
    circuit.addGate(CNOT(arrayListOf(0,1)))

    val backend = LocalBackend()
    val result:Outcome = backend.execute(circuit)

    println("---OUTPUT---")

    for((index,prob) in result.toDist().withIndex()){
        val binaryRep = index.toString(radix = 2).padStart(2, '0')
        println("$binaryRep $prob")
    }
}
```