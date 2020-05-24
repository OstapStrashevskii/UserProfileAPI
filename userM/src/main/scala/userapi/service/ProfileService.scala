package userapi.service

import userapi.dto.{UserCreateRequest, UserUpdateRequest}
import userapi.model.User

import scala.concurrent.Future

trait ProfileService {

  type CreateResult = Future[User]
  type UpdateResult = Future[User]

//  type CreateCommand = UserCreateRequest
//  type UpdateCommand = UserUpdateRequest

  def createUser(r: UserCreateRequest): CreateResult
  def updateUser(r: UserUpdateRequest): UpdateResult
}


