
package mytests

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class demo9 extends Simulation {

  val httpConf = http
    .baseURL("http://127.0.0.1:8888")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")
    
  val headers = Map("Accept" -> "text/html,application/xhtml+xml,application/json;q=0.9,*/*;q=0.8")

  val scn = scenario("myDemo1")
    .exec(
    	http("getJson")
      .get("/getJson?name=beibei")
			.headers(headers)
			.check(status.is(200), jsonPath("$..name").is("beibei"), jsonPath("$..number").ofType[Int] )
		)
    .pause(1)
      
  setUp(scn.inject(atOnceUsers(100))
  	.protocols(httpConf))
}
