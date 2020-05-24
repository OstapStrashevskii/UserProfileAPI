package userapi.utils

object Utils {
  def getRandomId: String = {
    java.util.UUID.randomUUID.toString
  }
}
