package iterator

import scala.collection.Iterator

/**
 * 1. Итератор
 * Есть итератор, каждый элемент которого либо строка, либо такой же итератор. Нужно написать обертку в виде чистого итератора со строками.
 *
 * class FlatIterator(i: Iterator[Any]) extends Iterator[String]
 * или
 * def flatten(i: Iterator[Any]): Iterator[String]
 *
 * Пример:
 * Iterator(
 * "1",
 * Iterator(
 * "1.1",
 * "1.2"
 * ),
 * "2",
 * Iterator(
 * Iterator(
 * "2.1.1"
 * ),
 * "2.2"
 * )
 * )
 *
 * Ожидаемый вывод: 1, 1.1, 1.2, 2, 2.1.1, 2.2
 */

object Main extends App {

  def flatten(i: Iterator[Any]): Iterator[String] = {
    def loop(iterator: Iterator[Any], acc: List[String]): List[String] = {
      if (iterator.hasNext) {
        val x = iterator.next()
        x match {
          case s: String =>
            s :: loop(iterator.drop(0),List[String]())
          case iter: Iterator[Object] =>
            loop(iter, acc) ::: loop(iterator.drop(0),List[String]())
          case _ => throw new Exception("Unexpected element")
        }
      } else {
        acc
      }
    }
    Iterator(loop(i, List[String]()).mkString(", "))
  }

  val i = Iterator("1", Iterator("1.1", "1.2"), "2", Iterator(Iterator("2.1.1"), "2.2"))

  val res = flatten(i)
  assert(res.next() == "1, 1.1, 1.2, 2, 2.1.1, 2.2")
}
