import Circuit
import math.Complex
import kotlin.test.Test

class CircuitTest {
    @Test
    fun testInitializationOneQubit(){
        val circuit:Circuit = Circuit("TestCircuit", 1);
        val state:Array<Complex> = circuit.getState();

        assert(state[0].real == 1.toDouble());
        assert(state[0].imaginary == 0.toDouble());

        assert(state[1].real == 0.toDouble());
        assert(state[1].imaginary == 0.toDouble());
    }

    @Test
    fun testInitializationThreeQubits(){
        val circuit:Circuit = Circuit("TestCircuit", 3);
        val state:Array<Complex> = circuit.getState();

        assert(state[0].real == 1.toDouble());
        assert(state[0].imaginary == 0.toDouble());

        for(i in 1..<state.size){
            assert(state[i].real == 0.toDouble());
            assert(state[i].imaginary == 0.toDouble());
        }


    }
}