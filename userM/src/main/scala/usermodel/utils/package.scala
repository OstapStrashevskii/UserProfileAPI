package usermodel

package object utils {
  def getRandomId: Int = {
    scala.util.Random.shuffle(1 to 999999999)
  }
}
