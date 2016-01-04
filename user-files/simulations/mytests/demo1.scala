package mytests

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class demo1 extends Simulation {

  val httpConf = http
    .baseURL("http://127.0.0.1:8888")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")
    
  val headers = Map("Accept" -> "text/html,application/xhtml+xml,application/json;q=0.9,*/*;q=0.8")

  val scn = scenario("myDemo1")
    .exec(
    	http("getJson")
      .get("/getJson?name=beibei")
			.headers(headers)
			.check(status.is(200))
		)
    .pause(1)
      
  setUp(scn.inject(splitUsers(1000) into(rampUsers(10) over(10 seconds)) separatedBy atOnceUsers(30))
  	.protocols(httpConf))
}
