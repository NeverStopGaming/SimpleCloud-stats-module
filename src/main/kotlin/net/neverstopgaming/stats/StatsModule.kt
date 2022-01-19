package net.neverstopgaming.stats

import eu.thesimplecloud.api.external.ICloudModule
import net.neverstopgaming.stats.db.Influx
import net.neverstopgaming.stats.time.DataHandler
import net.neverstopgaming.stats.util.Updater

const val pluginName = "SimpleCloud-Stats-Module"
const val pluginVersion = "1.0"

class StatsModule : ICloudModule {

    override fun onEnable() {

        Updater
        DataHandler
        Influx

    }

    override fun onDisable() {
    }

    override fun isReloadable() = true




}