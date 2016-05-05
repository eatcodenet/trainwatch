package services

import java.util.concurrent.atomic.AtomicInteger

import javax.inject.Singleton
import com.google.inject.ImplementedBy
import net.eatcode.trainwatch.search.TrainWatchSearch
import net.eatcode.trainwatch.search.hazelcast.HzTrainWatchSearch
import net.eatcode.trainwatch.nr.hazelcast.HzClientBuilder
import net.eatcode.trainwatch.search.Station

import scala.collection.JavaConversions._

@ImplementedBy(classOf[HzLiveDepartures])
trait LiveDepartures {
  def listStations: List[Station]
}

@Singleton
class HzLiveDepartures extends LiveDepartures {

  val client = new HzClientBuilder().buildInstance("trainwatch.eatcode.net");
  val search = new HzTrainWatchSearch(client)
  override def listStations = search.listStations.toList
}
