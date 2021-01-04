package model

trait Id[E <: Id[E]] {
    def id: Option[Int]
    def withId(id: Int): E
}
