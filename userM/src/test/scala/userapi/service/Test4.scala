package userapi.service

import org.scalatest.WordSpec
import org.scalatest.concurrent.ScalaFutures
import play.api.libs.json.{JsValue, Json, OFormat}
import userapi.model.UserCreateException
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

  val validRequest = UserCreateRequest("John","Doe",100, List("0771234567", "+0771234667"), List("dasdasffff@mail.ru", "dasdasffff@mail.ru"))
  val wrongNameRequest = UserCreateRequest("","Doe",100, List("0771234567", "+0771234667"), List("dasdasffff@mail.ru", "dasdasffff@mail.ru"))
  val wrongEmailRequest = UserCreateRequest("John","Doe",100, List("0771234567", "+0771234667"), List("dasdasffff@mail.ru", "dasdasffffmail.ru"))
  val wrongEmailsQuantityRequest = UserCreateRequest("John","Doe",100, List("0771234567", "+0771234667"), List())
  val wrongPhoneRequest = UserCreateRequest("John","Doe",100, List("0771234567", "RR+0771234667"), List("dasdasffff@mail.ru", "dasdasffff@mail.ru"))


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
