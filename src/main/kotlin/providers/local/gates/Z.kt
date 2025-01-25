package providers.local.gates

import Gate

class Z (override val qubits: ArrayList<Int>): Gate {
    override val name: String = "Z"
    override val params: ArrayList<Double> = arrayListOf()

    override fun checkTotalQubits(){
        check(this.qubits.size == 1){ "Invalid number of Qubits for ${this.name} gate!" }
    }

    override fun returnQasm(): String {
        return "z q[${this.qubits.first()}];"
    }

    override fun checkTotalParams() {
    }
}