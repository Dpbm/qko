package providers.local.gates

import Gate

class RZ (override val qubits: ArrayList<Int>, override val params: ArrayList<Double>): Gate {
    override val name: String = "RZ"

    override fun checkTotalQubits(){
        check(this.qubits.size == 1){ "Invalid number of Qubits for ${this.name} gate!" }
    }

    override fun returnQasm(): String {
        return "rz(${this.params.first()}) q[${this.qubits.first()}];"
    }

    override fun checkTotalParams() {
        check(this.params.size == 1){ "Invalid number of Params for ${this.name} gate!" }
    }
}