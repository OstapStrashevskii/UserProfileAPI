package userapi.dao

import akka.actor.Status.Success
import akka.http.scaladsl.unmarshalling.Unmarshal
import userapi.dto.{UserCreateRequest, UserUpdateRequest}
import userapi.model.{Age, Email, Name, PhoneNum, Surname, User, UserCreateException}

import scala.concurrent.{ExecutionContext, Future}

class UserDao(redisDataSource: RedisDataSource) {

  def createUser(userCreateRequest: UserCreateRequest)(implicit executionContext: ExecutionContext): Future[User] = {
    println("dao user create")
    val userEither: Either[String, User] = User(None, userCreateRequest.name, userCreateRequest.surName, userCreateRequest.age, userCreateRequest.phoneNum, userCreateRequest.email)
    userEither match {
      case Left(error) => Future.failed(UserCreateException(error))
      case Right(user) => if (redisDataSource.redisClient.set(user.id, user)) {
          Future {
            user
          }
        } else {
          Future.failed(UserCreateException("error with Redis client or datasource while creating"))
        }
    }
  }

  def updateUser(userUpdateRequest: UserUpdateRequest)(implicit executionContext: ExecutionContext): Future[User] = {
    val userEither: Either[String, User] = User(Some(userUpdateRequest.id), userUpdateRequest.name, userUpdateRequest.surName, userUpdateRequest.age, userUpdateRequest.phoneNum, userUpdateRequest.email)
    userEither match {
      case Left(error) => Future.failed(UserCreateException(error))
      case Right(user) => if (redisDataSource.redisClient.set(userUpdateRequest.id, user)) {
        Future {
          user
        }
      } else {
        Future.failed(UserCreateException("error with Redis client or datasource while updating"))
      }
    }
  }
}
