package userapi.dao

import com.redis.RedisClientPool

class NonBlockingRedisCl2 {


  val clients = new RedisClientPool("localhost", 6379)

  // lpush
  def lp(msgs: List[String]) = {
    clients.withClient {
      client => {
        msgs.foreach(client.lpush("list-l", _))
        client.llen("list-l")
      }
    }
  }

  // rpush
  def rp(msgs: List[String]) = {
    clients.withClient {
      client => {
        msgs.foreach(client.rpush("list-r", _))
        client.llen("list-r")
      }
    }
  }

  // set
  def set(msgs: List[String]) = {
    clients.withClient {
      client => {
        var i = 0
        msgs.foreach { v =>
          client.set("key-%d".format(i), v)
          i += 1
        }
        Some(1000)
      }
    }
  }

//  And here's the snippet that throttles our redis server with the above operations in a non blocking mode using Scala futures:

//  val l = (0 until 5000).map(_.toString).toList
//  val fns = List[List[String] => Option[Int]](lp, rp, set)
//  val tasks = fns map (fn => scala.actors.Futures.future { fn(l) })
//  val results = tasks map (future => future.apply())
}
