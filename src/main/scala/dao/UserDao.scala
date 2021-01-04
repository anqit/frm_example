package dao

import model.User

import scala.concurrent.Future

trait UserDao extends IdDao[User] {
    def create(u: User): Future[Option[User]]
}
