package userapi.dao

import com.redis.RedisClient

import scala.concurrent.ExecutionContext

class RedisDataSource {

  val redisClient = new RedisClient("localhost", 6379)
}
