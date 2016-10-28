package controllers

import javax.inject.Inject

import anorm.{Macro, RowParser, SQL}
import models.Tour
import play.api.db.Database
import play.api.libs.json.Json
import play.api.mvc._


class User @Inject()(db: Database) extends Controller {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def search(maxPrice: Double) = Action {

    db.withConnection { implicit connection =>
      val parser: RowParser[Tour] = Macro.namedParser[Tour]
      val result: List[Tour] = SQL("SELECT * FROM TOUR WHERE PRICE < " + maxPrice).as(parser.*)

      implicit val tourWrites = Json.writes[Tour]

      val res = Json.toJson(result)

      Ok(res + "\n")
    }
  }
}