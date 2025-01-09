import math.Complex

class Qubit (private var state:State, private val label:String="q"){
    fun swapAmplitudes(){
        val currentZeroAmplitude:Complex = state.getZeroAmplitude()
        val currentOneAmplitude:Complex = state.getOneAmplitude()

        this.state.setAmplitudeOfZero(currentOneAmplitude)
        this.state.setAmplitudeOfOne(currentZeroAmplitude)
    }

    fun getState():State{
        return this.state
    }

    fun getLabel():String{
        return this.label
    }
}