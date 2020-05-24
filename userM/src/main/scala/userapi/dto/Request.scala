package userapi.dto

import play.api.libs.json.{ Json, OFormat }

sealed trait Request

final case class UserCreateRequest(name: String, surName: String, age: Int, phoneNum: List[String], email: List[String]) extends Request

object UserCreateRequest {
  implicit lazy val photosFormat: OFormat[UserCreateRequest] = Json.format[UserCreateRequest]
}

final case class UserUpdateRequest(id: String, name: String, surName: String, age: Int, phoneNum: List[String], email: List[String]) extends Request

object UserUpdateRequest {
  implicit lazy val photosFormat: OFormat[UserUpdateRequest] = Json.format[UserUpdateRequest]
}
