import gates.*
import math.HALF_PROB
import math.notMinusZero

import kotlin.test.Test
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.math.PI
import kotlin.math.round

class CircuitTest {

    @Test
    fun testCircuitInitializationOneQubit(){
        val circuit:Circuit = Circuit(1)

        assertEquals(circuit.getTotalQubits(), 1)

        val qubits: Array<Qubit> = circuit.getQubits()
        assertEquals(qubits.size, 1)

        val qubitState: State = qubits[0].getState()
        assertEquals(qubitState.zeroAmplitude.real, 1.0)
        assertEquals(qubitState.zeroAmplitude.imaginary, 0.0)

        assertEquals(qubitState.oneAmplitude.real, 0.0)
        assertEquals(qubitState.oneAmplitude.imaginary, 0.0)
    }

    @Test
    fun testCircuitInitializationThreeQubits(){
        val circuit:Circuit = Circuit(3)

        assertEquals(circuit.getTotalQubits(), 3)

        val qubits: Array<Qubit> = circuit.getQubits();
        assertEquals(qubits.size, 3)

        for(qubit in qubits){
            val qubitState: State = qubit.getState()
            assertEquals(qubitState.zeroAmplitude.real, 1.0)
            assertEquals(qubitState.zeroAmplitude.imaginary, 0.0)

            assertEquals(qubitState.oneAmplitude.real, 0.0)
            assertEquals(qubitState.oneAmplitude.imaginary, 0.0)
        }
    }

    @Test
    fun testCircuitInitializationOneQubitWithDifferentInitialState(){
        val circuit:Circuit = Circuit(1, PLUS_STATE)

        val qubits: Array<Qubit> = circuit.getQubits()
        val qubitState: State = qubits[0].getState()
        assertEquals(qubitState.zeroAmplitude.real, HALF_PROB)
        assertEquals(qubitState.zeroAmplitude.imaginary, 0.0)

        assertEquals(qubitState.oneAmplitude.real, HALF_PROB)
        assertEquals(qubitState.oneAmplitude.imaginary, 0.0)
    }


    @Test
    fun testAddXGateZeroState(){
        val circuit:Circuit = Circuit(1, ZERO_STATE)

        val qubits:Array<Qubit> = circuit.getQubits()

        circuit.addGate(X(qubits))
        circuit.runCircuit()


        val qubit:Qubit = qubits[0]
        val qubitState: State = qubit.getState()

        assertEquals(qubitState.zeroAmplitude.real, 0.0)
        assertEquals(qubitState.zeroAmplitude.imaginary, 0.0)

        assertEquals(qubitState.oneAmplitude.real, 1.0)
        assertEquals(qubitState.oneAmplitude.imaginary, 0.0)
    }

    @Test
    fun testAddXGateOneState(){
        val circuit:Circuit = Circuit(1, ONE_STATE)

        val qubits:Array<Qubit> = circuit.getQubits()

        circuit.addGate(X(qubits))
        circuit.runCircuit()

        val qubit = qubits[0]
        val qubitState: State = qubit.getState()

        assertEquals(qubitState.zeroAmplitude.real, 1.0)
        assertEquals(qubitState.zeroAmplitude.imaginary, 0.0)

        assertEquals(qubitState.oneAmplitude.real, 0.0)
        assertEquals(qubitState.oneAmplitude.imaginary, 0.0)
    }

    @Test
    fun testAddXGatePlusState(){
        val circuit:Circuit = Circuit(1, PLUS_STATE)

        val qubits:Array<Qubit> = circuit.getQubits()

        circuit.addGate(X(qubits))
        circuit.runCircuit()

        val qubit = qubits[0]
        val qubitState: State = qubit.getState()

        assertEquals(qubitState.zeroAmplitude.real, HALF_PROB)
        assertEquals(qubitState.zeroAmplitude.imaginary, 0.0)

        assertEquals(qubitState.oneAmplitude.real, HALF_PROB)
        assertEquals(qubitState.oneAmplitude.imaginary, 0.0)
    }

