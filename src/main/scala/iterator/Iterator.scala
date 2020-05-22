package iterator

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

sealed trait Iterator[E <: Any]

final case class IteratorString(s: String) extends Iterator[Any]

class FlatIterator(i: Iterator[Any]) extends Iterator[String]

object Main extends App {

//  def getIteratorStr(): Iterator[Any] = {
//
//
//
//
//
//
//  }



}

