import com.influxdb.client.domain.WritePrecision
import com.influxdb.client.write.Point
import kotlinx.coroutines.runBlocking
import net.neverstopgaming.stats.db.Influx
import net.neverstopgaming.stats.db.Influx.writeApi
import java.time.Instant

fun main(){

    Influx

    val pointTo = Point
        .measurement("service")
        .addField("online Player", 2)
        .addTag("service", "Chaoten-1")
        .addTag("group", "Chaoten")
        .time(Instant.now(), WritePrecision.NS);

    runBlocking {
        writeApi.writePoint(pointTo,"simplecloud", "NeverStopGaming")
    }
}