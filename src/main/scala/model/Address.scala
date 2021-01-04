package model

import java.time.Instant

case class Address(number: Int, street: String, createdAt: Instant, updatedAt: Instant, ownerId: Int, id: Option[Int] = None) extends Id[Address] with Timestamps[Address] {
    override def withId(id: Int): Address = this.copy(id = Some(id))

    override def withCreatedAt(c: Instant): Address = this.copy(createdAt = c)

    override def withUpdatedAt(u: Instant): Address = this.copy(updatedAt = u)
}
