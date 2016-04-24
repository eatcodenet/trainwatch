package controllers

import javax.inject._
import play.api._
import play.api.mvc._

@Singleton
class AppController @Inject() extends Controller {

  def index = Action {
    Redirect(routes.AppController.delays())
  }

  def delays = Action {
    Ok(views.html.delays())
  }

  def liveDepartures = Action {
    Ok(views.html.livedepartures())
  }

}
