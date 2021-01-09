package slick.dao

import model.{Id, Timestamps}
import slick.SlickProviders.SlickProfileProvider
import slick.schema.SchemaTraits

import java.time.Instant
import scala.concurrent.ExecutionContext.Implicits.global
import scala.reflect.ClassTag

trait SlickDaoTraits { self: SlickProfileProvider with SchemaTraits =>
    import profile.api._

    trait SlickIdDao[E <: Id[E]] { self: QueryProvider[_ <: Table[E] with IdColumns[E]] =>
        def createAction(entity: E)(implicit tpe: ClassTag[E]): DBIOAction[Option[E], NoStream, Effect.Write] =
            ((query returning query.map(_.id) into ((e, id) => e.withId(id))) += entity) map {
                case eid: E => Some(eid)
                case _ => None
            }

        def getByIdAction(id: Int): DBIOAction[Option[E], NoStream, Effect.Read] =
            query.filter(_.id === id).result.headOption

        def updateByIdAction(e: E): DBIOAction[Option[E], NoStream, Effect.Write] =
            query.filter(_.id === e.id).update(e) map {
                case 0 => None
                case _ => Some(e)
            }

        def deleteByIdAction(id: Int): DBIOAction[Option[Unit], NoStream, Effect.Write] =
            query.filter(_.id === id).delete map {
                case 0 => None
                case _ => Some()
            }
    }

    trait SlickTimestampDao[E <: Timestamps[E]] { self: QueryProvider[_ <: Table[E] with TimestampColumns[E]] =>
        def stampCreated(e: E) = {
            val now = Instant.now()
            e.withCreatedAt(now).withUpdatedAt(now)
        }
        def stampUpdated(e: E) = e.withUpdatedAt(Instant.now())

        def createAction(e: E) = query += stampCreated(e)

        def getUpdatedAfterAction(updatedAfter: Instant): DBIOAction[Seq[E], NoStream, Effect.Read] =
            query.filter(_.updatedAt > updatedAfter).result
    }
}
