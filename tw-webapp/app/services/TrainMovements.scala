package services

import java.util.concurrent.atomic.AtomicInteger

import javax.inject.Singleton
import com.google.inject.ImplementedBy
import net.eatcode.trainwatch.movement.TrainMovement
import net.eatcode.trainwatch.movement.DelayWindow

@ImplementedBy(classOf[HazelcastTrainMovments])
trait TrainMovements {
  def count: Int
  
  def all: Map[DelayWindow, TrainMovement]
}


@Singleton
class HazelcastTrainMovments extends TrainMovements {
  private val atomicCounter = new AtomicInteger()
  
  override def count: Int = atomicCounter.getAndIncrement()
  
  override def all = ???
}
