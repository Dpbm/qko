interface Gate{
    val name:String
    val qubits:ArrayList<Int>
    val params: ArrayList<Double>

    fun returnQasm() : String
    fun checkTotalQubits()
    fun checkTotalParams();
}
