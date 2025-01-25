import math.HALF_PROB
import kotlin.test.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import providers.Outcome
import providers.local.LocalBackend
import providers.local.gates.X
import providers.local.gates.SWAP
import providers.local.gates.Z
import providers.local.gates.CNOT
import providers.local.gates.CZ
import providers.local.gates.H
import providers.local.gates.RX
import providers.local.gates.RY
import providers.local.gates.RZ
import kotlin.math.PI
import kotlin.math.round

class LocalBackendTest {
    @Test
    fun testLocalBackendInitializeAtZero(){
        val backend = LocalBackend()
        val circuit = Circuit(2)

        val result:Outcome = backend.execute(circuit)

        assertEquals(result.state.size, 4);

        assertEquals(result.state.first().real, 1.0)
        assertEquals(result.state.first().imaginary, 0.0)

        for(i in 1..<4){
            assertEquals(result.state[i].real, 0.0)
            assertEquals(result.state[i].imaginary, 0.0)
        }
    }

    @Test()
    fun testLocalBackendXGateInvalidNumberOfQubits(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(X(arrayListOf(1,2)))

        val exception = assertThrows(IllegalStateException::class.java){
            backend.execute(circuit)
        }
        assertEquals("Invalid number of Qubits for X gate!", exception.message)
    }

    @Test()
    fun testLocalBackendXGateInvalidNumberOfCircuitQubits(){
        val backend = LocalBackend()
        val circuit = Circuit(0)
        circuit.addGate(X(arrayListOf(1)))

        val exception = assertThrows(IllegalStateException::class.java){
            backend.execute(circuit)
        }
        assertEquals("Your Circuit must have at least 1 qubit!", exception.message)
    }

    @Test()
    fun testLocalBackendXGateInvalidSelectedQubit(){
        val backend = LocalBackend()
        val circuit = Circuit(1)
        circuit.addGate(X(arrayListOf(1)))

        val exception = assertThrows(IllegalStateException::class.java){
            backend.execute(circuit)
        }
        assertEquals("Invalid selected Qubit for X gate!", exception.message)
    }

    @Test
    fun testLocalBackendXGateZeroState(){
        val backend = LocalBackend()
        val circuit = Circuit(1)
        circuit.addGate(X(arrayListOf(0)))
        val result:Outcome = backend.execute(circuit)

        assertEquals(result.state.first().real, 0.0)
        assertEquals(result.state.first().imaginary, 0.0)

        assertEquals(result.state.last().real, 1.0)
        assertEquals(result.state.last().imaginary, 0.0)
    }

    @Test
    fun testLocalBackendXGateOneState(){
        val backend = LocalBackend()
        val circuit = Circuit(1)
        circuit.addGate(X(arrayListOf(0)))
        circuit.addGate(X(arrayListOf(0)))
        val result:Outcome = backend.execute(circuit)

        assertEquals(result.state.first().real, 1.0)
        assertEquals(result.state.first().imaginary, 0.0)

        assertEquals(result.state.last().real, 0.0)
        assertEquals(result.state.last().imaginary, 0.0)
    }

    @Test
    fun testLocalBackendXGateTwoQubitsTo01(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(X(arrayListOf(1)))
        val result:Outcome = backend.execute(circuit)

        assertEquals(result.state.first().real, 0.0)
        assertEquals(result.state.first().imaginary, 0.0)

        assertEquals(result.state[1].real, 1.0)
        assertEquals(result.state[1].imaginary, 0.0)

        assertEquals(result.state[2].real, 0.0)
        assertEquals(result.state[2].imaginary, 0.0)

        assertEquals(result.state[3].real, 0.0)
        assertEquals(result.state[3].imaginary, 0.0)
    }

    @Test
    fun testLocalBackendXGateTwoQubitsTo10(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(X(arrayListOf(0)))
        val result:Outcome = backend.execute(circuit)

        assertEquals(result.state.first().real, 0.0)
        assertEquals(result.state.first().imaginary, 0.0)

        assertEquals(result.state[1].real, 0.0)
        assertEquals(result.state[1].imaginary, 0.0)

        assertEquals(result.state[2].real, 1.0)
        assertEquals(result.state[2].imaginary, 0.0)

        assertEquals(result.state[3].real, 0.0)
        assertEquals(result.state[3].imaginary, 0.0)
    }

    @Test
    fun testLocalBackendXGateTwoQubitsTo11(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(X(arrayListOf(0)))
        circuit.addGate(X(arrayListOf(1)))
        val result:Outcome = backend.execute(circuit)

        assertEquals(result.state.first().real, 0.0)
        assertEquals(result.state.first().imaginary, 0.0)

        assertEquals(result.state[1].real, 0.0)
        assertEquals(result.state[1].imaginary, 0.0)

        assertEquals(result.state[2].real, 0.0)
        assertEquals(result.state[2].imaginary, 0.0)

        assertEquals(result.state[3].real, 1.0)
        assertEquals(result.state[3].imaginary, 0.0)
    }

    @Test
    fun testLocalBackendXGateTwoQubitsTo00(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(X(arrayListOf(0)))
        circuit.addGate(X(arrayListOf(1)))
        circuit.addGate(X(arrayListOf(0)))
        circuit.addGate(X(arrayListOf(1)))
        val result:Outcome = backend.execute(circuit)

        assertEquals(result.state.first().real, 1.0)
        assertEquals(result.state.first().imaginary, 0.0)

        assertEquals(result.state[1].real, 0.0)
        assertEquals(result.state[1].imaginary, 0.0)

        assertEquals(result.state[2].real, 0.0)
        assertEquals(result.state[2].imaginary, 0.0)

        assertEquals(result.state[3].real, 0.0)
        assertEquals(result.state[3].imaginary, 0.0)
    }

