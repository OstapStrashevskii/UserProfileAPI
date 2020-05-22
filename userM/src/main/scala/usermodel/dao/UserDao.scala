package usermodel.dao

import akka.actor.Status.Success
import akka.http.scaladsl.unmarshalling.Unmarshal
import usermodel.dto.{UserCreateRequest, UserUpdateRequest}
import usermodel.model.{Age, Email, Name, PhoneNum, Surname, User}


import scala.concurrent.Future

class UserDao(redisDataSource: RedisDataSource) {

  def createUser(userCreateRequest: UserCreateRequest): Future[User] = {

//    redisDataSource.redisClient.get(RejectedRequestNum)
  }

  def updateUser(userUpdateRequest: UserUpdateRequest): Future[User] = {

    val user = User(1, Name("name"), Surname("surname"), Age(20), PhoneNum(List("5756")), Email(List("sfsdf")))

    val updUser: Option[String] = redisDataSource.redisClient.get(user.id)
    updUser match {
      case Some(value) => Future.successful(updUser)
      case None =>
    }



//    redisDataSource.redisClient.set(RejectedRequestNum)
  }

//  def getUser: Option[String] = {
//    redisDataSource.redisClient.get(RejectedRequestNum)
//  }
}
