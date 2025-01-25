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

    fun getQasm():String{
        var qasm:String = """
            OPENQASM 2.0;
            include "qelib1.inc";
            
            qreg q[${this.totalQubits}];
            

        """.trimIndent()

        for(op in this.ops){
            qasm += op.returnQasm() + "\n"
        }

        return qasm
    }

}