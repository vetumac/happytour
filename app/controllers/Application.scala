package controllers

import javax.inject.Inject

import play.api.db.Database
import play.api.mvc._

class Application @Inject()(db: Database) extends Controller {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def maxPriceList(maxPrice: Double) = Action {
    var outString = "Tour is "
    val conn = db.getConnection()
    try {
      val stmt = conn.createStatement
      val rs = stmt.executeQuery("SELECT * FROM TOUR WHERE PRICE < " + maxPrice)

      while (rs.next()) {
        outString += rs.getString("name")
      }
    } finally {
      conn.close()
    }
    Ok(outString)
  }

}