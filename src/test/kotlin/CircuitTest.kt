import gates.X
import kotlin.test.Test

class CircuitTest {

    @Test
    fun testCircuitInitializationOneQubit(){
        val circuit:Circuit = Circuit(1)

        assert(circuit.getTotalQubits() == 1)

        val qubits: Array<Qubit> = circuit.getQubits();
        assert(qubits.size == 1)

        val qubitState: State = qubits[0].getState()
        assert(qubitState.getZeroAmplitude().real == 1.0)
        assert(qubitState.getZeroAmplitude().imaginary == 0.0)

        assert(qubitState.getOneAmplitude().real == 0.0)
        assert(qubitState.getOneAmplitude().imaginary == 0.0)
    }

    @Test
    fun testCircuitInitializationThreeQubits(){
        val circuit:Circuit = Circuit(3)

        assert(circuit.getTotalQubits() == 3)

        val qubits: Array<Qubit> = circuit.getQubits();
        assert(qubits.size == 3)

        for(qubit in qubits){
            val qubitState: State = qubit.getState()
            assert(qubitState.getZeroAmplitude().real == 1.0)
            assert(qubitState.getZeroAmplitude().imaginary == 0.0)

            assert(qubitState.getOneAmplitude().real == 0.0)
            assert(qubitState.getOneAmplitude().imaginary == 0.0)
        }


    }

    @Test
    fun testAddXGate(){
        val circuit:Circuit = Circuit(1)

        val qubits:Array<Qubit> = circuit.getQubits()

        circuit.addGate(X(qubits))
        circuit.runCircuit()

        val qubit = qubits[0];
        val qubitState: State = qubit.getState()

        assert(qubitState.getZeroAmplitude().real == 0.0)
        assert(qubitState.getZeroAmplitude().imaginary == 0.0)

        assert(qubitState.getOneAmplitude().real == 1.0)
        assert(qubitState.getOneAmplitude().imaginary == 0.0)
    }
}