    @Test
    fun testAddXGateMinusState(){
        val circuit:Circuit = Circuit(1, MINUS_STATE)

        val qubits:Array<Qubit> = circuit.getQubits()

        circuit.addGate(X(qubits))
        circuit.runCircuit()

        val qubit = qubits[0]
        val qubitState: State = qubit.getState()

        assertEquals(qubitState.zeroAmplitude.real, -HALF_PROB)
        assertEquals(qubitState.zeroAmplitude.imaginary, 0.0)

        assertEquals(qubitState.oneAmplitude.real, HALF_PROB)
        assertEquals(qubitState.oneAmplitude.imaginary, 0.0)
    }

    @Test
    fun testAddHGateZeroState(){
        val circuit:Circuit = Circuit(1)

        val qubits:Array<Qubit> = circuit.getQubits()

        circuit.addGate(H(qubits))
        circuit.runCircuit()

        val qubit = qubits[0]
        val qubitState: State = qubit.getState()

        assertEquals(qubitState.zeroAmplitude.real, HALF_PROB)
        assertEquals(qubitState.zeroAmplitude.imaginary, 0.0)

        assertEquals(qubitState.oneAmplitude.real, HALF_PROB)
        assertEquals(qubitState.oneAmplitude.imaginary, 0.0)
    }

    @Test
    fun testAddHGateOneState(){
        val circuit:Circuit = Circuit(1, ONE_STATE)

        val qubits:Array<Qubit> = circuit.getQubits()

        circuit.addGate(H(qubits))
        circuit.runCircuit()

        val qubit = qubits[0]
        val qubitState: State = qubit.getState()

        assertEquals(qubitState.zeroAmplitude.real, HALF_PROB)
        assertEquals(qubitState.zeroAmplitude.imaginary, 0.0)

        assertEquals(qubitState.oneAmplitude.real, -HALF_PROB)
        assertEquals(qubitState.oneAmplitude.imaginary, 0.0)
    }

    @Test
    fun testAddHGatePlusState(){
        val circuit:Circuit = Circuit(1, PLUS_STATE)

        val qubits:Array<Qubit> = circuit.getQubits()

        circuit.addGate(H(qubits))
        circuit.runCircuit()

        val qubit = qubits[0]
        val qubitState: State = qubit.getState()

        assertEquals(qubitState.zeroAmplitude.real, 1.0)
        assertEquals(qubitState.zeroAmplitude.imaginary, 0.0)

        assertEquals(qubitState.oneAmplitude.real, 0.0)
        assertEquals(qubitState.oneAmplitude.imaginary, 0.0)
    }

    @Test
    fun testAddHGateMinusState(){
        val circuit:Circuit = Circuit(1, MINUS_STATE)

        val qubits:Array<Qubit> = circuit.getQubits()

        circuit.addGate(H(qubits))
        circuit.runCircuit()

        val qubit = qubits[0]
        val qubitState: State = qubit.getState()

        assertEquals(qubitState.zeroAmplitude.real, 0.0)
        assertEquals(qubitState.zeroAmplitude.imaginary, 0.0)

        assertEquals(qubitState.oneAmplitude.real, 1.0)
        assertEquals(qubitState.oneAmplitude.imaginary, 0.0)
    }

   // TODO: TEST H GATE WITH AN ARBITRARY STATE
    // TODO: TEST STATE (IS_ZERO, IS_ONE, IS_PLUS, IS_MINUS,etc)


    @Test
    fun testAddZGateZeroState(){
        val circuit:Circuit = Circuit(1)

        val qubits:Array<Qubit> = circuit.getQubits()

        circuit.addGate(Z(qubits))
        circuit.runCircuit()

        val qubit = qubits[0]
        val qubitState: State = qubit.getState()

        assertEquals(qubitState.zeroAmplitude.real, 1.0)
        assertEquals(qubitState.zeroAmplitude.imaginary, 0.0)

        assertEquals(qubitState.oneAmplitude.real, 0.0)
        assertEquals(qubitState.oneAmplitude.imaginary, 0.0)
    }

