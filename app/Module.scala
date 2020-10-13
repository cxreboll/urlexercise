import com.crp.mongo.Connection
import com.google.inject.AbstractModule
import play.api.libs.concurrent.AkkaGuiceSupport
import providers.ConnectionProvider

class Module extends AbstractModule with AkkaGuiceSupport {

  override def configure(): Unit = {
    bind(classOf[Connection]).toProvider(classOf[ConnectionProvider]).asEagerSingleton
  }

}
