package services

import java.util.concurrent.atomic.AtomicInteger

import javax.inject.Singleton
import com.google.inject.ImplementedBy

/**
 * This trait demonstrates how to create a component that is injected
 * into a controller. The trait represents a counter that returns a
 * incremented number each time it is called.
 */
@ImplementedBy(classOf[HazelcastTrainMovments])
trait TrainMovements {
  def count: Int
}

/**
 * This class is a concrete implementation of the [[Counter]] trait.
 * It is configured for Guice dependency injection in the [[Module]]
 * class.
 *
 * This class has a `Singleton` annotation because we need to make
 * sure we only use one counter per application. Without this
 * annotation we would get a new instance every time a [[Counter]] is
 * injected.
 */
@Singleton
class HazelcastTrainMovments extends TrainMovements {
  private val atomicCounter = new AtomicInteger()
  override def count: Int = atomicCounter.getAndIncrement()
}