    @Test
    fun testLocalBackendXGateThreeQubitsTo001(){
        val backend = LocalBackend()
        val circuit = Circuit(3)
        circuit.addGate(X(arrayListOf(2)))
        val result:Outcome = backend.execute(circuit)

        assertEquals(result.state.first().real, 0.0)
        assertEquals(result.state.first().imaginary, 0.0)

        assertEquals(result.state[1].real, 1.0)
        assertEquals(result.state[1].imaginary, 0.0)

        for(i in 2..<8){
            assertEquals(result.state[i].real, 0.0)
            assertEquals(result.state[i].imaginary, 0.0)
        }
    }

    @Test
    fun testLocalBackendXGateThreeQubitsTo100(){
        val backend = LocalBackend()
        val circuit = Circuit(3)
        circuit.addGate(X(arrayListOf(0)))
        val result:Outcome = backend.execute(circuit)


        assertEquals(result.state[4].real, 1.0)
        assertEquals(result.state[4].imaginary, 0.0)

        for(i in 0..<8){
            if(i == 4){
                continue
            }
            assertEquals(result.state[i].real, 0.0)
            assertEquals(result.state[i].imaginary, 0.0)
        }
    }

    @Test
    fun testLocalBackendXGateThreeQubitsTo101(){
        val backend = LocalBackend()
        val circuit = Circuit(3)
        circuit.addGate(X( arrayListOf(0)))
        circuit.addGate(X( arrayListOf(2)))
        val result:Outcome = backend.execute(circuit)


        assertEquals(result.state[5].real, 1.0)
        assertEquals(result.state[5].imaginary, 0.0)

        for(i in 0..<8){
            if(i == 5){
                continue
            }
            assertEquals(result.state[i].real, 0.0)
            assertEquals(result.state[i].imaginary, 0.0)
        }
    }

    @Test()
    fun testLocalBackendCNOTGateInvalidNumberOfQubits(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(CNOT( arrayListOf(1)))

        val exception = assertThrows(IllegalStateException::class.java){
            backend.execute(circuit)
        }
        assertEquals("Invalid number of Qubits for CNOT gate!", exception.message)
    }

    @Test()
    fun testLocalBackendCNOTGateInvalidNumberOfCircuitQubits(){
        val backend = LocalBackend()
        val circuit = Circuit(1)
        circuit.addGate(CNOT( arrayListOf(0,1)))

        val exception = assertThrows(IllegalStateException::class.java){
            backend.execute(circuit)
        }
        assertEquals("Your Circuit must have at least 2 qubits!", exception.message)
    }

    @Test()
    fun testLocalBackendCNOTGateInvalidControlQubit(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(CNOT( arrayListOf(2,0)))

        val exception = assertThrows(IllegalStateException::class.java){
            backend.execute(circuit)
        }
        assertEquals("Invalid control Qubit for CNOT gate!", exception.message)
    }

    @Test()
    fun testLocalBackendCNOTGateInvalidTargetQubit(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(CNOT( arrayListOf(0,2)))

        val exception = assertThrows(IllegalStateException::class.java){
            backend.execute(circuit)
        }
        assertEquals("Invalid target Qubit for CNOT gate!", exception.message)
    }

    @Test
    fun testLocalBackendCNOTFor00State(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(CNOT( arrayListOf(0,1)))
        val result:Outcome = backend.execute(circuit)

        assertEquals(result.state.first().real, 1.0)
        assertEquals(result.state.first().imaginary, 0.0)

        assertEquals(result.state[1].real, 0.0)
        assertEquals(result.state[1].imaginary, 0.0)

        assertEquals(result.state[2].real, 0.0)
        assertEquals(result.state[2].imaginary, 0.0)

        assertEquals(result.state[3].real, 0.0)
        assertEquals(result.state[3].imaginary, 0.0)
    }

    @Test
    fun testLocalBackendCNOTFor01State(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(X( arrayListOf(1)))
        circuit.addGate(CNOT( arrayListOf(0,1)))
        val result:Outcome = backend.execute(circuit)

        assertEquals(result.state.first().real, 0.0)
        assertEquals(result.state.first().imaginary, 0.0)

        assertEquals(result.state[1].real, 1.0)
        assertEquals(result.state[1].imaginary, 0.0)

        assertEquals(result.state[2].real, 0.0)
        assertEquals(result.state[2].imaginary, 0.0)

        assertEquals(result.state[3].real, 0.0)
        assertEquals(result.state[3].imaginary, 0.0)
    }

    @Test
    fun testLocalBackendCNOTFor10State(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(X( arrayListOf(0)))
        circuit.addGate(CNOT( arrayListOf(0,1)))
        val result:Outcome = backend.execute(circuit)

        assertEquals(result.state.first().real, 0.0)
        assertEquals(result.state.first().imaginary, 0.0)

        assertEquals(result.state[1].real, 0.0)
        assertEquals(result.state[1].imaginary, 0.0)

        assertEquals(result.state[2].real, 0.0)
        assertEquals(result.state[2].imaginary, 0.0)

        assertEquals(result.state[3].real, 1.0)
        assertEquals(result.state[3].imaginary, 0.0)
    }

    @Test
    fun testLocalBackendCNOTFor11State(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(X( arrayListOf(0)))
        circuit.addGate(X( arrayListOf(1)))
        circuit.addGate(CNOT( arrayListOf(0,1)))
        val result:Outcome = backend.execute(circuit)

        assertEquals(result.state.first().real, 0.0)
        assertEquals(result.state.first().imaginary, 0.0)

        assertEquals(result.state[1].real, 0.0)
        assertEquals(result.state[1].imaginary, 0.0)

        assertEquals(result.state[2].real, 1.0)
        assertEquals(result.state[2].imaginary, 0.0)

        assertEquals(result.state[3].real, 0.0)
        assertEquals(result.state[3].imaginary, 0.0)
    }

