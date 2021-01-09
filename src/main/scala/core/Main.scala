package core

import com.typesafe.config.ConfigFactory
import slick.SlickProviders.SlickProfileProvider
import slick.jdbc.JdbcBackend.Database
import slick.jdbc.{JdbcProfile, PostgresProfile}
import slick.schema.{AddressSchema, SchemaTraits, UserSchema}

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt
import scala.language.postfixOps

object Main extends App {

    println("starting 'er up")
    val conf = ConfigFactory.load()
    println(s"conf:\n$conf")

    val profile = PostgresProfile
    val db = Database.forConfig("local")
    println(s"db:\n$db")

    import profile.api._

    class UserSchemaImpl(val profile: JdbcProfile) extends UserSchema with SchemaTraits with SlickProfileProvider
    class AddressSchemaImpl(override val profile: JdbcProfile) extends UserSchemaImpl(profile) with AddressSchema with SchemaTraits

    val userSchema = new UserSchemaImpl(profile)
    val addressSchema = new AddressSchemaImpl(profile)

    def createSchema() = {
//        import userSchema._
        import addressSchema._
        val aqp = new AddressQueryProvider {}
        val uqp = new UserQueryProvider {}
        val schema = uqp.query.schema ++ aqp.query.schema

        println("dropping statements:")
        schema.dropIfExistsStatements.foreach(println _)

        println("create statements to run:")
        schema.createStatements.foreach(println _)

        println("statements to run if not exists:")
        schema.createIfNotExistsStatements.foreach(println _)

        val createTableActions = DBIO.seq(
            schema.create // IfNotExists
        )

        db.run(schema.dropIfExists).flatMap(_ =>
            db.run(createTableActions))
    }

    val f = createSchema()

    Await.result(f, 20 seconds)
    f.failed.foreach(print _)
    f.foreach(_ => println("supposedly this worked"))
}
