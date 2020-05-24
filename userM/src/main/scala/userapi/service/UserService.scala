package userapi.service

import userapi.dao.UserDao
import userapi.dto.{UserCreateRequest, UserUpdateRequest}

import scala.concurrent.ExecutionContext

class UserService(userDao: UserDao)(implicit executionContext: ExecutionContext) extends ProfileService {

  override def createUser(userCreateRequest: UserCreateRequest): CreateResult = userDao.createUser(userCreateRequest)

  override def updateUser(userUpdateRequest: UserUpdateRequest): UpdateResult = userDao.updateUser(userUpdateRequest)
}
