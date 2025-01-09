import math.Complex

class Qubit (private var state:State, private val label:String="q"){
    fun swapAmplitudes(){
        val currentZeroAmplitude:Amplitude = this.state.zeroAmplitude
        val currentOneAmplitude:Amplitude = this.state.oneAmplitude

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