    @Test
    fun testLocalBackendCNOTFor101State(){
        val backend = LocalBackend()
        val circuit = Circuit(3)
        circuit.addGate(X( arrayListOf(0)))
        circuit.addGate(X( arrayListOf(2)))
        circuit.addGate(CNOT( arrayListOf(0,2)))
        val result:Outcome = backend.execute(circuit)

        assertEquals(result.state[4].real, 1.0)
        assertEquals(result.state[4].imaginary, 0.0)

        for(i in 0..<8){
            if(i == 4){
                continue
            }
            assertEquals(result.state[i].real, 0.0)
            assertEquals(result.state[i].imaginary, 0.0)
        }

    }

    @Test()
    fun testLocalBackendZGateInvalidNumberOfQubits(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(Z( arrayListOf(1,2)))

        val exception = assertThrows(IllegalStateException::class.java){
            backend.execute(circuit)
        }
        assertEquals("Invalid number of Qubits for Z gate!", exception.message)
    }

    @Test()
    fun testLocalBackendZGateInvalidNumberOfCircuitQubits(){
        val backend = LocalBackend()
        val circuit = Circuit(0)
        circuit.addGate(Z( arrayListOf(1)))

        val exception = assertThrows(IllegalStateException::class.java){
            backend.execute(circuit)
        }
        assertEquals("Your Circuit must have at least 1 qubit!", exception.message)
    }

    @Test()
    fun testLocalBackendZGateInvalidSelectedQubit(){
        val backend = LocalBackend()
        val circuit = Circuit(1)
        circuit.addGate(Z( arrayListOf(1)))

        val exception = assertThrows(IllegalStateException::class.java){
            backend.execute(circuit)
        }
        assertEquals("Invalid selected Qubit for Z gate!", exception.message)
    }

    @Test
    fun testLocalBackendZGateZeroState(){
        val backend = LocalBackend()
        val circuit = Circuit(1)
        circuit.addGate(Z( arrayListOf(0)))
        val result:Outcome = backend.execute(circuit)

        assertEquals(result.state.first().real, 1.0)
        assertEquals(result.state.first().imaginary, 0.0)

        assertEquals(result.state.last().real, 0.0)
        assertEquals(result.state.last().imaginary, 0.0)
    }

    @Test
    fun testLocalBackendZGateOneState(){
        val backend = LocalBackend()
        val circuit = Circuit(1)
        circuit.addGate(X( arrayListOf(0)))
        circuit.addGate(Z( arrayListOf(0)))
        val result:Outcome = backend.execute(circuit)

        assertEquals(result.state.first().real, 0.0)
        assertEquals(result.state.first().imaginary, 0.0)

        assertEquals(result.state.last().real, -1.0)
        assertEquals(result.state.last().imaginary, 0.0)
    }

    @Test
    fun testLocalBackendZGate01State(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(X( arrayListOf(1)))
        circuit.addGate(Z( arrayListOf(1)))
        val result:Outcome = backend.execute(circuit)

        assertEquals(result.state.first().real, 0.0)
        assertEquals(result.state.first().imaginary, 0.0)

        assertEquals(result.state[1].real, -1.0)
        assertEquals(result.state[1].imaginary, 0.0)

        assertEquals(result.state[2].real, 0.0)
        assertEquals(result.state[2].imaginary, 0.0)

        assertEquals(result.state[3].real, 0.0)
        assertEquals(result.state[3].imaginary, 0.0)
    }

    @Test()
    fun testLocalBackendCZGateInvalidNumberOfQubits(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(CZ( arrayListOf(1)))

        val exception = assertThrows(IllegalStateException::class.java){
            backend.execute(circuit)
        }
        assertEquals("Invalid number of Qubits for CZ gate!", exception.message)
    }

    @Test()
    fun testLocalBackendCZGateInvalidNumberOfCircuitQubits(){
        val backend = LocalBackend()
        val circuit = Circuit(1)
        circuit.addGate(CZ( arrayListOf(0,1)))

        val exception = assertThrows(IllegalStateException::class.java){
            backend.execute(circuit)
        }
        assertEquals("Your Circuit must have at least 2 qubits!", exception.message)
    }

    @Test()
    fun testLocalBackendCZGateInvalidControlQubit(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(CZ( arrayListOf(2,0)))

        val exception = assertThrows(IllegalStateException::class.java){
            backend.execute(circuit)
        }
        assertEquals("Invalid control Qubit for CZ gate!", exception.message)
    }

    @Test()
    fun testLocalBackendCZGateInvalidTargetQubit(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(CZ( arrayListOf(0,2)))

        val exception = assertThrows(IllegalStateException::class.java){
            backend.execute(circuit)
        }
        assertEquals("Invalid target Qubit for CZ gate!", exception.message)
    }

    @Test
    fun testLocalBackendCZFor00State(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(CZ( arrayListOf(0,1)))
        val result:Outcome = backend.execute(circuit)

        assertEquals(result.state.first().real, 1.0)
        assertEquals(result.state.first().imaginary, 0.0)

        assertEquals(result.state[1].real, 0.0)
        assertEquals(result.state[1].imaginary, 0.0)

        assertEquals(result.state[2].real, 0.0)
        assertEquals(result.state[2].imaginary, 0.0)

        assertEquals(result.state[3].real, 0.0)
        assertEquals(result.state[3].imaginary, 0.0)
    }

