package dao

import model.Id

import scala.concurrent.Future

trait IdDao[E <: Id[E]] {
    def getById(id: Int): Future[Option[E]]
    def updateById(e: E): Future[Option[E]]
    def deleteById(id: Int) : Future[Option[Unit]]
}
