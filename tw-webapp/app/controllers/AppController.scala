package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import services.TrainMovements

@Singleton
class AppController @Inject() (trainMovements: TrainMovements) extends Controller {

  def index = Action {
    Redirect(routes.AppController.delays())
  }

  def delays = Action {
    Ok(views.html.delays(trainMovements.count))
  }

  def liveDepartures = Action {
    Ok(views.html.livedepartures())
  }

}
