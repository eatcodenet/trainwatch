package services

import java.util.concurrent.atomic.AtomicInteger

import javax.inject.Singleton
import com.google.inject.ImplementedBy
import net.eatcode.trainwatch.movement.TrainMovement


@ImplementedBy(classOf[HazelcastTrainMovments])
trait TrainMovements {
  def count: Int
  
  def all: Map[Int, TrainMovement]
}


@Singleton
class HazelcastTrainMovments extends TrainMovements {
  private val atomicCounter = new AtomicInteger()
  
  override def count: Int = atomicCounter.getAndIncrement()
  
  override def all = ???
}
