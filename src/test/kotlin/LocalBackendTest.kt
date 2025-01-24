import math.HALF_PROB
import kotlin.test.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import providers.Outcome
import providers.local.LocalBackend
import kotlin.math.PI
import kotlin.math.round

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

    @Test()
    fun testLocalBackendHGateInvalidNumberOfQubits(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(Gate("H", arrayListOf(1,2)))

        val exception = assertThrows(IllegalStateException::class.java){
            backend.execute(circuit)
        }
        assertEquals("Invalid number of Qubits for H gate!", exception.message)
    }

    @Test()
    fun testLocalBackendHGateInvalidNumberOfCircuitQubits(){
        val backend = LocalBackend()
        val circuit = Circuit(0)
        circuit.addGate(Gate("H", arrayListOf(1)))

        val exception = assertThrows(IllegalStateException::class.java){
            backend.execute(circuit)
        }
        assertEquals("Your Circuit must have at least 1 qubit!", exception.message)
    }

    @Test()
    fun testLocalBackendHGateInvalidSelectedQubit(){
        val backend = LocalBackend()
        val circuit = Circuit(1)
        circuit.addGate(Gate("Z", arrayListOf(1)))

        val exception = assertThrows(IllegalStateException::class.java){
            backend.execute(circuit)
        }
        assertEquals("Invalid selected Qubit for Z gate!", exception.message)
    }

    @Test
    fun testLocalBackendHGateZeroState(){
        val backend = LocalBackend()
        val circuit = Circuit(1)
        circuit.addGate(Gate("H", arrayListOf(0)))
        val result:Outcome = backend.execute(circuit)

        assertEquals(result.first().real, HALF_PROB)
        assertEquals(result.first().imaginary, 0.0)

        assertEquals(result.last().real, HALF_PROB)
        assertEquals(result.last().imaginary, 0.0)
    }

    @Test
    fun testLocalBackendHGateOneState(){
        val backend = LocalBackend()
        val circuit = Circuit(1)
        circuit.addGate(Gate("X", arrayListOf(0)))
        circuit.addGate(Gate("H", arrayListOf(0)))
        val result:Outcome = backend.execute(circuit)

        assertEquals(result.first().real, HALF_PROB)
        assertEquals(result.first().imaginary, 0.0)

        assertEquals(result.last().real, -HALF_PROB)
        assertEquals(result.last().imaginary, 0.0)
    }

    @Test
    fun testLocalBackendHGate00State(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(Gate("H", arrayListOf(0)))
        circuit.addGate(Gate("H", arrayListOf(1)))
        val result:Outcome = backend.execute(circuit)

        for (i in 0..<4){
            assertEquals(round(result[i].real*10)/10, 0.5)
            assertEquals(result[i].imaginary, 0.0)
        }
    }

    @Test
    fun testLocalBackendHGate01State(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(Gate("X", arrayListOf(1)))
        circuit.addGate(Gate("H", arrayListOf(0)))
        circuit.addGate(Gate("H", arrayListOf(1)))
        val result:Outcome = backend.execute(circuit)

        assertEquals(round(result[0].real*10)/10, 0.5)
        assertEquals(result[0].imaginary, 0.0)

        assertEquals(round(result[1].real*10)/10, -0.5)
        assertEquals(result[1].imaginary, 0.0)

        assertEquals(round(result[2].real*10)/10, 0.5)
        assertEquals(result[2].imaginary, 0.0)

        assertEquals(round(result[3].real*10)/10, -0.5)
        assertEquals(result[3].imaginary, 0.0)
    }

    @Test
    fun testLocalBackendHGate10State(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(Gate("X", arrayListOf(0)))
        circuit.addGate(Gate("H", arrayListOf(0)))
        circuit.addGate(Gate("H", arrayListOf(1)))
        val result:Outcome = backend.execute(circuit)

        assertEquals(round(result[0].real*10)/10, 0.5)
        assertEquals(result[0].imaginary, 0.0)

        assertEquals(round(result[1].real*10)/10, 0.5)
        assertEquals(result[1].imaginary, 0.0)

        assertEquals(round(result[2].real*10)/10, -0.5)
        assertEquals(result[2].imaginary, 0.0)

        assertEquals(round(result[3].real*10)/10, -0.5)
        assertEquals(result[3].imaginary, 0.0)
    }

    @Test
    fun testLocalBackendHGate11State(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(Gate("X", arrayListOf(0)))
        circuit.addGate(Gate("X", arrayListOf(1)))
        circuit.addGate(Gate("H", arrayListOf(0)))
        circuit.addGate(Gate("H", arrayListOf(1)))
        val result:Outcome = backend.execute(circuit)

        assertEquals(round(result[0].real*10)/10, 0.5)
        assertEquals(result[0].imaginary, 0.0)

        assertEquals(round(result[1].real*10)/10, -0.5)
        assertEquals(result[1].imaginary, 0.0)

        assertEquals(round(result[2].real*10)/10, -0.5)
        assertEquals(result[2].imaginary, 0.0)

        assertEquals(round(result[3].real*10)/10, 0.5)
        assertEquals(result[3].imaginary, 0.0)
    }

    @Test
    fun testLocalBackendHGateHII(){
        val backend = LocalBackend()
        val circuit = Circuit(3)
        circuit.addGate(Gate("H", arrayListOf(0)))
        val result:Outcome = backend.execute(circuit)

        assertEquals(result[0].real, HALF_PROB)
        assertEquals(result[0].imaginary, 0.0)

        assertEquals(result[4].real, HALF_PROB)
        assertEquals(result[4].imaginary, 0.0)

        for(i in 0..<8){
            if(i == 0 || i == 4){
                continue
            }

            assertEquals(result[i].real, 0.0)
            assertEquals(result[i].imaginary, 0.0)
        }
    }

    @Test
    fun testLocalBackendHGateIHI(){
        val backend = LocalBackend()
        val circuit = Circuit(3)
        circuit.addGate(Gate("H", arrayListOf(1)))
        val result:Outcome = backend.execute(circuit)

        assertEquals(result[0].real, HALF_PROB)
        assertEquals(result[0].imaginary, 0.0)

        assertEquals(result[2].real, HALF_PROB)
        assertEquals(result[2].imaginary, 0.0)

        for(i in 0..<8){
            if(i == 0 || i == 2){
                continue
            }

            assertEquals(result[i].real, 0.0)
            assertEquals(result[i].imaginary, 0.0)
        }
    }

    @Test
    fun testLocalBackendHGateIIH(){
        val backend = LocalBackend()
        val circuit = Circuit(3)
        circuit.addGate(Gate("H", arrayListOf(2)))
        val result:Outcome = backend.execute(circuit)

        assertEquals(result[0].real, HALF_PROB)
        assertEquals(result[0].imaginary, 0.0)

        assertEquals(result[1].real, HALF_PROB)
        assertEquals(result[1].imaginary, 0.0)

        for(i in 0..<8){
            if(i == 0 || i == 1){
                continue
            }

            assertEquals(result[i].real, 0.0)
            assertEquals(result[i].imaginary, 0.0)
        }
    }

    @Test
    fun testLocalBackendBellStatePhiPlus(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(Gate("H", arrayListOf(0)))
        circuit.addGate(Gate("CNOT", arrayListOf(0,1)))
        val result:Outcome = backend.execute(circuit)

        assertEquals(result[0].real, HALF_PROB)
        assertEquals(result[0].imaginary, 0.0)

        assertEquals(result[1].real, 0.0)
        assertEquals(result[1].imaginary, 0.0)

        assertEquals(result[2].real, 0.0)
        assertEquals(result[2].imaginary, 0.0)

        assertEquals(result[3].real, HALF_PROB)
        assertEquals(result[3].imaginary, 0.0)
    }

    @Test
    fun testLocalBackendBellStatePhiMinus(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(Gate("H", arrayListOf(0)))
        circuit.addGate(Gate("Z", arrayListOf(0)))
        circuit.addGate(Gate("CNOT", arrayListOf(0,1)))
        val result:Outcome = backend.execute(circuit)

        assertEquals(result[0].real, HALF_PROB)
        assertEquals(result[0].imaginary, 0.0)

        assertEquals(result[1].real, 0.0)
        assertEquals(result[1].imaginary, 0.0)

        assertEquals(result[2].real, 0.0)
        assertEquals(result[2].imaginary, 0.0)

        assertEquals(result[3].real, -HALF_PROB)
        assertEquals(result[3].imaginary, 0.0)
    }

    @Test
    fun testLocalBackendBellStatePsiPlus(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(Gate("H", arrayListOf(0)))
        circuit.addGate(Gate("X", arrayListOf(1)))
        circuit.addGate(Gate("CNOT", arrayListOf(0,1)))
        val result:Outcome = backend.execute(circuit)

        assertEquals(result[0].real, 0.0)
        assertEquals(result[0].imaginary, 0.0)

        assertEquals(result[1].real, HALF_PROB)
        assertEquals(result[1].imaginary, 0.0)

        assertEquals(result[2].real, HALF_PROB)
        assertEquals(result[2].imaginary, 0.0)

        assertEquals(result[3].real, 0.0)
        assertEquals(result[3].imaginary, 0.0)
    }

    @Test
    fun testLocalBackendBellStatePsiMinus(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(Gate("H", arrayListOf(0)))
        circuit.addGate(Gate("X", arrayListOf(1)))
        circuit.addGate(Gate("Z", arrayListOf(0)))
        circuit.addGate(Gate("CNOT", arrayListOf(0,1)))
        val result:Outcome = backend.execute(circuit)

        assertEquals(result[0].real, 0.0)
        assertEquals(result[0].imaginary, 0.0)

        assertEquals(result[1].real, HALF_PROB)
        assertEquals(result[1].imaginary, 0.0)

        assertEquals(result[2].real, -HALF_PROB)
        assertEquals(result[2].imaginary, 0.0)

        assertEquals(result[3].real, 0.0)
        assertEquals(result[3].imaginary, 0.0)
    }

    @Test
    fun testLocalBackendBellStatePsiMinus2(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(Gate("H", arrayListOf(0)))
        circuit.addGate(Gate("X", arrayListOf(1)))
        circuit.addGate(Gate("Z", arrayListOf(0)))
        circuit.addGate(Gate("Z", arrayListOf(1)))
        circuit.addGate(Gate("CNOT", arrayListOf(0,1)))
        val result:Outcome = backend.execute(circuit)

        // if you do this circuit using a little endian pattern, the phase is going to be inserted on 10
        // however, once our first qubit is the left-most, this will be the reverse

        assertEquals(result[0].real, 0.0)
        assertEquals(result[0].imaginary, 0.0)

        assertEquals(result[1].real, -HALF_PROB)
        assertEquals(result[1].imaginary, 0.0)

        assertEquals(result[2].real, HALF_PROB)
        assertEquals(result[2].imaginary, 0.0)

        assertEquals(result[3].real, 0.0)
        assertEquals(result[3].imaginary, 0.0)
    }

    @Test
    fun testLocalBackendGHZState(){
        val backend = LocalBackend()
        val circuit = Circuit(3)
        circuit.addGate(Gate("H", arrayListOf(0)))
        circuit.addGate(Gate("CNOT", arrayListOf(0,1)))
        circuit.addGate(Gate("CNOT", arrayListOf(1,2)))
        val result:Outcome = backend.execute(circuit)

        assertEquals(result[0].real, HALF_PROB)
        assertEquals(result[0].imaginary, 0.0)

        assertEquals(result[7].real, HALF_PROB)
        assertEquals(result[7].imaginary, 0.0)

        for(i in 0..<8){
            if(i == 0 || i == 7){
                continue
            }

            assertEquals(result[i].real, 0.0)
            assertEquals(result[i].imaginary, 0.0)
        }
    }

    @Test
    fun testLocalBackendHCnotReverseSimpleSuperposition(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(Gate("H", arrayListOf(0)))
        circuit.addGate(Gate("CNOT", arrayListOf(1,0)))
        val result:Outcome = backend.execute(circuit)

        assertEquals(result[0].real, HALF_PROB)
        assertEquals(result[0].imaginary, 0.0)

        assertEquals(result[1].real, 0.0)
        assertEquals(result[1].imaginary, 0.0)

        assertEquals(result[2].real, HALF_PROB)
        assertEquals(result[2].imaginary, 0.0)

        assertEquals(result[3].real, 0.0)
        assertEquals(result[3].imaginary, 0.0)
    }

    @Test
    fun testLocalBackendReverseBellState(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(Gate("H", arrayListOf(1)))
        circuit.addGate(Gate("CNOT", arrayListOf(1,0)))
        val result:Outcome = backend.execute(circuit)

        assertEquals(result[0].real, HALF_PROB)
        assertEquals(result[0].imaginary, 0.0)

        assertEquals(result[1].real, 0.0)
        assertEquals(result[1].imaginary, 0.0)

        assertEquals(result[2].real, 0.0)
        assertEquals(result[2].imaginary, 0.0)

        assertEquals(result[3].real, HALF_PROB)
        assertEquals(result[3].imaginary, 0.0)
    }

    @Test()
    fun testLocalBackendRXGateInvalidNumberOfParams(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(Gate("RX", arrayListOf(1,2)))

        val exception = assertThrows(IllegalStateException::class.java){
            backend.execute(circuit)
        }
        assertEquals("Invalid number of Params for RX gate!", exception.message)
    }

    @Test()
    fun testLocalBackendRXGateInvalidNumberOfQubits(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(Gate("RX", arrayListOf(1,2), arrayListOf(0.0)))

        val exception = assertThrows(IllegalStateException::class.java){
            backend.execute(circuit)
        }
        assertEquals("Invalid number of Qubits for RX gate!", exception.message)
    }

    @Test()
    fun testLocalBackendRXGateInvalidNumberOfCircuitQubits(){
        val backend = LocalBackend()
        val circuit = Circuit(0)
        circuit.addGate(Gate("RX", arrayListOf(1), arrayListOf(0.0)))

        val exception = assertThrows(IllegalStateException::class.java){
            backend.execute(circuit)
        }
        assertEquals("Your Circuit must have at least 1 qubit!", exception.message)
    }

    @Test()
    fun testLocalBackendRXGateInvalidSelectedQubit(){
        val backend = LocalBackend()
        val circuit = Circuit(1)
        circuit.addGate(Gate("RX", arrayListOf(1), arrayListOf(0.0)))

        val exception = assertThrows(IllegalStateException::class.java){
            backend.execute(circuit)
        }
        assertEquals("Invalid selected Qubit for RX gate!", exception.message)
    }

    @Test
    fun testLocalBackendRXGateZeroState(){
        val backend = LocalBackend()
        val circuit = Circuit(1)
        circuit.addGate(Gate("RX", arrayListOf(0), arrayListOf(PI)))
        val result:Outcome = backend.execute(circuit)

        assertEquals(round(result.first().real), 0.0)
        assertEquals(result.first().imaginary, 0.0)

        assertEquals(round(result.last().real), 0.0)
        assertEquals(result.last().imaginary, -1.0)
    }

    @Test
    fun testLocalBackendRXGateOneState(){
        val backend = LocalBackend()
        val circuit = Circuit(1)
        circuit.addGate(Gate("X", arrayListOf(0)))
        circuit.addGate(Gate("RX", arrayListOf(0), arrayListOf(PI)))
        val result:Outcome = backend.execute(circuit)

        assertEquals(round(result.first().real), 0.0)
        assertEquals(result.first().imaginary, -1.0)

        assertEquals(round(result.last().real), 0.0)
        assertEquals(result.last().imaginary, 0.0)
    }

    @Test()
    fun testLocalBackendRYGateInvalidNumberOfParams(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(Gate("RY", arrayListOf(1,2)))

        val exception = assertThrows(IllegalStateException::class.java){
            backend.execute(circuit)
        }
        assertEquals("Invalid number of Params for RY gate!", exception.message)
    }

    @Test()
    fun testLocalBackendRYGateInvalidNumberOfQubits(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(Gate("RY", arrayListOf(1,2), arrayListOf(0.0)))

        val exception = assertThrows(IllegalStateException::class.java){
            backend.execute(circuit)
        }
        assertEquals("Invalid number of Qubits for RY gate!", exception.message)
    }

    @Test()
    fun testLocalBackendRYGateInvalidNumberOfCircuitQubits(){
        val backend = LocalBackend()
        val circuit = Circuit(0)
        circuit.addGate(Gate("RY", arrayListOf(1), arrayListOf(0.0)))

        val exception = assertThrows(IllegalStateException::class.java){
            backend.execute(circuit)
        }
        assertEquals("Your Circuit must have at least 1 qubit!", exception.message)
    }

    @Test()
    fun testLocalBackendRYGateInvalidSelectedQubit(){
        val backend = LocalBackend()
        val circuit = Circuit(1)
        circuit.addGate(Gate("RY", arrayListOf(1), arrayListOf(0.0)))

        val exception = assertThrows(IllegalStateException::class.java){
            backend.execute(circuit)
        }
        assertEquals("Invalid selected Qubit for RY gate!", exception.message)
    }

    @Test
    fun testLocalBackendRYGateZeroState(){
        val backend = LocalBackend()
        val circuit = Circuit(1)
        circuit.addGate(Gate("RY", arrayListOf(0), arrayListOf(PI)))
        val result:Outcome = backend.execute(circuit)

        assertEquals(round(result.first().real), 0.0)
        assertEquals(result.first().imaginary, 0.0)

        assertEquals(round(result.last().real), 1.0)
        assertEquals(result.last().imaginary, 0.0)
    }

    @Test
    fun testLocalBackendRYGateOneState(){
        val backend = LocalBackend()
        val circuit = Circuit(1)
        circuit.addGate(Gate("X", arrayListOf(0)))
        circuit.addGate(Gate("RY", arrayListOf(0), arrayListOf(PI)))
        val result:Outcome = backend.execute(circuit)

        assertEquals(round(result.first().real), -1.0)
        assertEquals(result.first().imaginary, 0.0)

        assertEquals(round(result.last().real), 0.0)
        assertEquals(result.last().imaginary, 0.0)
    }

    // TODO: TEST PHASEKICKBACK
    // TODO: TEST X FOR DIFFERENT STATES
    // TODO: TEST CNOT FOR DIFFERENT STATES
    // TODO: TEST Z FOR DIFFERENT STATES
    // TODO: TEST CZ FOR DIFFERENT STATES
    // TODO: TEST SWAP FOR DIFFERENT STATES
    // TODO: TEST RX FOR DIFFERENT STATES
    // TODO: TEST RY FOR DIFFERENT STATES
    // TODO: TEST RZ FOR DIFFERENT STATES
    // TODO: TEST CANCEL GATES (H, RX, RY, etc.)
}