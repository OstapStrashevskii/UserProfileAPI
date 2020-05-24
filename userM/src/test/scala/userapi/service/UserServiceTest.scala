package userapi.service

import akka.http.scaladsl.server.Route
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.WordSpec
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.matchers.should.Matchers
import play.api.libs.json.OFormat
import userapi.controller.UserController
import userapi.dao.{RedisDataSource, UserDao}
import userapi.dto.UserCreateRequest
import userapi.model.User

class UserServiceTest extends WordSpec with Matchers with ScalaFutures with ScalatestRouteTest {

  implicit val formatter: OFormat[User] = User.userFormat
  val redisDataSource: RedisDataSource = new RedisDataSource()
  val userDao: UserDao = new UserDao(redisDataSource)
  val userService: ProfileService = new UserService(userDao)

  val sRoute: Route = new UserController(userService).route

  val validRequest: UserCreateRequest = UserCreateRequest("John", "Doe", 100, List("0771234567", "+0771234667"), List("dasdasffff@mail.ru", "dasdasffff@mail.ru"))
  val wrongNameRequest: UserCreateRequest = UserCreateRequest("", "Doe", 100, List("0771234567", "+0771234667"), List("dasdasffff@mail.ru", "dasdasffff@mail.ru"))
  val wrongEmailRequest: UserCreateRequest = UserCreateRequest("John", "Doe", 100, List("0771234567", "+0771234667"), List("dasdasffff@mail.ru", "dasdasffffmail.ru"))
  val wrongEmailsQuantityRequest: UserCreateRequest = UserCreateRequest("John", "Doe", 100, List("0771234567", "+0771234667"), List())
  val wrongPhoneRequest: UserCreateRequest = UserCreateRequest("John", "Doe", 100, List("0771234567", "RR+0771234667"), List("dasdasffff@mail.ru", "dasdasffff@mail.ru"))


  "userService" when {
    "should work if valid" should {
      "in creating user" in {
        whenReady(userService.createUser(validRequest)) {
          u =>
            u.name.namestr shouldBe "John"
        }
      }
    }
    "should fail if not valid" should {
      "should throw ex if name is not valid" in {
        whenReady(userService.createUser(wrongNameRequest).failed) {
          _ shouldBe userapi.model.UserCreateException(" incorrect name")
        }
      }
    }
    "should fail if not valid" should {
      "should throw ex if phone is not valid" in {
        whenReady(userService.createUser(wrongPhoneRequest).failed) {
          _ shouldBe userapi.model.UserCreateException(" incorrect phone")
        }
      }
    }
    "should fail if not valid" should {
      "should throw ex if email is not valid" in {
        whenReady(userService.createUser(wrongEmailRequest).failed) {
          _ shouldBe userapi.model.UserCreateException(" incorrect email")
        }
      }
    }
    "should fail if not valid" should {
      "should throw ex if no emails provided" in {
        whenReady(userService.createUser(wrongEmailsQuantityRequest).failed) {
          _ shouldBe userapi.model.UserCreateException(" incorrect email")
        }
      }
    }
  }
}
