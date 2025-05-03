package locadoraFilmes.application.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

public class DatabaseConfig {

    @Value("${spring.datasource.url}")
    String  url;
    @Value("${spring.datasource.username}")
    String  username;
    @Value("${spring.datasource.password}")
    String  password;
    @Value("${spring.datasource.driver-class-name}")
    String  driver;

    @Bean
    public DataSource hikariDataSource(){

        HikariConfig config = new HikariConfig();
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName(driver);
        config.setJdbcUrl(url);

        config.setMaximumPoolSize(10);              // Máximo de conexão ao mesmo tempo
        config.setMinimumIdle(1);                   // Mínomo/tamanho inicial
        config.setPoolName("teste");
        config.setIdleTimeout(600000);               // 10 minutos
        config.setConnectionTimeout(100000);         // 10 segundos       // Tempo para tomar time out
        config.setConnectionTestQuery("select 1");  // Para testar se o banco está funcionando - query de teste

        return new HikariDataSource(config);
    }

}
