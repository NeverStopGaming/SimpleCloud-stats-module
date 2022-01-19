package net.neverstopgaming.stats.time

import com.influxdb.client.write.Point
import eu.thesimplecloud.api.CloudAPI
import eu.thesimplecloud.jsonlib.JsonLib
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import net.neverstopgaming.stats.config.Config
import net.neverstopgaming.stats.db.Influx
import net.neverstopgaming.stats.pluginName
import java.io.File

object DataHandler {

    private val configFile = File("modules/$pluginName", "config.json")
    val config = loadConfig()

    private val points = mutableListOf<Point>()

    init {

        launchCoroutines()
    }

    private fun launchCoroutines() {
        runBlocking {

            launch { // this one is for the service
                while (this.isActive && config.intervalConfig.serivceIntervalEnabled) {

                    for(service in CloudAPI.instance.getCloudServiceManager().getAllCachedObjects()) {
                        points.add(Point.measurement("service")
                            .addTag("name", service.getName())
                            .addTag("group", service.getGroupName())
                            .addField("onlinePlayer", service.getOnlineCount())
                            .addField("mem_used", service.getUsedMemory())
                            .addField("mem_max", service.getMaxMemory()))
                    }
                    delay(config.intervalConfig.serivceInterval)
                }
            }

            launch { // this one is for the groups
                while (this.isActive && config.intervalConfig.groupIntervalEnabled) {

                    for(group in CloudAPI.instance.getCloudServiceGroupManager().getAllCachedObjects()) {
                        points.add(Point.measurement("group")
                            .addTag("name", group.getName())
                            .addField("onlinePlayer", group.getOnlinePlayerCount())
                            .addField("onlineService", group.getOnlineServiceCount())
                            .addField("onlineRegisteredServices", group.getRegisteredServiceCount()))
                    }
                    delay(config.intervalConfig.groupInterval)
                }
            }

            launch { // this one is for the warpper
                while (this.isActive && config.intervalConfig.warrperIntervalEnabled) {

                    for(wrapper in CloudAPI.instance.getWrapperManager().getAllCachedObjects()) {
                        points.add(Point.measurement("warpper")
                            .addTag("name", wrapper.getName())
                            .addTag("host", wrapper.getHost())
                            .addField("cpu_used", wrapper.getCpuUsage())
                            .addField("mem_used", wrapper.getUsedMemory())
                            .addField("mem_max", wrapper.getMaxMemory()))
                    }
                    delay(config.intervalConfig.warrperInterval)
                }
            }


            launch { // this one is for the cloud
                while (this.isActive && config.intervalConfig.cloudIntervalEnabled) {

                    points.add(Point.measurement("cloud")
                        .addField("onlinePlayer",CloudAPI.instance.getCloudPlayerManager().getAllCachedObjects().size))

                    delay(config.intervalConfig.cloudInterval)
                }
            }



            launch { // this one to update the database
                while (this.isActive) {

                    Influx.writeApi.writePoints(points, config.bucket, config.org)
                    points.clear()

                    delay(config.intervalConfig.updateInterval)
                }
            }

        }
    }

    private fun loadConfig() : Config {
        if (!configFile.exists()) {
            val config = Config()
            JsonLib.fromObject(config).saveAsFile(configFile)
            return config
        }

        return JsonLib.fromJsonFile(configFile)!!.getObject(Config::class.java)
    }
}