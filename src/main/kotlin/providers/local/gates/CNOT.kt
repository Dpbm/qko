package providers.local.gates

import Gate

class CNOT (override val qubits: ArrayList<Int>): Gate {
    override val name: String = "CNOT"
    override val params: ArrayList<Double> = arrayListOf()

    override fun checkTotalQubits(){
        check(this.qubits.size == 2){ "Invalid number of Qubits for ${this.name} gate!" }
    }

    override fun returnQasm(): String {
        return "cx q[${this.qubits.first()}], q[${this.qubits.last()}];"
    }

    override fun checkTotalParams() {
    }
}