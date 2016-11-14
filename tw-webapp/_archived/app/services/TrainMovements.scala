package services

import java.util.concurrent.atomic.AtomicInteger

import javax.inject.Singleton
import com.google.inject.ImplementedBy
import net.eatcode.trainwatch.movement.TrainMovement
import net.eatcode.trainwatch.movement.DelayWindow
import net.eatcode.trainwatch.search.hazelcast.HzTrainWatchSearch
import net.eatcode.trainwatch.nr.hazelcast.HzClientBuilder

import scala.collection.JavaConversions._

@ImplementedBy(classOf[HazelcastTrainMovments])
trait TrainMovements {
  def count: Int

  def all: Map[DelayWindow, List[TrainMovement]]
}

@Singleton
class HazelcastTrainMovments extends TrainMovements {

  val search = new HzTrainWatchSearch(new HzClientBuilder().buildInstance("trainwatch.eatcode.net"))

  private val atomicCounter = new AtomicInteger()

  override def count: Int = search.delayedTrainsByAllWindows(100).entrySet.size

  override def all = {
    val jmap = search.delayedTrainsByAllWindows(100)
    jmap.keySet.map { k => (k, jmap.get(k).toList) }.toMap
  }
}