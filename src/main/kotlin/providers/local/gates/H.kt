package providers.local.gates

import Gate

class H (override val qubits: ArrayList<Int>): Gate {
    override val name: String = "H"
    override val params: ArrayList<Double> = arrayListOf()

    override fun checkTotalQubits(){
        check(this.qubits.size == 1){ "Invalid number of Qubits for ${this.name} gate!" }
    }

    override fun returnQasm(): String {
        return "h q[${this.qubits.first()}];"
    }

    override fun checkTotalParams() {
    }
}