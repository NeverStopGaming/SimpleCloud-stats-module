package net.neverstopgaming.stats.config

data class Config(
    val host: String = "localhost",
    val port: Int = 8086,
    val protocol : String = "http",
    val org: String = "NeverStopGaming",
    val bucket: String = "simplecloud",
    val token: String = "your-token",
    val intervalConfig: IntervalConfig = IntervalConfig()
    )
