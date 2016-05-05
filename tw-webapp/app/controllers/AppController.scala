package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import services.TrainMovements
import services.LiveDepartures

@Singleton
class AppController @Inject() (trainMovements: TrainMovements, departures: LiveDepartures ) extends Controller {

  def index = Action {
    Redirect(routes.AppController.delays())
  }

  def delays = Action {
    Ok(views.html.delays(trainMovements.count))
  }

  def liveDepartures = Action {
    
    Ok(views.html.livedepartures(departures.listStations))
  }

}
