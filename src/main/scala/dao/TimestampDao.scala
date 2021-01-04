package dao

import model.Timestamps

import java.time.Instant
import scala.concurrent.Future

trait TimestampDao [E <: Timestamps[E]] {
    def getUpdatedAfter(updatedAt: Instant): Future[Seq[E]]
}
