package userapi

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives.handleRejections
import akka.http.scaladsl.server.Route
import akka.stream.Materializer
import userapi.controller.UserController
import userapi.dao.{RedisDataSource, UserDao}
import userapi.service.{ProfileService, UserService}

import scala.concurrent.{ExecutionContextExecutor, Future}

/**
 * 2. Модель с валидацией
 * Смоделировать API модификации профилей пользователей (методы create/update): описать модель, ошибки валидации и реализовать логику валидации.
 *
 * Профиль пользователя состоит из следующих атрибутов:
 * - id
 * - имя (обязательное)
 * - фамилия (обязательное)
 * - возраст
 * - номер телефона (непустой список)
 * - email (список)
 *
 * Пример ожидаемого результата:
 * trait ProfileService {
 * def createUser(r: CreateCommand): CreateResult
 * def updateUser(r: UpdateCommand): UpdateResult
 * }
 * Где результаты обработки команд возвращают либо успех, либо описание ошибок валидации.
 */

object UserAPIApp extends App {

  implicit val system = ActorSystem("actorSystem")
  implicit val materializer: Materializer = Materializer(system)
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  val redisDataSource: RedisDataSource = new RedisDataSource()
  val userDao: UserDao = new UserDao(redisDataSource)
  val userService: ProfileService = new UserService(userDao)


  private def getRoute: Route = {
      new UserController(userService).route
  }

  val bindingFuture: Future[Http.ServerBinding] = Http().bindAndHandle(getRoute, "localhost", 8080)
}
