interface BuildType {
    companion object{
        const val DEBUG="debug"
        const val RELEASE="release"
    }
    val isMinifyEnabled:Boolean
    val isZipAlignEnabled :Boolean
    val isTestCoverageEnabled:Boolean
}
object BuildTypeDebug:BuildType{
    override val isMinifyEnabled: Boolean
        get() = false
    override val isZipAlignEnabled: Boolean
        get() = false
    override val isTestCoverageEnabled: Boolean
        get() = true

}
object BuildTypeRelease:BuildType{
    override val isMinifyEnabled: Boolean
        get() = false
    override val isZipAlignEnabled: Boolean
        get() = false
    override val isTestCoverageEnabled: Boolean
        get() = false

}