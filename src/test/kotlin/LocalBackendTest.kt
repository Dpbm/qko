import kotlin.test.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import providers.Outcome
import providers.local.LocalBackend

class LocalBackendTest {
    @Test
    fun testLocalBackendInitializeAtZero(){
        val backend = LocalBackend()
        val circuit = Circuit(2)

        val result:Outcome = backend.execute(circuit)

        assertEquals(result.size, 4);

        assertEquals(result.first().real, 1.0)
        assertEquals(result.first().imaginary, 0.0)

        for(i in 1..<4){
            assertEquals(result[i].real, 0.0)
            assertEquals(result[i].imaginary, 0.0)
        }
    }

    @Test()
    fun testLocalBackendXGateInvalidNumberOfQubits(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(Gate("X", arrayListOf(1,2)))

        val exception = assertThrows(IllegalStateException::class.java){
            backend.execute(circuit)
        }
        assertEquals("Invalid number of Qubits for X gate!", exception.message)
    }

    @Test()
    fun testLocalBackendXGateInvalidSelectedQubit(){
        val backend = LocalBackend()
        val circuit = Circuit(1)
        circuit.addGate(Gate("X", arrayListOf(1)))

        val exception = assertThrows(IllegalStateException::class.java){
            backend.execute(circuit)
        }
        assertEquals("Invalid selected Qubit for X gate!", exception.message)
    }

    @Test
    fun testLocalBackendXGateZeroState(){
        val backend = LocalBackend()
        val circuit = Circuit(1)
        circuit.addGate(Gate("X", arrayListOf(0)))
        val result:Outcome = backend.execute(circuit)

        assertEquals(result.first().real, 0.0)
        assertEquals(result.first().imaginary, 0.0)

        assertEquals(result.last().real, 1.0)
        assertEquals(result.last().imaginary, 0.0)
    }

    @Test
    fun testLocalBackendXGateOneState(){
        val backend = LocalBackend()
        val circuit = Circuit(1)
        circuit.addGate(Gate("X", arrayListOf(0)))
        circuit.addGate(Gate("X", arrayListOf(0)))
        val result:Outcome = backend.execute(circuit)

        assertEquals(result.first().real, 1.0)
        assertEquals(result.first().imaginary, 0.0)

        assertEquals(result.last().real, 0.0)
        assertEquals(result.last().imaginary, 0.0)
    }

    @Test
    fun testLocalBackendXGateTwoQubitsTo01(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(Gate("X", arrayListOf(1)))
        val result:Outcome = backend.execute(circuit)

        assertEquals(result.first().real, 0.0)
        assertEquals(result.first().imaginary, 0.0)

        assertEquals(result[1].real, 1.0)
        assertEquals(result[1].imaginary, 0.0)

        assertEquals(result[2].real, 0.0)
        assertEquals(result[2].imaginary, 0.0)

        assertEquals(result[3].real, 0.0)
        assertEquals(result[3].imaginary, 0.0)
    }

    @Test
    fun testLocalBackendXGateTwoQubitsTo10(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(Gate("X", arrayListOf(0)))
        val result:Outcome = backend.execute(circuit)

        assertEquals(result.first().real, 0.0)
        assertEquals(result.first().imaginary, 0.0)

        assertEquals(result[1].real, 0.0)
        assertEquals(result[1].imaginary, 0.0)

        assertEquals(result[2].real, 1.0)
        assertEquals(result[2].imaginary, 0.0)

        assertEquals(result[3].real, 0.0)
        assertEquals(result[3].imaginary, 0.0)
    }

    @Test
    fun testLocalBackendXGateTwoQubitsTo11(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(Gate("X", arrayListOf(0)))
        circuit.addGate(Gate("X", arrayListOf(1)))
        val result:Outcome = backend.execute(circuit)

        assertEquals(result.first().real, 0.0)
        assertEquals(result.first().imaginary, 0.0)

        assertEquals(result[1].real, 0.0)
        assertEquals(result[1].imaginary, 0.0)

        assertEquals(result[2].real, 0.0)
        assertEquals(result[2].imaginary, 0.0)

        assertEquals(result[3].real, 1.0)
        assertEquals(result[3].imaginary, 0.0)
    }

    @Test
    fun testLocalBackendXGateTwoQubitsTo00(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(Gate("X", arrayListOf(0)))
        circuit.addGate(Gate("X", arrayListOf(1)))
        circuit.addGate(Gate("X", arrayListOf(0)))
        circuit.addGate(Gate("X", arrayListOf(1)))
        val result:Outcome = backend.execute(circuit)

        assertEquals(result.first().real, 1.0)
        assertEquals(result.first().imaginary, 0.0)

        assertEquals(result[1].real, 0.0)
        assertEquals(result[1].imaginary, 0.0)

        assertEquals(result[2].real, 0.0)
        assertEquals(result[2].imaginary, 0.0)

        assertEquals(result[3].real, 0.0)
        assertEquals(result[3].imaginary, 0.0)
    }

    @Test
    fun testLocalBackendXGateThreeQubitsTo001(){
        val backend = LocalBackend()
        val circuit = Circuit(3)
        circuit.addGate(Gate("X", arrayListOf(2)))
        val result:Outcome = backend.execute(circuit)

        assertEquals(result.first().real, 0.0)
        assertEquals(result.first().imaginary, 0.0)

        assertEquals(result[1].real, 1.0)
        assertEquals(result[1].imaginary, 0.0)

        for(i in 2..<8){
            assertEquals(result[i].real, 0.0)
            assertEquals(result[i].imaginary, 0.0)
        }
    }

    @Test
    fun testLocalBackendXGateThreeQubitsTo100(){
        val backend = LocalBackend()
        val circuit = Circuit(3)
        circuit.addGate(Gate("X", arrayListOf(0)))
        val result:Outcome = backend.execute(circuit)


        assertEquals(result[4].real, 1.0)
        assertEquals(result[4].imaginary, 0.0)

        for(i in 0..<8){
            if(i == 4){
                continue
            }
            assertEquals(result[i].real, 0.0)
            assertEquals(result[i].imaginary, 0.0)
        }
    }

    @Test
    fun testLocalBackendXGateThreeQubitsTo101(){
        val backend = LocalBackend()
        val circuit = Circuit(3)
        circuit.addGate(Gate("X", arrayListOf(0)))
        circuit.addGate(Gate("X", arrayListOf(2)))
        val result:Outcome = backend.execute(circuit)


        assertEquals(result[5].real, 1.0)
        assertEquals(result[5].imaginary, 0.0)

        for(i in 0..<8){
            if(i == 5){
                continue
            }
            assertEquals(result[i].real, 0.0)
            assertEquals(result[i].imaginary, 0.0)
        }
    }
}