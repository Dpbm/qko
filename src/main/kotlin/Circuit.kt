class Circuit (private val totalQubits: Int){
    // the ops are added in order as they are inserted in the circuit
    // this way to apply the operations, it's just needed to follow the sequence
    private var ops: ArrayList<Gate> = ArrayList()

     fun addGate(gate:Gate){
        this.ops.add(gate)
    }

    fun getTotalQubits():Int{
        return this.totalQubits
    }

    fun getOpsList():ArrayList<Gate>{
        return this.ops
    }

}