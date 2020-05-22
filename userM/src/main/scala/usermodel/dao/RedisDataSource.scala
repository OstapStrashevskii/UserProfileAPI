package usermodel.dao

import com.redis.RedisClient

class RedisDataSource {

  val redisClient = new RedisClient("localhost", 6379)
}
