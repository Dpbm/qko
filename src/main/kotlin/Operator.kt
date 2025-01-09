interface Operator {
    val name:String
    val qubits:Array<Qubit>

    fun apply()
}