package providers

import Circuit

interface Backend {
    fun execute(circuit:Circuit) : Outcome
}