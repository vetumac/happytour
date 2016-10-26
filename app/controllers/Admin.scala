package controllers

import javax.inject.Inject

import anorm.{Macro, RowParser, SQL}
import models.Tour
import play.api.db.Database
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}

class Admin @Inject()(db: Database) extends Controller {
  def showAll = Action {
    db.withConnection { implicit connection =>
      val parser: RowParser[Tour] = Macro.namedParser[Tour]
      val result: List[Tour] = SQL("SELECT * FROM TOUR").as(parser.*)

      implicit val tourWrites = Json.writes[Tour]

      val res = Json.toJson(result)

      Ok(res)
    }
  }

  def add = Action { request =>
    val jsonTour = request.body.asJson
    implicit val tourReads = Json.reads[Tour]
    val tour = Json.fromJson(jsonTour.get).get
    db.withConnection { implicit connection =>
      val id: Option[Long] =
        SQL("INSERT INTO TOUR(NAME, PRICE, DURATION, TRANSFER) values ({name}, {price}, {duration}, {transfer})")
          .on("name" -> tour.name, "price" -> tour.price, "duration" -> tour.duration, "transfer" -> tour.transfer)
          .executeInsert()
      Ok(tour + " added successful with id=" + id)
    }
  }
}
