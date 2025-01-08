import math.Complex
import kotlin.math.pow

class Circuit{
    // the ops are added in order as they are inserted in the circuit
    // this way to apply the operations, it's just needed to follow the sequence
    private var ops: ArrayList<Operator> = ArrayList()

    private val totalQubits:Int
    private val name:String

    // Every possible state is a complex number encoding the amplitude
    // the default zero is so complex(1, 0), once cos(0/2) = 1
    private var state: Array<Complex>

    constructor(name:String, totalQubits:Int){
        this.name = name
        this.totalQubits = totalQubits

        val totalStates = (2).toDouble().pow(totalQubits).toInt()
        this.state = Array(totalStates) { Complex(0.toDouble(),0.toDouble()); } // initialize everything as zero
        this.state[0] = Complex(1.toDouble(), 0.toDouble()) // set the whole circuit state as 0000....0
    }

     fun addGate(gate:Operator){
        this.ops.add(gate)
    }

    fun getState() : Array<Complex>{
        return this.state
    }
}