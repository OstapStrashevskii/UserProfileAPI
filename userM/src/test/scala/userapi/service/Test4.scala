package userapi.service

import org.scalatest.WordSpec
import org.scalatest.concurrent.ScalaFutures
import play.api.libs.json.{JsValue, Json, OFormat}
//import userapi.UserAPIApp.userService
import userapi.controller.UserController
import userapi.dao.{RedisDataSource, UserDao}
import userapi.dto.UserCreateRequest
import userapi.model.User

import scala.concurrent.duration.FiniteDuration
//import org.scalatest.{Matchers, WordSpec}
import akka.http.scaladsl.model.{HttpEntity, HttpMethods, HttpRequest, MediaTypes, StatusCodes}
import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.http.scaladsl.server._
import Directives._
import akka.util.ByteString
import org.scalatest.matchers.should.Matchers
import scala.concurrent.duration._

class Test4 extends WordSpec with Matchers with ScalaFutures with ScalatestRouteTest {

  implicit val formatter: OFormat[User] = User.userFormat
  private val expirationTime: FiniteDuration = 300.millis
  val redisDataSource: RedisDataSource = new RedisDataSource()
  val userDao: UserDao = new UserDao(redisDataSource)
  val userService: ProfileService = new UserService(userDao)

  val sRoute =new UserController(userService).route

//  "return a 'PONG!' response for GET requests to /ping" in {
//    // tests:
//    Put("/users") ~> sRoute ~> check {
//      responseAs[String] shouldEqual "PONG!"
//    }
//  }


  val jsonRequest = ByteString(
    s"""
       |{
       |	"name": "Jhohn",
       |	"surName": "ddsad",
       |	"age": 100,
       |	"phoneNum": [
       |		"+0771234567",
       |		"+0771234667"
       |		],
       |	"email": [
       |		"dasdasffff@mail.ru",
       |		"dasdasffff@mail.ru"
       |		]
       |}
        """.stripMargin)


  val crRequest = UserCreateRequest("John","Doe",100, List("0771234567", "+0771234667"), List("dasdasffff@mail.ru", "dasdasffff@mail.ru"))

  "ffff" when {
    "dsdsd" should {
      "adsasdas" in {

        val postRequest = HttpRequest(
          HttpMethods.PUT,
          uri = "/users",
          entity = HttpEntity(MediaTypes.`application/json`, jsonRequest))

          postRequest ~> sRoute ~> check {

//            status.intValue() shouldEqual StatusCodes.InternalServerError



            whenReady(userService.createUser(crRequest)) {
              _ shouldBe jsonRequest
            }

//            val e = response.entity.toStrict(expirationTime)
//            Json.parse(e.)
//
//            Json.stringify(Json.toJson(response.entity.toStrict(expirationTime)))
//            Json.fromJson[User](parseResponseBody(body))
//        private def parseResponseBody(e: HttpEntity.Strict): JsValue = Json.parse(e.data.utf8String)



//            status.isSuccess() shouldEqual true
//            response.entity.toString should be ("!!")
          }


//        assert(false)

      }
    }
  }







}
