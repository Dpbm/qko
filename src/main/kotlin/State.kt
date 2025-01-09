import math.Complex

typealias QubitStateData = Array<Complex>
typealias Amplitude = Complex

class State (private var state:QubitStateData) {

    fun setAmplitudeOfZero(amplitude:Complex){
        this.setAmplitude(0, amplitude)
    }

    fun setAmplitudeOfOne(amplitude:Complex){
        this.setAmplitude(1, amplitude)
    }

    private fun setAmplitude(index:Int, amplitude:Complex){
        this.state[index] = amplitude
    }

    fun getZeroAmplitude():Amplitude{
        return this.getAmplitude(0)
    }

    fun getOneAmplitude():Amplitude{
        return this.getAmplitude(1)
    }

    private fun getAmplitude(value:Int):Amplitude{
        return this.state[value]
    }

    fun getState():QubitStateData{
        return this.state
    }

}