package providers

import com.crp.mongo.Connection
import javax.inject.{Inject, Provider, Singleton}
import play.api.Configuration

import scala.concurrent.ExecutionContext

@Singleton
class ConnectionProvider @Inject() (configuration: Configuration)
                                   (implicit ec: ExecutionContext) extends Provider[Connection] {
  override lazy val get = {
    new Connection(configuration)
  }
}