    @Test
    fun testLocalBackendCZFor01State(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(X( arrayListOf(1)))
        circuit.addGate(CZ( arrayListOf(0,1)))
        val result:Outcome = backend.execute(circuit)

        assertEquals(result.state.first().real, 0.0)
        assertEquals(result.state.first().imaginary, 0.0)

        assertEquals(result.state[1].real, 1.0)
        assertEquals(result.state[1].imaginary, 0.0)

        assertEquals(result.state[2].real, 0.0)
        assertEquals(result.state[2].imaginary, 0.0)

        assertEquals(result.state[3].real, 0.0)
        assertEquals(result.state[3].imaginary, 0.0)
    }

    @Test
    fun testLocalBackendCZFor10State(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(X( arrayListOf(0)))
        circuit.addGate(CZ( arrayListOf(0,1)))
        val result:Outcome = backend.execute(circuit)

        assertEquals(result.state.first().real, 0.0)
        assertEquals(result.state.first().imaginary, 0.0)

        assertEquals(result.state[1].real, 0.0)
        assertEquals(result.state[1].imaginary, 0.0)

        assertEquals(result.state[2].real, 1.0)
        assertEquals(result.state[2].imaginary, 0.0)

        assertEquals(result.state[3].real, 0.0)
        assertEquals(result.state[3].imaginary, 0.0)
    }

    @Test
    fun testLocalBackendCZFor11State(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(X( arrayListOf(0)))
        circuit.addGate(X( arrayListOf(1)))
        circuit.addGate(CZ( arrayListOf(0,1)))
        val result:Outcome = backend.execute(circuit)

        assertEquals(result.state.first().real, 0.0)
        assertEquals(result.state.first().imaginary, 0.0)

        assertEquals(result.state[1].real, 0.0)
        assertEquals(result.state[1].imaginary, 0.0)

        assertEquals(result.state[2].real, 0.0)
        assertEquals(result.state[2].imaginary, 0.0)

        assertEquals(result.state[3].real, -1.0)
        assertEquals(result.state[3].imaginary, 0.0)
    }

    @Test
    fun testLocalBackendCZFor101State(){
        val backend = LocalBackend()
        val circuit = Circuit(3)
        circuit.addGate(X( arrayListOf(0)))
        circuit.addGate(X( arrayListOf(2)))
        circuit.addGate(CZ( arrayListOf(0,2)))
        val result:Outcome = backend.execute(circuit)

        assertEquals(result.state[5].real, -1.0)
        assertEquals(result.state[5].imaginary, 0.0)

        for(i in 0..<8){
            if(i == 5){
                continue;
            }
            assertEquals(result.state[i].real, 0.0)
            assertEquals(result.state[i].imaginary, 0.0)
        }
    }

    @Test()
    fun testLocalBackendSWAPGateInvalidNumberOfQubits(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(SWAP( arrayListOf(1)))

        val exception = assertThrows(IllegalStateException::class.java){
            backend.execute(circuit)
        }
        assertEquals("Invalid number of Qubits for SWAP gate!", exception.message)
    }

    @Test()
    fun testLocalBackendSWAPGateInvalidNumberOfCircuitQubits(){
        val backend = LocalBackend()
        val circuit = Circuit(1)
        circuit.addGate(SWAP( arrayListOf(0,1)))

        val exception = assertThrows(IllegalStateException::class.java){
            backend.execute(circuit)
        }
        assertEquals("Your Circuit must have at least 2 qubits!", exception.message)
    }

    @Test()
    fun testLocalBackendSWAPGateInvalidControlQubit(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(SWAP( arrayListOf(2,0)))

        val exception = assertThrows(IllegalStateException::class.java){
            backend.execute(circuit)
        }
        assertEquals("Invalid control Qubit for SWAP gate!", exception.message)
    }

    @Test()
    fun testLocalBackendSWAPGateInvalidTargetQubit(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(SWAP( arrayListOf(0,2)))

        val exception = assertThrows(IllegalStateException::class.java){
            backend.execute(circuit)
        }
        assertEquals("Invalid target Qubit for SWAP gate!", exception.message)
    }

    @Test
    fun testLocalBackendSWAPFor00State(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(SWAP( arrayListOf(0,1)))
        val result:Outcome = backend.execute(circuit)

        assertEquals(result.state.first().real, 1.0)
        assertEquals(result.state.first().imaginary, 0.0)

        assertEquals(result.state[1].real, 0.0)
        assertEquals(result.state[1].imaginary, 0.0)

        assertEquals(result.state[2].real, 0.0)
        assertEquals(result.state[2].imaginary, 0.0)

        assertEquals(result.state[3].real, 0.0)
        assertEquals(result.state[3].imaginary, 0.0)
    }

    @Test
    fun testLocalBackendSWAPFor01State(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(X( arrayListOf(1)))
        circuit.addGate(SWAP( arrayListOf(0,1)))
        val result:Outcome = backend.execute(circuit)

        assertEquals(result.state.first().real, 0.0)
        assertEquals(result.state.first().imaginary, 0.0)

        assertEquals(result.state[1].real, 0.0)
        assertEquals(result.state[1].imaginary, 0.0)

        assertEquals(result.state[2].real, 1.0)
        assertEquals(result.state[2].imaginary, 0.0)

        assertEquals(result.state[3].real, 0.0)
        assertEquals(result.state[3].imaginary, 0.0)
    }

    @Test
    fun testLocalBackendSWAPFor10State(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(X( arrayListOf(0)))
        circuit.addGate(SWAP( arrayListOf(0,1)))
        val result:Outcome = backend.execute(circuit)

        assertEquals(result.state.first().real, 0.0)
        assertEquals(result.state.first().imaginary, 0.0)

        assertEquals(result.state[1].real, 1.0)
        assertEquals(result.state[1].imaginary, 0.0)

        assertEquals(result.state[2].real, 0.0)
        assertEquals(result.state[2].imaginary, 0.0)

        assertEquals(result.state[3].real, 0.0)
        assertEquals(result.state[3].imaginary, 0.0)
    }

