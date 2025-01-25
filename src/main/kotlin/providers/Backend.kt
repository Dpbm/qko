package providers

import Circuit
import CircuitState

interface Backend {
    fun execute(circuit:Circuit) : Outcome
}