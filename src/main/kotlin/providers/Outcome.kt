package providers

import CircuitState
import kotlin.math.pow

typealias Prob = Double
typealias Dist = Array<Prob>

class Outcome (val state:CircuitState) {

    fun toDist() : Dist {
        val totalAmplitudes = this.state.size
        val dist = Array(totalAmplitudes){0.0}

        for(amplitudeIndex in 0..<(totalAmplitudes)){
            // in this case, we can get rid of the imaginary part
            val amplitude = this.state[amplitudeIndex]
            dist[amplitudeIndex] = (amplitude.real).pow(2)
        }

        return dist
    }
}