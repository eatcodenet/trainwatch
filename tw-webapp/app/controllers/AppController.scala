package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import services.TrainMovements
import services.LiveDepartures
import play.api.cache.CacheApi
import net.eatcode.trainwatch.search.Station
import scala.concurrent.duration._

@Singleton
class AppController @Inject() (trainMovements: TrainMovements, departures: LiveDepartures, cache: CacheApi) extends Controller {

  def index = Action {
    Redirect(routes.AppController.delays())
  }

  def delays = Action {
    Ok(views.html.delays(trainMovements.count))
  }

  def liveDepartures(crs: String) = Action {
    val stations: List[Station] = cache.getOrElse[List[Station]]("station.list", 5.minute) {
      departures.listStations
    }

    Ok(views.html.livedepartures(stations, crs))
  }

}
