import math.Complex
import math.HALF_PROB

typealias Amplitude = Complex
typealias QubitState = Array<Amplitude>

class State {
     var state:QubitState
        private set

     val zeroAmplitude : Amplitude
        get(){
            return this.state[0]
        }

     val oneAmplitude : Amplitude
        get(){
            return this.state[1]
        }

    constructor(state:QubitState){
        this.state = state
    }

    fun setAmplitudeOfZero(amplitude:Amplitude){
        this.setAmplitude(0, amplitude)
    }

    fun setAmplitudeOfOne(amplitude:Amplitude){
        this.setAmplitude(1, amplitude)
    }

    private fun setAmplitude(index:Int, amplitude:Amplitude){
        this.state[index] = amplitude
    }

    fun isZero():Boolean{
        return this.zeroAmplitude.real == 1.0 &&
                this.zeroAmplitude.imaginary == 0.0 &&
                this.oneAmplitude.real == 0.0 &&
                this.oneAmplitude.imaginary == 0.0
    }

    fun isOne():Boolean{
        return this.zeroAmplitude.real == 0.0 &&
                this.zeroAmplitude.imaginary == 0.0 &&
                this.oneAmplitude.real == 1.0 &&
                this.oneAmplitude.imaginary == 0.0
    }

    fun isPlus():Boolean{
        return this.zeroAmplitude.real == HALF_PROB &&
                this.zeroAmplitude.imaginary == 0.0 &&
                this.oneAmplitude.real == HALF_PROB &&
                this.oneAmplitude.imaginary == 0.0
    }

    fun isMinus():Boolean{
        return this.zeroAmplitude.real == HALF_PROB &&
                this.zeroAmplitude.imaginary == 0.0 &&
                this.oneAmplitude.real == -HALF_PROB &&
                this.oneAmplitude.imaginary == 0.0
    }

    fun changeToPlus(){
        this.state = PLUS_STATE.state
    }

    fun changeToMinus(){
        this.state = MINUS_STATE.state
    }

    fun changeToZero(){
        this.state = ZERO_STATE.state
    }

    fun changeToOne(){
        this.state = ONE_STATE.state
    }

}