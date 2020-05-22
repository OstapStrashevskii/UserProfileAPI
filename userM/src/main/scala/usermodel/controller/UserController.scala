package usermodel.controller

import akka.http.scaladsl.model.{ContentTypes, HttpEntity, StatusCodes}
import akka.http.scaladsl.server.Directives.{complete, get, path, _}
import akka.http.scaladsl.server.Route
import usermodel.dto.{Request, UserCreateRequest, UserUpdateRequest}
import usermodel.service.ProfileService
import usermodel.utils.Validation

import scala.util.{Failure, Success}

class UserController(profileService: ProfileService) {

  val InappropriateParams = "inappropriate params"

  val route: Route =
    path("users") {
      concat(
        put {
          entity(as[UserCreateRequest]) { request =>
            validate(Validation.paramsValidate(request.name, request.surName, request.age,request.phoneNum,request.email), InappropriateParams){
              onComplete(profileService.createUser(request)) {
                case Success(_)   => complete(StatusCodes.NoContent)
                case Failure(exc) => complete(StatusCodes.InternalServerError, exc.getMessage)
              }
            }
          }
        },
        put {
          entity(as[UserUpdateRequest]) { request =>
            validate(Validation.paramsValidate(request.name, request.surName, request.age,request.phoneNum,request.email), InappropriateParams){
              onComplete(profileService.updateUser(request)) {
                case Success(_)   => complete(StatusCodes.NoContent)
                case Failure(exc) => complete(StatusCodes.InternalServerError, exc.getMessage)
              }
            }
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
