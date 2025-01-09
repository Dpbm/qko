import math.Complex
import kotlin.math.pow

class Circuit{
    // the ops are added in order as they are inserted in the circuit
    // this way to apply the operations, it's just needed to follow the sequence
    private var ops: ArrayList<Operator> = ArrayList()

    private val totalQubits:Int
    private val qubits: Array<Qubit>

    constructor(totalQubits:Int){
        this.totalQubits = totalQubits
        this.qubits = Array(totalQubits){ Qubit(ZeroState) }
    }

     fun addGate(gate:Operator){
        this.ops.add(gate)
    }

    fun getQubits():Array<Qubit>{
        return this.qubits
    }

    fun getTotalQubits():Int{
        return this.totalQubits
    }

    // TODO: MUST BE UPDATED
    fun runCircuit(){
        for(op in  this.ops){
            op.apply()
        }
    }

}