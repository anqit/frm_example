package slick.schema

import model.{Id, Timestamps}
import slick.SlickProfileProvider

import java.time.Instant

trait SchemaTraits {
    self: SlickProfileProvider =>

    import profile.api._

    trait QueryProvider[T <: Table[_]] {
        def query: TableQuery[_ <: T]
    }

    trait IdColumns[E <: Id[E]] {
        self: Table[E] =>
        def id: Rep[Int]
    }

    trait TimestampColumns[E <: Timestamps[E]] {
        self: Table[E] =>
        def createdAt = column[Instant]("created_at")

        def updatedAt = column[Instant]("updated_at")
    }

}
