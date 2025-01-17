import kotlin.test.Test
import org.junit.jupiter.api.Assertions.assertEquals

class CircuitTest {
    @Test
    fun testCreateCircuit(){
        val circuit = Circuit(10)
        circuit.addGate(Gate("X", arrayListOf(1)))

        assertEquals(circuit.getTotalQubits(), 10)
        assertEquals(circuit.getOpsList().first.name, "X")
        assertEquals(circuit.getOpsList().first.qubits.first, 1)
    }


}