abstract class Operator protected constructor(private val name: String) {
    protected abstract fun apply()
}