    @Test
    fun testLocalBackendSWAPFor11State(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(X( arrayListOf(0)))
        circuit.addGate(X( arrayListOf(1)))
        circuit.addGate(SWAP( arrayListOf(0,1)))
        val result:Outcome = backend.execute(circuit)

        assertEquals(result.state.first().real, 0.0)
        assertEquals(result.state.first().imaginary, 0.0)

        assertEquals(result.state[1].real, 0.0)
        assertEquals(result.state[1].imaginary, 0.0)

        assertEquals(result.state[2].real, 0.0)
        assertEquals(result.state[2].imaginary, 0.0)

        assertEquals(result.state[3].real, 1.0)
        assertEquals(result.state[3].imaginary, 0.0)
    }

    @Test
    fun testLocalBackendSWAPFor100State(){
        val backend = LocalBackend()
        val circuit = Circuit(3)
        circuit.addGate(X( arrayListOf(0)))
        circuit.addGate(SWAP( arrayListOf(0,2)))
        val result:Outcome = backend.execute(circuit)

        assertEquals(result.state[1].real, 1.0)
        assertEquals(result.state[1].imaginary, 0.0)

        for(i in 0..<8){
            if(i == 1){
                continue
            }

            assertEquals(result.state[i].real, 0.0)
            assertEquals(result.state[i].imaginary, 0.0)
        }
    }

    @Test
    fun testLocalBackendSWAPFor011State(){
        val backend = LocalBackend()
        val circuit = Circuit(3)
        circuit.addGate(X( arrayListOf(1)))
        circuit.addGate(X( arrayListOf(2)))
        circuit.addGate(SWAP( arrayListOf(0,2)))
        val result:Outcome = backend.execute(circuit)

        assertEquals(result.state[6].real, 1.0)
        assertEquals(result.state[6].imaginary, 0.0)

        for(i in 0..<8){
            if(i == 6){
                continue
            }

            assertEquals(result.state[i].real, 0.0)
            assertEquals(result.state[i].imaginary, 0.0)
        }
    }

    @Test()
    fun testLocalBackendHGateInvalidNumberOfQubits(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(H( arrayListOf(1,2)))

        val exception = assertThrows(IllegalStateException::class.java){
            backend.execute(circuit)
        }
        assertEquals("Invalid number of Qubits for H gate!", exception.message)
    }

    @Test()
    fun testLocalBackendHGateInvalidNumberOfCircuitQubits(){
        val backend = LocalBackend()
        val circuit = Circuit(0)
        circuit.addGate(H( arrayListOf(1)))

        val exception = assertThrows(IllegalStateException::class.java){
            backend.execute(circuit)
        }
        assertEquals("Your Circuit must have at least 1 qubit!", exception.message)
    }

    @Test()
    fun testLocalBackendHGateInvalidSelectedQubit(){
        val backend = LocalBackend()
        val circuit = Circuit(1)
        circuit.addGate(Z( arrayListOf(1)))

        val exception = assertThrows(IllegalStateException::class.java){
            backend.execute(circuit)
        }
        assertEquals("Invalid selected Qubit for Z gate!", exception.message)
    }

    @Test
    fun testLocalBackendHGateZeroState(){
        val backend = LocalBackend()
        val circuit = Circuit(1)
        circuit.addGate(H( arrayListOf(0)))
        val result:Outcome = backend.execute(circuit)

        assertEquals(result.state.first().real, HALF_PROB)
        assertEquals(result.state.first().imaginary, 0.0)

        assertEquals(result.state.last().real, HALF_PROB)
        assertEquals(result.state.last().imaginary, 0.0)
    }

    @Test
    fun testLocalBackendHGateOneState(){
        val backend = LocalBackend()
        val circuit = Circuit(1)
        circuit.addGate(X( arrayListOf(0)))
        circuit.addGate(H( arrayListOf(0)))
        val result:Outcome = backend.execute(circuit)

        assertEquals(result.state.first().real, HALF_PROB)
        assertEquals(result.state.first().imaginary, 0.0)

        assertEquals(result.state.last().real, -HALF_PROB)
        assertEquals(result.state.last().imaginary, 0.0)
    }

    @Test
    fun testLocalBackendHGate00State(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(H( arrayListOf(0)))
        circuit.addGate(H( arrayListOf(1)))
        val result:Outcome = backend.execute(circuit)

        for (i in 0..<4){
            assertEquals(round(result.state[i].real*10)/10, 0.5)
            assertEquals(result.state[i].imaginary, 0.0)
        }
    }

    @Test
    fun testLocalBackendHGate01State(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(X( arrayListOf(1)))
        circuit.addGate(H( arrayListOf(0)))
        circuit.addGate(H( arrayListOf(1)))
        val result:Outcome = backend.execute(circuit)

        assertEquals(round(result.state[0].real*10)/10, 0.5)
        assertEquals(result.state[0].imaginary, 0.0)

        assertEquals(round(result.state[1].real*10)/10, -0.5)
        assertEquals(result.state[1].imaginary, 0.0)

        assertEquals(round(result.state[2].real*10)/10, 0.5)
        assertEquals(result.state[2].imaginary, 0.0)

        assertEquals(round(result.state[3].real*10)/10, -0.5)
        assertEquals(result.state[3].imaginary, 0.0)
    }

    @Test
    fun testLocalBackendHGate10State(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(X( arrayListOf(0)))
        circuit.addGate(H( arrayListOf(0)))
        circuit.addGate(H( arrayListOf(1)))
        val result:Outcome = backend.execute(circuit)

        assertEquals(round(result.state[0].real*10)/10, 0.5)
        assertEquals(result.state[0].imaginary, 0.0)

        assertEquals(round(result.state[1].real*10)/10, 0.5)
        assertEquals(result.state[1].imaginary, 0.0)

        assertEquals(round(result.state[2].real*10)/10, -0.5)
        assertEquals(result.state[2].imaginary, 0.0)

        assertEquals(round(result.state[3].real*10)/10, -0.5)
        assertEquals(result.state[3].imaginary, 0.0)
    }

