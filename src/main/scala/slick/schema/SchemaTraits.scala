package slick.schema

import model.{Id, Timestamps}
import slick.SlickProviders.SlickProfileProvider

import java.time.Instant

trait SchemaTraits {
    self: SlickProfileProvider =>
    import profile.api._

    trait IdColumns[E <: Id[E]] {
        self: Table[E] =>
        def id: Rep[Int]
    }

    trait TimestampColumns[E <: Timestamps[E]] {
        self: Table[E] =>
        def createdAt = column[Instant]("created_at")

        def updatedAt = column[Instant]("updated_at")
    }

    trait QueryProviderComponent[T <: Table [_]] {
        def queryProvider: QueryProvider

        trait QueryProvider {
            def query: TableQuery[_ <: T]
        }
    }
}
