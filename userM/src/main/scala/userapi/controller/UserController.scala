package userapi.controller

import akka.http.scaladsl.model.{ContentTypes, HttpEntity, StatusCodes}
import akka.http.scaladsl.server.Directives.{complete, get, path, _}
import akka.http.scaladsl.server.Route
import userapi.dto.{Request, UserCreateRequest, UserUpdateRequest}
import userapi.service.ProfileService
import userapi.utils.Validation
import de.heikoseeberger.akkahttpplayjson.PlayJsonSupport._

import scala.util.{Failure, Success}

class UserController(profileService: ProfileService) {

  val InappropriateParams = "inappropriate params"

  val route: Route =
    path("users") {
      concat(
        put {
          entity(as[UserCreateRequest]) { request =>
//            validate(Validation.paramsValidate(request.name, request.surName, request.age,request.phoneNum,request.email), InappropriateParams){//todo - possible akka validation
              onComplete(profileService.createUser(request)) {
                case Success(user)   => complete(user)
                case Failure(exc) => complete(StatusCodes.InternalServerError, exc.getMessage)
//              }
            }
          }
        },
        post {
          entity(as[UserUpdateRequest]) { request =>
//            validate(Validation.paramsValidate(request.name, request.surName, request.age,request.phoneNum,request.email), InappropriateParams){//todo - possible akka validation
              onComplete(profileService.updateUser(request)) {
                case Success(user)   => complete(user)
                case Failure(exc) => complete(StatusCodes.InternalServerError, exc.getMessage)
              }
//            }
          }
        })
    }
}


/*
можно и оборачивать подряд чтобы выдавать несколько ошибок

val route =
  extractUri { uri =>
    validate(uri.path.toString.size < 5, s"Path too long: '${uri.path.toString}'") {
      complete(s"Full URI: $uri")
    }
  }
 */