    @Test
    fun testAddZGateOneState(){
        val circuit:Circuit = Circuit(1, ONE_STATE)

        val qubits:Array<Qubit> = circuit.getQubits()

        circuit.addGate(Z(qubits))
        circuit.runCircuit()

        val qubit = qubits[0]
        val qubitState: State = qubit.getState()

        assertEquals(qubitState.zeroAmplitude.real, 0.0)
        assertEquals(qubitState.zeroAmplitude.imaginary, 0.0)

        assertEquals(qubitState.oneAmplitude.real, -1.0)
        assertEquals(qubitState.oneAmplitude.imaginary, 0.0)
    }

    @Test
    fun testAddZGatePlusState(){
        val circuit:Circuit = Circuit(1, PLUS_STATE)

        val qubits:Array<Qubit> = circuit.getQubits()

        circuit.addGate(Z(qubits))
        circuit.runCircuit()

        val qubit = qubits[0]
        val qubitState: State = qubit.getState()

        assertEquals(qubitState.zeroAmplitude.real, HALF_PROB)
        assertEquals(qubitState.zeroAmplitude.imaginary, 0.0)

        assertEquals(qubitState.oneAmplitude.real, -HALF_PROB)
        assertEquals(qubitState.oneAmplitude.imaginary, 0.0)
    }

    @Test
    fun testAddZGateMinusState(){
        val circuit:Circuit = Circuit(1, MINUS_STATE)

        val qubits:Array<Qubit> = circuit.getQubits()

        circuit.addGate(Z(qubits))
        circuit.runCircuit()

        val qubit = qubits[0]
        val qubitState: State = qubit.getState()

        assertEquals(qubitState.zeroAmplitude.real, HALF_PROB)
        assertEquals(qubitState.zeroAmplitude.imaginary, 0.0)

        assertEquals(qubitState.oneAmplitude.real, HALF_PROB)
        assertEquals(qubitState.oneAmplitude.imaginary, 0.0)
    }

    // TODO: TEST Z GATE WITH AN ARBITRARY STATE

    @Test
    fun testAddRYGateZeroState(){
        val circuit:Circuit = Circuit(1)

        val qubits:Array<Qubit> = circuit.getQubits()

        circuit.addGate(RY(qubits, PI))
        circuit.runCircuit()

        val qubit = qubits[0]
        val qubitState: State = qubit.getState()

        assertEquals(round(qubitState.zeroAmplitude.real), 0.0)
        assertEquals(round(qubitState.zeroAmplitude.imaginary), 0.0)

        assertEquals(round(qubitState.oneAmplitude.real), 1.0)
        assertEquals(round(qubitState.oneAmplitude.imaginary), 0.0)
    }

    @Test
    fun testAddRYGateOneState(){
        val circuit:Circuit = Circuit(1, ONE_STATE)

        val qubits:Array<Qubit> = circuit.getQubits()

        circuit.addGate(RY(qubits, PI))
        circuit.runCircuit()

        val qubit = qubits[0]
        val qubitState: State = qubit.getState()

        assertEquals(round(qubitState.zeroAmplitude.real), -1.0) // we have a phase
        assertEquals(round(qubitState.zeroAmplitude.imaginary), 0.0)

        assertEquals(round(qubitState.oneAmplitude.real), 0.0)
        assertEquals(round(qubitState.oneAmplitude.imaginary), 0.0)
    }

    @Test
    fun testAddRYGatePlusState(){
        val circuit:Circuit = Circuit(1, PLUS_STATE)

        val qubits:Array<Qubit> = circuit.getQubits()

        circuit.addGate(RY(qubits, PI))
        circuit.runCircuit()

        val qubit = qubits[0]
        val qubitState: State = qubit.getState()

        assertEquals(qubitState.zeroAmplitude.real, -HALF_PROB)
        assertEquals(round(qubitState.zeroAmplitude.imaginary), 0.0)

        assertEquals(qubitState.oneAmplitude.real, HALF_PROB)
        assertEquals(round(qubitState.oneAmplitude.imaginary), 0.0)
    }

