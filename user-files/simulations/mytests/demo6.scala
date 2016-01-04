package mytests

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class demo6 extends Simulation {

  val httpConf = http
    .baseURL("http://127.0.0.1:8888")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")
    
  val headers = Map("Accept" -> "text/html,application/xhtml+xml,application/json;q=0.9,*/*;q=0.8")
  
  val feeder = csv("randomdata.csv").random
  
  object GetNullHtml {
  	val getNullHtml = exec(http("getNullHtml").get("/getHtml").check(status.is(200))).pause(1)
	}

	object GetRegularHtml {
  	val getRegularHtml = exec(http("getRegularHtml").get("/getHtml?name=${userName}").check(status.is(200))).pause(1)
	}

	object GetNullJson {
  	val getNullJson = exec(http("getNullJson").get("/getJson").check(status.is(200))).pause(1)
	}

	object GetRegularJson {
  	val getRegularJson = exec(http("getRegularJson").get("/getJson?name=${userName}").check(status.is(200))).pause(1)
	}
	
	object GetNullFormJson {
  	val getNullFormJson = exec(http("getNullFormJson").post("/getFormJson").check(status.is(200))).pause(1)
	}

	object GetRegularFormJson {
  	val getRegularFormJson = exec(http("getRegularFormJson").post("/getFormJson").formParam("name", "${userName}").check(status.is(200))).pause(1)
	}

	val htmlUsers = scenario("htmlUsers").feed(feeder).exec(GetNullHtml.getNullHtml, GetRegularHtml.getRegularHtml)
	val jsonUsers = scenario("jsonUsers").feed(feeder).exec(GetNullJson.getNullJson, GetRegularJson.getRegularJson)
	val formUsers = scenario("formUsers").feed(feeder).exec(GetNullFormJson.getNullFormJson, GetRegularFormJson.getRegularFormJson)
      
	setUp(
  	htmlUsers.inject(rampUsers(100) over (5 seconds)),
  	jsonUsers.inject(rampUsers(1000) over (5 seconds)),
  	formUsers.inject(rampUsers(50) over (5 seconds))
	).protocols(httpConf)
}
