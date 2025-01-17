package providers.local

import Amplitude
import Circuit
import CircuitState
import math.Complex
import providers.Backend
import kotlin.math.pow

class LocalBackend : Backend{

    override fun execute(circuit: Circuit) : CircuitState {

        val totalQubits:Int = circuit.getTotalQubits()

        val totalPossibleStates:Int = (2.0).pow(totalQubits).toInt()

        var circuitState:CircuitState = Array(totalPossibleStates){ Complex(0.0, 0.0) }
        circuitState[0] = Complex(1.0, 0.0) // set to state 00000...0


        for(op in circuit.getOpsList()){

            // it's going to throw an exception if the op.name is not in our enum
            val parsedOp:NativeOperators  = enumValueOf<NativeOperators>(op.name)

            when(parsedOp){
                NativeOperators.X -> circuitState = this.x(circuitState, op.qubits, totalQubits)
                NativeOperators.H -> print("AAA")
                NativeOperators.Z -> print("AAA")
                NativeOperators.CZ ->print("AAA")
                NativeOperators.RX ->print("AAA")
                NativeOperators.RY ->print("AAA")
                NativeOperators.RZ ->print("AAA")
                NativeOperators.CNOT ->print("AAA")
                NativeOperators.SWAP ->print("AAA")
            }
        }

        return circuitState
    }

    private fun x(circuitState:CircuitState, qubits:ArrayList<Int>, totalQubits:Int) : CircuitState{
        check(qubits.size == 1){ "Invalid number of Qubits for X gate!" }

        val selectedQubit:Int = qubits.first()
        check(selectedQubit >= 0 && selectedQubit <= totalQubits-1){ "Invalid selected Qubit for X gate!" }

        val totalBitStringsCombinations:Int = circuitState.size


        // this one maps the observable (I⊗X⊗I, X⊗I⊗I,  etc.) to zeros and ones, so X=1,I=0
        // we're using big endian, so the left most is zero and the rightmost is 2^n-1
        val observableToDecimal:Int = "".padStart(totalQubits,'0')
                                    .replaceRange(selectedQubit,selectedQubit+1, "1")
                                    .toInt(radix=2)

        // give the direction of the bitwise shift (1 for shift right and 0 for shift left)
        val direction:Int = if(observableToDecimal%2 == 0) 1 else 0


        // the value yield by the observable transformation is the same as the position of the value 1 in the first row
        // example: X⊗I --> 10
        // [ 0 0 1 0 ] --> it's possible to see that the position of the value 1 is the second starting from zero (10 in binary)
        // [ 0 0 0 1 ]
        // [ 1 0 0 0 ]
        // [ 0 1 0 0 ]
        var matrixRow:String = "".padStart(totalBitStringsCombinations, '0')
                                 .replaceRange(observableToDecimal,observableToDecimal+1,"1")


        val maxValue:Int = (2.0).pow(totalBitStringsCombinations-1).toInt()
        val shiftLeftMax:Int = maxValue * 2 // valued used to loop the binary sequence when doing shift left

        val newState:CircuitState = Array(totalBitStringsCombinations){ Complex(0.0,0.0) }

        for(rowIndex in 0..<(totalBitStringsCombinations)){
            // IT MAY RAISE AN ERROR IF SOMETHING GOES WRONG AND THE MATRIX ROW, FOR SOME REASON, HAS NO VALUES 1
            val statePos:Int = matrixRow.indexOf("1")

            val circuitStatePosValue:Amplitude= circuitState[statePos]
            newState[rowIndex].real = circuitStatePosValue.real
            newState[rowIndex].imaginary = circuitStatePosValue.imaginary

            var binaryRowToDecimal:Int = matrixRow.toInt(radix=2)

            if(direction == 1){
                binaryRowToDecimal = binaryRowToDecimal shr 1
                if(binaryRowToDecimal == 0){ // reset it looping from 0 to max
                    binaryRowToDecimal = maxValue
                }
            }else{
                binaryRowToDecimal = (binaryRowToDecimal shl 1) % shiftLeftMax // reset it looping from 0 to max using mod
                if(binaryRowToDecimal == 0){
                    // once shl won't add another 1, shifting to the left won't change the state
                    // to avoid that we add one, this way acting as we expected
                    // 0000 << 1 = 0000
                    // 0000 + 1 = 0001 ---> 0001 << 1 == 0010
                    binaryRowToDecimal ++
                }
            }

            matrixRow = binaryRowToDecimal.toString(radix = 2)
                                          .padStart(totalBitStringsCombinations,'0')
        }
        return newState
    }

}