    @Test
    fun testLocalBackendHGate11State(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(X( arrayListOf(0)))
        circuit.addGate(X( arrayListOf(1)))
        circuit.addGate(H( arrayListOf(0)))
        circuit.addGate(H( arrayListOf(1)))
        val result:Outcome = backend.execute(circuit)

        assertEquals(round(result.state[0].real*10)/10, 0.5)
        assertEquals(result.state[0].imaginary, 0.0)

        assertEquals(round(result.state[1].real*10)/10, -0.5)
        assertEquals(result.state[1].imaginary, 0.0)

        assertEquals(round(result.state[2].real*10)/10, -0.5)
        assertEquals(result.state[2].imaginary, 0.0)

        assertEquals(round(result.state[3].real*10)/10, 0.5)
        assertEquals(result.state[3].imaginary, 0.0)
    }

    @Test
    fun testLocalBackendHGateHII(){
        val backend = LocalBackend()
        val circuit = Circuit(3)
        circuit.addGate(H( arrayListOf(0)))
        val result:Outcome = backend.execute(circuit)

        assertEquals(result.state[0].real, HALF_PROB)
        assertEquals(result.state[0].imaginary, 0.0)

        assertEquals(result.state[4].real, HALF_PROB)
        assertEquals(result.state[4].imaginary, 0.0)

        for(i in 0..<8){
            if(i == 0 || i == 4){
                continue
            }

            assertEquals(result.state[i].real, 0.0)
            assertEquals(result.state[i].imaginary, 0.0)
        }
    }

    @Test
    fun testLocalBackendHGateIHI(){
        val backend = LocalBackend()
        val circuit = Circuit(3)
        circuit.addGate(H( arrayListOf(1)))
        val result:Outcome = backend.execute(circuit)

        assertEquals(result.state[0].real, HALF_PROB)
        assertEquals(result.state[0].imaginary, 0.0)

        assertEquals(result.state[2].real, HALF_PROB)
        assertEquals(result.state[2].imaginary, 0.0)

        for(i in 0..<8){
            if(i == 0 || i == 2){
                continue
            }

            assertEquals(result.state[i].real, 0.0)
            assertEquals(result.state[i].imaginary, 0.0)
        }
    }

    @Test
    fun testLocalBackendHGateIIH(){
        val backend = LocalBackend()
        val circuit = Circuit(3)
        circuit.addGate(H( arrayListOf(2)))
        val result:Outcome = backend.execute(circuit)

        assertEquals(result.state[0].real, HALF_PROB)
        assertEquals(result.state[0].imaginary, 0.0)

        assertEquals(result.state[1].real, HALF_PROB)
        assertEquals(result.state[1].imaginary, 0.0)

        for(i in 0..<8){
            if(i == 0 || i == 1){
                continue
            }

            assertEquals(result.state[i].real, 0.0)
            assertEquals(result.state[i].imaginary, 0.0)
        }
    }

    @Test
    fun testLocalBackendBellStatePhiPlus(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(H( arrayListOf(0)))
        circuit.addGate(CNOT( arrayListOf(0,1)))
        val result:Outcome = backend.execute(circuit)

        assertEquals(result.state[0].real, HALF_PROB)
        assertEquals(result.state[0].imaginary, 0.0)

        assertEquals(result.state[1].real, 0.0)
        assertEquals(result.state[1].imaginary, 0.0)

        assertEquals(result.state[2].real, 0.0)
        assertEquals(result.state[2].imaginary, 0.0)

        assertEquals(result.state[3].real, HALF_PROB)
        assertEquals(result.state[3].imaginary, 0.0)
    }

    @Test
    fun testLocalBackendBellStatePhiMinus(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(H( arrayListOf(0)))
        circuit.addGate(Z( arrayListOf(0)))
        circuit.addGate(CNOT( arrayListOf(0,1)))
        val result:Outcome = backend.execute(circuit)

        assertEquals(result.state[0].real, HALF_PROB)
        assertEquals(result.state[0].imaginary, 0.0)

        assertEquals(result.state[1].real, 0.0)
        assertEquals(result.state[1].imaginary, 0.0)

        assertEquals(result.state[2].real, 0.0)
        assertEquals(result.state[2].imaginary, 0.0)

        assertEquals(result.state[3].real, -HALF_PROB)
        assertEquals(result.state[3].imaginary, 0.0)
    }

    @Test
    fun testLocalBackendBellStatePsiPlus(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(H( arrayListOf(0)))
        circuit.addGate(X( arrayListOf(1)))
        circuit.addGate(CNOT( arrayListOf(0,1)))
        val result:Outcome = backend.execute(circuit)

        assertEquals(result.state[0].real, 0.0)
        assertEquals(result.state[0].imaginary, 0.0)

        assertEquals(result.state[1].real, HALF_PROB)
        assertEquals(result.state[1].imaginary, 0.0)

        assertEquals(result.state[2].real, HALF_PROB)
        assertEquals(result.state[2].imaginary, 0.0)

        assertEquals(result.state[3].real, 0.0)
        assertEquals(result.state[3].imaginary, 0.0)
    }

