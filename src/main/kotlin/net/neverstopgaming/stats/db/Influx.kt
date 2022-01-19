@file:Suppress("MemberVisibilityCanBePrivate")

package net.neverstopgaming.stats.db

import com.influxdb.client.kotlin.InfluxDBClientKotlinFactory
import net.neverstopgaming.stats.time.DataHandler.config

object Influx {

    // You can generate an API token from the "API Tokens Tab" in the UI
    val client = InfluxDBClientKotlinFactory.create(
        "${config.protocol}://${config.host}:${config.port}",
        config.token.toCharArray(),
        org = config.org,
        bucket = config.bucket)

    val writeApi = client.getWriteKotlinApi()
}