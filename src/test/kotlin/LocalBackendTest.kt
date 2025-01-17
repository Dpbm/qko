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
    fun testLocalBackendXGateInvalidNumberOfCircuitQubits(){
        val backend = LocalBackend()
        val circuit = Circuit(0)
        circuit.addGate(Gate("X", arrayListOf(1)))

        val exception = assertThrows(IllegalStateException::class.java){
            backend.execute(circuit)
        }
        assertEquals("Your Circuit must have at least 1 qubit!", exception.message)
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

    @Test()
    fun testLocalBackendCNOTGateInvalidNumberOfQubits(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(Gate("CNOT", arrayListOf(1)))

        val exception = assertThrows(IllegalStateException::class.java){
            backend.execute(circuit)
        }
        assertEquals("Invalid number of Qubits for CNOT gate!", exception.message)
    }

    @Test()
    fun testLocalBackendCNOTGateInvalidNumberOfCircuitQubits(){
        val backend = LocalBackend()
        val circuit = Circuit(1)
        circuit.addGate(Gate("CNOT", arrayListOf(0,1)))

        val exception = assertThrows(IllegalStateException::class.java){
            backend.execute(circuit)
        }
        assertEquals("Your Circuit must have at least 2 qubits!", exception.message)
    }

    @Test()
    fun testLocalBackendCNOTGateInvalidControlQubit(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(Gate("CNOT", arrayListOf(2,0)))

        val exception = assertThrows(IllegalStateException::class.java){
            backend.execute(circuit)
        }
        assertEquals("Invalid control Qubit for CNOT gate!", exception.message)
    }

    @Test()
    fun testLocalBackendCNOTGateInvalidTargetQubit(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(Gate("CNOT", arrayListOf(0,2)))

        val exception = assertThrows(IllegalStateException::class.java){
            backend.execute(circuit)
        }
        assertEquals("Invalid target Qubit for CNOT gate!", exception.message)
    }

    @Test
    fun testLocalBackendCNOTFor00State(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(Gate("CNOT", arrayListOf(0,1)))
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
    fun testLocalBackendCNOTFor01State(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(Gate("X", arrayListOf(1)))
        circuit.addGate(Gate("CNOT", arrayListOf(0,1)))
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
    fun testLocalBackendCNOTFor10State(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(Gate("X", arrayListOf(0)))
        circuit.addGate(Gate("CNOT", arrayListOf(0,1)))
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
    fun testLocalBackendCNOTFor11State(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(Gate("X", arrayListOf(0)))
        circuit.addGate(Gate("X", arrayListOf(1)))
        circuit.addGate(Gate("CNOT", arrayListOf(0,1)))
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
    fun testLocalBackendCNOTFor101State(){
        val backend = LocalBackend()
        val circuit = Circuit(3)
        circuit.addGate(Gate("X", arrayListOf(0)))
        circuit.addGate(Gate("X", arrayListOf(2)))
        circuit.addGate(Gate("CNOT", arrayListOf(0,2)))
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

    @Test()
    fun testLocalBackendZGateInvalidNumberOfQubits(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(Gate("Z", arrayListOf(1,2)))

        val exception = assertThrows(IllegalStateException::class.java){
            backend.execute(circuit)
        }
        assertEquals("Invalid number of Qubits for Z gate!", exception.message)
    }

    @Test()
    fun testLocalBackendZGateInvalidNumberOfCircuitQubits(){
        val backend = LocalBackend()
        val circuit = Circuit(0)
        circuit.addGate(Gate("Z", arrayListOf(1)))

        val exception = assertThrows(IllegalStateException::class.java){
            backend.execute(circuit)
        }
        assertEquals("Your Circuit must have at least 1 qubit!", exception.message)
    }

    @Test()
    fun testLocalBackendZGateInvalidSelectedQubit(){
        val backend = LocalBackend()
        val circuit = Circuit(1)
        circuit.addGate(Gate("Z", arrayListOf(1)))

        val exception = assertThrows(IllegalStateException::class.java){
            backend.execute(circuit)
        }
        assertEquals("Invalid selected Qubit for Z gate!", exception.message)
    }

    @Test
    fun testLocalBackendZGateZeroState(){
        val backend = LocalBackend()
        val circuit = Circuit(1)
        circuit.addGate(Gate("Z", arrayListOf(0)))
        val result:Outcome = backend.execute(circuit)

        assertEquals(result.first().real, 1.0)
        assertEquals(result.first().imaginary, 0.0)

        assertEquals(result.last().real, 0.0)
        assertEquals(result.last().imaginary, 0.0)
    }

    @Test
    fun testLocalBackendZGateOneState(){
        val backend = LocalBackend()
        val circuit = Circuit(1)
        circuit.addGate(Gate("X", arrayListOf(0)))
        circuit.addGate(Gate("Z", arrayListOf(0)))
        val result:Outcome = backend.execute(circuit)

        assertEquals(result.first().real, 0.0)
        assertEquals(result.first().imaginary, 0.0)

        assertEquals(result.last().real, -1.0)
        assertEquals(result.last().imaginary, 0.0)
    }

    @Test
    fun testLocalBackendZGate01State(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(Gate("X", arrayListOf(1)))
        circuit.addGate(Gate("Z", arrayListOf(1)))
        val result:Outcome = backend.execute(circuit)

        assertEquals(result.first().real, 0.0)
        assertEquals(result.first().imaginary, 0.0)

        assertEquals(result[1].real, -1.0)
        assertEquals(result[1].imaginary, 0.0)

        assertEquals(result[2].real, 0.0)
        assertEquals(result[2].imaginary, 0.0)

        assertEquals(result[3].real, 0.0)
        assertEquals(result[3].imaginary, 0.0)
    }

    @Test()
    fun testLocalBackendCZGateInvalidNumberOfQubits(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(Gate("CZ", arrayListOf(1)))

        val exception = assertThrows(IllegalStateException::class.java){
            backend.execute(circuit)
        }
        assertEquals("Invalid number of Qubits for CZ gate!", exception.message)
    }

    @Test()
    fun testLocalBackendCZGateInvalidNumberOfCircuitQubits(){
        val backend = LocalBackend()
        val circuit = Circuit(1)
        circuit.addGate(Gate("CZ", arrayListOf(0,1)))

        val exception = assertThrows(IllegalStateException::class.java){
            backend.execute(circuit)
        }
        assertEquals("Your Circuit must have at least 2 qubits!", exception.message)
    }

    @Test()
    fun testLocalBackendCZGateInvalidControlQubit(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(Gate("CZ", arrayListOf(2,0)))

        val exception = assertThrows(IllegalStateException::class.java){
            backend.execute(circuit)
        }
        assertEquals("Invalid control Qubit for CZ gate!", exception.message)
    }

    @Test()
    fun testLocalBackendCZGateInvalidTargetQubit(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(Gate("CZ", arrayListOf(0,2)))

        val exception = assertThrows(IllegalStateException::class.java){
            backend.execute(circuit)
        }
        assertEquals("Invalid target Qubit for CZ gate!", exception.message)
    }

    @Test
    fun testLocalBackendCZFor00State(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(Gate("CZ", arrayListOf(0,1)))
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
    fun testLocalBackendCZFor01State(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(Gate("X", arrayListOf(1)))
        circuit.addGate(Gate("CZ", arrayListOf(0,1)))
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
    fun testLocalBackendCZFor10State(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(Gate("X", arrayListOf(0)))
        circuit.addGate(Gate("CZ", arrayListOf(0,1)))
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
    fun testLocalBackendCZFor11State(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(Gate("X", arrayListOf(0)))
        circuit.addGate(Gate("X", arrayListOf(1)))
        circuit.addGate(Gate("CZ", arrayListOf(0,1)))
        val result:Outcome = backend.execute(circuit)

        assertEquals(result.first().real, 0.0)
        assertEquals(result.first().imaginary, 0.0)

        assertEquals(result[1].real, 0.0)
        assertEquals(result[1].imaginary, 0.0)

        assertEquals(result[2].real, 0.0)
        assertEquals(result[2].imaginary, 0.0)

        assertEquals(result[3].real, -1.0)
        assertEquals(result[3].imaginary, 0.0)
    }

    @Test
    fun testLocalBackendCZFor101State(){
        val backend = LocalBackend()
        val circuit = Circuit(3)
        circuit.addGate(Gate("X", arrayListOf(0)))
        circuit.addGate(Gate("X", arrayListOf(2)))
        circuit.addGate(Gate("CZ", arrayListOf(0,2)))
        val result:Outcome = backend.execute(circuit)

        assertEquals(result[5].real, -1.0)
        assertEquals(result[5].imaginary, 0.0)

        for(i in 0..<8){
            if(i == 5){
                continue;
            }
            assertEquals(result[i].real, 0.0)
            assertEquals(result[i].imaginary, 0.0)
        }
    }

    @Test()
    fun testLocalBackendSWAPGateInvalidNumberOfQubits(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(Gate("SWAP", arrayListOf(1)))

        val exception = assertThrows(IllegalStateException::class.java){
            backend.execute(circuit)
        }
        assertEquals("Invalid number of Qubits for SWAP gate!", exception.message)
    }

    @Test()
    fun testLocalBackendSWAPGateInvalidNumberOfCircuitQubits(){
        val backend = LocalBackend()
        val circuit = Circuit(1)
        circuit.addGate(Gate("SWAP", arrayListOf(0,1)))

        val exception = assertThrows(IllegalStateException::class.java){
            backend.execute(circuit)
        }
        assertEquals("Your Circuit must have at least 2 qubits!", exception.message)
    }

    @Test()
    fun testLocalBackendSWAPGateInvalidControlQubit(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(Gate("SWAP", arrayListOf(2,0)))

        val exception = assertThrows(IllegalStateException::class.java){
            backend.execute(circuit)
        }
        assertEquals("Invalid control Qubit for SWAP gate!", exception.message)
    }

    @Test()
    fun testLocalBackendSWAPGateInvalidTargetQubit(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(Gate("SWAP", arrayListOf(0,2)))

        val exception = assertThrows(IllegalStateException::class.java){
            backend.execute(circuit)
        }
        assertEquals("Invalid target Qubit for SWAP gate!", exception.message)
    }

    @Test
    fun testLocalBackendSWAPFor00State(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(Gate("SWAP", arrayListOf(0,1)))
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
    fun testLocalBackendSWAPFor01State(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(Gate("X", arrayListOf(1)))
        circuit.addGate(Gate("SWAP", arrayListOf(0,1)))
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
    fun testLocalBackendSWAPFor10State(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(Gate("X", arrayListOf(0)))
        circuit.addGate(Gate("SWAP", arrayListOf(0,1)))
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
    fun testLocalBackendSWAPFor11State(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(Gate("X", arrayListOf(0)))
        circuit.addGate(Gate("X", arrayListOf(1)))
        circuit.addGate(Gate("SWAP", arrayListOf(0,1)))
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
    fun testLocalBackendSWAPFor100State(){
        val backend = LocalBackend()
        val circuit = Circuit(3)
        circuit.addGate(Gate("X", arrayListOf(0)))
        circuit.addGate(Gate("SWAP", arrayListOf(0,2)))
        val result:Outcome = backend.execute(circuit)

        assertEquals(result[1].real, 1.0)
        assertEquals(result[1].imaginary, 0.0)

        for(i in 0..<8){
            if(i == 1){
                continue
            }

            assertEquals(result[i].real, 0.0)
            assertEquals(result[i].imaginary, 0.0)
        }
    }

    @Test
    fun testLocalBackendSWAPFor011State(){
        val backend = LocalBackend()
        val circuit = Circuit(3)
        circuit.addGate(Gate("X", arrayListOf(1)))
        circuit.addGate(Gate("X", arrayListOf(2)))
        circuit.addGate(Gate("SWAP", arrayListOf(0,2)))
        val result:Outcome = backend.execute(circuit)

        assertEquals(result[6].real, 1.0)
        assertEquals(result[6].imaginary, 0.0)

        for(i in 0..<8){
            if(i == 6){
                continue
            }

            assertEquals(result[i].real, 0.0)
            assertEquals(result[i].imaginary, 0.0)
        }
    }



    // TODO: TEST X FOR DIFFERENT STATES
    // TODO: TEST CNOT FOR DIFFERENT STATES
    // TODO: TEST Z FOR DIFFERENT STATES
    // TODO: TEST CZ FOR DIFFERENT STATES
    // TODO: TEST SWAP FOR DIFFERENT STATES
}