    @Test
    fun testLocalBackendBellStatePsiMinus(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(H( arrayListOf(0)))
        circuit.addGate(X( arrayListOf(1)))
        circuit.addGate(Z( arrayListOf(0)))
        circuit.addGate(CNOT( arrayListOf(0,1)))
        val result:Outcome = backend.execute(circuit)

        assertEquals(result.state[0].real, 0.0)
        assertEquals(result.state[0].imaginary, 0.0)

        assertEquals(result.state[1].real, HALF_PROB)
        assertEquals(result.state[1].imaginary, 0.0)

        assertEquals(result.state[2].real, -HALF_PROB)
        assertEquals(result.state[2].imaginary, 0.0)

        assertEquals(result.state[3].real, 0.0)
        assertEquals(result.state[3].imaginary, 0.0)
    }

    @Test
    fun testLocalBackendBellStatePsiMinus2(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(H( arrayListOf(0)))
        circuit.addGate(X( arrayListOf(1)))
        circuit.addGate(Z( arrayListOf(0)))
        circuit.addGate(Z( arrayListOf(1)))
        circuit.addGate(CNOT( arrayListOf(0,1)))
        val result:Outcome = backend.execute(circuit)

        // if you do this circuit using a little endian pattern, the phase is going to be inserted on 10
        // however, once our first qubit is the left-most, this will be the reverse

        assertEquals(result.state[0].real, 0.0)
        assertEquals(result.state[0].imaginary, 0.0)

        assertEquals(result.state[1].real, -HALF_PROB)
        assertEquals(result.state[1].imaginary, 0.0)

        assertEquals(result.state[2].real, HALF_PROB)
        assertEquals(result.state[2].imaginary, 0.0)

        assertEquals(result.state[3].real, 0.0)
        assertEquals(result.state[3].imaginary, 0.0)
    }

    @Test
    fun testLocalBackendGHZState(){
        val backend = LocalBackend()
        val circuit = Circuit(3)
        circuit.addGate(H( arrayListOf(0)))
        circuit.addGate(CNOT( arrayListOf(0,1)))
        circuit.addGate(CNOT( arrayListOf(1,2)))
        val result:Outcome = backend.execute(circuit)

        assertEquals(result.state[0].real, HALF_PROB)
        assertEquals(result.state[0].imaginary, 0.0)

        assertEquals(result.state[7].real, HALF_PROB)
        assertEquals(result.state[7].imaginary, 0.0)

        for(i in 0..<8){
            if(i == 0 || i == 7){
                continue
            }

            assertEquals(result.state[i].real, 0.0)
            assertEquals(result.state[i].imaginary, 0.0)
        }
    }

    @Test
    fun testLocalBackendHCnotReverseSimpleSuperposition(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(H( arrayListOf(0)))
        circuit.addGate(CNOT( arrayListOf(1,0)))
        val result:Outcome = backend.execute(circuit)

        assertEquals(result.state[0].real, HALF_PROB)
        assertEquals(result.state[0].imaginary, 0.0)

        assertEquals(result.state[1].real, 0.0)
        assertEquals(result.state[1].imaginary, 0.0)

        assertEquals(result.state[2].real, HALF_PROB)
        assertEquals(result.state[2].imaginary, 0.0)

        assertEquals(result.state[3].real, 0.0)
        assertEquals(result.state[3].imaginary, 0.0)
    }

    @Test
    fun testLocalBackendReverseBellState(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(H( arrayListOf(1)))
        circuit.addGate(CNOT( arrayListOf(1,0)))
        val result:Outcome = backend.execute(circuit)

        assertEquals(result.state[0].real, HALF_PROB)
        assertEquals(result.state[0].imaginary, 0.0)

        assertEquals(result.state[1].real, 0.0)
        assertEquals(result.state[1].imaginary, 0.0)

        assertEquals(result.state[2].real, 0.0)
        assertEquals(result.state[2].imaginary, 0.0)

        assertEquals(result.state[3].real, HALF_PROB)
        assertEquals(result.state[3].imaginary, 0.0)
    }

    @Test()
    fun testLocalBackendRXGateInvalidNumberOfParams(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(RX(arrayListOf(1), arrayListOf()))

        val exception = assertThrows(IllegalStateException::class.java){
            backend.execute(circuit)
        }
        assertEquals("Invalid number of Params for RX gate!", exception.message)
    }

    @Test()
    fun testLocalBackendRXGateInvalidNumberOfQubits(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(RX( arrayListOf(1,2), arrayListOf(0.0)))

        val exception = assertThrows(IllegalStateException::class.java){
            backend.execute(circuit)
        }
        assertEquals("Invalid number of Qubits for RX gate!", exception.message)
    }

    @Test()
    fun testLocalBackendRXGateInvalidNumberOfCircuitQubits(){
        val backend = LocalBackend()
        val circuit = Circuit(0)
        circuit.addGate(RX( arrayListOf(1), arrayListOf(0.0)))

        val exception = assertThrows(IllegalStateException::class.java){
            backend.execute(circuit)
        }
        assertEquals("Your Circuit must have at least 1 qubit!", exception.message)
    }

    @Test()
    fun testLocalBackendRXGateInvalidSelectedQubit(){
        val backend = LocalBackend()
        val circuit = Circuit(1)
        circuit.addGate(RX( arrayListOf(1), arrayListOf(0.0)))

        val exception = assertThrows(IllegalStateException::class.java){
            backend.execute(circuit)
        }
        assertEquals("Invalid selected Qubit for RX gate!", exception.message)
    }

    @Test
    fun testLocalBackendRXGateZeroState(){
        val backend = LocalBackend()
        val circuit = Circuit(1)
        circuit.addGate(RX( arrayListOf(0), arrayListOf(PI)))
        val result:Outcome = backend.execute(circuit)

        assertEquals(round(result.state.first().real), 0.0)
        assertEquals(result.state.first().imaginary, 0.0)

        assertEquals(round(result.state.last().real), 0.0)
        assertEquals(result.state.last().imaginary, -1.0)
    }

