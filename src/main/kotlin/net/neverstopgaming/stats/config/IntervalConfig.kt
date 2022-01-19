package net.neverstopgaming.stats.config

data class IntervalConfig(
    val updateInterval: Long = 1000L,
    val warrperInterval : Long = 1000L,
    val warrperIntervalEnabled: Boolean = true,
    val serivceInterval : Long = 10000L,
    val serivceIntervalEnabled : Boolean = true,
    val groupInterval : Long = 60000L,
    val groupIntervalEnabled : Boolean = true,
    val cloudInterval : Long = 60000L,
    val cloudIntervalEnabled : Boolean = true
    )