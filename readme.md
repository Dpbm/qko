# QKO
## A quantum library for Kotlin

![build and test workflow](https://github.com/Dpbm/qko/actions/workflows/build-test.yml/badge.svg)
![release and publish workflow](https://github.com/Dpbm/qko/actions/workflows/release-publish.yml/badge.svg)
![test publish workflow](https://github.com/Dpbm/qko/actions/workflows/publish-test-action.yml/badge.svg)

Qko is an open source library for creating quantum circuits and executing them easily.

Its usage is inspired by `Qiskit` and `Cirq`. So creating your own circuit must be easy as using one of the mainstream frameworks.

## Install

First of all, create a new `Gradle + Kotlin` project.

```bash
mkdir new_project
cd new_project
gradle init
```

Then add to your `build.gradle.kts`:

```kotlin
dependencies {
    implementation("io.github.dpbm:qko:1.0")
}

```

After that you're free to start creating your circuits.

## Create circuits

For instance, to Create a Bell State, just run:

```kotlin
package examples

import Circuit
import providers.Outcome
import providers.local.LocalBackend
import providers.local.gates.H
import providers.local.gates.CNOT

fun main(){
    val circuit = Circuit(2) //circuit with 2 qubits
    circuit.addGate(H(arrayListOf(0))) // add an H gate on qubit 0 
    circuit.addGate(CNOT(arrayListOf(0,1))) // add a CNOT between qubits 1 and 0

    val backend = LocalBackend()
    val result:Outcome = backend.execute(circuit) // execute the job

    println("---OUTPUT---")

    for((index,prob) in result.toDist().withIndex()){ // map through your probabilities distribution
        val binaryRep = index.toString(radix = 2).padStart(2, '0')
        println("$binaryRep $prob")
    }
}
```

In this framework, we've chosen to follow the big-endian pattern, so the less significant qubit is the first from the left to the right ($q_{0}q_{1}q_{3}\ldots q_{n-1}$). This choice implies on the final bitstrings sequence, for example, applying $X(q_{0})$ changes $00$ to $10$ instead of $01$. For some people, it may seem as a problem, but in practice it's easy to remap to little-endian just flipping the sequence and its easier to think during the gates implementation. Also, in the near future, a method will be added to apply the remapping with ease.