    @Test
    fun testLocalBackendRXGateOneState(){
        val backend = LocalBackend()
        val circuit = Circuit(1)
        circuit.addGate(X( arrayListOf(0)))
        circuit.addGate(RX( arrayListOf(0), arrayListOf(PI)))
        val result:Outcome = backend.execute(circuit)

        assertEquals(round(result.state.first().real), 0.0)
        assertEquals(result.state.first().imaginary, -1.0)

        assertEquals(round(result.state.last().real), 0.0)
        assertEquals(result.state.last().imaginary, 0.0)
    }

    @Test()
    fun testLocalBackendRYGateInvalidNumberOfParams(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(RY( arrayListOf(1), arrayListOf()))

        val exception = assertThrows(IllegalStateException::class.java){
            backend.execute(circuit)
        }
        assertEquals("Invalid number of Params for RY gate!", exception.message)
    }

    @Test()
    fun testLocalBackendRYGateInvalidNumberOfQubits(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(RY( arrayListOf(1,2), arrayListOf(0.0)))

        val exception = assertThrows(IllegalStateException::class.java){
            backend.execute(circuit)
        }
        assertEquals("Invalid number of Qubits for RY gate!", exception.message)
    }

    @Test()
    fun testLocalBackendRYGateInvalidNumberOfCircuitQubits(){
        val backend = LocalBackend()
        val circuit = Circuit(0)
        circuit.addGate(RY( arrayListOf(1), arrayListOf(0.0)))

        val exception = assertThrows(IllegalStateException::class.java){
            backend.execute(circuit)
        }
        assertEquals("Your Circuit must have at least 1 qubit!", exception.message)
    }

    @Test()
    fun testLocalBackendRYGateInvalidSelectedQubit(){
        val backend = LocalBackend()
        val circuit = Circuit(1)
        circuit.addGate(RY( arrayListOf(1), arrayListOf(0.0)))

        val exception = assertThrows(IllegalStateException::class.java){
            backend.execute(circuit)
        }
        assertEquals("Invalid selected Qubit for RY gate!", exception.message)
    }

    @Test
    fun testLocalBackendRYGateZeroState(){
        val backend = LocalBackend()
        val circuit = Circuit(1)
        circuit.addGate(RY( arrayListOf(0), arrayListOf(PI)))
        val result:Outcome = backend.execute(circuit)

        assertEquals(round(result.state.first().real), 0.0)
        assertEquals(result.state.first().imaginary, 0.0)

        assertEquals(round(result.state.last().real), 1.0)
        assertEquals(result.state.last().imaginary, 0.0)
    }

    @Test
    fun testLocalBackendRYGateOneState(){
        val backend = LocalBackend()
        val circuit = Circuit(1)
        circuit.addGate(X( arrayListOf(0)))
        circuit.addGate(RY( arrayListOf(0), arrayListOf(PI)))
        val result:Outcome = backend.execute(circuit)

        assertEquals(round(result.state.first().real), -1.0)
        assertEquals(result.state.first().imaginary, 0.0)

        assertEquals(round(result.state.last().real), 0.0)
        assertEquals(result.state.last().imaginary, 0.0)
    }

    @Test()
    fun testLocalBackendRZGateInvalidNumberOfParams(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(RZ( arrayListOf(1), arrayListOf()))

        val exception = assertThrows(IllegalStateException::class.java){
            backend.execute(circuit)
        }
        assertEquals("Invalid number of Params for RZ gate!", exception.message)
    }

    @Test()
    fun testLocalBackendRZGateInvalidNumberOfQubits(){
        val backend = LocalBackend()
        val circuit = Circuit(2)
        circuit.addGate(RZ( arrayListOf(1,2), arrayListOf(PI)))

        val exception = assertThrows(IllegalStateException::class.java){
            backend.execute(circuit)
        }
        assertEquals("Invalid number of Qubits for RZ gate!", exception.message)
    }

    @Test()
    fun testLocalBackendRZGateInvalidNumberOfCircuitQubits(){
        val backend = LocalBackend()
        val circuit = Circuit(0)
        circuit.addGate(RZ( arrayListOf(1), arrayListOf(PI)))

        val exception = assertThrows(IllegalStateException::class.java){
            backend.execute(circuit)
        }
        assertEquals("Your Circuit must have at least 1 qubit!", exception.message)
    }

    @Test()
    fun testLocalBackendRZGateInvalidSelectedQubit(){
        val backend = LocalBackend()
        val circuit = Circuit(1)
        circuit.addGate(RZ( arrayListOf(1), arrayListOf(PI)))

        val exception = assertThrows(IllegalStateException::class.java){
            backend.execute(circuit)
        }
        assertEquals("Invalid selected Qubit for RZ gate!", exception.message)
    }

    @Test
    fun testLocalBackendRZGateZeroState(){
        val backend = LocalBackend()
        val circuit = Circuit(1)
        circuit.addGate(RZ( arrayListOf(0), arrayListOf(PI)))
        val result:Outcome = backend.execute(circuit)

        assertEquals(round(result.state.first().real), 0.0)
        assertEquals(result.state.first().imaginary, -1.0)

        assertEquals(round(result.state.last().real), 0.0)
        assertEquals(result.state.last().imaginary, 0.0)
    }

    @Test
    fun testLocalBackendRZGateOneState(){
        val backend = LocalBackend()
        val circuit = Circuit(1)
        circuit.addGate(X( arrayListOf(0)))
        circuit.addGate(RZ( arrayListOf(0), arrayListOf(PI)))
        val result:Outcome = backend.execute(circuit)

        assertEquals(result.state.first().real, 0.0)
        assertEquals(result.state.first().imaginary, 0.0)

        assertEquals(round(result.state.last().real), 0.0)
        assertEquals(result.state.last().imaginary, 1.0)
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
    // TODO: TEST QASM
    // TODO: TEST TO DIST
}