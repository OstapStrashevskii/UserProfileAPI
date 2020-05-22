package usermodel.service

import usermodel.dto.{UserCreateRequest, UserUpdateRequest}
import usermodel.model.User

import scala.concurrent.Future

trait ProfileService {

  type CreateResult = Future[User]
  type UpdateResult = Future[User]

//  type CreateCommand = UserCreateRequest
//  type UpdateCommand = UserUpdateRequest

  def createUser(r: UserCreateRequest): CreateResult
  def updateUser(r: UserUpdateRequest): UpdateResult
}


