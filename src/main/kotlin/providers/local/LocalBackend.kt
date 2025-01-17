package providers.local

import Amplitude
import Circuit
import CircuitState
import math.Complex
import math.notMinusZero
import providers.Backend
import kotlin.math.absoluteValue
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
                NativeOperators.Z -> circuitState = this.z(circuitState, op.qubits, totalQubits)
                NativeOperators.CZ -> circuitState = this.cz(circuitState, op.qubits, totalQubits)
                NativeOperators.RX ->print("AAA")
                NativeOperators.RY ->print("AAA")
                NativeOperators.RZ ->print("AAA")
                NativeOperators.CNOT -> circuitState = this.cnot(circuitState, op.qubits, totalQubits)
                NativeOperators.SWAP ->print("AAA")
            }
        }

        return circuitState
    }

    private fun x(circuitState:CircuitState, qubits:ArrayList<Int>, totalQubits:Int) : CircuitState{
        check(totalQubits >= 1){ "Your Circuit must have at least 1 qubit!" }
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


    private fun cnot(circuitState:CircuitState, qubits:ArrayList<Int>, totalQubits:Int) : CircuitState{
        check(totalQubits >= 2){ "Your Circuit must have at least 2 qubits!" }
        check(qubits.size == 2){ "Invalid number of Qubits for CNOT gate!" }

        val controlQubit:Int = qubits.first()
        val targetQubit:Int = qubits.last()
        check(controlQubit >= 0 && controlQubit <= totalQubits-1){ "Invalid control Qubit for CNOT gate!" }
        check(targetQubit >= 0 && targetQubit <= totalQubits-1){ "Invalid target Qubit for CNOT gate!" }


        val totalBitStringsCombinations:Int = circuitState.size
        val newState:CircuitState = Array(totalBitStringsCombinations){ Complex(0.0,0.0) }


        for(rowIndex in 0..<(totalBitStringsCombinations)){
            val binaryRowIndex:String = rowIndex.toString(radix = 2)
                                                .padStart(totalQubits, '0')
            val rowValue:Amplitude = circuitState[rowIndex]

            // keep in mind that the qubit 0 is the leftmost
            val controlHasOne:Boolean = binaryRowIndex[controlQubit] == '1'
            val targetBinaryValue:Char = binaryRowIndex[targetQubit]

            var newRowIndex:Int = rowIndex;
            if(controlHasOne){
                newRowIndex = binaryRowIndex.replaceRange(targetQubit, targetQubit+1, if(targetBinaryValue == '1') "0" else "1")
                                                    .toInt(radix = 2)
            }

            newState[newRowIndex].real = rowValue.real
            newState[newRowIndex].imaginary = rowValue.imaginary
        }

        return newState
    }

    private fun z(circuitState:CircuitState, qubits:ArrayList<Int>, totalQubits:Int) : CircuitState{
        check(totalQubits >= 1){ "Your Circuit must have at least 1 qubit!" }
        check(qubits.size == 1){ "Invalid number of Qubits for Z gate!" }

        val selectedQubit:Int = qubits.first()
        check(selectedQubit >= 0 && selectedQubit <= totalQubits-1){ "Invalid selected Qubit for Z gate!" }

        for(rowIndex in 0..<(circuitState.size)){
            val binaryRowIndex:String = rowIndex.toString(radix = 2)
                .padStart(totalQubits, '0')
            val rowValue:Amplitude = circuitState[rowIndex]

            val factor:Int = if(binaryRowIndex[selectedQubit] == '1') -1 else 1
            circuitState[rowIndex].real = notMinusZero(factor*rowValue.real)
            circuitState[rowIndex].imaginary = notMinusZero(factor*rowValue.imaginary)

        }

        return circuitState
    }

    private fun cz(circuitState:CircuitState, qubits:ArrayList<Int>, totalQubits:Int) : CircuitState{
        check(totalQubits >= 2){ "Your Circuit must have at least 2 qubits!" }
        check(qubits.size == 2){ "Invalid number of Qubits for CZ gate!" }

        val controlQubit:Int = qubits.first()
        val targetQubit:Int = qubits.last()
        check(controlQubit >= 0 && controlQubit <= totalQubits-1){ "Invalid control Qubit for CZ gate!" }
        check(targetQubit >= 0 && targetQubit <= totalQubits-1){ "Invalid target Qubit for CZ gate!" }

        for(rowIndex in 0..<(circuitState.size)){
            val binaryRowIndex:String = rowIndex.toString(radix = 2)
                .padStart(totalQubits, '0')
            val rowValue:Amplitude = circuitState[rowIndex]

            val factor:Int = if(binaryRowIndex[targetQubit] == '1' && binaryRowIndex[controlQubit] == '1') -1 else 1
            circuitState[rowIndex].real = notMinusZero(factor*rowValue.real)
            circuitState[rowIndex].imaginary = notMinusZero(factor*rowValue.imaginary)
        }

        return circuitState
    }
}