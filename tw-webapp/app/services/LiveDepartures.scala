package services

import java.util.concurrent.atomic.AtomicInteger

import javax.inject.Singleton
import com.google.inject.ImplementedBy
import net.eatcode.trainwatch.search.TrainWatchSearch
import net.eatcode.trainwatch.search.hazelcast.HzTrainWatchSearch
import net.eatcode.trainwatch.nr.hazelcast.HzClientBuilder
import net.eatcode.trainwatch.search.Station
import net.eatcode.trainwatch.movement.TrainDeparture
import net.eatcode.trainwatch.nr.{Schedule, Location}

import scala.collection.JavaConversions._

@ImplementedBy(classOf[HzLiveDepartures])
trait LiveDepartures {
  def listStations: List[Station]

  def departuresFrom(crs: String): List[TrainDeparture]
}

@Singleton
class HzLiveDepartures extends LiveDepartures {

  val client = new HzClientBuilder().buildInstance("trainwatch.eatcode.net");
  val search = new HzTrainWatchSearch(client)
  override def listStations = search.listStations.toList

  override def departuresFrom(crs: String) = List()
}

@Singleton
class InMemDepartures extends LiveDepartures {

  override def listStations = List(
    new Station("London Euston", "EUS"),
    new Station("Manchester Picadilly", "MAN"),
    new Station("Leeds", "LDS"),
    new Station("Poole", "POO"))

  override def departuresFrom(crs: String) = {
    val schedule: Schedule = new Schedule
    schedule.departure = java.time.LocalTime.now()
    schedule.arrival = schedule.departure.plusHours(2)
    schedule.origin = new Location("stanox", "Manchester Piccadilluy", "123", "MAN", null)
    schedule.destination = new Location("stanox", "London Euston", "123", "EUS", null)
    List(new TrainDeparture("1", "1462729321000", schedule))
  }
}