    @Test
    fun testAddRYGateMinusState(){
        val circuit:Circuit = Circuit(1, MINUS_STATE)

        val qubits:Array<Qubit> = circuit.getQubits()

        circuit.addGate(RY(qubits, PI))
        circuit.runCircuit()

        val qubit = qubits[0]
        val qubitState: State = qubit.getState()

        assertEquals(qubitState.zeroAmplitude.real, HALF_PROB)
        assertEquals(round(qubitState.zeroAmplitude.imaginary), 0.0)

        assertEquals(qubitState.oneAmplitude.real, HALF_PROB)
        assertEquals(round(qubitState.oneAmplitude.imaginary), 0.0)
    }


    // TODO: TEST RY GATE WITH AN ARBITRARY STATE

    @Test
    fun testAddRXGateZeroState(){
        val circuit:Circuit = Circuit(1)

        val qubits:Array<Qubit> = circuit.getQubits()

        circuit.addGate(RX(qubits, PI))
        circuit.runCircuit()

        val qubit = qubits[0]
        val qubitState: State = qubit.getState()

        assertEquals(round(qubitState.zeroAmplitude.real), 0.0)
        assertEquals(round(qubitState.zeroAmplitude.imaginary), 0.0)

        assertEquals(round(qubitState.oneAmplitude.real), 0.0)
        assertEquals(round(qubitState.oneAmplitude.imaginary), -1.0)
    }

    @Test
    fun testAddRXGateOneState(){
        val circuit:Circuit = Circuit(1, ONE_STATE)

        val qubits:Array<Qubit> = circuit.getQubits()

        circuit.addGate(RX(qubits, PI))
        circuit.runCircuit()

        val qubit = qubits[0]
        val qubitState: State = qubit.getState()

        assertEquals(round(qubitState.zeroAmplitude.real), 0.0)
        assertEquals(round(qubitState.zeroAmplitude.imaginary), -1.0)

        assertEquals(round(qubitState.oneAmplitude.real), 0.0)
        assertEquals(round(qubitState.oneAmplitude.imaginary), 0.0)
    }

    @Test
    fun testAddRXGatePlusState(){
        val circuit:Circuit = Circuit(1, PLUS_STATE)

        val qubits:Array<Qubit> = circuit.getQubits()

        circuit.addGate(RX(qubits, PI))
        circuit.runCircuit()

        val qubit = qubits[0]
        val qubitState: State = qubit.getState()

        assertEquals(round(qubitState.zeroAmplitude.real), 0.0)
        assertEquals(qubitState.zeroAmplitude.imaginary, -HALF_PROB)

        assertEquals(round(qubitState.oneAmplitude.real), 0.0)
        assertEquals(qubitState.oneAmplitude.imaginary, -HALF_PROB)
    }

    @Test
    fun testAddRXGateMinusState(){
        val circuit:Circuit = Circuit(1, MINUS_STATE)

        val qubits:Array<Qubit> = circuit.getQubits()

        circuit.addGate(RX(qubits, PI))
        circuit.runCircuit()

        val qubit = qubits[0]
        val qubitState: State = qubit.getState()

        assertEquals(round(qubitState.zeroAmplitude.real), 0.0)
        assertEquals(qubitState.zeroAmplitude.imaginary, HALF_PROB)

        // the value is to close to 0.0, but it's negative, so after rounding it, it becomes -0.0, that's why I'm using notMinusZero() here
        assertEquals(notMinusZero(round(qubitState.oneAmplitude.real)), 0.0)
        assertEquals(qubitState.oneAmplitude.imaginary, -HALF_PROB)
    }


    // TODO: TEST RX GATE WITH AN ARBITRARY STATE
}